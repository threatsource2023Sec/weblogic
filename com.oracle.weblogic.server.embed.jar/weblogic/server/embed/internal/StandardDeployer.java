package weblogic.server.embed.internal;

import java.io.File;
import java.security.AccessController;
import java.util.List;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.embed.Deployer;
import weblogic.server.embed.EmbeddedServerException;

public class StandardDeployer implements Deployer {
   private static final int DEPLOYMENT_TIMEOUT_MILLIS = Integer.getInteger("weblogic.server.embed.deployer.timeout", 60) * 1000;

   public void deployApp(String name, File location) throws EmbeddedServerException {
      this.deploy(name, location, false);
   }

   public void deployLib(String name, File location) throws EmbeddedServerException {
      this.deploy(name, location, true);
   }

   public void undeployApp(String name) throws EmbeddedServerException {
      this.doOperation(name, false, StandardDeployer.OP.UNDEPLOY);
   }

   public void undeployLib(String name) throws EmbeddedServerException {
      this.doOperation(name, true, StandardDeployer.OP.UNDEPLOY);
   }

   public void redeployApp(String name) throws EmbeddedServerException {
      this.doOperation(name, false, StandardDeployer.OP.REDEPLOY);
   }

   public void redeployLib(String name) throws EmbeddedServerException {
      this.doOperation(name, true, StandardDeployer.OP.REDEPLOY);
   }

   public List getDeployedApps() {
      throw new UnsupportedOperationException("Unimplemented");
   }

   public List getDeployedLibs() {
      throw new UnsupportedOperationException("Unimplemented");
   }

   private void waitForProgress(ProgressObject progress) throws EmbeddedServerException {
      if (progress != null) {
         long t0 = System.currentTimeMillis();
         int waitTime = 100;

         while(progress.getDeploymentStatus().isRunning()) {
            if (System.currentTimeMillis() - t0 > (long)DEPLOYMENT_TIMEOUT_MILLIS) {
               throw new EmbeddedServerException("Deployment operation timed out ");
            }

            try {
               Thread.sleep((long)waitTime);
            } catch (InterruptedException var6) {
            }

            if (waitTime < 3000) {
               waitTime *= 2;
            }
         }

         DeploymentStatus status = progress.getDeploymentStatus();
         if (status.isFailed()) {
            throw new EmbeddedServerException("Deployment operation failed - " + status.getMessage());
         }
      }
   }

   public synchronized void deploy(String name, File path, boolean isLibrary) throws EmbeddedServerException {
      EmbeddedServerImpl.get().assertServerRunning();

      try {
         DeploymentOptions options = new DeploymentOptions();
         options.setName(name);
         options.setLibrary(isLibrary);
         options.setSucceedIfNameUsed(true);
         RuntimeAccess rtAccess = ManagementService.getRuntimeAccess(this.getKernelId());
         WebLogicDeploymentManager deployMgr = this.getDeployMgr();
         Target target = deployMgr.getTarget(rtAccess.getServer().getName());
         this.waitForProgress(deployMgr.deploy(new Target[]{target}, path, (File)null, options));
      } catch (Exception var8) {
         throw new EmbeddedServerException("Error during deployment", var8);
      }
   }

   public void doOperation(String name, boolean isLibrary, OP op) throws EmbeddedServerException {
      EmbeddedServerImpl.get().assertServerRunning();
      DeploymentOptions options = new DeploymentOptions();
      options.setName(name);
      options.setLibrary(isLibrary);
      if (op == StandardDeployer.OP.REDEPLOY) {
         this.waitForProgress(this.getDeployMgr().redeploy(new TargetModuleID[0], (File)null, (File)null, options));
      } else if (op == StandardDeployer.OP.UNDEPLOY) {
         this.waitForProgress(this.getDeployMgr().undeploy(new TargetModuleID[0], options));
      } else if (op == StandardDeployer.OP.START) {
         this.waitForProgress(this.getDeployMgr().start(new TargetModuleID[0], options));
      } else if (op == StandardDeployer.OP.STOP) {
         this.waitForProgress(this.getDeployMgr().stop(new TargetModuleID[0], options));
      }

   }

   private WebLogicDeploymentManager getDeployMgr() throws EmbeddedServerException {
      try {
         return SessionHelper.getDeploymentManager((String)null, (String)null);
      } catch (Exception var2) {
         throw new EmbeddedServerException("Error looking up deployment manager", var2);
      }
   }

   private AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static enum OP {
      START,
      STOP,
      UNDEPLOY,
      REDEPLOY,
      LIST;
   }
}
