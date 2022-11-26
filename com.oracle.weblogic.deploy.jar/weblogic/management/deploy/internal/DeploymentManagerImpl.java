package weblogic.management.deploy.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.internal.WebLogicDeployableObjectFactoryImpl;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.DeploymentConfigurationImpl;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.deploy.internal.adminserver.EditAccessHelper;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.AccessCallback;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AppDeploymentRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.DeploymentProgressObjectMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.LibDeploymentRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;

public final class DeploymentManagerImpl extends DomainRuntimeMBeanDelegate implements DeploymentManagerMBean, PropertyChangeListener, AccessCallback {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int progressObjectsMaxCount = 20;
   private ArrayList progressObjects = new ArrayList();
   private Map appDeploymentRuntimes = new HashMap();
   private Map libDeploymentRuntimes = new HashMap();
   private MBeanNotificationInfo[] mbeanNotificationInfo = null;
   private NotificationGenerator notificationGenerator = null;
   private long notificationSequence = 0L;
   private long timerDelay = 60000L;
   private long timerPeriod = 60000L;
   private long removalTimeout = 3600000L;
   private static HashSet deployRecognizedOptions = new HashSet();
   private static HashSet distributeRecognizedOptions = new HashSet();
   private static HashSet extendLoaderRecognizedOptions = new HashSet();
   private static HashSet undeployRecognizedOptions = new HashSet();
   private static HashSet redeployRecognizedOptions = new HashSet();
   private static HashSet updateRecognizedOptions = new HashSet();
   private final int[] deployUndeployLock = new int[0];
   private final String partitionName;
   private boolean partitionDeployer = false;
   private final Timer timer;
   private static DeploymentManagerImpl deploymentManagerImpl = null;
   private static Map deplMgrs = new HashMap();

   public static synchronized void removeDeploymentManager(String partitionName) {
      DeploymentManagerImpl depl = (DeploymentManagerImpl)deplMgrs.get(partitionName);
      if (depl != null) {
         depl.timer.cancel();
         Iterator var2 = depl.appDeploymentRuntimes.keySet().iterator();

         while(var2.hasNext()) {
            String appName = (String)var2.next();
            AppDeploymentRuntimeImpl appDeplRuntime = (AppDeploymentRuntimeImpl)depl.appDeploymentRuntimes.get(appName);
            if (appDeplRuntime != null) {
               appDeplRuntime.removeNotificationGenerator();
            }
         }

         deplMgrs.remove(partitionName);
      }

   }

   DeploymentManagerImpl(String name) throws ManagementException {
      super(name);
      ManagementService.getDomainAccess(kernelId).addAccessCallback(this);
      this.timer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new TimerListener() {
         public void timerExpired(Timer timer) {
            DeploymentManagerImpl.this.removeExpiredDeploymentProgressObjects();
         }
      }, this.timerDelay, this.timerPeriod);
      this.partitionName = "DOMAIN";
      deploymentManagerImpl = this;
   }

   DeploymentManagerImpl(RuntimeMBean parent, String partitionName) throws ManagementException {
      super(parent.getName(), parent);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Instantiating DeploymentManagerImpl for partition: " + partitionName);
      }

      this.timer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new TimerListener() {
         public void timerExpired(Timer timer) {
            DeploymentManagerImpl.this.removeExpiredDeploymentProgressObjects();
         }
      }, this.timerDelay, this.timerPeriod);
      this.partitionName = DeploymentServerService.normalizePartitionName(partitionName);
      this.partitionDeployer = true;
      deplMgrs.put(partitionName, this);
      this.debugDeploymentManagerList();
   }

   public DeploymentProgressObjectMBean deploy(String name, String applicationPath, String plan) throws RuntimeException {
      if (applicationPath == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("applicationPath"));
      } else {
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(DeployHelper.getAllServerTargets(), plan, (Properties)null);
         return this.doOperation(DeploymentManagerImpl.OperationType.DEPLOY, true, name, applicationPath, deploymentData, (List)null);
      }
   }

   public DeploymentProgressObjectMBean deploy(String name, String applicationPath, String[] targets, String plan, Properties deploymentOptions) throws RuntimeException {
      if (applicationPath == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("applicationPath"));
      } else {
         DeployHelper.validateOptions(deploymentOptions, deployRecognizedOptions, "deploy");
         List msgs = new ArrayList();
         if (targets == null) {
            targets = DeployHelper.getAllServerTargets();
         }

         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, plan, deploymentOptions);
         boolean createPlan = Boolean.parseBoolean(deploymentOptions.getProperty("createPlan"));
         if (plan == null && createPlan) {
            String planPath = createPlan(applicationPath);
            msgs.add("Plan is created at " + planPath);
            deploymentData.setDeploymentPlan(planPath);
         }

         return this.doOperation(DeploymentManagerImpl.OperationType.DEPLOY, false, name, applicationPath, deploymentData, msgs);
      }
   }

   public DeploymentProgressObjectMBean distribute(String name, String applicationPath, String plan) throws RuntimeException {
      if (applicationPath == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("applicationPath"));
      } else {
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(DeployHelper.getAllServerTargets(), plan, (Properties)null);
         return this.doOperation(DeploymentManagerImpl.OperationType.DISTRIBUTE, true, name, applicationPath, deploymentData, (List)null);
      }
   }

   public DeploymentProgressObjectMBean distribute(String name, String applicationPath, String[] targets, String plan, Properties deploymentOptions) throws RuntimeException {
      if (applicationPath == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("applicationPath"));
      } else {
         DeployHelper.validateOptions(deploymentOptions, distributeRecognizedOptions, "distribute");
         List msgs = new ArrayList();
         if ((targets == null || targets.length == 0) && this.lookupAppDeploymentRuntime(name) == null) {
            targets = DeployHelper.getAllServerTargets();
         }

         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, plan, deploymentOptions);
         boolean createPlan = Boolean.parseBoolean(deploymentOptions.getProperty("createPlan"));
         String taskId;
         if (plan == null && createPlan) {
            taskId = createPlan(applicationPath);
            msgs.add("Plan is created at " + taskId);
            deploymentData.setDeploymentPlan(taskId);
         }

         taskId = deploymentOptions.getProperty("id");
         return this.doOperation(DeploymentManagerImpl.OperationType.DISTRIBUTE, false, name, applicationPath, deploymentData, msgs);
      }
   }

   public DeploymentProgressObjectMBean appendToExtensionLoader(String codeSourcePath) throws RuntimeException {
      if (codeSourcePath == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("codeSourcePath"));
      } else {
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(DeployHelper.getAllServerTargets(), (String)null, (Properties)null);
         return this.doOperation(DeploymentManagerImpl.OperationType.EXTEND_LOADER, true, (String)null, codeSourcePath, deploymentData, (List)null);
      }
   }

   public DeploymentProgressObjectMBean appendToExtensionLoader(String codeSourcePath, String[] targets, Properties deploymentOptions) throws RuntimeException {
      if (codeSourcePath == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("codeSourcePath"));
      } else {
         DeployHelper.validateOptions(deploymentOptions, extendLoaderRecognizedOptions, "appendToExtensionLoader");
         List msgs = new ArrayList();
         if (targets == null || targets.length == 0) {
            targets = DeployHelper.getAllServerTargets();
         }

         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData(targets, (String)null, deploymentOptions);
         String taskId = deploymentOptions.getProperty("id");
         return this.doOperation(DeploymentManagerImpl.OperationType.EXTEND_LOADER, false, (String)null, codeSourcePath, deploymentData, msgs);
      }
   }

   public DeploymentProgressObjectMBean undeploy(String name, String template) throws RuntimeException {
      DeployerTextFormatter cat;
      if (name == null) {
         cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("name"));
      } else if (template == null) {
         cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("template"));
      } else {
         DeploymentData deploymentData = new DeploymentData();
         deploymentData.getDeploymentOptions().setResourceGroupTemplate(template);
         return this.doOperation(DeploymentManagerImpl.OperationType.UNDEPLOY, true, name, (String)null, deploymentData, (List)null);
      }
   }

   public DeploymentProgressObjectMBean undeploy(String name, Properties deploymentOptions) throws RuntimeException {
      if (name == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("name"));
      } else {
         DeployHelper.validateOptions(deploymentOptions, undeployRecognizedOptions, "undeploy");
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData((String[])null, (String)null, deploymentOptions);
         if (deploymentData.getDeploymentOptions().getResourceGroupTemplate() == null) {
            DeployerTextFormatter cat = new DeployerTextFormatter();
            throw new IllegalArgumentException(cat.paramRequired("resourceGroupTemplate"));
         } else {
            return this.doOperation(DeploymentManagerImpl.OperationType.UNDEPLOY, false, name, (String)null, deploymentData, (List)null);
         }
      }
   }

   public DeploymentProgressObjectMBean redeploy(String name, String plan, Properties deploymentOptions) throws RuntimeException {
      return this.redeploy(name, (String)null, plan, deploymentOptions);
   }

   public DeploymentProgressObjectMBean redeploy(String name, String applicationPath, String plan, Properties deploymentOptions) throws RuntimeException {
      if (name == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("name"));
      } else {
         DeployHelper.validateOptions(deploymentOptions, redeployRecognizedOptions, "redeploy");
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData((String[])null, plan, deploymentOptions);
         if (deploymentData.getDeploymentOptions().getResourceGroupTemplate() == null) {
            DeployerTextFormatter cat = new DeployerTextFormatter();
            throw new IllegalArgumentException(cat.paramRequired("resourceGroupTemplate"));
         } else {
            return this.doOperation(DeploymentManagerImpl.OperationType.REDEPLOY, false, name, applicationPath, deploymentData, (List)null);
         }
      }
   }

   public DeploymentProgressObjectMBean update(String name, String plan, Properties deploymentOptions) throws RuntimeException {
      if (name == null) {
         DeployerTextFormatter cat = new DeployerTextFormatter();
         throw new IllegalArgumentException(cat.paramRequired("name"));
      } else {
         DeployHelper.validateOptions(deploymentOptions, updateRecognizedOptions, "update");
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData((String[])null, plan, deploymentOptions);
         if (deploymentData.getDeploymentOptions().getResourceGroupTemplate() == null) {
            DeployerTextFormatter cat = new DeployerTextFormatter();
            throw new IllegalArgumentException(cat.paramRequired("resourceGroupTemplate"));
         } else {
            return this.doOperation(DeploymentManagerImpl.OperationType.UPDATE, false, name, (String)null, deploymentData, (List)null);
         }
      }
   }

   private DomainMBean getRuntimeConfigDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   private DeploymentProgressObjectMBean doOperation(OperationType operation, boolean sync, String name, String applicationPath, DeploymentData deploymentData, List messages) throws RuntimeException {
      synchronized(this.deployUndeployLock) {
         DeploymentProgressObjectMBean progressObjectMBean = null;
         DeploymentTaskRuntimeMBean task = null;

         try {
            DeployerRuntimeMBean deployer = null;
            String pName = deploymentData.getDeploymentOptions().getPartition();
            if (this.partitionName.equals("DOMAIN")) {
               deployer = ManagementService.getDomainAccess(kernelId).getDeployerRuntime();
            } else {
               if (pName == null) {
                  pName = this.partitionName;
               }

               if (!this.partitionName.equals(pName)) {
                  throw new IllegalArgumentException("incorrect partition");
               }

               DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
               DomainPartitionRuntimeMBean domainPartitionRuntime = domainRuntime.lookupDomainPartitionRuntime(pName);
               deployer = domainPartitionRuntime.getDeployerRuntime();
               deploymentData.getDeploymentOptions().setPartition(this.partitionName);
            }

            switch (operation) {
               case DEPLOY:
                  task = deployer.deploy(applicationPath, name, deploymentData.getDeploymentOptions().getStageMode(), deploymentData, (String)null, false);
                  break;
               case DISTRIBUTE:
                  task = deployer.distribute(applicationPath, name, deploymentData, (String)null, false);
                  break;
               case EXTEND_LOADER:
                  task = deployer.appendToExtensionLoader(applicationPath, deploymentData, (String)null, false);
                  break;
               case UNDEPLOY:
                  task = deployer.undeploy(name, deploymentData, (String)null, false);
                  break;
               case REDEPLOY:
                  if (applicationPath == null) {
                     task = deployer.redeploy(name, deploymentData, (String)null, false);
                  } else {
                     task = deployer.redeploy(applicationPath, name, deploymentData, (String)null, false);
                  }
                  break;
               case UPDATE:
                  task = deployer.update(name, deploymentData, (String)null, false);
            }

            if (!(task.getDeploymentMBean() instanceof AppDeploymentMBean)) {
               throw new IllegalArgumentException(name + "is not an application");
            }

            String versionId = deploymentData.getDeploymentOptions().getVersionIdentifier();
            String appId = ApplicationVersionUtils.getApplicationId(task.getApplicationName(), versionId);
            progressObjectMBean = this.allocateDeploymentProgressObject(appId, task, (AppDeploymentMBean)task.getDeploymentMBean());
            if (messages != null && messages.size() > 0) {
               progressObjectMBean.addMessages(messages);
            }

            task.start();
            if (sync) {
               task.waitForTaskCompletion(-1L);
            }
         } catch (Throwable var15) {
            RuntimeException rtEx = ExceptionTranslator.translateException(var15);
            if (progressObjectMBean == null || task == null) {
               throw rtEx;
            }

            ((DeploymentProgressObjectImpl)progressObjectMBean).addException(rtEx);
         }

         return progressObjectMBean;
      }
   }

   public static String createPlan(String applicationPath, String planPath) throws RuntimeException {
      File appFile = new File(applicationPath);
      FileOutputStream fos = null;
      WebLogicDeploymentConfiguration wdc = null;

      String var17;
      try {
         WebLogicDeployableObject dObj = (new WebLogicDeployableObjectFactoryImpl()).createDeployableObject(appFile);
         wdc = new DeploymentConfigurationImpl(dObj);
         fos = new FileOutputStream(planPath);
         wdc.save(fos);
         fos.flush();
         var17 = planPath;
      } catch (Throwable var15) {
         RuntimeException rtEx = ExceptionTranslator.translateException(var15);
         throw rtEx;
      } finally {
         try {
            fos.close();
            wdc.close();
         } catch (Throwable var14) {
         }

      }

      return var17;
   }

   public static String createPlan(String applicationPath) throws RuntimeException {
      File appFile = new File(applicationPath);
      String planPathDir = DomainDir.getDeploymentsDir() + File.separator + appFile.getName() + File.separator + "plan";
      File planPathFile = new File(planPathDir);
      if (!planPathFile.exists() && !planPathFile.mkdirs()) {
         return null;
      } else {
         String planPath = planPathDir + File.separator + "plan.xml";
         return createPlan(applicationPath, planPath);
      }
   }

   private synchronized void removeExpiredDeploymentProgressObjects() {
      ArrayList newProgressObjects = new ArrayList();

      for(int n = 0; n < this.progressObjects.size(); ++n) {
         DeploymentProgressObjectImpl mbean = (DeploymentProgressObjectImpl)this.progressObjects.get(n);
         long endTime = mbean.getEndTime();
         if (endTime > 0L && System.currentTimeMillis() - endTime > this.removalTimeout) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("removing expired progress object:  " + mbean.getApplicationName());
            }

            mbean.clear();
         } else {
            newProgressObjects.add(mbean);
         }
      }

      this.progressObjects.clear();
      this.progressObjects = newProgressObjects;
   }

   public synchronized AppDeploymentRuntimeMBean[] getAppDeploymentRuntimes() {
      List appDeplList = new ArrayList();
      Iterator var2 = deploymentManagerImpl.appDeploymentRuntimes.values().iterator();

      while(true) {
         AppDeploymentRuntimeMBean appDepl;
         String pName;
         do {
            if (!var2.hasNext()) {
               return (AppDeploymentRuntimeMBean[])appDeplList.toArray(new AppDeploymentRuntimeMBean[0]);
            }

            appDepl = (AppDeploymentRuntimeMBean)var2.next();
            pName = appDepl.getPartitionName();
         } while((pName != null || !this.partitionName.equals("DOMAIN")) && (pName == null || !this.partitionName.equals(pName)));

         appDeplList.add(appDepl);
      }
   }

   public synchronized AppDeploymentRuntimeMBean lookupAppDeploymentRuntime(String appName) {
      if (this.partitionDeployer) {
         String pName = ApplicationVersionUtils.getPartitionName(appName);
         if (pName.equals("DOMAIN")) {
            pName = ApplicationVersionUtils.getApplicationIdWithPartition(appName, this.partitionName);
            return deploymentManagerImpl.lookupAppDeploymentRuntime(pName);
         } else if (!this.partitionName.equals(pName)) {
            throw new IllegalArgumentException("Incorrect partition name specified:  " + pName);
         } else {
            return deploymentManagerImpl.lookupAppDeploymentRuntime(appName);
         }
      } else {
         return (AppDeploymentRuntimeMBean)this.appDeploymentRuntimes.get(appName);
      }
   }

   public synchronized AppDeploymentRuntimeMBean lookupAppDeploymentRuntime(String appName, Properties deploymentOptions) {
      if (deploymentOptions == null) {
         return this.lookupAppDeploymentRuntime(appName);
      } else {
         String optPartitionName = deploymentOptions.getProperty("partition");
         if (optPartitionName != null && optPartitionName.length() != 0) {
            String pName = ApplicationVersionUtils.getApplicationIdWithPartition(appName, optPartitionName);
            return this.lookupAppDeploymentRuntime(pName);
         } else {
            return this.lookupAppDeploymentRuntime(appName);
         }
      }
   }

   public synchronized LibDeploymentRuntimeMBean[] getLibDeploymentRuntimes() {
      List libDeplList = new ArrayList();
      Iterator var2 = deploymentManagerImpl.libDeploymentRuntimes.values().iterator();

      while(true) {
         LibDeploymentRuntimeMBean libDepl;
         String pName;
         do {
            if (!var2.hasNext()) {
               return (LibDeploymentRuntimeMBean[])libDeplList.toArray(new LibDeploymentRuntimeMBean[0]);
            }

            libDepl = (LibDeploymentRuntimeMBean)var2.next();
            pName = libDepl.getPartitionName();
         } while((pName != null || !this.partitionName.equals("DOMAIN")) && (pName == null || !this.partitionName.equals(pName)));

         libDeplList.add(libDepl);
      }
   }

   public synchronized LibDeploymentRuntimeMBean lookupLibDeploymentRuntime(String libName) {
      if (this.partitionDeployer) {
         String pName = ApplicationVersionUtils.getPartitionName(libName);
         if (pName.equals("DOMAIN")) {
            pName = ApplicationVersionUtils.getApplicationIdWithPartition(libName, this.partitionName);
            return deploymentManagerImpl.lookupLibDeploymentRuntime(pName);
         } else if (!this.partitionName.equals(pName)) {
            throw new IllegalArgumentException("Incorrect partition name specified:  " + pName);
         } else {
            return deploymentManagerImpl.lookupLibDeploymentRuntime(libName);
         }
      } else {
         return (LibDeploymentRuntimeMBean)this.libDeploymentRuntimes.get(libName);
      }
   }

   public synchronized DeploymentProgressObjectMBean[] getDeploymentProgressObjects() {
      return (DeploymentProgressObjectMBean[])this.progressObjects.toArray(new DeploymentProgressObjectMBean[0]);
   }

   public synchronized void setMaximumDeploymentProgressObjectsCount(int maxCount) {
      this.progressObjectsMaxCount = maxCount;
   }

   public int getMaximumDeploymentProgressObjectsCount() {
      return this.progressObjectsMaxCount;
   }

   public synchronized void purgeCompletedDeploymentProgressObjects() {
      if (this.progressObjects.size() != 0) {
         ArrayList newProgressObjects = new ArrayList();

         for(int n = 0; n < this.progressObjects.size(); ++n) {
            DeploymentProgressObjectImpl mbean = (DeploymentProgressObjectImpl)this.progressObjects.get(n);
            String state = mbean.getState();
            if (!"STATE_COMPLETED".equals(state) && !"STATE_FAILED".equals(state)) {
               newProgressObjects.add(mbean);
            } else {
               mbean.clear();
            }
         }

         this.progressObjects.clear();
         this.progressObjects = newProgressObjects;
      }
   }

   synchronized DeploymentProgressObjectMBean allocateDeploymentProgressObject(String appName, DeploymentTaskRuntimeMBean task, AppDeploymentMBean deployable) throws ManagementException {
      this.removeDeploymentProgressObject(appName);
      if (this.progressObjects.size() < this.progressObjectsMaxCount) {
         DeploymentProgressObjectImpl progressObject = new DeploymentProgressObjectImpl(appName, task, deployable, this);
         this.progressObjects.add(progressObject);
         return progressObject;
      } else {
         throw new ManagementException("Max count reached");
      }
   }

   public synchronized void removeDeploymentProgressObject(String appName) {
      if (this.progressObjects.size() != 0 && appName != null) {
         ArrayList newProgressObjects = new ArrayList();

         for(int n = 0; n < this.progressObjects.size(); ++n) {
            DeploymentProgressObjectImpl mbean = (DeploymentProgressObjectImpl)this.progressObjects.get(n);
            if (appName.equals(mbean.getApplicationName())) {
               try {
                  mbean.unregister();
               } catch (Exception var6) {
               }
            } else {
               newProgressObjects.add(mbean);
            }
         }

         this.progressObjects.clear();
         this.progressObjects = newProgressObjects;
      }
   }

   public String confirmApplicationName(Boolean isRedeployment, String appSource, String altAppDescriptor, String tentativeName, String tentativeApplicationId) throws RuntimeException {
      return this.confirmApplicationName(isRedeployment, appSource, altAppDescriptor, tentativeName, tentativeApplicationId, new Properties());
   }

   public String confirmApplicationName(Boolean isRedeployment, String appSource, String altAppDescriptor, String tentativeName, String tentativeApplicationId, Properties deploymentOptions) throws RuntimeException {
      try {
         if (tentativeApplicationId == null) {
            tentativeApplicationId = "";
         }

         DomainRuntimeServiceMBean domainRuntimeService = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
         DomainMBean domain = domainRuntimeService.getDomainConfiguration();
         AuthenticatedSubject authenticatedSubject = SecurityServiceManager.getCurrentSubject(kernelId);
         DomainMBean editDomain = EditAccessHelper.getInstance(kernelId, deploymentOptions.getProperty("editSession")).getEditDomainBean(authenticatedSubject);
         DeploymentData deploymentData = DeployHelper.propertiesToDeploymentData((String[])null, (String)null, deploymentOptions);
         return ApplicationUtils.confirmApplicationName(isRedeployment, new File(appSource), altAppDescriptor == null ? null : new File(altAppDescriptor), tentativeName, tentativeApplicationId, domain, editDomain, deploymentData.getDeploymentOptions());
      } catch (Exception var12) {
         throw new RuntimeException(var12.getMessage());
      }
   }

   private synchronized void initDeploymentRuntimes() {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In initDeploymentRuntimes");
      }

      this.debugDeploymentManagerList();
      this.appDeploymentRuntimes.clear();
      DomainMBean domain = this.getRuntimeConfigDomain();
      AppDeploymentMBean[] appDeployments = domain.getAppDeployments();
      if (appDeployments != null) {
         for(int n = 0; n < appDeployments.length; ++n) {
            try {
               String partitionPart = ApplicationVersionUtils.getPartitionName(appDeployments[n].getName());
               DeploymentManagerImpl deplMgr = (DeploymentManagerImpl)deplMgrs.get(partitionPart);
               if (deplMgr == null) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("DeploymentManager for partition name " + partitionPart + " is null");
                  }

                  deplMgr = this;
               }

               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("DeploymentManager for partition name " + partitionPart + "is: " + deplMgr);
               }

               AppDeploymentRuntimeImpl appDeploymentRuntime = new AppDeploymentRuntimeImpl(appDeployments[n], deplMgr);
               this.appDeploymentRuntimes.put(appDeployments[n].getName(), appDeploymentRuntime);
               this.sendNotification("weblogic.appdeployment.created", appDeploymentRuntime);
            } catch (Exception var9) {
            }
         }
      }

      this.libDeploymentRuntimes.clear();
      LibraryMBean[] libDeployments = domain.getLibraries();
      if (libDeployments != null) {
         for(int n = 0; n < libDeployments.length; ++n) {
            try {
               String partitionPart = ApplicationVersionUtils.getPartitionName(libDeployments[n].getName());
               DeploymentManagerImpl deplMgr = (DeploymentManagerImpl)deplMgrs.get(partitionPart);
               if (deplMgr == null) {
                  deplMgr = this;
               }

               LibDeploymentRuntimeImpl libDeploymentRuntime = new LibDeploymentRuntimeImpl(libDeployments[n], deplMgr);
               this.libDeploymentRuntimes.put(libDeployments[n].getName(), libDeploymentRuntime);
               this.sendNotification("weblogic.libdeployment.created", libDeploymentRuntime);
            } catch (Exception var8) {
            }
         }
      }

      domain.addPropertyChangeListener(this);
      AppRuntimeStateManager.getManager().addStateListener(this);
   }

   private synchronized void initAppDeploymentRuntimes(AppDeploymentMBean[] apps) {
      if (apps != null) {
         Map newAppDeploymentRuntimes = new HashMap();

         for(int n = 0; n < apps.length; ++n) {
            String partitionPart = ApplicationVersionUtils.getPartitionName(apps[n].getName());
            DeploymentManagerImpl deplMgr = (DeploymentManagerImpl)deplMgrs.get(partitionPart);
            if (deplMgr == null) {
               deplMgr = this;
            }

            AppDeploymentRuntimeImpl impl = (AppDeploymentRuntimeImpl)this.appDeploymentRuntimes.get(apps[n].getName());
            if (impl != null) {
               newAppDeploymentRuntimes.put(apps[n].getName(), impl);
               this.appDeploymentRuntimes.remove(apps[n].getName());
            } else {
               try {
                  AppDeploymentRuntimeImpl appDeploymentRuntime = new AppDeploymentRuntimeImpl(apps[n], deplMgr);
                  newAppDeploymentRuntimes.put(apps[n].getName(), appDeploymentRuntime);
                  this.sendNotification("weblogic.appdeployment.created", appDeploymentRuntime);
               } catch (Exception var9) {
               }
            }
         }

         if (this.appDeploymentRuntimes != null) {
            Collection coll = this.appDeploymentRuntimes.values();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
               AppDeploymentRuntimeImpl mbean = (AppDeploymentRuntimeImpl)it.next();

               try {
                  this.sendNotification("weblogic.appdeployment.deleted", mbean);
                  mbean.unregister();
               } catch (Exception var8) {
               }
            }
         }

         this.appDeploymentRuntimes.clear();
         this.appDeploymentRuntimes = newAppDeploymentRuntimes;
      }

   }

   private synchronized void initLibDeploymentRuntimes(LibraryMBean[] libs) {
      if (libs != null) {
         Map newLibDeploymentRuntimes = new HashMap();

         for(int n = 0; n < libs.length; ++n) {
            String partitionPart = ApplicationVersionUtils.getPartitionName(libs[n].getName());
            DeploymentManagerImpl deplMgr = (DeploymentManagerImpl)deplMgrs.get(partitionPart);
            if (deplMgr == null) {
               deplMgr = this;
            }

            LibDeploymentRuntimeImpl impl = (LibDeploymentRuntimeImpl)this.libDeploymentRuntimes.get(libs[n].getName());
            if (impl != null) {
               newLibDeploymentRuntimes.put(libs[n].getName(), impl);
               this.libDeploymentRuntimes.remove(libs[n].getName());
            } else {
               try {
                  LibDeploymentRuntimeImpl libDeploymentRuntime = new LibDeploymentRuntimeImpl(libs[n], deplMgr);
                  newLibDeploymentRuntimes.put(libs[n].getName(), libDeploymentRuntime);
                  this.sendNotification("weblogic.libdeployment.created", libDeploymentRuntime);
               } catch (Exception var9) {
               }
            }
         }

         if (this.libDeploymentRuntimes != null) {
            Collection coll = this.libDeploymentRuntimes.values();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
               LibDeploymentRuntimeImpl mbean = (LibDeploymentRuntimeImpl)it.next();

               try {
                  this.sendNotification("weblogic.libdeployment.deleted", mbean);
                  mbean.unregister();
               } catch (Exception var8) {
               }
            }
         }

         this.libDeploymentRuntimes.clear();
         this.libDeploymentRuntimes = newLibDeploymentRuntimes;
      }

   }

   public void propertyChange(PropertyChangeEvent event) {
      if ("Libraries".equals(event.getPropertyName())) {
         LibraryMBean[] libs = (LibraryMBean[])((LibraryMBean[])event.getNewValue());
         this.initLibDeploymentRuntimes(libs);
      } else if ("AppDeployments".equals(event.getPropertyName())) {
         AppDeploymentMBean[] apps = (AppDeploymentMBean[])((AppDeploymentMBean[])event.getNewValue());
         this.initAppDeploymentRuntimes(apps);
      } else if ("State".equals(event.getPropertyName())) {
         DeploymentState deploymentState = (DeploymentState)event.getNewValue();
         if (deploymentState != null) {
            AppDeploymentRuntimeImpl appDeploymentRuntime = (AppDeploymentRuntimeImpl)this.appDeploymentRuntimes.get(deploymentState.getId());
            if (appDeploymentRuntime != null) {
               appDeploymentRuntime.sendNotification(deploymentState);
            }

            if (this.notificationGenerator != null) {
               if (deploymentState.getCurrentState() != null) {
                  this.sendNotification(translateState(deploymentState.getCurrentState()), (AppDeploymentRuntimeImpl)this.appDeploymentRuntimes.get(deploymentState.getId()));
               } else {
                  TargetModuleState[] moduleStates = deploymentState.getTargetModules();
                  if (moduleStates != null) {
                     for(int n = 0; n < moduleStates.length; ++n) {
                        this.sendNotification(translateState(moduleStates[n].getCurrentState()), (AppDeploymentRuntimeImpl)this.appDeploymentRuntimes.get(deploymentState.getId()));
                     }
                  }
               }
            }
         }
      }

   }

   public void accessed(DomainMBean domain) {
      this.initDeploymentRuntimes();
   }

   public void shutdown() {
   }

   void setNotificationGenerator(NotificationGenerator ng) {
      this.notificationGenerator = ng;
   }

   private void sendNotification(String type, AppDeploymentRuntimeImpl appDeploymentRuntime) {
      if (this.notificationGenerator != null && appDeploymentRuntime != null) {
         try {
            ++this.notificationSequence;
            Notification notification = new Notification(type, this.notificationGenerator.getObjectName(), this.notificationSequence);
            String userData = "com.bea:Type=" + appDeploymentRuntime.getType() + ",Name=" + appDeploymentRuntime.getName();
            notification.setUserData(userData);
            this.notificationGenerator.sendNotification(notification);
         } catch (Throwable var5) {
         }
      }

   }

   private void sendNotification(String type, LibDeploymentRuntimeImpl libDeploymentRuntime) {
      if (this.notificationGenerator != null) {
         try {
            ++this.notificationSequence;
            Notification notification = new Notification(type, this.notificationGenerator.getObjectName(), this.notificationSequence);
            String userData = "com.bea:Type=LibDeploymentRuntime,Name=" + libDeploymentRuntime.getName();
            notification.setUserData(userData);
            this.notificationGenerator.sendNotification(notification);
         } catch (Throwable var5) {
         }
      }

   }

   static String translateState(String state) {
      if ("STATE_NEW".equals(state)) {
         return "weblogic.appdeployment.state.new";
      } else if ("STATE_PREPARED".equals(state)) {
         return "weblogic.appdeployment.state.prepared";
      } else if ("STATE_ADMIN".equals(state)) {
         return "weblogic.appdeployment.state.admin";
      } else if ("STATE_ACTIVE".equals(state)) {
         return "weblogic.appdeployment.state.active";
      } else if ("STATE_RETIRED".equals(state)) {
         return "weblogic.appdeployment.state.retired";
      } else if ("STATE_FAILED".equals(state)) {
         return "weblogic.appdeployment.state.failed";
      } else {
         return "STATE_UPDATE_PENDING".equals(state) ? "weblogic.appdeployment.state.update.pending" : "weblogic.appdeployment.state.unknown";
      }
   }

   private void debugDeploymentManagerList() {
      if (Debug.isDeploymentDebugEnabled()) {
         StringBuffer sb = new StringBuffer();
         Iterator var2 = deplMgrs.keySet().iterator();

         while(var2.hasNext()) {
            String par = (String)var2.next();
            DeploymentManagerImpl dm = (DeploymentManagerImpl)deplMgrs.get(par);
            sb.append("Partition " + par + " DeploymentManager " + dm);
            sb.append(", ");
         }

         Debug.deploymentDebug("DeploymentManager list deplMrgs: " + sb.toString());
      }

   }

   static {
      deployRecognizedOptions.add("adminMode");
      deployRecognizedOptions.add("altDD");
      deployRecognizedOptions.add("altWlsDD");
      deployRecognizedOptions.add("appVersion");
      deployRecognizedOptions.add("createPlan");
      deployRecognizedOptions.add("clusterDeploymentTimeout");
      deployRecognizedOptions.add("defaultSubmoduleTargets");
      deployRecognizedOptions.add("deploymentOrder");
      deployRecognizedOptions.add("deploymentPrincipalName");
      deployRecognizedOptions.add("forceUndeployTimeout");
      deployRecognizedOptions.add("planVersion");
      deployRecognizedOptions.add("libSpecVer");
      deployRecognizedOptions.add("libImplVer");
      deployRecognizedOptions.add("library");
      deployRecognizedOptions.add("noVersion");
      deployRecognizedOptions.add("retireTimeout");
      deployRecognizedOptions.add("retireGracefully");
      deployRecognizedOptions.add("securityModel");
      deployRecognizedOptions.add("securityValidationEnabled");
      deployRecognizedOptions.add("subModuleTargets");
      deployRecognizedOptions.add("stageMode");
      deployRecognizedOptions.add("timeout");
      deployRecognizedOptions.add("useNonExclusiveLock");
      deployRecognizedOptions.add("versionIdentifier");
      deployRecognizedOptions.add("cacheInAppDirectory");
      deployRecognizedOptions.add("specifiedTargetsOnly");
      deployRecognizedOptions.add("partition");
      deployRecognizedOptions.add("editSession");
      deployRecognizedOptions.add("resourceGroup");
      deployRecognizedOptions.add("resourceGroupTemplate");
      distributeRecognizedOptions.add("appVersion");
      distributeRecognizedOptions.add("createPlan");
      distributeRecognizedOptions.add("clusterDeploymentTimeout");
      distributeRecognizedOptions.add("defaultSubmoduleTargets");
      distributeRecognizedOptions.add("deploymentOrder");
      distributeRecognizedOptions.add("deploymentPrincipalName");
      distributeRecognizedOptions.add("forceUndeployTimeout");
      distributeRecognizedOptions.add("planVersion");
      distributeRecognizedOptions.add("libSpecVer");
      distributeRecognizedOptions.add("libImplVer");
      distributeRecognizedOptions.add("library");
      distributeRecognizedOptions.add("noVersion");
      distributeRecognizedOptions.add("securityModel");
      distributeRecognizedOptions.add("securityValidationEnabled");
      distributeRecognizedOptions.add("subModuleTargets");
      distributeRecognizedOptions.add("stageMode");
      distributeRecognizedOptions.add("id");
      distributeRecognizedOptions.add("timeout");
      distributeRecognizedOptions.add("useNonExclusiveLock");
      distributeRecognizedOptions.add("versionIdentifier");
      distributeRecognizedOptions.add("cacheInAppDirectory");
      distributeRecognizedOptions.add("specifiedTargetsOnly");
      distributeRecognizedOptions.add("partition");
      distributeRecognizedOptions.add("resourceGroup");
      distributeRecognizedOptions.add("resourceGroupTemplate");
      distributeRecognizedOptions.add("editSession");
      extendLoaderRecognizedOptions.add("clusterDeploymentTimeout");
      extendLoaderRecognizedOptions.add("id");
      extendLoaderRecognizedOptions.add("timeout");
      extendLoaderRecognizedOptions.add("specifiedTargetsOnly");
      undeployRecognizedOptions.add("clusterDeploymentTimeout");
      undeployRecognizedOptions.add("defaultSubmoduleTargets");
      undeployRecognizedOptions.add("forceUndeployTimeout");
      undeployRecognizedOptions.add("gracefulProductionToAdmin");
      undeployRecognizedOptions.add("gracefulIgnoreSessions");
      undeployRecognizedOptions.add("rmiGracePeriod");
      undeployRecognizedOptions.add("subModuleTargets");
      undeployRecognizedOptions.add("timeout");
      undeployRecognizedOptions.add("useNonExclusiveLock");
      undeployRecognizedOptions.add("editSession");
      undeployRecognizedOptions.add("resourceGroupTemplate");
      redeployRecognizedOptions.addAll(deployRecognizedOptions);
      updateRecognizedOptions.addAll(deployRecognizedOptions);
   }

   private static enum OperationType {
      DEPLOY,
      DISTRIBUTE,
      UNDEPLOY,
      REDEPLOY,
      UPDATE,
      EXTEND_LOADER;
   }
}
