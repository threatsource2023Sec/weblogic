package weblogic.security.providers.pk;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.pk.CertPathBuilderMBean;
import weblogic.management.security.pk.CertPathValidatorMBean;

public interface WebLogicCertPathProviderMBean extends StandardInterface, DescriptorBean, CertPathBuilderMBean, CertPathValidatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getName();
}
