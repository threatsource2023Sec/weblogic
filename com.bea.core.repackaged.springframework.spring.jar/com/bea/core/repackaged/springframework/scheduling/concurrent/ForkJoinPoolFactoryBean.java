package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolFactoryBean implements FactoryBean, InitializingBean, DisposableBean {
   private boolean commonPool = false;
   private int parallelism = Runtime.getRuntime().availableProcessors();
   private ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory;
   @Nullable
   private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
   private boolean asyncMode;
   private int awaitTerminationSeconds;
   @Nullable
   private ForkJoinPool forkJoinPool;

   public ForkJoinPoolFactoryBean() {
      this.threadFactory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
      this.asyncMode = false;
      this.awaitTerminationSeconds = 0;
   }

   public void setCommonPool(boolean commonPool) {
      this.commonPool = commonPool;
   }

   public void setParallelism(int parallelism) {
      this.parallelism = parallelism;
   }

   public void setThreadFactory(ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory) {
      this.threadFactory = threadFactory;
   }

   public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
      this.uncaughtExceptionHandler = uncaughtExceptionHandler;
   }

   public void setAsyncMode(boolean asyncMode) {
      this.asyncMode = asyncMode;
   }

   public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
      this.awaitTerminationSeconds = awaitTerminationSeconds;
   }

   public void afterPropertiesSet() {
      this.forkJoinPool = this.commonPool ? ForkJoinPool.commonPool() : new ForkJoinPool(this.parallelism, this.threadFactory, this.uncaughtExceptionHandler, this.asyncMode);
   }

   @Nullable
   public ForkJoinPool getObject() {
      return this.forkJoinPool;
   }

   public Class getObjectType() {
      return ForkJoinPool.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() {
      if (this.forkJoinPool != null) {
         this.forkJoinPool.shutdown();
         if (this.awaitTerminationSeconds > 0) {
            try {
               this.forkJoinPool.awaitTermination((long)this.awaitTerminationSeconds, TimeUnit.SECONDS);
            } catch (InterruptedException var2) {
               Thread.currentThread().interrupt();
            }
         }
      }

   }
}
