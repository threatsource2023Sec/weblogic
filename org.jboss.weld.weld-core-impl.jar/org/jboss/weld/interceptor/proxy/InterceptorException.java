package org.jboss.weld.interceptor.proxy;

public class InterceptorException extends RuntimeException {
   public InterceptorException() {
   }

   public InterceptorException(String s) {
      super(s);
   }

   public InterceptorException(String s, Throwable throwable) {
      super(s, throwable);
   }

   public InterceptorException(Throwable throwable) {
      super(throwable);
   }
}
