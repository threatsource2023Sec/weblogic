package org.apache.commons.pool.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Timer;
import java.util.TimerTask;

class EvictionTimer {
   private static Timer _timer;
   private static int _usageCount;

   private EvictionTimer() {
   }

   static synchronized void schedule(TimerTask task, long delay, long period) {
      if (null == _timer) {
         ClassLoader ccl = (ClassLoader)AccessController.doPrivileged(new PrivilegedGetTccl());

         try {
            AccessController.doPrivileged(new PrivilegedSetTccl(EvictionTimer.class.getClassLoader()));
            _timer = new Timer(true);
         } finally {
            AccessController.doPrivileged(new PrivilegedSetTccl(ccl));
         }
      }

      ++_usageCount;
      _timer.schedule(task, delay, period);
   }

   static synchronized void cancel(TimerTask task) {
      task.cancel();
      --_usageCount;
      if (_usageCount == 0) {
         _timer.cancel();
         _timer = null;
      }

   }

   private static class PrivilegedSetTccl implements PrivilegedAction {
      private final ClassLoader cl;

      PrivilegedSetTccl(ClassLoader cl) {
         this.cl = cl;
      }

      public ClassLoader run() {
         Thread.currentThread().setContextClassLoader(this.cl);
         return null;
      }
   }

   private static class PrivilegedGetTccl implements PrivilegedAction {
      private PrivilegedGetTccl() {
      }

      public ClassLoader run() {
         return Thread.currentThread().getContextClassLoader();
      }

      // $FF: synthetic method
      PrivilegedGetTccl(Object x0) {
         this();
      }
   }
}
