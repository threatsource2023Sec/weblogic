package com.bea.core.repackaged.aspectj.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;

public class Reflection {
   public static final Class[] MAIN_PARM_TYPES = new Class[]{String[].class};

   private Reflection() {
   }

   public static Object invokestaticN(Class class_, String name, Object[] args) {
      return invokeN(class_, name, (Object)null, args);
   }

   public static Object invoke(Class class_, Object target, String name, Object arg1, Object arg2) {
      return invokeN(class_, name, target, new Object[]{arg1, arg2});
   }

   public static Object invoke(Class class_, Object target, String name, Object arg1, Object arg2, Object arg3) {
      return invokeN(class_, name, target, new Object[]{arg1, arg2, arg3});
   }

   public static Object invokeN(Class class_, String name, Object target, Object[] args) {
      Method meth = getMatchingMethod(class_, name, args);

      try {
         return meth.invoke(target, args);
      } catch (IllegalAccessException var7) {
         throw new RuntimeException(var7.toString());
      } catch (InvocationTargetException var8) {
         Throwable t = var8.getTargetException();
         if (t instanceof Error) {
            throw (Error)t;
         } else if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
         } else {
            t.printStackTrace();
            throw new RuntimeException(t.toString());
         }
      }
   }

   public static Method getMatchingMethod(Class class_, String name, Object[] args) {
      Method[] meths = class_.getMethods();

      for(int i = 0; i < meths.length; ++i) {
         Method meth = meths[i];
         if (meth.getName().equals(name) && isCompatible(meth, args)) {
            return meth;
         }
      }

      return null;
   }

   private static boolean isCompatible(Method meth, Object[] args) {
      return meth.getParameterTypes().length == args.length;
   }

   public static Object getStaticField(Class class_, String name) {
      try {
         return class_.getField(name).get((Object)null);
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("unimplemented");
      } catch (NoSuchFieldException var4) {
         throw new RuntimeException("unimplemented");
      }
   }

   public static void runMainInSameVM(String classpath, String className, String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
      LangUtil.throwIaxIfNull(className, "class name");
      if (LangUtil.isEmpty(classpath)) {
         Class mainClass = Class.forName(className);
         runMainInSameVM(mainClass, args);
      } else {
         ArrayList dirs = new ArrayList();
         ArrayList libs = new ArrayList();
         ArrayList urls = new ArrayList();
         String[] entries = LangUtil.splitClasspath(classpath);

         for(int i = 0; i < entries.length; ++i) {
            String entry = entries[i];
            URL url = makeURL(entry);
            if (null != url) {
               urls.add(url);
            }

            File file = new File(entries[i]);
            if (FileUtil.isZipFile(file)) {
               libs.add(file);
            } else if (file.isDirectory()) {
               dirs.add(file);
            }
         }

         File[] dirRa = (File[])((File[])dirs.toArray(new File[0]));
         File[] libRa = (File[])((File[])libs.toArray(new File[0]));
         URL[] urlRa = (URL[])((URL[])urls.toArray(new URL[0]));
         runMainInSameVM(urlRa, libRa, dirRa, className, args);
      }
   }

   public static void runMainInSameVM(URL[] urls, File[] libs, File[] dirs, String className, String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
      LangUtil.throwIaxIfNull(className, "class name");
      LangUtil.throwIaxIfNotAssignable((Object[])libs, File.class, "jars");
      LangUtil.throwIaxIfNotAssignable((Object[])dirs, File.class, "dirs");
      URL[] libUrls = FileUtil.getFileURLs(libs);
      if (!LangUtil.isEmpty((Object[])libUrls)) {
         if (!LangUtil.isEmpty((Object[])urls)) {
            URL[] temp = new URL[libUrls.length + urls.length];
            System.arraycopy(urls, 0, temp, 0, urls.length);
            System.arraycopy(urls, 0, temp, libUrls.length, urls.length);
            urls = temp;
         } else {
            urls = libUrls;
         }
      }

      UtilClassLoader loader = new UtilClassLoader(urls, dirs);
      Class targetClass = null;

      try {
         targetClass = loader.loadClass(className);
      } catch (ClassNotFoundException var10) {
         String s = "unable to load class " + className + " using class loader " + loader;
         throw new ClassNotFoundException(s);
      }

      Method main = targetClass.getMethod("main", MAIN_PARM_TYPES);
      main.invoke((Object)null, args);
   }

   public static void runMainInSameVM(Class mainClass, String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      LangUtil.throwIaxIfNull(mainClass, "main class");
      Method main = mainClass.getMethod("main", MAIN_PARM_TYPES);
      main.invoke((Object)null, args);
   }

   private static URL makeURL(String s) {
      try {
         return new URL(s);
      } catch (Throwable var2) {
         return null;
      }
   }
}
