package com.neu.prattle.model;

import java.util.List;

/**
 * A group interface to denote all necessary functions a group must have.
 */
public interface IGroup {
  /**
   * Retrives the unique id of this group.
   * @return String representation of the group ID
   */
  String getGroupId();

  /**
   * The name of the group as given by a user/admin.
   * @return String representing the name of this group.
   */
  String getGroupName();

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
   * A function to set the group name after it has been created.
   * @param groupName Desired new name of the group 
   */
  void setGroupName(String groupName);
  void addUser(String user);
  void removeUser(String userName);
  void addAdmin(String adminName);
}
