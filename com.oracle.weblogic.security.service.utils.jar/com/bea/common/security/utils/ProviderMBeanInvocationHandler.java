package com.bea.common.security.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;

public class ProviderMBeanInvocationHandler implements InvocationHandler {
   private Object bean;
   private String className;
   private ClassLoader classloader;

   public ProviderMBeanInvocationHandler(Object bean, String className, ClassLoader classloader) {
      this.bean = bean;
      this.className = className;
      this.classloader = classloader;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (AccessController.class.isAssignableFrom(method.getDeclaringClass())) {
         throw new IllegalArgumentException("Unable to invoke method");
      } else if (method.getName().equals("getProviderClassName")) {
         return this.className;
      } else {
         try {
            return method.invoke(this.bean, args);
         } catch (InvocationTargetException var5) {
            throw var5.getCause();
         }
      }
   }

   public ClassLoader getClassLoader() {
      return this.classloader;
   }

   public Object getBean() {
      return this.bean;
   }
}
