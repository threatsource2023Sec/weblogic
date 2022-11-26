package weblogic.apache.org.apache.velocity.runtime.log;

import java.util.Date;
import weblogic.apache.org.apache.log.format.PatternFormatter;

public class VelocityFormatter extends PatternFormatter {
   public VelocityFormatter(String format) {
      super(format);
   }

   protected String getTime(long time, String format) {
      return (new Date()).toString();
   }
}
