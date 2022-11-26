package org.jboss.weld.security;

import java.lang.reflect.Method;
import java.security.PrivilegedExceptionAction;

public class GetMethodAction extends AbstractReflectionAction implements PrivilegedExceptionAction {
   private final String methodName;
   private final Class[] parameterTypes;

   public GetMethodAction(Class javaClass, String methodName, Class... parameterTypes) {
      super(javaClass);
      this.methodName = methodName;
      this.parameterTypes = parameterTypes;
   }

   public Method run() throws NoSuchMethodException {
      return this.javaClass.getMethod(this.methodName, this.parameterTypes);
   }
}
