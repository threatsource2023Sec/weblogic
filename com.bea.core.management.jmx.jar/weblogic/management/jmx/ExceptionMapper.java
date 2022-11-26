package weblogic.management.jmx;

import java.lang.reflect.Method;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;

public class ExceptionMapper {
   public static Throwable matchJMXException(Method method, Throwable throwable) {
      if (throwable instanceof RuntimeMBeanException) {
         return ((RuntimeMBeanException)throwable).getTargetException();
      } else if (throwable instanceof RuntimeErrorException) {
         return ((RuntimeErrorException)throwable).getTargetError();
      } else {
         Exception theCause;
         if (throwable instanceof MBeanException) {
            theCause = ((MBeanException)throwable).getTargetException();
            if (throwableMatches(method, theCause)) {
               return theCause;
            } else if (throwableMatches(method, throwable)) {
               return throwable;
            } else {
               return (Throwable)(theCause instanceof RuntimeException ? theCause : new RuntimeException("Unexpected Exception mapped to RuntimeException", throwable));
            }
         } else if (throwable instanceof OperationsException) {
            if (throwable.getCause() instanceof OperationsException) {
               throwable = throwable.getCause();
            }

            if (throwableMatches(method, throwable)) {
               return throwable;
            } else if (throwableMatches(method, throwable.getCause())) {
               return throwable.getCause();
            } else {
               if (throwable instanceof InvalidAttributeValueException) {
                  InvalidAttributeValueException iave = (InvalidAttributeValueException)throwable;
                  Throwable cause = iave.getCause();
                  if (cause != null) {
                     if (cause instanceof RuntimeException) {
                        return (RuntimeException)cause;
                     }

                     if (cause instanceof Error) {
                        return (Error)cause;
                     }
                  }
               }

               if (throwable instanceof AttributeNotFoundException) {
                  return new RuntimeException("The requested attribute is not exposed through JMX: " + method.getName() + ": " + throwable.getMessage(), throwable);
               } else if (throwable instanceof InstanceNotFoundException) {
                  InstanceNotFoundException infe = (InstanceNotFoundException)throwable;
                  return new RuntimeException("The JMX ObjectName that is being proxied has been unregistered: " + infe.getMessage(), throwable);
               } else {
                  return new RuntimeException("Unexplained problems with the JMX Object invoking " + method.getName(), throwable);
               }
            }
         } else if (throwable instanceof ReflectionException) {
            theCause = ((ReflectionException)throwable).getTargetException();
            if (theCause != null) {
               RuntimeException runtimeException = new RuntimeException("The requested operation is not exposed through JMX in this context: " + method.getName() + ": " + theCause.getMessage(), theCause);
               runtimeException.setStackTrace(theCause.getStackTrace());
               return runtimeException;
            } else {
               return new RuntimeException("The requested operation is not exposed through JMX in this context: " + method.getName(), throwable);
            }
         } else {
            return throwable;
         }
      }
   }

   private static boolean throwableMatches(Method method, Throwable throwable) {
      Class[] exceptionTypes = method.getExceptionTypes();

      for(int i = 0; i < exceptionTypes.length; ++i) {
         Class exceptionType = exceptionTypes[i];
         if (throwable != null && exceptionType.isAssignableFrom(throwable.getClass())) {
            return true;
         }
      }

      return false;
   }
}
