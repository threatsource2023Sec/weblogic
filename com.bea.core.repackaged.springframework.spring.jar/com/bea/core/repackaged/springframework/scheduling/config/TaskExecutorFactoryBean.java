package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.task.TaskExecutor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.concurrent.RejectedExecutionHandler;

public class TaskExecutorFactoryBean implements FactoryBean, BeanNameAware, InitializingBean, DisposableBean {
   @Nullable
   private String poolSize;
   @Nullable
   private Integer queueCapacity;
   @Nullable
   private RejectedExecutionHandler rejectedExecutionHandler;
   @Nullable
   private Integer keepAliveSeconds;
   @Nullable
   private String beanName;
   @Nullable
   private ThreadPoolTaskExecutor target;

   public void setPoolSize(String poolSize) {
      this.poolSize = poolSize;
   }

   public void setQueueCapacity(int queueCapacity) {
      this.queueCapacity = queueCapacity;
   }

   public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
      this.rejectedExecutionHandler = rejectedExecutionHandler;
   }

   public void setKeepAliveSeconds(int keepAliveSeconds) {
      this.keepAliveSeconds = keepAliveSeconds;
   }

   public void setBeanName(String beanName) {
      this.beanName = beanName;
   }

   public void afterPropertiesSet() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      this.determinePoolSizeRange(executor);
      if (this.queueCapacity != null) {
         executor.setQueueCapacity(this.queueCapacity);
      }

      if (this.keepAliveSeconds != null) {
         executor.setKeepAliveSeconds(this.keepAliveSeconds);
      }

      if (this.rejectedExecutionHandler != null) {
         executor.setRejectedExecutionHandler(this.rejectedExecutionHandler);
      }

      if (this.beanName != null) {
         executor.setThreadNamePrefix(this.beanName + "-");
      }

      executor.afterPropertiesSet();
      this.target = executor;
   }

   private void determinePoolSizeRange(ThreadPoolTaskExecutor executor) {
      if (StringUtils.hasText(this.poolSize)) {
         try {
            int separatorIndex = this.poolSize.indexOf(45);
            int corePoolSize;
            int maxPoolSize;
            if (separatorIndex != -1) {
               corePoolSize = Integer.valueOf(this.poolSize.substring(0, separatorIndex));
               maxPoolSize = Integer.valueOf(this.poolSize.substring(separatorIndex + 1, this.poolSize.length()));
               if (corePoolSize > maxPoolSize) {
                  throw new IllegalArgumentException("Lower bound of pool-size range must not exceed the upper bound");
               }

               if (this.queueCapacity == null) {
                  if (corePoolSize != 0) {
                     throw new IllegalArgumentException("A non-zero lower bound for the size range requires a queue-capacity value");
                  }

                  executor.setAllowCoreThreadTimeOut(true);
                  corePoolSize = maxPoolSize;
               }
            } else {
               Integer value = Integer.valueOf(this.poolSize);
               corePoolSize = value;
               maxPoolSize = value;
            }

            executor.setCorePoolSize(corePoolSize);
            executor.setMaxPoolSize(maxPoolSize);
         } catch (NumberFormatException var6) {
            throw new IllegalArgumentException("Invalid pool-size value [" + this.poolSize + "]: only single maximum integer (e.g. \"5\") and minimum-maximum range (e.g. \"3-5\") are supported", var6);
         }
      }

   }

   @Nullable
   public TaskExecutor getObject() {
      return this.target;
   }

   public Class getObjectType() {
      return this.target != null ? this.target.getClass() : ThreadPoolTaskExecutor.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() {
      if (this.target != null) {
         this.target.destroy();
      }

   }
}
