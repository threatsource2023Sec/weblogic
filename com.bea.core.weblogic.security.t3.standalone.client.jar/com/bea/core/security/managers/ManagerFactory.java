package com.bea.core.security.managers;

public abstract class ManagerFactory {
   private static ManagerFactory INSTANCE;

   public static ManagerFactory getInstance() {
      return INSTANCE;
   }

   public static void setInstance(ManagerFactory factory) {
      INSTANCE = factory;
   }

   public abstract ManagerService getManagerService();
}
