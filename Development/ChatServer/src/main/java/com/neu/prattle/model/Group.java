package com.neu.prattle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a group of users. Each Group consists of a list of all of it's users
 * also, the group contains another list that denotes the admin(s) of this group.
 */
public class Group implements IGroup{
  private final String groupId;
  private String groupName;
  private List<String> users;
  private List<String> admins;

  /**
   * A paramterized constructor that initializes all fields as required.
   * @param name Name of the group.
   * @param users List of users currently present in the group.
   * @param admins List of admins in the group that have privileges above normal users.
   */
  Group(String name, List<String> users, List<String> admins) {
    this.groupId = "auto-generated-id-here";
    this.groupName = name;
    this.users = new ArrayList<>(users);
    this.admins = new ArrayList<>(admins);
  }

  @Override
  public String getGroupName() {
    return this.groupName;
  }

  @Override
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  @Override
  public String getGroupId() {
    return groupId;
  }

  @Override
  public void addUser(String user) {
    this.users.add(user);
  }

  @Override
  public void removeUser(String userName) {
    this.users.remove(userName);
  }

  @Override
  public void addAdmin(String adminName) {
    this.admins.add(adminName);
  }

  @Override
  public List<String> getUsers() {
    return new ArrayList<>(this.users);
  }

  @Override
  public List<String> getAdmins() {
    return new ArrayList<>(this.admins);
  }
}
