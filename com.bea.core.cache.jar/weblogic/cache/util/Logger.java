package weblogic.cache.util;

import java.util.Arrays;
import java.util.List;

public class Logger {
   private final String name;
   private final boolean debug;

   public static Logger getLogger(String name) {
      return new Logger(name);
   }

   private Logger(String name) {
      this.name = name;
      String debuggers = System.getProperty("debuggers");
      if (debuggers != null) {
         List debuggerList = Arrays.asList(debuggers.split(","));
         this.debug = debuggerList.contains(name);
      } else {
         this.debug = false;
      }

   }

   public void info(Object message) {
      if (this.debug) {
         System.out.println(this.name + ": " + message);
      }

   }

   public boolean isEnabled() {
      return this.debug;
   }
}
