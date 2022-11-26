package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStream;

public class OverridingClassLoader extends DecoratingClassLoader {
   public static final String[] DEFAULT_EXCLUDED_PACKAGES = new String[]{"java.", "javax.", "sun.", "oracle.", "javassist.", "com.bea.core.repackaged.aspectj.", "net.sf.cglib."};
   private static final String CLASS_FILE_SUFFIX = ".class";
   @Nullable
   private final ClassLoader overrideDelegate;

   public OverridingClassLoader(@Nullable ClassLoader parent) {
      this(parent, (ClassLoader)null);
   }

   public OverridingClassLoader(@Nullable ClassLoader parent, @Nullable ClassLoader overrideDelegate) {
      super(parent);
      this.overrideDelegate = overrideDelegate;
      String[] var3 = DEFAULT_EXCLUDED_PACKAGES;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String packageName = var3[var5];
         this.excludePackage(packageName);
      }

   }

   public Class loadClass(String name) throws ClassNotFoundException {
      return this.overrideDelegate != null && this.isEligibleForOverriding(name) ? this.overrideDelegate.loadClass(name) : super.loadClass(name);
   }

   protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      if (this.isEligibleForOverriding(name)) {
         Class result = this.loadClassForOverriding(name);
         if (result != null) {
            if (resolve) {
               this.resolveClass(result);
            }

            return result;
         }
      }

      return super.loadClass(name, resolve);
   }

   protected boolean isEligibleForOverriding(String className) {
      return !this.isExcluded(className);
   }

   @Nullable
   protected Class loadClassForOverriding(String name) throws ClassNotFoundException {
      Class result = this.findLoadedClass(name);
      if (result == null) {
         byte[] bytes = this.loadBytesForClass(name);
         if (bytes != null) {
            result = this.defineClass(name, bytes, 0, bytes.length);
         }
      }

      return result;
   }

   @Nullable
   protected byte[] loadBytesForClass(String name) throws ClassNotFoundException {
      InputStream is = this.openStreamForClass(name);
      if (is == null) {
         return null;
      } else {
         try {
            byte[] bytes = FileCopyUtils.copyToByteArray(is);
            return this.transformIfNecessary(name, bytes);
         } catch (IOException var4) {
            throw new ClassNotFoundException("Cannot load resource for class [" + name + "]", var4);
         }
      }
   }

   @Nullable
   protected InputStream openStreamForClass(String name) {
      String internalName = name.replace('.', '/') + ".class";
      return this.getParent().getResourceAsStream(internalName);
   }

   protected byte[] transformIfNecessary(String name, byte[] bytes) {
      return bytes;
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
