package com.oracle.jrf.concurrent.impl;

import com.oracle.jrf.concurrent.ExecutorFactory;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedExecutors;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.server.GlobalServiceLocator;

public class WlsExecutorFactory extends ExecutorFactory {
   private static final String CHECK_PARTITION_TASK = "weblogic.concurrent.CHECK_USER_OBJECT";

   public ExecutorService getExecutor(String name) throws NamingException {
      Util.checkNull(name);
      ExecutorService es = null;

      try {
         es = (ExecutorService)this.lookup(name);
      } catch (NamingException var4) {
      }

      if (es != null) {
         return es;
      } else {
         es = (ExecutorService)GlobalServiceLocator.getServiceLocator().getService(ManagedExecutorService.class, name, new Annotation[0]);
         if (es != null) {
            return es;
         } else {
            es = (ExecutorService)GlobalServiceLocator.getServiceLocator().getService(ManagedScheduledExecutorService.class, name, new Annotation[0]);
            if (es != null) {
               return es;
            } else {
               throw new NameNotFoundException("Failed to lookup ExecutorService " + name + " from JNDI and HK2 service.");
            }
         }
      }
   }

   private Object lookup(String name) throws NamingException {
      Util.checkNull(name);
      return (new InitialContext()).lookup(name);
   }

   public ScheduledExecutorService getScheduler(String name) throws NamingException {
      Util.checkNull(name);
      ScheduledExecutorService ses = null;

      try {
         ses = (ScheduledExecutorService)this.lookup(name);
      } catch (NamingException var4) {
      }

      if (ses != null) {
         return ses;
      } else {
         ses = (ScheduledExecutorService)GlobalServiceLocator.getServiceLocator().getService(ManagedScheduledExecutorService.class, name, new Annotation[0]);
         if (ses != null) {
            return ses;
         } else {
            throw new NameNotFoundException("Failed to lookup ScheduledExecutorService " + name + " from JNDI and HK2 service.");
         }
      }
   }

   public Callable wrapAsLongRunningTask(Callable task) {
      Util.checkNull(task);
      Map execProps = new HashMap();
      execProps.put("javax.enterprise.concurrent.LONGRUNNING_HINT", Boolean.TRUE.toString());
      return ManagedExecutors.managedTask(task, execProps, (ManagedTaskListener)null);
   }

   public Runnable wrapAsLongRunningTask(Runnable task) {
      Util.checkNull(task);
      Map execProps = new HashMap();
      execProps.put("javax.enterprise.concurrent.LONGRUNNING_HINT", Boolean.TRUE.toString());
      return ManagedExecutors.managedTask(task, execProps, (ManagedTaskListener)null);
   }

   public Callable wrapAsTenantTask(Callable task) {
      Util.checkNull(task);
      Map execProps = new HashMap();
      execProps.put("weblogic.concurrent.CHECK_USER_OBJECT", Boolean.FALSE.toString());
      return ManagedExecutors.managedTask(task, execProps, (ManagedTaskListener)null);
   }

   public Runnable wrapAsTenantTask(Runnable task) {
      Util.checkNull(task);
      Map execProps = new HashMap();
      execProps.put("weblogic.concurrent.CHECK_USER_OBJECT", Boolean.FALSE.toString());
      return ManagedExecutors.managedTask(task, execProps, (ManagedTaskListener)null);
   }

   public Executor wrapAsSerialExecutor(Executor e) {
      Util.checkNull(e);
      return new SerialExecutor(e);
   }

   public List shutdownAll() {
      return Collections.EMPTY_LIST;
   }
}
