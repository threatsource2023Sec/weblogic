package org.jboss.weld.security;

import java.lang.reflect.Constructor;
import java.security.PrivilegedAction;

public class GetDeclaredConstructorsAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetDeclaredConstructorsAction(Class javaClass) {
      super(javaClass);
   }

   public Constructor[] run() {
      return this.javaClass.getDeclaredConstructors();
   }
}
