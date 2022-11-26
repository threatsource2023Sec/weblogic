package weblogic.security.providers.pk;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.pk.CertPathBuilderMBean;
import weblogic.management.security.pk.CertPathValidatorMBean;
import weblogic.security.providers.utils.CertRegManagerMBean;

public interface CertificateRegistryMBean extends StandardInterface, DescriptorBean, CertPathBuilderMBean, CertPathValidatorMBean, GroupCertRegManagerMBean, CertRegManagerMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String getName();
}
