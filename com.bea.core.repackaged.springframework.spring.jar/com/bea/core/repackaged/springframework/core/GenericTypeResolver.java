package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class GenericTypeResolver {
   private static final Map typeVariableCache = new ConcurrentReferenceHashMap();

   private GenericTypeResolver() {
   }

   public static Class resolveParameterType(MethodParameter methodParameter, Class implementationClass) {
      Assert.notNull(methodParameter, (String)"MethodParameter must not be null");
      Assert.notNull(implementationClass, (String)"Class must not be null");
      methodParameter.setContainingClass(implementationClass);
      ResolvableType.resolveMethodParameter(methodParameter);
      return methodParameter.getParameterType();
   }

   public static Class resolveReturnType(Method method, Class clazz) {
      Assert.notNull(method, (String)"Method must not be null");
      Assert.notNull(clazz, (String)"Class must not be null");
      return ResolvableType.forMethodReturnType(method, clazz).resolve(method.getReturnType());
   }

   @Nullable
   public static Class resolveReturnTypeArgument(Method method, Class genericIfc) {
      Assert.notNull(method, (String)"Method must not be null");
      ResolvableType resolvableType = ResolvableType.forMethodReturnType(method).as(genericIfc);
      return resolvableType.hasGenerics() && !(resolvableType.getType() instanceof WildcardType) ? getSingleGeneric(resolvableType) : null;
   }

   @Nullable
   public static Class resolveTypeArgument(Class clazz, Class genericIfc) {
      ResolvableType resolvableType = ResolvableType.forClass(clazz).as(genericIfc);
      return !resolvableType.hasGenerics() ? null : getSingleGeneric(resolvableType);
   }

   @Nullable
   private static Class getSingleGeneric(ResolvableType resolvableType) {
      Assert.isTrue(resolvableType.getGenerics().length == 1, () -> {
         return "Expected 1 type argument on generic interface [" + resolvableType + "] but found " + resolvableType.getGenerics().length;
      });
      return resolvableType.getGeneric().resolve();
   }

   @Nullable
   public static Class[] resolveTypeArguments(Class clazz, Class genericIfc) {
      ResolvableType type = ResolvableType.forClass(clazz).as(genericIfc);
      return type.hasGenerics() && !type.isEntirelyUnresolvable() ? type.resolveGenerics(Object.class) : null;
   }

   public static Type resolveType(Type genericType, @Nullable Class contextClass) {
      if (contextClass != null) {
         ResolvableType resolvedType;
         if (genericType instanceof TypeVariable) {
            resolvedType = resolveVariable((TypeVariable)genericType, ResolvableType.forClass(contextClass));
            if (resolvedType != ResolvableType.NONE) {
               Class resolved = resolvedType.resolve();
               if (resolved != null) {
                  return resolved;
               }
            }
         } else if (genericType instanceof ParameterizedType) {
            resolvedType = ResolvableType.forType(genericType);
            if (resolvedType.hasUnresolvableGenerics()) {
               ParameterizedType parameterizedType = (ParameterizedType)genericType;
               Class[] generics = new Class[parameterizedType.getActualTypeArguments().length];
               Type[] typeArguments = parameterizedType.getActualTypeArguments();

               for(int i = 0; i < typeArguments.length; ++i) {
                  Type typeArgument = typeArguments[i];
                  if (typeArgument instanceof TypeVariable) {
                     ResolvableType resolvedTypeArgument = resolveVariable((TypeVariable)typeArgument, ResolvableType.forClass(contextClass));
                     if (resolvedTypeArgument != ResolvableType.NONE) {
                        generics[i] = resolvedTypeArgument.resolve();
                     } else {
                        generics[i] = ResolvableType.forType(typeArgument).resolve();
                     }
                  } else {
                     generics[i] = ResolvableType.forType(typeArgument).resolve();
                  }
               }

               Class rawClass = resolvedType.getRawClass();
               if (rawClass != null) {
                  return ResolvableType.forClassWithGenerics(rawClass, generics).getType();
               }
            }
         }
      }

      return genericType;
   }

   private static ResolvableType resolveVariable(TypeVariable typeVariable, ResolvableType contextType) {
      ResolvableType resolvedType;
      if (contextType.hasGenerics()) {
         resolvedType = ResolvableType.forType(typeVariable, (ResolvableType)contextType);
         if (resolvedType.resolve() != null) {
            return resolvedType;
         }
      }

      ResolvableType superType = contextType.getSuperType();
      if (superType != ResolvableType.NONE) {
         resolvedType = resolveVariable(typeVariable, superType);
         if (resolvedType.resolve() != null) {
            return resolvedType;
         }
      }

      ResolvableType[] var4 = contextType.getInterfaces();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ResolvableType ifc = var4[var6];
         resolvedType = resolveVariable(typeVariable, ifc);
         if (resolvedType.resolve() != null) {
            return resolvedType;
         }
      }

      return ResolvableType.NONE;
   }

   public static Class resolveType(Type genericType, Map map) {
      return ResolvableType.forType(genericType, (ResolvableType.VariableResolver)(new TypeVariableMapVariableResolver(map))).toClass();
   }

   public static Map getTypeVariableMap(Class clazz) {
      Map typeVariableMap = (Map)typeVariableCache.get(clazz);
      if (typeVariableMap == null) {
         typeVariableMap = new HashMap();
         buildTypeVariableMap(ResolvableType.forClass(clazz), (Map)typeVariableMap);
         typeVariableCache.put(clazz, Collections.unmodifiableMap((Map)typeVariableMap));
      }

      return (Map)typeVariableMap;
   }

   private static void buildTypeVariableMap(ResolvableType type, Map typeVariableMap) {
      if (type != ResolvableType.NONE) {
         Class resolved = type.resolve();
         int i;
         if (resolved != null && type.getType() instanceof ParameterizedType) {
            TypeVariable[] variables = resolved.getTypeParameters();

            for(i = 0; i < variables.length; ++i) {
               ResolvableType generic;
               for(generic = type.getGeneric(i); generic.getType() instanceof TypeVariable; generic = generic.resolveType()) {
               }

               if (generic != ResolvableType.NONE) {
                  typeVariableMap.put(variables[i], generic.getType());
               }
            }
         }

         buildTypeVariableMap(type.getSuperType(), typeVariableMap);
         ResolvableType[] var7 = type.getInterfaces();
         i = var7.length;

         for(int var8 = 0; var8 < i; ++var8) {
            ResolvableType interfaceType = var7[var8];
            buildTypeVariableMap(interfaceType, typeVariableMap);
         }

         if (resolved != null && resolved.isMemberClass()) {
            buildTypeVariableMap(ResolvableType.forClass(resolved.getEnclosingClass()), typeVariableMap);
         }
      }

   }

   private static class TypeVariableMapVariableResolver implements ResolvableType.VariableResolver {
      private final Map typeVariableMap;

      public TypeVariableMapVariableResolver(Map typeVariableMap) {
         this.typeVariableMap = typeVariableMap;
      }

      @Nullable
      public ResolvableType resolveVariable(TypeVariable variable) {
         Type type = (Type)this.typeVariableMap.get(variable);
         return type != null ? ResolvableType.forType(type) : null;
      }

      public Object getSource() {
         return this.typeVariableMap;
      }
   }
}
