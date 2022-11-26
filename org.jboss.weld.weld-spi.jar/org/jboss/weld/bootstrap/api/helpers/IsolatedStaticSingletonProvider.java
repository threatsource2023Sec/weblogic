package org.jboss.weld.bootstrap.api.helpers;

import org.jboss.weld.bootstrap.api.Singleton;
import org.jboss.weld.bootstrap.api.SingletonProvider;

public class IsolatedStaticSingletonProvider extends SingletonProvider {
   public Singleton create(Class type) {
      return new IsolatedStaticSingleton();
   }

   private static class IsolatedStaticSingleton implements Singleton {
      private Object object;

      private IsolatedStaticSingleton() {
      }

      public Object get(String id) {
         if (this.object == null) {
            throw new IllegalStateException("Singleton is not set. Is your Thread.currentThread().getContextClassLoader() set correctly?");
         } else {
            return this.object;
         }
      }

      public void set(String id, Object object) {
         this.object = object;
      }

      public void clear(String id) {
         this.object = null;
      }

      public boolean isSet(String id) {
         return this.object != null;
      }

      // $FF: synthetic method
      IsolatedStaticSingleton(Object x0) {
         this();
      }
   }
}
