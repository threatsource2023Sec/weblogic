package com.oracle.jrf.concurrent;

import com.oracle.jrf.concurrent.impl.DefaultExecutorFactory;
import com.oracle.jrf.concurrent.impl.WlsExecutorFactory;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import javax.naming.NamingException;
import weblogic.kernel.Kernel;

public abstract class ExecutorFactory {
   public static final String APPLICATION_EXECUTOR = "java:comp/DefaultManagedExecutorService";
   public static final String TENANT_EXECUTOR = "DefaultManagedExecutorService";
   public static final String APPLICATION_SCHEDULER = "java:comp/DefaultManagedScheduledExecutorService";
   public static final String TENANT_SCHEDULER = "DefaultManagedScheduledExecutorService";

   protected ExecutorFactory() {
   }

   private static ExecutorFactory init() {
      return (ExecutorFactory)(isWls() ? new WlsExecutorFactory() : new DefaultExecutorFactory());
   }

   private static boolean isWls() {
      try {
         return Kernel.isServer();
      } catch (LinkageError var1) {
      } catch (Exception var2) {
      }

      return false;
   }

   public static ExecutorFactory getInstance() {
      return ExecutorFactory.SingletonHolder.SINGLETON;
   }

   public abstract ExecutorService getExecutor(String var1) throws NamingException;

   public abstract ScheduledExecutorService getScheduler(String var1) throws NamingException;

   public abstract Callable wrapAsLongRunningTask(Callable var1);

   public abstract Runnable wrapAsLongRunningTask(Runnable var1);

   public abstract Callable wrapAsTenantTask(Callable var1);

   public abstract Runnable wrapAsTenantTask(Runnable var1);

   public abstract Executor wrapAsSerialExecutor(Executor var1);

   public abstract List shutdownAll();

   private static class SingletonHolder {
      private static final ExecutorFactory SINGLETON = ExecutorFactory.init();
   }
}
