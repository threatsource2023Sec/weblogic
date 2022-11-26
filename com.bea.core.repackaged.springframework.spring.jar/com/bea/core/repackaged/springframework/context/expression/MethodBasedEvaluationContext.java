package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.expression.spel.support.StandardEvaluationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodBasedEvaluationContext extends StandardEvaluationContext {
   private final Method method;
   private final Object[] arguments;
   private final ParameterNameDiscoverer parameterNameDiscoverer;
   private boolean argumentsLoaded = false;

   public MethodBasedEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
      super(rootObject);
      this.method = method;
      this.arguments = arguments;
      this.parameterNameDiscoverer = parameterNameDiscoverer;
   }

   @Nullable
   public Object lookupVariable(String name) {
      Object variable = super.lookupVariable(name);
      if (variable != null) {
         return variable;
      } else {
         if (!this.argumentsLoaded) {
            this.lazyLoadArguments();
            this.argumentsLoaded = true;
            variable = super.lookupVariable(name);
         }

         return variable;
      }
   }

   protected void lazyLoadArguments() {
      if (!ObjectUtils.isEmpty(this.arguments)) {
         String[] paramNames = this.parameterNameDiscoverer.getParameterNames(this.method);
         int paramCount = paramNames != null ? paramNames.length : this.method.getParameterCount();
         int argsCount = this.arguments.length;

         for(int i = 0; i < paramCount; ++i) {
            Object value = null;
            if (argsCount > paramCount && i == paramCount - 1) {
               value = Arrays.copyOfRange(this.arguments, i, argsCount);
            } else if (argsCount > i) {
               value = this.arguments[i];
            }

            this.setVariable("a" + i, value);
            this.setVariable("p" + i, value);
            if (paramNames != null && paramNames[i] != null) {
               this.setVariable(paramNames[i], value);
            }
         }

      }
   }
}
