package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

/** @deprecated */
@Deprecated
public interface JMSDestCommonMBean extends ConfigurationMBean, JMSConstants {
   JMSDestinationKeyMBean[] getDestinationKeys();

   boolean addDestinationKey(JMSDestinationKeyMBean var1);

   void setDestinationKeys(JMSDestinationKeyMBean[] var1) throws InvalidAttributeValueException;

   boolean removeDestinationKey(JMSDestinationKeyMBean var1);

   long getBytesMaximum();

   void setBytesMaximum(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getBytesThresholdHigh();

   void setBytesThresholdHigh(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getBytesThresholdLow();

   void setBytesThresholdLow(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMessagesMaximum();

   void setMessagesMaximum(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMessagesThresholdHigh();

   void setMessagesThresholdHigh(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMessagesThresholdLow();

   void setMessagesThresholdLow(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getPriorityOverride();

   void setPriorityOverride(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getTimeToDeliverOverride();

   void setTimeToDeliverOverride(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getRedeliveryDelayOverride();

   void setRedeliveryDelayOverride(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   void setErrorDestination(JMSDestinationMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   JMSDestinationMBean getErrorDestination();

   int getRedeliveryLimit();

   void setRedeliveryLimit(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getTimeToLiveOverride();

   void setTimeToLiveOverride(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getDeliveryModeOverride();

   void setDeliveryModeOverride(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   void setExpirationPolicy(String var1) throws InvalidAttributeValueException;

   String getExpirationPolicy();

   void setExpirationLoggingPolicy(String var1) throws InvalidAttributeValueException;

   String getExpirationLoggingPolicy();

   int getMaximumMessageSize();

   void setMaximumMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getCreationTime();

   void setCreationTime(long var1);
}
