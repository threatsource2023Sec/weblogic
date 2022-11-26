package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Attribute;
import com.bea.core.repackaged.springframework.asm.Type;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectUtils {
   private static final Map primitives = new HashMap(8);
   private static final Map transforms = new HashMap(8);
   private static final ClassLoader defaultLoader = ReflectUtils.class.getClassLoader();
   private static final Method privateLookupInMethod;
   private static final Method lookupDefineClassMethod;
   private static final Method classLoaderDefineClassMethod;
   private static final ProtectionDomain PROTECTION_DOMAIN;
   private static final Throwable THROWABLE;
   private static final List OBJECT_METHODS = new ArrayList();
   private static final String[] CGLIB_PACKAGES;

   private ReflectUtils() {
   }

   public static ProtectionDomain getProtectionDomain(final Class source) {
      return source == null ? null : (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return source.getProtectionDomain();
         }
      });
   }

   public static Type[] getExceptionTypes(Member member) {
      if (member instanceof Method) {
         return TypeUtils.getTypes(((Method)member).getExceptionTypes());
      } else if (member instanceof Constructor) {
         return TypeUtils.getTypes(((Constructor)member).getExceptionTypes());
      } else {
         throw new IllegalArgumentException("Cannot get exception types of a field");
      }
   }

   public static Signature getSignature(Member member) {
      if (member instanceof Method) {
         return new Signature(member.getName(), Type.getMethodDescriptor((Method)member));
      } else if (member instanceof Constructor) {
         Type[] types = TypeUtils.getTypes(((Constructor)member).getParameterTypes());
         return new Signature("<init>", Type.getMethodDescriptor(Type.VOID_TYPE, types));
      } else {
         throw new IllegalArgumentException("Cannot get signature of a field");
      }
   }

   public static Constructor findConstructor(String desc) {
      return findConstructor(desc, defaultLoader);
   }

   public static Constructor findConstructor(String desc, ClassLoader loader) {
      try {
         int lparen = desc.indexOf(40);
         String className = desc.substring(0, lparen).trim();
         return getClass(className, loader).getConstructor(parseTypes(desc, loader));
      } catch (NoSuchMethodException | ClassNotFoundException var4) {
         throw new CodeGenerationException(var4);
      }
   }

   public static Method findMethod(String desc) {
      return findMethod(desc, defaultLoader);
   }

   public static Method findMethod(String desc, ClassLoader loader) {
      try {
         int lparen = desc.indexOf(40);
         int dot = desc.lastIndexOf(46, lparen);
         String className = desc.substring(0, dot).trim();
         String methodName = desc.substring(dot + 1, lparen).trim();
         return getClass(className, loader).getDeclaredMethod(methodName, parseTypes(desc, loader));
      } catch (NoSuchMethodException | ClassNotFoundException var6) {
         throw new CodeGenerationException(var6);
      }
   }

   private static Class[] parseTypes(String desc, ClassLoader loader) throws ClassNotFoundException {
      int lparen = desc.indexOf(40);
      int rparen = desc.indexOf(41, lparen);
      List params = new ArrayList();
      int start = lparen + 1;

      while(true) {
         int comma = desc.indexOf(44, start);
         if (comma < 0) {
            if (start < rparen) {
               params.add(desc.substring(start, rparen).trim());
            }

            Class[] types = new Class[params.size()];

            for(int i = 0; i < types.length; ++i) {
               types[i] = getClass((String)params.get(i), loader);
            }

            return types;
         }

         params.add(desc.substring(start, comma).trim());
         start = comma + 1;
      }
   }

   private static Class getClass(String className, ClassLoader loader) throws ClassNotFoundException {
      return getClass(className, loader, CGLIB_PACKAGES);
   }

   private static Class getClass(String className, ClassLoader loader, String[] packages) throws ClassNotFoundException {
      String save = className;
      int dimensions = 0;

      for(int index = 0; (index = className.indexOf("[]", index) + 1) > 0; ++dimensions) {
      }

      StringBuffer brackets = new StringBuffer(className.length() - dimensions);

      for(int i = 0; i < dimensions; ++i) {
         brackets.append('[');
      }

      className = className.substring(0, className.length() - 2 * dimensions);
      String prefix = dimensions > 0 ? brackets + "L" : "";
      String suffix = dimensions > 0 ? ";" : "";

      try {
         return Class.forName(prefix + className + suffix, false, loader);
      } catch (ClassNotFoundException var13) {
         int i = 0;

         while(i < packages.length) {
            try {
               return Class.forName(prefix + packages[i] + '.' + className + suffix, false, loader);
            } catch (ClassNotFoundException var12) {
               ++i;
            }
         }

         if (dimensions == 0) {
            Class c = (Class)primitives.get(className);
            if (c != null) {
               return c;
            }
         } else {
            String transform = (String)transforms.get(className);
            if (transform != null) {
               try {
                  return Class.forName(brackets + transform, false, loader);
               } catch (ClassNotFoundException var11) {
               }
            }
         }

         throw new ClassNotFoundException(save);
      }
   }

   public static Object newInstance(Class type) {
      return newInstance(type, Constants.EMPTY_CLASS_ARRAY, (Object[])null);
   }

   public static Object newInstance(Class type, Class[] parameterTypes, Object[] args) {
      return newInstance(getConstructor(type, parameterTypes), args);
   }

   public static Object newInstance(Constructor cstruct, Object[] args) {
      boolean flag = cstruct.isAccessible();

      Object var4;
      try {
         if (!flag) {
            cstruct.setAccessible(true);
         }

         Object result = cstruct.newInstance(args);
         var4 = result;
      } catch (InstantiationException var10) {
         throw new CodeGenerationException(var10);
      } catch (IllegalAccessException var11) {
         throw new CodeGenerationException(var11);
      } catch (InvocationTargetException var12) {
         throw new CodeGenerationException(var12.getTargetException());
      } finally {
         if (!flag) {
            cstruct.setAccessible(flag);
         }

      }

      return var4;
   }

   public static Constructor getConstructor(Class type, Class[] parameterTypes) {
      try {
         Constructor constructor = type.getDeclaredConstructor(parameterTypes);
         constructor.setAccessible(true);
         return constructor;
      } catch (NoSuchMethodException var3) {
         throw new CodeGenerationException(var3);
      }
   }

   public static String[] getNames(Class[] classes) {
      if (classes == null) {
         return null;
      } else {
         String[] names = new String[classes.length];

         for(int i = 0; i < names.length; ++i) {
            names[i] = classes[i].getName();
         }

         return names;
      }
   }

   public static Class[] getClasses(Object[] objects) {
      Class[] classes = new Class[objects.length];

      for(int i = 0; i < objects.length; ++i) {
         classes[i] = objects[i].getClass();
      }

      return classes;
   }

   public static Method findNewInstance(Class iface) {
      Method m = findInterfaceMethod(iface);
      if (!m.getName().equals("newInstance")) {
         throw new IllegalArgumentException(iface + " missing newInstance method");
      } else {
         return m;
      }
   }

   public static Method[] getPropertyMethods(PropertyDescriptor[] properties, boolean read, boolean write) {
      Set methods = new HashSet();

      for(int i = 0; i < properties.length; ++i) {
         PropertyDescriptor pd = properties[i];
         if (read) {
            methods.add(pd.getReadMethod());
         }

         if (write) {
            methods.add(pd.getWriteMethod());
         }
      }

      methods.remove((Object)null);
      return (Method[])((Method[])methods.toArray(new Method[methods.size()]));
   }

   public static PropertyDescriptor[] getBeanProperties(Class type) {
      return getPropertiesHelper(type, true, true);
   }

   public static PropertyDescriptor[] getBeanGetters(Class type) {
      return getPropertiesHelper(type, true, false);
   }

   public static PropertyDescriptor[] getBeanSetters(Class type) {
      return getPropertiesHelper(type, false, true);
   }

   private static PropertyDescriptor[] getPropertiesHelper(Class type, boolean read, boolean write) {
      try {
         BeanInfo info = Introspector.getBeanInfo(type, Object.class);
         PropertyDescriptor[] all = info.getPropertyDescriptors();
         if (read && write) {
            return all;
         } else {
            List properties = new ArrayList(all.length);

            for(int i = 0; i < all.length; ++i) {
               PropertyDescriptor pd = all[i];
               if (read && pd.getReadMethod() != null || write && pd.getWriteMethod() != null) {
                  properties.add(pd);
               }
            }

            return (PropertyDescriptor[])((PropertyDescriptor[])properties.toArray(new PropertyDescriptor[properties.size()]));
         }
      } catch (IntrospectionException var8) {
         throw new CodeGenerationException(var8);
      }
   }

   public static Method findDeclaredMethod(Class type, String methodName, Class[] parameterTypes) throws NoSuchMethodException {
      Class cl = type;

      while(cl != null) {
         try {
            return cl.getDeclaredMethod(methodName, parameterTypes);
         } catch (NoSuchMethodException var5) {
            cl = cl.getSuperclass();
         }
      }

      throw new NoSuchMethodException(methodName);
   }

   public static List addAllMethods(Class type, List list) {
      if (type == Object.class) {
         list.addAll(OBJECT_METHODS);
      } else {
         list.addAll(Arrays.asList(type.getDeclaredMethods()));
      }

      Class superclass = type.getSuperclass();
      if (superclass != null) {
         addAllMethods(superclass, list);
      }

      Class[] interfaces = type.getInterfaces();

      for(int i = 0; i < interfaces.length; ++i) {
         addAllMethods(interfaces[i], list);
      }

      return list;
   }

   public static List addAllInterfaces(Class type, List list) {
      Class superclass = type.getSuperclass();
      if (superclass != null) {
         list.addAll(Arrays.asList(type.getInterfaces()));
         addAllInterfaces(superclass, list);
      }

      return list;
   }

   public static Method findInterfaceMethod(Class iface) {
      if (!iface.isInterface()) {
         throw new IllegalArgumentException(iface + " is not an interface");
      } else {
         Method[] methods = iface.getDeclaredMethods();
         if (methods.length != 1) {
            throw new IllegalArgumentException("expecting exactly 1 method in " + iface);
         } else {
            return methods[0];
         }
      }
   }

   public static Class defineClass(String className, byte[] b, ClassLoader loader) throws Exception {
      return defineClass(className, b, loader, (ProtectionDomain)null, (Class)null);
   }

   public static Class defineClass(String className, byte[] b, ClassLoader loader, ProtectionDomain protectionDomain) throws Exception {
      return defineClass(className, b, loader, protectionDomain, (Class)null);
   }

   public static Class defineClass(String className, byte[] b, ClassLoader loader, ProtectionDomain protectionDomain, Class contextClass) throws Exception {
      Class c = null;
      MethodHandles.Lookup lookup;
      if (contextClass != null && contextClass.getClassLoader() == loader && privateLookupInMethod != null && lookupDefineClassMethod != null) {
         try {
            lookup = (MethodHandles.Lookup)privateLookupInMethod.invoke((Object)null, contextClass, MethodHandles.lookup());
            c = (Class)lookupDefineClassMethod.invoke(lookup, b);
         } catch (InvocationTargetException var12) {
            Throwable target = var12.getTargetException();
            if (target.getClass() != LinkageError.class && target.getClass() != IllegalArgumentException.class) {
               throw new CodeGenerationException(target);
            }
         } catch (Throwable var13) {
            throw new CodeGenerationException(var13);
         }
      }

      if (c == null && classLoaderDefineClassMethod != null) {
         if (protectionDomain == null) {
            protectionDomain = PROTECTION_DOMAIN;
         }

         Object[] args = new Object[]{className, b, 0, b.length, protectionDomain};

         try {
            if (!classLoaderDefineClassMethod.isAccessible()) {
               classLoaderDefineClassMethod.setAccessible(true);
            }

            c = (Class)classLoaderDefineClassMethod.invoke(loader, args);
         } catch (InvocationTargetException var10) {
            throw new CodeGenerationException(var10.getTargetException());
         } catch (Throwable var11) {
            if (!var11.getClass().getName().endsWith("InaccessibleObjectException")) {
               throw new CodeGenerationException(var11);
            }
         }
      }

      if (c == null && contextClass != null && contextClass.getClassLoader() != loader && privateLookupInMethod != null && lookupDefineClassMethod != null) {
         try {
            lookup = (MethodHandles.Lookup)privateLookupInMethod.invoke((Object)null, contextClass, MethodHandles.lookup());
            c = (Class)lookupDefineClassMethod.invoke(lookup, b);
         } catch (InvocationTargetException var8) {
            throw new CodeGenerationException(var8.getTargetException());
         } catch (Throwable var9) {
            throw new CodeGenerationException(var9);
         }
      }

      if (c == null) {
         throw new CodeGenerationException(THROWABLE);
      } else {
         Class.forName(className, true, loader);
         return c;
      }
   }

   public static int findPackageProtected(Class[] classes) {
      for(int i = 0; i < classes.length; ++i) {
         if (!Modifier.isPublic(classes[i].getModifiers())) {
            return i;
         }
      }

      return 0;
   }

   public static MethodInfo getMethodInfo(final Member member, final int modifiers) {
      final Signature sig = getSignature(member);
      return new MethodInfo() {
         private ClassInfo ci;

         public ClassInfo getClassInfo() {
            if (this.ci == null) {
               this.ci = ReflectUtils.getClassInfo(member.getDeclaringClass());
            }

            return this.ci;
         }

         public int getModifiers() {
            return modifiers;
         }

         public Signature getSignature() {
            return sig;
         }

         public Type[] getExceptionTypes() {
            return ReflectUtils.getExceptionTypes(member);
         }

         public Attribute getAttribute() {
            return null;
         }
      };
   }

   public static MethodInfo getMethodInfo(Member member) {
      return getMethodInfo(member, member.getModifiers());
   }

   public static ClassInfo getClassInfo(final Class clazz) {
      final Type type = Type.getType(clazz);
      final Type sc = clazz.getSuperclass() == null ? null : Type.getType(clazz.getSuperclass());
      return new ClassInfo() {
         public Type getType() {
            return type;
         }

         public Type getSuperType() {
            return sc;
         }

         public Type[] getInterfaces() {
            return TypeUtils.getTypes(clazz.getInterfaces());
         }

         public int getModifiers() {
            return clazz.getModifiers();
         }
      };
   }

   public static Method[] findMethods(String[] namesAndDescriptors, Method[] methods) {
      Map map = new HashMap();

      for(int i = 0; i < methods.length; ++i) {
         Method method = methods[i];
         map.put(method.getName() + Type.getMethodDescriptor(method), method);
      }

      Method[] result = new Method[namesAndDescriptors.length / 2];

      for(int i = 0; i < result.length; ++i) {
         result[i] = (Method)map.get(namesAndDescriptors[i * 2] + namesAndDescriptors[i * 2 + 1]);
         if (result[i] == null) {
         }
      }

      return result;
   }

   static {
      Throwable throwable = null;

      Method privateLookupIn;
      Method lookupDefineClass;
      Method classLoaderDefineClass;
      ProtectionDomain protectionDomain;
      try {
         privateLookupIn = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               try {
                  return MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
               } catch (NoSuchMethodException var2) {
                  return null;
               }
            }
         });
         lookupDefineClass = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               try {
                  return MethodHandles.Lookup.class.getMethod("defineClass", byte[].class);
               } catch (NoSuchMethodException var2) {
                  return null;
               }
            }
         });
         classLoaderDefineClass = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
            }
         });
         protectionDomain = getProtectionDomain(ReflectUtils.class);
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               Method[] methods = Object.class.getDeclaredMethods();
               Method[] var2 = methods;
               int var3 = methods.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  Method method = var2[var4];
                  if (!"finalize".equals(method.getName()) && (method.getModifiers() & 24) <= 0) {
                     ReflectUtils.OBJECT_METHODS.add(method);
                  }
               }

               return null;
            }
         });
      } catch (Throwable var6) {
         privateLookupIn = null;
         lookupDefineClass = null;
         classLoaderDefineClass = null;
         protectionDomain = null;
         throwable = var6;
      }

      privateLookupInMethod = privateLookupIn;
      lookupDefineClassMethod = lookupDefineClass;
      classLoaderDefineClassMethod = classLoaderDefineClass;
      PROTECTION_DOMAIN = protectionDomain;
      THROWABLE = throwable;
      CGLIB_PACKAGES = new String[]{"java.lang"};
      primitives.put("byte", Byte.TYPE);
      primitives.put("char", Character.TYPE);
      primitives.put("double", Double.TYPE);
      primitives.put("float", Float.TYPE);
      primitives.put("int", Integer.TYPE);
      primitives.put("long", Long.TYPE);
      primitives.put("short", Short.TYPE);
      primitives.put("boolean", Boolean.TYPE);
      transforms.put("byte", "B");
      transforms.put("char", "C");
      transforms.put("double", "D");
      transforms.put("float", "F");
      transforms.put("int", "I");
      transforms.put("long", "J");
      transforms.put("short", "S");
      transforms.put("boolean", "Z");
   }
}
