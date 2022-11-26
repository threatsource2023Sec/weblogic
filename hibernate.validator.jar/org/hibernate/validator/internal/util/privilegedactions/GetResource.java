package org.hibernate.validator.internal.util.privilegedactions;

import java.net.URL;
import java.security.PrivilegedAction;

public final class GetResource implements PrivilegedAction {
   private final String resourceName;
   private final ClassLoader classLoader;

   public static GetResource action(ClassLoader classLoader, String resourceName) {
      return new GetResource(classLoader, resourceName);
   }

   private GetResource(ClassLoader classLoader, String resourceName) {
      this.classLoader = classLoader;
      this.resourceName = resourceName;
   }

   public URL run() {
      return this.classLoader.getResource(this.resourceName);
   }
}
