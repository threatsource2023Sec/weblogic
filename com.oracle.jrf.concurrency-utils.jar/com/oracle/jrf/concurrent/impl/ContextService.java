package com.oracle.jrf.concurrent.impl;

import com.oracle.jrf.mt.tenant.internal.se.SEThreadLocal;
import com.oracle.jrf.mt.tenant.runtime.TenantContext;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

class ContextService {
   private static final Logger logger = Logger.getLogger(ContextService.class.getName());

   public static ContextService getInstance() {
      return ContextService.SingletonHolder.SINGLETON;
   }

   public Callable decorate(final Callable callable) {
      if (logger.isLoggable(Level.FINER)) {
         logger.entering("ContextService", "decorate", new Object[]{callable});
      }

      final ClassLoader tcl = Thread.currentThread().getContextClassLoader();
      final AccessControlContext acc = AccessController.getContext();
      final TenantContext tc = SEThreadLocal.get();
      return new Callable() {
         public Object call() throws Exception {
            TenantContext oldTc = SEThreadLocal.get();
            ClassLoader oldCl = Thread.currentThread().getContextClassLoader();

            Object var4;
            try {
               Thread.currentThread().setContextClassLoader(tcl);
               SEThreadLocal.set(tc);
               PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     return callable.call();
                  }
               };

               try {
                  var4 = AccessController.doPrivileged(action, acc);
               } catch (PrivilegedActionException var8) {
                  throw var8.getException();
               }
            } finally {
               SEThreadLocal.set(oldTc);
               Thread.currentThread().setContextClassLoader(oldCl);
            }

            return var4;
         }
      };
   }

   public Runnable decorate(Runnable runnable) {
      final Callable callable = this.decorate(Executors.callable(runnable));
      return new Runnable() {
         public void run() {
            try {
               callable.call();
            } catch (RuntimeException var2) {
               throw var2;
            } catch (Exception var3) {
               var3.printStackTrace();

               assert false;
            }

         }
      };
   }

   public Collection decorate(Collection tasks) {
      Collection wrappedTasks = new ArrayList(tasks.size());
      Iterator var3 = tasks.iterator();

      while(var3.hasNext()) {
         Callable task = (Callable)var3.next();
         wrappedTasks.add(this.decorate(task));
      }

      return wrappedTasks;
   }

   private static class SingletonHolder {
      private static final ContextService SINGLETON = init();

      private static ContextService init() {
         Iterator services = ServiceLoader.load(ContextService.class).iterator();
         return services.hasNext() ? (ContextService)services.next() : new ContextService();
      }
   }
}
