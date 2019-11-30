package com.neu.prattle.model;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class to represent a group of users. Each Group consists of a list of all of it's users also,
 * the group contains another list that denotes the admin(s) of this group.
 *
 * @author Harshil Mavani
 * @author Devansh Gandhi
 * @version 2.0 dated 2019-11-26
 */
public class Group extends AbstractMember implements IGroup {

  private List<String> users;
  private List<String> admins;
  private Set<IMember> userMember;
  private Set<IMember> adminsMember;

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
  public Group(@JsonProperty("name") String name, @JsonProperty("users") List<String> users,
               @JsonProperty("admins") List<String> admins) {
    this.setName(name);
    this.users = new ArrayList<>(users);
    getAllIMembers(users);
    this.admins = new ArrayList<>(admins);
    this.userMember = getAllIMembers(users);
    this.adminsMember = getAllIMembers(admins);
  }

  /**
   * A parameterized constructor that initializes all fields as required.
   *
   * @param name   Name of the group.
   * @param users  List of users currently present in the group.
   * @param admins List of admins in the group that have privileges above normal users.
   */
  public Group(String name, Set<IMember> users, Set<IMember> admins) {
    this.validateIMembers(users);
    this.validateIMembers(admins);
    this.setName(name);
    this.userMember = users;
    this.adminsMember = admins;
    this.users = new ArrayList<>();
    this.admins = new ArrayList<>();
    users.forEach(user -> this.users.add(user.getName()));
    admins.forEach(user -> this.admins.add(user.getName()));
    getAllIMembers(this.users);
  }

  @Override
  public void setGroupsForUser(IMember group) {
    userMember.forEach(member -> member.setGroupsForUser(group));
  }

  private Set<IMember> getAllIMembers(List<String> userNames) {
    MemberService memberService = MemberServiceImpl.getInstance();
    Set<IMember> allConnectedMembers = new HashSet<>();
    userNames.forEach(member -> {
      Optional<IMember> eachMember = memberService.findMemberByName(member);
      allConnectedMembers.add(eachMember.get());
      eachMember.ifPresent(iMember -> iMember.setGroupsForUser(this));
    });
    return allConnectedMembers;
  }

  private void validateIMembers(Set<IMember> members) {
    members.forEach(member -> {
      Optional<IMember> validateUser = MemberServiceImpl.getInstance().findMemberByName(member.getName());
      if (!validateUser.isPresent()) {
        throw new NoSuchUserPresentException(member.getName() + " is not a valid user.");
      }
    });
  }

  @Override
  public void addUser(String admin, String user) {
    this.validateAdmin(admin);
    this.validateUser(user);
    this.users.add(user);
  }

  @Override
  public void removeUser(String admin, String userName) {
    this.validateAdmin(admin);
    this.users.remove(userName);
    this.admins.remove(userName);
    MemberServiceImpl.getInstance().findMemberByName(userName).ifPresent(member ->
            this.userMember.remove(member));
    MemberServiceImpl.getInstance().findMemberByName(userName).ifPresent(member ->
            this.adminsMember.remove(member));
  }

  @Override
  public void makeAdmin(String admin, String adminName) {
    this.validateAdmin(admin);
    this.validateUser(adminName);
    this.admins.add(adminName);
    MemberServiceImpl.getInstance().findMemberByName(adminName).ifPresent(member ->
            this.adminsMember.add(member));
  }

  @Override
  public void removeAdmin(String admin, String adminToBeRemoved) {
    this.validateAdmin(admin);
    this.validateAdmin(adminToBeRemoved);
    this.admins.remove(adminToBeRemoved);
    MemberServiceImpl.getInstance().findMemberByName(adminToBeRemoved).ifPresent(member ->
            this.adminsMember.remove(member));
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

  private void validateUser(String user) {
    AtomicBoolean validate = new AtomicBoolean(false);
    MemberServiceImpl.getInstance().findMemberByName(user).ifPresent(member ->
            validate.set(true));
    if (!validate.get()) {
      throw new NoSuchUserPresentException("User with name user does not exist");
    }
  }

  @Override
  public IMemberDTO getDTO() {
    return new GroupDTO(this.name, this.admins, this.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Group) {
      return ((Group) obj).getId().equals(this.getId());
    }
    return false;
  }

  @Override
  public Set<String> getAllConnectedMembers() {
    MemberService memberService = MemberServiceImpl.getInstance();
    Set<String> allConnectedMembers = new HashSet<>();
    users.forEach(member -> {
      Optional<IMember> eachMember = memberService.findMemberByName(member);
      eachMember.ifPresent(memberInternal ->
              allConnectedMembers.addAll(memberInternal.getAllConnectedMembers()));
    });
    return allConnectedMembers;
  }
}
