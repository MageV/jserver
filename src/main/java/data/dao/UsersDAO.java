package data.dao;

import data.dao.datasets.UserDataSet;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsersDAO {
    private final Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UserDataSet get(long id) throws RuntimeException {
        return (UserDataSet) session.get(UserDataSet.class, id);
    }

    public long getUserId(String name) throws RuntimeException {
        CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteriaQuery=criteriaBuilder.createQuery(UserDataSet.class);
        Root<UserDataSet> root=criteriaQuery.from(UserDataSet.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"),name));
        Query<UserDataSet> query=session.createQuery(criteriaQuery);
        return query.getSingleResult().getId();
    }

    public long insertUser(String name) throws RuntimeException {
        return (Long) session.save(new UserDataSet(name));
    }
}
