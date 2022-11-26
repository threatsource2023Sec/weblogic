package org.apache.openjpa.util;

import java.security.AccessController;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.MultiClassLoader;

public class ClassResolverImpl implements ClassResolver {
   public ClassLoader getClassLoader(Class contextClass, ClassLoader envLoader) {
      ClassLoader contextLoader = null;
      if (contextClass != null) {
         contextLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(contextClass));
         if (contextLoader == null) {
            contextLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getSystemClassLoaderAction());
         }
      }

      ClassLoader threadLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      if ((contextLoader == null || contextLoader == threadLoader) && (envLoader == null || envLoader == threadLoader)) {
         return threadLoader;
      } else {
         MultiClassLoader loader = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());
         if (contextLoader != null) {
            loader.addClassLoader(contextLoader);
         }

         loader.addClassLoader(threadLoader);
         if (envLoader != null) {
            loader.addClassLoader(envLoader);
         }

         return loader;
      }
   }
}
