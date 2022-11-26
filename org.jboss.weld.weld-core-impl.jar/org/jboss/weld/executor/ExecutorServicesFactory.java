package org.jboss.weld.executor;

import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.Permissions;

public class ExecutorServicesFactory {
   private ExecutorServicesFactory() {
   }

   public static ExecutorServices create(ResourceLoader loader, WeldConfiguration configuration) {
      int threadPoolSize = configuration.getIntegerProperty(ConfigurationKey.EXECUTOR_THREAD_POOL_SIZE);
      boolean debug = configuration.getBooleanProperty(ConfigurationKey.EXECUTOR_THREAD_POOL_DEBUG);
      ThreadPoolType threadPoolType = initThreadPoolType(configuration);
      long threadPoolKeepAliveTime = configuration.getLongProperty(ConfigurationKey.EXECUTOR_THREAD_POOL_KEEP_ALIVE_TIME);
      return debug ? enableDebugMode(constructExecutorServices(threadPoolType, threadPoolSize, threadPoolKeepAliveTime)) : constructExecutorServices(threadPoolType, threadPoolSize, threadPoolKeepAliveTime);
   }

   private static ExecutorServices constructExecutorServices(ThreadPoolType type, int threadPoolSize, long threadPoolKeepAliveTime) {
      switch (type) {
         case NONE:
            return null;
         case SINGLE_THREAD:
            return new SingleThreadExecutorServices();
         case FIXED_TIMEOUT:
            return new TimingOutFixedThreadPoolExecutorServices(threadPoolSize, threadPoolKeepAliveTime);
         case COMMON:
            return new CommonForkJoinPoolExecutorServices();
         default:
            return new FixedThreadPoolExecutorServices(threadPoolSize);
      }
   }

   private static ExecutorServices enableDebugMode(ExecutorServices executor) {
      return (ExecutorServices)(executor == null ? executor : new ProfilingExecutorServices(executor));
   }

   private static ThreadPoolType initThreadPoolType(WeldConfiguration configuration) {
      String threadPoolTypeString = configuration.getStringProperty(ConfigurationKey.EXECUTOR_THREAD_POOL_TYPE);
      if (threadPoolTypeString.isEmpty()) {
         return Permissions.hasPermission(Permissions.MODIFY_THREAD_GROUP) ? ExecutorServicesFactory.ThreadPoolType.FIXED : ExecutorServicesFactory.ThreadPoolType.NONE;
      } else {
         try {
            ThreadPoolType threadPoolType = ExecutorServicesFactory.ThreadPoolType.valueOf(threadPoolTypeString);
            if (System.getSecurityManager() != null && ExecutorServicesFactory.ThreadPoolType.COMMON == threadPoolType) {
               threadPoolType = ExecutorServicesFactory.ThreadPoolType.FIXED;
               BootstrapLogger.LOG.commonThreadPoolWithSecurityManagerEnabled(threadPoolType);
            }

            return threadPoolType;
         } catch (Exception var3) {
            throw BootstrapLogger.LOG.invalidThreadPoolType(threadPoolTypeString);
         }
      }
   }

   public static enum ThreadPoolType {
      FIXED,
      FIXED_TIMEOUT,
      NONE,
      SINGLE_THREAD,
      COMMON;
   }
}
