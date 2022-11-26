package org.jboss.weld.bootstrap.api.helpers;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.weld.bootstrap.api.Singleton;
import org.jboss.weld.bootstrap.api.SingletonProvider;

public class TCCLSingletonProvider extends SingletonProvider {
   public Singleton create(Class type) {
      return new TCCLSingleton();
   }

   private static class TCCLSingleton implements Singleton {
      private final Map store;

      private TCCLSingleton() {
         this.store = new ConcurrentHashMap();
      }

      public Object get(String id) {
         Object instance = this.store.get(this.getClassLoader());
         if (instance == null) {
            throw new IllegalStateException("Singleton not set for " + this.getClassLoader());
         } else {
            return instance;
         }
      }

      public void set(String id, Object object) {
         this.store.put(this.getClassLoader(), object);
      }

      public void clear(String id) {
         this.store.remove(this.getClassLoader());
      }

      public boolean isSet(String id) {
         return this.store.containsKey(this.getClassLoader());
      }

      private ClassLoader getClassLoader() {
         SecurityManager sm = System.getSecurityManager();
         return sm != null ? (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public ClassLoader run() {
               return Thread.currentThread().getContextClassLoader();
            }
         }) : Thread.currentThread().getContextClassLoader();
      }

      // $FF: synthetic method
      TCCLSingleton(Object x0) {
         this();
      }
   }
}
