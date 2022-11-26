package weblogic.cluster.replication;

import org.jvnet.hk2.annotations.Service;

@Service
public class QueueManagerFactoryImpl implements QueueManagerFactory {
   public QueueManager newAsyncQueue(AsyncFlush flusher, boolean greedy, int flushInterval, int flushThreshold, int queueTimeout) {
      return new AsyncQueueManager(flusher, greedy, flushInterval, flushThreshold, queueTimeout);
   }

   public QueueManager newAsyncQueue(AsyncFlush flusher, boolean greedy) {
      return new AsyncQueueManager(flusher, greedy);
   }
}
