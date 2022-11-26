package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface FileT3MBean extends DeploymentMBean {
   String getPath();

   void setPath(String var1) throws InvalidAttributeValueException;
}
