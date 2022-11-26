package weblogic.deploy.internal.targetserver;

import java.io.InputStream;
import weblogic.application.AdminModeCallback;
import weblogic.application.DeploymentContext;
import weblogic.management.configuration.DomainMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class DeploymentContextImpl implements DeploymentContext {
   private DomainMBean proposedDomain = null;
   private String[] updatedResources = null;
   private final AuthenticatedSubject initiator;
   private AdminModeCallback adminModeCallback = null;
   private boolean isAdminModeTransition;
   private boolean isIgnoreSessionsEnabled;
   private int rmiGracePeriodSecs;
   private String[] userSuppliedTargets = null;
   private boolean requiresRestart = false;
   private int deploymentOperation;
   private boolean isStatic = false;
   private boolean isStaged = false;
   private boolean isAdminModeSpecified = false;
   private String resourceGroupTemplate = null;
   private String resourceGroup = null;
   private boolean isSpecifiedTargetsOnly = false;

   public DeploymentContextImpl(AuthenticatedSubject initiator) {
      this.initiator = initiator;
   }

   public final DomainMBean getProposedDomain() {
      return this.proposedDomain;
   }

   public final void setProposedDomain(DomainMBean proposedDomain) {
      if (this.proposedDomain == null) {
         this.proposedDomain = proposedDomain;
      }

   }

   public final String[] getUpdatedResourceURIs() {
      return this.updatedResources == null ? new String[0] : this.updatedResources;
   }

   public final void setUpdatedResourceURIs(String[] resources) {
      if (this.updatedResources == null) {
         this.updatedResources = resources;
      }

   }

   public final AuthenticatedSubject getInitiator() {
      return this.initiator;
   }

   public final AdminModeCallback getAdminModeCallback() {
      return this.adminModeCallback;
   }

   public final void setAdminModeCallback(AdminModeCallback callback) {
      if (this.adminModeCallback == null) {
         this.adminModeCallback = callback;
      }

   }

   public final boolean isAdminModeTransition() {
      return this.isAdminModeTransition;
   }

   public final void setAdminModeTransition(boolean newValue) {
      this.isAdminModeTransition = newValue;
   }

   public final boolean isIgnoreSessionsEnabled() {
      return this.isIgnoreSessionsEnabled;
   }

   public final void setRMIGracePeriodSecs(int secs) {
      this.rmiGracePeriodSecs = secs;
   }

   public final int getRMIGracePeriodSecs() {
      return this.rmiGracePeriodSecs;
   }

   public final String[] getUserSuppliedTargets() {
      return this.userSuppliedTargets;
   }

   public final void setIgnoreSessions(boolean newValue) {
      this.isIgnoreSessionsEnabled = newValue;
   }

   public final void setUserSuppliedTargets(String[] theTargets) {
      this.userSuppliedTargets = theTargets;
   }

   public final void setRequiresRestart(boolean restart) {
      this.requiresRestart = restart;
   }

   public final boolean requiresRestart() {
      return this.requiresRestart;
   }

   public final void setDeploymentOperation(int deploymentOperation) {
      this.deploymentOperation = deploymentOperation;
   }

   public final int getDeploymentOperation() {
      return this.deploymentOperation;
   }

   public final void setStaticDeploymentOperation(boolean bVal) {
      this.isStatic = bVal;
   }

   public final boolean isStaticDeploymentOperation() {
      return this.isStatic;
   }

   public boolean isAppStaged() {
      return this.isStaged;
   }

   public void setAppStaged(boolean givenIsStaged) {
      this.isStaged = givenIsStaged;
   }

   public final InputStream getApplicationDescriptor() {
      return null;
   }

   public final InputStream getWLApplicationDescriptor() {
      return null;
   }

   public final InputStream getAltDD() {
      return null;
   }

   public final InputStream getAltWLDD() {
      return null;
   }

   public boolean isAdminModeSpecified() {
      return this.isAdminModeSpecified;
   }

   public final void setAdminModeSpecified(boolean adminModeSpecifiedFlag) {
      this.isAdminModeSpecified = adminModeSpecifiedFlag;
   }

   public String getResourceGroupTemplate() {
      return this.resourceGroupTemplate;
   }

   public void setResourceGroupTemplate(String rgt) {
      this.resourceGroupTemplate = rgt;
   }

   public String getResourceGroup() {
      return this.resourceGroup;
   }

   public void setResourceGroup(String rg) {
      this.resourceGroup = rg;
   }

   public boolean isSpecifiedTargetsOnly() {
      return this.isSpecifiedTargetsOnly;
   }

   public void setSpecifiedTargetsOnly(boolean sto) {
      this.isSpecifiedTargetsOnly = sto;
   }
}
