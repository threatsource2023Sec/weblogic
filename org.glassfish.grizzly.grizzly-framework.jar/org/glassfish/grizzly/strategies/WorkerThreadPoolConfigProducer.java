package org.glassfish.grizzly.strategies;

import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public interface WorkerThreadPoolConfigProducer {
   ThreadPoolConfig createDefaultWorkerPoolConfig(Transport var1);
}
