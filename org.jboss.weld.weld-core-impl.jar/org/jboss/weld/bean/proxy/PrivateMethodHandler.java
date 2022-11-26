package org.jboss.weld.bean.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PrivateMethodHandler implements MethodHandler {
   static final PrivateMethodHandler INSTANCE = new PrivateMethodHandler();

   public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      Object result = null;

      try {
         SecurityActions.ensureAccessible(thisMethod);
         result = thisMethod.invoke(self, args);
         return result;
      } catch (InvocationTargetException var7) {
         throw var7.getCause();
      }
   }
}
