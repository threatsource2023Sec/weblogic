package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface XMLEntitySpecRegistryEntryMBean extends ConfigurationMBean {
   String getPublicId();

   void setPublicId(String var1) throws InvalidAttributeValueException;

   String getSystemId();

   void setSystemId(String var1) throws InvalidAttributeValueException;

   String getEntityURI();

   void setEntityURI(String var1) throws InvalidAttributeValueException;

   String getWhenToCache();

   void setWhenToCache(String var1);

   int getCacheTimeoutInterval();

   void setCacheTimeoutInterval(int var1) throws InvalidAttributeValueException;

   String getHandleEntityInvalidation();

   void setHandleEntityInvalidation(String var1);
}
