package com.neu.prattle.main;

import com.neu.prattle.controller.MemberController;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
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
  private final static Logger logger = Logger.getLogger(PrattleApplication.class);

  @Override
  public Set<Class<?>> getClasses() {
    logger.error("Application started.");
    resourceClasses.add(MemberController.class);
    return resourceClasses;
  }
}
