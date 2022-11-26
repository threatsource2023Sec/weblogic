package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

@SingleTargetOnly
public interface SAFAgentMBean extends DeploymentMBean, TargetMBean {
   String SENDING_ONLY = "Sending-only";
   String RECEIVING_ONLY = "Receiving-only";
   String BOTH = "Both";

   String getName();

   TargetMBean[] getTargets();

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   PersistentStoreMBean getStore();

   void setStore(PersistentStoreMBean var1) throws InvalidAttributeValueException;

   long getBytesMaximum();

   void setBytesMaximum(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getBytesThresholdHigh();

   void setBytesThresholdHigh(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getBytesThresholdLow();

   void setBytesThresholdLow(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMessagesMaximum();

   void setMessagesMaximum(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMessagesThresholdHigh();

   void setMessagesThresholdHigh(long var1) throws InvalidAttributeValueException;

   long getMessagesThresholdLow();

   void setMessagesThresholdLow(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaximumMessageSize();

   void setMaximumMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getDefaultRetryDelayBase();

   void setDefaultRetryDelayBase(long var1) throws InvalidAttributeValueException;

   long getDefaultRetryDelayMaximum();

   void setDefaultRetryDelayMaximum(long var1) throws InvalidAttributeValueException;

   double getDefaultRetryDelayMultiplier();

   void setDefaultRetryDelayMultiplier(double var1) throws InvalidAttributeValueException;

   String getServiceType();

   void setServiceType(String var1) throws InvalidAttributeValueException;

   int getWindowSize();

   void setWindowSize(int var1) throws InvalidAttributeValueException;

   boolean isLoggingEnabled();

   void setLoggingEnabled(boolean var1) throws InvalidAttributeValueException;

   long getConversationIdleTimeMaximum();

   void setConversationIdleTimeMaximum(long var1) throws InvalidAttributeValueException;

   long getAcknowledgeInterval();

   void setAcknowledgeInterval(long var1) throws InvalidAttributeValueException;

   long getDefaultTimeToLive();

   void setDefaultTimeToLive(long var1) throws InvalidAttributeValueException;

   boolean isIncomingPausedAtStartup();

   void setIncomingPausedAtStartup(boolean var1) throws InvalidAttributeValueException;

   boolean isForwardingPausedAtStartup();

   void setForwardingPausedAtStartup(boolean var1) throws InvalidAttributeValueException;

   boolean isReceivingPausedAtStartup();

   void setReceivingPausedAtStartup(boolean var1) throws InvalidAttributeValueException;

   long getMessageBufferSize();

   void setMessageBufferSize(long var1) throws InvalidAttributeValueException;

   String getPagingDirectory();

   void setPagingDirectory(String var1) throws InvalidAttributeValueException;

   long getWindowInterval();

   void setWindowInterval(long var1) throws InvalidAttributeValueException;

   JMSSAFMessageLogFileMBean getJMSSAFMessageLogFile();

   boolean isStoreMessageCompressionEnabled();

   void setStoreMessageCompressionEnabled(boolean var1);

   boolean isPagingMessageCompressionEnabled();

   void setPagingMessageCompressionEnabled(boolean var1);

   void setMessageCompressionOptionsOverride(String var1);

   String getMessageCompressionOptionsOverride();

   void setMessageCompressionOptions(String var1);

   String getMessageCompressionOptions();
}
