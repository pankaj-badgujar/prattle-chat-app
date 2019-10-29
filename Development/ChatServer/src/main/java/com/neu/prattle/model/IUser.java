package com.neu.prattle.model;

public interface IUser {

  /**
   * Getter to get all the currently connected user to this user.
   *
   * @return List of the users that are connected to this user.
   */
  String getConnectedMembers();

  /**
   * Setter methods to connect this users to the other users passed in the parameter.
   *
   * @param otherUsers users that needs to be connected to this user.
   */
  void connectTo(String otherUsers) ;

}
