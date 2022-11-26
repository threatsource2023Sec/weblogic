package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface MessagingBridgeMBean extends DynamicDeploymentMBean {
   String BRIDGE_POLICY_AUTO = "Automatic";
   String BRIDGE_POLICY_MANUAL = "Manual";
   String BRIDGE_POLICY_SCHEDULED = "Scheduled";
   String BRIDGE_QOS_EXACTLY_ONCE = "Exactly-once";
   String BRIDGE_QOS_ATMOST_ONCE = "Atmost-once";
   String BRIDGE_QOS_DUPLICATE_OKAY = "Duplicate-okay";

   String getName();

   BridgeDestinationCommonMBean getSourceDestination();

   void setSourceDestination(BridgeDestinationCommonMBean var1) throws InvalidAttributeValueException;

   BridgeDestinationCommonMBean getTargetDestination();

   void setTargetDestination(BridgeDestinationCommonMBean var1) throws InvalidAttributeValueException;

   String getSelector();

   void setSelector(String var1) throws InvalidAttributeValueException;

   String getForwardingPolicy();

   void setForwardingPolicy(String var1) throws InvalidAttributeValueException;

   String getScheduleTime();

   void setScheduleTime(String var1) throws InvalidAttributeValueException;

   String getQualityOfService();

   void setQualityOfService(String var1) throws InvalidAttributeValueException;

   boolean isQOSDegradationAllowed();

   void setQOSDegradationAllowed(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isDurabilityDisabled();

   /** @deprecated */
   @Deprecated
   void setDurabilityDisabled(boolean var1) throws InvalidAttributeValueException;

   boolean isDurabilityEnabled();

   void setDurabilityEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   long getReconnectDelayInitialMilliseconds();

   /** @deprecated */
   @Deprecated
   void setReconnectDelayInitialMilliseconds(long var1) throws InvalidAttributeValueException;

   int getReconnectDelayMinimum();

   void setReconnectDelayMinimum(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   long getReconnectDelayIncrement();

   /** @deprecated */
   @Deprecated
   void setReconnectDelayIncrement(long var1) throws InvalidAttributeValueException;

   int getReconnectDelayIncrease();

   void setReconnectDelayIncrease(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   long getReconnectDelayMaximumMilliseconds();

   /** @deprecated */
   @Deprecated
   void setReconnectDelayMaximumMilliseconds(long var1) throws InvalidAttributeValueException;

   int getReconnectDelayMaximum();

   void setReconnectDelayMaximum(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   long getMaximumIdleTimeMilliseconds();

   /** @deprecated */
   @Deprecated
   void setMaximumIdleTimeMilliseconds(long var1) throws InvalidAttributeValueException;

   int getIdleTimeMaximum();

   void setIdleTimeMaximum(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   long getScanUnitMilliseconds();

   /** @deprecated */
   @Deprecated
   void setScanUnitMilliseconds(long var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getTransactionTimeoutSeconds();

   /** @deprecated */
   @Deprecated
   void setTransactionTimeoutSeconds(int var1) throws InvalidAttributeValueException;

   int getTransactionTimeout();

   void setTransactionTimeout(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isAsyncDisabled();

   /** @deprecated */
   @Deprecated
   void setAsyncDisabled(boolean var1) throws InvalidAttributeValueException;

   boolean isAsyncEnabled();

   void setAsyncEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isStarted();

   void setStarted(boolean var1) throws InvalidAttributeValueException;

   int getBatchSize();

   void setBatchSize(int var1) throws InvalidAttributeValueException;

   long getBatchInterval();

   void setBatchInterval(long var1) throws InvalidAttributeValueException;

   boolean getPreserveMsgProperty();

   void setPreserveMsgProperty(boolean var1) throws InvalidAttributeValueException;
}
