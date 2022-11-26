package com.oracle.pitchfork.server;

import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;

public interface ContainerBootstrap {
   GenericApplicationContext bootstrap(ApplicationContext var1, String[] var2, String[] var3, ResourceLoader var4);
}
