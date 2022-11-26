package weblogic.timers.internal;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.Timer;
import weblogic.management.runtime.TimerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class TimerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements TimerRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   TimerRuntimeMBeanImpl() throws ManagementException {
      super("TimerRuntime");
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setTimerRuntime(this);
   }

   public Timer[] getTimers() {
      TimerImpl[] timers = TimerImpl.getTimers();
      if (timers != null && timers.length != 0) {
         Timer[] runtimes = new Timer[timers.length];

         for(int count = 0; count < timers.length; ++count) {
            runtimes[count] = new TimerRuntime(timers[count]);
         }

         return runtimes;
      } else {
         return null;
      }
   }

   public static final class TimerRuntime implements Timer {
      static final long serialVersionUID = -1639099960837088234L;
      private final String timerManagerName;
      private final long timeout;
      private final long period;
      private final boolean stopped;
      private final boolean cancelled;
      private final long[] pastExpirationTimes;
      private final String listenerClassName;

      public TimerRuntime(TimerImpl timer) {
         this.timerManagerName = timer.getTimerManager().getName() + "[" + timer.toString() + "]";
         this.timeout = timer.getTimeout();
         this.period = Math.abs(timer.getPeriod());
         this.stopped = timer.isStopped();
         this.cancelled = timer.isCancelled();
         this.pastExpirationTimes = timer.sortedExpirationTimes();
         this.listenerClassName = timer.getListenerClassName();
      }

      public String getTimerManagerName() {
         return this.timerManagerName;
      }

      public long getTimeout() {
         return this.timeout;
      }

      public long getPeriod() {
         return this.period;
      }

      public boolean isStopped() {
         return this.stopped;
      }

      public boolean isCancelled() {
         return this.cancelled;
      }

      public long getExpirationCount() {
         return (long)this.pastExpirationTimes.length;
      }

      public long[] getPastExpirationTimes() {
         return this.pastExpirationTimes;
      }

      public String getListenerClassName() {
         return this.listenerClassName;
      }
   }
}
