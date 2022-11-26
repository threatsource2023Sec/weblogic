package weblogic.management.security.credentials;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface DeployableCredentialMapperMBean extends CredentialMapperMBean {
   boolean isCredentialMappingDeploymentEnabled();

   void setCredentialMappingDeploymentEnabled(boolean var1) throws InvalidAttributeValueException;
}
