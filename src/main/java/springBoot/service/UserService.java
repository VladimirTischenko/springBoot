package springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springBoot.domain.User;
import springBoot.repository.UserRepository;

import java.util.UUID;

/**
 * Created by Vladimir on 20.05.2020.
 */
@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final MailSenderService senderService;

    @Autowired
    public UserService(UserRepository userRepository, MailSenderService senderService) {
        this.userRepository = userRepository;
        this.senderService = senderService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByName(s);
    }

    public boolean addUser(User user) {
        User userByName = userRepository.findByEmail(user.getEmail());

        if (userByName != null) {
            return false;
        }

        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);
        userRepository.save(user);

        String text = "Hello, " + user.getName() + "!\n" +
                "To confirm your mail go to http://localhost:8080/mailConfirmation/" + activationCode;
        senderService.send(user.getEmail(), "Mail confirmation", text);

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
}
