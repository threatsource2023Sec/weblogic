package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.factory.ObjectFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

abstract class AutowireUtils {
   private static final Comparator EXECUTABLE_COMPARATOR = (e1, e2) -> {
      boolean p1 = Modifier.isPublic(e1.getModifiers());
      boolean p2 = Modifier.isPublic(e2.getModifiers());
      if (p1 != p2) {
         return p1 ? -1 : 1;
      } else {
         int c1pl = e1.getParameterCount();
         int c2pl = e2.getParameterCount();
         return Integer.compare(c2pl, c1pl);
      }
   };

   public static void sortConstructors(Constructor[] constructors) {
      Arrays.sort(constructors, EXECUTABLE_COMPARATOR);
   }

   public static void sortFactoryMethods(Method[] factoryMethods) {
      Arrays.sort(factoryMethods, EXECUTABLE_COMPARATOR);
   }

   public static boolean isExcludedFromDependencyCheck(PropertyDescriptor pd) {
      Method wm = pd.getWriteMethod();
      if (wm == null) {
         return false;
      } else if (!wm.getDeclaringClass().getName().contains("$$")) {
         return false;
      } else {
         Class superclass = wm.getDeclaringClass().getSuperclass();
         return !ClassUtils.hasMethod(superclass, wm.getName(), wm.getParameterTypes());
      }
   }

   public static boolean isSetterDefinedInInterface(PropertyDescriptor pd, Set interfaces) {
      Method setter = pd.getWriteMethod();
      if (setter != null) {
         Class targetClass = setter.getDeclaringClass();
         Iterator var4 = interfaces.iterator();

         while(var4.hasNext()) {
            Class ifc = (Class)var4.next();
            if (ifc.isAssignableFrom(targetClass) && ClassUtils.hasMethod(ifc, setter.getName(), setter.getParameterTypes())) {
               return true;
            }
         }
      }

      return false;
   }

   public static Object resolveAutowiringValue(Object autowiringValue, Class requiredType) {
      if (autowiringValue instanceof ObjectFactory && !requiredType.isInstance(autowiringValue)) {
         ObjectFactory factory = (ObjectFactory)autowiringValue;
         if (!(autowiringValue instanceof Serializable) || !requiredType.isInterface()) {
            return factory.getObject();
         }

         autowiringValue = Proxy.newProxyInstance(requiredType.getClassLoader(), new Class[]{requiredType}, new ObjectFactoryDelegatingInvocationHandler(factory));
      }

      return autowiringValue;
   }

   public static Class resolveReturnTypeForFactoryMethod(Method method, Object[] args, @Nullable ClassLoader classLoader) {
      Assert.notNull(method, (String)"Method must not be null");
      Assert.notNull(args, (String)"Argument array must not be null");
      TypeVariable[] declaredTypeVariables = method.getTypeParameters();
      Type genericReturnType = method.getGenericReturnType();
      Type[] methodParameterTypes = method.getGenericParameterTypes();
      Assert.isTrue(args.length == methodParameterTypes.length, "Argument array does not match parameter count");
      boolean locallyDeclaredTypeVariableMatchesReturnType = false;
      TypeVariable[] var7 = declaredTypeVariables;
      int var8 = declaredTypeVariables.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         TypeVariable currentTypeVariable = var7[var9];
         if (currentTypeVariable.equals(genericReturnType)) {
            locallyDeclaredTypeVariableMatchesReturnType = true;
            break;
         }
      }

      if (locallyDeclaredTypeVariableMatchesReturnType) {
         for(int i = 0; i < methodParameterTypes.length; ++i) {
            Type methodParameterType = methodParameterTypes[i];
            Object arg = args[i];
            if (methodParameterType.equals(genericReturnType)) {
               if (arg instanceof TypedStringValue) {
                  TypedStringValue typedValue = (TypedStringValue)arg;
                  if (typedValue.hasTargetType()) {
                     return typedValue.getTargetType();
                  }

                  try {
                     Class resolvedType = typedValue.resolveTargetType(classLoader);
                     if (resolvedType != null) {
                        return resolvedType;
                     }
                  } catch (ClassNotFoundException var19) {
                     throw new IllegalStateException("Failed to resolve value type [" + typedValue.getTargetTypeName() + "] for factory method argument", var19);
                  }
               } else if (arg != null && !(arg instanceof BeanMetadataElement)) {
                  return arg.getClass();
               }

               return method.getReturnType();
            }

            if (methodParameterType instanceof ParameterizedType) {
               ParameterizedType parameterizedType = (ParameterizedType)methodParameterType;
               Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
               Type[] var12 = actualTypeArguments;
               int var13 = actualTypeArguments.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Type typeArg = var12[var14];
                  if (typeArg.equals(genericReturnType)) {
                     if (arg instanceof Class) {
                        return (Class)arg;
                     }

                     String className = null;
                     if (arg instanceof String) {
                        className = (String)arg;
                     } else if (arg instanceof TypedStringValue) {
                        TypedStringValue typedValue = (TypedStringValue)arg;
                        String targetTypeName = typedValue.getTargetTypeName();
                        if (targetTypeName == null || Class.class.getName().equals(targetTypeName)) {
                           className = typedValue.getValue();
                        }
                     }

                     if (className != null) {
                        try {
                           return ClassUtils.forName(className, classLoader);
                        } catch (ClassNotFoundException var20) {
                           throw new IllegalStateException("Could not resolve class name [" + arg + "] for factory method argument", var20);
                        }
                     }

                     return method.getReturnType();
                  }
               }
            }
         }
      }

      return method.getReturnType();
   }

   private static class ObjectFactoryDelegatingInvocationHandler implements InvocationHandler, Serializable {
      private final ObjectFactory objectFactory;

      public ObjectFactoryDelegatingInvocationHandler(ObjectFactory objectFactory) {
         this.objectFactory = objectFactory;
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         String methodName = method.getName();
         if (methodName.equals("equals")) {
            return proxy == args[0];
         } else if (methodName.equals("hashCode")) {
            return System.identityHashCode(proxy);
         } else if (methodName.equals("toString")) {
            return this.objectFactory.toString();
         } else {
            try {
               return method.invoke(this.objectFactory.getObject(), args);
            } catch (InvocationTargetException var6) {
               throw var6.getTargetException();
            }
         }
      }
   }
}
