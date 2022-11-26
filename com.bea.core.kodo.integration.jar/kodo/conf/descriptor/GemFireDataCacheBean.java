package kodo.conf.descriptor;

public interface GemFireDataCacheBean extends DataCacheBean {
   String getGemFireCacheName();

   void setGemFireCacheName(String var1);

   String getEvictionSchedule();

   void setEvictionSchedule(String var1);
}
