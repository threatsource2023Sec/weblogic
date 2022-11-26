package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KParameter.Kind;
import kotlin.reflect.jvm.ReflectJvmMapping;

public class KotlinReflectionParameterNameDiscoverer implements ParameterNameDiscoverer {
   @Nullable
   public String[] getParameterNames(Method method) {
      if (!KotlinDetector.isKotlinType(method.getDeclaringClass())) {
         return null;
      } else {
         try {
            KFunction function = ReflectJvmMapping.getKotlinFunction(method);
            return function != null ? this.getParameterNames(function.getParameters()) : null;
         } catch (UnsupportedOperationException var3) {
            return null;
         }
      }
   }

   @Nullable
   public String[] getParameterNames(Constructor ctor) {
      if (!ctor.getDeclaringClass().isEnum() && KotlinDetector.isKotlinType(ctor.getDeclaringClass())) {
         try {
            KFunction function = ReflectJvmMapping.getKotlinFunction(ctor);
            return function != null ? this.getParameterNames(function.getParameters()) : null;
         } catch (UnsupportedOperationException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   @Nullable
   private String[] getParameterNames(List parameters) {
      List filteredParameters = (List)parameters.stream().filter((p) -> {
         return Kind.VALUE.equals(p.getKind()) || Kind.EXTENSION_RECEIVER.equals(p.getKind());
      }).collect(Collectors.toList());
      String[] parameterNames = new String[filteredParameters.size()];

      for(int i = 0; i < filteredParameters.size(); ++i) {
         KParameter parameter = (KParameter)filteredParameters.get(i);
         String name = Kind.EXTENSION_RECEIVER.equals(parameter.getKind()) ? "$receiver" : parameter.getName();
         if (name == null) {
            return null;
         }

         parameterNames[i] = name;
      }

      return parameterNames;
   }
}
