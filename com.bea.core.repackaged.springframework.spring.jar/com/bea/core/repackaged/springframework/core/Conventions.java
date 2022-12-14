package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;

public final class Conventions {
   private static final String PLURAL_SUFFIX = "List";

   private Conventions() {
   }

   public static String getVariableName(Object value) {
      Assert.notNull(value, "Value must not be null");
      boolean pluralize = false;
      Class valueClass;
      if (value.getClass().isArray()) {
         valueClass = value.getClass().getComponentType();
         pluralize = true;
      } else if (value instanceof Collection) {
         Collection collection = (Collection)value;
         if (collection.isEmpty()) {
            throw new IllegalArgumentException("Cannot generate variable name for an empty Collection");
         }

         Object valueToCheck = peekAhead(collection);
         valueClass = getClassForValue(valueToCheck);
         pluralize = true;
      } else {
         valueClass = getClassForValue(value);
      }

      String name = ClassUtils.getShortNameAsProperty(valueClass);
      return pluralize ? pluralize(name) : name;
   }

   public static String getVariableNameForParameter(MethodParameter parameter) {
      Assert.notNull(parameter, (String)"MethodParameter must not be null");
      boolean pluralize = false;
      String reactiveSuffix = "";
      Class valueClass;
      if (parameter.getParameterType().isArray()) {
         valueClass = parameter.getParameterType().getComponentType();
         pluralize = true;
      } else if (Collection.class.isAssignableFrom(parameter.getParameterType())) {
         valueClass = ResolvableType.forMethodParameter(parameter).asCollection().resolveGeneric();
         if (valueClass == null) {
            throw new IllegalArgumentException("Cannot generate variable name for non-typed Collection parameter type");
         }

         pluralize = true;
      } else {
         valueClass = parameter.getParameterType();
         ReactiveAdapter adapter = ReactiveAdapterRegistry.getSharedInstance().getAdapter(valueClass);
         if (adapter != null && !adapter.getDescriptor().isNoValue()) {
            reactiveSuffix = ClassUtils.getShortName(valueClass);
            valueClass = parameter.nested().getNestedParameterType();
         }
      }

      String name = ClassUtils.getShortNameAsProperty(valueClass);
      return pluralize ? pluralize(name) : name + reactiveSuffix;
   }

   public static String getVariableNameForReturnType(Method method) {
      return getVariableNameForReturnType(method, method.getReturnType(), (Object)null);
   }

   public static String getVariableNameForReturnType(Method method, @Nullable Object value) {
      return getVariableNameForReturnType(method, method.getReturnType(), value);
   }

   public static String getVariableNameForReturnType(Method method, Class resolvedType, @Nullable Object value) {
      Assert.notNull(method, (String)"Method must not be null");
      if (Object.class == resolvedType) {
         if (value == null) {
            throw new IllegalArgumentException("Cannot generate variable name for an Object return type with null value");
         } else {
            return getVariableName(value);
         }
      } else {
         boolean pluralize = false;
         String reactiveSuffix = "";
         Class valueClass;
         if (resolvedType.isArray()) {
            valueClass = resolvedType.getComponentType();
            pluralize = true;
         } else if (Collection.class.isAssignableFrom(resolvedType)) {
            valueClass = ResolvableType.forMethodReturnType(method).asCollection().resolveGeneric();
            if (valueClass == null) {
               if (!(value instanceof Collection)) {
                  throw new IllegalArgumentException("Cannot generate variable name for non-typed Collection return type and a non-Collection value");
               }

               Collection collection = (Collection)value;
               if (collection.isEmpty()) {
                  throw new IllegalArgumentException("Cannot generate variable name for non-typed Collection return type and an empty Collection value");
               }

               Object valueToCheck = peekAhead(collection);
               valueClass = getClassForValue(valueToCheck);
            }

            pluralize = true;
         } else {
            valueClass = resolvedType;
            ReactiveAdapter adapter = ReactiveAdapterRegistry.getSharedInstance().getAdapter(resolvedType);
            if (adapter != null && !adapter.getDescriptor().isNoValue()) {
               reactiveSuffix = ClassUtils.getShortName(resolvedType);
               valueClass = ResolvableType.forMethodReturnType(method).getGeneric().toClass();
            }
         }

         String name = ClassUtils.getShortNameAsProperty(valueClass);
         return pluralize ? pluralize(name) : name + reactiveSuffix;
      }
   }

   public static String attributeNameToPropertyName(String attributeName) {
      Assert.notNull(attributeName, (String)"'attributeName' must not be null");
      if (!attributeName.contains("-")) {
         return attributeName;
      } else {
         char[] chars = attributeName.toCharArray();
         char[] result = new char[chars.length - 1];
         int currPos = 0;
         boolean upperCaseNext = false;
         char[] var5 = chars;
         int var6 = chars.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            char c = var5[var7];
            if (c == '-') {
               upperCaseNext = true;
            } else if (upperCaseNext) {
               result[currPos++] = Character.toUpperCase(c);
               upperCaseNext = false;
            } else {
               result[currPos++] = c;
            }
         }

         return new String(result, 0, currPos);
      }
   }

   public static String getQualifiedAttributeName(Class enclosingClass, String attributeName) {
      Assert.notNull(enclosingClass, (String)"'enclosingClass' must not be null");
      Assert.notNull(attributeName, (String)"'attributeName' must not be null");
      return enclosingClass.getName() + '.' + attributeName;
   }

   private static Class getClassForValue(Object value) {
      Class valueClass = value.getClass();
      if (Proxy.isProxyClass(valueClass)) {
         Class[] ifcs = valueClass.getInterfaces();
         Class[] var3 = ifcs;
         int var4 = ifcs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class ifc = var3[var5];
            if (!ClassUtils.isJavaLanguageInterface(ifc)) {
               return ifc;
            }
         }
      } else if (valueClass.getName().lastIndexOf(36) != -1 && valueClass.getDeclaringClass() == null) {
         valueClass = valueClass.getSuperclass();
      }

      return valueClass;
   }

   private static String pluralize(String name) {
      return name + "List";
   }

   private static Object peekAhead(Collection collection) {
      Iterator it = collection.iterator();
      if (!it.hasNext()) {
         throw new IllegalStateException("Unable to peek ahead in non-empty collection - no element found");
      } else {
         Object value = it.next();
         if (value == null) {
            throw new IllegalStateException("Unable to peek ahead in non-empty collection - only null element found");
         } else {
            return value;
         }
      }
   }
}
