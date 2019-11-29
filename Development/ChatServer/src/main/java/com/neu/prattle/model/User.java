package com.neu.prattle.model;

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

/***
 * A User object represents a basic account information for a user.
 *
 * @author Bhargavi Padhya
 * @version 1.2 dated 2019-11-15
 */
public class User extends AbstractMember implements IUser {

  private IMember connectedTo;
  private List<IMember> groups;
  private String password;


  /**
   * A constructor using which we can create an object of the class {@link User} which takes in the
   * name of the user.
   *
   * @param name     The name of the user whose object needs to be created.
   * @param password Password that will be used to authenticate the user during run time.
   */
  public User(@JsonProperty("username") String name, @JsonProperty("password") String password) {
    this.name = name;
    this.connectedTo = null;
    groups = new ArrayList<>();
    this.password = password;
  }

  /**
   * A constructor using which we can create an object of the class {@link User} which takes in the
   * name of the user.
   *
   * @param name The name of the user whose object needs to be created.
   */
  public User(String name) {
    this.name = name;
    this.connectedTo = null;
    groups = new ArrayList<>();
    password = "";
  }

  public User() {

  }

  @Override
  public void setGroupsForUser(IMember group) {
    groups.add(group);
  }

  @Override
  public void connectTo(IMember otherMember) {

    MemberService allRegisteredMember = MemberServiceImpl.getInstance();

    if (!allRegisteredMember.findMemberByName(otherMember.getName()).isPresent()) {
      throw new NoSuchUserPresentException(otherMember.getName());
    }

    this.connectedTo = otherMember;
  }

  @Override
  public Optional<IMember> getConnectedMembers() {
    return Optional.of(connectedTo);
  }

  @Override
  public boolean isCorrectPassword(String password) {
    return password.equals(this.password);
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
    if (!(obj instanceof User)) {
      return false;
    }

    User user = (User) obj;
    return user.name.equals(this.name);
  }

  @Override
  public Set<String> getAllConnectedMembers() {
    Set<String> user = new HashSet<>();
    user.add(name);
    return user;
  }

  @Override
  public List<IMember> getGroupsForUser() {
    return groups;
  }
}