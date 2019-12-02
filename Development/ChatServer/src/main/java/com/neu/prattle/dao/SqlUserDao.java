package com.neu.prattle.dao;

import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

/**
 * Concrete implementation of the UserDao with SQL database. This implementation enables persistence
 * using a sql database.
 */
public class SqlUserDao implements UserDao {

  private static SqlUserDao userDao;
  private Session session;

  public SqlUserDao(Session session) {
    this.session = session;
  }

  private SqlUserDao() {
    Configuration configuration = new Configuration().configure().addAnnotatedClass(User.class);
    SessionFactory sf = configuration.buildSessionFactory();
    this.session = sf.openSession();
  }

  public static UserDao getInstance() {
    userDao = userDao == null ? (new SqlUserDao()) : userDao;
    return userDao;
  }

  @Override
  public User createUser(User user) {
    session.save(user);
    return user;
  }

  @Override
  public Optional<IMember> getUser(String username) {
    return Optional.ofNullable(session.get(User.class, username));
  }
}
