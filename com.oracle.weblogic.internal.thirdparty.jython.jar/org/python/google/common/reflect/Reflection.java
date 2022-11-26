package org.python.google.common.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;

@Beta
public final class Reflection {
   public static String getPackageName(Class clazz) {
      return getPackageName(clazz.getName());
   }

   public static String getPackageName(String classFullName) {
      int lastDot = classFullName.lastIndexOf(46);
      return lastDot < 0 ? "" : classFullName.substring(0, lastDot);
   }

   public static void initialize(Class... classes) {
      Class[] var1 = classes;
      int var2 = classes.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class clazz = var1[var3];

         try {
            Class.forName(clazz.getName(), true, clazz.getClassLoader());
         } catch (ClassNotFoundException var6) {
            throw new AssertionError(var6);
         }
      }

   }

   public static Object newProxy(Class interfaceType, InvocationHandler handler) {
      Preconditions.checkNotNull(handler);
      Preconditions.checkArgument(interfaceType.isInterface(), "%s is not an interface", (Object)interfaceType);
      Object object = Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
      return interfaceType.cast(object);
   }

   private Reflection() {
   }
}
