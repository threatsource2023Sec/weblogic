package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.utils.LDAPServerMBean;

public interface LDAPX509IdentityAsserterMBean extends StandardInterface, DescriptorBean, IdentityAsserterMBean, LDAPServerMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedTypes();

   String[] getActiveTypes();

   void setActiveTypes(String[] var1) throws InvalidAttributeValueException;

   String[] getUserFilterAttributes();

   void setUserFilterAttributes(String[] var1) throws InvalidAttributeValueException;

   String getUsernameAttribute();

   void setUsernameAttribute(String var1) throws InvalidAttributeValueException;

   String getCertificateAttribute();

   void setCertificateAttribute(String var1) throws InvalidAttributeValueException;

   String getCertificateMapping();

   void setCertificateMapping(String var1) throws InvalidAttributeValueException;

   String getCredential();

   void setCredential(String var1) throws InvalidAttributeValueException;

   String getName();

   void setCredentialEncrypted(byte[] var1);

   byte[] getCredentialEncrypted();
}
