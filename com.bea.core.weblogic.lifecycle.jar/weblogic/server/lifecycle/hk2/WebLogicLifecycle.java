package weblogic.server.lifecycle.hk2;

public class WebLogicLifecycle {
   public static final int SHUTDOWN = 0;
   public static final int STARTING = 5;
   public static final int STANDBY = 10;
   public static final int ADMIN = 15;
   public static final int RUNNING = 20;
   public static final int POST_RUNNING_1 = 25;
   public static final int HIGH_RANKING = 100;
   public static final int LOW_RANKING = -100;
}
