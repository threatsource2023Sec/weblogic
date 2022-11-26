package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;

class ExceptionTypeMismatchException extends Exception {
   private final Method method;
   private final Class exception;

   ExceptionTypeMismatchException(Method m, Class e) {
      this.method = m;
      this.exception = e;
   }

   final Method getMethod() {
      return this.method;
   }

   final Class getException() {
      return this.exception;
   }
}
