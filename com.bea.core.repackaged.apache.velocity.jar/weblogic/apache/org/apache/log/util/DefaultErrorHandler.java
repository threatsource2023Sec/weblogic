package weblogic.apache.org.apache.log.util;

import weblogic.apache.org.apache.log.ErrorHandler;
import weblogic.apache.org.apache.log.LogEvent;

public class DefaultErrorHandler implements ErrorHandler {
   public void error(String message, Throwable throwable, LogEvent event) {
      System.err.println("Logging Error: " + message);
      if (null != throwable) {
         throwable.printStackTrace();
      }

   }
}
