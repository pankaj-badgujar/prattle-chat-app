package com.neu.prattle.model;

import java.util.List;
import java.util.Optional;

/**
 * @author Devansh Gandhi
 * @version 1.0 dated 11/1/2019
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
   * @return list of all groups {@code this} user is a part of.
   */
  List<IMember> getGroupsForUser();

  void setGroupsForUser(IMember group);

}
