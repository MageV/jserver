package services;

import interfaces.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {
    private final String login;
    private final String pass;
    private final String email;
    public UserProfileServiceImpl(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }
    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }
}
