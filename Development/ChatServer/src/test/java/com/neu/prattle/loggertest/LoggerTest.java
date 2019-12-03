package com.neu.prattle.loggertest;

import com.neu.prattle.utils.PrattleLogger;
import org.apache.log4j.Level;
import org.junit.Test;

public class LoggerTest {

  @Test
  public void testGroupLogger(){
    PrattleLogger.log("Testing logger.", Level.INFO);
  }


}
