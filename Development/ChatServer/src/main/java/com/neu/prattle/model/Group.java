package com.neu.prattle.model;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to represent a group of users. Each Group consists of a list of all of it's users also,
 * the group contains another list that denotes the admin(s) of this group.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 2019-10-16
 */
public class Group extends AbstractMember implements IGroup {
  private List<String> users;
  private List<String> admins;
  private MemberService ms;

  @Override
  public String getName() {
    return this.name;
  }

  /**
   * A parameterized constructor that initializes all fields as required.
   *
   * @param name   Name of the group.
   * @param users  List of users currently present in the group.
   * @param admins List of admins in the group that have privileges above normal users.
   */
  public Group(String name, List<String> users, List<String> admins) {
    this.setName(name);
    this.users = new ArrayList<>(users);
    this.admins = new ArrayList<>(admins);
  }

  public Group(String name, List<String> users) {
    this.setName(name);
    this.users = new ArrayList<>(users);
    ms = MemberServiceImpl.getInstance();
    for(String username: users){
      Optional <IMember> member = ms.findMemberByName(username);
      member.ifPresent(iMember -> ((IUser) iMember).setGroupsForUser(this));
    }
  }

  public Group() {

  }

  @Override
  public void addUser(String admin, String user) {
    this.validateAdmin(admin);
    this.users.add(user);
  }

  @Override
  public void removeUser(String admin, String userName) {
    this.validateAdmin(admin);
    this.users.remove(userName);
  }

  @Override
  public void makeAdmin(String admin, String adminName) {
    this.validateAdmin(admin);
    this.admins.add(adminName);
  }

  @Override
  public void removeAdmin(String admin, String adminToBeRemoved) {
    this.validateAdmin(admin);
    this.validateAdmin(adminToBeRemoved);
    this.admins.remove(adminToBeRemoved);
  }

  @Override
  public List<String> getUsers() {
    return new ArrayList<>(this.users);
  }

  @Override
  public List<String> getAdmins() {
    return new ArrayList<>(this.admins);
  }

  /**
   * A private helper method to validate the admin and make sure that the entity passed as a
   * parameter is indeed an admin of the group.
   *
   * @param admin The name of the admin who needs to validated. Throws an unchecked exception if the
   *              entity is not an admin of this group.
   */
  private void validateAdmin(String admin) {
    if (!this.admins.contains(admin)) {
      throw new InvalidAdminException(admin + " is not an admin of " + this.name + " group");
    }
  }

  @Override
  public List<String> getAllConnectedMembers() {
    MemberService memberService = MemberServiceImpl.getInstance();
    List<String> allConnectedMembers = new ArrayList<>();
    users.forEach(member -> {
      Optional<IMember> eachMember = memberService.findMemberByName(member);
      allConnectedMembers.addAll(eachMember.get().getAllConnectedMembers());
    });
    return allConnectedMembers;
  }

}
