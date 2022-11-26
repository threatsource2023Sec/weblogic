package weblogic.time.common.internal;

import java.util.Date;
import java.util.Hashtable;
import weblogic.common.T3MiscLogger;
import weblogic.kernel.Kernel;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.work.WorkManagerFactory;

public final class TimeEventGenerator implements Runnable {
   private static final int HIGH_THREAD_PRIORITY = 9;
   private static final String RUNTIME_MBEAN_NAME = "TimeEventGenerator";
   private static final Date LAUNCH = new Date();
   private static TimeEventGenerator singleton;
   private final Hashtable allTriggers = new Hashtable();
   private final Thread theThread;
   private final TimeTable timeTriggers;
   private long nexttime;
   private long startMillis;
   private int triggerInstanceCount;
   private int triggerExpiredCount;

   public static Date getLaunch() {
      return LAUNCH;
   }

   public static synchronized TimeEventGenerator init(ThreadGroup tg) {
      if (singleton != null) {
         throw new IllegalStateException("Attempt to double initialize");
      } else {
         singleton = new TimeEventGenerator(tg, 9);
         singleton.start();
         return singleton;
      }
   }

   public static synchronized TimeEventGenerator getOne() {
      return getHighPriorityOne();
   }

   public static synchronized TimeEventGenerator getHighPriorityOne() {
      return singleton != null ? singleton : init((ThreadGroup)null);
   }

   public static int deltaMillis(long beginMillis) {
      return beginMillis == 0L ? Integer.MAX_VALUE : (int)(System.currentTimeMillis() - beginMillis);
   }

   public static int deltaSecs(long beginSecs) {
      return beginSecs == 0L ? Integer.MAX_VALUE : (int)(getCurrentSecs() - beginSecs);
   }

   public static int deltaMins(long beginMins) {
      return beginMins == 0L ? Integer.MAX_VALUE : (int)(getCurrentMins() - beginMins);
   }

   public static long getCurrentMillis() {
      return System.currentTimeMillis();
   }

   public static long getCurrentSecs() {
      return (System.currentTimeMillis() + 500L) / 1000L;
   }

   public static long getCurrentMins() {
      return (getCurrentSecs() + 30L) / 60L;
   }

   private TimeEventGenerator(ThreadGroup tg, int priority) {
      this.theThread = new Thread(tg, this, "weblogic.time.TimeEventGenerator");
      this.theThread.setDaemon(true);
      this.theThread.setPriority(priority);
      this.timeTriggers = new TimeTable(100, 1000L, System.currentTimeMillis());
   }

   public void start() {
      this.theThread.start();
   }

   public void stop() {
      this.theThread.stop();
      if (this == singleton) {
         singleton = null;
      }

   }

   public void run() {
      this.startMillis = System.currentTimeMillis();

      while(true) {
         while(true) {
            try {
               this.timeTriggers.execute(System.currentTimeMillis(), WorkManagerFactory.getInstance().getDefault(), true);
               this.timeTriggers.snooze();
            } catch (ThreadDeath var2) {
               if (Kernel.isServer()) {
                  throw var2;
               }
            } catch (Throwable var3) {
               T3MiscLogger.logThrowable(var3);
            }
         }
      }
   }

   public void insert(InternalScheduledTrigger st) {
      this.timeTriggers.insert(st);
   }

   public boolean delete(InternalScheduledTrigger st) {
      if (this.timeTriggers.delete(st)) {
         this.dropTriggerID();
         return true;
      } else {
         return false;
      }
   }

   public void deleted(InternalScheduledTrigger st) {
      this.dropTriggerID();
   }

   public void register(Object key, ScheduledTriggerDef timeTrigger) {
      this.allTriggers.put(key, timeTrigger);
   }

   public ScheduledTriggerDef unregister(Object key) {
      return (ScheduledTriggerDef)this.allTriggers.remove(key);
   }

   public ScheduledTriggerDef registered(Object key) {
      return (ScheduledTriggerDef)this.allTriggers.get(key);
   }

   public final synchronized int nextTriggerID() {
      return this.triggerInstanceCount++;
   }

   public final synchronized void dropTriggerID() {
      ++this.triggerExpiredCount;
   }

   public int getTriggerInstanceCount() {
      return this.triggerInstanceCount;
   }

   public int getTriggerExpiredCount() {
      return this.triggerExpiredCount;
   }

   public final int getAvgExecCount() {
      int elapsedMillis = (int)(System.currentTimeMillis() - this.startMillis);
      return this.timeTriggers.executeCount() * 1000 * 60 / elapsedMillis;
   }

   public int getExecuteCount() {
      return this.timeTriggers.executeCount();
   }

   public int getExceptionCount() {
      return this.timeTriggers.exceptionCount();
   }

   public String getRuntimeName() {
      return "TimeEventGenerator";
   }
}
