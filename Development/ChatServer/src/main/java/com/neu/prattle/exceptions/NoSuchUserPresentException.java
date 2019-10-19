package com.neu.prattle.exceptions;

public class NoSuchUserPresentException extends RuntimeException {
  public NoSuchUserPresentException(String username) {
    super("User with name " + username + " does not exist.");
  }
}
