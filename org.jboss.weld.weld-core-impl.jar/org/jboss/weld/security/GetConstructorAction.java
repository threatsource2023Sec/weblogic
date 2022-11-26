package org.jboss.weld.security;

import java.lang.reflect.Constructor;
import java.security.PrivilegedExceptionAction;

public class GetConstructorAction extends AbstractGenericReflectionAction implements PrivilegedExceptionAction {
   private final Class[] parameterTypes;

   public static GetConstructorAction of(Class javaClass, Class... parameterTypes) {
      return new GetConstructorAction(javaClass, parameterTypes);
   }

   private GetConstructorAction(Class javaClass, Class... parameterTypes) {
      super(javaClass);
      this.parameterTypes = parameterTypes;
   }

   public Constructor run() throws NoSuchMethodException {
      return this.javaClass.getConstructor(this.parameterTypes);
   }
}
