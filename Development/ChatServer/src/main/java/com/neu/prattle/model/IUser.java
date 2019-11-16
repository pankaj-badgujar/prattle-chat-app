package com.neu.prattle.model;

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

}
