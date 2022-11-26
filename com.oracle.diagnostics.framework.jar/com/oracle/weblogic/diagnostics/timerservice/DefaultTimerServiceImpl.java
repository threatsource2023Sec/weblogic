package com.oracle.weblogic.diagnostics.timerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import weblogic.diagnostics.debug.DebugLogger;

class DefaultTimerServiceImpl implements TimerService {
   private static final int POOL_SIZE = 4;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsTimerService");
   private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4, new ThreadFactory() {
      public Thread newThread(Runnable r) {
         Thread th = new Thread(r);
         th.setDaemon(true);
         return th;
      }
   });
   private HashMap listenerMap = new HashMap();

   public synchronized void registerListener(TimerListener listener) {
      TimerInfoInternal timerInfo = (TimerInfoInternal)this.listenerMap.get(listener.getFrequency());
      if (timerInfo == null) {
         timerInfo = new TimerInfoInternal(listener.getFrequency());
         this.listenerMap.put(listener.getFrequency(), timerInfo);
      }

      timerInfo.addListener(listener);
   }

   public synchronized boolean unregisterListener(TimerListener listener) {
      boolean result = false;
      TimerInfoInternal timerInfo = (TimerInfoInternal)this.listenerMap.get(listener.getFrequency());
      if (timerInfo != null) {
         result = timerInfo.removeListener(listener);
         if (timerInfo.numListeners() == 0) {
            this.listenerMap.remove(listener.getFrequency());
         }
      }

      return result;
   }

   private class TimerInfoInternal implements Runnable {
      private int frequency;
      private ArrayList listeners;
      private ScheduledFuture future;

      private TimerInfoInternal(int frequency) {
         this.frequency = frequency;
      }

      public void run() {
         ArrayList cloneList = null;
         synchronized(this) {
            cloneList = (ArrayList)this.listeners.clone();
         }

         Iterator var2 = cloneList.iterator();

         while(var2.hasNext()) {
            TimerListener l = (TimerListener)var2.next();

            try {
               l.timerExpired();
            } catch (Throwable var5) {
               throw new RuntimeException(var5);
            }
         }

      }

      synchronized void addListener(TimerListener l) {
         if (this.listeners == null) {
            this.listeners = new ArrayList();
         }

         this.listeners.add(l);
         this.scheduleTimer();
      }

      synchronized boolean removeListener(TimerListener l) {
         boolean ret = false;
         if (this.listeners != null) {
            ret = this.listeners.remove(l);
            if (this.listeners.size() == 0) {
               this.cancelTimer();
               this.listeners = null;
            }
         }

         return ret;
      }

      private void cancelTimer() {
         if (this.future != null && !this.future.isDone()) {
            this.future.cancel(true);
         }

      }

      private void scheduleTimer() {
         if (this.future == null) {
            if (DefaultTimerServiceImpl.debugLogger.isDebugEnabled()) {
               DefaultTimerServiceImpl.debugLogger.debug("Scheduling timer at " + this.frequency + " second intervals");
            }

            this.future = DefaultTimerServiceImpl.this.executor.scheduleAtFixedRate(this, 0L, (long)this.frequency, TimeUnit.SECONDS);
         }

      }

      public synchronized int numListeners() {
         return this.listeners != null ? this.listeners.size() : 0;
      }

      // $FF: synthetic method
      TimerInfoInternal(int x1, Object x2) {
         this(x1);
      }
   }
}
