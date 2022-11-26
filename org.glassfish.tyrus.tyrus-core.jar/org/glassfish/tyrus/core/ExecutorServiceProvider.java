package org.glassfish.tyrus.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ExecutorServiceProvider {
   public abstract ExecutorService getExecutorService();

   public abstract ScheduledExecutorService getScheduledExecutorService();
}
