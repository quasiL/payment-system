package cz.cvut.nss.userservice.service;

import cz.cvut.nss.userservice.model.ChangeRequest;
import cz.cvut.nss.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Method for changing user's avatar by updating a link to it
     * @param request Contains user's email and new avatar url
     */
    public void changeAvatar(ChangeRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        user.setAvatar(request.getAvatar());
        userRepository.save(user);
        log.info("User avatar has been changed");
    }
}
