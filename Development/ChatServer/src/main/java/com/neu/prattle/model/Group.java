package com.neu.prattle.model;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * A class to represent a group of users. Each Group consists of a list of all of it's users also,
 * the group contains another list that denotes the admin(s) of this group.
 *
 * @author Bhargavi Padhya
 * @version 1.1 dated 11/15/2019
 */
public class Group extends AbstractMember implements IGroup {

  private List<String> users;
  private List<String> admins;
  private MemberService ms;
  private final static Logger logger = Logger.getLogger(Group.class);

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
    logger.info("Group created with the following name: "+ name);
    logger.info("Following are the users added to the group: "+name);
    for(String u: users)
      logger.info(u);
    logger.info("Following are the existing admins for the group:" +name);
    for(String a: admins)
      logger.info(a);
  }

  public Group(String name, List<String> users) {
    this.setName(name);
    this.users = new ArrayList<>(users);
    ms = MemberServiceImpl.getInstance();
    for (String username : users) {
      Optional<IMember> member = ms.findMemberByName(username);
      member.ifPresent(iMember -> ((IUser) iMember).setGroupsForUser(this));
    }
  }

  public Group() {
    logger.info("A Group created.");
  }

  @Override
  public void addUser(String admin, String user) {
    this.validateAdmin(admin);
    this.users.add(user);
    logger.info(admin+" added "+user+" to the group "+this.getName());
  }

  @Override
  public void removeUser(String admin, String userName) {
    this.validateAdmin(admin);
    this.users.remove(userName);
    logger.info(admin+" removed "+userName+" from the group "+this.getName());
  }

  @Override
  public void makeAdmin(String admin, String adminName) {
    this.validateAdmin(admin);
    this.admins.add(adminName);
    logger.info(admin+" made "+ adminName+", an admin of the group "+this.getName());
  }

  @Override
  public void removeAdmin(String admin, String adminToBeRemoved) {
    this.validateAdmin(admin);
    this.validateAdmin(adminToBeRemoved);
    this.admins.remove(adminToBeRemoved);
    logger.info(admin+" cancelled admin rights for the admin, "+adminToBeRemoved);
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
      logger.error(admin+" is not the admin of the group "+this.getName());
      throw new InvalidAdminException(admin + " is not an admin of " + this.name + " group");
    }
  }

  @Override
  public Set<String> getAllConnectedMembers() {
    MemberService memberService = MemberServiceImpl.getInstance();
    Set<String> allConnectedMembers = new HashSet<>();
    users.forEach(member -> {
      Optional<IMember> eachMember = memberService.findMemberByName(member);
      allConnectedMembers.addAll(eachMember.get().getAllConnectedMembers());
    });
    return allConnectedMembers;
  }

}
