package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class SimpleThrowawayClassLoader extends OverridingClassLoader {
   public SimpleThrowawayClassLoader(@Nullable ClassLoader parent) {
      super(parent);
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
