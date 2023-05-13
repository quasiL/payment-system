package cz.cvut.nss.paymentprocessingservice;

public final class MessageConstants {

    private MessageConstants() {}

    public static final String USER_ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "UserAccount not found";
    public static final String TRANSACTION_NOT_FOUND_EXCEPTION_MESSAGE = "Transaction not found";
    public static final String PAYMENT_NOT_FOUND_EXCEPTION_MESSAGE = "Transaction not found";
    public static final String NEGATIVE_BALANCE_EXCEPTION_MESSAGE = "Insufficient balance";

    public static final String PAYMENT_CREATED_MESSAGE = "Payment created";
}
