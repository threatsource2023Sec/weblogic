package weblogic.deploy.api.tools;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import javax.enterprise.deploy.shared.StateType;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public class ManagedSessionHelper {
   private boolean debug;
   private static final AuthenticatedSubject kernelid = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String BAD_PLAN = "_wls_bad_plan.xml";
   private static final String BACKUP_PLAN = "_wls_plan_bk.xml";
   private AppDeploymentMBean mbean;
   private RuntimeAccess rt;
   protected WebLogicDeploymentManager myDM;
   private String server;
   private String host;
   private String port;
   private boolean isadmin;
   private boolean noplan;
   private File planBackup;
   private IOException backupPlanException;
   private boolean enableLightWeightView;
   protected SessionHelper helper;

   protected AppDeploymentMBean getAppDeploymentMBean() {
      return this.mbean;
   }

   protected String getPort() {
      return this.port;
   }

   protected String getHost() {
      return this.host;
   }

   protected void initSessionHelper(WebLogicDeploymentManager dm) {
      this.helper = SessionHelper.getInstance(this.myDM);
   }

   protected boolean isAdmin() {
      return this.isadmin;
   }

   public SessionHelper getHelper() {
      return this.helper;
   }

   public ManagedSessionHelper(AppDeploymentMBean mbean) throws ConfigurationException, IOException, DeploymentManagerCreationException, InvalidModuleException {
      this(mbean, (String)null, (String)null);
   }

   public ManagedSessionHelper(AppDeploymentMBean mbean, boolean enableLightWeightView) throws ConfigurationException, IOException, DeploymentManagerCreationException, InvalidModuleException {
      this.debug = Debug.isDebug("deploy");
      this.noplan = true;
      this.planBackup = null;
      this.backupPlanException = null;
      this.enableLightWeightView = false;
      this.enableLightWeightView = enableLightWeightView;
      this.rt = ManagementService.getRuntimeAccess(kernelid);
      if (this.rt == null) {
         throw new IllegalStateException("Must be running on server");
      } else {
         String admin = this.rt.getAdminServerName();
         ServerMBean adminServer = this.rt.getDomain().lookupServer(admin);
         this.port = (new Integer(adminServer.getListenPort())).toString();
         this.host = adminServer.getListenAddress();
         this.server = this.rt.getServerName();
         this.isadmin = admin.equals(this.server);
         this.mbean = mbean;
         this.createSession(mbean, (String)null, (String)null);
      }
   }

   public ManagedSessionHelper(AppDeploymentMBean mbean, String user, String pwd) throws ConfigurationException, IOException, DeploymentManagerCreationException, InvalidModuleException {
      this.debug = Debug.isDebug("deploy");
      this.noplan = true;
      this.planBackup = null;
      this.backupPlanException = null;
      this.enableLightWeightView = false;
      this.rt = ManagementService.getRuntimeAccess(kernelid);
      if (this.rt == null) {
         throw new IllegalStateException("Must be running on server");
      } else {
         String admin = this.rt.getAdminServerName();
         ServerMBean adminServer = this.rt.getDomain().lookupServer(admin);
         this.port = (new Integer(adminServer.getListenPort())).toString();
         this.host = adminServer.getListenAddress();
         this.server = this.rt.getServerName();
         this.isadmin = admin.equals(this.server);
         this.mbean = mbean;
         this.createSession(mbean, user, pwd);
      }
   }

   private void createSession(AppDeploymentMBean mbean, String user, String pwd) throws DeploymentManagerCreationException, ConfigurationException, IOException, InvalidModuleException {
      this.initDM(user, pwd);
      this.initSessionHelper(this.myDM);
      this.helper = this.getHelper();
      String f = mbean.getLocalInstallDir();
      if (f != null) {
         this.helper.setApplicationRoot(new File(f));
      }

      f = mbean.getLocalSourcePath();
      if (f != null) {
         this.helper.setApplication(new File(f));
      }

      f = mbean.getLocalPlanPath();
      if (f != null) {
         this.helper.setPlan(new File(f));
         this.noplan = false;
      }

      f = mbean.getLocalPlanDir();
      if (f != null) {
         this.helper.setPlandir(new File(f));
      } else {
         this.helper.setPlandir(this.getOrCreatePlanDir());
      }

      DeploymentPlanBean pb = mbean.getDeploymentPlanDescriptor();
      this.helper.setPlanBean(pb);
      if (pb != null) {
         pb.setConfigRoot(this.helper.getPlandir().getPath());
      }

      this.helper.enableLibraryMerge();
      if (this.enableLightWeightView) {
         this.helper.setLightWeightAppName(mbean.getApplicationName());
      }

      this.helper.initializeConfiguration(mbean);
   }

   protected void initDM(String user, String pwd) throws DeploymentManagerCreationException {
      if (user != null && !this.isadmin) {
         this.myDM = SessionHelper.getRemoteDeploymentManager(this.host, this.port, user, pwd);
      } else {
         this.myDM = SessionHelper.getDeploymentManager(this.host, this.port);
         if (!this.isadmin) {
            this.myDM.enableFileUploads();
         }
      }

   }

   public ProgressObject saveAndUpdate() throws ConfigurationException, IOException {
      File newPlan = this.getNewPlanFile();
      TargetModuleID[] tmids = this.myDM.getModules(this.mbean);
      if (this.debug) {
         Debug.say("Updating " + this.mbean.getName() + " with new plan: " + newPlan.getPath());
      }

      String[] uris = this.helper.getChangedDescriptors();
      ProgressObject po = this.myDM.update(tmids, newPlan, uris, (DeploymentOptions)null);
      this.waitForTask(po);
      DeploymentStatus deploymentStatus = po.getDeploymentStatus();
      if (deploymentStatus.getState() != StateType.COMPLETED) {
         this.restoreOrigPlan(newPlan);
         SPIDeployerLogger.logRestorePlan(deploymentStatus.getMessage());
      }

      if (!this.debug && this.planBackup != null) {
         this.planBackup.delete();
      }

      return po;
   }

   private void waitForTask(ProgressObject po) {
      while(po.getDeploymentStatus().isRunning()) {
         try {
            Thread.sleep(500L);
         } catch (InterruptedException var3) {
         }
      }

   }

   private void saveBadPlan(File plan) {
      try {
         FileUtils.copyPreservePermissions(plan, new File(plan.getParentFile(), "_wls_bad_plan.xml"));
      } catch (IOException var3) {
         Debug.say("Unable to copy the bad plan to a backup location : " + var3);
      }

   }

   private void restoreOrigPlan(File newPlan) throws IOException {
      if (this.planBackup != null) {
         if (this.debug) {
            this.saveBadPlan(newPlan);
         }

         try {
            FileUtils.copyPreservePermissions(this.planBackup, newPlan);
            this.mbean.setDeploymentPlanDescriptor((DeploymentPlanBean)null);
            DeploymentPlanBean pb = this.mbean.getDeploymentPlanDescriptor();
            this.helper.setPlanBean(pb);
            newPlan = null;
            this.planBackup = null;
         } catch (IOException var3) {
            throw new IOException(SPIDeployerLogger.restorePlanFailure(var3.getMessage(), this.planBackup.getPath()));
         }
      }
   }

   protected File getNewPlanFile() throws IOException, ConfigurationException {
      File pd = this.getOrCreatePlanDir();
      File newp = new File(pd, this.helper.getNewPlanName());
      if (this.helper.getPlan() != null && newp.getPath().equals(this.helper.getPlan().getPath())) {
         this.planBackup = new File(pd, "_wls_plan_bk.xml");

         try {
            FileUtils.copyPreservePermissions(this.helper.getPlan(), this.planBackup);
         } catch (IOException var4) {
            throw new IllegalArgumentException(SPIDeployerLogger.backupPlanError(var4.getMessage(), this.planBackup.getPath()));
         }
      }

      newp = this.helper.savePlan(pd, this.helper.getNewPlanName());
      return newp;
   }

   private File getOrCreatePlanDir() throws IOException {
      if (this.helper.getPlandir() != null) {
         return this.helper.getPlandir();
      } else {
         File pd;
         if (this.isadmin) {
            if (this.noplan) {
               pd = new File(DomainDir.getDeploymentsDir());
               if (this.debug) {
                  Debug.say("deployments dir: " + pd);
               }

               pd = new File(pd, this.mbean.getName());
               pd = new File(pd, "plan");
               pd.mkdirs();
            } else {
               pd = this.helper.getPlan().getParentFile();
            }

            if (this.debug) {
               Debug.say("Using plan dir: " + pd);
            }
         } else {
            pd = new File(DomainDir.getTempDirForServer(this.server));
         }

         return pd;
      }
   }

   public void close() {
      this.helper.close();
      this.myDM.release();
   }
}
