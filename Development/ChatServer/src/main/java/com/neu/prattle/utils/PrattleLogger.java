package com.neu.prattle.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PrattleLogger {

  private final static Logger logger = Logger.getLogger(PrattleLogger.class);

  public static void log(String message, Level level){
    logger.log(level, message);
  }

}
