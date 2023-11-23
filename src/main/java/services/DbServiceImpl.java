package services;


import data.dao.UsersDAO;
import data.dao.datasets.UserDataSet;
import interfaces.DBService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import tools.DBException;

public class DbServiceImpl implements DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private final SessionFactory sessionFactory;

    private static DBService _instance;

    public static DBService instance()
    {
        if (_instance==null)
        {
            _instance= new DbServiceImpl();
        }
        return _instance;
    }

    private DbServiceImpl() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }
    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/java_server");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }
    public UserDataSet getUser(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UserDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
    public UserDataSet ifExistUser(String name) throws DBException {
        try
        {
            Session session=sessionFactory.openSession();
            UsersDAO dao=new UsersDAO(session);
            long id=dao.getUserId(name);
            UserDataSet dataSet=dao.get(id);
            session.close();
            return  dataSet;
        }
        catch (Exception e)
        {
            throw new DBException(e);
        }
    }

    public void addUser(String name) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(name);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
