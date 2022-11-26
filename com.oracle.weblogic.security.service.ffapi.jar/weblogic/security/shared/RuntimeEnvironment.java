package weblogic.security.shared;

import weblogic.utils.LocatorUtilities;

public class RuntimeEnvironment {
   public static RuntimeUtilities getRuntimeUtilities() {
      return RuntimeEnvironment.RuntimeUtilityHolder.utilities;
   }

   private static class RuntimeUtilityHolder {
      public static final RuntimeUtilities utilities = (RuntimeUtilities)LocatorUtilities.getService(RuntimeUtilities.class);

      static {
         if (utilities == null) {
            throw new IllegalStateException("Could not find an implementation of RuntimeUtilities");
         }
      }
   }
}
