package com.sun.faces.facelets.util;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Util;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;

public class ReflectionUtil {
   private static final String[] PRIMITIVE_NAMES = new String[]{"boolean", "byte", "char", "double", "float", "int", "long", "short", "void"};
   private static final Class[] PRIMITIVES;

   private ReflectionUtil() {
   }

   public static Class forName(String name) throws ClassNotFoundException {
      if (null != name && !"".equals(name)) {
         Class c = forNamePrimitive(name);
         if (c == null) {
            if (name.endsWith("[]")) {
               String nc = name.substring(0, name.length() - 2);
               c = Class.forName(nc, false, Thread.currentThread().getContextClassLoader());
               c = Array.newInstance(c, 0).getClass();
            } else {
               c = Class.forName(name, false, Thread.currentThread().getContextClassLoader());
            }
         }

         return c;
      } else {
         return null;
      }
   }

   protected static Class forNamePrimitive(String name) {
      if (name.length() <= 8) {
         int p = Arrays.binarySearch(PRIMITIVE_NAMES, name);
         if (p >= 0) {
            return PRIMITIVES[p];
         }
      }

      return null;
   }

   public static Class[] toTypeArray(String[] s) throws ClassNotFoundException {
      if (s == null) {
         return null;
      } else {
         Class[] c = new Class[s.length];

         for(int i = 0; i < s.length; ++i) {
            c[i] = forName(s[i]);
         }

         return c;
      }
   }

   public static String[] toTypeNameArray(Class[] c) {
      if (c == null) {
         return null;
      } else {
         String[] s = new String[c.length];

         for(int i = 0; i < c.length; ++i) {
            s[i] = c[i].getName();
         }

         return s;
      }
   }

   protected static final String paramString(Class[] types) {
      if (types == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < types.length; ++i) {
            sb.append(types[i].getName()).append(", ");
         }

         if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
         }

         return sb.toString();
      }
   }

   public static Object decorateInstance(Class clazz, Class rootType, Object root) {
      Object returnObject = null;

      try {
         if (returnObject == null && rootType != null && root != null) {
            Constructor construct = ReflectionUtils.lookupConstructor(clazz, rootType);
            if (construct != null) {
               returnObject = construct.newInstance(root);
            }
         }

         if (clazz != null && returnObject == null) {
            returnObject = clazz.newInstance();
         }

         return returnObject;
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException var5) {
         throw new ConfigurationException(buildMessage(MessageFormat.format("Unable to create a new instance of ''{0}'': {1}", clazz.getName(), var5.toString())), var5);
      }
   }

   public static Object decorateInstance(String className, Class rootType, Object root) {
      Object returnObject = null;
      if (className != null) {
         try {
            Class clazz = loadClass(className, returnObject, (Class)null);
            if (clazz != null) {
               returnObject = decorateInstance(clazz, rootType, root);
            }
         } catch (ClassNotFoundException var6) {
            throw new ConfigurationException(buildMessage(MessageFormat.format("Unable to find class ''{0}''", className)));
         } catch (NoClassDefFoundError var7) {
            throw new ConfigurationException(buildMessage(MessageFormat.format("Class ''{0}'' is missing a runtime dependency: {1}", className, var7.toString())));
         } catch (ClassCastException var8) {
            throw new ConfigurationException(buildMessage(MessageFormat.format("Class ''{0}'' is not an instance of ''{1}''", className, rootType)));
         } catch (Exception var9) {
            throw new ConfigurationException(buildMessage(MessageFormat.format("Unable to create a new instance of ''{0}'': {1}", className, var9.toString())), var9);
         }
      }

      return returnObject;
   }

   private static String buildMessage(String cause) {
      return MessageFormat.format("\n  Source Document: {0}\n  Cause: {1}", "web.xml", cause);
   }

   private static Class loadClass(String className, Object fallback, Class expectedType) throws ClassNotFoundException {
      Class clazz = Util.loadClass(className, fallback);
      if (expectedType != null && !expectedType.isAssignableFrom(clazz)) {
         throw new ClassCastException();
      } else {
         return clazz;
      }
   }

   private static boolean isDevModeEnabled() {
      WebConfiguration webconfig = WebConfiguration.getInstance();
      return webconfig != null && "Development".equals(webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.JavaxFacesProjectStage));
   }

   static {
      PRIMITIVES = new Class[]{Boolean.TYPE, Byte.TYPE, Character.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE, Void.TYPE};
   }
}
