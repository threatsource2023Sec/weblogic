package weblogic.j2ee.descriptor.wl;

public interface DestinationBean extends TargetableBean {
   TemplateBean getTemplate();

   void setTemplate(TemplateBean var1) throws IllegalArgumentException;

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

   String getJNDIName();

   void setJNDIName(String var1) throws IllegalArgumentException;

   String getLocalJNDIName();

   void setLocalJNDIName(String var1) throws IllegalArgumentException;

   String getJMSCreateDestinationIdentifier();

   void setJMSCreateDestinationIdentifier(String var1) throws IllegalArgumentException;

   boolean isDefaultUnitOfOrder();

   void setDefaultUnitOfOrder(boolean var1) throws IllegalArgumentException;

   String getSAFExportPolicy();

   void setSAFExportPolicy(String var1);

   int getMessagingPerformancePreference();

   void setMessagingPerformancePreference(int var1) throws IllegalArgumentException;

   String getUnitOfWorkHandlingPolicy();

   void setUnitOfWorkHandlingPolicy(String var1) throws IllegalArgumentException;

   int getIncompleteWorkExpirationTime();

   void setIncompleteWorkExpirationTime(int var1) throws IllegalArgumentException;

   String getLoadBalancingPolicy();

   void setLoadBalancingPolicy(String var1) throws IllegalArgumentException;

   String getUnitOfOrderRouting();

   void setUnitOfOrderRouting(String var1) throws IllegalArgumentException;
}
