package weblogic.diagnostics.timerservice;

import com.oracle.weblogic.diagnostics.timerservice.TimerListener;
import com.oracle.weblogic.diagnostics.timerservice.TimerService;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

@Service
@Singleton
class WLDFTimerServiceImpl implements TimerService {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsTimer");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private TimerManagerFactory timerFactory;
   private TimerManager timerManager;
   private ConcurrentHashMap listenerMap = new ConcurrentHashMap();

   WLDFTimerServiceImpl() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WLDFTimerService, using timer manager factory with System WM");
      }

      this.timerFactory = TimerManagerFactory.getTimerManagerFactory();
      this.timerManager = this.timerFactory.getTimerManager("WLDFTimerManager", WorkManagerFactory.getInstance().getDefault());
   }

   public void registerListener(TimerListener listener) {
      Object key = this.getListenerKey(listener);
      TimerInfoInternal timerInfo = (TimerInfoInternal)this.listenerMap.get(key);
      if (timerInfo == null) {
         if (key instanceof ScheduleExpression) {
            timerInfo = new CalendarTimerInfoInternal((ScheduleExpression)key);
         } else {
            timerInfo = new PeriodicTimerInfoInternal((Integer)key);
         }

         this.listenerMap.put(key, timerInfo);
      }

      ((TimerInfoInternal)timerInfo).addListener(listener);
   }

   private Object getListenerKey(TimerListener listener) {
      Object key = listener.getFrequency();
      if (listener instanceof WLDFTimerListener) {
         WLDFTimerListener wldfTimerListener = (WLDFTimerListener)listener;
         if (wldfTimerListener.useCalendarSchedule()) {
            key = wldfTimerListener.getSchedule();
         }
      }

      return key;
   }

   public boolean unregisterListener(TimerListener listener) {
      Object key = this.getListenerKey(listener);
      boolean result = false;
      TimerInfoInternal timerInfo = (TimerInfoInternal)this.listenerMap.get(key);
      if (timerInfo != null) {
         result = timerInfo.removeListener(listener);
         if (timerInfo.numListeners() == 0) {
            this.listenerMap.remove(listener.getFrequency());
         }
      }

      return result;
   }

   private class CalendarTimerInfoInternal extends TimerInfoInternal {
      private ScheduleExpression schedule;
      private String key;

      public CalendarTimerInfoInternal(ScheduleExpression sched) {
         super(null);
         this.schedule = sched;
         this.key = this.schedule.toString();
      }

      protected Timer createTimer() throws PrivilegedActionException {
         return WLDFTimerServiceImpl.this.timerManager.schedule(this, this.schedule);
      }

      protected String getKey() {
         return this.key;
      }
   }

   private class PeriodicTimerInfoInternal extends TimerInfoInternal {
      private long frequency;
      private String key;

      public PeriodicTimerInfoInternal(int frequency) {
         super(null);
         this.frequency = (long)frequency;
         this.key = "" + frequency;
      }

      protected String getKey() {
         return this.key;
      }

      protected Timer createTimer() throws PrivilegedActionException {
         return WLDFTimerServiceImpl.this.timerManager.scheduleAtFixedRate(this, 0L, this.frequency * 1000L);
      }
   }

   private abstract class TimerInfoInternal implements weblogic.timers.TimerListener {
      private Timer timer;
      private ArrayList listeners;

      private TimerInfoInternal() {
      }

      public void timerExpired(final Timer timer) {
         try {
            SecurityServiceManager.runAs(WLDFTimerServiceImpl.KERNEL_ID, SecurityServiceManager.getCurrentSubject(WLDFTimerServiceImpl.KERNEL_ID), new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  ArrayList cloneList = null;
                  synchronized(TimerInfoInternal.this.listeners) {
                     cloneList = (ArrayList)TimerInfoInternal.this.listeners.clone();
                  }

                  if (WLDFTimerServiceImpl.debugLogger.isDebugEnabled()) {
                     WLDFTimerServiceImpl.debugLogger.debug("Timer expired for " + timer.getPeriod() + ", notifying " + cloneList.size() + " listeners");
                  }

                  Iterator var2 = cloneList.iterator();

                  while(var2.hasNext()) {
                     TimerListener l = (TimerListener)var2.next();
                     l.timerExpired();
                  }

                  return null;
               }
            });
         } catch (Throwable var3) {
            throw new RuntimeException(var3);
         }
      }

      public synchronized void addListener(TimerListener l) {
         if (this.listeners == null) {
            this.listeners = new ArrayList();
         }

         this.listeners.add(l);
         if (this.timer == null) {
            this.scheduleTimer();
         }

      }

      public synchronized boolean removeListener(TimerListener l) {
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

      public int numListeners() {
         return this.listeners != null ? this.listeners.size() : 0;
      }

      protected void cancelTimer() {
         if (WLDFTimerServiceImpl.debugLogger.isDebugEnabled()) {
            WLDFTimerServiceImpl.debugLogger.debug("Canceling timer for period " + this.getKey());
         }

         this.timer.cancel();
         this.timer = null;
      }

      protected abstract String getKey();

      protected abstract Timer createTimer() throws PrivilegedActionException;

      protected void scheduleTimer() {
         try {
            if (WLDFTimerServiceImpl.debugLogger.isDebugEnabled()) {
               WLDFTimerServiceImpl.debugLogger.debug("Scheduling fixed-rate timer for " + this.getKey());
            }

            this.timer = (Timer)SecurityServiceManager.runAs(WLDFTimerServiceImpl.KERNEL_ID, SecurityServiceManager.getCurrentSubject(WLDFTimerServiceImpl.KERNEL_ID), new PrivilegedExceptionAction() {
               public Timer run() throws Exception {
                  return TimerInfoInternal.this.createTimer();
               }
            });
         } catch (Exception var2) {
            throw new RuntimeException(var2);
         }
      }

      // $FF: synthetic method
      TimerInfoInternal(Object x1) {
         this();
      }
   }
}
