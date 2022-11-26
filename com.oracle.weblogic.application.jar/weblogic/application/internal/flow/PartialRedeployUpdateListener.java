package weblogic.application.internal.flow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.AdminModeCallback;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.DeploymentContext;
import weblogic.application.DeploymentManager;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.WorkDeployment;
import weblogic.application.utils.EarUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

final class PartialRedeployUpdateListener implements UpdateListener {
   private final DeploymentManager dm = DeploymentManager.getDeploymentManager();
   private final ApplicationContextInternal appCtx;
   private final List redeployModules = new ArrayList();
   private int activateCount;

   PartialRedeployUpdateListener(ApplicationContextInternal appCtx) {
      this.appCtx = appCtx;
   }

   public boolean acceptURI(String uri) {
      ApplicationBean appDD = this.appCtx.getApplicationDD();
      if (appDD == null) {
         return false;
      } else {
         ModuleBean[] m = appDD.getModules();
         if (m == null) {
            return false;
         } else {
            for(int i = 0; i < m.length; ++i) {
               if (uri.equals(EarUtils.reallyGetModuleURI(m[i]))) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public void prepareUpdate(String uri) throws ModuleException {
      ModuleBean[] m = this.appCtx.getApplicationDD().getModules();
      String moduleId = uri;

      for(int i = 0; i < m.length; ++i) {
         if (m[i].getWeb() != null && m[i].getWeb().getWebUri().equals(uri)) {
            moduleId = EarUtils.getContextRootName(m[i]);
            break;
         }
      }

      this.redeployModules.add(moduleId);
      this.activateCount = 0;
   }

   public void activateUpdate(String uri) throws ModuleException {
      ++this.activateCount;
      if (this.activateCount >= this.redeployModules.size()) {
         try {
            String[] uris = (String[])((String[])this.redeployModules.toArray(new String[this.redeployModules.size()]));
            WorkDeployment d = this.dm.findDeployment(this.appCtx.getApplicationId());

            try {
               DeploymentContext deploymentContext = new DeploymentContextImpl(this.appCtx.getDeploymentInitiator(), this.appCtx.getProposedDomain(), uris);
               d.stop(deploymentContext);
               d.start(deploymentContext);
            } catch (DeploymentException var8) {
               throw new ModuleException(var8);
            }
         } finally {
            this.redeployModules.clear();
         }

      }
   }

   public void rollbackUpdate(String uri) {
      this.redeployModules.clear();
   }

   private class DeploymentContextImpl implements DeploymentContext {
      private final AuthenticatedSubject initiator;
      private final DomainMBean proposedDomain;
      private final String[] resourceURIs;

      private DeploymentContextImpl(AuthenticatedSubject initiator, DomainMBean proposedDomain, String[] resourceURIs) {
         this.initiator = initiator;
         this.proposedDomain = proposedDomain;
         if (resourceURIs == null) {
            this.resourceURIs = new String[0];
         } else {
            this.resourceURIs = resourceURIs;
         }

      }

      public AdminModeCallback getAdminModeCallback() {
         return null;
      }

      public InputStream getAltDD() {
         return null;
      }

      public InputStream getAltWLDD() {
         return null;
      }

      public InputStream getApplicationDescriptor() {
         return null;
      }

      public int getDeploymentOperation() {
         return 0;
      }

      public AuthenticatedSubject getInitiator() {
         return this.initiator;
      }

      public DomainMBean getProposedDomain() {
         return this.proposedDomain;
      }

      public int getRMIGracePeriodSecs() {
         return 0;
      }

      public String[] getUpdatedResourceURIs() {
         return this.resourceURIs;
      }

      public String[] getUserSuppliedTargets() {
         return null;
      }

      public InputStream getWLApplicationDescriptor() {
         return null;
      }

      public boolean isAdminModeTransition() {
         return false;
      }

      public boolean isAppStaged() {
         return false;
      }

      public boolean isIgnoreSessionsEnabled() {
         return false;
      }

      public boolean isStaticDeploymentOperation() {
         return false;
      }

      public boolean requiresRestart() {
         return false;
      }

      public boolean isAdminModeSpecified() {
         return false;
      }

      public String getResourceGroupTemplate() {
         return null;
      }

      public String getResourceGroup() {
         return null;
      }

      public boolean isSpecifiedTargetsOnly() {
         return false;
      }

      public void setAdminModeTransition(boolean newValue) {
      }

      public void setAdminModeCallback(AdminModeCallback callback) {
      }

      // $FF: synthetic method
      DeploymentContextImpl(AuthenticatedSubject x1, DomainMBean x2, String[] x3, Object x4) {
         this(x1, x2, x3);
      }
   }
}
