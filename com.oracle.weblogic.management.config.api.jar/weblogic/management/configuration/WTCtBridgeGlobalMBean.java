package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCtBridgeGlobalMBean extends ConfigurationMBean {
   void setTransactional(String var1) throws InvalidAttributeValueException;

   String getTransactional();

   void setTimeout(int var1) throws InvalidAttributeValueException;

   int getTimeout();

   void setRetries(int var1) throws InvalidAttributeValueException;

   int getRetries();

   void setRetryDelay(int var1) throws InvalidAttributeValueException;

   int getRetryDelay();

   void setWlsErrorDestination(String var1) throws InvalidAttributeValueException;

   String getWlsErrorDestination();

   void setTuxErrorQueue(String var1) throws InvalidAttributeValueException;

   String getTuxErrorQueue();

   void setDeliveryModeOverride(String var1) throws InvalidAttributeValueException;

   String getDeliveryModeOverride();

   void setDefaultReplyDeliveryMode(String var1) throws InvalidAttributeValueException;

   String getDefaultReplyDeliveryMode();

   void setUserId(String var1) throws InvalidAttributeValueException;

   String getUserId();

   void setAllowNonStandardTypes(String var1) throws InvalidAttributeValueException;

   String getAllowNonStandardTypes();

   void setJndiFactory(String var1) throws InvalidAttributeValueException;

   String getJndiFactory();

   void setJmsFactory(String var1) throws InvalidAttributeValueException;

   String getJmsFactory();

   void setTuxFactory(String var1) throws InvalidAttributeValueException;

   String getTuxFactory();

   void setJmsToTuxPriorityMap(String var1) throws InvalidAttributeValueException;

   String getJmsToTuxPriorityMap();

   void setTuxToJmsPriorityMap(String var1) throws InvalidAttributeValueException;

   String getTuxToJmsPriorityMap();
}
