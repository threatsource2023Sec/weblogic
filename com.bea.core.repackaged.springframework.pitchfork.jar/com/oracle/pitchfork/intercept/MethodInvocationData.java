package com.oracle.pitchfork.intercept;

import java.lang.reflect.Method;

public class MethodInvocationData {
   private final Object target;
   private final Method method;

   public MethodInvocationData(Object target, Method method) {
      this.target = target;
      this.method = method;
   }

   public Object getInvocationTarget() {
      return this.target;
   }

   public Method getInvocationMethod() {
      return this.method;
   }

   public String toString() {
      return "MethodInvocationData(" + this.target + "," + this.method.getName() + "," + System.identityHashCode(this) + ")";
   }
}
