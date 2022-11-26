package weblogic.deploy.internal.targetserver;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.DeployHelperService;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.internal.DeploymentStateChecker;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.common.Debug;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeployerRuntimeTextTextFormatter;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.NestedThrowable;

public class DeployHelper {
   private static final String CONFIG_DIR_PREFIX;
   private static final AuthenticatedSubject kernelId;

   public static String getTaskName(int task) {
      return getTaskName(task, (Locale)null);
   }

   public static String getTaskName(int task, Locale locale) {
      String retval = null;
      DeployerRuntimeTextTextFormatter dtf = null;
      if (locale == null) {
         dtf = DeployerRuntimeTextTextFormatter.getInstance();
      } else {
         dtf = DeployerRuntimeTextTextFormatter.getInstance(locale);
      }

      switch (task) {
         case 1:
            retval = dtf.messageActivate();
            break;
         case 2:
            retval = dtf.messagePrepare();
            break;
         case 3:
            retval = dtf.messageDeactivate();
            break;
         case 4:
            retval = dtf.messageRemove();
            break;
         case 5:
            retval = dtf.messageUnprepare();
            break;
         case 6:
            retval = dtf.messageDistribute();
            break;
         case 7:
            retval = dtf.messageStart();
            break;
         case 8:
            retval = dtf.messageStop();
            break;
         case 9:
            retval = dtf.messageRedeploy();
            break;
         case 10:
            retval = dtf.messageUpdate();
            break;
         case 11:
            retval = dtf.messageDeploy();
         case 12:
         default:
            break;
         case 13:
            retval = dtf.messageRetire();
      }

      return retval;
   }

   public static String getStagingMode(String server, AppDeploymentMBean d) {
      String mode = d.getStagingMode();
      if (mode == null || mode.length() == 0) {
         mode = getServerStagingMode(server);
      }

      return mode;
   }

   public static String getStagingMode(ServerMBean mbean, AppDeploymentMBean d) {
      String mode = d.getStagingMode();
      if (mode == null || mode.length() == 0) {
         mode = getServerStagingMode(mbean);
      }

      return mode;
   }

   public static String getServerStagingMode(String serverName) {
      RuntimeAccess configAccess = ManagementService.getRuntimeAccess(kernelId);
      return getServerStagingMode(configAccess.getDomain().lookupServer(serverName));
   }

   public static String getServerStagingMode(ServerMBean mbean) {
      String sMode = mbean.getStagingMode();
      return sMode != null && sMode.length() != 0 ? sMode : determineDefaultStagingMode(mbean.getName());
   }

   public static String determineDefaultStagingMode(String serverName) {
      RuntimeAccess configAccess = ManagementService.getRuntimeAccess(kernelId);
      if (null == configAccess) {
         return ServerMBean.DEFAULT_STAGE;
      } else {
         String sMode;
         if (DeployHelper.AdminServer.adminServerName != null && !DeployHelper.AdminServer.adminServerName.equals(serverName)) {
            sMode = "stage";
         } else {
            sMode = "nostage";
         }

         return sMode;
      }
   }

   public static DeploymentException convertThrowable(Throwable thrown) {
      if (Debug.isDeploymentDebugEnabled()) {
         thrown.printStackTrace();
      }

      String unexpectedMessage = SlaveDeployerLogger.logUnexpectedThrowableLoggable().getMessage();
      return handleException(thrown, unexpectedMessage);
   }

   private static ServerMBean getServerMBean() {
      return ManagementService.getRuntimeAccess(kernelId).getServer();
   }

   public static BasicDeployment createDeployment(BasicDeploymentMBean mbean) {
      return (BasicDeployment)(mbean instanceof AppDeploymentMBean ? new AppDeployment((AppDeploymentMBean)mbean, getServerMBean()) : new SystemResourceDeployment((SystemResourceMBean)mbean, getServerMBean()));
   }

   public static void logAndThrow(Loggable l) throws DeploymentException {
      l.log();
      throw new DeploymentException(l.getMessage());
   }

   public static void throwUnexpected(Throwable t) throws DeploymentException {
      throw convertThrowable(t);
   }

   public static void debug(String m) {
      Debug.deploymentDebug(m);
   }

   public static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public static boolean isModuleType(TargetInfoMBean mbean, ModuleType type) {
      return type != null && type.toString().equals(mbean.getModuleType());
   }

   public static String getSourcePath(BasicDeploymentMBean depMBean) {
      String sourcePath = null;
      if (depMBean instanceof AppDeploymentMBean) {
         sourcePath = ((AppDeploymentMBean)depMBean).getAbsoluteSourcePath();
      } else {
         if (!(depMBean instanceof SystemResourceMBean)) {
            throw new AssertionError("DeploymentMBean should be either AppDeployment/SystemResourceMBean");
         }

         sourcePath = CONFIG_DIR_PREFIX + ((SystemResourceMBean)depMBean).getDescriptorFileName();
      }

      return sourcePath;
   }

   public static int getState(Deployment depl) {
      if (depl instanceof AppContainerInvoker) {
         return getState(((AppContainerInvoker)depl).getDelegate());
      } else if (depl instanceof DeploymentStateChecker) {
         return ((DeploymentStateChecker)depl).getState();
      } else {
         throw new java.lang.AssertionError("Deployment must be an instanceof DeploymentStateChecker. Got - " + depl.getClass());
      }
   }

   public static boolean isPreparedState(Deployment dep) {
      return getState(dep) == 1;
   }

   public static boolean isAdminState(Deployment dep) {
      return getState(dep) == 2;
   }

   public static boolean isActiveState(Deployment dep) {
      return getState(dep) == 3;
   }

   public static String getStagingModeFromOptions(DeploymentData info) {
      String s = null;
      DeploymentOptions options = info.getDeploymentOptions();
      if (options != null) {
         s = options.getStageMode();
      }

      return s;
   }

   public static boolean isOkToTransition(AppDeploymentMBean appDeploymentMBean, ServerMBean server, String toState) {
      if (appDeploymentMBean.isInternalApp()) {
         return true;
      } else {
         Set targets = getAllTargets(appDeploymentMBean);
         String intendedState = null;
         Iterator iterator = targets.iterator();

         while(iterator.hasNext()) {
            TargetMBean s = (TargetMBean)iterator.next();
            if (TargetUtils.isDeployedLocally(new TargetMBean[]{s})) {
               String st = AppRuntimeStateManager.getManager().getIntendedState(appDeploymentMBean.getName(), s.getName());
               if (intendedState == null) {
                  intendedState = st;
               }

               if (less(intendedState, st)) {
                  intendedState = st;
               }
            }
         }

         if (intendedState == null) {
            try {
               AppRuntimeStateManager.getManager().setState(appDeploymentMBean.getName(), (String[])((String[])getAllTargetNames(targets).toArray(new String[0])), "STATE_ACTIVE");
            } catch (ManagementException var8) {
            }

            return true;
         } else if (!toState.equals(intendedState) && less(intendedState, toState)) {
            return false;
         } else {
            return true;
         }
      }
   }

   private static Set getAllTargetNames(Set targets) {
      HashSet names = new HashSet(targets.size());
      Iterator iterator = targets.iterator();

      while(iterator.hasNext()) {
         TargetMBean bean = (TargetMBean)iterator.next();
         names.add(bean.getName());
      }

      return names;
   }

   public static Set getAllTargetNames(BasicDeploymentMBean bean) {
      Set targets = getAllTargets(bean);
      Set names = new HashSet(targets.size());
      Iterator iterator = targets.iterator();

      while(iterator.hasNext()) {
         TargetMBean mBean = (TargetMBean)iterator.next();
         names.add(mBean.getName());
      }

      return names;
   }

   public static Set getAllTargets(BasicDeploymentMBean bean) {
      Set targets = new HashSet();
      addFromMbean(bean, targets);
      TargetInfoMBean[] subs = bean.getSubDeployments();

      for(int i = 0; i < subs.length; ++i) {
         addFromMbean(subs[i], targets);
         TargetInfoMBean[] subs1 = bean.getSubDeployments();

         for(int j = 0; j < subs1.length; ++j) {
            addFromMbean(subs1[j], targets);
         }
      }

      return targets;
   }

   private static void addFromMbean(TargetInfoMBean bean, Set s) {
      TargetMBean[] t = ApplicationUtils.getActualTargets(bean);

      for(int i = 0; i < t.length; ++i) {
         s.add(t[i]);
      }

   }

   public static boolean less(String s, String state) {
      if (s != null && state != null) {
         for(int i = 0; i < AppRuntimeStateRuntimeMBean.appStateDefs.length; ++i) {
            if (s.equals(AppRuntimeStateRuntimeMBean.appStateDefs[i])) {
               return true;
            }

            if (state.equals(AppRuntimeStateRuntimeMBean.appStateDefs[i])) {
               return false;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static DeploymentException handleException(Throwable expn, String msg) {
      if (expn instanceof DeploymentException) {
         return (DeploymentException)expn;
      } else if (expn instanceof ManagementException) {
         ManagementException managementExpn = (ManagementException)expn;
         Throwable cause = managementExpn.getNested();
         DeploymentException de = cause != null ? new DeploymentException(managementExpn.getMessage(), cause) : new DeploymentException(managementExpn.getMessage());
         de.setStackTrace(managementExpn.getStackTrace());
         return de;
      } else {
         assertForNullMessage(msg);
         return new DeploymentException(msg, getWrappedException(expn));
      }
   }

   private static void assertForNullMessage(String msg) {
      if (msg == null || msg.length() == 0) {
         throw new AssertionError(" ************* CONSTRUCTING EXCEPTION WITH NULL MESSAGE ********** ");
      }
   }

   private static Throwable getWrappedException(Throwable t) {
      Throwable cause;
      if (t instanceof NestedThrowable) {
         cause = ((NestedThrowable)t).getNested();
      } else {
         cause = t.getCause();
      }

      return cause == null ? t : getWrappedException(cause);
   }

   public static DeploymentContextImpl createDeploymentContext(BasicDeploymentMBean basicDeploymentMBean) throws DeploymentException {
      try {
         String prinName = null;
         if (basicDeploymentMBean != null) {
            prinName = basicDeploymentMBean.getDeploymentPrincipalName();
         }

         if (prinName != null) {
            PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
            AuthenticatedSubject user = pa.impersonateIdentity(prinName, (ContextHandler)null);
            return new DeploymentContextImpl(user);
         } else {
            String partitionName = null;
            if (basicDeploymentMBean != null) {
               partitionName = basicDeploymentMBean.getPartitionName();
            }

            if (partitionName != null) {
               PartitionRuntimeMBean.State partitionState = ApplicationUtils.getPartitionState(partitionName);
               if (partitionState == State.STARTING) {
                  AuthenticatedSubject currentUser = SecurityServiceManager.getCurrentSubject(kernelId);
                  return new DeploymentContextImpl(currentUser);
               }
            }

            return new DeploymentContextImpl(SubjectUtils.getAnonymousSubject());
         }
      } catch (Exception var5) {
         throw new DeploymentException(var5);
      }
   }

   private static boolean isInternalClass(Object ob) {
      String packagename = ob.getClass().getPackage().getName();
      String[] internalpackage = new String[]{"java.", "javax.", "weblogic."};

      for(int i = 0; i < internalpackage.length; ++i) {
         if (packagename.startsWith(internalpackage[i])) {
            return true;
         }
      }

      return false;
   }

   private static Throwable handleUnTranferableCause(Throwable expn, int level) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
      if (level == 10) {
         return expn;
      } else {
         if (expn.getCause() != null) {
            Throwable cause = expn.getCause();
            if (!isInternalClass(cause)) {
               Constructor c = expn.getClass().getConstructor(String.class);
               Throwable t = (Throwable)c.newInstance(expn.getMessage() + ":" + cause.getClass().getCanonicalName() + ":" + cause.getMessage());
               t.setStackTrace(cause.getStackTrace());
               return t;
            }

            Throwable returnex = handleUnTranferableCause(cause, level + 1);
            if (returnex != cause) {
               Constructor c = expn.getClass().getConstructor(String.class);
               Throwable t = (Throwable)c.newInstance(expn.getMessage());
               t.initCause(returnex);
               t.setStackTrace(expn.getStackTrace());
               return t;
            }
         }

         return expn;
      }
   }

   public static DeploymentException convertThrowableForTransfer(Throwable expn) {
      if (Debug.isDeploymentDebugEnabled()) {
         expn.printStackTrace();
      }

      if (expn instanceof DeploymentException) {
         try {
            if (expn.getCause() != null) {
               return (DeploymentException)handleUnTranferableCause(expn, 0);
            }

            return (DeploymentException)expn;
         } catch (Exception var2) {
         }
      }

      String unexpectedMessage = SlaveDeployerLogger.logUnexpectedThrowableLoggable().getMessage();
      return handleException(expn, unexpectedMessage);
   }

   public static boolean isMBeanInPartitionScope(ConfigurationMBean cloneMBean) {
      ActiveBeanUtil activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
      return activeBeanUtil.isInPartition(cloneMBean);
   }

   public static PartitionMBean findContainingPartitionMBean(ConfigurationMBean cloneMBean) {
      ActiveBeanUtil activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
      return activeBeanUtil.findContainingPartition(cloneMBean);
   }

   public static ConfigurationMBean getOriginalMBean(ConfigurationMBean cloneMBean) {
      ActiveBeanUtil activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
      return activeBeanUtil.toOriginalBean(cloneMBean);
   }

   public static PartitionMBean getParentPartitionMBean(ConfigurationMBean bean) {
      return (PartitionMBean)containedBy(bean, PartitionMBean.class);
   }

   public static ResourceGroupMBean getParentResourceGroupMBean(ConfigurationMBean bean) {
      return (ResourceGroupMBean)containedBy(bean, ResourceGroupMBean.class);
   }

   public static Object containedBy(ConfigurationMBean bean, Class type) {
      while(bean != null) {
         if (type.isAssignableFrom(bean.getClass())) {
            return bean;
         }

         Object o = bean.getParent();
         bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
      }

      return null;
   }

   public static String[] getAffectedPartitions(ResourceGroupTemplateMBean rgt) {
      Set partitionNames = new HashSet();
      return (String[])partitionNames.toArray(new String[partitionNames.size()]);
   }

   static {
      CONFIG_DIR_PREFIX = DomainDir.getRootDir() + File.separator + "config" + File.separator;
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   @Service
   private static class DeployHelperServiceImpl implements DeployHelperService {
      public DeploymentContext createDeploymentContext(BasicDeploymentMBean basicDeploymentMBean) throws DeploymentException {
         return DeployHelper.createDeploymentContext(basicDeploymentMBean);
      }
   }

   static class AdminServer {
      static String adminServerName;

      static {
         adminServerName = ManagementService.getRuntimeAccess(DeployHelper.kernelId).getAdminServerName();
      }
   }
}
