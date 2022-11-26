package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.MultiIdentityDomainAuthenticatorMBean;

public interface VirtualUserAuthenticatorMBean extends StandardInterface, DescriptorBean, AuthenticatorMBean, IdentityDomainAuthenticatorMBean, MultiIdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getControlFlag();

   void setControlFlag(String var1) throws InvalidAttributeValueException;

   String[] getIdentityDomains();

   void setIdentityDomains(String[] var1) throws InvalidAttributeValueException;

   String getName();
}
