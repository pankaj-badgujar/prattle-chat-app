package com.neu.prattle.exceptions;

/***
 * An representation of an error which is thrown where a request has been made
 * for connecting a user with other of a user object that does not exists in the system.
 *
 * @author Devansh Gandhi
 * @version dated 2019-10-17
 */
public class NoSuchUserPresentException extends RuntimeException {

  /**
   * Constructs a new runtime exception with the specified detail message.
   *
   * @param username the detail message. The detail message is saved for later retrieval by the
   *                 {@link #getMessage()} method.
   */
  public NoSuchUserPresentException(String username) {
    super("User with name " + username + " does not exist.");
  }

  public NoSuchUserPresentException() {
    super();
  }

}
