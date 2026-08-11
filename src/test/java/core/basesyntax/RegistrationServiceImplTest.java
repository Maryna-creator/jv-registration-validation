package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void validUser_Ok() {
        User user = createUser("validLogin", "validPassword", 20);
        User registered = registrationService.register(user);
        Assertions.assertEquals(user, registered);
        Assertions.assertNotNull(Storage.people.get(0));
    }

    @Test
    void userNull_NotOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void loginShort_NotOk() {
        User user = createUser("log", "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void passwordShort_NotOk() {
        User user = createUser("validLogin", "pass", 23);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void notValidAge_NotOk() {
        User user = createUser("validLogin", "validPassword", 17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void userLoginExist_NotOk() {
        User user1 = createUser("sameLogin", "password1", 20);
        registrationService.register(user1);

        User user2 = createUser("sameLogin", "password2", 25);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }
}
