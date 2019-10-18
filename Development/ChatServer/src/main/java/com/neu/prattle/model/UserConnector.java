package com.neu.prattle.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class UserConnector {
  private final String userFrom;
  private final String userTo;

  public UserConnector(@JsonProperty("userFrom") String userFrom,
                       @JsonProperty("userTo") String userTo) {
    this.userFrom = userFrom;
    this.userTo = userTo;
  }

  public UserConnector(List<String> userPair) {
    this.userFrom = userPair.get(0);
    this.userTo = userPair.get(1);
  }

  public String getUserFrom() {
    return userFrom;
  }

  public String getUserTo() {
    return userTo;
  }
}
