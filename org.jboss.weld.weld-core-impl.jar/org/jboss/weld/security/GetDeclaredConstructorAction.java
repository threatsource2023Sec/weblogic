package org.jboss.weld.security;

import java.lang.reflect.Constructor;
import java.security.PrivilegedExceptionAction;

public class GetDeclaredConstructorAction extends AbstractGenericReflectionAction implements PrivilegedExceptionAction {
   private final Class[] parameterTypes;

   public static GetDeclaredConstructorAction of(Class javaClass, Class... parameterTypes) {
      return new GetDeclaredConstructorAction(javaClass, parameterTypes);
   }

   private GetDeclaredConstructorAction(Class javaClass, Class... parameterTypes) {
      super(javaClass);
      this.parameterTypes = parameterTypes;
   }

   public Constructor run() throws NoSuchMethodException {
      return this.javaClass.getDeclaredConstructor(this.parameterTypes);
   }
}
