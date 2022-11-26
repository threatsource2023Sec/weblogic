package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.threadpool.DefaultWorkerThread;

public abstract class AbstractMemoryManager implements MemoryManager, ThreadLocalPoolProvider {
   public static final int DEFAULT_MAX_BUFFER_SIZE = 65536;
   protected final DefaultMonitoringConfig monitoringConfig;
   protected final int maxBufferSize;

   public AbstractMemoryManager() {
      this(65536);
   }

   public AbstractMemoryManager(int maxBufferSize) {
      this.monitoringConfig = new DefaultMonitoringConfig(MemoryProbe.class) {
         public Object createManagementObject() {
            return AbstractMemoryManager.this.createJmxManagementObject();
         }
      };
      this.maxBufferSize = maxBufferSize;
   }

   public int getReadyThreadBufferSize() {
      ThreadLocalPool threadLocalPool = getThreadLocalPool();
      return threadLocalPool != null ? threadLocalPool.remaining() : 0;
   }

   public int getMaxBufferSize() {
      return this.maxBufferSize;
   }

   protected Object allocateFromPool(ThreadLocalPool threadLocalCache, int size) {
      if (threadLocalCache.remaining() >= size) {
         ProbeNotifier.notifyBufferAllocatedFromPool(this.monitoringConfig, size);
         return threadLocalCache.allocate(size);
      } else {
         return null;
      }
   }

   protected abstract Object createJmxManagementObject();

   protected static ThreadLocalPool getThreadLocalPool() {
      Thread t = Thread.currentThread();
      return t instanceof DefaultWorkerThread ? ((DefaultWorkerThread)t).getMemoryPool() : null;
   }

   protected interface TrimAware extends Cacheable {
   }
}
