package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecycleConfigListener {
   void configFileLoaded(LifecycleConfig var1);
}
