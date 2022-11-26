package weblogic.security.service.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import weblogic.security.SecurityLogger;

public class Delegator {
   public static Object getInstance(Class intf, Object delegate) {
      Class[] intfs = new Class[]{intf};
      return getInstance(intfs, delegate);
   }

   public static Object getInstance(Class[] intfs, Object delegate) {
      if (delegate == null) {
         throw new NullPointerException(SecurityLogger.getNullParameterSupplied("getInstance"));
      } else {
         return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), intfs, new MyInvocationHandler(delegate));
      }
   }

   private static final class MyInvocationHandler implements InvocationHandler {
      private Object delegate;

      private MyInvocationHandler(Object delegate) {
         this.setDelegate(delegate);
      }

      private void setDelegate(Object delegate) {
         this.delegate = delegate;
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if (AccessController.class.isAssignableFrom(method.getDeclaringClass())) {
            throw new IllegalArgumentException("Unable to invoke method");
         } else {
            try {
               return method.invoke(this.delegate, args);
            } catch (InvocationTargetException var5) {
               throw var5.getCause();
            }
         }
      }

      // $FF: synthetic method
      MyInvocationHandler(Object x0, Object x1) {
         this(x0);
      }
   }
}
