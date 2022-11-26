package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface WebServiceLogicalStoreMBean extends ConfigurationMBean {
   String LOCAL_ACCESS_ONLY = "LOCAL_ACCESS_ONLY";
   String NETWORK_ACCESSIBLE = "NETWORK_ACCESSIBLE";
   String IN_MEMORY = "IN_MEMORY";

   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getPersistenceStrategy();

   void setPersistenceStrategy(String var1);

   void setCleanerInterval(String var1);

   String getCleanerInterval();

   void setDefaultMaximumObjectLifetime(String var1);

   String getDefaultMaximumObjectLifetime();

   void setRequestBufferingQueueJndiName(String var1);

   String getRequestBufferingQueueJndiName();

   void setResponseBufferingQueueJndiName(String var1);

   String getResponseBufferingQueueJndiName();

   void setPhysicalStoreName(String var1);

   String getPhysicalStoreName();
}
