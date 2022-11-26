package weblogic.elasticity;

import java.util.concurrent.atomic.AtomicBoolean;

public class ClusterScalingStatus {
   private int scaleUpAttemptCount;
   private int scaleUpCount;
   private int scaleDownAttemptCount;
   private int scaleDownCount;
   private long lastScalingStartTime;
   private long lastScalingEndTime;
   private AtomicBoolean scalingActionInProgress = new AtomicBoolean();

   public int getScaleUpAttemptCount() {
      return this.scaleUpAttemptCount;
   }

   public void setScaleUpAttemptCount(int scaleUpAttemptCount) {
      this.scaleUpAttemptCount = scaleUpAttemptCount;
   }

   public int getScaleUpCount() {
      return this.scaleUpCount;
   }

   public void setScaleUpCount(int scaleUpCount) {
      this.scaleUpCount = scaleUpCount;
   }

   public int getScaleDownAttemptCount() {
      return this.scaleDownAttemptCount;
   }

   public void setScaleDownAttemptCount(int scaleDownAttemptCount) {
      this.scaleDownAttemptCount = scaleDownAttemptCount;
   }

   public int getScaleDownCount() {
      return this.scaleDownCount;
   }

   public void setScaleDownCount(int scaleDownCount) {
      this.scaleDownCount = scaleDownCount;
   }

   public long getLastScalingStartTime() {
      return this.lastScalingStartTime;
   }

   public void setLastScalingStartTime(long lastScalingStartTime) {
      this.lastScalingStartTime = lastScalingStartTime;
   }

   public long getLastScalingEndTime() {
      return this.lastScalingEndTime;
   }

   public void setLastScalingEndTime(long lastScalingEndTime) {
      this.lastScalingEndTime = lastScalingEndTime;
   }

   public boolean isScalingActionInProgress() {
      return this.scalingActionInProgress.get();
   }

   public boolean markScalingActionInProgress() {
      return this.scalingActionInProgress.compareAndSet(false, true);
   }

   public void resetScalingActionInProgress() {
      this.scalingActionInProgress.set(false);
   }
}
