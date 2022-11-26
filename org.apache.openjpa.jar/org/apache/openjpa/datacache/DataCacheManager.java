package org.apache.openjpa.datacache;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.ObjectValue;

public interface DataCacheManager {
   void initialize(OpenJPAConfiguration var1, ObjectValue var2, ObjectValue var3);

   DataCache getSystemDataCache();

   DataCache getDataCache(String var1);

   DataCache getDataCache(String var1, boolean var2);

   QueryCache getSystemQueryCache();

   DataCachePCDataGenerator getPCDataGenerator();

   DataCacheScheduler getDataCacheScheduler();

   void close();
}
