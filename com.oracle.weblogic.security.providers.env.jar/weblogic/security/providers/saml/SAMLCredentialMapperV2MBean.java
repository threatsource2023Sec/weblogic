package weblogic.security.providers.saml;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.security.providers.saml.registry.SAMLRelyingPartyRegistryMBean;

public interface SAMLCredentialMapperV2MBean extends StandardInterface, DescriptorBean, CredentialMapperMBean, SAMLRelyingPartyRegistryMBean, ApplicationVersionerMBean, IdentityDomainAwareProviderMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getIssuerURI();

   void setIssuerURI(String var1) throws InvalidAttributeValueException;

   String getNameQualifier();

   void setNameQualifier(String var1) throws InvalidAttributeValueException;

   String getSigningKeyAlias();

   void setSigningKeyAlias(String var1) throws InvalidAttributeValueException;

   String getSigningKeyPassPhrase();

   void setSigningKeyPassPhrase(String var1) throws InvalidAttributeValueException;

   int getDefaultTimeToLive();

   void setDefaultTimeToLive(int var1) throws InvalidAttributeValueException;

   int getDefaultTimeToLiveDelta();

   void setDefaultTimeToLiveDelta(int var1) throws InvalidAttributeValueException;

   String getNameMapperClassName();

   void setNameMapperClassName(String var1) throws InvalidAttributeValueException;

   int getMinimumParserPoolSize();

   void setMinimumParserPoolSize(int var1) throws InvalidAttributeValueException;

   int getCredCacheSize();

   void setCredCacheSize(int var1) throws InvalidAttributeValueException;

   int getCredCacheMinViableTTL();

   void setCredCacheMinViableTTL(int var1) throws InvalidAttributeValueException;

   String getName();

   void setSigningKeyPassPhraseEncrypted(byte[] var1);

   byte[] getSigningKeyPassPhraseEncrypted();
}
