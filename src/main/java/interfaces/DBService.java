package interfaces;

import data.dao.datasets.UserDataSet;
import tools.DBException;

public interface DBService {
    void addUser(String login) throws DBException;

    UserDataSet ifExistUser(String login) throws DBException;
}
