package com.neu.prattle.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class GroupData {
  private final String groupName;
  private final List<String> members;

  public GroupData(@JsonProperty("groupName") String groupName, @JsonProperty("newMembers") List<String> members) {
    this.groupName = groupName;
    this.members = members;
  }

  public String getGroupName() {
    return groupName;
  }

  public List<String> getMembers() {
    return members;
  }
}
