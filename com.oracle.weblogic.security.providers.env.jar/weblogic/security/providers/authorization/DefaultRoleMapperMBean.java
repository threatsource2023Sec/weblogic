package weblogic.security.providers.authorization;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.authorization.DeployableRoleMapperMBean;
import weblogic.management.security.authorization.PredicateEditorMBean;
import weblogic.management.security.authorization.RoleAuxiliaryMBean;
import weblogic.management.security.authorization.RoleConsumerMBean;
import weblogic.management.security.authorization.RoleListerMBean;

public interface DefaultRoleMapperMBean extends StandardInterface, DescriptorBean, DeployableRoleMapperMBean, RoleAuxiliaryMBean, RoleListerMBean, PredicateEditorMBean, RoleConsumerMBean, ApplicationVersionerMBean, ImportMBean, ExportMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String getName();
}
