package org.hibernate.validator.internal.util;

import java.lang.invoke.MethodHandles;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class Version {
   public static String getVersionString() {
      return Version.class.getPackage().getImplementationVersion();
   }

   public static void touch() {
   }

   private Version() {
   }

   static {
      LoggerFactory.make(MethodHandles.lookup()).version(getVersionString());
   }
}
