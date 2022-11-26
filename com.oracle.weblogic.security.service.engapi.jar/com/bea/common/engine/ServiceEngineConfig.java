package com.bea.common.engine;

public interface ServiceEngineConfig {
   void addEnvironmentManagedServiceConfig(String var1, Object var2, boolean var3);

   void addEnvironmentManagedServiceConfig(String var1, Object var2, boolean var3, String var4);

   ServiceEngineManagedServiceConfig addServiceEngineManagedServiceConfig(String var1, String var2, boolean var3);

   ServiceEngineManagedServiceConfig addServiceEngineManagedServiceConfig(String var1, String var2, boolean var3, String var4);

   Object getEnvironmentManagedService(String var1) throws ServiceNotFoundException;

   ServiceEngine startEngine();
}
