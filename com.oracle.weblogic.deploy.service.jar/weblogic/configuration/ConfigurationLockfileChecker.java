package weblogic.configuration;

import java.io.File;
import java.util.Map;
import weblogic.callout.spi.WebLogicCallout;

public class ConfigurationLockfileChecker implements WebLogicCallout {
   File lockfile = null;

   public String callout(String hookPoint, String location, Map values) {
      if ("HOOK_POINT_EXPECTED_CHANGE".equals(hookPoint)) {
         return this.lockfile != null && this.lockfile.exists() ? "ignore" : "accept";
      } else {
         return "continue";
      }
   }

   public void init(String argument) {
      if (argument != null) {
         this.lockfile = new File(argument);
      }

   }
}
