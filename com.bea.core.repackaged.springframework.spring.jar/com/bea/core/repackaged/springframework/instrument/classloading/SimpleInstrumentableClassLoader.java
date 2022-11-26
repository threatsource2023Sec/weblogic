package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.instrument.ClassFileTransformer;

public class SimpleInstrumentableClassLoader extends OverridingClassLoader {
   private final WeavingTransformer weavingTransformer;

   public SimpleInstrumentableClassLoader(@Nullable ClassLoader parent) {
      super(parent);
      this.weavingTransformer = new WeavingTransformer(parent);
   }

   public void addTransformer(ClassFileTransformer transformer) {
      this.weavingTransformer.addTransformer(transformer);
   }

   protected byte[] transformIfNecessary(String name, byte[] bytes) {
      return this.weavingTransformer.transformIfNecessary(name, bytes);
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
