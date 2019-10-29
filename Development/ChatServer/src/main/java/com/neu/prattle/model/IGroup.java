package com.neu.prattle.model;

import java.util.List;

/**
 * A group interface to denote all necessary functions a group must have. Each group has options to
 * edit admins and users. A group consists of a number of users. Also, every group has a list of
 * admins who enjoy special privileges to add and remove users.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 2019-10-26
 */
public interface IGroup {

  /**
   * Returns the list of users currently a part of this group.
   * @return List of String representing names of the users.
   */
  List<String> getUsers();

  /**
   * Returns the list of admins of this group.
   * @return List of String representing names of the admins of this group.
   */
  List<String> getAdmins();

  /**
   * A function to add a user to the current list of users present in this group.
   * @param user The new user that has to be added.
   */
  void addUser(String admin, String user);

  /**
   * A function to remove a user from a group.
   * @param userName name of the user that needs to be removed from the group
   */
  void removeUser(String admin, String userName);

  /**
   * A function to add to the list of already existing admins.
   * @param adminName Name of the person who wants to be an admin.
   */
  void makeAdmin(String admin, String adminName);

  /**
   * A function to remove from the list of already existing admins.
   * @param adminName Name of the admin who has to be removed.
   */
  void removeAdmin(String admin, String adminName);
}
