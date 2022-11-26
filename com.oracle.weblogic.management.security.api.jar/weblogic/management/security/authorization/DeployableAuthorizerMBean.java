package weblogic.management.security.authorization;

import javax.management.InvalidAttributeValueException;

public interface DeployableAuthorizerMBean extends AuthorizerMBean {
   boolean isPolicyDeploymentEnabled();

   void setPolicyDeploymentEnabled(boolean var1) throws InvalidAttributeValueException;
}
