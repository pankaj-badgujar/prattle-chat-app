package com.neu.prattle.model;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.service.MemberService;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.apache.log4j.Logger;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * A class to represent a group of users. Each Group consists of a list of all of it's users also,
 * the group contains another list that denotes the admin(s) of this group.
 *
 * @author Harshil Mavani
 * @author Devansh Gandhi
 * @version 2.0 dated 2019-11-26
 */
@Entity
public class Group extends AbstractMember implements IGroup {

  @ElementCollection
  private List<String> users;
  @ElementCollection
  private List<String> admins;
  private MemberService ms;
  private final static Logger logger = Logger.getLogger(Group.class);

  @Transient
  private Set<IMember> userMember;
  @Transient
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
    this.initializeFields(name, users, admins);
  }

  /**
   * A parameterized constructor that initializes all fields as required.
   *
   * @param name   Name of the group.
   * @param users  List of users currently present in the group.
   * @param admins List of admins in the group that have privileges above normal users.
   */
  public Group(String name, List<String> users, List<String> admins, MemberService ms) {
    super(ms);
    this.initializeFields(name, users, admins);
  }

  /**
   * A private helper method to assign all fields from constructors
   */
  private void initializeFields(String name, List<String> users, List<String> admins) {
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
    this.assignGroupToUsers(name, users, admins);
  }

  @Override
  public void setGroupsForUser(IMember group) {
    userMember.forEach(member -> member.setGroupsForUser(group));
  }

  private Set<IMember> getAllIMembers(List<String> userNames) {
    Set<IMember> allConnectedMembers = new HashSet<>();
    userNames.forEach(member -> {
      Optional<IMember> eachMember = this.ms.findMemberByName(member);
      allConnectedMembers.add(eachMember.get());
      eachMember.ifPresent(iMember -> iMember.setGroupsForUser(this));
    });
    return allConnectedMembers;
  }

  public Group() {
    logger.info("A Group created.");
  }

  public Group(String name, Set<IMember> users, Set<IMember> admins, MemberService ms) {
    super(ms);
    this.assignGroupToUsers(name, users, admins);
  }

  private void validateIMembers(Set<IMember> members) {
    members.forEach(member -> {
      Optional<IMember> validateUser = this.ms.findMemberByName(member.getName());
      if (!validateUser.isPresent()) {
        throw new NoSuchUserPresentException(member.getName() + " is not a valid user.");
      }
    });
  }

  private void assignGroupToUsers(String name, Set<IMember> users, Set<IMember> admins) {
    this.validateIMembers(users);
    this.validateIMembers(admins);
    this.setName(name);
    this.userMember = users;
    this.adminsMember = admins;
    this.users = new ArrayList<>();
    this.admins = new ArrayList<>();
    users.forEach(user -> this.users.add(user.getName()));
    admins.forEach(user -> this.admins.add(user.getName()));
  }

  @Override
  public void addUser(String admin, String user) {
    this.validateAdmin(admin);
    this.validateUser(user);
    this.users.add(user);
    logger.info(admin+" added "+user+" to the group "+this.getName());
  }

  @Override
  public void removeUser(String admin, String userName) {
    this.validateAdmin(admin);
    this.users.remove(userName);
    logger.info(admin+" removed "+userName+" from the group "+this.getName());
    this.admins.remove(userName);
    Optional<IMember> memberEntity = this.ms.findMemberByName(userName);
    validateMember(memberEntity, userName);
    memberEntity.ifPresent(member -> this.userMember.remove(member));
    memberEntity.ifPresent(member -> this.adminsMember.remove(member));
  }

  @Override
  public void makeAdmin(String admin, String adminName) {
    this.validateAdmin(admin);
    this.validateUser(adminName);
    this.admins.add(adminName);
    logger.info(admin+" made "+ adminName+", an admin of the group "+this.getName());
    Optional<IMember> adminMember = this.ms.findMemberByName(adminName);
    validateMember(adminMember, adminName);
    adminMember.ifPresent(member -> this.adminsMember.add(member));
  }

  private void validateMember(Optional<IMember> member, String name) {
    if (!member.isPresent()) {
      throw new NoSuchUserPresentException(name);
    }
  }

  @Override
  public void removeAdmin(String admin, String adminToBeRemoved) {
    this.validateAdmin(admin);
    this.validateAdmin(adminToBeRemoved);
    this.admins.remove(adminToBeRemoved);
    logger.info(admin+" cancelled admin rights for the admin, "+adminToBeRemoved);
    Optional<IMember> adminMember = this.ms.findMemberByName(adminToBeRemoved);
    validateMember(adminMember, adminToBeRemoved);
    adminMember.ifPresent(member -> this.adminsMember.remove(member));
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

  private void validateUser(String user) {
    AtomicBoolean validate = new AtomicBoolean(false);
    this.ms.findMemberByName(user).ifPresent(member -> validate.set(true));
    if (!validate.get()) {
      throw new NoSuchUserPresentException("User with name user does not exist");
    }
  }

  @Override
  public IMemberDTO getDTO() {
    return new GroupDTO(this.id, this.name, this.admins, this.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Group) {
      return ((Group) obj).getId() == (this.getId());
    }
    return false;
  }

  @Override
  public Set<String> getAllConnectedMembers() {
    Set<String> allConnectedMembers = new HashSet<>();
    users.forEach(member -> {
      Optional<IMember> eachMember = this.ms.findMemberByName(member);
      eachMember.ifPresent(memberInternal ->
              allConnectedMembers.addAll(memberInternal.getAllConnectedMembers()));
    });
    return allConnectedMembers;
  }
}
