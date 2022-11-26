package weblogic.ejb.container.timer;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.ScheduleExpression;

public final class TimerData implements Serializable {
   private static final long serialVersionUID = -7307857879830348982L;
   private final Long id;
   private final Object pk;
   private final long intervalDuration;
   private final long retryDelay;
   private final int maxRetryAttempts;
   private final int maxTimeouts;
   private final int failureAction;
   private final boolean autoCreated;
   private final boolean isTransactional;
   private final ScheduleExpression timerSchedule;
   private final String callbackMethodString;
   private Serializable info;
   private Date nextExpiration;
   private int successfulTimeouts = 0;

   public TimerData(Object pk, Long id, boolean isTransactional, ScheduleExpression timerSchedule, boolean autoCreated, long intervalDuration, int maxRetryAttempts, long retryDelay, int failureAction, int maxTimeouts, String callbackMethodString) {
      this.pk = pk;
      this.id = id;
      this.isTransactional = isTransactional;
      this.timerSchedule = timerSchedule;
      this.autoCreated = autoCreated;
      this.intervalDuration = intervalDuration;
      this.maxRetryAttempts = maxRetryAttempts;
      this.retryDelay = retryDelay;
      this.failureAction = failureAction;
      this.maxTimeouts = maxTimeouts;
      this.callbackMethodString = callbackMethodString;
   }

   public boolean isTransactional() {
      return this.isTransactional;
   }

   public ScheduleExpression getTimerSchedule() {
      return this.timerSchedule;
   }

   public boolean isAutoCreated() {
      return this.autoCreated;
   }

   public Long getTimerId() {
      return this.id;
   }

   public Object getPk() {
      return this.pk;
   }

   public Serializable getInfo() {
      return this.info;
   }

   public void setInfo(Serializable info) {
      this.info = info;
   }

   public Date getNextExpiration() {
      return this.nextExpiration;
   }

   public void setNextExpiration(Date nextExpiration) {
      this.nextExpiration = nextExpiration;
   }

   public long getIntervalDuration() {
      return this.intervalDuration;
   }

   public long getRetryDelay() {
      return this.retryDelay;
   }

   public int getMaxRetryAttempts() {
      return this.maxRetryAttempts;
   }

   public int getMaxTimeouts() {
      return this.maxTimeouts;
   }

   public int getFailureAction() {
      return this.failureAction;
   }

   public int getSuccessfulTimeouts() {
      return this.successfulTimeouts;
   }

   public void incrementSuccessfulTimeouts() {
      ++this.successfulTimeouts;
   }

   String getCallbackMethodString() {
      return this.callbackMethodString;
   }
}
