package org.jboss.weld.security;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import org.jboss.weld.logging.ReflectionLogger;

public abstract class GetDeclaredMethodAction extends AbstractReflectionAction {
   protected final String methodName;
   protected final Class[] parameterTypes;

   public static PrivilegedExceptionAction of(Class javaClass, String methodName, Class... parameterTypes) {
      return new ExceptionAction(javaClass, methodName, parameterTypes);
   }

   public static PrivilegedAction wrapException(Class javaClass, String methodName, Class... parameterTypes) {
      return new WrappingAction(javaClass, methodName, parameterTypes);
   }

   public GetDeclaredMethodAction(Class javaClass, String methodName, Class... parameterTypes) {
      super(javaClass);
      this.methodName = methodName;
      this.parameterTypes = parameterTypes;
   }

   public Method run() throws NoSuchMethodException {
      return this.javaClass.getDeclaredMethod(this.methodName, this.parameterTypes);
   }

   private static class WrappingAction extends GetDeclaredMethodAction implements PrivilegedAction {
      public WrappingAction(Class javaClass, String methodName, Class[] parameterTypes) {
         super(javaClass, methodName, parameterTypes);
      }

      public Method run() {
         try {
            return super.run();
         } catch (NoSuchMethodException var2) {
            throw ReflectionLogger.LOG.noSuchMethodWrapper(var2, var2.getMessage());
         }
      }
   }

   private static class ExceptionAction extends GetDeclaredMethodAction implements PrivilegedExceptionAction {
      public ExceptionAction(Class javaClass, String methodName, Class[] parameterTypes) {
         super(javaClass, methodName, parameterTypes);
      }
   }
}
