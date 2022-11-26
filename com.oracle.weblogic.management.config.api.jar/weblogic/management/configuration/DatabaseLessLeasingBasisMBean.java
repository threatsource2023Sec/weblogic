package weblogic.management.configuration;

public interface DatabaseLessLeasingBasisMBean extends ConfigurationMBean {
   void setMemberDiscoveryTimeout(int var1);

   int getMemberDiscoveryTimeout();

   void setLeaderHeartbeatPeriod(int var1);

   int getLeaderHeartbeatPeriod();

   void setMessageDeliveryTimeout(int var1);

   int getMessageDeliveryTimeout();

   void setFenceTimeout(int var1);

   int getFenceTimeout();

   boolean isPeriodicSRMCheckEnabled();

   void setPeriodicSRMCheckEnabled(boolean var1);

   void setNodeManagerTimeoutMillis(int var1);

   int getNodeManagerTimeoutMillis();
}
