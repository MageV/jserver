package interfaces;

import services.UserProfileServiceImpl;
import tools.DBException;

public interface AccountService {
    boolean getUserByLogin(String login);

    void addNewUser(UserProfileService userProfileServiceImpl) throws DBException;
}
