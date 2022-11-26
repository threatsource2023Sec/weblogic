package weblogic.cluster.replication;

import java.util.concurrent.BlockingQueue;

public interface AsyncFlush {
   void flushQueue(BlockingQueue var1, ResourceGroupKey var2);
}
