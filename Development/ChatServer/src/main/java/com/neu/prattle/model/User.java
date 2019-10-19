package com.neu.prattle.model;

import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.service.UserService;
import com.neu.prattle.service.UserServiceImpl;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;
import java.util.UUID;

/***
 * A User object represents a basic account information for a user.
 *
 * @author Devansh Gandhi
 * @version dated 2019-10-16
 */
public class User {

  private String name;
  private final String userID;
  private String connectedTo;

  /**
   * A constructor using which we can create an object of the class {@link User} which takes in the
   * name of the user.
   *
   * @param name The name of the user whose object needs to be created.
   */
  public User(@JsonProperty("name") String name) {
    this.name = name;
    this.connectedTo = null;
    this.userID = UUID.randomUUID().toString();
  }

  public User() {
    this.name = null;
    userID = UUID.randomUUID().toString();
    this.connectedTo = null;
  }

  /**
   * Getter method to get the name of the user attached to this object.
   *
   * @return Name of the user.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter method to change the name of the user to the name provided in the parameter.
   *
   * @param name The changed name of this user.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter method to get the unique user ID of this user.
   *
   * @return Unique user ID of the user.
   */
  public String getUserID() {
    return userID;
  }

  /**
   * Getter to get all the currently connected user to this user.
   *
   * @return List of the users that are connected to this user.
   */
  public String getConnectedUsers() {
    return connectedTo;
  }

  /**
   * Setter methods to connect this users to the other users passed in the parameter.
   *
   * @param otherUsers users that needs to be connected to this user.
   */
  public void connectTo(String otherUsers) {

    UserService allRegisteredUsers = UserServiceImpl.getInstance();

    if (!allRegisteredUsers.findUserByName(otherUsers).isPresent()) {
      throw new NoSuchUserPresentException(otherUsers);
    }

    this.connectedTo = otherUsers;
  }

  /***
   * Returns the hashCode of this object.
   *
   * As name can be treated as a sort of identifier for
   * this instance, we can use the hashCode of "name"
   * for the complete object.
   *
   *
   * @return hashCode of "this"
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /***
   * Makes comparison between two user accounts.
   *
   * Two user objects are equal if their name are equal ( names are case-sensitive )
   *
   * @param obj Object to compare
   * @return a predicate value for the comparison.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof User))
      return false;

    User user = (User) obj;
    return user.name.equals(this.name);
  }
}
