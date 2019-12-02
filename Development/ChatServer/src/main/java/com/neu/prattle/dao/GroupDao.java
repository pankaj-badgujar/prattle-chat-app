package com.neu.prattle.dao;

import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;

import java.util.Optional;

/**
 * An interface to represent operations offered by a group data access object.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 11/26/2019
 */
public interface GroupDao {

  /**
   * A function to create a specific group as requested by the user.
   * @param group Group object that needs to be persisted.
   * @return Group that was recently created.
   */
  Group createGroup(Group group);

  /**
   * A method to find a group from the database.
   * @param name Unique name identifier of the group.
   * @return Optional object of the group found from the database.
   */
  Optional<IMember> findGroup(String name);
}
