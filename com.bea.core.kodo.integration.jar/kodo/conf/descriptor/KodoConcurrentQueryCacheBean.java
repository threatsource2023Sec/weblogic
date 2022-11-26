package kodo.conf.descriptor;

public interface KodoConcurrentQueryCacheBean extends QueryCacheBean {
   int getCacheSize();

   void setCacheSize(int var1);

   int getSoftReferenceSize();

   void setSoftReferenceSize(int var1);
}
