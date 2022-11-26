package kodo.conf.descriptor;

public interface ConcurrentHashMapBean extends QueryCompilationCacheBean {
   int getMaxSize();

   void setMaxSize(int var1);
}
