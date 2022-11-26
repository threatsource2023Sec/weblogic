package weblogic.deploy.internal.targetserver.operations;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.application.Deployment;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.OrderedDeployments;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;

public final class RemoveOperation extends DeactivateOperation {
   private String[] moduleIds = null;
   private String[] subModuleIds = null;
   private boolean doAssertUndeployable = true;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public RemoveOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean restartRequired) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, restartRequired);
      if (this.mbean != null) {
         this.operation = 4;
         this.moduleIds = TargetHelper.getNonGlobalModules(this.deploymentData, this.getApplication(), proposedDomain);
         this.subModuleIds = TargetHelper.getNonGlobalSubModules(this.deploymentData, this.getApplication());
         this.dumpModuleInfo();
         this.controlOperation = false;
      }

   }

   private void dumpModuleInfo() {
      if (this.isDebugEnabled()) {
         int i;
         String id;
         if (this.moduleIds != null) {
            this.debug("Module Ids:");

            for(i = 0; i < this.moduleIds.length; ++i) {
               id = this.moduleIds[i];
               this.debug("  " + id);
            }
         }

         if (this.subModuleIds != null) {
            this.debug("SubModule Ids:");

            for(i = 0; i < this.subModuleIds.length; ++i) {
               id = this.subModuleIds[i];
               this.debug("  " + id);
            }
         }

      }
   }

   protected boolean isRequireBasicDeploymentMBean() {
      return false;
   }

   protected BasicDeployment createBasicDeployment(BasicDeploymentMBean mbean) {
      BasicDeployment basicDep = OrderedDeployments.getOrDeferredBasicDeployment(mbean);
      return basicDep != null ? basicDep : super.createBasicDeployment(mbean);
   }

   protected void compatibilityProcessor() {
      MBeanConverter.remove81MBean((AppDeploymentMBean)this.mbean);
   }

   protected void doCommit() throws DeploymentException {
      if (this.mbean != null) {
         boolean localDoAssertUndeployable = this.doAssertUndeployable;
         this.doAssertUndeployable = true;
         boolean needRemoveStagedFiles = true;
         boolean fullRemove;
         if (cluster != null) {
            fullRemove = this.isFullRemoveFromCluster();
            if (!fullRemove) {
               needRemoveStagedFiles = false;
               if (!this.isTargetListContainsCurrentServer()) {
                  return;
               }
            }
         }

         fullRemove = this.isFullRemove();
         this.appcontainer = this.getApplication().findDeployment();
         if (this.isDebugEnabled()) {
            this.debug("RemoveOperation.doCommit for " + this.getApplication().getName());
         }

         if (this.appcontainer != null) {
            if (!fullRemove) {
               this.stop(this.appcontainer, this.moduleIds);
            } else {
               this.removeDeployment(localDoAssertUndeployable);
            }
         } else if (this.isDebugEnabled()) {
            this.debug("RemoveOperation.doCommit: No app container found for " + this.getApplication().getName());
         }

         if (fullRemove) {
            this.getApplication().remove(needRemoveStagedFiles);
            this.appcontainer = null;
         }

         this.complete(3, (Exception)null);
         if (fullRemove && !ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            try {
               AppRuntimeStateManager.getManager().remove(this.mbean.getName());
            } catch (Throwable var5) {
            }
         }

      }
   }

   protected void doPrepare() throws DeploymentException {
      if (this.mbean != null) {
         this.doAssertUndeployable = true;
         super.doPrepare();
         Deployment dep = this.getApplication().findDeployment();
         if (dep != null) {
            try {
               dep.assertUndeployable();
               this.doAssertUndeployable = false;
            } catch (DeploymentException var3) {
               this.complete(2, var3);
               throw var3;
            }
         }

      }
   }

   protected void doCancel() {
      this.doAssertUndeployable = true;
      super.doCancel();
   }

   private boolean isFullRemove() {
      boolean full = this.moduleIds == null && this.subModuleIds == null;
      full = full && (this.deploymentData.hasModuleTargets() || this.deploymentData.getGlobalTargets().length != 0 || !this.deploymentData.hasSubModuleTargets());
      if (this.isDebugEnabled()) {
         this.debug("isFullRemove set to : " + full);
      }

      return full;
   }

   protected final boolean isDeploymentRequestValidForCurrentServer() {
      if (this.isTargetListContainsCurrentServer()) {
         if (this.isDebugEnabled()) {
            this.debug("TargetList Contains Current Server");
         }

         return true;
      } else {
         String[] globalTargets = this.deploymentData.getGlobalTargets();
         if (cluster != null) {
            Set clusterServers = cluster.getServerNames();

            for(int i = 0; i < globalTargets.length; ++i) {
               if (clusterServers.contains(globalTargets[i])) {
                  return true;
               }
            }
         }

         if (!(this.mbean instanceof AppDeploymentMBean)) {
            return false;
         } else {
            AppDeploymentMBean bean = (AppDeploymentMBean)this.mbean;
            Set mbeanTargets = TargetHelper.getAllTargetedServers(bean);
            boolean isValidRequest = false;
            if (cluster == null) {
               return false;
            } else {
               String clusterName = cluster.getName();
               ServerMBean[] mbeanOfServersInTheCluster = cluster.getServers();

               for(int i = 0; i < globalTargets.length; ++i) {
                  if (globalTargets[i].equals(clusterName)) {
                     return true;
                  }
               }

               String[] serversInTheCluster = new String[mbeanOfServersInTheCluster.length];

               for(int i = 0; i < serversInTheCluster.length; ++i) {
                  serversInTheCluster[i] = mbeanOfServersInTheCluster[i].getName();
               }

               List listOfTargetsInRequest = Arrays.asList(globalTargets);
               List serversInCluster = Arrays.asList(serversInTheCluster);
               ArrayList pinnedServers = new ArrayList();
               if (mbeanTargets.containsAll(serversInCluster)) {
                  pinnedServers.addAll(serversInCluster);
               } else {
                  Iterator mbeanTargetsIter = mbeanTargets.iterator();

                  while(mbeanTargetsIter.hasNext()) {
                     Object eachTarget = mbeanTargetsIter.next();
                     if (serversInCluster.contains(eachTarget)) {
                        pinnedServers.add(eachTarget);
                     }
                  }
               }

               if (this.isDebugEnabled()) {
                  this.debug("pinned servers " + pinnedServers);
                  this.debug("listOfTargetsInRequest " + listOfTargetsInRequest);
                  this.debug("mbean targets " + mbeanTargets);
               }

               return listOfTargetsInRequest.containsAll(pinnedServers);
            }
         }
      }
   }

   private boolean isFullRemoveFromCluster() {
      Debug.assertion(cluster != null);
      if (!this.isFullRemove()) {
         return false;
      } else if (this.deploymentData.isTargetsFromConfig()) {
         return true;
      } else {
         TargetMBean[] targets = this.mbean.getTargets();
         ArrayList targetsList = new ArrayList();

         for(int i = 0; i < targets.length; ++i) {
            targetsList.addAll(targets[i].getServerNames());
         }

         String[] removeTargets = this.deploymentData.getGlobalTargets();

         for(int i = 0; i < removeTargets.length; ++i) {
            targetsList.remove(removeTargets[i]);
         }

         if (targetsList.isEmpty()) {
            return true;
         } else {
            try {
               Set removeTargetSet = new HashSet();
               removeTargetSet.addAll(Arrays.asList(removeTargets));
               Set targetSets = this.deploymentData.getAllTargetedServers(removeTargetSet);
               Iterator targetsit = targetSets.iterator();

               while(targetsit.hasNext()) {
                  targetsList.remove(targetsit.next());
               }
            } catch (InvalidTargetException var7) {
            }

            return targetsList.isEmpty();
         }
      }
   }

   private void removeDeployment(boolean localDoAssertUndeployable) {
      try {
         this.deactivate();
      } catch (DeploymentException var3) {
         SlaveDeployerLogger.logOperationFailed("Deactivate", this.getMBean().getName(), var3);
      }

      if (this.appcontainer != null) {
         if (this.getState(this.appcontainer) >= 1) {
            this.silentUnprepare(this.appcontainer);
         }

         this.silentRemove(this.appcontainer, localDoAssertUndeployable);
      }

   }
}
