package com.oracle.weblogic.lifecycle;

public final class MTHelper {
   private static boolean isMultitenancyEnabled = false;
   private static final String propName = "com.oracle.jrf.mt.enabled";

   public static boolean getMultitenancyEnabled() {
      return isMultitenancyEnabled = Boolean.getBoolean("com.oracle.jrf.mt.enabled");
   }
}
