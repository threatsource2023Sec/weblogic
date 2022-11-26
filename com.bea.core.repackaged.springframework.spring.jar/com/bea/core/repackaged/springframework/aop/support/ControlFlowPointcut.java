package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Method;

public class ControlFlowPointcut implements Pointcut, ClassFilter, MethodMatcher, Serializable {
   private final Class clazz;
   @Nullable
   private final String methodName;
   private volatile int evaluations;

   public ControlFlowPointcut(Class clazz) {
      this(clazz, (String)null);
   }

   public ControlFlowPointcut(Class clazz, @Nullable String methodName) {
      Assert.notNull(clazz, (String)"Class must not be null");
      this.clazz = clazz;
      this.methodName = methodName;
   }

   public boolean matches(Class clazz) {
      return true;
   }

   public boolean matches(Method method, Class targetClass) {
      return true;
   }

   public boolean isRuntime() {
      return true;
   }

   public boolean matches(Method method, Class targetClass, Object... args) {
      ++this.evaluations;
      StackTraceElement[] var4 = (new Throwable()).getStackTrace();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         StackTraceElement element = var4[var6];
         if (element.getClassName().equals(this.clazz.getName()) && (this.methodName == null || element.getMethodName().equals(this.methodName))) {
            return true;
         }
      }

      return false;
   }

   public int getEvaluations() {
      return this.evaluations;
   }

   public ClassFilter getClassFilter() {
      return this;
   }

   public MethodMatcher getMethodMatcher() {
      return this;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ControlFlowPointcut)) {
         return false;
      } else {
         ControlFlowPointcut that = (ControlFlowPointcut)other;
         return this.clazz.equals(that.clazz) && ObjectUtils.nullSafeEquals(this.methodName, that.methodName);
      }
   }

   public int hashCode() {
      int code = this.clazz.hashCode();
      if (this.methodName != null) {
         code = 37 * code + this.methodName.hashCode();
      }

      return code;
   }

   public String toString() {
      return this.getClass().getName() + ": class = " + this.clazz.getName() + "; methodName = " + this.methodName;
   }
}
