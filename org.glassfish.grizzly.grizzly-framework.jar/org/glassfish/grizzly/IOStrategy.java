package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.Executor;
import org.glassfish.grizzly.strategies.WorkerThreadPoolConfigProducer;

public interface IOStrategy extends WorkerThreadPoolConfigProducer {
   boolean executeIoEvent(Connection var1, IOEvent var2) throws IOException;

   boolean executeIoEvent(Connection var1, IOEvent var2, boolean var3) throws IOException;

   Executor getThreadPoolFor(Connection var1, IOEvent var2);
}
