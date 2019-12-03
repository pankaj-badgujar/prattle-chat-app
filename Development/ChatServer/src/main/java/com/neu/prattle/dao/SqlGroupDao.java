package com.neu.prattle.dao;

import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

/**
 * Concrete implementation of the group dao. This implementation uses a sql databse.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 11/27/2019
 */
public class SqlGroupDao implements GroupDao {

  private static SqlGroupDao groupDao;
  private Session session;

  /**
   * A private constructor to initialize all fields related to establishing data base connection.
   */
  private SqlGroupDao() {
    Configuration configuration = new Configuration().configure().addAnnotatedClass(Group.class);
    this.session = configuration.buildSessionFactory().openSession();
  }

  public SqlGroupDao(Session session) {
    this.session = session;
  }

  /**
   * A public method to return the singleton instance.
   *
   * @return Singleton instance created by sql group dao.
   */
  public static SqlGroupDao getInstance() {
    groupDao = groupDao == null ? new SqlGroupDao() : groupDao;
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

  @Override
  public boolean removeGroup(String name) {
    return false;
  }
}
