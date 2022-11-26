package com.bea.common.engine;

public final class ServiceEngineConfigFactory {
   private ServiceEngineConfigFactory() {
   }

   public static ServiceEngineConfig getInstance(ClassLoader classLoader) {
      try {
         Class implClass = Class.forName("com.bea.common.engine.internal.ServiceEngineConfigImpl", true, classLoader);
         return (ServiceEngineConfig)implClass.newInstance();
      } catch (ClassNotFoundException var2) {
         throw new SecurityServiceRuntimeException(var2);
      } catch (InstantiationException var3) {
         throw new SecurityServiceRuntimeException(var3);
      } catch (IllegalAccessException var4) {
         throw new SecurityServiceRuntimeException(var4);
      }
   }
}
