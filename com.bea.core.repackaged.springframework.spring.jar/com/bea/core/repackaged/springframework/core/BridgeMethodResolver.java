package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class BridgeMethodResolver {
   private BridgeMethodResolver() {
   }

   public static Method findBridgedMethod(Method bridgeMethod) {
      if (!bridgeMethod.isBridge()) {
         return bridgeMethod;
      } else {
         List candidateMethods = new ArrayList();
         Method[] methods = ReflectionUtils.getAllDeclaredMethods(bridgeMethod.getDeclaringClass());
         Method[] var3 = methods;
         int var4 = methods.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method candidateMethod = var3[var5];
            if (isBridgedCandidateFor(candidateMethod, bridgeMethod)) {
               candidateMethods.add(candidateMethod);
            }
         }

         if (candidateMethods.size() == 1) {
            return (Method)candidateMethods.get(0);
         } else {
            Method bridgedMethod = searchCandidates(candidateMethods, bridgeMethod);
            if (bridgedMethod != null) {
               return bridgedMethod;
            } else {
               return bridgeMethod;
            }
         }
      }
   }

   private static boolean isBridgedCandidateFor(Method candidateMethod, Method bridgeMethod) {
      return !candidateMethod.isBridge() && !candidateMethod.equals(bridgeMethod) && candidateMethod.getName().equals(bridgeMethod.getName()) && candidateMethod.getParameterCount() == bridgeMethod.getParameterCount();
   }

   @Nullable
   private static Method searchCandidates(List candidateMethods, Method bridgeMethod) {
      if (candidateMethods.isEmpty()) {
         return null;
      } else {
         Method previousMethod = null;
         boolean sameSig = true;

         Method candidateMethod;
         for(Iterator var4 = candidateMethods.iterator(); var4.hasNext(); previousMethod = candidateMethod) {
            candidateMethod = (Method)var4.next();
            if (isBridgeMethodFor(bridgeMethod, candidateMethod, bridgeMethod.getDeclaringClass())) {
               return candidateMethod;
            }

            if (previousMethod != null) {
               sameSig = sameSig && Arrays.equals(candidateMethod.getGenericParameterTypes(), previousMethod.getGenericParameterTypes());
            }
         }

         return sameSig ? (Method)candidateMethods.get(0) : null;
      }
   }

   static boolean isBridgeMethodFor(Method bridgeMethod, Method candidateMethod, Class declaringClass) {
      if (isResolvedTypeMatch(candidateMethod, bridgeMethod, declaringClass)) {
         return true;
      } else {
         Method method = findGenericDeclaration(bridgeMethod);
         return method != null && isResolvedTypeMatch(method, candidateMethod, declaringClass);
      }
   }

   private static boolean isResolvedTypeMatch(Method genericMethod, Method candidateMethod, Class declaringClass) {
      Type[] genericParameters = genericMethod.getGenericParameterTypes();
      Class[] candidateParameters = candidateMethod.getParameterTypes();
      if (genericParameters.length != candidateParameters.length) {
         return false;
      } else {
         for(int i = 0; i < candidateParameters.length; ++i) {
            ResolvableType genericParameter = ResolvableType.forMethodParameter(genericMethod, i, declaringClass);
            Class candidateParameter = candidateParameters[i];
            if (candidateParameter.isArray() && !candidateParameter.getComponentType().equals(genericParameter.getComponentType().toClass())) {
               return false;
            }

            if (!candidateParameter.equals(genericParameter.toClass())) {
               return false;
            }
         }

         return true;
      }
   }

   @Nullable
   private static Method findGenericDeclaration(Method bridgeMethod) {
      for(Class superclass = bridgeMethod.getDeclaringClass().getSuperclass(); superclass != null && Object.class != superclass; superclass = superclass.getSuperclass()) {
         Method method = searchForMatch(superclass, bridgeMethod);
         if (method != null && !method.isBridge()) {
            return method;
         }
      }

      Class[] interfaces = ClassUtils.getAllInterfacesForClass(bridgeMethod.getDeclaringClass());
      return searchInterfaces(interfaces, bridgeMethod);
   }

   @Nullable
   private static Method searchInterfaces(Class[] interfaces, Method bridgeMethod) {
      Class[] var2 = interfaces;
      int var3 = interfaces.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class ifc = var2[var4];
         Method method = searchForMatch(ifc, bridgeMethod);
         if (method != null && !method.isBridge()) {
            return method;
         }

         method = searchInterfaces(ifc.getInterfaces(), bridgeMethod);
         if (method != null) {
            return method;
         }
      }

      return null;
   }

   @Nullable
   private static Method searchForMatch(Class type, Method bridgeMethod) {
      try {
         return type.getDeclaredMethod(bridgeMethod.getName(), bridgeMethod.getParameterTypes());
      } catch (NoSuchMethodException var3) {
         return null;
      }
   }

   public static boolean isVisibilityBridgeMethodPair(Method bridgeMethod, Method bridgedMethod) {
      if (bridgeMethod == bridgedMethod) {
         return true;
      } else {
         return bridgeMethod.getReturnType().equals(bridgedMethod.getReturnType()) && Arrays.equals(bridgeMethod.getParameterTypes(), bridgedMethod.getParameterTypes());
      }
   }
}
