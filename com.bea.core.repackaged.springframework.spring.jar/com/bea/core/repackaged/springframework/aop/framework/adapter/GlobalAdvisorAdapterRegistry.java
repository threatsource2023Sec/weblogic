package com.bea.core.repackaged.springframework.aop.framework.adapter;

public final class GlobalAdvisorAdapterRegistry {
   private static AdvisorAdapterRegistry instance = new DefaultAdvisorAdapterRegistry();

   private GlobalAdvisorAdapterRegistry() {
   }

   public static AdvisorAdapterRegistry getInstance() {
      return instance;
   }

   static void reset() {
      instance = new DefaultAdvisorAdapterRegistry();
   }
}
