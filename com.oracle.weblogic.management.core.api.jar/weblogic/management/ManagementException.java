package weblogic.management;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;
import javax.management.RuntimeOperationsException;
import weblogic.utils.NestedException;
import weblogic.utils.NestedThrowable;

public class ManagementException extends NestedException {
   private static final long serialVersionUID = -5462989868981294354L;
   private boolean unWrapExceptions;

   public ManagementException(String message, Throwable t, boolean unWrapExceptions) {
      super(message, unWrapExceptions ? unWrapExceptions(t) : t);
      this.unWrapExceptions = unWrapExceptions;
   }

   public ManagementException(String message, Throwable t) {
      this(message, t, true);
   }

   public ManagementException(Throwable t) {
      super(unWrapExceptions(t));
      this.unWrapExceptions = true;
   }

   public ManagementException(String message) {
      super(message);
   }

   public static Throwable unWrapExceptions(Throwable exception) {
      Throwable realException = exception;
      if (exception instanceof RuntimeErrorException) {
         realException = ((RuntimeErrorException)exception).getTargetError();
      } else if (exception instanceof NestedThrowable) {
         realException = ((NestedThrowable)exception).getNested();
      } else if (exception instanceof RuntimeMBeanException) {
         realException = ((RuntimeMBeanException)exception).getTargetException();
      } else if (exception instanceof RuntimeOperationsException) {
         realException = ((RuntimeOperationsException)exception).getTargetException();
      } else if (exception instanceof ReflectionException) {
         realException = ((ReflectionException)exception).getTargetException();
      } else if (exception instanceof InvocationTargetException) {
         realException = ((InvocationTargetException)exception).getTargetException();
      } else if (exception instanceof UndeclaredThrowableException) {
         realException = ((UndeclaredThrowableException)exception).getUndeclaredThrowable();
      } else if (exception instanceof MBeanException) {
         realException = ((MBeanException)exception).getTargetException();
      } else if (exception != null) {
         realException = exception.getCause();
      }

      realException = realException == null ? exception : realException;
      return (Throwable)(realException == exception ? realException : unWrapExceptions((Throwable)realException));
   }
}
