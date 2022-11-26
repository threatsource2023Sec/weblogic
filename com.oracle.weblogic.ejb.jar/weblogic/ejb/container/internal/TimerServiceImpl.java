package weblogic.ejb.container.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import weblogic.ejb.WLTimerInfo;
import weblogic.ejb.WLTimerService;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.utils.Debug;

public final class TimerServiceImpl implements TimerService, WLTimerService {
   private final TimerManager timerManager;
   private final BaseEJBContext ejbCtx;
   private final boolean isClustered;

   public TimerServiceImpl(TimerManager timerManager, BaseEJBContext ejbCtx, boolean isClustered) {
      Debug.assertion(timerManager != null);
      this.timerManager = timerManager;
      this.ejbCtx = ejbCtx;
      this.isClustered = isClustered;
   }

   public Timer createTimer(Date initial, long interval, Serializable info) {
      return this.createTimer(initial, interval, (TimerConfig)(new TimerConfig(info, true)), (WLTimerInfo)null);
   }

   public Timer createTimer(Date expiration, Serializable info) {
      return this.createTimer(expiration, (TimerConfig)(new TimerConfig(info, true)), (WLTimerInfo)null);
   }

   public Timer createTimer(long initialDuration, long intervalDuration, Serializable info) {
      return this.createTimer(initialDuration, intervalDuration, (TimerConfig)(new TimerConfig(info, true)), (WLTimerInfo)null);
   }

   public Timer createTimer(long duration, Serializable info) {
      return this.createTimer(duration, (TimerConfig)(new TimerConfig(info, true)), (WLTimerInfo)null);
   }

   public Timer createTimer(Date initial, long interval, TimerConfig conf, WLTimerInfo wlti) {
      this.ejbCtx.checkAllowedToUseTimerService();
      this.ejbCtx.checkAllowedToCreateTimer();
      this.ensureWLTimerServiceSupport(wlti);
      if (interval <= 0L) {
         throw new IllegalArgumentException("IntervalDuration must be positive, value specified: " + interval);
      } else if (initial == null) {
         throw new IllegalArgumentException("InitialExpiration cannot be null");
      } else if (initial.getTime() < 0L) {
         throw new IllegalArgumentException("Invalid initialExpiration value: " + initial.getTime());
      } else {
         return this.timerManager.createTimer(this.getPK(), initial, interval, conf, wlti);
      }
   }

   public Timer createTimer(Date expiration, TimerConfig conf, WLTimerInfo wlti) {
      this.ejbCtx.checkAllowedToUseTimerService();
      this.ejbCtx.checkAllowedToCreateTimer();
      this.ensureWLTimerServiceSupport(wlti);
      if (expiration == null) {
         throw new IllegalArgumentException("Expiration cannot be null");
      } else if (expiration.getTime() < 0L) {
         throw new IllegalArgumentException("Invalid initialExpiration value: " + expiration.getTime());
      } else {
         if (wlti != null) {
            if (wlti.getMaxTimeouts() > 0) {
               throw new IllegalArgumentException("MaxTimeouts property on the WLTimerInfo object can only be set for interval timers. You are attempting to create a single-event timer");
            }

            if (wlti.getTimeoutFailureAction() == 3) {
               throw new IllegalArgumentException("The ejbTimeout failure action, 'SKIP_TIMEOUT_ACTION', can only be set for interval timers. You are attempting to create a single-event timer. Please reconfigure the ejbTimeout failure action on your WLTimerInfo object");
            }
         }

         return this.timerManager.createTimer(this.getPK(), expiration, conf, wlti);
      }
   }

   public Timer createTimer(long initialDuration, long intervalDuration, TimerConfig conf, WLTimerInfo wlti) {
      this.ejbCtx.checkAllowedToUseTimerService();
      this.ejbCtx.checkAllowedToCreateTimer();
      this.ensureWLTimerServiceSupport(wlti);
      if (initialDuration < 0L) {
         throw new IllegalArgumentException("InitialDuration must be positive, value specified: " + initialDuration);
      } else if (intervalDuration <= 0L) {
         throw new IllegalArgumentException("IntervalDuration must be positive, value specified: " + intervalDuration);
      } else {
         return this.timerManager.createTimer(this.getPK(), initialDuration, intervalDuration, conf, wlti);
      }
   }

   public Timer createTimer(long duration, TimerConfig conf, WLTimerInfo wlti) {
      this.ejbCtx.checkAllowedToUseTimerService();
      this.ejbCtx.checkAllowedToCreateTimer();
      this.ensureWLTimerServiceSupport(wlti);
      if (duration < 0L) {
         throw new IllegalArgumentException("Duration argument must be positive, value specified: " + duration);
      } else {
         if (wlti != null) {
            if (wlti.getMaxTimeouts() > 0) {
               throw new IllegalArgumentException("MaxTimeouts property on the WLTimerInfo object can only be set for interval timers.  You are attempting to create a single-event timer.");
            }

            if (wlti.getTimeoutFailureAction() == 3) {
               throw new IllegalArgumentException("The ejbTimeout failure action, 'SKIP_TIMEOUT_ACTION', can only be set for interval timers.  You are attempting to create a single-event timer. Please reconfigure the ejbTimeout failure action on your WLTimerInfo object.");
            }
         }

         return this.timerManager.createTimer(this.getPK(), duration, conf, wlti);
      }
   }

   private Timer createTimer(ScheduleExpression schedule, TimerConfig timerConfig) {
      this.ejbCtx.checkAllowedToUseTimerService();
      this.ejbCtx.checkAllowedToCreateTimer();
      return this.timerManager.createTimer(this.getPK(), schedule, timerConfig);
   }

   public Timer createSingleActionTimer(long duration, TimerConfig timerConfig) {
      return this.createTimer(duration, (TimerConfig)timerConfig, (WLTimerInfo)null);
   }

   public Timer createSingleActionTimer(Date expiration, TimerConfig timerConfig) {
      return this.createTimer(expiration, (TimerConfig)timerConfig, (WLTimerInfo)null);
   }

   public Timer createIntervalTimer(long initialDuration, long intervalDuration, TimerConfig timerConfig) {
      return this.createTimer(initialDuration, intervalDuration, (TimerConfig)timerConfig, (WLTimerInfo)null);
   }

   public Timer createIntervalTimer(Date initialExpiration, long intervalDuration, TimerConfig timerConfig) {
      return this.createTimer(initialExpiration, intervalDuration, (TimerConfig)timerConfig, (WLTimerInfo)null);
   }

   public Timer createCalendarTimer(ScheduleExpression schedule) {
      return this.createTimer((ScheduleExpression)schedule, (TimerConfig)null);
   }

   public Timer createCalendarTimer(ScheduleExpression schedule, TimerConfig tc) {
      return this.createTimer(schedule, tc);
   }

   public Collection getTimers() {
      this.ejbCtx.checkAllowedToUseTimerService();
      return this.timerManager.getTimers(this.getPK());
   }

   public Collection getAllTimers() throws IllegalStateException, EJBException {
      this.ejbCtx.checkAllowedToUseTimerService();
      Set timers = new HashSet();
      Iterator var2 = this.ejbCtx.getBeanInfo().getDeploymentInfo().getBeanInfos().iterator();

      while(var2.hasNext()) {
         BeanInfo bi = (BeanInfo)var2.next();
         TimerManager tm = bi.getBeanManager().getTimerManager();
         if (tm != null) {
            timers.addAll(tm.getAllTimers());
         }
      }

      return timers;
   }

   private Object getPK() {
      return this.ejbCtx instanceof EntityEJBContextImpl ? ((EntityEJBContextImpl)this.ejbCtx).__WL_getPrimaryKey() : new Integer(1);
   }

   private void ensureWLTimerServiceSupport(WLTimerInfo wlti) {
      if (this.isClustered && wlti != null) {
         throw new IllegalArgumentException("Clustered EJB timer service does not support the methods of the WLTimerService interface. Only methods declared on the javax.ejb.TimerService interface can be invoked");
      }
   }

   public Timer createTimer(Date initial, long duration, Serializable info, WLTimerInfo wlti) {
      return this.createTimer(initial, duration, new TimerConfig(info, true), wlti);
   }

   public Timer createTimer(Date expiration, Serializable info, WLTimerInfo wlti) {
      return this.createTimer(expiration, new TimerConfig(info, true), wlti);
   }

   public Timer createTimer(long initial, long duration, Serializable info, WLTimerInfo wlti) {
      return this.createTimer(initial, duration, new TimerConfig(info, true), wlti);
   }

   public Timer createTimer(long duration, Serializable info, WLTimerInfo wlti) {
      return this.createTimer(duration, new TimerConfig(info, true), wlti);
   }
}
