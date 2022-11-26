package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorFactoryBean extends ExecutorConfigurationSupport implements FactoryBean, InitializingBean, DisposableBean {
   private int corePoolSize = 1;
   private int maxPoolSize = Integer.MAX_VALUE;
   private int keepAliveSeconds = 60;
   private boolean allowCoreThreadTimeOut = false;
   private int queueCapacity = Integer.MAX_VALUE;
   private boolean exposeUnconfigurableExecutor = false;
   @Nullable
   private ExecutorService exposedExecutor;

   public void setCorePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
   }

   public void setMaxPoolSize(int maxPoolSize) {
      this.maxPoolSize = maxPoolSize;
   }

   public void setKeepAliveSeconds(int keepAliveSeconds) {
      this.keepAliveSeconds = keepAliveSeconds;
   }

   public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
      this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
   }

   public void setQueueCapacity(int queueCapacity) {
      this.queueCapacity = queueCapacity;
   }

   public void setExposeUnconfigurableExecutor(boolean exposeUnconfigurableExecutor) {
      this.exposeUnconfigurableExecutor = exposeUnconfigurableExecutor;
   }

   protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      BlockingQueue queue = this.createQueue(this.queueCapacity);
      ThreadPoolExecutor executor = this.createExecutor(this.corePoolSize, this.maxPoolSize, this.keepAliveSeconds, queue, threadFactory, rejectedExecutionHandler);
      if (this.allowCoreThreadTimeOut) {
         executor.allowCoreThreadTimeOut(true);
      }

      this.exposedExecutor = (ExecutorService)(this.exposeUnconfigurableExecutor ? Executors.unconfigurableExecutorService(executor) : executor);
      return executor;
   }

   protected ThreadPoolExecutor createExecutor(int corePoolSize, int maxPoolSize, int keepAliveSeconds, BlockingQueue queue, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
      return new ThreadPoolExecutor(corePoolSize, maxPoolSize, (long)keepAliveSeconds, TimeUnit.SECONDS, queue, threadFactory, rejectedExecutionHandler);
   }

   protected BlockingQueue createQueue(int queueCapacity) {
      return (BlockingQueue)(queueCapacity > 0 ? new LinkedBlockingQueue(queueCapacity) : new SynchronousQueue());
   }

   @Nullable
   public ExecutorService getObject() {
      return this.exposedExecutor;
   }

   public Class getObjectType() {
      return this.exposedExecutor != null ? this.exposedExecutor.getClass() : ExecutorService.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
