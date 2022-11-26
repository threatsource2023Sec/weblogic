package com.bea.common.security.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;

public class ThreadClassLoaderContextInvocationHandler implements InvocationHandler {
   private ClassLoader cl;
   private Object obj;

   public ThreadClassLoaderContextInvocationHandler(ClassLoader cl, Object obj) {
      this.cl = cl;
      this.obj = obj;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (AccessController.class.isAssignableFrom(method.getDeclaringClass())) {
         throw new IllegalArgumentException("Unable to invoke method");
      } else {
         Thread t = Thread.currentThread();
         ClassLoader tcl = t.getContextClassLoader();

         Object var6;
         try {
            if (this.cl instanceof SAML2ClassLoader) {
               ((SAML2ClassLoader)this.cl).setThreadConextClassLoader(tcl);
            }

            t.setContextClassLoader(this.cl);
            var6 = method.invoke(this.obj, args);
         } catch (InvocationTargetException var10) {
            throw var10.getCause();
         } finally {
            t.setContextClassLoader(tcl);
         }

         return var6;
      }
   }
}
