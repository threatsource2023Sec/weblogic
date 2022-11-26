package weblogic.cluster.singleton;

public class MissedHeartbeatException extends Exception {
   public MissedHeartbeatException(String s) {
      super(s);
   }
}
