package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.instrument.ClassFileTransformer;

public class SimpleLoadTimeWeaver implements LoadTimeWeaver {
   private final SimpleInstrumentableClassLoader classLoader;

   public SimpleLoadTimeWeaver() {
      this.classLoader = new SimpleInstrumentableClassLoader(ClassUtils.getDefaultClassLoader());
   }

   public SimpleLoadTimeWeaver(SimpleInstrumentableClassLoader classLoader) {
      Assert.notNull(classLoader, (String)"ClassLoader must not be null");
      this.classLoader = classLoader;
   }

   public void addTransformer(ClassFileTransformer transformer) {
      this.classLoader.addTransformer(transformer);
   }

   public ClassLoader getInstrumentableClassLoader() {
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      return new SimpleThrowawayClassLoader(this.getInstrumentableClassLoader());
   }
}
