package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void validUser_Ok() {
        setUp();
        User user = createUser("validLogin", "validPassword", 20);
        Assertions.assertEquals(user,registrationService.register(user));
        Assertions.assertNotNull(Storage.people.get(0));
    }

    @Test
    void userNull_NotOk() {
        setUp();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void loginNull_NotOk() {
        setUp();
        User user = createUser(null, "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void passwordNull_NotOk() {
        setUp();
        User user = createUser("validLogin", null, 20);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void ageNull_NotOk() {
        setUp();
        User user = createUser("validLogin", "validPassword", null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void loginShort5_NotOk() {
        setUp();
        User user = createUser("login", "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void loginLength6_Ok() {
        setUp();
        User user = createUser("validL", "validPassword", 22);
        Assertions.assertNotNull(registrationService.register(user));
    }

    @Test
    void loginLength8_Ok() {
        setUp();
        User user = createUser("validLog", "validPassword", 22);
        Assertions.assertNotNull(registrationService.register(user));
    }

    @Test
    void passwordShort5_NotOk() {
        setUp();
        User user = createUser("validLogin", "passW", 23);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void passwordLength6_Ok() {
        setUp();
        User user = createUser("validLogin", "passWo", 23);
        Assertions.assertNotNull(registrationService.register(user));
    }

    @Test
    void passwordLength8_Ok() {
        setUp();
        User user = createUser("validLogin", "passWord", 23);
        Assertions.assertNotNull(registrationService.register(user));
    }

    @Test
    void notValidAgeIs17_NotOk() {
        setUp();
        User user = createUser("validLogin", "validPassword", 17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void ageIs18_Ok() {
        setUp();
        User user = createUser("validLogin", "validPassword", 18);
        Assertions.assertNotNull(registrationService.register(user));
    }

    @Test
    void negativeAge() {
        setUp();
        User user = createUser("validLogin", "validPassword", -1);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void userLoginExist_NotOk() {
        setUp();
        User user1 = createUser("sameLogin", "password1", 20);
        registrationService.register(user1);

        User user2 = createUser("sameLogin", "password2", 25);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }
}
