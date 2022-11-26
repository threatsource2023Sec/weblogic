package org.glassfish.grizzly.threadpool;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeStorage;

public interface WorkerThread extends Runnable, AttributeStorage {
   long UNLIMITED_TRANSACTION_TIMEOUT = -1L;

   void start();

   void stop();

   void destroy();

   String getName();

   Thread getThread();

   long getTransactionTimeout(TimeUnit var1);

   void setTransactionTimeout(long var1, TimeUnit var3);
}
