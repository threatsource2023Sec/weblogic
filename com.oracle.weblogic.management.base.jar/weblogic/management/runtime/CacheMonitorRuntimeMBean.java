package weblogic.management.runtime;

import java.util.Date;

public interface CacheMonitorRuntimeMBean extends RuntimeMBean {
   long getAccessCount();

   long getHitCount();

   long getCurrentTotalEntries();

   long getFlushesCount();

   long getInsertCount();

   long getCurrentSize();

   long getAccessTime();

   long getInsertTime();

   Date getTimeCreated();

   long getTimeSinceStart();
}
