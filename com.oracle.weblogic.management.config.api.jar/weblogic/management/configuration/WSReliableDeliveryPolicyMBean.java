package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WSReliableDeliveryPolicyMBean extends ConfigurationMBean {
   /** @deprecated */
   @Deprecated
   JMSStoreMBean getStore();

   /** @deprecated */
   @Deprecated
   void setStore(JMSStoreMBean var1) throws InvalidAttributeValueException;

   JMSServerMBean getJMSServer();

   void setJMSServer(JMSServerMBean var1) throws InvalidAttributeValueException;

   int getDefaultRetryCount();

   void setDefaultRetryCount(int var1) throws InvalidAttributeValueException;

   int getDefaultRetryInterval();

   void setDefaultRetryInterval(int var1) throws InvalidAttributeValueException;

   int getDefaultTimeToLive();

   void setDefaultTimeToLive(int var1) throws InvalidAttributeValueException;
}
