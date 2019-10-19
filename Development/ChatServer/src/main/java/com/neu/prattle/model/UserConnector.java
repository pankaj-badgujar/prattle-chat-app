package com.neu.prattle.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;

public class UserConnector {
  private final String userFrom;
  private final String userTo;

  public UserConnector(@JsonProperty("userFrom") String userFrom,
                       @JsonProperty("userTo") String userTo) {
    Objects.requireNonNull(userFrom);
    Objects.requireNonNull(userTo);
    this.userFrom = userFrom;
    this.userTo = userTo;
  }

  public String getUserFrom() {
    return userFrom;
  }

  public String getUserTo() {
    return userTo;
  }
}
