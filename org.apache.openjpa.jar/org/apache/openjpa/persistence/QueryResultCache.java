package org.apache.openjpa.persistence;

import javax.persistence.Query;
import org.apache.openjpa.datacache.QueryCache;

public interface QueryResultCache {
   void pin(Query var1);

   void unpin(Query var1);

   void evict(Query var1);

   void evictAll();

   void evictAll(Class var1);

   /** @deprecated */
   QueryCache getDelegate();
}
