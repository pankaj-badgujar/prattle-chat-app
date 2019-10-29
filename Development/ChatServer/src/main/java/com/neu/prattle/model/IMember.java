package com.neu.prattle.model;

import java.util.List;

/**
 * A member class that denotes either a single user or a group of users. Exposes methods to access
 * details.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 10/28/2019
 */
public interface IMember {

  /**
   * A method to return the name of this member.
   * @return String representation of the member name.
   */
  List<String> getName();

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

}
