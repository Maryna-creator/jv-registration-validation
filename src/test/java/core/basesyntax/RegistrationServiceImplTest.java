package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User user = createUser("validLogin", "validPassword", 20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_userNull_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_NotOk() {
        User user = createUser(null, "validPassword", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_NotOk() {
        User user = createUser("validLogin", null, 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_NotOk() {
        User user = createUser("validLogin", "validPassword", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength0_NotOk() {
        User user = createUser("", "validPassword", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength3_NotOk() {
        User user = createUser("log", "validPassword", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginShort5_NotOk() {
        User user = createUser("login", "validPassword", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength6_Ok() {
        User user = createUser("validL", "validPassword", 22);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginLength8_Ok() {
        User user = createUser("validLog", "validPassword", 22);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordLength0_NotOk() {
        User user = createUser("validLogin", "", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength3_NotOk() {
        User user = createUser("validLogin", "pas", 22);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordShort5_NotOk() {
        User user = createUser("validLogin", "passW", 23);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength6_Ok() {
        User user = createUser("validLogin", "passWo", 23);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordLength8_Ok() {
        User user = createUser("validLogin", "passWord", 23);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_notValidAgeIs17_NotOk() {
        User user = createUser("validLogin", "validPassword", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageIs18_Ok() {
        User user = createUser("validLogin", "validPassword", 18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = createUser("validLogin", "validPassword", -1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginExist_NotOk() {
        User user = createUser("sameLogin", "password1", 20);
        Storage.people.add(user);

        User sameUser = createUser("sameLogin", "password2", 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(sameUser));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
