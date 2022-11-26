package weblogic.application;

import java.io.InputStream;
import weblogic.management.configuration.DomainMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface DeploymentContext {
   DomainMBean getProposedDomain();

   String[] getUpdatedResourceURIs();

   AuthenticatedSubject getInitiator();

   AdminModeCallback getAdminModeCallback();

   boolean isAdminModeSpecified();

   boolean isAdminModeTransition();

   boolean isIgnoreSessionsEnabled();

   int getRMIGracePeriodSecs();

   String[] getUserSuppliedTargets();

   boolean requiresRestart();

   InputStream getApplicationDescriptor();

   InputStream getWLApplicationDescriptor();

   InputStream getAltDD();

   InputStream getAltWLDD();

   int getDeploymentOperation();

   boolean isStaticDeploymentOperation();

   boolean isAppStaged();

   String getResourceGroupTemplate();

   String getResourceGroup();

   void setAdminModeTransition(boolean var1);

   void setAdminModeCallback(AdminModeCallback var1);

   boolean isSpecifiedTargetsOnly();
}
