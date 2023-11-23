package services;
import data.dao.datasets.UserDataSet;
import interfaces.AccountService;
import interfaces.DBService;
import interfaces.UserProfileService;
import tools.AppContext;
import tools.DBException;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService {
    private final Map<String, UserProfileServiceImpl> loginToProfile;
    private final Map<String, UserProfileServiceImpl> sessionIdToProfile;
    private final DBService dbServiceImpl;

    private static AccountService _instance;

    public static AccountService instance(AppContext context)
    {
        if(_instance==null)
        {
            _instance=new AccountServiceImpl((DBService) context.get(DBService.class));
        }
        return _instance;
    }

    private AccountServiceImpl(DBService dbServiceImpl) {
        this.loginToProfile=new HashMap<>();
        this.sessionIdToProfile=new HashMap<>();
        this.dbServiceImpl = dbServiceImpl;
    }

    public void addNewUser(UserProfileService userProfileServiceImpl) throws DBException {
       // loginToProfile.put(userProfile.getLogin(), userProfile);
        dbServiceImpl.addUser(userProfileServiceImpl.getLogin());

    }
    public boolean getUserByLogin(String login) {
        boolean result=false;
        try
        {
            UserDataSet userDataSet= dbServiceImpl.ifExistUser(login);
            result=true;
        } finally {
            return result;
        }
        //return loginToProfile.get(login);
    }

    public UserProfileServiceImpl getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfileServiceImpl userProfileServiceImpl) {
        sessionIdToProfile.put(sessionId, userProfileServiceImpl);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
