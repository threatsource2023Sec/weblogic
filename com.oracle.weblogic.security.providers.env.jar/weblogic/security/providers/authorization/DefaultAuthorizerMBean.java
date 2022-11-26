package weblogic.security.providers.authorization;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.authorization.DeployableAuthorizerMBean;
import weblogic.management.security.authorization.PolicyAuxiliaryMBean;
import weblogic.management.security.authorization.PolicyConsumerMBean;
import weblogic.management.security.authorization.PolicyListerMBean;
import weblogic.management.security.authorization.PredicateEditorMBean;

public interface DefaultAuthorizerMBean extends StandardInterface, DescriptorBean, DeployableAuthorizerMBean, PolicyAuxiliaryMBean, PolicyListerMBean, PredicateEditorMBean, PolicyConsumerMBean, ApplicationVersionerMBean, ImportMBean, ExportMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String getName();
}
