package org.hibernate.validator.internal.util.privilegedactions;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Enumeration;

public final class GetResources implements PrivilegedAction {
   private final String resourceName;
   private final ClassLoader classLoader;

   public static GetResources action(ClassLoader classLoader, String resourceName) {
      return new GetResources(classLoader, resourceName);
   }

   private GetResources(ClassLoader classLoader, String resourceName) {
      this.classLoader = classLoader;
      this.resourceName = resourceName;
   }

   public Enumeration run() {
      try {
         return this.classLoader.getResources(this.resourceName);
      } catch (IOException var2) {
         return Collections.enumeration(Collections.emptyList());
      }
   }
}
