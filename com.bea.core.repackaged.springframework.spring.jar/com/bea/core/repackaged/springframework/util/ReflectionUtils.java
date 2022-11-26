package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ReflectionUtils {
   /** @deprecated */
   @Deprecated
   public static final MethodFilter NON_BRIDGED_METHODS = (method) -> {
      return !method.isBridge();
   };
   public static final MethodFilter USER_DECLARED_METHODS = (method) -> {
      return !method.isBridge() && !method.isSynthetic() && method.getDeclaringClass() != Object.class;
   };
   public static final FieldFilter COPYABLE_FIELDS = (field) -> {
      return !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers());
   };
   private static final String CGLIB_RENAMED_METHOD_PREFIX = "CGLIB$";
   private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
   private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];
   private static final Map declaredMethodsCache = new ConcurrentReferenceHashMap(256);
   private static final Map declaredFieldsCache = new ConcurrentReferenceHashMap(256);

   public static void handleReflectionException(Exception ex) {
      if (ex instanceof NoSuchMethodException) {
         throw new IllegalStateException("Method not found: " + ex.getMessage());
      } else if (ex instanceof IllegalAccessException) {
         throw new IllegalStateException("Could not access method: " + ex.getMessage());
      } else {
         if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException)ex);
         }

         if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
         } else {
            throw new UndeclaredThrowableException(ex);
         }
      }
   }

   public static void handleInvocationTargetException(InvocationTargetException ex) {
      rethrowRuntimeException(ex.getTargetException());
   }

   public static void rethrowRuntimeException(Throwable ex) {
      if (ex instanceof RuntimeException) {
         throw (RuntimeException)ex;
      } else if (ex instanceof Error) {
         throw (Error)ex;
      } else {
         throw new UndeclaredThrowableException(ex);
      }
   }

   public static void rethrowException(Throwable ex) throws Exception {
      if (ex instanceof Exception) {
         throw (Exception)ex;
      } else if (ex instanceof Error) {
         throw (Error)ex;
      } else {
         throw new UndeclaredThrowableException(ex);
      }
   }

   public static Constructor accessibleConstructor(Class clazz, Class... parameterTypes) throws NoSuchMethodException {
      Constructor ctor = clazz.getDeclaredConstructor(parameterTypes);
      makeAccessible(ctor);
      return ctor;
   }

   public static void makeAccessible(Constructor ctor) {
      if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
         ctor.setAccessible(true);
      }

   }

   @Nullable
   public static Method findMethod(Class clazz, String name) {
      return findMethod(clazz, name);
   }

   @Nullable
   public static Method findMethod(Class clazz, String name, @Nullable Class... paramTypes) {
      Assert.notNull(clazz, (String)"Class must not be null");
      Assert.notNull(name, (String)"Method name must not be null");

      for(Class searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
         Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
         Method[] var5 = methods;
         int var6 = methods.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
               return method;
            }
         }
      }

      return null;
   }

   @Nullable
   public static Object invokeMethod(Method method, @Nullable Object target) {
      return invokeMethod(method, target);
   }

   @Nullable
   public static Object invokeMethod(Method method, @Nullable Object target, @Nullable Object... args) {
      try {
         return method.invoke(target, args);
      } catch (Exception var4) {
         handleReflectionException(var4);
         throw new IllegalStateException("Should never get here");
      }
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public static Object invokeJdbcMethod(Method method, @Nullable Object target) throws SQLException {
      return invokeJdbcMethod(method, target);
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public static Object invokeJdbcMethod(Method method, @Nullable Object target, @Nullable Object... args) throws SQLException {
      try {
         return method.invoke(target, args);
      } catch (IllegalAccessException var4) {
         handleReflectionException(var4);
      } catch (InvocationTargetException var5) {
         if (var5.getTargetException() instanceof SQLException) {
            throw (SQLException)var5.getTargetException();
         }

         handleInvocationTargetException(var5);
      }

      throw new IllegalStateException("Should never get here");
   }

   public static boolean declaresException(Method method, Class exceptionType) {
      Assert.notNull(method, (String)"Method must not be null");
      Class[] declaredExceptions = method.getExceptionTypes();
      Class[] var3 = declaredExceptions;
      int var4 = declaredExceptions.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class declaredException = var3[var5];
         if (declaredException.isAssignableFrom(exceptionType)) {
            return true;
         }
      }

      return false;
   }

   public static void doWithLocalMethods(Class clazz, MethodCallback mc) {
      Method[] methods = getDeclaredMethods(clazz);
      Method[] var3 = methods;
      int var4 = methods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method method = var3[var5];

         try {
            mc.doWith(method);
         } catch (IllegalAccessException var8) {
            throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + var8);
         }
      }

   }

   public static void doWithMethods(Class clazz, MethodCallback mc) {
      doWithMethods(clazz, mc, (MethodFilter)null);
   }

   public static void doWithMethods(Class clazz, MethodCallback mc, @Nullable MethodFilter mf) {
      Method[] methods = getDeclaredMethods(clazz);
      Method[] var4 = methods;
      int var5 = methods.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         if (mf == null || mf.matches(method)) {
            try {
               mc.doWith(method);
            } catch (IllegalAccessException var9) {
               throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + var9);
            }
         }
      }

      if (clazz.getSuperclass() != null) {
         doWithMethods(clazz.getSuperclass(), mc, mf);
      } else if (clazz.isInterface()) {
         Class[] var10 = clazz.getInterfaces();
         var5 = var10.length;

         for(var6 = 0; var6 < var5; ++var6) {
            Class superIfc = var10[var6];
            doWithMethods(superIfc, mc, mf);
         }
      }

   }

   public static Method[] getAllDeclaredMethods(Class leafClass) {
      List methods = new ArrayList(32);
      doWithMethods(leafClass, methods::add);
      return (Method[])methods.toArray(EMPTY_METHOD_ARRAY);
   }

   public static Method[] getUniqueDeclaredMethods(Class leafClass) {
      List methods = new ArrayList(32);
      doWithMethods(leafClass, (method) -> {
         boolean knownSignature = false;
         Method methodBeingOverriddenWithCovariantReturnType = null;
         Iterator var4 = methods.iterator();

         while(var4.hasNext()) {
            Method existingMethod = (Method)var4.next();
            if (method.getName().equals(existingMethod.getName()) && Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
               if (existingMethod.getReturnType() != method.getReturnType() && existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                  methodBeingOverriddenWithCovariantReturnType = existingMethod;
                  break;
               }

               knownSignature = true;
               break;
            }
         }

         if (methodBeingOverriddenWithCovariantReturnType != null) {
            methods.remove(methodBeingOverriddenWithCovariantReturnType);
         }

         if (!knownSignature && !isCglibRenamedMethod(method)) {
            methods.add(method);
         }

      });
      return (Method[])methods.toArray(EMPTY_METHOD_ARRAY);
   }

   private static Method[] getDeclaredMethods(Class clazz) {
      Assert.notNull(clazz, (String)"Class must not be null");
      Method[] result = (Method[])declaredMethodsCache.get(clazz);
      if (result == null) {
         try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List defaultMethods = findConcreteMethodsOnInterfaces(clazz);
            if (defaultMethods != null) {
               result = new Method[declaredMethods.length + defaultMethods.size()];
               System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
               int index = declaredMethods.length;

               for(Iterator var5 = defaultMethods.iterator(); var5.hasNext(); ++index) {
                  Method defaultMethod = (Method)var5.next();
                  result[index] = defaultMethod;
               }
            } else {
               result = declaredMethods;
            }

            declaredMethodsCache.put(clazz, result.length == 0 ? EMPTY_METHOD_ARRAY : result);
         } catch (Throwable var7) {
            throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", var7);
         }
      }

      return result;
   }

   @Nullable
   private static List findConcreteMethodsOnInterfaces(Class clazz) {
      List result = null;
      Class[] var2 = clazz.getInterfaces();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class ifc = var2[var4];
         Method[] var6 = ifc.getMethods();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Method ifcMethod = var6[var8];
            if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
               if (result == null) {
                  result = new ArrayList();
               }

               result.add(ifcMethod);
            }
         }
      }

      return result;
   }

   public static boolean isEqualsMethod(@Nullable Method method) {
      if (method != null && method.getName().equals("equals")) {
         Class[] paramTypes = method.getParameterTypes();
         return paramTypes.length == 1 && paramTypes[0] == Object.class;
      } else {
         return false;
      }
   }

   public static boolean isHashCodeMethod(@Nullable Method method) {
      return method != null && method.getName().equals("hashCode") && method.getParameterCount() == 0;
   }

   public static boolean isToStringMethod(@Nullable Method method) {
      return method != null && method.getName().equals("toString") && method.getParameterCount() == 0;
   }

   public static boolean isObjectMethod(@Nullable Method method) {
      return method != null && (method.getDeclaringClass() == Object.class || isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method));
   }

   public static boolean isCglibRenamedMethod(Method renamedMethod) {
      String name = renamedMethod.getName();
      if (!name.startsWith("CGLIB$")) {
         return false;
      } else {
         int i;
         for(i = name.length() - 1; i >= 0 && Character.isDigit(name.charAt(i)); --i) {
         }

         return i > "CGLIB$".length() && i < name.length() - 1 && name.charAt(i) == '$';
      }
   }

   public static void makeAccessible(Method method) {
      if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
         method.setAccessible(true);
      }

   }

   @Nullable
   public static Field findField(Class clazz, String name) {
      return findField(clazz, name, (Class)null);
   }

   @Nullable
   public static Field findField(Class clazz, @Nullable String name, @Nullable Class type) {
      Assert.notNull(clazz, (String)"Class must not be null");
      Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");

      for(Class searchType = clazz; Object.class != searchType && searchType != null; searchType = searchType.getSuperclass()) {
         Field[] fields = getDeclaredFields(searchType);
         Field[] var5 = fields;
         int var6 = fields.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Field field = var5[var7];
            if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
               return field;
            }
         }
      }

      return null;
   }

   public static void setField(Field field, @Nullable Object target, @Nullable Object value) {
      try {
         field.set(target, value);
      } catch (IllegalAccessException var4) {
         handleReflectionException(var4);
         throw new IllegalStateException("Unexpected reflection exception - " + var4.getClass().getName() + ": " + var4.getMessage());
      }
   }

   @Nullable
   public static Object getField(Field field, @Nullable Object target) {
      try {
         return field.get(target);
      } catch (IllegalAccessException var3) {
         handleReflectionException(var3);
         throw new IllegalStateException("Unexpected reflection exception - " + var3.getClass().getName() + ": " + var3.getMessage());
      }
   }

   public static void doWithLocalFields(Class clazz, FieldCallback fc) {
      Field[] var2 = getDeclaredFields(clazz);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];

         try {
            fc.doWith(field);
         } catch (IllegalAccessException var7) {
            throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + var7);
         }
      }

   }

   public static void doWithFields(Class clazz, FieldCallback fc) {
      doWithFields(clazz, fc, (FieldFilter)null);
   }

   public static void doWithFields(Class clazz, FieldCallback fc, @Nullable FieldFilter ff) {
      Class targetClass = clazz;

      do {
         Field[] fields = getDeclaredFields(targetClass);
         Field[] var5 = fields;
         int var6 = fields.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Field field = var5[var7];
            if (ff == null || ff.matches(field)) {
               try {
                  fc.doWith(field);
               } catch (IllegalAccessException var10) {
                  throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + var10);
               }
            }
         }

         targetClass = targetClass.getSuperclass();
      } while(targetClass != null && targetClass != Object.class);

   }

   private static Field[] getDeclaredFields(Class clazz) {
      Assert.notNull(clazz, (String)"Class must not be null");
      Field[] result = (Field[])declaredFieldsCache.get(clazz);
      if (result == null) {
         try {
            result = clazz.getDeclaredFields();
            declaredFieldsCache.put(clazz, result.length == 0 ? EMPTY_FIELD_ARRAY : result);
         } catch (Throwable var3) {
            throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", var3);
         }
      }

      return result;
   }

   public static void shallowCopyFieldState(Object src, Object dest) {
      Assert.notNull(src, "Source for field copy cannot be null");
      Assert.notNull(dest, "Destination for field copy cannot be null");
      if (!src.getClass().isAssignableFrom(dest.getClass())) {
         throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
      } else {
         doWithFields(src.getClass(), (field) -> {
            makeAccessible(field);
            Object srcValue = field.get(src);
            field.set(dest, srcValue);
         }, COPYABLE_FIELDS);
      }
   }

   public static boolean isPublicStaticFinal(Field field) {
      int modifiers = field.getModifiers();
      return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
   }

   public static void makeAccessible(Field field) {
      if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
         field.setAccessible(true);
      }

   }

   public static void clearCache() {
      declaredMethodsCache.clear();
      declaredFieldsCache.clear();
   }

   @FunctionalInterface
   public interface FieldFilter {
      boolean matches(Field var1);
   }

   @FunctionalInterface
   public interface FieldCallback {
      void doWith(Field var1) throws IllegalArgumentException, IllegalAccessException;
   }

   @FunctionalInterface
   public interface MethodFilter {
      boolean matches(Method var1);
   }

   @FunctionalInterface
   public interface MethodCallback {
      void doWith(Method var1) throws IllegalArgumentException, IllegalAccessException;
   }
}
