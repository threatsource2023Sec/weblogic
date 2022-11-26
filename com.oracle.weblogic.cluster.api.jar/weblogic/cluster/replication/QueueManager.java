package weblogic.cluster.replication;

import java.util.Iterator;

public interface QueueManager {
   void addToUpdates(Object var1);

   long getTimeAtLastUpdateFlush();

   int getQueueSize();

   Iterator iterator();

   void remove(Object var1);

   void flushOnce();

   void flush();
}
