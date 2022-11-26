package org.jboss.weld.util.reflection;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.Types;

public class Reflections {
   public static final Type[] EMPTY_TYPES = new Type[0];
   public static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
   public static final Class[] EMPTY_CLASSES = new Class[0];

   private Reflections() {
   }

   public static Map buildTypeMap(Set types) {
      Map map = new HashMap();
      Iterator var2 = types.iterator();

      while(var2.hasNext()) {
         Type type = (Type)var2.next();
         Class clazz = getRawType(type);
         if (clazz != null) {
            map.put(clazz, type);
         }
      }

      return map;
   }

   public static boolean isCacheable(Collection annotations) {
      Iterator var1 = annotations.iterator();

      Class clazz;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         Annotation qualifier = (Annotation)var1.next();
         clazz = qualifier.getClass();
      } while(isTopLevelOrStaticNestedClass(clazz));

      return false;
   }

   public static boolean isCacheable(Annotation[] annotations) {
      Annotation[] var1 = annotations;
      int var2 = annotations.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Annotation qualifier = var1[var3];
         Class clazz = qualifier.getClass();
         if (!isTopLevelOrStaticNestedClass(clazz)) {
            return false;
         }
      }

      return true;
   }

   public static Object cast(Object obj) {
      return obj;
   }

   public static String getPropertyName(Method method) {
      String methodName = method.getName();
      String get = "get";
      String is = "is";
      if (methodName.startsWith("get")) {
         return decapitalize(methodName.substring("get".length()));
      } else {
         return methodName.startsWith("is") ? decapitalize(methodName.substring("is".length())) : null;
      }
   }

   public static boolean isFinal(Class clazz) {
      return Modifier.isFinal(clazz.getModifiers());
   }

   public static int getNesting(Class clazz) {
      return clazz.isMemberClass() && !isStatic(clazz) ? 1 + getNesting(clazz.getDeclaringClass()) : 0;
   }

   public static boolean isFinal(Member member) {
      return Modifier.isFinal(member.getModifiers());
   }

   public static boolean isPrivate(Member member) {
      return Modifier.isPrivate(member.getModifiers());
   }

   public static boolean isTypeOrAnyMethodFinal(Class type) {
      return isFinal(type) || getNonPrivateNonStaticFinalMethod(type) != null;
   }

   public static Method getNonPrivateNonStaticFinalMethod(Class type) {
      for(Class clazz = type; clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
         Method[] var2 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(clazz));
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            if (isFinal((Member)method) && !isPrivate(method) && !isStatic((Member)method)) {
               return method;
            }
         }
      }

      return null;
   }

   public static boolean isPackagePrivate(int mod) {
      return !Modifier.isPrivate(mod) && !Modifier.isProtected(mod) && !Modifier.isPublic(mod);
   }

   public static boolean isStatic(Class type) {
      return Modifier.isStatic(type.getModifiers());
   }

   public static boolean isStatic(Member member) {
      return Modifier.isStatic(member.getModifiers());
   }

   public static boolean isTransient(Member member) {
      return Modifier.isTransient(member.getModifiers());
   }

   public static boolean isAbstract(Method method) {
      return Modifier.isAbstract(method.getModifiers());
   }

   public static boolean isAbstract(Class clazz) {
      return Modifier.isAbstract(clazz.getModifiers());
   }

   public static Type[] getActualTypeArguments(Class clazz) {
      Type type = Types.getCanonicalType(clazz);
      return type instanceof ParameterizedType ? ((ParameterizedType)type).getActualTypeArguments() : EMPTY_TYPES;
   }

   public static Type[] getActualTypeArguments(Type type) {
      Type resolvedType = Types.getCanonicalType(type);
      return resolvedType instanceof ParameterizedType ? ((ParameterizedType)resolvedType).getActualTypeArguments() : EMPTY_TYPES;
   }

   public static boolean isArrayType(Class rawType) {
      return rawType.isArray();
   }

   public static boolean isParameterizedType(Class type) {
      return type.getTypeParameters().length > 0;
   }

   public static boolean isParameterizedTypeWithWildcard(Class type) {
      return isParameterizedType(type) && containsWildcards(type.getTypeParameters());
   }

   public static boolean containsWildcards(Type[] types) {
      Type[] var1 = types;
      int var2 = types.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type type = var1[var3];
         if (type instanceof WildcardType) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSerializable(Class clazz) {
      return clazz.isPrimitive() || Serializable.class.isAssignableFrom(clazz);
   }

   public static boolean isPrimitive(Type type) {
      Class rawType = getRawType(type);
      return rawType != null && rawType.isPrimitive();
   }

   public static Class getRawType(Type type) {
      if (type instanceof Class) {
         return (Class)type;
      } else if (type instanceof ParameterizedType && ((ParameterizedType)type).getRawType() instanceof Class) {
         return (Class)((ParameterizedType)type).getRawType();
      } else if (type instanceof TypeVariable) {
         TypeVariable variable = (TypeVariable)type;
         Type[] bounds = variable.getBounds();
         return getBound(bounds);
      } else if (type instanceof WildcardType) {
         WildcardType wildcard = (WildcardType)type;
         return getBound(wildcard.getUpperBounds());
      } else {
         if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)type;
            Class rawType = getRawType(genericArrayType.getGenericComponentType());
            if (rawType != null) {
               return Array.newInstance(rawType, 0).getClass();
            }
         }

         return null;
      }
   }

   private static Class getBound(Type[] bounds) {
      return bounds.length == 0 ? Object.class : getRawType(bounds[0]);
   }

   public static boolean isClassLoadable(String className, ResourceLoader resourceLoader) {
      return loadClass(className, resourceLoader) != null;
   }

   public static Class loadClass(String className, ResourceLoader resourceLoader) {
      try {
         return (Class)cast(resourceLoader.classForName(className));
      } catch (ResourceLoadingException var3) {
         return null;
      } catch (SecurityException var4) {
         return null;
      }
   }

   public static boolean isUnboundedWildcard(Type type) {
      if (!(type instanceof WildcardType)) {
         return false;
      } else {
         WildcardType wildcard = (WildcardType)type;
         return isEmptyBoundArray(wildcard.getUpperBounds()) && isEmptyBoundArray(wildcard.getLowerBounds());
      }
   }

   public static boolean isUnboundedTypeVariable(Type type) {
      if (type instanceof TypeVariable) {
         TypeVariable typeVariable = (TypeVariable)type;
         return isEmptyBoundArray(typeVariable.getBounds());
      } else {
         return false;
      }
   }

   static boolean isEmptyBoundArray(Type[] bounds) {
      return bounds == null || bounds.length == 0 || bounds.length == 1 && Object.class.equals(bounds[0]);
   }

   public static boolean isStaticNestedClass(Class javaClass) {
      if (javaClass.getEnclosingConstructor() == null && javaClass.getEnclosingMethod() == null) {
         if (javaClass.getEnclosingClass() != null) {
            return javaClass.isAnonymousClass() ? false : isStatic(javaClass);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean isTopLevelOrStaticNestedClass(Class javaClass) {
      return javaClass.getEnclosingClass() == null || isStaticNestedClass(javaClass);
   }

   public static Object invokeAndUnwrap(Object instance, Method method, Object... parameters) throws Throwable {
      try {
         return cast(method.invoke(instance, parameters));
      } catch (IllegalArgumentException var4) {
         throw ReflectionLogger.LOG.illegalArgumentExceptionOnReflectionInvocation(instance.getClass(), instance, method, Arrays.toString(parameters), var4);
      } catch (IllegalAccessException var5) {
         throw new WeldException(var5);
      } catch (InvocationTargetException var6) {
         throw var6.getCause();
      }
   }

   public static void checkDeclaringClassLoadable(Class c) {
      for(Class clazz = c; clazz != null; clazz = clazz.getDeclaringClass()) {
      }

   }

   public static Method findDeclaredMethodByName(Class clazz, String methodName) {
      Method[] var2 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(clazz));
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         if (methodName.equals(method.getName())) {
            return method;
         }
      }

      return null;
   }

   public static Exception unwrapInvocationTargetException(InvocationTargetException e) throws Exception {
      Throwable cause = e.getCause();
      if (cause instanceof Error) {
         throw (Error)cause;
      } else if (cause instanceof Exception) {
         throw (Exception)cause;
      } else {
         throw new WeldException(cause);
      }
   }

   public static Set getInterfaceClosure(Class clazz) {
      Set result = new HashSet();

      for(Class classToDiscover = clazz; classToDiscover != null; classToDiscover = classToDiscover.getSuperclass()) {
         addInterfaces(classToDiscover, result);
      }

      return result;
   }

   private static void addInterfaces(Class clazz, Set result) {
      Class[] interfaces = clazz.getInterfaces();
      if (interfaces.length != 0) {
         Collections.addAll(result, interfaces);
         Class[] var3 = interfaces;
         int var4 = interfaces.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class interfac = var3[var5];
            addInterfaces(interfac, result);
         }

      }
   }

   public static boolean isDefault(Method method) {
      return (method.getModifiers() & 1033) == 1 && method.getDeclaringClass().isInterface();
   }

   public static String decapitalize(String name) {
      if (name != null && name.length() != 0) {
         if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
            return name;
         } else {
            char[] chars = name.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
         }
      } else {
         return name;
      }
   }
}
