package com.bea.core.repackaged.springframework.context.weaving;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;

public interface LoadTimeWeaverAware extends Aware {
   void setLoadTimeWeaver(LoadTimeWeaver var1);
}
