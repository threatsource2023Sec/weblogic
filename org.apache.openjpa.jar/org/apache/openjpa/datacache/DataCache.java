package org.apache.openjpa.datacache;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.lib.util.Closeable;

public interface DataCache extends Closeable {
   String NAME_DEFAULT = "default";

   String getName();

   void setName(String var1);

   void initialize(DataCacheManager var1);

   void commit(Collection var1, Collection var2, Collection var3, Collection var4);

   boolean contains(Object var1);

   BitSet containsAll(Collection var1);

   DataCachePCData get(Object var1);

   DataCachePCData put(DataCachePCData var1);

   void update(DataCachePCData var1);

   DataCachePCData remove(Object var1);

   BitSet removeAll(Collection var1);

   void removeAll(Class var1, boolean var2);

   void clear();

   boolean pin(Object var1);

   BitSet pinAll(Collection var1);

   void pinAll(Class var1, boolean var2);

   boolean unpin(Object var1);

   BitSet unpinAll(Collection var1);

   void unpinAll(Class var1, boolean var2);

   void writeLock();

   void writeUnlock();

   void addExpirationListener(ExpirationListener var1);

   boolean removeExpirationListener(ExpirationListener var1);

   void close();

   Map getAll(List var1);
}
