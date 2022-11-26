package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class StandardReflectionParameterNameDiscoverer implements ParameterNameDiscoverer {
   @Nullable
   public String[] getParameterNames(Method method) {
      return this.getParameterNames(method.getParameters());
   }

   @Nullable
   public String[] getParameterNames(Constructor ctor) {
      return this.getParameterNames(ctor.getParameters());
   }

   @Nullable
   private String[] getParameterNames(Parameter[] parameters) {
      String[] parameterNames = new String[parameters.length];

      for(int i = 0; i < parameters.length; ++i) {
         Parameter param = parameters[i];
         if (!param.isNamePresent()) {
            return null;
         }

         parameterNames[i] = param.getName();
      }

      return parameterNames;
   }
}
