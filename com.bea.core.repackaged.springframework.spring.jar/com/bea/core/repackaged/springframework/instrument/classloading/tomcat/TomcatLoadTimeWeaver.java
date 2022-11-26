package com.bea.core.repackaged.springframework.instrument.classloading.tomcat;

import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TomcatLoadTimeWeaver implements LoadTimeWeaver {
   private static final String INSTRUMENTABLE_LOADER_CLASS_NAME = "org.apache.tomcat.InstrumentableClassLoader";
   private final ClassLoader classLoader;
   private final Method addTransformerMethod;
   private final Method copyMethod;

   public TomcatLoadTimeWeaver() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public TomcatLoadTimeWeaver(@Nullable ClassLoader classLoader) {
      Assert.notNull(classLoader, (String)"ClassLoader must not be null");
      this.classLoader = classLoader;

      Class instrumentableLoaderClass;
      try {
         instrumentableLoaderClass = classLoader.loadClass("org.apache.tomcat.InstrumentableClassLoader");
         if (!instrumentableLoaderClass.isInstance(classLoader)) {
            instrumentableLoaderClass = classLoader.getClass();
         }
      } catch (ClassNotFoundException var5) {
         instrumentableLoaderClass = classLoader.getClass();
      }

      try {
         this.addTransformerMethod = instrumentableLoaderClass.getMethod("addTransformer", ClassFileTransformer.class);
         Method copyMethod = ClassUtils.getMethodIfAvailable(instrumentableLoaderClass, "copyWithoutTransformers");
         if (copyMethod == null) {
            copyMethod = instrumentableLoaderClass.getMethod("getThrowawayClassLoader");
         }

         this.copyMethod = copyMethod;
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not initialize TomcatLoadTimeWeaver because Tomcat API classes are not available", var4);
      }
   }

   public void addTransformer(ClassFileTransformer transformer) {
      try {
         this.addTransformerMethod.invoke(this.classLoader, transformer);
      } catch (InvocationTargetException var3) {
         throw new IllegalStateException("Tomcat addTransformer method threw exception", var3.getCause());
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not invoke Tomcat addTransformer method", var4);
      }
   }

   public ClassLoader getInstrumentableClassLoader() {
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      try {
         return new OverridingClassLoader(this.classLoader, (ClassLoader)this.copyMethod.invoke(this.classLoader));
      } catch (InvocationTargetException var2) {
         throw new IllegalStateException("Tomcat copy method threw exception", var2.getCause());
      } catch (Throwable var3) {
         throw new IllegalStateException("Could not invoke Tomcat copy method", var3);
      }
   }
}
