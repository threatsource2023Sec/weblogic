package weblogic.deploy.internal.targetserver;

import java.io.File;
import java.io.IOException;
import weblogic.application.Deployment;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.deploy.internal.targetserver.datamanagement.AppData;
import weblogic.deploy.internal.targetserver.datamanagement.Data;
import weblogic.deploy.internal.targetserver.operations.AbstractOperation;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.deploy.internal.SlaveDeployerLogger;

public class SystemResourceDeployment extends BasicDeployment {
   public SystemResourceDeployment(SystemResourceMBean srmBean, ServerMBean server) {
      super(srmBean, server);
   }

   public void verifyAppVersionSecurity(AbstractOperation task) throws DeploymentException {
   }

   public void prepare() throws IOException, DeploymentException {
      if (isDebugEnabled()) {
         debug("Preparing " + this.name);
      }

      try {
         this.fireVetoableDeploymentEvent();
         this.stageFilesForStatic();

         weblogic.application.DeploymentManager.DeploymentCreator deploymentCreator;
         try {
            deploymentCreator = this.deploymentManager.getDeploymentCreator(this.deploymentMBean, (File)null);
         } catch (DeploymentException var9) {
            Throwable cause = var9.getCause();
            throw new DeploymentException(cause.getMessage(), new DeploymentVetoException(cause));
         }

         Deployment deployment = this.createDeployment(deploymentCreator, this.deploymentMBean, (DeploymentState)null);
         DeploymentContextImpl deploymentContext;
         if (this.task != null && this.task.getDeploymentContext() != null) {
            deploymentContext = this.task.getDeploymentContext();
         } else {
            deploymentContext = DeployHelper.createDeploymentContext(this.deploymentMBean);
            deploymentContext.setStaticDeploymentOperation(true);
         }

         deployment.prepare(deploymentContext);
      } catch (Throwable var10) {
         this.removeDeployment();
         DeploymentException ex = DeployHelper.convertThrowable(var10);
         SlaveDeployerLogger.logIntialPrepareApplicationFailedLoggable(this.name, ex).log();
         throw ex;
      } finally {
         ;
      }
   }

   public void updateDescriptorsPathInfo() {
   }

   public void removeDeployment() {
      this.deploymentManager.removeDeployment(this.deploymentMBean.getName());
   }

   protected final Data createLocalData() {
      BasicDeploymentMBean mbean = this.getDeploymentMBean();
      return new AppData(this.server, mbean, this, "staged", DeployHelper.getSourcePath(mbean), (String)null);
   }
}
