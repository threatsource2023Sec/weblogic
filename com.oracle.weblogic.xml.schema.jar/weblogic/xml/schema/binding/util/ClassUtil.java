package weblogic.xml.schema.binding.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import weblogic.xml.schema.binding.internal.codegen.ArrayUtils;

public class ClassUtil {
   private static final boolean DEBUG = false;
   private static final boolean VERBOSE = false;
   private static final Map primMap = new HashMap();

   public static Object newInstance(String className) throws ClassUtilException {
      try {
         Class c = loadClass(className);
         return c.newInstance();
      } catch (IllegalAccessException var2) {
         throw new ClassUtilException("failed to create new instance:" + var2);
      } catch (InstantiationException var3) {
         throw new ClassUtilException("failed to create new instance:" + var3);
      }
   }

   public static Class loadClass(String className) throws ClassUtilException {
      Class prim_class = loadPrimitiveClass(className);
      if (prim_class != null) {
         return prim_class;
      } else {
         try {
            Class array_class = loadArrayClass(className);
            return array_class != null ? array_class : loadNonArrayClass(className);
         } catch (ClassNotFoundException var3) {
            throw new ClassUtilException("unable to load class:" + var3);
         }
      }
   }

   private static Class loadPrimitiveClass(String className) {
      return (Class)primMap.get(className);
   }

   private static Class loadNonArrayClass(String className) throws ClassNotFoundException {
      Class prim_class = loadPrimitiveClass(className);
      if (prim_class != null) {
         return prim_class;
      } else {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         if (cl == null) {
            cl = ClassUtil.class.getClassLoader();
         }

         return cl.loadClass(className);
      }
   }

   private static Class loadArrayClass(String className) throws ClassNotFoundException {
      StringBuffer component = new StringBuffer(className.length());
      int rank = ArrayUtils.getArrayComponentNameFromDecl(component, className);
      if (rank < 1) {
         return null;
      } else {
         int[] ranks = new int[rank];
         Arrays.fill(ranks, 1);
         Class component_class = loadNonArrayClass(component.toString());
         Object instance = Array.newInstance(component_class, ranks);
         return instance.getClass();
      }
   }

   static {
      primMap.put(Boolean.TYPE.getName(), Boolean.TYPE);
      primMap.put(Character.TYPE.getName(), Character.TYPE);
      primMap.put(Byte.TYPE.getName(), Byte.TYPE);
      primMap.put(Short.TYPE.getName(), Short.TYPE);
      primMap.put(Integer.TYPE.getName(), Integer.TYPE);
      primMap.put(Long.TYPE.getName(), Long.TYPE);
      primMap.put(Float.TYPE.getName(), Float.TYPE);
      primMap.put(Double.TYPE.getName(), Double.TYPE);
   }

   public static class ClassUtilException extends RuntimeException {
      public ClassUtilException(String message) {
         super(message);
      }
   }
}
