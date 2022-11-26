package org.jboss.weld.security;

import java.lang.reflect.Constructor;
import java.security.PrivilegedAction;

public class GetConstructorsAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetConstructorsAction(Class javaClass) {
      super(javaClass);
   }

   public Constructor[] run() {
      return this.javaClass.getConstructors();
   }
}
