package com.oracle.weblogic.lifecycle.config;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LifecycleConfigChangeListener {
   void configFileReloaded(List var1);
}
