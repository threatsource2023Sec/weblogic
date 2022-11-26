package weblogic.deploy.service;

import java.util.Map;

public interface ConfigurationContext {
   String PROPOSED_CONFIGURATION_ID = "PROPOSED_CONFIGURATION";
   String DESC_DIFF_BEAN_UPDATE_ID = "beanUpdateDescriptorDiffId";
   String DESC_DIFF_BEAN_REVERT_ID = "beanRevertDescriptorDiffId";
   String EXT_DESC_DIFF_BEAN_UPDATE_ID = "externalDescritorDiffId";
   String CALLOUT_ABORTED_PROPOSED_CONFIG = "calloutAbortedProposedConfig";

   DeploymentRequest getDeploymentRequest();

   void addContextComponent(String var1, Object var2);

   Object getContextComponent(String var1);

   Map getContextComponents();
}
