package org.apache.log.util;

import org.apache.log.ErrorHandler;
import org.apache.log.LogEvent;

public class DefaultErrorHandler implements ErrorHandler {
   public void error(String message, Throwable throwable, LogEvent event) {
      System.err.println("Logging Error: " + message);
      if (null != throwable) {
         throwable.printStackTrace();
      }

   }
}
