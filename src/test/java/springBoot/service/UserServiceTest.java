package springBoot.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import springBoot.domain.Role;
import springBoot.domain.User;
import springBoot.repository.UserRepository;

import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSenderService mailSenderService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUserTest() {
        User user = new User();
        user.setEmail("some@gmail.com");
        boolean isCreated = userService.add(user);

        Assert.assertTrue(isCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        verify(userRepository, times(1)).save(user);
        verify(mailSenderService, times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.eq("Mail confirmation"),
                ArgumentMatchers.contains("To confirm your mail go to"));
    }

    @Test
    void addUserFailTest() {
        User user = new User();
        user.setEmail("some@gmail.com");

        doReturn(new User())
                .when(userRepository)
                .findByEmail("some@gmail.com");

        boolean isCreated = userService.add(user);

        Assert.assertFalse(isCreated);

        verify(userRepository, times(0)).save(ArgumentMatchers.any(User.class));
        verify(mailSenderService, times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());
    }

    @Test
    void activateTest() {
        User user = new User();
        user.setActivationCode("some code");

        doReturn(user)
                .when(userRepository)
                .findByActivationCode("some string");

        boolean isActivated = userService.activate("some string");

        Assert.assertTrue(isActivated);
        Assert.assertNull(user.getActivationCode());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void activateFailTest() {
        boolean isActivated = userService.activate("some string");

        Assert.assertFalse(isActivated);

        verify(userRepository, times(0)).save(ArgumentMatchers.any(User.class));
    }
}