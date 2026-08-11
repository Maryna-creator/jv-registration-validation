package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void register_validUser_Ok() {
        setUp();
        User user = createUser("validLogin", "validPassword", 20);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_userNull_NotOk() {
        setUp();
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_NotOk() {
        setUp();
        User user = createUser(null, "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_NotOk() {
        setUp();
        User user = createUser("validLogin", null, 20);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_NotOk() {
        setUp();
        User user = createUser("validLogin", "validPassword", null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength0_NotOk() {
        setUp();
        User user = createUser("", "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength3_NotOk() {
        setUp();
        User user = createUser("log", "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginShort5_NotOk() {
        setUp();
        User user = createUser("login", "validPassword", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLength6_Ok() {
        setUp();
        User user = createUser("validL", "validPassword", 22);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginLength8_Ok() {
        setUp();
        User user = createUser("validLog", "validPassword", 22);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordLength0_NotOk() {
        setUp();
        User user = createUser("validLogin", "", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength3_NotOk() {
        setUp();
        User user = createUser("validLogin", "pas", 22);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordShort5_NotOk() {
        setUp();
        User user = createUser("validLogin", "passW", 23);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength6_Ok() {
        setUp();
        User user = createUser("validLogin", "passWo", 23);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_passwordLength8_Ok() {
        setUp();
        User user = createUser("validLogin", "passWord", 23);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_notValidAgeIs17_NotOk() {
        setUp();
        User user = createUser("validLogin", "validPassword", 17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageIs18_Ok() {
        setUp();
        User user = createUser("validLogin", "validPassword", 18);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_negativeAge_NotOk() {
        setUp();
        User user = createUser("validLogin", "validPassword", -1);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginExist_NotOk() {
        setUp();
        User user1 = createUser("sameLogin", "password1", 20);
        Storage.people.add(user1);

        User user2 = createUser("sameLogin", "password2", 25);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }
}
