package weblogic.security.providers.credentials;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.credentials.PKICredentialMapEditorMBean;
import weblogic.management.security.credentials.PKICredentialMapExtendedEditorMBean;

public interface PKICredentialMapperMBean extends StandardInterface, DescriptorBean, CredentialMapperMBean, PKICredentialMapEditorMBean, PKICredentialMapExtendedEditorMBean, ApplicationVersionerMBean, ImportMBean, ExportMBean, IdentityDomainAwareProviderMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String getKeyStoreType();

   void setKeyStoreType(String var1) throws InvalidAttributeValueException;

   String getKeyStoreProvider();

   void setKeyStoreProvider(String var1) throws InvalidAttributeValueException;

   String getKeyStoreFileName();

   void setKeyStoreFileName(String var1) throws InvalidAttributeValueException;

   String getKeyStorePassPhrase();

   void setKeyStorePassPhrase(String var1) throws InvalidAttributeValueException;

   boolean isUseResourceHierarchy();

   void setUseResourceHierarchy(boolean var1) throws InvalidAttributeValueException;

   boolean isUseInitiatorGroupNames();

   void setUseInitiatorGroupNames(boolean var1) throws InvalidAttributeValueException;

   String getName();

   void setKeyStorePassPhraseEncrypted(byte[] var1);

   byte[] getKeyStorePassPhraseEncrypted();
}
