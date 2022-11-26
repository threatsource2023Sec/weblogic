package org.python.icu.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassLoaderUtil {
   private static volatile ClassLoader BOOTSTRAP_CLASSLOADER;

   private static ClassLoader getBootstrapClassLoader() {
      if (BOOTSTRAP_CLASSLOADER == null) {
         Class var0 = ClassLoaderUtil.class;
         synchronized(ClassLoaderUtil.class) {
            if (BOOTSTRAP_CLASSLOADER == null) {
               ClassLoader cl = null;
               if (System.getSecurityManager() != null) {
                  cl = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
                     public BootstrapClassLoader run() {
                        return new BootstrapClassLoader();
                     }
                  });
               } else {
                  cl = new BootstrapClassLoader();
               }

               BOOTSTRAP_CLASSLOADER = (ClassLoader)cl;
            }
         }
      }

      return BOOTSTRAP_CLASSLOADER;
   }

   public static ClassLoader getClassLoader(Class cls) {
      ClassLoader cl = cls.getClassLoader();
      if (cl == null) {
         cl = getClassLoader();
      }

      return cl;
   }

   public static ClassLoader getClassLoader() {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = ClassLoader.getSystemClassLoader();
         if (cl == null) {
            cl = getBootstrapClassLoader();
         }
      }

      return cl;
   }

   private static class BootstrapClassLoader extends ClassLoader {
      BootstrapClassLoader() {
         super(Object.class.getClassLoader());
      }
   }
}
