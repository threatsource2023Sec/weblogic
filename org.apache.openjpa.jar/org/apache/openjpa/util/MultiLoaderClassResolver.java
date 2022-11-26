package org.apache.openjpa.util;

import java.security.AccessController;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.MultiClassLoader;

public class MultiLoaderClassResolver implements ClassResolver {
   private final MultiClassLoader _loader = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());

   public MultiLoaderClassResolver() {
   }

   public MultiLoaderClassResolver(ClassLoader[] loaders) {
      for(int i = 0; i < loaders.length; ++i) {
         this._loader.addClassLoader(loaders[i]);
      }

   }

   public boolean addClassLoader(ClassLoader loader) {
      return this._loader.addClassLoader(loader);
   }

   public ClassLoader getClassLoader(Class ctx, ClassLoader envLoader) {
      return this._loader;
   }
}
