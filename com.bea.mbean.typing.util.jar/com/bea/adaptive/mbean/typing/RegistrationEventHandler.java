package com.bea.adaptive.mbean.typing;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.PropertyHelper;

public abstract class RegistrationEventHandler {
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugMBeanEventHandler");
   protected static final DebugLogger debugSummaryLogger = DebugLogger.getDebugLogger("DebugMBeanEventHandlerSummary");
   protected static final DebugLogger workDebugLogger = DebugLogger.getDebugLogger("DebugMBeanEventHandlerWork");
   private static final long DEFAULT_TIMER_PERIOD = PropertyHelper.getLong("weblogic.mtu.handler.period", 2000L);
   private MBeanRegistrationQueue queue;
   private MBeanTypeUtil.RegHandler handler;
   private MBeanRegistrationWork work;
   private Executor executor;
   private Timer workTimer;
   private String handlerName;
   private AtomicBoolean workInProgress;
   private RegistrationStatistics statistics;
   private static DecimalFormat percentFormat = null;

   protected RegistrationEventHandler(String name, MBeanTypeUtil.RegHandler regHandler, String[] patterns, Executor exec) {
      this.workInProgress = new AtomicBoolean(false);
      this.statistics = new RegistrationStatistics();
      this.handlerName = name;
      this.handler = regHandler;
      this.executor = exec;
      this.queue = new MBeanRegistrationQueue(name, patterns);
   }

   RegistrationEventHandler(String name, MBeanTypeUtil.RegHandler regHandler, String[] patterns, Executor exec, Timer t) {
      this(name, regHandler, patterns, exec);
      this.workTimer = t;
   }

   public void initialize() throws Exception {
   }

   public void halt() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getMessagePrefix() + " halting JMX listener work...");
      }

      if (this.work != null) {
         this.work.release();
      }

      synchronized(this.queue) {
         this.queue.clear();
      }
   }

   public RegistrationStatistics getStatistics() {
      return this.statistics;
   }

   public String getHandlerName() {
      return this.handlerName;
   }

   protected MBeanTypeUtil.RegHandler getHandler() {
      return this.handler;
   }

   protected String getMessagePrefix() {
      String mtuName = this.handlerName;
      String messagePrefix = mtuName != null ? mtuName + ": " : "";
      return messagePrefix;
   }

   protected abstract void newInstance(ObjectName var1) throws Exception;

   protected void deletedInstance(ObjectName currentMBean) throws Exception {
      String canonicalName = currentMBean.getCanonicalName();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getMessagePrefix() + "Processing deleted MBean: " + canonicalName);
      }

      this.handler.instanceDeleted(currentMBean.getCanonicalName());
   }

   protected void processRegistrationEvent(ObjectName objectName, InstanceRegistrationEvent.EventType eventType) throws Exception {
      switch (eventType) {
         case ADD:
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getMessagePrefix() + "REGISTERED event, MBean " + objectName.getCanonicalName());
            }

            this.newInstance(objectName);
            break;
         case DELETE:
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getMessagePrefix() + "UNREGISTERED event, MBean " + objectName.getCanonicalName());
            }

            try {
               this.deletedInstance(objectName);
            } catch (InstanceNotFoundException var4) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(this.getMessagePrefix() + "Unable process unregistration event, instance not found; may have already been removed.  Error: " + var4.getMessage());
               }
            }
            break;
         case UNKNOWN:
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getMessagePrefix() + "UNEXPECTED event: " + eventType + "for MBean " + objectName.getCanonicalName());
            }
      }

   }

   protected void queueEvent(ObjectName oName, InstanceRegistrationEvent.EventType type) {
      switch (type) {
         case ADD:
         case DELETE:
            synchronized(this.queue) {
               if (!PatternsHelper.isOnIgnoreList(oName)) {
                  this.queue.enqueue(oName, type);
               }
            }

            this.scheduleWork();
            break;
         case UNKNOWN:
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getMessagePrefix() + " unknown eventType " + type + ", ignoring");
            }
      }

   }

   protected int queueSize() {
      return this.queue.size();
   }

   protected final void initQueue(Set currentMBeans) {
      synchronized(this.queue) {
         Iterator var3 = currentMBeans.iterator();

         while(true) {
            if (!var3.hasNext()) {
               break;
            }

            ObjectName name = (ObjectName)var3.next();
            if (!PatternsHelper.isOnIgnoreList(name)) {
               this.queue.enqueue(name, InstanceRegistrationEvent.EventType.ADD);
            }
         }
      }

      this.scheduleWork();
   }

   protected static String printStackTrace(Throwable x) {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] stackTrace = x.getStackTrace();
      sb.append("Exception: " + x.getMessage() + "\n");
      StackTraceElement[] var3 = stackTrace;
      int var4 = stackTrace.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         StackTraceElement e = var3[var5];
         sb.append("\tat " + e.getClassName() + "." + e.getMethodName() + "(" + e.getFileName() + ":" + e.getLineNumber() + ")\n");
      }

      if (x.getCause() != null) {
         sb.append("Caused by " + printStackTrace(x.getCause()));
      }

      sb.trimToSize();
      return sb.toString();
   }

   private void scheduleWork() {
      if (this.work == null) {
         if (workDebugLogger.isDebugEnabled()) {
            workDebugLogger.debug("Initializing registration queue work timer...");
         }

         this.work = new MBeanRegistrationWork();
         if (this.workTimer == null) {
            this.workTimer = new Timer("MTUTimer");
         }

         this.workTimer.schedule(new TimerTask() {
            public void run() {
               try {
                  if (!RegistrationEventHandler.this.workInProgress.get()) {
                     synchronized(RegistrationEventHandler.this.queue) {
                        if (RegistrationEventHandler.this.queue.size() > 0) {
                           if (RegistrationEventHandler.workDebugLogger.isDebugEnabled()) {
                              RegistrationEventHandler.workDebugLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "Running registrations ASYNC");
                           }

                           RegistrationEventHandler.this.executor.execute(RegistrationEventHandler.this.work);
                        }
                     }
                  } else if (RegistrationEventHandler.workDebugLogger.isDebugEnabled()) {
                     RegistrationEventHandler.workDebugLogger.debug("Work task in progress, skipping scheduling new job");
                  }
               } catch (Exception var4) {
                  MBeanTypingUtilLogger.logErrorSchedulingWork(RegistrationEventHandler.this.handlerName, var4);
               }

            }
         }, 0L, DEFAULT_TIMER_PERIOD);
      }

   }

   protected final class MBeanRegistrationWork implements Runnable {
      private boolean cancelled;
      private long startNanos;
      private long lastJobRunTime = System.currentTimeMillis();

      public MBeanRegistrationWork() {
      }

      public void release() {
         this.cancelled = true;
      }

      public void run() {
         RegistrationEventHandler.this.workInProgress.set(true);
         int processedCount = 0;
         long currentTimeMillis = System.currentTimeMillis();
         long timeDeltaMillis = currentTimeMillis - this.lastJobRunTime;
         this.lastJobRunTime = currentTimeMillis;
         int maxItemsToProcess = RegistrationEventHandler.this.queue.size();
         if (RegistrationEventHandler.debugSummaryLogger.isDebugEnabled() && maxItemsToProcess > 0) {
            RegistrationEventHandler.debugSummaryLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "Processing " + maxItemsToProcess + " events");
         }

         this.startNanos = System.nanoTime();

         while(!this.cancelled && processedCount < maxItemsToProcess) {
            InstanceRegistrationEvent event;
            synchronized(RegistrationEventHandler.this.queue) {
               event = RegistrationEventHandler.this.queue.dequeue();
               if (event == null) {
                  if (RegistrationEventHandler.debugLogger.isDebugEnabled()) {
                     RegistrationEventHandler.debugLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "Dequeued NULL event from " + RegistrationEventHandler.this.handlerName + " queue");
                  }
                  break;
               }
            }

            if (this.cancelled) {
               if (RegistrationEventHandler.debugSummaryLogger.isDebugEnabled()) {
                  RegistrationEventHandler.debugSummaryLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "In-flight work cancelled, halting queue processing.");
               }
            } else {
               int i = 0;

               while(i < 2) {
                  try {
                     ObjectName objectName = event.getObjectName();
                     InstanceRegistrationEvent.EventType eventType = event.getEvent();
                     RegistrationEventHandler.this.processRegistrationEvent(objectName, eventType);
                     ++processedCount;
                     break;
                  } catch (Throwable var11) {
                     if (RegistrationEventHandler.debugLogger.isDebugEnabled()) {
                        if (i == 0) {
                           RegistrationEventHandler.debugLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "Failed to access MBean on initial attempt , name: " + RegistrationEventHandler.this.handlerName + ", error: " + var11.getMessage());
                        } else {
                           RegistrationEventHandler.debugLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "Failed to access MBean on second attempt , name: " + RegistrationEventHandler.this.handlerName + ", error: " + var11.getMessage());
                        }
                     }

                     ++i;
                  }
               }
            }
         }

         long processingTimeNanos = System.nanoTime() - this.startNanos;
         RegistrationEventHandler.this.statistics.updateStatistics(processedCount, processingTimeNanos, RegistrationEventHandler.this.queue.getTotalTransientsHandled(), RegistrationEventHandler.this.queue.getTotalEventsReceived(), timeDeltaMillis);
         if (RegistrationEventHandler.debugSummaryLogger.isDebugEnabled() && processedCount > 0) {
            this.outputDebugStats(processedCount, processingTimeNanos);
         }

         RegistrationEventHandler.this.workInProgress.set(false);
      }

      private void outputDebugStats(int processedCount, long processingTimeNanos) {
         if (RegistrationEventHandler.percentFormat == null) {
            RegistrationEventHandler.percentFormat = new DecimalFormat("#.##");
         }

         RegistrationEventHandler.debugSummaryLogger.debug(RegistrationEventHandler.this.getMessagePrefix() + "Processing complete, processed " + processedCount + " events in " + processingTimeNanos + "ns,  total events received: " + RegistrationEventHandler.this.statistics.getTotalEventsReceived() + ", total events processed: " + RegistrationEventHandler.this.statistics.getTotalProcessedRegistrationEvents() + ", % filtered: " + RegistrationEventHandler.percentFormat.format(RegistrationEventHandler.this.statistics.getPercentEventsFiltered()) + ", event rate/sec: " + RegistrationEventHandler.this.statistics.getCurrentIntervalRatePerSec() + ", average processing time: " + RegistrationEventHandler.this.statistics.getAverageProcessingTimeNanos() + ", Total transient MBeans handled: " + RegistrationEventHandler.this.statistics.getTotalTransientsCount() + ", transients this cycle = " + RegistrationEventHandler.this.statistics.getCurrentTransientsCount() + ", transients/sec: " + RegistrationEventHandler.this.statistics.getCurrentTransientRatePerSec() + ", seconds since last invoke: " + RegistrationEventHandler.this.statistics.getCurrentIntervalTimeMillis() / 1000L);
      }
   }
}
