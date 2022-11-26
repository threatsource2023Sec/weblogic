package org.jboss.weld.bootstrap.api;

import org.jboss.weld.bootstrap.api.helpers.RegistrySingletonProvider;

public abstract class SingletonProvider {
   private static volatile SingletonProvider INSTANCE;
   private static final String DEFAULT_SCOPE_FACTORY = RegistrySingletonProvider.class.getName();

   public static SingletonProvider instance() {
      if (INSTANCE == null) {
         Class var0 = SingletonProvider.class;
         synchronized(SingletonProvider.class) {
            if (INSTANCE == null) {
               initializeWithDefaultScope();
            }
         }
      }

      return INSTANCE;
   }

   protected SingletonProvider() {
   }

   public abstract Singleton create(Class var1);

   private static void initializeWithDefaultScope() {
      try {
         Class aClass = Class.forName(DEFAULT_SCOPE_FACTORY);
         INSTANCE = (SingletonProvider)aClass.newInstance();
      } catch (Exception var1) {
         throw new RuntimeException(var1);
      }
   }

   public static void initialize(SingletonProvider instance) {
      Class var1 = SingletonProvider.class;
      synchronized(SingletonProvider.class) {
         if (INSTANCE == null) {
            INSTANCE = instance;
         } else {
            throw new RuntimeException("SingletonProvider is already initialized with " + INSTANCE);
         }
      }
   }

   public static void reset() {
      INSTANCE = null;
   }
}
