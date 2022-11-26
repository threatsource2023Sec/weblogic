package weblogic.management.runtime;

public interface KodoQueryCompilationCacheRuntimeMBean extends RuntimeMBean {
   void clear();

   int getTotalCurrentEntries();
}
