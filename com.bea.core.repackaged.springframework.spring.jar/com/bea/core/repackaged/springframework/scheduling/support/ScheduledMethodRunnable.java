package com.bea.core.repackaged.springframework.scheduling.support;

import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class ScheduledMethodRunnable implements Runnable {
   private final Object target;
   private final Method method;

   public ScheduledMethodRunnable(Object target, Method method) {
      this.target = target;
      this.method = method;
   }

   public ScheduledMethodRunnable(Object target, String methodName) throws NoSuchMethodException {
      this.target = target;
      this.method = target.getClass().getMethod(methodName);
   }

   public Object getTarget() {
      return this.target;
   }

   public Method getMethod() {
      return this.method;
   }

   public void run() {
      try {
         ReflectionUtils.makeAccessible(this.method);
         this.method.invoke(this.target);
      } catch (InvocationTargetException var2) {
         ReflectionUtils.rethrowRuntimeException(var2.getTargetException());
      } catch (IllegalAccessException var3) {
         throw new UndeclaredThrowableException(var3);
      }

   }

   public String toString() {
      return this.method.getDeclaringClass().getName() + "." + this.method.getName();
   }
}
