package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface RMCFactoryMBean extends DeploymentMBean {
   String getJNDIName();

   void setJNDIName(String var1) throws InvalidAttributeValueException;
}
