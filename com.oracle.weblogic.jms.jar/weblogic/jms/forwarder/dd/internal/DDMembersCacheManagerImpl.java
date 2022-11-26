package weblogic.jms.forwarder.dd.internal;

import java.util.HashMap;
import java.util.Map;
import weblogic.jms.cache.Cache;

public class DDMembersCacheManagerImpl {
   public static final DDMembersCacheManagerImpl ddMembersCacheManager = new DDMembersCacheManagerImpl();
   private static Map ddCacheMap = new HashMap();

   private DDMembersCacheManagerImpl() {
   }

   public synchronized void addDDMembersCache(Cache cache) {
      ddCacheMap.put(cache.getName(), cache);
   }

   public synchronized Cache getDDMembersCache(String name) {
      return (Cache)ddCacheMap.get(name);
   }
}
