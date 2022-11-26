package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.core.DecoratingClassLoader;
import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.core.SmartClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ContextTypeMatchClassLoader extends DecoratingClassLoader implements SmartClassLoader {
   private static Method findLoadedClassMethod;
   private final Map bytesCache = new ConcurrentHashMap(256);

   public ContextTypeMatchClassLoader(@Nullable ClassLoader parent) {
      super(parent);
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      return (new ContextOverridingClassLoader(this.getParent())).loadClass(name);
   }

   public boolean isClassReloadable(Class clazz) {
      return clazz.getClassLoader() instanceof ContextOverridingClassLoader;
   }

   static {
      ClassLoader.registerAsParallelCapable();

      try {
         findLoadedClassMethod = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
      } catch (NoSuchMethodException var1) {
         throw new IllegalStateException("Invalid [java.lang.ClassLoader] class: no 'findLoadedClass' method defined!");
      }
   }

   private class ContextOverridingClassLoader extends OverridingClassLoader {
      public ContextOverridingClassLoader(ClassLoader parent) {
         super(parent);
      }

      protected boolean isEligibleForOverriding(String className) {
         if (!this.isExcluded(className) && !ContextTypeMatchClassLoader.this.isExcluded(className)) {
            ReflectionUtils.makeAccessible(ContextTypeMatchClassLoader.findLoadedClassMethod);

            for(ClassLoader parent = this.getParent(); parent != null; parent = parent.getParent()) {
               if (ReflectionUtils.invokeMethod(ContextTypeMatchClassLoader.findLoadedClassMethod, parent, className) != null) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }

      protected Class loadClassForOverriding(String name) throws ClassNotFoundException {
         byte[] bytes = (byte[])ContextTypeMatchClassLoader.this.bytesCache.get(name);
         if (bytes == null) {
            bytes = this.loadBytesForClass(name);
            if (bytes == null) {
               return null;
            }

            ContextTypeMatchClassLoader.this.bytesCache.put(name, bytes);
         }

         return this.defineClass(name, bytes, 0, bytes.length);
      }
   }
}
