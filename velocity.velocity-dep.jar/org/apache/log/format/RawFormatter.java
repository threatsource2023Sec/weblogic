package org.apache.log.format;

import org.apache.log.LogEvent;

public class RawFormatter implements Formatter, org.apache.log.Formatter {
   public String format(LogEvent event) {
      String message = event.getMessage();
      return null == message ? "" : message;
   }
}
