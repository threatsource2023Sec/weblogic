package org.apache.openjpa.datacache;

import org.apache.openjpa.lib.util.Closeable;

public interface QueryCache extends TypesChangedListener, Closeable {
   void initialize(DataCacheManager var1);

   QueryResult get(QueryKey var1);

   QueryResult put(QueryKey var1, QueryResult var2);

   QueryResult remove(QueryKey var1);

   void clear();

   boolean pin(QueryKey var1);

   boolean unpin(QueryKey var1);

   void writeLock();

   void writeUnlock();

   void addTypesChangedListener(TypesChangedListener var1);

   boolean removeTypesChangedListener(TypesChangedListener var1);

   void close();
}
