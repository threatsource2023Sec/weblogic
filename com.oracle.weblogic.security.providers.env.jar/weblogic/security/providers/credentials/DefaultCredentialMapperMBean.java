package weblogic.security.providers.credentials;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.credentials.DeployableCredentialMapperMBean;
import weblogic.management.security.credentials.UserPasswordCredentialMapEditorMBean;
import weblogic.management.security.credentials.UserPasswordCredentialMapExtendedEditorMBean;

public interface DefaultCredentialMapperMBean extends StandardInterface, DescriptorBean, DeployableCredentialMapperMBean, UserPasswordCredentialMapEditorMBean, UserPasswordCredentialMapExtendedEditorMBean, ApplicationVersionerMBean, ImportMBean, ExportMBean, IdentityDomainAwareProviderMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   int getJWTTokenExpiration();

   void setJWTTokenExpiration(int var1) throws InvalidAttributeValueException;

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String getName();
}
