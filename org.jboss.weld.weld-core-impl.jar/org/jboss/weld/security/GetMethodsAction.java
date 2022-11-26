package org.jboss.weld.security;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;

public class GetMethodsAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetMethodsAction(Class javaClass) {
      super(javaClass);
   }

   public Method[] run() {
      return this.javaClass.getMethods();
   }
}
