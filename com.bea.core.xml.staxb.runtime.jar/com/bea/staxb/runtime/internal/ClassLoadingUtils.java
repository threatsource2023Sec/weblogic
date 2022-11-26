package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlRuntimeException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class ClassLoadingUtils {
   private static final Map PRIMITIVES = new HashMap();

   public static Class loadClass(String className, ClassLoader backup_classloader) throws ClassNotFoundException {
      if (className.equals("java.lang.String")) {
         return String.class;
      } else {
         Class prim_class = loadPrimitiveClass(className);
         if (prim_class != null) {
            return prim_class;
         } else {
            Class array_class = loadArrayClass(className, backup_classloader);
            return array_class != null ? array_class : loadNonArrayClass(className, backup_classloader);
         }
      }
   }

   private static Class loadPrimitiveClass(String className) {
      return (Class)PRIMITIVES.get(className);
   }

   private static Class loadNonArrayClass(String className, ClassLoader backup_classloader) throws ClassNotFoundException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = backup_classloader;
      }

      try {
         return cl.loadClass(className);
      } catch (ClassNotFoundException var4) {
         return Class.forName(className);
      }
   }

   private static Class loadArrayClass(String className, ClassLoader backup_classloader) throws ClassNotFoundException {
      StringBuffer component = new StringBuffer(className.length());
      int rank = getArrayComponentNameFromDecl(component, className);
      if (rank < 1) {
         return null;
      } else {
         int[] ranks = new int[rank];
         Arrays.fill(ranks, 1);
         String arrayClassName = component.toString();
         Class component_class = loadPrimitiveClass(arrayClassName);
         if (component_class == null) {
            component_class = loadNonArrayClass(arrayClassName, backup_classloader);
         }

         Object instance = Array.newInstance(component_class, ranks);
         return instance.getClass();
      }
   }

   private static int getArrayComponentNameFromDecl(StringBuffer compname, String aname) {
      compname.setLength(0);
      int first_bracket = aname.indexOf(91);
      if (first_bracket <= 0) {
         compname.append(aname);
         return 0;
      } else {
         String base = aname.substring(0, first_bracket).trim();
         compname.append(base);
         int rank = 0;

         for(int idx = aname.indexOf(93); idx >= 0; idx = aname.indexOf(93, idx + 1)) {
            ++rank;
         }

         assert compname.length() > 0;

         assert rank > 0;

         return rank;
      }
   }

   static Object newInstance(Class javaClass) {
      try {
         Class oc = javaClass.getEnclosingClass();
         return oc != null && !Modifier.isStatic(javaClass.getModifiers()) ? javaClass.getConstructor(oc).newInstance(oc.newInstance()) : javaClass.newInstance();
      } catch (InstantiationException var2) {
         throw new XmlRuntimeException(var2);
      } catch (IllegalAccessException var3) {
         throw new XmlRuntimeException(var3);
      } catch (NoSuchMethodException var4) {
         throw new XmlRuntimeException(var4);
      } catch (InvocationTargetException var5) {
         throw new XmlRuntimeException(var5);
      }
   }

   static {
      PRIMITIVES.put(Boolean.TYPE.getName(), Boolean.TYPE);
      PRIMITIVES.put(Character.TYPE.getName(), Character.TYPE);
      PRIMITIVES.put(Byte.TYPE.getName(), Byte.TYPE);
      PRIMITIVES.put(Short.TYPE.getName(), Short.TYPE);
      PRIMITIVES.put(Integer.TYPE.getName(), Integer.TYPE);
      PRIMITIVES.put(Long.TYPE.getName(), Long.TYPE);
      PRIMITIVES.put(Float.TYPE.getName(), Float.TYPE);
      PRIMITIVES.put(Double.TYPE.getName(), Double.TYPE);
   }
}
