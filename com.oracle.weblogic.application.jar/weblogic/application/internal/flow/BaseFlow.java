package weblogic.application.internal.flow;

import java.util.ArrayList;
import java.util.List;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.DeploymentContext;
import weblogic.application.Module;
import weblogic.application.internal.Flow;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.ExceptionUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;

public abstract class BaseFlow implements Flow {
   protected final FlowContext appCtx;

   protected BaseFlow(ApplicationContextInternal appCtx) {
      this.appCtx = (FlowContext)appCtx;
   }

   public String toString() {
      return this.getClass().getSimpleName() + " [" + this.appCtx.getApplicationId() + "]";
   }

   protected void throwAppException(Throwable th) throws DeploymentException {
      ExceptionUtils.throwDeploymentException(th);
   }

   public void remove() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("remove");
      }

   }

   public void prepare() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("prepare");
      }

   }

   public void activate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("activate");
      }

   }

   public void assertUndeployable() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("assertUndeployable");
      }

   }

   public void deactivate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("deactivate");
      }

   }

   public void unprepare() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("unprepare");
      }

   }

   public void start(String[] uris) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("start");
      }

   }

   public void stop(String[] uris) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("stop");
      }

   }

   public void prepareUpdate(String[] uris) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("prepareUpdate");
      }

   }

   public void activateUpdate(String[] uris) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("activateUpdate");
      }

   }

   public void rollbackUpdate(String[] uris) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("rollbackUpdate");
      }

   }

   public void adminToProduction() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("adminToProduction");
      }

   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("forceProductionToAdmin");
      }

   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("gracefulProductionToAdmin");
      }

   }

   public void validateRedeploy(DeploymentContext deplCtx) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("validateRedeploy");
      }

   }

   protected boolean isParallelActivateEnabled() {
      BasicDeploymentMBean bean = this.appCtx.getBasicDeploymentMBean();
      if (bean instanceof AppDeploymentMBean && ((AppDeploymentMBean)bean).isParallelDeployModules()) {
         ApplicationBean appBean = this.appCtx.getApplicationDD();
         return appBean == null || !Boolean.parseBoolean(this.appCtx.getApplicationDD().getInitializeInOrder());
      } else {
         return false;
      }
   }

   private boolean isConcurrent(Module module) {
      return this.appCtx.getModuleAttributes(module.getId()).isConcurrent();
   }

   private boolean inInSamePartition(Module module, Module otherModule) {
      return !(this.isConcurrent(module) ^ this.isConcurrent(otherModule));
   }

   public List partitionModules(Module[] modules) {
      List groups = new ArrayList();
      if (modules != null && modules.length > 0) {
         int start = 0;
         int end = 0;

         boolean firstEntry;
         Module[] subArray;
         for(firstEntry = true; end < modules.length; ++end) {
            if (!this.inInSamePartition(modules[start], modules[end])) {
               if (firstEntry) {
                  firstEntry = false;
                  if (this.isConcurrent(modules[start])) {
                     groups.add(new Module[0]);
                  }
               }

               subArray = new Module[end - start];
               System.arraycopy(modules, start, subArray, 0, end - start);
               groups.add(subArray);
               start = end;
            }
         }

         if (firstEntry) {
            firstEntry = false;
            if (this.isConcurrent(modules[start])) {
               groups.add(new Module[0]);
            }
         }

         subArray = new Module[end - start];
         System.arraycopy(modules, start, subArray, 0, end - start);
         groups.add(subArray);
      }

      return groups;
   }

   protected boolean isDebugEnabled() {
      return this.appCtx.isDebugEnabled();
   }

   protected void debug(String message) {
      this.appCtx.debug('[' + this.getClass().getSimpleName() + ']' + ' ' + message);
   }

   protected void debug(String message, Throwable t) {
      this.appCtx.debug('[' + this.getClass().getSimpleName() + ']' + ' ' + message, t);
   }
}
