package weblogic.security.providers.xacml.authorization;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.authorization.DeployableAuthorizerMBean;
import weblogic.management.security.authorization.PolicyAuxiliaryMBean;
import weblogic.management.security.authorization.PolicyConsumerMBean;
import weblogic.management.security.authorization.PolicyListerMBean;
import weblogic.management.security.authorization.PolicyStoreMBean;
import weblogic.management.security.authorization.PredicateEditorMBean;
import weblogic.management.utils.InvalidParameterException;

public interface XACMLAuthorizerMBean extends StandardInterface, DescriptorBean, DeployableAuthorizerMBean, PolicyStoreMBean, PolicyAuxiliaryMBean, PolicyListerMBean, PredicateEditorMBean, PolicyConsumerMBean, ApplicationVersionerMBean, ImportMBean, ExportMBean, IdentityDomainAwareProviderMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String evaluate(String var1) throws InvalidParameterException;

   String evaluate(String var1, String var2) throws InvalidParameterException;

   void evaluate(String var1, String var2, String var3) throws InvalidParameterException;

   String getName();
}
