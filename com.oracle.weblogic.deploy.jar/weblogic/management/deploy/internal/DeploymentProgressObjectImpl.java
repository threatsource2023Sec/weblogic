package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.TargetStatus;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentProgressObjectMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class DeploymentProgressObjectImpl extends DomainRuntimeMBeanDelegate implements DeploymentProgressObjectMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   DeploymentTaskRuntimeMBean deploymentTaskRuntime;
   AppDeploymentMBean deployable;
   List messages = new ArrayList();
   private List exceptions = new ArrayList();

   public DeploymentProgressObjectImpl(String name, DeploymentTaskRuntimeMBean task, AppDeploymentMBean deployable, RuntimeMBeanDelegate restParent) throws ManagementException {
      super(ApplicationVersionUtils.getNonPartitionName(deployable.getName()), (RuntimeMBean)("DOMAIN".equals(ApplicationVersionUtils.getPartitionName(deployable.getName())) ? ManagementService.getDomainAccess(kernelId).getDomainRuntime() : restParent));
      this.setRestParent(restParent);
      this.deploymentTaskRuntime = task;
      this.deployable = deployable;
   }

   public String getId() {
      return this.deploymentTaskRuntime != null ? this.deploymentTaskRuntime.getId() : null;
   }

   public int getOperationType() {
      if (this.deploymentTaskRuntime != null) {
         int opType = this.deploymentTaskRuntime.getTask();
         switch (opType) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 10:
               return 0;
            case 4:
            case 12:
               return 5;
            case 7:
               return 1;
            case 8:
               return 2;
            case 9:
               return 4;
            case 11:
               return 3;
         }
      }

      return 0;
   }

   public String getApplicationName() {
      return this.getName();
   }

   public AppDeploymentMBean getAppDeploymentMBean() {
      if (this.deployable instanceof LibraryMBean) {
         return null;
      } else {
         return this.deploymentTaskRuntime.getDeploymentData().getDeploymentOptions().getResourceGroupTemplate() != null ? null : this.deployable;
      }
   }

   public LibraryMBean getLibraryMBean() {
      if (this.deployable instanceof LibraryMBean) {
         return this.deploymentTaskRuntime.getDeploymentData().getDeploymentOptions().getResourceGroupTemplate() != null ? null : (LibraryMBean)this.deployable;
      } else {
         return null;
      }
   }

   public String getState() {
      if (this.deploymentTaskRuntime != null) {
         int state = this.deploymentTaskRuntime.getState();
         if (0 == state) {
            return "STATE_INITIALIZED";
         }

         if (1 == state) {
            return "STATE_RUNNING";
         }

         if (2 == state) {
            return "STATE_COMPLETED";
         }

         if (3 == state) {
            return "STATE_FAILED";
         }

         if (4 == state) {
            return "STATE_DEFERRED";
         }
      }

      return "Unknown";
   }

   public String[] getTargets() {
      List targets = new ArrayList();
      if (this.deploymentTaskRuntime != null) {
         TargetStatus[] targetStatus = this.deploymentTaskRuntime.getTargets();
         if (targetStatus != null) {
            for(int n = 0; n < targetStatus.length; ++n) {
               targets.add(targetStatus[n].getTarget());
            }
         }
      }

      return (String[])((String[])targets.toArray(new String[0]));
   }

   public String[] getFailedTargets() {
      List targets = new ArrayList();
      if (this.deploymentTaskRuntime != null && this.deploymentTaskRuntime instanceof DeploymentTaskRuntime) {
         Map targetStatus = ((DeploymentTaskRuntime)this.deploymentTaskRuntime).getFailedTargets();
         if (targetStatus != null) {
            Set failedTargets = targetStatus.keySet();
            Iterator failedIt = failedTargets.iterator();

            while(failedIt.hasNext()) {
               targets.add((String)failedIt.next());
            }
         }
      }

      return (String[])((String[])targets.toArray(new String[0]));
   }

   public void addMessages(List msgs) {
      this.messages.addAll(msgs);
   }

   public String[] getMessages() {
      if (this.deploymentTaskRuntime != null) {
         this.messages.addAll(this.deploymentTaskRuntime.getTaskMessages());
      }

      return (String[])((String[])this.messages.toArray(new String[0]));
   }

   public RuntimeException[] getExceptions(String target) {
      List ret = new ArrayList();
      if (this.deploymentTaskRuntime != null) {
         TargetStatus ts = this.deploymentTaskRuntime.findTarget(target);
         Exception[] excs = ts != null ? ts.getMessages() : null;

         for(int n = 0; excs != null && n < excs.length; ++n) {
            ret.add(ExceptionTranslator.translateException(excs[n]));
         }
      }

      return (RuntimeException[])ret.toArray(new RuntimeException[0]);
   }

   public RuntimeException[] getRootExceptions() {
      List ret = new ArrayList();

      for(int n = 0; n < this.exceptions.size(); ++n) {
         ret.add(ExceptionTranslator.translateException((Throwable)this.exceptions.get(n)));
      }

      return (RuntimeException[])ret.toArray(new RuntimeException[0]);
   }

   public void cancel() throws RuntimeException {
      if (this.deploymentTaskRuntime != null) {
         try {
            this.deploymentTaskRuntime.cancel();
         } catch (Throwable var2) {
            throw ExceptionTranslator.translateException(var2);
         }
      }

   }

   public void setDeploymentTaskRuntime(DeploymentTaskRuntimeMBean task) {
      this.deploymentTaskRuntime = task;
   }

   public DeploymentTaskRuntimeMBean getDeploymentTaskRuntime() {
      return this.deploymentTaskRuntime;
   }

   public void clear() {
      this.deploymentTaskRuntime = null;

      try {
         this.unregister();
      } catch (Exception var2) {
      }

   }

   public long getBeginTime() {
      return this.deploymentTaskRuntime != null && this.deploymentTaskRuntime instanceof DeploymentTaskRuntime ? ((DeploymentTaskRuntime)this.deploymentTaskRuntime).getBeginTime() : 0L;
   }

   public long getEndTime() {
      return this.deploymentTaskRuntime != null && this.deploymentTaskRuntime instanceof DeploymentTaskRuntime ? ((DeploymentTaskRuntime)this.deploymentTaskRuntime).getEndTime() : 0L;
   }

   public void addException(Throwable th) {
      this.exceptions.add(th);
   }
}
