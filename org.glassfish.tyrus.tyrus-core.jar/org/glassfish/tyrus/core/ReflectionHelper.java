package org.glassfish.tyrus.core;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class ReflectionHelper {
   public static Class getDeclaringClass(AccessibleObject ao) {
      if (ao instanceof Method) {
         return ((Method)ao).getDeclaringClass();
      } else if (ao instanceof Field) {
         return ((Field)ao).getDeclaringClass();
      } else if (ao instanceof Constructor) {
         return ((Constructor)ao).getDeclaringClass();
      } else {
         throw new RuntimeException();
      }
   }

   public static String objectToString(Object o) {
      if (o == null) {
         return "null";
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(o.hashCode()));
         return sb.toString();
      }
   }

   public static String methodInstanceToString(Object o, Method m) {
      StringBuilder sb = new StringBuilder();
      sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(o.hashCode())).append('.').append(m.getName()).append('(');
      Class[] params = m.getParameterTypes();

      for(int i = 0; i < params.length; ++i) {
         sb.append(getTypeName(params[i]));
         if (i < params.length - 1) {
            sb.append(",");
         }
      }

      sb.append(')');
      return sb.toString();
   }

   private static String getTypeName(Class type) {
      if (type.isArray()) {
         try {
            Class cl = type;

            int dimensions;
            for(dimensions = 0; cl.isArray(); cl = cl.getComponentType()) {
               ++dimensions;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(cl.getName());

            for(int i = 0; i < dimensions; ++i) {
               sb.append("[]");
            }

            return sb.toString();
         } catch (Throwable var5) {
         }
      }

      return type.getName();
   }

   public static Class classForName(String name) {
      return classForName(name, getContextClassLoader());
   }

   public static Class classForName(String name, ClassLoader cl) {
      if (cl != null) {
         try {
            return Class.forName(name, false, cl);
         } catch (ClassNotFoundException var4) {
         }
      }

      try {
         return Class.forName(name);
      } catch (ClassNotFoundException var3) {
         return null;
      }
   }

   public static Class classForNameWithException(String name) throws ClassNotFoundException {
      return classForNameWithException(name, getContextClassLoader());
   }

   public static Class classForNameWithException(String name, ClassLoader cl) throws ClassNotFoundException {
      if (cl != null) {
         try {
            return Class.forName(name, false, cl);
         } catch (ClassNotFoundException var3) {
         }
      }

      return Class.forName(name);
   }

   public static PrivilegedExceptionAction classForNameWithExceptionPEA(String name) throws ClassNotFoundException {
      return classForNameWithExceptionPEA(name, getContextClassLoader());
   }

   public static PrivilegedExceptionAction classForNameWithExceptionPEA(final String name, final ClassLoader cl) throws ClassNotFoundException {
      return new PrivilegedExceptionAction() {
         public Class run() throws ClassNotFoundException {
            if (cl != null) {
               try {
                  return Class.forName(name, false, cl);
               } catch (ClassNotFoundException var2) {
               }
            }

            return Class.forName(name);
         }
      };
   }

   private static ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(getContextClassLoaderPA());
   }

   public static PrivilegedAction getContextClassLoaderPA() {
      return new PrivilegedAction() {
         public ClassLoader run() {
            return Thread.currentThread().getContextClassLoader();
         }
      };
   }

   public static void setAccessibleMethod(final Method m) {
      if (!Modifier.isPublic(m.getModifiers())) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               if (!m.isAccessible()) {
                  m.setAccessible(true);
               }

               return m;
            }
         });
      }
   }

   public static Class getGenericClass(Type parameterizedType) throws IllegalArgumentException {
      Type t = getTypeArgumentOfParameterizedType(parameterizedType);
      if (t == null) {
         return null;
      } else {
         Class c = getClassOfType(t);
         if (c == null) {
            throw new IllegalArgumentException("Type not supported");
         } else {
            return c;
         }
      }
   }

   public static TypeClassPair getTypeArgumentAndClass(Type parameterizedType) throws IllegalArgumentException {
      Type t = getTypeArgumentOfParameterizedType(parameterizedType);
      if (t == null) {
         return null;
      } else {
         Class c = getClassOfType(t);
         if (c == null) {
            throw new IllegalArgumentException("Generic type not supported");
         } else {
            return new TypeClassPair(t, c);
         }
      }
   }

   private static Type getTypeArgumentOfParameterizedType(Type parameterizedType) {
      if (!(parameterizedType instanceof ParameterizedType)) {
         return null;
      } else {
         ParameterizedType type = (ParameterizedType)parameterizedType;
         Type[] genericTypes = type.getActualTypeArguments();
         return genericTypes.length != 1 ? null : genericTypes[0];
      }
   }

   private static Class getClassOfType(Type type) {
      if (type instanceof Class) {
         return (Class)type;
      } else {
         Type t;
         if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)type;
            t = arrayType.getGenericComponentType();
            if (t instanceof Class) {
               return getArrayClass((Class)t);
            }
         } else if (type instanceof ParameterizedType) {
            ParameterizedType subType = (ParameterizedType)type;
            t = subType.getRawType();
            if (t instanceof Class) {
               return (Class)t;
            }
         }

         return null;
      }
   }

   public static Class getArrayClass(Class c) {
      try {
         Object o = Array.newInstance(c, 0);
         return o.getClass();
      } catch (Exception var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   public static Method getValueOfStringMethod(Class c) {
      try {
         Method m = c.getDeclaredMethod("valueOf", String.class);
         return !Modifier.isStatic(m.getModifiers()) && m.getReturnType() == c ? null : m;
      } catch (NoSuchMethodException var2) {
         return null;
      }
   }

   public static Method getFromStringStringMethod(Class c) {
      try {
         Method m = c.getDeclaredMethod("fromString", String.class);
         return !Modifier.isStatic(m.getModifiers()) && m.getReturnType() == c ? null : m;
      } catch (NoSuchMethodException var2) {
         return null;
      }
   }

   public static Constructor getStringConstructor(Class c) {
      try {
         return c.getConstructor(String.class);
      } catch (Exception var2) {
         return null;
      }
   }

   public static Class[] getParameterizedClassArguments(DeclaringClassInterfacePair p) {
      if (p.genericInterface instanceof ParameterizedType) {
         ParameterizedType pt = (ParameterizedType)p.genericInterface;
         Type[] as = pt.getActualTypeArguments();
         Class[] cas = new Class[as.length];

         for(int i = 0; i < as.length; ++i) {
            Type a = as[i];
            if (a instanceof Class) {
               cas[i] = (Class)a;
            } else if (a instanceof ParameterizedType) {
               pt = (ParameterizedType)a;
               cas[i] = (Class)pt.getRawType();
            } else if (a instanceof TypeVariable) {
               ClassTypePair ctp = resolveTypeVariable(p.concreteClass, p.declaringClass, (TypeVariable)a);
               cas[i] = ctp != null ? ctp.c : Object.class;
            } else if (a instanceof GenericArrayType) {
               cas[i] = getClassOfType(a);
            }
         }

         return cas;
      } else {
         return null;
      }
   }

   public static Type[] getParameterizedTypeArguments(DeclaringClassInterfacePair p) {
      if (p.genericInterface instanceof ParameterizedType) {
         ParameterizedType pt = (ParameterizedType)p.genericInterface;
         Type[] as = pt.getActualTypeArguments();
         Type[] ras = new Type[as.length];

         for(int i = 0; i < as.length; ++i) {
            Type a = as[i];
            if (a instanceof Class) {
               ras[i] = a;
            } else if (a instanceof ParameterizedType) {
               pt = (ParameterizedType)a;
               ras[i] = a;
            } else if (a instanceof TypeVariable) {
               ClassTypePair ctp = resolveTypeVariable(p.concreteClass, p.declaringClass, (TypeVariable)a);
               ras[i] = ctp.t;
            }
         }

         return ras;
      } else {
         return null;
      }
   }

   public static DeclaringClassInterfacePair getClass(Class concrete, Class iface) {
      return getClass(concrete, iface, concrete);
   }

   private static DeclaringClassInterfacePair getClass(Class concrete, Class iface, Class c) {
      Type[] gis = c.getGenericInterfaces();
      DeclaringClassInterfacePair p = getType(concrete, iface, c, gis);
      if (p != null) {
         return p;
      } else {
         c = c.getSuperclass();
         return c != null && c != Object.class ? getClass(concrete, iface, c) : null;
      }
   }

   private static DeclaringClassInterfacePair getType(Class concrete, Class iface, Class c, Type[] ts) {
      Type[] var4 = ts;
      int var5 = ts.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Type t = var4[var6];
         DeclaringClassInterfacePair p = getType(concrete, iface, c, t);
         if (p != null) {
            return p;
         }
      }

      return null;
   }

   private static DeclaringClassInterfacePair getType(Class concrete, Class iface, Class c, Type t) {
      if (t instanceof Class) {
         return t == iface ? new DeclaringClassInterfacePair(concrete, c, t) : getClass(concrete, iface, (Class)t);
      } else if (t instanceof ParameterizedType) {
         ParameterizedType pt = (ParameterizedType)t;
         return pt.getRawType() == iface ? new DeclaringClassInterfacePair(concrete, c, t) : getClass(concrete, iface, (Class)pt.getRawType());
      } else {
         return null;
      }
   }

   public static ClassTypePair resolveTypeVariable(Class c, Class dc, TypeVariable tv) {
      return resolveTypeVariable(c, dc, tv, new HashMap());
   }

   private static ClassTypePair resolveTypeVariable(Class c, Class dc, TypeVariable tv, Map map) {
      Type[] gis = c.getGenericInterfaces();
      Type[] var5 = gis;
      int var6 = gis.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Type gi = var5[var7];
         if (gi instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)gi;
            ClassTypePair ctp = resolveTypeVariable(pt, (Class)pt.getRawType(), dc, tv, map);
            if (ctp != null) {
               return ctp;
            }
         }
      }

      Type gsc = c.getGenericSuperclass();
      if (gsc instanceof ParameterizedType) {
         ParameterizedType pt = (ParameterizedType)gsc;
         return resolveTypeVariable(pt, c.getSuperclass(), dc, tv, map);
      } else if (gsc instanceof Class) {
         return resolveTypeVariable(c.getSuperclass(), dc, tv, map);
      } else {
         return null;
      }
   }

   private static ClassTypePair resolveTypeVariable(ParameterizedType pt, Class c, Class dc, TypeVariable tv, Map map) {
      Type[] typeArguments = pt.getActualTypeArguments();
      TypeVariable[] typeParameters = c.getTypeParameters();
      Map submap = new HashMap();

      Type rt;
      for(int i = 0; i < typeArguments.length; ++i) {
         if (typeArguments[i] instanceof TypeVariable) {
            rt = (Type)map.get(typeArguments[i]);
            submap.put(typeParameters[i], rt);
         } else {
            submap.put(typeParameters[i], typeArguments[i]);
         }
      }

      if (c == dc) {
         Type t = (Type)submap.get(tv);
         if (t instanceof Class) {
            return new ClassTypePair((Class)t);
         } else if (t instanceof GenericArrayType) {
            t = ((GenericArrayType)t).getGenericComponentType();
            if (t instanceof Class) {
               c = (Class)t;

               try {
                  return new ClassTypePair(getArrayClass(c));
               } catch (Exception var11) {
                  return null;
               }
            } else if (t instanceof ParameterizedType) {
               rt = ((ParameterizedType)t).getRawType();
               if (rt instanceof Class) {
                  c = (Class)rt;

                  try {
                     return new ClassTypePair(getArrayClass(c), t);
                  } catch (Exception var12) {
                     return null;
                  }
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else if (t instanceof ParameterizedType) {
            pt = (ParameterizedType)t;
            if (pt.getRawType() instanceof Class) {
               return new ClassTypePair((Class)pt.getRawType(), pt);
            } else {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return resolveTypeVariable(c, dc, tv, submap);
      }
   }

   public static Method findMethodOnClass(Class c, Method m) {
      try {
         return c.getMethod(m.getName(), m.getParameterTypes());
      } catch (NoSuchMethodException var7) {
         Method[] var3 = c.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method _m = var3[var5];
            if (_m.getName().equals(m.getName()) && _m.getParameterTypes().length == m.getParameterTypes().length && compareParameterTypes(m.getGenericParameterTypes(), _m.getGenericParameterTypes())) {
               return _m;
            }
         }

         return null;
      }
   }

   private static boolean compareParameterTypes(Type[] ts, Type[] _ts) {
      for(int i = 0; i < ts.length; ++i) {
         if (!ts[i].equals(_ts[i]) && !(_ts[i] instanceof TypeVariable)) {
            return false;
         }
      }

      return true;
   }

   public static Class getClassType(Class inspectedClass, Class superClass) {
      DeclaringClassInterfacePair p = getClass(inspectedClass, superClass);
      Class[] as = getParameterizedClassArguments(p);
      return as == null ? null : as[0];
   }

   public static OsgiRegistry getOsgiRegistryInstance() {
      try {
         Class bundleReferenceClass = Class.forName("org.osgi.framework.BundleReference");
         if (bundleReferenceClass != null) {
            return OsgiRegistry.getInstance();
         }
      } catch (Exception var1) {
      }

      return null;
   }

   public static Object getInstance(Class c, ErrorCollector collector) {
      Object instance = null;

      try {
         instance = getInstance(c);
      } catch (Exception var4) {
         collector.addException(new DeploymentException(LocalizationMessages.CLASS_NOT_INSTANTIATED(c.getName()), var4));
      }

      return instance;
   }

   public static Object getInstance(Class c) throws IllegalAccessException, InstantiationException {
      return c.newInstance();
   }

   public static class ClassTypePair {
      public final Class c;
      public final Type t;

      public ClassTypePair(Class c) {
         this(c, c);
      }

      public ClassTypePair(Class c, Type t) {
         this.c = c;
         this.t = t;
      }
   }

   public static class DeclaringClassInterfacePair {
      public final Class concreteClass;
      public final Class declaringClass;
      public final Type genericInterface;

      private DeclaringClassInterfacePair(Class concreteClass, Class declaringClass, Type genericInteface) {
         this.concreteClass = concreteClass;
         this.declaringClass = declaringClass;
         this.genericInterface = genericInteface;
      }

      // $FF: synthetic method
      DeclaringClassInterfacePair(Class x0, Class x1, Type x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   public static final class TypeClassPair {
      public final Type t;
      public final Class c;

      public TypeClassPair(Type t, Class c) {
         this.t = t;
         this.c = c;
      }
   }
}
