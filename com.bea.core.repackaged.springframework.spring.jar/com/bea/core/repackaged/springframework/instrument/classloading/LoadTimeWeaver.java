package com.bea.core.repackaged.springframework.instrument.classloading;

import java.lang.instrument.ClassFileTransformer;

public interface LoadTimeWeaver {
   void addTransformer(ClassFileTransformer var1);

   ClassLoader getInstrumentableClassLoader();

   ClassLoader getThrowawayClassLoader();
}
