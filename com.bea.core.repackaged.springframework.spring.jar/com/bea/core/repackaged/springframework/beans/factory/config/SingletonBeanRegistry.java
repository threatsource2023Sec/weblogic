package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface SingletonBeanRegistry {
   void registerSingleton(String var1, Object var2);

   @Nullable
   Object getSingleton(String var1);

   boolean containsSingleton(String var1);

   String[] getSingletonNames();

   int getSingletonCount();

   Object getSingletonMutex();
}
