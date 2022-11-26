package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.MethodExecutor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public final class DataBindingMethodResolver extends ReflectiveMethodResolver {
   private DataBindingMethodResolver() {
   }

   @Nullable
   public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List argumentTypes) throws AccessException {
      if (targetObject instanceof Class) {
         throw new IllegalArgumentException("DataBindingMethodResolver does not support Class targets");
      } else {
         return super.resolve(context, targetObject, name, argumentTypes);
      }
   }

   protected boolean isCandidateForInvocation(Method method, Class targetClass) {
      if (Modifier.isStatic(method.getModifiers())) {
         return false;
      } else {
         Class clazz = method.getDeclaringClass();
         return clazz != Object.class && clazz != Class.class && !ClassLoader.class.isAssignableFrom(targetClass);
      }
   }

   public static DataBindingMethodResolver forInstanceMethodInvocation() {
      return new DataBindingMethodResolver();
   }
}
