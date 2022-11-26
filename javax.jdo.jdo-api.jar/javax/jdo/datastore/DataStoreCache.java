package javax.jdo.datastore;

import java.util.Collection;

public interface DataStoreCache {
   void evict(Object var1);

   void evictAll();

   void evictAll(Object... var1);

   void evictAll(Collection var1);

   /** @deprecated */
   void evictAll(Class var1, boolean var2);

   void evictAll(boolean var1, Class var2);

   void pin(Object var1);

   void pinAll(Collection var1);

   void pinAll(Object... var1);

   /** @deprecated */
   void pinAll(Class var1, boolean var2);

   void pinAll(boolean var1, Class var2);

   void unpin(Object var1);

   void unpinAll(Collection var1);

   void unpinAll(Object... var1);

   /** @deprecated */
   void unpinAll(Class var1, boolean var2);

   void unpinAll(boolean var1, Class var2);

   public static class EmptyDataStoreCache implements DataStoreCache {
      public void evict(Object oid) {
      }

      public void evictAll() {
      }

      public void evictAll(Object... oids) {
      }

      public void evictAll(Collection oids) {
      }

      public void evictAll(Class pcClass, boolean subclasses) {
      }

      public void evictAll(boolean subclasses, Class pcClass) {
      }

      public void pin(Object oid) {
      }

      public void pinAll(Object... oids) {
      }

      public void pinAll(Collection oids) {
      }

      public void pinAll(Class pcClass, boolean subclasses) {
      }

      public void pinAll(boolean subclasses, Class pcClass) {
      }

      public void unpin(Object oid) {
      }

      public void unpinAll(Object... oids) {
      }

      public void unpinAll(Collection oids) {
      }

      public void unpinAll(Class pcClass, boolean subclasses) {
      }

      public void unpinAll(boolean subclasses, Class pcClass) {
      }
   }
}
