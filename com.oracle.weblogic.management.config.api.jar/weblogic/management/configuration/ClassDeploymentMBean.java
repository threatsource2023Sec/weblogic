package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface ClassDeploymentMBean extends DeploymentMBean {
   String getClassName();

   void setClassName(String var1) throws InvalidAttributeValueException;

   String getArguments();

   void setArguments(String var1) throws InvalidAttributeValueException;
}
