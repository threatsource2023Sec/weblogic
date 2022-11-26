package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface VirtualDestinationMBean extends DeploymentMBean, JMSConstants {
   String getJNDIName();

   void setJNDIName(String var1) throws InvalidAttributeValueException;
}
