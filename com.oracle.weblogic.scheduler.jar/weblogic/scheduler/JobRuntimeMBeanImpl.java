package weblogic.scheduler;

import java.io.Serializable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JobRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class JobRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JobRuntimeMBean {
   private final String id;
   private final String description;
   private final TimerBasis timerBasis;
   private long lastLocalExecutionTime;
   private long localExecutionCount;
   private long period;
   private String state = "Running";

   public JobRuntimeMBeanImpl(RuntimeMBean parent, String timerId, String description, long interval, TimerBasis timerBasis) throws ManagementException {
      super("JobRuntime-" + timerId, parent, true);
      this.id = timerId;
      this.lastLocalExecutionTime = System.currentTimeMillis();
      this.localExecutionCount = 1L;
      this.period = interval;
      this.description = description;
      this.timerBasis = timerBasis;
   }

   public void cancel() {
      if ((new TimerImpl(this.id, this.timerBasis)).cancel()) {
         this.state = "Cancelled";
      }

   }

   public Serializable getTimerListener() {
      return (Serializable)(new TimerImpl(this.id, this.timerBasis)).getListener();
   }

   public long getPeriod() {
      return this.period;
   }

   public long getTimeout() {
      return (new TimerImpl(this.id, this.timerBasis)).getTimeout();
   }

   public long getLastLocalExecutionTime() {
      return this.lastLocalExecutionTime;
   }

   public long getLocalExecutionCount() {
      return this.localExecutionCount;
   }

   public void update() {
      this.lastLocalExecutionTime = System.currentTimeMillis();
      ++this.localExecutionCount;
   }

   public String getDescription() {
      return this.description;
   }

   public String getID() {
      return this.id;
   }

   public String getState() {
      if (this.state.equals("Cancelled")) {
         return this.state;
      } else {
         try {
            this.timerBasis.getTimerState(this.id);
         } catch (NoSuchObjectLocalException var2) {
            this.state = "Cancelled";
         } catch (TimerException var3) {
            this.state = "Cancelled";
         }

         return this.state;
      }
   }
}
