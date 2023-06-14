package cz.cvut.nss.paymentprocessingservice.service;

import com.hazelcast.map.IMap;
import cz.cvut.nss.paymentprocessingservice.dto.ChangeBalanceRequest;
import cz.cvut.nss.paymentprocessingservice.dto.PaymentRequest;
import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.model.UserAccount;
import cz.cvut.nss.paymentprocessingservice.repository.PaymentRepository;
import cz.cvut.nss.paymentprocessingservice.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static cz.cvut.nss.paymentprocessingservice.MessageConstants.*;

@Service
@AllArgsConstructor
public class PaymentProcessingService {

    private final PaymentRepository paymentProcessingRepository;
    private final UserAccountRepository userAccountRepository;
    private IMap<String, List<Payment>> paymentCache;

    private UserAccount getUserByAccount(String userAccount) {
        return userAccountRepository.findByAccount(userAccount)
                .orElseThrow(() -> new EntityNotFoundException(USER_ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    public UserAccount getUserAccountById(Integer id) {
        return userAccountRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    public Payment getPaymentById(Integer id) {
        return paymentProcessingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PAYMENT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Transactional
    public String createNewPayment(PaymentRequest paymentRequest) {

        Payment payment = new Payment();

        if (!paymentRequest.isInternal() && userAccountRepository.findByAccount(paymentRequest.getToAccount()).isEmpty()) {
            userAccountRepository.save(UserAccount.builder()
                    .userId(1)
                    .account(paymentRequest.getToAccount())
                    .balance(BigDecimal.valueOf(0))
                    .build()
            );
        }

        UserAccount toAccount = getUserByAccount(paymentRequest.getToAccount());
        UserAccount fromAccount = getUserByAccount(paymentRequest.getFromAccount());

        payment.setToAccount(toAccount);
        payment.setFromAccount(fromAccount);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setInternal(paymentRequest.isInternal());
        payment.setInvalidated(false);
        paymentProcessingRepository.save(payment);

        if (paymentRequest.isInternal()) {
            processInternalPayment(paymentRequest);
        } else {
            processExternalPayment(paymentRequest);
        }

        return PAYMENT_CREATED_MESSAGE;
    }

    @Transactional
    private void processInternalPayment(PaymentRequest paymentRequest) {
        UserAccount fromUser = getUserByAccount(paymentRequest.getFromAccount());
        UserAccount toUser = getUserByAccount(paymentRequest.getToAccount());

        fromUser.subtractAmount(paymentRequest.getAmount());
        toUser.addAmount(paymentRequest.getAmount());
        userAccountRepository.save(fromUser);
        userAccountRepository.save(toUser);
    }

    @Transactional
    private void processExternalPayment(PaymentRequest paymentRequest) {
        UserAccount fromUser = getUserByAccount(paymentRequest.getFromAccount());
        fromUser.subtractAmount(paymentRequest.getAmount());
        userAccountRepository.save(fromUser);
    }

    public List<Payment> getAllUserRelatedPayments(String userAccount) {
        UserAccount user = getUserByAccount(userAccount);

        return paymentCache.computeIfAbsent(userAccount, key -> {
            List<Payment> toPayments = paymentProcessingRepository.findByToAccountAndIsInvalidatedFalse(user);
            List<Payment> fromPayments = paymentProcessingRepository.findByFromAccountAndIsInvalidatedFalse(user);
            return Stream.concat(toPayments.stream(), fromPayments.stream()).toList();
        });
    }

    public UserAccount getUserAccountByAccount(String userAccount) {
        return getUserByAccount(userAccount);
    }

    @Transactional
    public void addToUserBalance(ChangeBalanceRequest request) {
        getUserByAccount(request.getAccount()).addAmount(request.getAmount());
    }

    @Transactional
    public void subtractFromUserBalance(ChangeBalanceRequest request) {
        UserAccount user = getUserByAccount(request.getAccount());
        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException(NEGATIVE_BALANCE_EXCEPTION_MESSAGE);
        }
        user.subtractAmount(request.getAmount());
    }
}
