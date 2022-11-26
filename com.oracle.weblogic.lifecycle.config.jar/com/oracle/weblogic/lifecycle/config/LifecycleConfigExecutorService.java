package com.oracle.weblogic.lifecycle.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jvnet.hk2.annotations.ContractsProvided;

@org.jvnet.hk2.annotations.Service
@javax.inject.Named("transactions-executor")
@ContractsProvided({ExecutorService.class})
public class LifecycleConfigExecutorService extends ThreadPoolExecutor {
   private LifecycleConfigExecutorService() {
      super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(true), new MyThreadFactory());
   }

   public static class MyThreadFactory implements ThreadFactory {
      public Thread newThread(Runnable runnable) {
         Thread activeThread = new Thread(runnable);
         activeThread.setDaemon(true);
         return activeThread;
      }
   }
}
