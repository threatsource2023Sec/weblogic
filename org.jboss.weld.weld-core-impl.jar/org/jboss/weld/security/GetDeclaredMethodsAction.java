package org.jboss.weld.security;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;

public class GetDeclaredMethodsAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetDeclaredMethodsAction(Class javaClass) {
      super(javaClass);
   }

   public Method[] run() {
      return this.javaClass.getDeclaredMethods();
   }
}
