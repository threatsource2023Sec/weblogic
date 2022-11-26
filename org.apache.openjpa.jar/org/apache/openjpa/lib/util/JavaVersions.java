package org.apache.openjpa.lib.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;

public class JavaVersions {
   public static final int VERSION;
   private static final Class[] EMPTY_CLASSES = new Class[0];
   private static Class PARAM_TYPE = null;
   private static Class ENUM_TYPE = null;
   private static Class ANNO_TYPE = null;
   private static Method GET_STACK = null;
   private static Method SET_STACK = null;
   private static Method GET_CAUSE = null;
   private static Method INIT_CAUSE = null;

   public static Class getVersionSpecificClass(Class base) {
      try {
         return getVersionSpecificClass(base.getName());
      } catch (ClassNotFoundException var2) {
         return base;
      }
   }

   public static Class getVersionSpecificClass(String base) throws ClassNotFoundException {
      int i = VERSION;

      while(i >= 1) {
         try {
            return Class.forName(base + i);
         } catch (Throwable var3) {
            --i;
         }
      }

      return Class.forName(base);
   }

   public static boolean isAnnotation(Class cls) {
      return ANNO_TYPE != null && ANNO_TYPE.isAssignableFrom(cls);
   }

   public static boolean isEnumeration(Class cls) {
      return ENUM_TYPE != null && ENUM_TYPE.isAssignableFrom(cls);
   }

   public static Class[] getParameterizedTypes(Field f) {
      if (f == null) {
         return null;
      } else if (VERSION < 5) {
         return EMPTY_CLASSES;
      } else {
         try {
            Object type = Field.class.getMethod("getGenericType", (Class[])null).invoke(f, (Object[])null);
            return collectParameterizedTypes(type);
         } catch (Exception var2) {
            return EMPTY_CLASSES;
         }
      }
   }

   public static Class[] getParameterizedTypes(Method meth) {
      if (meth == null) {
         return null;
      } else if (VERSION < 5) {
         return EMPTY_CLASSES;
      } else {
         try {
            Object type = Method.class.getMethod("getGenericReturnType", (Class[])null).invoke(meth, (Object[])null);
            return collectParameterizedTypes(type);
         } catch (Exception var2) {
            return EMPTY_CLASSES;
         }
      }
   }

   private static Class[] collectParameterizedTypes(Object type) throws Exception {
      if (PARAM_TYPE != null && PARAM_TYPE.isInstance(type)) {
         Object[] args = (Object[])((Object[])PARAM_TYPE.getMethod("getActualTypeArguments", (Class[])null).invoke(type, (Object[])null));
         if (args.length == 0) {
            return EMPTY_CLASSES;
         } else {
            Class[] clss = new Class[args.length];

            for(int i = 0; i < args.length; ++i) {
               if (!(args[i] instanceof Class)) {
                  return EMPTY_CLASSES;
               }

               clss[i] = (Class)args[i];
            }

            return clss;
         }
      } else {
         return EMPTY_CLASSES;
      }
   }

   public static boolean transferStackTrace(Throwable from, Throwable to) {
      if (GET_STACK != null && SET_STACK != null && from != null && to != null) {
         try {
            Object stack = GET_STACK.invoke(from, (Object[])null);
            SET_STACK.invoke(to, stack);
            return true;
         } catch (Throwable var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static Throwable getCause(Throwable ex) {
      if (GET_CAUSE != null && ex != null) {
         try {
            return (Throwable)GET_CAUSE.invoke(ex, (Object[])null);
         } catch (Throwable var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static Throwable initCause(Throwable ex, Throwable cause) {
      if (INIT_CAUSE != null && ex != null && cause != null) {
         try {
            return (Throwable)INIT_CAUSE.invoke(ex, cause);
         } catch (Throwable var3) {
            return ex;
         }
      } else {
         return ex;
      }
   }

   public static void main(String[] args) {
      System.out.println("Java version is: " + VERSION);
   }

   static {
      String specVersion = (String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction("java.specification.version"));
      if ("1.2".equals(specVersion)) {
         VERSION = 2;
      } else if ("1.3".equals(specVersion)) {
         VERSION = 3;
      } else if ("1.4".equals(specVersion)) {
         VERSION = 4;
      } else if ("1.5".equals(specVersion)) {
         VERSION = 5;
      } else if ("1.6".equals(specVersion)) {
         VERSION = 6;
      } else {
         VERSION = 7;
      }

      if (VERSION >= 5) {
         try {
            PARAM_TYPE = Class.forName("java.lang.reflect.ParameterizedType");
            ENUM_TYPE = Class.forName("java.lang.Enum");
            ANNO_TYPE = Class.forName("java.lang.annotation.Annotation");
         } catch (Throwable var3) {
         }
      }

      if (VERSION >= 4) {
         try {
            Class stack = Class.forName("[Ljava.lang.StackTraceElement;");
            GET_STACK = Throwable.class.getMethod("getStackTrace", (Class[])null);
            SET_STACK = Throwable.class.getMethod("setStackTrace", stack);
            GET_CAUSE = Throwable.class.getMethod("getCause", (Class[])null);
            INIT_CAUSE = Throwable.class.getMethod("initCause", Throwable.class);
         } catch (Throwable var2) {
         }
      }

   }
}
