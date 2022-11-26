package kodo.conf.descriptor;

public interface LRUDataCacheBean extends DataCacheBean {
   int getCacheSize();

   void setCacheSize(int var1);

   int getSoftReferenceSize();

   void setSoftReferenceSize(int var1);

   String getEvictionSchedule();

   void setEvictionSchedule(String var1);
}
