package weblogic.deploy.api.tools.remote;

import java.io.IOException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.ManagementException;
import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.SessionHelperRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class SessionHelperRuntime extends RuntimeMBeanDelegate implements SessionHelperRuntimeMBean {
   private RemoteSessionHelper helperOrig = null;
   private RemoteSessionHelper helperSaved = null;
   private RemoteSessionHelper helper = null;
   private WebLogicDeploymentManager dm = null;
   private WLSModelMBeanContext context = null;
   private AppInfo appInfo = null;
   private final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public SessionHelperRuntime(AppInfo appInfo, WLSModelMBeanContext context) throws ConfigurationException, IOException, InvalidModuleException, ManagementException, DeploymentManagerCreationException {
      super(appInfo.getName(), false);
      this.context = context;
      this.appInfo = appInfo;
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(this.kernelId).getServerRuntime();
      this.dm = RemoteSessionHelper.getDeploymentManager(serverRuntime.getAdminServerHost(), (new Integer(serverRuntime.getAdminServerListenPort())).toString());
      this.helperOrig = new RemoteSessionHelper(this.dm, appInfo, appInfo.getPlanPath(), context);
      this.helperOrig.initConfig((RemoteSessionHelper)null);
      String pendingPlanPath = RemoteSessionHelper.getPendingPlanPath(appInfo.getName());
      if (pendingPlanPath != null) {
         this.helper = new RemoteSessionHelper(this.dm, appInfo, pendingPlanPath, context);
         this.helperSaved = new RemoteSessionHelper(this.dm, appInfo, pendingPlanPath, context);
         this.helperSaved.initConfig((RemoteSessionHelper)null);
         this.helper.setSaved(this.helperSaved);
      } else {
         this.helper = new RemoteSessionHelper(this.dm, appInfo, appInfo.getPlanPath(), context);
      }

      this.helper.initConfig(this.helperOrig);
   }

   public WeblogicApplicationBean getWeblogicApplicationBean() {
      return this.helper.getWeblogicApplicationBean();
   }

   public ApplicationBean getApplicationBean() {
      return this.helper.getApplicationBean();
   }

   public List getWebAppBeans() {
      return this.helper.getWebAppBeans();
   }

   public List getEjbJarBeans() {
      return this.helper.getEjbJarBeans();
   }

   public List getConnectorBeans() {
      return this.helper.getConnectorBeans();
   }

   public List getGarBeans() {
      return this.helper.getGarBeans();
   }

   public List getModuleBeans() {
      return this.helper.getModuleBeans();
   }

   public void savePlan() {
      try {
         if (this.helper != null) {
            this.helper.savePlan();
            boolean dirExists = this.helper.getPlan().getAbsoluteFile().getParentFile().exists();
            this.helperSaved = new RemoteSessionHelper(this.dm, this.appInfo, this.helper.getPlan().getAbsolutePath(), this.context);
            this.helperSaved.initConfig((RemoteSessionHelper)null);
            this.helper.setSaved(this.helperSaved);
         }

      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public void activateChanges() {
      try {
         this.helper.activateChanges();
         this.helperOrig = new RemoteSessionHelper(this.dm, this.appInfo, this.helper.getPlan().getAbsolutePath(), this.context);
         this.helperOrig.initConfig((RemoteSessionHelper)null);
         this.helper.setOrig(this.helperOrig);
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public Iterator getChanges() {
      return this.helper.getChanges();
   }

   public Iterator getUnactivatedChanges() {
      return this.helper.getUnactivatedChanges();
   }

   public boolean isModified() {
      return this.helper.isModified();
   }

   public void updateApplication() {
      this.helper.updateApplication();
   }

   public void undoUnsavedChanges() {
      this.helper.undoUnsavedChanges();
   }

   public void undoUnactivatedChanges() {
      this.helper.undoUnactivatedChanges();
   }
}
