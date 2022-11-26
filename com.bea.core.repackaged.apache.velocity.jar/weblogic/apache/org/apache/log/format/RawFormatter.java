package weblogic.apache.org.apache.log.format;

import weblogic.apache.org.apache.log.LogEvent;

public class RawFormatter implements Formatter, weblogic.apache.org.apache.log.Formatter {
   public String format(LogEvent event) {
      String message = event.getMessage();
      return null == message ? "" : message;
   }
}
