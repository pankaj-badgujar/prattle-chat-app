package com.neu.prattle.model;

import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.service.UserService;
import com.neu.prattle.service.UserServiceImpl;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/***
 * A User object represents a basic account information for a user.
 *
 * @author Devansh Gandhi
 * @version 1.1 dated 2019-10-16
 */
public class User extends AbstractMember implements IUser{

  /**
   * A constructor using which we can create an object of the class {@link User} which takes in the
   * name of the user.
   *
   * @param name The name of the user whose object needs to be created.
   */
  public User(@JsonProperty("name") String name) {
    this.name = name;
    this.connectedTo = null;
    this.id = UUID.randomUUID().toString();
  }

  public User() {

  }

  @Override
  public List<String> getName() {
    return Collections.singletonList(this.name);
  }

  @Override
  public void connectTo(String otherUsers) {

    UserService allRegisteredUsers = UserServiceImpl.getInstance();

    if (!allRegisteredUsers.findUserByName(otherUsers).isPresent()) {
      throw new NoSuchUserPresentException(otherUsers);
    }

    this.connectedTo = otherUsers;
  }

  @Override
  public String getConnectedMembers() {
    return connectedTo;
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
