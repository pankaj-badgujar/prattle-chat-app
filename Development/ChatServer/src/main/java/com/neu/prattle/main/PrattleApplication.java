package com.neu.prattle.main;

import com.neu.prattle.controller.MemberController;

import com.neu.prattle.utils.PrattleLogger;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/***
 * Sets up the resource classes for handling REST requests.
 * Refer {@link Application}
 *
 * @author Devansh Gandhi
 * @version 2.0 dated 11/01/2019
 */
public class PrattleApplication extends Application {
  private Set<Class<?>> resourceClasses = new HashSet<>();

  @Override
  public Set<Class<?>> getClasses() {
    PrattleLogger.log("Application started.", Level.ERROR);
    resourceClasses.add(MemberController.class);
    return resourceClasses;
  }
}
