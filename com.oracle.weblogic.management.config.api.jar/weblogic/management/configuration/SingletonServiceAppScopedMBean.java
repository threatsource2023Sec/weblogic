package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SingletonServiceAppScopedMBean extends SingletonServiceBaseMBean, SubDeploymentMBean {
   String getClassName();

   void setClassName(String var1) throws InvalidAttributeValueException;
}
