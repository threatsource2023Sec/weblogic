package com.bea.common.security.internal.utils;

import com.bea.common.security.internal.service.ServiceLogger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;

public class Delegator {
   private static boolean DEBUG = false;

   public static Object getProxy(String intfName, Object delegate) {
      Class intf = null;

      try {
         intf = Class.forName(intfName);
      } catch (ClassNotFoundException var4) {
         throw new IllegalArgumentException(ServiceLogger.getFailedToInstantiate(intfName));
      }

      return getProxy(new Class[]{intf}, delegate);
   }

   public static Object getProxy(Class intf, Object delegate) {
      return getProxy(new Class[]{intf}, delegate);
   }

   public static Object getProxy(Class[] intfs, Object delegate) {
      if (delegate == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullArgumentSpecified("Delegator"));
      } else {
         Object theProxy = Proxy.newProxyInstance(delegate.getClass().getClassLoader(), intfs, new ProxyInvocationHandler(delegate));
         return theProxy;
      }
   }

   private static final class ProxyInvocationHandler implements InvocationHandler {
      private Object delegate;

      public ProxyInvocationHandler(Object delegate) {
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
   }
}
