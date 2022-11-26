package weblogic.security.providers.saml;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;

public interface SAMLAuthenticatorMBean extends StandardInterface, DescriptorBean, AuthenticatorMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getName();
}
