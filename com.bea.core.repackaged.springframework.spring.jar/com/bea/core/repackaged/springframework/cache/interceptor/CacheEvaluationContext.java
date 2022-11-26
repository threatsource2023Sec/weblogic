package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.context.expression.MethodBasedEvaluationContext;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

class CacheEvaluationContext extends MethodBasedEvaluationContext {
   private final Set unavailableVariables = new HashSet(1);

   CacheEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
      super(rootObject, method, arguments, parameterNameDiscoverer);
   }

   public void addUnavailableVariable(String name) {
      this.unavailableVariables.add(name);
   }

   @Nullable
   public Object lookupVariable(String name) {
      if (this.unavailableVariables.contains(name)) {
         throw new VariableNotAvailableException(name);
      } else {
         return super.lookupVariable(name);
      }
   }
}
