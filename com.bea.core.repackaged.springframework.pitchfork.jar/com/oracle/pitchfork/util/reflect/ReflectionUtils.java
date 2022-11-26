package com.oracle.pitchfork.util.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ReflectionUtils {
   private static final Map CLASS_TRANSLATION = new HashMap();
   private static final Set PRIM_SET = new HashSet();

   private ReflectionUtils() {
   }

   public static void checkMethodAndArguments(Method method, Object[] args) throws IllegalArgumentException {
      checkArguments(method.getParameterTypes(), args);
   }

   public static void checkArguments(Class[] parameters, Object[] args) throws IllegalArgumentException {
      if (args == null) {
         args = new Object[0];
      }

      if (args.length != parameters.length) {
         throw new IllegalArgumentException("Invalid number of arguments " + args.length + " when method has " + parameters.length);
      } else {
         for(int lcv = 0; lcv < parameters.length; ++lcv) {
            Class param = parameters[lcv];
            Class argClass = args[lcv] == null ? null : args[lcv].getClass();
            if (!isAssignableFromIncludingPrimitive(param, argClass)) {
               throw new IllegalArgumentException("Invalid argument in spot " + (lcv + 1) + " of type " + (argClass == null ? "null" : argClass.getName()) + " when it should be of type " + param.getName());
            }
         }

      }
   }

   private static boolean isAssignableFromIncludingPrimitive(Class originalClass, Class testClass) {
      if (originalClass == null) {
         return false;
      } else if (testClass == null) {
         return !PRIM_SET.contains(originalClass);
      } else if (originalClass.isAssignableFrom(testClass)) {
         return true;
      } else {
         Class convertedClass = (Class)CLASS_TRANSLATION.get(testClass);
         return convertedClass == null ? false : originalClass.isAssignableFrom(convertedClass);
      }
   }

   public static void makeAccessible(Method m) {
      if (!Modifier.isPublic(m.getModifiers())) {
         makeAccessible((AccessibleObject)m);
      }

   }

   public static void makeAccessible(Field f) {
      if (!Modifier.isPublic(f.getModifiers())) {
         makeAccessible((AccessibleObject)f);
      }

   }

   public static void makeAccessible(AccessibleObject ao) {
      if (!ao.isAccessible()) {
         ao.setAccessible(true);
      }

   }

   public static Constructor getDefaultConstructor(Class clazz) {
      Constructor ctr = null;
      Constructor[] var2 = clazz.getConstructors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Constructor constructor = var2[var4];
         if (constructor.getParameterTypes().length == 0) {
            ctr = constructor;
            break;
         }
      }

      return ctr;
   }

   static {
      PRIM_SET.add(Boolean.TYPE);
      PRIM_SET.add(Character.TYPE);
      PRIM_SET.add(Byte.TYPE);
      PRIM_SET.add(Short.TYPE);
      PRIM_SET.add(Integer.TYPE);
      PRIM_SET.add(Long.TYPE);
      PRIM_SET.add(Float.TYPE);
      PRIM_SET.add(Double.TYPE);
      CLASS_TRANSLATION.put(Short.class, Short.TYPE);
      CLASS_TRANSLATION.put(Integer.class, Integer.TYPE);
      CLASS_TRANSLATION.put(Long.class, Long.TYPE);
      CLASS_TRANSLATION.put(Float.class, Float.TYPE);
      CLASS_TRANSLATION.put(Double.class, Double.TYPE);
      CLASS_TRANSLATION.put(Boolean.class, Boolean.TYPE);
      CLASS_TRANSLATION.put(Character.class, Character.TYPE);
      CLASS_TRANSLATION.put(Byte.class, Byte.TYPE);
   }
}
