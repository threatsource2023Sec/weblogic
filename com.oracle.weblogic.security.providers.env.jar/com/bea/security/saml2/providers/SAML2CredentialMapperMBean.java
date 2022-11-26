package com.bea.security.saml2.providers;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;

public interface SAML2CredentialMapperMBean extends StandardInterface, DescriptorBean, CredentialMapperMBean, SAML2SPPartnerRegistryMBean, ImportMBean, ExportMBean, ApplicationVersionerMBean, IdentityDomainAwareProviderMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

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

   int getDefaultTimeToLiveOffset();

   void setDefaultTimeToLiveOffset(int var1) throws InvalidAttributeValueException;

   boolean getGenerateAttributes();

   void setGenerateAttributes(boolean var1) throws InvalidAttributeValueException;

   String getNameMapperClassName();

   void setNameMapperClassName(String var1) throws InvalidAttributeValueException;

   int getCredCacheSize();

   void setCredCacheSize(int var1) throws InvalidAttributeValueException;

   int getCredCacheMinViableTTL();

   void setCredCacheMinViableTTL(int var1) throws InvalidAttributeValueException;

   String getName();

   void setSigningKeyPassPhraseEncrypted(byte[] var1);

   byte[] getSigningKeyPassPhraseEncrypted();
}
