package weblogic.rjvm;

public final class HeartbeatMonitor {
   public static final int DEFAULT_PERIOD_LENGTH_MILLIS = 60000;
   public static final int DISABLE_HEARTBEATS = 0;
   public static final int DEFAULT_IDLE_PERIODS_UNTIL_TIMEOUT = 4;

   public static int periodLengthMillis() {
      return RJVMEnvironment.getEnvironment().getHeartbeatPeriodLengthMillis();
   }

   public static int periodLengthMillisNoDisable() {
      int period = periodLengthMillis();
      return period == 0 ? '\uea60' : period;
   }

   public static int idlePeriodsUntilTimeout() {
      return RJVMEnvironment.getEnvironment().getHeartbeatIdlePeriodsUntilTimeout();
   }

   public static int idleMilliSecondsTillTimeout() {
      return idlePeriodsUntilTimeout() * periodLengthMillis();
   }
}
