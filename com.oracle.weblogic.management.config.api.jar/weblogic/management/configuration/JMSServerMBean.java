package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

@SingleTargetOnly
public interface JMSServerMBean extends DeploymentMBean, TargetMBean, JMSConstants {
   String getName();

   TargetMBean[] getTargets();

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean addTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean[] getSessionPools();

   /** @deprecated */
   @Deprecated
   void setSessionPools(JMSSessionPoolMBean[] var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean addSessionPool(JMSSessionPoolMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean removeSessionPool(JMSSessionPoolMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean[] getJMSSessionPools();

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean createJMSSessionPool(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean createJMSSessionPool(String var1, JMSSessionPoolMBean var2) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   void destroyJMSSessionPool(JMSSessionPoolMBean var1);

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean lookupJMSSessionPool(String var1);

   /** @deprecated */
   @Deprecated
   JMSDestinationMBean[] getDestinations();

   /** @deprecated */
   @Deprecated
   void setDestinations(JMSDestinationMBean[] var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean addDestination(JMSDestinationMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean removeDestination(JMSDestinationMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   JMSStoreMBean getStore();

   /** @deprecated */
   @Deprecated
   void setStore(JMSStoreMBean var1) throws InvalidAttributeValueException;

   PersistentStoreMBean getPersistentStore();

   void setPersistentStore(PersistentStoreMBean var1) throws InvalidAttributeValueException;

   boolean getStoreEnabled();

   void setStoreEnabled(boolean var1);

   boolean isAllowsPersistentDowngrade();

   void setAllowsPersistentDowngrade(boolean var1);

   /** @deprecated */
   @Deprecated
   JMSTemplateMBean getTemporaryTemplate();

   /** @deprecated */
   @Deprecated
   void setTemporaryTemplate(JMSTemplateMBean var1) throws InvalidAttributeValueException;

   boolean isHostingTemporaryDestinations();

   void setHostingTemporaryDestinations(boolean var1);

   String getTemporaryTemplateResource();

   void setTemporaryTemplateResource(String var1) throws InvalidAttributeValueException;

   String getTemporaryTemplateName();

   void setTemporaryTemplateName(String var1) throws InvalidAttributeValueException;

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

   /** @deprecated */
   @Deprecated
   boolean isJDBCStoreUpgradeEnabled();

   /** @deprecated */
   @Deprecated
   void setJDBCStoreUpgradeEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   JMSStoreMBean getPagingStore();

   /** @deprecated */
   @Deprecated
   void setPagingStore(JMSStoreMBean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isMessagesPagingEnabled();

   /** @deprecated */
   @Deprecated
   void setMessagesPagingEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isBytesPagingEnabled();

   /** @deprecated */
   @Deprecated
   void setBytesPagingEnabled(boolean var1) throws InvalidAttributeValueException;

   long getMessageBufferSize();

   void setMessageBufferSize(long var1) throws InvalidAttributeValueException;

   String getPagingDirectory();

   void setPagingDirectory(String var1) throws InvalidAttributeValueException;

   boolean isPagingFileLockingEnabled();

   void setPagingFileLockingEnabled(boolean var1);

   int getPagingMinWindowBufferSize();

   void setPagingMinWindowBufferSize(int var1);

   int getPagingMaxWindowBufferSize();

   void setPagingMaxWindowBufferSize(int var1);

   int getPagingIoBufferSize();

   void setPagingIoBufferSize(int var1);

   long getPagingMaxFileSize();

   void setPagingMaxFileSize(long var1);

   int getPagingBlockSize();

   void setPagingBlockSize(int var1);

   void setExpirationScanInterval(int var1) throws InvalidAttributeValueException;

   int getExpirationScanInterval();

   int getMaximumMessageSize();

   void setMaximumMessageSize(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getBlockingSendPolicy();

   void setBlockingSendPolicy(String var1) throws InvalidAttributeValueException;

   void setProductionPausedAtStartup(String var1) throws InvalidAttributeValueException;

   String getProductionPausedAtStartup();

   void setInsertionPausedAtStartup(String var1) throws InvalidAttributeValueException;

   String getInsertionPausedAtStartup();

   void setConsumptionPausedAtStartup(String var1) throws InvalidAttributeValueException;

   String getConsumptionPausedAtStartup();

   /** @deprecated */
   @Deprecated
   JMSQueueMBean[] getJMSQueues();

   /** @deprecated */
   @Deprecated
   JMSQueueMBean createJMSQueue(String var1);

   /** @deprecated */
   @Deprecated
   JMSQueueMBean createJMSQueue(String var1, JMSQueueMBean var2);

   /** @deprecated */
   @Deprecated
   void destroyJMSQueue(JMSQueueMBean var1);

   /** @deprecated */
   @Deprecated
   JMSQueueMBean lookupJMSQueue(String var1);

   /** @deprecated */
   @Deprecated
   JMSTopicMBean[] getJMSTopics();

   /** @deprecated */
   @Deprecated
   JMSTopicMBean createJMSTopic(String var1);

   /** @deprecated */
   @Deprecated
   JMSTopicMBean createJMSTopic(String var1, JMSTopicMBean var2);

   /** @deprecated */
   @Deprecated
   void destroyJMSTopic(JMSTopicMBean var1);

   /** @deprecated */
   @Deprecated
   JMSTopicMBean lookupJMSTopic(String var1);

   /** @deprecated */
   @Deprecated
   JMSMessageLogFileMBean getJMSMessageLogFile();

   void useDelegates(DomainMBean var1);

   boolean isStoreMessageCompressionEnabled();

   void setStoreMessageCompressionEnabled(boolean var1);

   boolean isPagingMessageCompressionEnabled();

   void setPagingMessageCompressionEnabled(boolean var1);

   void setMessageCompressionOptionsOverride(String var1);

   String getMessageCompressionOptionsOverride();

   void setMessageCompressionOptions(String var1);

   String getMessageCompressionOptions();
}
