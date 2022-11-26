package com.bea.core.security.managers;

public class CEO {
   private static Manager manager;

   public static Manager getManager() throws NotInitializedException {
      if (manager == null) {
         throw new NotInitializedException();
      } else {
         return manager;
      }
   }

   public static void setManager(Manager paramManager) {
      manager = paramManager;
   }
}
