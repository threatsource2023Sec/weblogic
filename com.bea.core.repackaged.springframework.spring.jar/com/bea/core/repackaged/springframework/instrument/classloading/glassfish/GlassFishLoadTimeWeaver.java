package com.bea.core.repackaged.springframework.instrument.classloading.glassfish;

import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GlassFishLoadTimeWeaver implements LoadTimeWeaver {
   private static final String INSTRUMENTABLE_LOADER_CLASS_NAME = "org.glassfish.api.deployment.InstrumentableClassLoader";
   private final ClassLoader classLoader;
   private final Method addTransformerMethod;
   private final Method copyMethod;

   public GlassFishLoadTimeWeaver() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public GlassFishLoadTimeWeaver(@Nullable ClassLoader classLoader) {
      Assert.notNull(classLoader, (String)"ClassLoader must not be null");

      Class instrumentableLoaderClass;
      try {
         instrumentableLoaderClass = classLoader.loadClass("org.glassfish.api.deployment.InstrumentableClassLoader");
         this.addTransformerMethod = instrumentableLoaderClass.getMethod("addTransformer", ClassFileTransformer.class);
         this.copyMethod = instrumentableLoaderClass.getMethod("copy");
      } catch (Throwable var5) {
         throw new IllegalStateException("Could not initialize GlassFishLoadTimeWeaver because GlassFish API classes are not available", var5);
      }

      ClassLoader clazzLoader = null;

      for(ClassLoader cl = classLoader; cl != null && clazzLoader == null; cl = cl.getParent()) {
         if (instrumentableLoaderClass.isInstance(cl)) {
            clazzLoader = cl;
         }
      }

      if (clazzLoader == null) {
         throw new IllegalArgumentException(classLoader + " and its parents are not suitable ClassLoaders: A [" + instrumentableLoaderClass.getName() + "] implementation is required.");
      } else {
         this.classLoader = clazzLoader;
      }
   }

   public void addTransformer(ClassFileTransformer transformer) {
      try {
         this.addTransformerMethod.invoke(this.classLoader, transformer);
      } catch (InvocationTargetException var3) {
         throw new IllegalStateException("GlassFish addTransformer method threw exception", var3.getCause());
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not invoke GlassFish addTransformer method", var4);
      }
   }

   public ClassLoader getInstrumentableClassLoader() {
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      try {
         return new OverridingClassLoader(this.classLoader, (ClassLoader)this.copyMethod.invoke(this.classLoader));
      } catch (InvocationTargetException var2) {
         throw new IllegalStateException("GlassFish copy method threw exception", var2.getCause());
      } catch (Throwable var3) {
         throw new IllegalStateException("Could not invoke GlassFish copy method", var3);
      }
   }
}
