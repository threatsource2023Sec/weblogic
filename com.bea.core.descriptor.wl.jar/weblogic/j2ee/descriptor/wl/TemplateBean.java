package weblogic.j2ee.descriptor.wl;

public interface TemplateBean extends NamedEntityBean {
   String[] getDestinationKeys();

   void addDestinationKey(String var1);

   void removeDestinationKey(String var1);

   void setDestinationKeys(String[] var1) throws IllegalArgumentException;

   ThresholdParamsBean getThresholds();

   DeliveryParamsOverridesBean getDeliveryParamsOverrides();

   DeliveryFailureParamsBean getDeliveryFailureParams();

   MessageLoggingParamsBean getMessageLoggingParams();

   String getAttachSender();

   void setAttachSender(String var1) throws IllegalArgumentException;

   boolean isProductionPausedAtStartup();

   void setProductionPausedAtStartup(boolean var1) throws IllegalArgumentException;

   boolean isInsertionPausedAtStartup();

   void setInsertionPausedAtStartup(boolean var1) throws IllegalArgumentException;

   boolean isConsumptionPausedAtStartup();

   void setConsumptionPausedAtStartup(boolean var1) throws IllegalArgumentException;

   int getMaximumMessageSize();

   void setMaximumMessageSize(int var1) throws IllegalArgumentException;

   QuotaBean getQuota();

   void setQuota(QuotaBean var1) throws IllegalArgumentException;

   boolean isDefaultUnitOfOrder();

   void setDefaultUnitOfOrder(boolean var1) throws IllegalArgumentException;

   String getSafExportPolicy();

   void setSafExportPolicy(String var1);

   MulticastParamsBean getMulticast();

   TopicSubscriptionParamsBean getTopicSubscriptionParams();

   GroupParamsBean[] getGroupParams();

   GroupParamsBean createGroupParams(String var1);

   void destroyGroupParams(GroupParamsBean var1);

   GroupParamsBean lookupGroupParams(String var1);

   DestinationBean findErrorDestination(String var1);

   int getMessagingPerformancePreference();

   void setMessagingPerformancePreference(int var1) throws IllegalArgumentException;

   String getUnitOfWorkHandlingPolicy();

   void setUnitOfWorkHandlingPolicy(String var1) throws IllegalArgumentException;

   int getIncompleteWorkExpirationTime();

   void setIncompleteWorkExpirationTime(int var1) throws IllegalArgumentException;
}
