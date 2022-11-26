package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface ForeignDestinationOverrideMBean extends ConfigurationMBean {
   String getRemoteJNDIName();

   void setRemoteJNDIName(String var1) throws InvalidAttributeValueException;
}
