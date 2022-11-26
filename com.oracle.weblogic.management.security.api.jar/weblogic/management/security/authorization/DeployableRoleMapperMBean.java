package weblogic.management.security.authorization;

import javax.management.InvalidAttributeValueException;

public interface DeployableRoleMapperMBean extends RoleMapperMBean {
   boolean isRoleDeploymentEnabled();

   void setRoleDeploymentEnabled(boolean var1) throws InvalidAttributeValueException;
}
