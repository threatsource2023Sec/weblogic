package org.apache.openjpa.enhance;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.UserException;

public class Reflection {
   private static final Localizer _loc = Localizer.forPackage(Reflection.class);

   public static Method findGetter(Class cls, String prop, boolean mustExist) {
      prop = StringUtils.capitalize(prop);
      String name = "get" + prop;

      try {
         for(Class c = cls; c != null && c != Object.class; c = c.getSuperclass()) {
            Method m = getDeclaredMethod(c, name, (Class)null);
            if (m != null) {
               return m;
            }

            m = getDeclaredMethod(c, "is" + prop, (Class)null);
            if (m != null && (m.getReturnType() == Boolean.TYPE || m.getReturnType() == Boolean.class)) {
               return m;
            }
         }
      } catch (Exception var6) {
         throw new GeneralException(var6);
      }

      if (mustExist) {
         throw new UserException(_loc.get("bad-getter", cls, prop));
      } else {
         return null;
      }
   }

   public static Method findSetter(Class cls, String prop, boolean mustExist) {
      Method getter = findGetter(cls, prop, mustExist);
      return getter == null ? null : findSetter(cls, prop, getter.getReturnType(), mustExist);
   }

   public static Method findSetter(Class cls, String prop, Class param, boolean mustExist) {
      String name = "set" + StringUtils.capitalize(prop);

      try {
         for(Class c = cls; c != null && c != Object.class; c = c.getSuperclass()) {
            Method m = getDeclaredMethod(c, name, param);
            if (m != null) {
               return m;
            }
         }
      } catch (Exception var7) {
         throw new GeneralException(var7);
      }

      if (mustExist) {
         throw new UserException(_loc.get("bad-setter", cls, prop));
      } else {
         return null;
      }
   }

   static Method getDeclaredMethod(Class cls, String name, Class param) {
      Method[] methods = (Method[])((Method[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodsAction(cls)));
      Method candidate = null;

      for(int i = 0; i < methods.length; ++i) {
         if (name.equals(methods[i].getName())) {
            Class[] methodParams = methods[i].getParameterTypes();
            if (param == null && methodParams.length == 0) {
               candidate = mostDerived(methods[i], candidate);
            } else if (param != null && methodParams.length == 1 && param.equals(methodParams[0])) {
               candidate = mostDerived(methods[i], candidate);
            }
         }
      }

      return candidate;
   }

   static Method mostDerived(Method meth1, Method meth2) {
      if (meth1 == null) {
         return meth2;
      } else if (meth2 == null) {
         return meth1;
      } else {
         Class cls2 = meth2.getDeclaringClass();
         Class cls1 = meth1.getDeclaringClass();
         if (cls1.equals(cls2)) {
            Class ret1 = meth1.getReturnType();
            Class ret2 = meth2.getReturnType();
            if (ret1.isAssignableFrom(ret2)) {
               return meth2;
            } else if (ret2.isAssignableFrom(ret1)) {
               return meth1;
            } else {
               throw new IllegalArgumentException(_loc.get("most-derived-unrelated-same-type", meth1, meth2).getMessage());
            }
         } else if (cls1.isAssignableFrom(cls2)) {
            return meth2;
         } else if (cls2.isAssignableFrom(cls1)) {
            return meth1;
         } else {
            throw new IllegalArgumentException(_loc.get("most-derived-unrelated", meth1, meth2).getMessage());
         }
      }
   }

   public static Field findField(Class cls, String name, boolean mustExist) {
      try {
         for(Class c = cls; c != null && c != Object.class; c = c.getSuperclass()) {
            Field f = getDeclaredField(c, name);
            if (f != null) {
               return f;
            }
         }
      } catch (Exception var5) {
         throw new GeneralException(var5);
      }

      if (mustExist) {
         throw new UserException(_loc.get("bad-field", cls, name));
      } else {
         return null;
      }
   }

   private static Field getDeclaredField(Class cls, String name) {
      Field[] fields = (Field[])((Field[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldsAction(cls)));

      for(int i = 0; i < fields.length; ++i) {
         if (name.equals(fields[i].getName())) {
            return fields[i];
         }
      }

      return null;
   }

   public static Object get(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.get(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return null;
      }
   }

   private static void makeAccessible(AccessibleObject ao, int mods) {
      try {
         if (!Modifier.isPublic(mods) && !ao.isAccessible()) {
            AccessController.doPrivileged(J2DoPrivHelper.setAccessibleAction(ao, true));
         }

      } catch (SecurityException var3) {
         throw (new UserException(_loc.get("reflect-security", (Object)ao))).setFatal(true);
      }
   }

   private static RuntimeException wrapReflectionException(Throwable t) {
      if (t instanceof InvocationTargetException) {
         t = ((InvocationTargetException)t).getTargetException();
      }

      return (RuntimeException)(t instanceof RuntimeException ? (RuntimeException)t : new GeneralException(t));
   }

   public static boolean getBoolean(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getBoolean(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return false;
      }
   }

   public static byte getByte(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getByte(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return 0;
      }
   }

   public static char getChar(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getChar(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return '\u0000';
      }
   }

   public static double getDouble(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getDouble(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return 0.0;
      }
   }

   public static float getFloat(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getFloat(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return 0.0F;
      }
   }

   public static int getInt(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getInt(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return 0;
      }
   }

   public static long getLong(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getLong(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return 0L;
      }
   }

   public static short getShort(Object target, Field field) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            return field.getShort(target);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return 0;
      }
   }

   public static Object get(Object target, Method getter) {
      if (target != null && getter != null) {
         makeAccessible(getter, getter.getModifiers());

         try {
            return getter.invoke(target, (Object[])null);
         } catch (Throwable var3) {
            throw wrapReflectionException(var3);
         }
      } else {
         return null;
      }
   }

   public static boolean getBoolean(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? false : (Boolean)o;
   }

   public static byte getByte(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? 0 : ((Number)o).byteValue();
   }

   public static char getChar(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? '\u0000' : (Character)o;
   }

   public static double getDouble(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? 0.0 : ((Number)o).doubleValue();
   }

   public static float getFloat(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? 0.0F : ((Number)o).floatValue();
   }

   public static int getInt(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? 0 : ((Number)o).intValue();
   }

   public static long getLong(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? 0L : ((Number)o).longValue();
   }

   public static short getShort(Object target, Method getter) {
      Object o = get(target, getter);
      return o == null ? 0 : ((Number)o).shortValue();
   }

   public static void set(Object target, Field field, Object value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.set(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Field field, boolean value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setBoolean(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Field field, byte value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setByte(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Field field, char value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setChar(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Field field, double value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setDouble(target, value);
         } catch (Throwable var5) {
            throw wrapReflectionException(var5);
         }
      }
   }

   public static void set(Object target, Field field, float value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setFloat(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Field field, int value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setInt(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Field field, long value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setLong(target, value);
         } catch (Throwable var5) {
            throw wrapReflectionException(var5);
         }
      }
   }

   public static void set(Object target, Field field, short value) {
      if (target != null && field != null) {
         makeAccessible(field, field.getModifiers());

         try {
            field.setShort(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Object value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, boolean value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, byte value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, char value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, double value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, float value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, int value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, long value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, short value, Field field) {
      set(target, field, value);
   }

   public static void set(Object target, Method setter, Object value) {
      if (target != null && setter != null) {
         makeAccessible(setter, setter.getModifiers());

         try {
            setter.invoke(target, value);
         } catch (Throwable var4) {
            throw wrapReflectionException(var4);
         }
      }
   }

   public static void set(Object target, Method setter, boolean value) {
      set(target, (Method)setter, (Object)(value ? Boolean.TRUE : Boolean.FALSE));
   }

   public static void set(Object target, Method setter, byte value) {
      set(target, (Method)setter, (Object)(new Byte(value)));
   }

   public static void set(Object target, Method setter, char value) {
      set(target, (Method)setter, (Object)(new Character(value)));
   }

   public static void set(Object target, Method setter, double value) {
      set(target, (Method)setter, (Object)(new Double(value)));
   }

   public static void set(Object target, Method setter, float value) {
      set(target, (Method)setter, (Object)(new Float(value)));
   }

   public static void set(Object target, Method setter, int value) {
      set(target, (Method)setter, (Object)(new Integer(value)));
   }

   public static void set(Object target, Method setter, long value) {
      set(target, (Method)setter, (Object)(new Long(value)));
   }

   public static void set(Object target, Method setter, short value) {
      set(target, (Method)setter, (Object)(new Short(value)));
   }
}
