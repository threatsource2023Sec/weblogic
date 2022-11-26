package org.mozilla.classfile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefiningClassLoader extends ClassLoader {
   private static Method getContextClassLoaderMethod;
   // $FF: synthetic field
   static Class class$org$mozilla$classfile$DefiningClassLoader;

   static {
      try {
         Class var0 = Class.forName("java.lang.Thread");
         getContextClassLoaderMethod = var0.getDeclaredMethod("getContextClassLoader");
      } catch (ClassNotFoundException var1) {
      } catch (NoSuchMethodException var2) {
      } catch (SecurityException var3) {
      }

   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public Class defineClass(String var1, byte[] var2) {
      return super.defineClass(var1, var2, 0, var2.length);
   }

   public static ClassLoader getContextClassLoader() {
      try {
         if (getContextClassLoaderMethod != null) {
            return (ClassLoader)getContextClassLoaderMethod.invoke(Thread.currentThread());
         }
      } catch (IllegalAccessException var0) {
      } catch (InvocationTargetException var1) {
      }

      return (class$org$mozilla$classfile$DefiningClassLoader != null ? class$org$mozilla$classfile$DefiningClassLoader : (class$org$mozilla$classfile$DefiningClassLoader = class$("org.mozilla.classfile.DefiningClassLoader"))).getClassLoader();
   }

   public Class loadClass(String var1, boolean var2) throws ClassNotFoundException {
      Class var3 = this.findLoadedClass(var1);
      if (var3 == null) {
         ClassLoader var4 = getContextClassLoader();
         if (var4 != null) {
            var3 = var4.loadClass(var1);
         } else {
            var3 = this.findSystemClass(var1);
         }
      }

      if (var2) {
         this.resolveClass(var3);
      }

      return var3;
   }
}
