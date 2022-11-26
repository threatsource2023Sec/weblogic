package kodo.conf.descriptor;

public interface CacheMapBean extends QueryCompilationCacheBean {
   int getCacheSize();

   void setCacheSize(int var1);

   int getSoftReferenceSize();

   void setSoftReferenceSize(int var1);
}
