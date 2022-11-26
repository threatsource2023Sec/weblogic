package weblogic.ejb.container.monitoring;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class EJBTimerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements EJBTimerRuntimeMBean {
   private static final long serialVersionUID = 1L;
   private final BeanInfo bi;
   private final TimerManager timerManager;
   private final AtomicLong timeoutCount = new AtomicLong(0L);
   private final AtomicLong cancelledTimerCount = new AtomicLong(0L);
   private final AtomicInteger activeTimerCount = new AtomicInteger(0);
   private final AtomicInteger disabledTimerCount = new AtomicInteger(0);

   public EJBTimerRuntimeMBeanImpl(String name, BeanInfo bi, EJBRuntimeMBean ejbRuntime, TimerManager timerManager) throws ManagementException {
      super(name, ejbRuntime, true, "TimerRuntime");
      this.timerManager = timerManager;
      this.bi = bi;
   }

   public long getTimeoutCount() {
      return this.timeoutCount.get();
   }

   public void incrementTimeoutCount() {
      this.timeoutCount.incrementAndGet();
   }

   public long getCancelledTimerCount() {
      return this.cancelledTimerCount.get();
   }

   public void incrementCancelledTimerCount() {
      this.cancelledTimerCount.incrementAndGet();
   }

   public int getActiveTimerCount() {
      return this.activeTimerCount.get();
   }

   public void incrementActiveTimerCount() {
      this.activeTimerCount.incrementAndGet();
   }

   public void decrementActiveTimerCount() {
      this.activeTimerCount.decrementAndGet();
   }

   public int getDisabledTimerCount() {
      return this.disabledTimerCount.get();
   }

   public void incrementDisabledTimerCount() {
      this.disabledTimerCount.incrementAndGet();
   }

   public void resetDisabledTimerCount() {
      this.disabledTimerCount.set(0);
   }

   public void activateDisabledTimers() {
      ManagedInvocationContext mic = this.bi.setCIC();
      Throwable var2 = null;

      try {
         this.timerManager.enableDisabledTimers();
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }
}
