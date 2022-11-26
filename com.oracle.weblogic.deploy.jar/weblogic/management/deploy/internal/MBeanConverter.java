package weblogic.management.deploy.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessController;
import java.util.Locale;
import javax.management.InvalidAttributeValueException;
import weblogic.application.DeploymentManager;
import weblogic.application.MBeanFactory;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentException;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.ArrayUtils;
import weblogic.utils.NestedException;

public class MBeanConverter {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static ApplicationMBean createApplicationForAppDeployment(DomainMBean root, AppDeploymentMBean appdeployment, String path) throws DeploymentException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer() && !TargetHelper.isTargetedLocaly(appdeployment) && !TargetHelper.isPinnedToServerInCluster(appdeployment)) {
         return null;
      } else {
         ApplicationMBean appMB = root.lookupApplication(appdeployment.getName());

         try {
            if (appMB != null) {
               return appMB;
            }

            if (isDebugEnabled()) {
               debug("Creating appmeans for " + appdeployment.getObjectName() + " In " + root.getObjectName() + " from " + path);
            }

            if (appdeployment.getSourcePath().toLowerCase(Locale.US).endsWith(".xml")) {
               appMB = createTEMPCompatMBean(appdeployment, root, path);
            } else {
               appMB = createApplicationMBean(appdeployment, root, path);
               setApplicationTargets(appdeployment, appMB);
            }

            appMB.setLoadOrder(appdeployment.getDeploymentOrder());
            appMB.setDelegationEnabled(true);
         } catch (Throwable var5) {
            handleException(appdeployment, var5);
         }

         return appMB;
      }
   }

   private static ApplicationMBean createApplicationMBean(AppDeploymentMBean ad, DomainMBean root, String path) throws InvalidAttributeValueException, IOException, ApplicationException {
      File f = BootStrap.apply(path);
      if (!f.isAbsolute()) {
         f = f.getCanonicalFile();
      }

      MBeanFactory mbFac = MBeanFactory.getMBeanFactory();
      synchronized(mbFac) {
         ApplicationMBean appMB = mbFac.initializeMBeans(root, f, ad.getName(), (String)null, (String)null, ad);
         return appMB;
      }
   }

   private static void setApplicationTargets(AppDeploymentMBean appDeploymentMBean, ApplicationMBean appMB) throws DistributedManagementException, InvalidAttributeValueException {
      ComponentMBean[] comps = appMB.getComponents();

      for(int i = 0; i < comps.length; ++i) {
         ComponentMBean comp = comps[i];
         comp.setTargets(getTargetsForComponent(appDeploymentMBean, comp));
      }

   }

   public static TargetMBean[] getTargetsForComponent(AppDeploymentMBean appDeploymentMBean, ComponentMBean comp) {
      SubDeploymentMBean mtbean = findSubDeployment(appDeploymentMBean, comp);
      return mtbean != null && mtbean.getTargets() != null ? mtbean.getTargets() : appDeploymentMBean.getTargets();
   }

   public static void addTargetFromComponent(AppDeploymentMBean du, ComponentMBean comp, TargetMBean target) {
      SubDeploymentMBean mtbean = findOrCreateSubDeployment(du, comp);

      try {
         mtbean.addTarget(target);
      } catch (InvalidAttributeValueException var5) {
         throw new AssertionError(var5);
      } catch (DistributedManagementException var6) {
         throw new AssertionError(var6);
      }
   }

   public static void removeTargetFromComponent(AppDeploymentMBean du, ComponentMBean comp, TargetMBean target) {
      SubDeploymentMBean mtbean = findOrCreateSubDeployment(du, comp);

      try {
         mtbean.removeTarget(target);
      } catch (InvalidAttributeValueException var5) {
         throw new AssertionError(var5);
      } catch (DistributedManagementException var6) {
         throw new AssertionError(var6);
      }
   }

   private static SubDeploymentMBean findSubDeployment(AppDeploymentMBean du, ComponentMBean comp) {
      SubDeploymentMBean mtbean = du.lookupSubDeployment(comp.getName());
      if (mtbean == null) {
         mtbean = du.lookupSubDeployment(comp.getURI());
      }

      return mtbean;
   }

   private static SubDeploymentMBean findOrCreateSubDeployment(AppDeploymentMBean du, ComponentMBean comp) {
      SubDeploymentMBean result = findSubDeployment(du, comp);
      if (result == null) {
         result = du.createSubDeployment(comp.getName());
      }

      return result;
   }

   private static void handleException(AppDeploymentMBean du, Throwable e) throws DeploymentException {
      DeploymentException toBePropogated = null;
      String errMsg = DeploymentManagerLogger.logConfigureAppMBeanFailedLoggable(du.getName()).getMessage();
      if (e instanceof DeploymentException) {
         toBePropogated = (DeploymentException)e;
      } else if (e instanceof NestedException) {
         Throwable nested = ((NestedException)e).getNestedException();
         if (nested == null) {
            toBePropogated = new DeploymentException(errMsg, e);
         } else if (nested instanceof DeploymentException) {
            toBePropogated = (DeploymentException)nested;
         } else {
            toBePropogated = new DeploymentException(errMsg, nested);
         }
      } else {
         toBePropogated = new DeploymentException(errMsg, e);
      }

      DeploymentManagerLogger.logConversionToAppMBeanFailed(du.getName(), toBePropogated);
      if (isDebugEnabled()) {
         toBePropogated.printStackTrace();
      }

      throw toBePropogated;
   }

   public static void setTargetsForComponent(AppDeploymentMBean delegate, ComponentMBean componentMBean, TargetMBean[] targets) {
   }

   private static ApplicationMBean createTEMPCompatMBean(AppDeploymentMBean du, DomainMBean domain, String path) throws InvalidAttributeValueException, ManagementException, FileNotFoundException {
      File mod = BootStrap.apply(path);
      if (!mod.exists()) {
         throw new FileNotFoundException(mod + " not found.");
      } else {
         ApplicationMBean appmb = domain.lookupApplication(du.getName());
         if (appmb == null) {
            appmb = domain.createApplication(du.getName());
            appmb.setAppDeployment(du);
            appmb.setPath(mod.getParent());
            appmb.setDelegationEnabled(true);
         }

         ComponentMBean[] comps = appmb.getComponents();
         if (comps == null || comps.length == 0) {
            ComponentMBean cmb = createComponent(appmb, mod.getName());
            cmb.setTargets(du.getTargets());
            cmb.setURI(mod.getName());
         }

         return appmb;
      }
   }

   private static ComponentMBean createComponent(ApplicationMBean appmb, String filename) throws ManagementException {
      Object cm;
      if (filename.toLowerCase(Locale.US).endsWith("-jms.xml")) {
         cm = appmb.createDummyComponent(appmb.getName());
      } else if (filename.toLowerCase(Locale.US).endsWith("-jdbc.xml")) {
         cm = appmb.createJDBCPoolComponent(appmb.getName());
      } else {
         if (!filename.toLowerCase(Locale.US).endsWith("-interception.xml")) {
            String msg = DeploymentManagerLogger.logUnknownDeployable(filename);
            throw new DeploymentException(msg);
         }

         cm = appmb.createDummyComponent(appmb.getName());
      }

      return (ComponentMBean)cm;
   }

   public static void debug(String s) {
      Debug.deploymentDebug(s);
   }

   public static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public static void reconcile81MBeans(DeploymentData deploymentData, AppDeploymentMBean appDeploymentMBean) throws DeploymentException {
      if (appDeploymentMBean.getAppMBean() == null) {
         setupNew81MBean(appDeploymentMBean);
      } else {
         if (isDebugEnabled()) {
            debug("Reconcile appmbean for" + appDeploymentMBean.getObjectName());
         }

         String[] updateUris = deploymentData.getFiles();

         try {
            DeploymentManager.getDeploymentManager().getMBeanFactory().reconcileMBeans(appDeploymentMBean, AppDeployment.getFile(appDeploymentMBean, ManagementService.getRuntimeAccess(kernelId).getServer()));
         } catch (FileNotFoundException var4) {
            throw new DeploymentException(var4);
         } catch (ApplicationException var5) {
            throw new DeploymentException(var5);
         }
      }

   }

   public static void setupNew81MBean(AppDeploymentMBean appDeploymentMBean) throws DeploymentException {
      DomainMBean root = (DomainMBean)appDeploymentMBean.getDescriptor().getRootBean();
      if (!appDeploymentMBean.getSourcePath().toLowerCase(Locale.US).endsWith("-jms.xml")) {
         ApplicationMBean appmbean = createApplicationForAppDeployment(root, appDeploymentMBean, AppDeployment.getFile(appDeploymentMBean, ManagementService.getRuntimeAccess(kernelId).getServer()).getPath());
         if (appmbean != null) {
            appmbean.setAppDeployment(appDeploymentMBean);
         }

      }
   }

   public static void remove81MBean(AppDeploymentMBean appDeploymentMBean) {
      DomainMBean root = (DomainMBean)appDeploymentMBean.getDescriptor().getRootBean();
      ApplicationMBean appmbean = root.lookupApplication(appDeploymentMBean.getName());
      if (appmbean != null) {
         root.destroyApplication(appmbean);
      }

   }

   public static void addStagedTarget(AppDeploymentMBean appdepmbean, String serverName) {
      ApplicationMBean mbean = appdepmbean.getAppMBean();
      if (mbean != null) {
         mbean.addStagedTarget(serverName);
      }

   }

   private static class SubDTargetDiffHandler implements ArrayUtils.DiffHandler {
      private final SubDeploymentMBean subDeployment;

      public SubDTargetDiffHandler(SubDeploymentMBean subDeployment) {
         this.subDeployment = subDeployment;
      }

      public void addObject(Object added) {
         try {
            this.subDeployment.addTarget((TargetMBean)added);
         } catch (InvalidAttributeValueException var3) {
            throw new AssertionError(var3);
         } catch (DistributedManagementException var4) {
            throw new AssertionError(var4);
         }
      }

      public void removeObject(Object removed) {
         try {
            this.subDeployment.removeTarget((TargetMBean)removed);
         } catch (InvalidAttributeValueException var3) {
            throw new AssertionError(var3);
         } catch (DistributedManagementException var4) {
            throw new AssertionError(var4);
         }
      }
   }
}
