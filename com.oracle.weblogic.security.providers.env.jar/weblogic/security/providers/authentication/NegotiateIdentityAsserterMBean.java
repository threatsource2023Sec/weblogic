package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.ServletAuthenticationFilterMBean;

public interface NegotiateIdentityAsserterMBean extends StandardInterface, DescriptorBean, IdentityAsserterMBean, ServletAuthenticationFilterMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedTypes();

   String[] getActiveTypes();

   void setActiveTypes(String[] var1) throws InvalidAttributeValueException;

   boolean getBase64DecodingRequired();

   boolean isFormBasedNegotiationEnabled();

   void setFormBasedNegotiationEnabled(boolean var1) throws InvalidAttributeValueException;

   String getName();
}
