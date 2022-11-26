package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.management.security.IdentityDomainAwareProviderMBean;

public interface AnyIdentityDomainAuthenticatorMBean extends IdentityDomainAwareProviderMBean {
   boolean isAnyIdentityDomainEnabled();

   void setAnyIdentityDomainEnabled(boolean var1) throws InvalidAttributeValueException;
}
