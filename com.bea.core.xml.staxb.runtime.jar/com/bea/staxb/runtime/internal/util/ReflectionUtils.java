package com.bea.staxb.runtime.internal.util;

import com.bea.staxb.buildtime.internal.bts.BindingProperty;
import com.bea.staxb.buildtime.internal.bts.MethodName;
import com.bea.xml.XmlException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {
   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

   public static Object invokeMethod(Object target, Method method, Object[] params) throws XmlException {
      assert method != null : "null method";

      assert target == null || method.getDeclaringClass().isInstance(target) : "DECL=" + method.getDeclaringClass() + " GOT:" + target.getClass();

      assert checkParams(method, params);

      try {
         return method.invoke(target, params);
      } catch (IllegalAccessException var4) {
         throw new XmlException(var4);
      } catch (IllegalArgumentException var5) {
         throw new XmlException(var5);
      } catch (InvocationTargetException var6) {
         throw new XmlException(var6.getTargetException());
      }
   }

   public static Object invokeConstructor(Constructor ctor, Object[] params) throws XmlException {
      assert ctor != null : "null method";

      assert checkParams(ctor, params);

      try {
         return ctor.newInstance(params);
      } catch (IllegalAccessException var3) {
         throw new XmlException("error invoking constructor " + ctor, var3);
      } catch (IllegalArgumentException var4) {
         throw new XmlException("error invoking constructor " + ctor, var4);
      } catch (InvocationTargetException var5) {
         throw new XmlException("error invoking constructor " + ctor, var5.getTargetException());
      } catch (InstantiationException var6) {
         throw new XmlException("error invoking constructor " + ctor, var6);
      }
   }

   private static boolean checkParams(Method method, Object[] params) {
      assert method != null;

      int expected_len = method.getParameterTypes().length;
      int actual_len = params == null ? 0 : params.length;
      if (actual_len != expected_len) {
         String msg = "Method " + method + " expects " + expected_len + " parameters -- got " + actual_len;
         throw new AssertionError(msg);
      } else {
         return true;
      }
   }

   private static boolean checkParams(Constructor ctor, Object[] params) {
      assert ctor != null;

      int expected_len = ctor.getParameterTypes().length;
      int actual_len = params == null ? 0 : params.length;
      if (actual_len != expected_len) {
         String msg = "Method " + ctor + " expects " + expected_len + " parameters -- got " + actual_len;
         throw new AssertionError(msg);
      } else {
         return true;
      }
   }

   public static Object invokeMethod(Object target, Method method) throws XmlException {
      return invokeMethod(target, method, EMPTY_OBJECT_ARRAY);
   }

   public static Method getSetterMethod(BindingProperty binding_prop, Class beanClass) throws XmlException {
      if (!binding_prop.hasSetter()) {
         return null;
      } else {
         MethodName setterName = binding_prop.getSetterName();
         return getMethodOnClass(setterName, beanClass);
      }
   }

   public static Method getIssetterMethod(BindingProperty binding_prop, Class clazz) throws XmlException {
      if (!binding_prop.hasIssetter()) {
         return null;
      } else {
         Method isset_method = getMethodOnClass(binding_prop.getIssetterName(), clazz);
         if (!isset_method.getReturnType().equals(Boolean.TYPE)) {
            String msg = "invalid isset method: " + isset_method + " -- return type must be boolean not " + isset_method.getReturnType().getName();
            throw new XmlException(msg);
         } else {
            return isset_method;
         }
      }
   }

   public static Method getGetterMethod(BindingProperty binding_prop, Class beanClass) throws XmlException {
      MethodName getterName = binding_prop.getGetterName();
      return getMethodOnClass(getterName, beanClass);
   }

   public static Method getMethodOnClass(MethodName method_name, Class clazz) throws XmlException {
      if (method_name == null) {
         return null;
      } else {
         try {
            return method_name.getMethodOn(clazz);
         } catch (NoSuchMethodException var4) {
            String m = "failed to find method " + method_name + " on " + clazz;
            throw new XmlException(m, var4);
         } catch (SecurityException var5) {
            throw new XmlException(var5);
         } catch (ClassNotFoundException var6) {
            throw new XmlException(var6);
         }
      }
   }

   public static boolean isMethodStatic(Method m) {
      return Modifier.isStatic(m.getModifiers());
   }

   public static boolean isClassFinal(Class javaClass) {
      int modifiers = javaClass.getModifiers();
      return Modifier.isFinal(modifiers);
   }

   public static Field getField(BindingProperty prop, Class aClass) throws XmlException {
      String field_name = prop.getFieldName();

      try {
         Field field = aClass.getField(field_name);
         int mods = field.getModifiers();
         if (Modifier.isPublic(mods) && !Modifier.isStatic(mods) && !Modifier.isFinal(mods)) {
            return field;
         } else {
            String msg = "only public, non-static, non-final fields supported: " + field + " in property " + prop;
            throw new XmlException(msg);
         }
      } catch (NoSuchFieldException var6) {
         throw new XmlException(var6);
      } catch (SecurityException var7) {
         throw new XmlException(var7);
      }
   }

   public static Object getFieldValue(Object target, Field field) throws XmlException {
      try {
         return field.get(target);
      } catch (IllegalArgumentException var3) {
         throw new XmlException(var3);
      } catch (IllegalAccessException var4) {
         throw new XmlException(var4);
      }
   }

   public static void setFieldValue(Object target, Field field, Object value) throws XmlException {
      try {
         field.set(target, value);
      } catch (IllegalArgumentException var4) {
         throw new XmlException(var4);
      } catch (IllegalAccessException var5) {
         throw new XmlException(var5);
      }
   }
}
