package com.neu.prattle.dao;

import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

import javax.inject.Inject;

/**
 * Concrete implementation of the group dao. This implementation uses a sql databse.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 11/27/2019
 */
public class SqlGroupDao implements GroupDao{

  private static SqlGroupDao groupDao;

  @Inject
  private Session session;

  /**
   * A private constructor to initialize all fields related to establishing data base connection.
   */
  private SqlGroupDao() {
    Configuration configuration = new Configuration().configure().addAnnotatedClass(User.class);
    SessionFactory sf = configuration.buildSessionFactory();
    this.session = sf.openSession();
  }

  public SqlGroupDao(Session session) {
    this.session = session;
  }

  /**
   * A public method to return the singleton instance.
   * @return Singleton instance created by sql group dao.
   */
  public static SqlGroupDao getInstance() {
    if(groupDao == null) {
      groupDao = new SqlGroupDao();
    }
    return groupDao;
  }

  @Override
  public Group createGroup(Group group) {
    session.save(group);
    return group;
  }

  @Override
  public Optional<IMember> findGroup(String name) {
    return Optional.ofNullable(session.get(Group.class, name));
  }
}
