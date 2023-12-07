package com.example.repository;

import com.example.model.User;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.SUPPORTS)
    public ArrayList<User> queryAllUser() {
        // String SQL
        String sql = "SELECT * FROM USER";

        // Query
        Query query = entityManager.createNativeQuery(sql);

        // Get result List
        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        // new return value
        ArrayList<User> users = new ArrayList<>();

        // Put result List from DB into Object
        resultList.forEach(result -> {
            User user = new User();
            user.setId(((Integer) result[0]).longValue());
            user.setName((String) result[1]);
            user.setSurname((String) result[2]);
            user.setAge(((Integer) result[3]).longValue());
            users.add(user);
        });

        return users;
    }
}
