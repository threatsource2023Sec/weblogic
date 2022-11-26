package org.jboss.weld.injection;

import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import javax.enterprise.inject.CreationException;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.security.NewInstanceAction;

class Exceptions {
   private Exceptions() {
   }

   private static void rethrowException(Throwable t, Class exceptionToThrow) {
      if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else {
         RuntimeException e;
         try {
            e = (RuntimeException)AccessController.doPrivileged(NewInstanceAction.of(exceptionToThrow));
         } catch (PrivilegedActionException var4) {
            throw new WeldException(var4.getCause());
         }

         e.initCause(t);
         throw e;
      }
   }

   private static void rethrowException(Throwable t) {
      rethrowException(t, CreationException.class);
   }

   public static void rethrowException(IllegalArgumentException e) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e));
   }

   public static void rethrowException(IllegalArgumentException e, Class exceptionToThrow) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e), exceptionToThrow);
   }

   public static void rethrowException(InstantiationException e, Class exceptionToThrow) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e), exceptionToThrow);
   }

   public static void rethrowException(InstantiationException e) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e));
   }

   public static void rethrowException(IllegalAccessException e) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e));
   }

   public static void rethrowException(IllegalAccessException e, Class exceptionToThrow) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e), exceptionToThrow);
   }

   public static void rethrowException(InvocationTargetException e, Class exceptionToThrow) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e), exceptionToThrow);
   }

   public static void rethrowException(SecurityException e, Class exceptionToThrow) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e), exceptionToThrow);
   }

   public static void rethrowException(NoSuchMethodException e, Class exceptionToThrow) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e), exceptionToThrow);
   }

   public static void rethrowException(InvocationTargetException e) {
      rethrowException((Throwable)(e.getCause() != null ? e.getCause() : e));
   }
}
