package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class MethodIntrospector {
   private MethodIntrospector() {
   }

   public static Map selectMethods(Class targetType, MetadataLookup metadataLookup) {
      Map methodMap = new LinkedHashMap();
      Set handlerTypes = new LinkedHashSet();
      Class specificHandlerType = null;
      if (!Proxy.isProxyClass(targetType)) {
         specificHandlerType = ClassUtils.getUserClass(targetType);
         handlerTypes.add(specificHandlerType);
      }

      handlerTypes.addAll(ClassUtils.getAllInterfacesForClassAsSet(targetType));
      Iterator var5 = handlerTypes.iterator();

      while(var5.hasNext()) {
         Class currentHandlerType = (Class)var5.next();
         Class targetClass = specificHandlerType != null ? specificHandlerType : currentHandlerType;
         ReflectionUtils.doWithMethods(currentHandlerType, (method) -> {
            Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
            Object result = metadataLookup.inspect(specificMethod);
            if (result != null) {
               Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
               if (bridgedMethod == specificMethod || metadataLookup.inspect(bridgedMethod) == null) {
                  methodMap.put(specificMethod, result);
               }
            }

         }, ReflectionUtils.USER_DECLARED_METHODS);
      }

      return methodMap;
   }

   public static Set selectMethods(Class targetType, ReflectionUtils.MethodFilter methodFilter) {
      return selectMethods(targetType, (method) -> {
         return methodFilter.matches(method) ? Boolean.TRUE : null;
      }).keySet();
   }

   public static Method selectInvocableMethod(Method method, Class targetType) {
      if (method.getDeclaringClass().isAssignableFrom(targetType)) {
         return method;
      } else {
         try {
            String methodName = method.getName();
            Class[] parameterTypes = method.getParameterTypes();
            Class[] var4 = targetType.getInterfaces();
            int var5 = var4.length;
            int var6 = 0;

            while(var6 < var5) {
               Class ifc = var4[var6];

               try {
                  return ifc.getMethod(methodName, parameterTypes);
               } catch (NoSuchMethodException var9) {
                  ++var6;
               }
            }

            return targetType.getMethod(methodName, parameterTypes);
         } catch (NoSuchMethodException var10) {
            throw new IllegalStateException(String.format("Need to invoke method '%s' declared on target class '%s', but not found in any interface(s) of the exposed proxy type. Either pull the method up to an interface or switch to CGLIB proxies by enforcing proxy-target-class mode in your configuration.", method.getName(), method.getDeclaringClass().getSimpleName()));
         }
      }
   }

   @FunctionalInterface
   public interface MetadataLookup {
      @Nullable
      Object inspect(Method var1);
   }
}
