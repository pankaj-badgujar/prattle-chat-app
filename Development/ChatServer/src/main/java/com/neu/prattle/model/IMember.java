package com.neu.prattle.model;

import java.io.Serializable;
import java.util.Set;

/**
 * A member class that denotes either a single user or a group of users. Exposes methods to access
 * details.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 10/28/2019
 */
public interface IMember extends Serializable {

  /**
   * A method to return the name of this member.
   *
   * @return String representation of the member name.
   */
  String getName();

  /**
   * Setter method to change the name of the member to the name provided in the parameter.
   *
   * @param name The changed name of this member.
   */
  void setName(String name);

  /**
   * Getter method to get the unique user ID of this member.
   *
   * @return Unique user ID of the user.
   */
  String getId();

  /**
   * Get all connected members to this member as a list of strings with their username.
   *
   * @return List of all the connected members.
   */
  Set<String> getAllConnectedMembers();

  /**
   * A method to add a group to the list of groups, {@code this} user is a part of.
   *
   * @param group the group to be added to the list of groups, {@code this} user is a part of.
   */
  void setGroupsForUser(IMember group);
}
