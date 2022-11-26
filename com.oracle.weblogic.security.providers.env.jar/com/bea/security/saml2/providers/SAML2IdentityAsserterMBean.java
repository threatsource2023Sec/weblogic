package com.bea.security.saml2.providers;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.ServletAuthenticationFilterMBean;

public interface SAML2IdentityAsserterMBean extends StandardInterface, DescriptorBean, IdentityAsserterMBean, SAML2IdPPartnerRegistryMBean, ImportMBean, ExportMBean, ServletAuthenticationFilterMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String[] getSupportedTypes();

   String[] getActiveTypes();

   boolean getBase64DecodingRequired();

   String getNameMapperClassName();

   void setNameMapperClassName(String var1) throws InvalidAttributeValueException;

   boolean getReplicatedCacheEnabled();

   void setReplicatedCacheEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isLoginTokenAssociatonEnabled();

   void setLoginTokenAssociatonEnabled(boolean var1) throws InvalidAttributeValueException;

   String getName();
}
