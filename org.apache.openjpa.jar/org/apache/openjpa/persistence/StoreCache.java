package org.apache.openjpa.persistence;

import java.util.Collection;

public interface StoreCache {
   String NAME_DEFAULT = "default";

   boolean contains(Class var1, Object var2);

   boolean containsAll(Class var1, Object... var2);

   boolean containsAll(Class var1, Collection var2);

   void pin(Class var1, Object var2);

   void pinAll(Class var1, Object... var2);

   void pinAll(Class var1, Collection var2);

   void unpin(Class var1, Object var2);

   void unpinAll(Class var1, Object... var2);

   void unpinAll(Class var1, Collection var2);

   void evict(Class var1, Object var2);

   void evictAll(Class var1, Object... var2);

   void evictAll(Class var1, Collection var2);

   void evictAll();

   /** @deprecated */
   org.apache.openjpa.datacache.DataCache getDelegate();
}
