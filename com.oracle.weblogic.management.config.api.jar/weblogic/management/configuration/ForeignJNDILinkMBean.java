package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface ForeignJNDILinkMBean extends ConfigurationMBean {
   String getLocalJNDIName();

   void setLocalJNDIName(String var1) throws InvalidAttributeValueException;

   String getRemoteJNDIName();

   void setRemoteJNDIName(String var1) throws InvalidAttributeValueException;
}
