package org.jboss.weld.bootstrap.api.helpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.weld.bootstrap.api.Singleton;
import org.jboss.weld.bootstrap.api.SingletonProvider;

public class RegistrySingletonProvider extends SingletonProvider {
   public static final String STATIC_INSTANCE = "STATIC_INSTANCE";

   public Singleton create(Class type) {
      return new RegistrySingleton();
   }

   private static class RegistrySingleton implements Singleton {
      private final Map store;

      private RegistrySingleton() {
         this.store = new ConcurrentHashMap();
      }

      public Object get(String id) {
         Object instance = this.store.get(id);
         if (instance == null) {
            throw new IllegalStateException("Singleton not set for " + id + " => " + this.store.keySet());
         } else {
            return instance;
         }
      }

      public void set(String id, Object object) {
         this.store.put(id, object);
      }

      public void clear(String id) {
         this.store.remove(id);
      }

      public boolean isSet(String id) {
         return this.store.containsKey(id);
      }

      // $FF: synthetic method
      RegistrySingleton(Object x0) {
         this();
      }
   }
}
