package com.bea.core.repackaged.aspectj.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Aspects14 {
   private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
   private static final Class[] PEROBJECT_CLASS_ARRAY;
   private static final Class[] PERTYPEWITHIN_CLASS_ARRAY;
   private static final Object[] EMPTY_OBJECT_ARRAY;
   private static final String ASPECTOF = "aspectOf";
   private static final String HASASPECT = "hasAspect";
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$java$lang$Class;

   public static Object aspectOf(Class aspectClass) throws NoAspectBoundException {
      try {
         return getSingletonOrThreadAspectOf(aspectClass).invoke((Object)null, EMPTY_OBJECT_ARRAY);
      } catch (InvocationTargetException var2) {
         throw new NoAspectBoundException(aspectClass.getName(), var2);
      } catch (Exception var3) {
         throw new NoAspectBoundException(aspectClass.getName(), var3);
      }
   }

   public static Object aspectOf(Class aspectClass, Object perObject) throws NoAspectBoundException {
      try {
         return getPerObjectAspectOf(aspectClass).invoke((Object)null, perObject);
      } catch (InvocationTargetException var3) {
         throw new NoAspectBoundException(aspectClass.getName(), var3);
      } catch (Exception var4) {
         throw new NoAspectBoundException(aspectClass.getName(), var4);
      }
   }

   public static Object aspectOf(Class aspectClass, Class perTypeWithin) throws NoAspectBoundException {
      try {
         return getPerTypeWithinAspectOf(aspectClass).invoke((Object)null, perTypeWithin);
      } catch (InvocationTargetException var3) {
         throw new NoAspectBoundException(aspectClass.getName(), var3);
      } catch (Exception var4) {
         throw new NoAspectBoundException(aspectClass.getName(), var4);
      }
   }

   public static boolean hasAspect(Class aspectClass) throws NoAspectBoundException {
      try {
         return (Boolean)getSingletonOrThreadHasAspect(aspectClass).invoke((Object)null, EMPTY_OBJECT_ARRAY);
      } catch (Exception var2) {
         return false;
      }
   }

   public static boolean hasAspect(Class aspectClass, Object perObject) throws NoAspectBoundException {
      try {
         return (Boolean)getPerObjectHasAspect(aspectClass).invoke((Object)null, perObject);
      } catch (Exception var3) {
         return false;
      }
   }

   public static boolean hasAspect(Class aspectClass, Class perTypeWithin) throws NoAspectBoundException {
      try {
         return (Boolean)getPerTypeWithinHasAspect(aspectClass).invoke((Object)null, perTypeWithin);
      } catch (Exception var3) {
         return false;
      }
   }

   private static Method getSingletonOrThreadAspectOf(Class aspectClass) throws NoSuchMethodException {
      Method method = aspectClass.getDeclaredMethod("aspectOf", EMPTY_CLASS_ARRAY);
      return checkAspectOf(method, aspectClass);
   }

   private static Method getPerObjectAspectOf(Class aspectClass) throws NoSuchMethodException {
      Method method = aspectClass.getDeclaredMethod("aspectOf", PEROBJECT_CLASS_ARRAY);
      return checkAspectOf(method, aspectClass);
   }

   private static Method getPerTypeWithinAspectOf(Class aspectClass) throws NoSuchMethodException {
      Method method = aspectClass.getDeclaredMethod("aspectOf", PERTYPEWITHIN_CLASS_ARRAY);
      return checkAspectOf(method, aspectClass);
   }

   private static Method checkAspectOf(Method method, Class aspectClass) throws NoSuchMethodException {
      method.setAccessible(true);
      if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
         return method;
      } else {
         throw new NoSuchMethodException(aspectClass.getName() + ".aspectOf(..) is not accessible public static");
      }
   }

   private static Method getSingletonOrThreadHasAspect(Class aspectClass) throws NoSuchMethodException {
      Method method = aspectClass.getDeclaredMethod("hasAspect", EMPTY_CLASS_ARRAY);
      return checkHasAspect(method, aspectClass);
   }

   private static Method getPerObjectHasAspect(Class aspectClass) throws NoSuchMethodException {
      Method method = aspectClass.getDeclaredMethod("hasAspect", PEROBJECT_CLASS_ARRAY);
      return checkHasAspect(method, aspectClass);
   }

   private static Method getPerTypeWithinHasAspect(Class aspectClass) throws NoSuchMethodException {
      Method method = aspectClass.getDeclaredMethod("hasAspect", PERTYPEWITHIN_CLASS_ARRAY);
      return checkHasAspect(method, aspectClass);
   }

   private static Method checkHasAspect(Method method, Class aspectClass) throws NoSuchMethodException {
      method.setAccessible(true);
      if (method.isAccessible() && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
         return method;
      } else {
         throw new NoSuchMethodException(aspectClass.getName() + ".hasAspect(..) is not accessible public static");
      }
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      PEROBJECT_CLASS_ARRAY = new Class[]{class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object};
      PERTYPEWITHIN_CLASS_ARRAY = new Class[]{class$java$lang$Class == null ? (class$java$lang$Class = class$("java.lang.Class")) : class$java$lang$Class};
      EMPTY_OBJECT_ARRAY = new Object[0];
   }
}
