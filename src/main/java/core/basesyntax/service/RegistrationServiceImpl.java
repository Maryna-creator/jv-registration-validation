package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("user is not found");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MAX_LENGTH) {
            throw new RegistrationException("Login too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already exists");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (user.getPassword().length() < MAX_LENGTH) {
            throw new RegistrationException("Password too short");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
