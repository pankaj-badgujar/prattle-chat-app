package com.neu.prattle.dao;

import com.neu.prattle.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Concrete implementation of the UserDao with SQL database. This implementation enables persistence
 * using a sql database.
 */
public class SqlUserDao implements UserDao {

  private static SqlUserDao userDao;
  private Session session;

  static {
    userDao = new SqlUserDao();
  }

  private SqlUserDao() {
    Configuration configuration = new Configuration().configure().addAnnotatedClass(User.class);
    SessionFactory sf = configuration.buildSessionFactory();
    this.session = sf.openSession();
  }

  public static UserDao getInstance() {
    return userDao;
  }

  @Override
  public User createUser(User user) {
    session.save(user);
    return user;
  }
}
