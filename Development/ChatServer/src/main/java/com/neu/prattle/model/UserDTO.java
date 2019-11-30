package com.neu.prattle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO implements IMemberDTO {

  private final IMember connectedTo;
  private final List<String> groups;
  private final String username;

  @Override
  public String getUsername() {
    return this.username;
  }

  public UserDTO(String name, IMember connectedTo, List<IMember> groups) {
    this.connectedTo = connectedTo;
    this.username = name;
    this.groups = groups.stream().map(IMember::getName).collect(Collectors.toList());
  }

  public IMember getConnectedTo() {
    return connectedTo;
  }

  public List<String> getGroups() {
    return new ArrayList<>(groups);
  }
}
