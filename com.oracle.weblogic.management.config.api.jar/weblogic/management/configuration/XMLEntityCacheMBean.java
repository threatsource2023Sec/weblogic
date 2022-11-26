package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface XMLEntityCacheMBean extends ConfigurationMBean {
   String getCacheLocation();

   void setCacheLocation(String var1) throws InvalidAttributeValueException;

   int getCacheMemorySize();

   void setCacheMemorySize(int var1) throws InvalidAttributeValueException;

   int getCacheDiskSize();

   void setCacheDiskSize(int var1) throws InvalidAttributeValueException;

   int getCacheTimeoutInterval();

   void setCacheTimeoutInterval(int var1) throws InvalidAttributeValueException;

   int getMaxSize();

   void setMaxSize(int var1);
}
