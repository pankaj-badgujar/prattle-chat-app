package com.neu.prattle.model;

import java.util.List;
import java.util.Optional;

/**
 * @author Bhargavi Padhya
 * @version 1.1 dated 11/15/2019
 */
public interface IUser {

  /**
   * Getter to get all the currently connected user to this user.
   *
   * @return List of the users that are connected to this user.
   */
  Optional<IMember> getConnectedMembers();

  /**
   * Setter methods to connect this users to the other users passed in the parameter.
   *
   * @param otherMember users that needs to be connected to this user.
   */
  void connectTo(IMember otherMember);

  /**
   * A method to return the list of all groups {@code this} user is a part of.
   *
   * @return list of all groups {@code this} user is a part of.
   */
  List<IMember> getGroupsForUser();

  /**
   * A method to validate if the password is same or not for this user.
   *
   * @return true if the password matches else false
   */
  boolean isCorrectPassword(String password);

}
