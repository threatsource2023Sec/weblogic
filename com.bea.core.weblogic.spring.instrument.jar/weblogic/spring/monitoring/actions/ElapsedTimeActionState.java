package weblogic.spring.monitoring.actions;

import weblogic.utils.time.Timer;

public class ElapsedTimeActionState extends BaseActionState {
   private long startTime;
   private long elapsedTime;

   public ElapsedTimeActionState() {
      this.startTimer();
   }

   public long getElapsedTime() {
      return this.elapsedTime;
   }

   public void startTimer() {
      Timer timer = Timer.createTimer();
      this.startTime = timer.timestamp();
   }

   public void stopTimer() {
      Timer timer = Timer.createTimer();
      this.elapsedTime = timer.timestamp() - this.startTime;
   }
}
