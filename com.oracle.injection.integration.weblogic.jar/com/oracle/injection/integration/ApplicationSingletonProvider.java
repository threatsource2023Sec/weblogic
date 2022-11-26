package com.oracle.injection.integration;

import java.util.concurrent.ConcurrentHashMap;
import org.jboss.weld.bootstrap.api.Singleton;
import org.jboss.weld.bootstrap.api.SingletonProvider;
import weblogic.application.ComponentInvocationContextManagerImpl;
import weblogic.invocation.ComponentInvocationContextManager;

class ApplicationSingletonProvider extends SingletonProvider {
   public Singleton create(Class aClass) {
      return new ApplicationSingleton();
   }

   private static class ApplicationSingleton implements Singleton {
      private final ConcurrentHashMap m_mapOfSingletonObjects;

      private ApplicationSingleton() {
         this.m_mapOfSingletonObjects = new ConcurrentHashMap();
      }

      public Object get(String id) {
         String applicationId = this.getApplicationId();
         return applicationId != null ? this.m_mapOfSingletonObjects.get(applicationId) : null;
      }

      private String getApplicationId() {
         ComponentInvocationContextManager componentInvocationContextManager = ComponentInvocationContextManagerImpl.getInstance();
         return componentInvocationContextManager.getCurrentComponentInvocationContext().getApplicationId();
      }

      public boolean isSet(String id) {
         String applicationId = this.getApplicationId();
         return applicationId != null ? this.m_mapOfSingletonObjects.containsKey(applicationId) : false;
      }

      public void set(String id, Object object) {
         String applicationId = this.getApplicationId();
         if (applicationId != null) {
            this.m_mapOfSingletonObjects.put(applicationId, object);
         }

      }

      public void clear(String id) {
         String applicationId = this.getApplicationId();
         if (applicationId != null) {
            this.m_mapOfSingletonObjects.remove(applicationId);
         }

      }

      // $FF: synthetic method
      ApplicationSingleton(Object x0) {
         this();
      }
   }
}
