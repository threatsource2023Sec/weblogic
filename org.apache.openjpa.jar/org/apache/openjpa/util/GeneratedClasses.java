package org.apache.openjpa.util;

import java.security.AccessController;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;

public class GeneratedClasses {
   public static ClassLoader getMostDerivedLoader(Class c1, Class c2) {
      ClassLoader l1 = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(c1));
      ClassLoader l2 = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(c2));
      if (l1 == l2) {
         return l1;
      } else if (l1 == null) {
         return l2;
      } else if (l2 == null) {
         return l1;
      } else {
         return canLoad(l1, c2) ? l1 : l2;
      }
   }

   public static Class loadBCClass(BCClass bc, ClassLoader loader) {
      BCClassLoader bcloader = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(bc.getProject(), loader));

      try {
         Class c = Class.forName(bc.getName(), true, bcloader);
         bc.getProject().clear();
         return c;
      } catch (Throwable var4) {
         throw (new GeneralException(bc.getName())).setCause(var4);
      }
   }

   private static boolean canLoad(ClassLoader loader, Class clazz) {
      Class loaded = null;

      try {
         loaded = loader.loadClass(clazz.getName());
      } catch (ClassNotFoundException var4) {
      }

      return clazz == loaded;
   }
}
