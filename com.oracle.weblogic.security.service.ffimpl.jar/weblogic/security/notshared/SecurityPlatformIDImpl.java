package weblogic.security.notshared;

import weblogic.security.shared.SecurityPlatformID;

public class SecurityPlatformIDImpl implements SecurityPlatformID {
   private static final int id = 1;

   public int value() {
      return 1;
   }

   public String toString() {
      return "WLS";
   }
}
