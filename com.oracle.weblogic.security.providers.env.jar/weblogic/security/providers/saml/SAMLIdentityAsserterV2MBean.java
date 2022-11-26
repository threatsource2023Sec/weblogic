package weblogic.security.providers.saml;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.ServletAuthenticationFilterMBean;
import weblogic.security.providers.saml.registry.SAMLAssertingPartyRegistryMBean;

public interface SAMLIdentityAsserterV2MBean extends StandardInterface, DescriptorBean, IdentityAsserterMBean, SAMLAssertingPartyRegistryMBean, ServletAuthenticationFilterMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedTypes();

   String[] getActiveTypes();

   boolean getBase64DecodingRequired();

   String getNameMapperClassName();

   void setNameMapperClassName(String var1) throws InvalidAttributeValueException;

   int getMinimumParserPoolSize();

   void setMinimumParserPoolSize(int var1) throws InvalidAttributeValueException;

   String getName();
}
