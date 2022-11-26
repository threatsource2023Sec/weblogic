package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.management.security.IdentityDomainAwareProviderMBean;

public interface IdentityDomainAuthenticatorMBean extends IdentityDomainAwareProviderMBean {
   String getIdentityDomain();

   void setIdentityDomain(String var1) throws InvalidAttributeValueException;
}
