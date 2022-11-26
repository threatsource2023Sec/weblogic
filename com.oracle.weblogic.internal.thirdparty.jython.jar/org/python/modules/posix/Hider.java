package org.python.modules.posix;

import java.lang.reflect.Method;
import org.python.core.PyObject;

class Hider {
   public static void hideFunctions(Class cls, PyObject dict, OS os, boolean isNative) {
      PosixImpl posixImpl = isNative ? PosixImpl.NATIVE : PosixImpl.JAVA;
      Method[] var5 = cls.getDeclaredMethods();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Method method = var5[var7];
         if (isHidden(method, os, posixImpl)) {
            dict.__setitem__((String)method.getName(), (PyObject)null);
         }
      }

   }

   private static boolean isHidden(Method method, OS os, PosixImpl posixImpl) {
      if (method.isAnnotationPresent(Hide.class)) {
         Hide hide = (Hide)method.getAnnotation(Hide.class);
         if (hide.posixImpl() != PosixImpl.NOT_APPLICABLE && hide.posixImpl() == posixImpl) {
            return true;
         }

         OS[] var4 = hide.value();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            OS hideOS = var4[var6];
            if (os == hideOS) {
               return true;
            }
         }
      }

      return false;
   }
}
