package com.neu.prattle.modeltest;

import static org.junit.Assert.assertEquals;

import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class FindAllMembersTest {

  @Test
  public void testFindAllMembers(){
    MemberService memberService = MemberServiceImpl.getInstance();
    User bhargavi = new User("bhargavi");
    User mike = new User("mike");
    User pranay = new User("pranay");

    User harshil5 = new User("Harshil5");
    User pankaj5 = new User("Pankaj5");
    User devansh5 = new User("Devansh5");
    User mike5 = new User("Mike5");
    User harshil = new User("Harshil");
    User devansh = new User("devansh");
    User pankaj = new User("pankaj");
    User pankaj2 = new User("Pankaj2");

    List<String> users = new ArrayList<>();
    users.add("bhargavi");
    users.add("mike");

    memberService.addUser(bhargavi);
    memberService.addUser(mike);
    memberService.addUser(pranay);
    Group group = new Group("fse", users);
    memberService.addGroup(group);

    Set<IMember> allMembers = new HashSet<>();
    allMembers.add(harshil5);
    allMembers.add(pankaj5);
    allMembers.add(devansh5);
    allMembers.add(mike5);
    allMembers.add(mike);
    allMembers.add(pranay);
    allMembers.add(group);



    Set<IMember> im = memberService.findAllMembers("bhargavi");
    for(IMember im2: im)
      System.out.println(im2.getName());

    assertEquals(allMembers, memberService.findAllMembers("bhargavi"));
  }

}
