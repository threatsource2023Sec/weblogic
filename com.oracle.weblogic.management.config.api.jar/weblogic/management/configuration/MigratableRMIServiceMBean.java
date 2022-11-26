package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface MigratableRMIServiceMBean extends DeploymentMBean {
   String getClassname();

   void setClassname(String var1) throws InvalidAttributeValueException;

   String getArgument();

   void setArgument(String var1) throws InvalidAttributeValueException;
}
