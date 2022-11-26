package weblogic.cluster.messaging.internal;

public class PropertyServiceImpl implements PropertyService {
   private static final int DISCOVERY_PERIOD_MILLIS = Integer.getInteger("weblogic.cluster.unicastDiscoveryPeriodMillis", 3000);
   private static final int HEARTBEAT_TIMEOUT_MILLIS = Integer.getInteger("weblogic.cluster.unicastHeartbeatTimeoutMillis", 15000);

   public static PropertyServiceImpl getInstance() {
      return PropertyServiceImpl.Factory.THE_ONE;
   }

   public int getDiscoveryPeriodMillis() {
      return DISCOVERY_PERIOD_MILLIS;
   }

   public int getHeartbeatTimeoutMillis() {
      return HEARTBEAT_TIMEOUT_MILLIS;
   }

   private static final class Factory {
      static final PropertyServiceImpl THE_ONE = new PropertyServiceImpl();
   }
}
