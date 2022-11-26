package org.glassfish.grizzly.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface TransferQueue extends BlockingQueue {
   boolean tryTransfer(Object var1);

   void transfer(Object var1) throws InterruptedException;

   boolean tryTransfer(Object var1, long var2, TimeUnit var4) throws InterruptedException;

   boolean hasWaitingConsumer();

   int getWaitingConsumerCount();
}
