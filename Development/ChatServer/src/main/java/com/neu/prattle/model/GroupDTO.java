package com.neu.prattle.model;

import java.util.ArrayList;
import java.util.List;

public class GroupDTO implements IMemberDTO {

  private final int id;
  private final String username;
  private final List<String> admins;
  private final List<String> users;

  public GroupDTO(int id, String name, List<String> admins, List<String> users) {
    this.username = name;
    this.admins = admins;
    this.users = users;
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public List<String> getAdmins() {
    return new ArrayList<>(this.admins);
  }

  public List<String> getUsers() {
    return new ArrayList<>(this.users);
  }

  public int getId() {
    return id;
  }
}
