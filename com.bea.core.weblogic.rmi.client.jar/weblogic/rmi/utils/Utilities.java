package weblogic.rmi.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.utils.NestedError;
import weblogic.utils.annotation.BeaSynthetic.Helper;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.reflect.MethodSignatureBuilder;
import weblogic.utils.reflect.UniqueMethod;

public final class Utilities {
   public static final char SEPARATOR_CHAR = '/';
   public static final String SEPARATOR = "/";
   private static Class[] stringExceptionExceptionConstructorSig = new Class[2];
   private static Class[] stringExceptionConstructorSig = new Class[1];
   private static Class[] stringStringExceptionConstructorSig = new Class[2];
   private static Class[] stringErrorExceptionConstructorSig = new Class[2];
   private static Class[] stringThrowableExceptionConstructorSig = new Class[2];
   private static Class alreadyBoundEx;
   private static Class noSuchObjectEx;
   private static Class notBoundEx;
   private static Class serverNotActiveEx;
   private static Class skeletonMismatchEx;
   private static Class rmiSecurityEx;
   private static Class serverErr;
   private static Class theOtherRemote;
   private static ClassLoader classLoader = null;

   public static void setClassLoader(ClassLoader cl) {
      classLoader = cl;
   }

   public static ClassLoader getClassLoader() {
      return classLoader;
   }

   public static Throwable theOtherException(RemoteException re) {
      Class mapableClass;
      for(mapableClass = re.getClass(); mapableClass != null && !mapableClass.getName().startsWith("java.rmi"); mapableClass = mapableClass.getSuperclass()) {
      }

      for(; mapableClass != null; mapableClass = mapableClass.getSuperclass()) {
         if (mapableClass.getName().startsWith("java.rmi.")) {
            try {
               String newClassName = "weblogic." + mapableClass.getName().substring("java.".length());
               Class newClass = classForName(newClassName, re.getClass());
               Constructor constructor;
               Object[] parameters;
               if (!newClass.equals(alreadyBoundEx) && !newClass.equals(noSuchObjectEx) && !newClass.equals(notBoundEx) && !newClass.equals(serverNotActiveEx) && !newClass.equals(skeletonMismatchEx)) {
                  if (newClass.equals(rmiSecurityEx)) {
                     constructor = newClass.getConstructor(stringStringExceptionConstructorSig);
                     parameters = new Object[]{"WebLogic RemoteException(" + re.getClass().getName() + ") remapped from:" + re.toString(), null};
                     return (Throwable)constructor.newInstance(parameters);
                  }

                  if (newClass.equals(serverErr)) {
                     constructor = newClass.getConstructor(stringErrorExceptionConstructorSig);
                     parameters = new Object[]{"WebLogic RemoteException(" + re.getClass().getName() + ") remapped from:" + re.toString(), null};
                     if (re.detail instanceof Error) {
                        parameters[1] = re;
                     } else {
                        parameters[1] = new NestedError(re);
                     }

                     return (Throwable)constructor.newInstance(parameters);
                  }

                  constructor = newClass.getConstructor(stringExceptionExceptionConstructorSig);
                  parameters = new Object[]{"WebLogic RemoteException(" + re.getClass().getName() + ") remapped from:" + re.toString(), re};
                  return (Throwable)constructor.newInstance(parameters);
               }

               constructor = newClass.getConstructor(stringExceptionConstructorSig);
               parameters = new Object[]{"WebLogic RemoteException(" + re.getClass().getName() + ") remapped from:" + re.toString()};
               return (Throwable)constructor.newInstance(parameters);
            } catch (ClassNotFoundException var12) {
            } catch (IllegalArgumentException var13) {
            } catch (InvocationTargetException var14) {
            } catch (IllegalAccessException var15) {
            } catch (NoSuchMethodException var16) {
            } catch (InstantiationException var17) {
            }
         }
      }

      try {
         Class newClass = classForName("weblogic.rmi.RemoteException", (Class)null);
         Constructor constructor = newClass.getConstructor(stringThrowableExceptionConstructorSig);
         Object[] parameters = new Object[]{"WebLogic RemoteException(" + re.getClass().getName() + ") remapped from:" + re.toString(), re};
         return (Throwable)constructor.newInstance(parameters);
      } catch (ClassNotFoundException var6) {
      } catch (IllegalArgumentException var7) {
      } catch (InvocationTargetException var8) {
      } catch (IllegalAccessException var9) {
      } catch (NoSuchMethodException var10) {
      } catch (InstantiationException var11) {
      }

      return re;
   }

   public static Class classForName(String className) throws ClassNotFoundException {
      return classForName(className, (Class)null);
   }

   public static Class classForName(String className, Class parent) throws ClassNotFoundException {
      ClassLoader loader = parent != null ? parent.getClassLoader() : null;

      try {
         return loader == null ? Class.forName(className) : loader.loadClass(className);
      } catch (ClassNotFoundException var4) {
         if (classLoader != null) {
            return classLoader.loadClass(className);
         } else {
            throw var4;
         }
      }
   }

   public static boolean isARemote(Class c) {
      boolean result = true;
      if (!Remote.class.equals(c) && !weblogic.rmi.Remote.class.equals(c)) {
         result = Remote.class.isAssignableFrom(c) || org.omg.CORBA.Object.class.isAssignableFrom(c);
      } else {
         result = false;
      }

      return result;
   }

   public static boolean areEquivalent(Method a, Method b) {
      if (a.getName().equals(b.getName())) {
         Class[] aParams = a.getParameterTypes();
         Class[] bParams = b.getParameterTypes();
         if (aParams.length == bParams.length) {
            for(int i = 0; i < aParams.length; ++i) {
               if (aParams[i] != bParams[i]) {
                  return false;
               }
            }

            return true;
         }
      }

      return false;
   }

   public static final Class loadClass(String className, String annotation, String remoteCodebase, ClassLoader loadingContext) throws ClassNotFoundException {
      if (KernelStatus.isApplet()) {
         return Class.forName(className);
      } else {
         if (loadingContext == null && KernelStatus.isServer() && annotation != null) {
            loadingContext = WLRMIClassLoaderDelegate.getInstance().findClassLoader(annotation);
         }

         if (loadingContext == null) {
            loadingContext = Thread.currentThread().getContextClassLoader();
         }

         if (loadingContext == null) {
            loadingContext = Utilities.class.getClassLoader();
         }

         String codebaseURL = null;
         if (remoteCodebase != null) {
            codebaseURL = remoteCodebase;
            if (annotation != null && annotation.length() > 0) {
               codebaseURL = remoteCodebase + annotation;
            }
         } else if (annotation != null && annotation.length() > 0) {
            codebaseURL = annotation;
         }

         GenericClassLoader gcl = AugmentableClassLoaderManager.getAugmentableClassLoader(loadingContext);

         try {
            return WLRMIClassLoaderDelegate.getInstance().loadClass(codebaseURL, (String)className, (ClassLoader)gcl);
         } catch (MalformedURLException var7) {
            throw new ClassNotFoundException("Failed to load class " + className + " from " + remoteCodebase, var7);
         } catch (ClassNotFoundException var8) {
            if (annotation != null && annotation.length() > 0) {
               WLRMIClassLoaderDelegate.getInstance();
               return WLRMIClassLoaderDelegate.loadClass(codebaseURL, annotation, className, gcl);
            } else {
               throw var8;
            }
         }
      }
   }

   public static String getAnnotationString(Object o) {
      ClassLoader cl = o.getClass().getClassLoader();
      return cl instanceof GenericClassLoader ? ((GenericClassLoader)cl).getAnnotation().getAnnotationString() : null;
   }

   public static final Class loadClass(String className, String remoteCodebase, ClassLoader loadingContext) throws ClassNotFoundException {
      return loadClass(className, (String)null, remoteCodebase, loadingContext);
   }

   public static final Class loadClass(String className, String remoteCodebase) throws ClassNotFoundException {
      return loadClass(className, (String)null, remoteCodebase, (ClassLoader)null);
   }

   public static final Class loadClass(String className, String annotation, String remoteCodebase) throws ClassNotFoundException {
      return loadClass(className, annotation, remoteCodebase, (ClassLoader)null);
   }

   public static Method[] getRemoteRMIMethods(Class[] interfaces) {
      Map map = getRemoteMethodsAndSignatures(interfaces, org.omg.CORBA.Object.class);
      Method[] methods = new Method[map.size()];
      return (Method[])map.values().toArray(methods);
   }

   public static Map getRemoteMethodsAndSignatures(Class[] interfaces) {
      return getRemoteMethodsAndSignatures(interfaces, (Class)null);
   }

   public static Map getRemoteMethodsAndSignatures(Class[] interfaces, Class ignoreThis) {
      Set methods = new HashSet();
      Class[] var3 = interfaces;
      int var4 = interfaces.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class c = var3[var5];
         if (c != StubInfoIntf.class && c != ignoreThis) {
            Method[] mArray = c.getMethods();
            Method[] var8 = mArray;
            int var9 = mArray.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Method m = var8[var10];
               if (!Helper.isBeaSyntheticMethod(m) && m.getDeclaringClass() != ignoreThis) {
                  methods.add(UniqueMethod.intern(m));
               }
            }
         }
      }

      Map sigMap = new TreeMap();
      Iterator var13 = methods.iterator();

      while(var13.hasNext()) {
         Method m = (Method)var13.next();
         String signature = MethodSignatureBuilder.compute(m);
         sigMap.put(signature, m);
      }

      return sigMap;
   }

   public static String[] getRemoteInterfaceNames(Class remoteClass) {
      return getRemoteInterfaceNames(getRemoteInterfaces(remoteClass));
   }

   public static String[] getRemoteInterfaceNames(Class[] remoteInterfaces) {
      String[] interfaceNames = new String[remoteInterfaces.length + 1];

      for(int i = 0; i < remoteInterfaces.length; ++i) {
         interfaceNames[i] = remoteInterfaces[i].getName();
      }

      interfaceNames[remoteInterfaces.length] = StubInfoIntf.class.getName();
      return interfaceNames;
   }

   public static Class[] getRemoteInterfaces(Class remoteClass) {
      if (null == remoteClass) {
         return new Class[0];
      } else {
         ArrayList s = new ArrayList();
         if (remoteClass.isInterface()) {
            s.add(remoteClass);
         }

         for(Class c = remoteClass; c != null; c = c.getSuperclass()) {
            addSuperRemoteInterfaces(c, s);
         }

         return (Class[])((Class[])s.toArray(new Class[s.size()]));
      }
   }

   private static void addSuperRemoteInterfaces(Class c, ArrayList s) {
      Class[] is = c.getInterfaces();

      for(int i = 0; i < is.length; ++i) {
         if (isARemote(is[i])) {
            if (!s.contains(is[i])) {
               s.add(is[i]);
            }

            addSuperRemoteInterfaces(is[i], s);
         }
      }

   }

   static {
      stringExceptionExceptionConstructorSig[0] = String.class;
      stringExceptionExceptionConstructorSig[1] = Exception.class;
      stringExceptionConstructorSig[0] = String.class;
      stringStringExceptionConstructorSig[0] = String.class;
      stringStringExceptionConstructorSig[1] = String.class;
      stringErrorExceptionConstructorSig[0] = String.class;
      stringErrorExceptionConstructorSig[1] = Error.class;
      stringThrowableExceptionConstructorSig[0] = String.class;
      stringThrowableExceptionConstructorSig[1] = Throwable.class;

      try {
         theOtherRemote = Class.forName("weblogic.rmi.Remote");
      } catch (Exception var8) {
         throw new Error(var8);
      }

      try {
         alreadyBoundEx = Class.forName("weblogic.rmi.AlreadyBoundException");
      } catch (Exception var7) {
      }

      try {
         noSuchObjectEx = Class.forName("weblogic.rmi.NoSuchObjectException");
      } catch (Exception var6) {
      }

      try {
         notBoundEx = Class.forName("weblogic.rmi.NotBoundException");
      } catch (Exception var5) {
      }

      try {
         serverNotActiveEx = Class.forName("weblogic.rmi.server.ServerNotActiveException");
      } catch (Exception var4) {
      }

      try {
         skeletonMismatchEx = Class.forName("weblogic.rmi.server.SkeletonMismatchException");
      } catch (Exception var3) {
      }

      try {
         rmiSecurityEx = Class.forName("weblogic.rmi.RMISecurityException");
      } catch (Exception var2) {
      }

      try {
         serverErr = Class.forName("weblogic.rmi.ServerError");
      } catch (Exception var1) {
      }

   }
}
