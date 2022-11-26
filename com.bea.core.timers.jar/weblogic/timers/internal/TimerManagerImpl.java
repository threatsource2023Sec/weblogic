package weblogic.timers.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.kernel.QueueFullException;
import weblogic.timers.CancelTimerListener;
import weblogic.timers.RuntimeDomainSelector;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.UnsyncCircularQueue;
import weblogic.work.WorkManager;

public class TimerManagerImpl implements TimerManager {
   private static final int STOPPING = 1;
   private static final int STOPPED = 2;
   private static final int SUSPENDING = 3;
   private static final int SUSPENDED = 4;
   private static final int RUNNING = 5;
   private static ConcurrentHashMap timerManagers = new ConcurrentHashMap();
   private TimerThread timerThread;
   private String name;
   private final Executor executor;
   private final String domainId;
   private int executing;
   private volatile int state;
   private final AtomicLong counter;
   private CountDownLatch listenersCompletionLatch;
   private volatile TimerSet timerSet;
   private final TimerFactory timerFactory;
   protected int idx = -1;
   private static final TimerSet STOPPED_STATE_TIMERSET = new TimerSet() {
      public boolean add(TimerImpl t) {
         t.cleanup();
         throw new IllegalStateException();
      }

      public boolean remove(TimerImpl t) {
         return false;
      }

      public boolean update(TimerImpl t) {
         return false;
      }

      public TimerImpl[] getNotAfter(long timeout) {
         return new TimerImpl[0];
      }

      public boolean isEmpty() {
         return true;
      }
   };

   TimerManagerImpl(TimerThread timerThread, String name, Executor executor, TimerFactory timerFactory) {
      this.timerThread = timerThread;
      this.name = name;
      this.executor = executor;
      this.domainId = RuntimeDomainSelector.getDomain();
      this.counter = new AtomicLong(0L);
      this.timerSet = new TimerSet();
      this.timerFactory = timerFactory;
      this.state = 5;
   }

   static void clearAll() {
      timerManagers = new ConcurrentHashMap();
   }

   public void stop() {
      try {
         this.waitForStop(0L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   public boolean isStopped() {
      return this.state == 2 || this.timerThread.isStopped();
   }

   public boolean isStopping() {
      return this.state == 1 || this.isStopped();
   }

   public boolean isSuspended() {
      return this.state == 4;
   }

   public boolean isSuspending() {
      return this.state == 3 || this.isSuspended();
   }

   public void suspend() {
      try {
         this.waitForSuspend(0L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   public boolean waitForSuspend(long timeout_ms) throws InterruptedException, IllegalStateException, IllegalArgumentException {
      synchronized(this) {
         if (this.state <= 2) {
            throw new IllegalStateException("Cannot suspend a TimerManager that is in STOPPED state");
         }

         if (this.state == 4) {
            return true;
         }

         this.state = 3;
         this.timerThread.unregister(this);
         if (this.listenersCompletionLatch == null) {
            this.listenersCompletionLatch = new CountDownLatch(this.executing);
         }
      }

      if (timeout_ms == 0L) {
         this.listenersCompletionLatch.await();
      } else {
         boolean completed = this.listenersCompletionLatch.await(timeout_ms, TimeUnit.MILLISECONDS);
         if (!completed) {
            return false;
         }
      }

      synchronized(this) {
         if (this.state != 3) {
            throw new IllegalStateException("TimerManager state changed to " + this.state + " while suspending. waitForSuspend is aborted.");
         } else {
            this.state = 4;
            return true;
         }
      }
   }

   public boolean waitForStop(long timeout_ms) throws InterruptedException, IllegalArgumentException {
      TimerImpl[] timers;
      synchronized(this) {
         if (this.state == 2) {
            return true;
         }

         this.state = 1;
         TimerSet prev = this.timerSet;
         this.timerSet = STOPPED_STATE_TIMERSET;
         this.timerThread.unregister(this);
         timers = prev.getNotAfter(Long.MAX_VALUE);
         prev.clear();
         this.executing += timers.length;
         if (this.listenersCompletionLatch == null) {
            this.listenersCompletionLatch = new CountDownLatch(this.executing);
         }
      }

      TimerImpl[] var4 = timers;
      int var34 = timers.length;

      for(int var6 = 0; var6 < var34; ++var6) {
         TimerImpl timer = var4[var6];
         timer.setStopped();

         try {
            this.executor.execute(timer);
         } catch (QueueFullException var28) {
            this.complete(timer);
         } catch (UnsyncCircularQueue.FullQueueException var29) {
            this.complete(timer);
         }
      }

      boolean var24 = false;

      boolean var35;
      Map timerManagers;
      label195: {
         try {
            var24 = true;
            if (timeout_ms == 0L) {
               this.listenersCompletionLatch.await();
            } else {
               boolean completed = this.listenersCompletionLatch.await(timeout_ms, TimeUnit.MILLISECONDS);
               if (!completed) {
                  var35 = false;
                  var24 = false;
                  break label195;
               }
            }

            synchronized(this) {
               assert this.state == 1 || this.state == 2;

               this.state = 2;
               var35 = true;
               var24 = false;
            }
         } finally {
            if (var24) {
               Map timerManagers = getTimerManagersMapForDomain(this.domainId);
               synchronized(timerManagers) {
                  timerManagers.remove(this.name);
               }
            }
         }

         timerManagers = getTimerManagersMapForDomain(this.domainId);
         synchronized(timerManagers) {
            timerManagers.remove(this.name);
            return var35;
         }
      }

      timerManagers = getTimerManagersMapForDomain(this.domainId);
      synchronized(timerManagers) {
         timerManagers.remove(this.name);
         return var35;
      }
   }

   public synchronized void resume() {
      if (this.state != 5) {
         if (this.state <= 2) {
            throw new IllegalStateException("Cannot resume a TimerManager that is in STOPPED state");
         } else {
            this.state = 5;
            if (!this.timerSet.isEmpty()) {
               this.timerThread.register(this);
               this.ping();
            }

            if (this.listenersCompletionLatch != null) {
               for(long l = this.listenersCompletionLatch.getCount(); l > 0L; --l) {
                  this.listenersCompletionLatch.countDown();
               }

               this.listenersCompletionLatch = null;
            }

         }
      }
   }

   public Timer schedule(TimerListener listener, long delay) {
      return this.schedule(listener, delay, 0L);
   }

   public Timer schedule(TimerListener listener, Date time) {
      return this.schedule(listener, time, 0L);
   }

   public Timer schedule(TimerListener listener, long delay, long period) {
      if (delay < 0L) {
         throw new IllegalArgumentException("Delay is negative.");
      } else if (period < 0L) {
         throw new IllegalArgumentException("Period is negative.");
      } else if (this.state == 2) {
         throw new IllegalStateException("TimerManager is in STOPPED state");
      } else {
         return this.add(this.timerFactory.createTimerImpl(this, listener, this.normalizeTimeout(delay), -period));
      }
   }

   long normalizeTimeout(long positiveDelay) {
      long now = System.currentTimeMillis();
      long normal = now + positiveDelay;
      return normal < now ? 9223372036854773807L : normal;
   }

   public Timer schedule(TimerListener listener, Date firstTime, long period) {
      if (period < 0L) {
         throw new IllegalArgumentException("Period is negative.");
      } else if (this.state == 2) {
         throw new IllegalStateException("TimerManager is in STOPPED state");
      } else {
         return this.add(this.timerFactory.createTimerImpl(this, listener, firstTime.getTime(), -period));
      }
   }

   public Timer schedule(TimerListener listener, ScheduleExpression schedule) {
      if (this.state == 2) {
         throw new IllegalStateException("TimerManager is in STOPPED state");
      } else {
         ScheduleExpressionWrapper scheduleWrapper = ScheduleExpressionWrapper.create(schedule);
         CalendarTimerImpl newTimer = this.timerFactory.createCalendarTimerImpl(this, listener, scheduleWrapper);
         return (Timer)(!newTimer.isExpired() || schedule.getFirstTimeout() != null && schedule.getFirstTimeout().getTime() == newTimer.getTimeout() ? this.add(newTimer) : newTimer);
      }
   }

   public Timer scheduleAtFixedRate(TimerListener listener, Date firstTime, long period) {
      if (period < 0L) {
         throw new IllegalArgumentException("Period is negative.");
      } else {
         return this.add(this.timerFactory.createTimerImpl(this, listener, firstTime.getTime(), period));
      }
   }

   public Timer scheduleAtFixedRate(TimerListener listener, long delay, long period) {
      if (delay < 0L) {
         throw new IllegalArgumentException("Delay is negative.");
      } else if (period < 0L) {
         throw new IllegalArgumentException("Period is negative.");
      } else {
         return this.add(this.timerFactory.createTimerImpl(this, listener, this.normalizeTimeout(delay), period));
      }
   }

   boolean cancel(TimerImpl timer) {
      if (timer.isCancelled()) {
         return false;
      } else {
         timer.setCancelled(true);
         synchronized(this) {
            if (!this.timerSet.remove(timer)) {
               return false;
            }

            if (timer.running) {
               if (timer.getPeriod() == 0L && !timer.isCalendarTimer()) {
                  return false;
               }

               return true;
            }

            ++this.executing;
         }

         TimerListener listener = timer.getListener();
         if (listener instanceof CancelTimerListener) {
            try {
               this.executor.execute(timer);
            } catch (UnsyncCircularQueue.FullQueueException var4) {
               this.complete(timer);
            }
         } else {
            this.complete(timer);
         }

         return true;
      }
   }

   private Timer add(TimerImpl timer) {
      timer.setCounter(this.getNextCounter());
      if (this.timerSet.add(timer) && this.state == 5) {
         synchronized(this) {
            if (this.state == 5 && this.idx == -1) {
               this.timerThread.register(this);
            }
         }

         this.ping();
      }

      return timer;
   }

   private void update(TimerImpl timer) {
      timer.setCounter(this.getNextCounter());
      if (this.timerSet.update(timer)) {
         this.ping();
      }

   }

   private void remove(TimerImpl timer) {
      timer.cleanup();
      this.timerSet.remove(timer);
      if (this.timerSet.isEmpty() && this.idx != -1) {
         synchronized(this) {
            if (this.timerSet.isEmpty() && this.idx != -1) {
               this.timerThread.unregister(this);
               return;
            }
         }
      }

   }

   void execute() {
      TimerImpl[] timers;
      synchronized(this) {
         if (this.isSuspending()) {
            return;
         }

         timers = this.timerSet.getNotAfter(System.currentTimeMillis());
         this.executing += timers.length;
      }

      TimerImpl[] var2 = timers;
      int var3 = timers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TimerImpl timer = var2[var4];
         if (this.isStopped()) {
            timer.setStopped();
         }

         try {
            this.executor.execute(timer);
         } catch (QueueFullException var7) {
            this.complete(timer);
         } catch (UnsyncCircularQueue.FullQueueException var8) {
            this.complete(timer);
         }
      }

      this.ping();
   }

   void complete(TimerImpl timer) {
      synchronized(this) {
         --this.executing;
         if (this.state != 5 && this.listenersCompletionLatch != null) {
            this.listenersCompletionLatch.countDown();
         }
      }

      if (timer.isCancelled()) {
         this.remove(timer);
      } else if (!timer.incrementTimeout()) {
         this.remove(timer);
      } else {
         if (this.isStopping()) {
            this.remove(timer);
         } else {
            this.update(timer);
         }

      }
   }

   private void ping() {
      long wakeup = this.timerSet.peakMin();
      if (wakeup > 0L) {
         this.timerThread.ping(wakeup);
      }

   }

   long earliestWakeup() {
      return this.timerSet.peakMin();
   }

   Executor getExecutor() {
      return this.executor;
   }

   public String getExecutorName() {
      return this.executor instanceof WorkManager ? ((WorkManager)this.executor).getName() : "" + this.executor;
   }

   private long getNextCounter() {
      return this.counter.incrementAndGet();
   }

   public String getName() {
      return this.name;
   }

   TimerImpl[] getTimers() {
      return this.timerSet.getAll();
   }

   public static TimerManagerImpl getTimerManager(String name, Executor exec, TimerFactory timerFactory) {
      if (exec == null) {
         throw new IllegalArgumentException("executor == null");
      } else {
         String domainId = RuntimeDomainSelector.getDomain();
         Map timerManagers = getTimerManagersMapForDomain(domainId);
         synchronized(timerManagers) {
            TimerManagerImpl timerManager = (TimerManagerImpl)timerManagers.get(name);
            if (timerManager != null) {
               if (timerManager.getExecutor() == null) {
                  return timerManager;
               } else if (exec.equals(timerManager.getExecutor())) {
                  return timerManager;
               } else {
                  throw new IllegalArgumentException("Existing timer manager has different work manager.\nTimerManager requested: " + name + " in domain: " + domainId + "\nTimerManager  obtained: " + timerManager.getName() + " in domain: " + timerManager.domainId + "\nWorkManager  requested: " + exec + "\nWorkManager   obtained: " + timerManager.getExecutor());
               }
            } else {
               timerManager = new TimerManagerImpl(TimerThread.getTimerThread(), name, exec, timerFactory);
               timerManagers.put(name, timerManager);
               return timerManager;
            }
         }
      }
   }

   static void stopAllTimers() {
      Iterator var0 = timerManagers.values().iterator();

      while(var0.hasNext()) {
         Map byDomainManagers = (Map)var0.next();
         TimerManagerImpl[] managers;
         synchronized(byDomainManagers) {
            managers = (TimerManagerImpl[])byDomainManagers.values().toArray(new TimerManagerImpl[byDomainManagers.size()]);
         }

         TimerManagerImpl[] var3 = managers;
         int var4 = managers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TimerManagerImpl timerManager = var3[var5];
            timerManager.stop();
         }
      }

   }

   static TimerManagerImpl[] getAllTimerManagers() {
      Map timerManagers = getTimerManagersMapForDomain(RuntimeDomainSelector.getDomain());
      synchronized(timerManagers) {
         return (TimerManagerImpl[])timerManagers.values().toArray(new TimerManagerImpl[timerManagers.size()]);
      }
   }

   private static Map getTimerManagersMapForDomain(String domainId) {
      Map tmMap = (Map)timerManagers.get(domainId);
      if (tmMap != null) {
         return tmMap;
      } else {
         Map newTmMap = new HashMap();
         tmMap = (Map)timerManagers.putIfAbsent(domainId, newTmMap);
         return (Map)(tmMap == null ? newTmMap : tmMap);
      }
   }

   public static void cleanupForPartition() {
      String domainId = RuntimeDomainSelector.getDomain();
      Map tmMap = (Map)timerManagers.remove(domainId);
      if (tmMap != null) {
         Iterator var2 = tmMap.values().iterator();

         while(var2.hasNext()) {
            TimerManagerImpl mgrs = (TimerManagerImpl)var2.next();
            mgrs.stop();
         }
      }

      TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
      if (timerManagerFactory instanceof TimerManagerFactoryImpl) {
         ((TimerManagerFactoryImpl)timerManagerFactory).cleanupForPartition();
      }

   }

   public String toString() {
      return "TimerManager '" + this.getName() + "' that uses WorkManager '" + this.executor + "'";
   }

   public interface Executor {
      void execute(Runnable var1);

      boolean equals(Executor var1);
   }
}
