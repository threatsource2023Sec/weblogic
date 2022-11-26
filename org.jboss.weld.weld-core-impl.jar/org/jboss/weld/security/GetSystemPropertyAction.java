package org.jboss.weld.security;

import java.security.PrivilegedAction;

public class GetSystemPropertyAction implements PrivilegedAction {
   private final String propertyName;

   public GetSystemPropertyAction(String propertyName) {
      this.propertyName = propertyName;
   }

   public String run() {
      return System.getProperty(this.propertyName);
   }
}
