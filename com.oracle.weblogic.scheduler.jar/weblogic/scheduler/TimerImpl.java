package weblogic.scheduler;

import java.io.Serializable;
import weblogic.timers.RuntimeDomainSelector;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.TimerListener;

public class TimerImpl implements Timer {
   private final String id;
   private final String domainId;
   private TimerBasis timerBasis;

   TimerImpl(String id, String domainId) {
      this.id = id;
      this.domainId = domainId;
   }

   TimerImpl(String id, TimerBasis timerBasis) {
      this.id = id;
      this.timerBasis = timerBasis;
      this.domainId = null;
   }

   String getID() {
      return this.id;
   }

   private TimerBasis getTimerBasis() throws TimerException {
      if (this.timerBasis == null) {
         this.timerBasis = TimerBasisAccess.getTimerBasis(this.domainId);
      }

      return this.timerBasis;
   }

   public boolean cancel() {
      try {
         return this.getTimerBasis().cancelTimer(this.id);
      } catch (NoSuchObjectLocalException var2) {
         return false;
      } catch (TimerException var3) {
         return false;
      }
   }

   public boolean isStopped() {
      return this.isCancelled();
   }

   public boolean isCancelled() {
      return this.getTimerState() == null;
   }

   public long getTimeout() {
      TimerState state = this.getTimerState();
      return state == null ? -1L : state.getTimeout();
   }

   public long getPeriod() {
      TimerState state = this.getTimerState();
      return state == null ? -1L : state.getInterval();
   }

   public TimerListener getListener() {
      TimerState state = this.getTimerState();
      return state == null ? null : state.getTimedObject();
   }

   public ScheduleExpression getSchedule() {
      TimerState state = this.getTimerState();
      return state == null ? null : state.getSchedule();
   }

   public boolean isCalendarTimer() {
      TimerState state = this.getTimerState();
      return state == null ? false : state.isCalendarTimer();
   }

   public Serializable getInfo() throws NoSuchObjectLocalException, TimerException {
      TimerState state = this.getTimerBasis().getTimerState(this.id);
      return state.getInfo();
   }

   public TimerHandle getHandle() throws NoSuchObjectLocalException, TimerException {
      return new TimerHandleImpl(this.id, RuntimeDomainSelector.getDomain());
   }

   public String getListenerClassName() {
      TimerListener listener = this.getListener();
      return listener != null ? listener.getClass().getName() : null;
   }

   private TimerState getTimerState() {
      try {
         return this.getTimerBasis().getTimerState(this.id);
      } catch (NoSuchObjectLocalException var2) {
         return null;
      } catch (TimerException var3) {
         return null;
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result *= this.id.hashCode();
      }

      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof TimerImpl)) {
         return false;
      } else {
         TimerImpl other = (TimerImpl)o;
         if (this.id == null && other.id == null) {
            return true;
         } else {
            return this.id != null && this.id.equals(other.id);
         }
      }
   }

   public String toString() {
      return this.id;
   }
}
