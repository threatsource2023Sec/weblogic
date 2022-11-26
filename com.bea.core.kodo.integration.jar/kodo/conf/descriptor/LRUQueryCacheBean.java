package kodo.conf.descriptor;

public interface LRUQueryCacheBean extends QueryCacheBean {
   int getCacheSize();

   void setCacheSize(int var1);

   int getSoftReferenceSize();

   void setSoftReferenceSize(int var1);
}
