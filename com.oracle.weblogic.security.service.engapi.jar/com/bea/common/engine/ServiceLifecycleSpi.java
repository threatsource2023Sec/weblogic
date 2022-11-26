package com.bea.common.engine;

public interface ServiceLifecycleSpi {
   Object init(Object var1, Services var2) throws ServiceInitializationException;

   void shutdown();
}
