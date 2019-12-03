package com.neu.prattle.dao;

import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import java.util.Optional;

/**
 * An interface that exposes basic operations on the User table.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 11/25/2019
 */
public interface UserDao {

  /**
   * A method to persist a user.
   *
   * @param user The user record that needs to be persisted.
   * @return User that was successfully created.
   */
  User createUser(User user);

  /**
   * A method to retrieve a particular user from the database.
   *
   * @return The user who matches the username as provided.
   */
  Optional<IMember> getUser(String username);

  boolean removeUser(String name);
}
