package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.management.security.IdentityDomainAwareProviderMBean;

public interface MultiIdentityDomainAuthenticatorMBean extends IdentityDomainAwareProviderMBean {
   String[] getIdentityDomains();

   void setIdentityDomains(String[] var1) throws InvalidAttributeValueException;
}
