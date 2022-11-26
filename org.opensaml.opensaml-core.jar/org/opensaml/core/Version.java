package org.opensaml.core;

import javax.annotation.Nullable;

public final class Version {
   @Nullable
   private static final String VERSION = Version.class.getPackage().getImplementationVersion();

   private Version() {
   }

   public static void main(String[] args) {
      System.out.println(VERSION);
   }

   @Nullable
   public static String getVersion() {
      return VERSION;
   }
}
