package com.neu.prattle.exceptions;

/**
 * An unchecked exception thrown when the admin trying to edit the group is invalid. Invalid denotes
 * a situation when the entity trying to edit the group is not an admin of that group.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 2019-10-14
 *
 */
public class InvalidAdminException extends RuntimeException {

  /**
   * Parametrized constructor to accept the message of the exception and cascade it to the Super
   * class.
   * @param  message Message that gives more details about the exception.
   */
  public InvalidAdminException(String message)  {
    super(message);
  }
}
