package com.bea.core.repackaged.springframework.instrument.classloading.weblogic;

import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.instrument.ClassFileTransformer;

public class WebLogicLoadTimeWeaver implements LoadTimeWeaver {
   private final WebLogicClassLoaderAdapter classLoader;

   public WebLogicLoadTimeWeaver() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public WebLogicLoadTimeWeaver(@Nullable ClassLoader classLoader) {
      Assert.notNull(classLoader, (String)"ClassLoader must not be null");
      this.classLoader = new WebLogicClassLoaderAdapter(classLoader);
   }

   public void addTransformer(ClassFileTransformer transformer) {
      this.classLoader.addTransformer(transformer);
   }

   public ClassLoader getInstrumentableClassLoader() {
      return this.classLoader.getClassLoader();
   }

   public ClassLoader getThrowawayClassLoader() {
      return new OverridingClassLoader(this.classLoader.getClassLoader(), this.classLoader.getThrowawayClassLoader());
   }
}
