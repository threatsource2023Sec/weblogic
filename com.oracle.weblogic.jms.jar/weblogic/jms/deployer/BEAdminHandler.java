package weblogic.jms.deployer;

import java.io.IOException;
import java.security.AccessController;
import java.util.HashMap;
import javax.jms.JMSException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEUOWCallbackFactory;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.common.JMSDebug;
import weblogic.logging.jms.JMSMessageLogger;
import weblogic.logging.jms.JMSMessageLoggerFactory;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.JMSMessageLogFileMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSessionPoolMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericAdminHandler;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.management.utils.ManagedDeploymentNotPreparedException;
import weblogic.management.utils.ManagedServiceShutdownException;
import weblogic.management.utils.MigratableGenericAdminHandler;
import weblogic.messaging.kernel.internal.UOWSequenceImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.PersistentStoreManager;
import weblogic.store.admin.util.PartitionFileSystemUtils;
import weblogic.store.xa.PersistentStoreXA;

public final class BEAdminHandler implements GenericAdminHandler, MigratableGenericAdminHandler {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final HashMap backendBeanSignatures = new HashMap();
   private static final HashMap backendAdditionSignatures = new HashMap();
   private static String injectFaultOnStore;
   private static boolean faultInjectionEnabledForThisServer;
   private GenericBeanListener changeListener;
   private boolean migrationInProgress = false;
   private Object backEndNameLock = new Object();
   private String backEndName = null;

   private void setBackEndName(String name) {
      synchronized(this.backEndNameLock) {
         this.backEndName = name;
      }
   }

   private BackEnd getBackEndWithDeploymentException(GenericManagedDeployment deployment) throws DeploymentException {
      String name = deployment.getName();
      if (name == null) {
         throw new DeploymentException("name null in deployment " + deployment);
      } else {
         JMSService jmsService;
         try {
            jmsService = JMSService.getJMSServiceWithManagedServiceShutdownException();
         } catch (ManagedServiceShutdownException var6) {
            throw new DeploymentException(var6);
         }

         synchronized(this.backEndNameLock) {
            if (this.backEndName == null) {
               throw new DeploymentException("No BackEnd " + this.backEndName + " prepared with name " + name);
            }

            if (!name.equals(this.backEndName)) {
               throw new DeploymentException("Unknown name " + name + " for prepared BackEnd " + this.backEndName);
            }
         }

         return jmsService.getBEDeployer().findBackEnd(name);
      }
   }

   private BackEnd getBackEndWithUndeploymentException(GenericManagedDeployment deployment) throws UndeploymentException {
      String name = deployment.getName();
      if (name == null) {
         throw new UndeploymentException("name null in deployment " + deployment);
      } else {
         JMSService jmsService;
         try {
            jmsService = JMSService.getJMSServiceWithManagedServiceShutdownException();
         } catch (ManagedServiceShutdownException var6) {
            throw new UndeploymentException(var6);
         }

         synchronized(this.backEndNameLock) {
            if (this.backEndName == null) {
               throw new UndeploymentException(new ManagedDeploymentNotPreparedException("No BackEnd " + this.backEndName + " prepared with name " + name));
            }

            if (!name.equals(this.backEndName)) {
               throw new UndeploymentException("Unknown name " + name + " for prepared BackEnd " + this.backEndName);
            }
         }

         return jmsService.getBEDeployer().findBackEnd(name);
      }
   }

   public void setMigrationInProgress(boolean migrationInProgress) {
      JMSService jmsService;
      try {
         jmsService = JMSService.getJMSServiceWithManagedServiceShutdownException();
      } catch (ManagedServiceShutdownException var4) {
         throw new IllegalStateException(var4);
      }

      this.migrationInProgress = migrationInProgress;
      if (this.backEndName != null) {
         BackEnd be = jmsService.getBEDeployer().findBackEnd(this.backEndName);
         if (be != null) {
            be.setMigrationInProgress(migrationInProgress);
         }
      }

   }

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEAdminHandler.prepare(" + deployment + "), this=@" + this.hashCode());
      }

      DeploymentMBean bean = deployment.getMBean();

      try {
         JMSService jmsService = JMSService.getStartedService();
         JMSServerMBean mbean = (JMSServerMBean)bean;
         SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

         BackEnd backEnd;
         try {
            backEnd = new BackEnd(deployment.getName(), mbean, "JMSServer", jmsService);
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

         this.changeListener = new GenericBeanListener(mbean, backEnd, backendBeanSignatures, backendAdditionSignatures);
         this.changeListener.setCustomizer(backEnd);
         this.changeListener.initialize();
         backEnd.setSessionPoolMBeans(mbean.getJMSSessionPools());
         backEnd.setMigrationInProgress(this.migrationInProgress);
         jmsService.getBEDeployer().addBackEnd(backEnd);
         this.setBackEndName(deployment.getName());
         synchronized(this) {
            jmsService.getUddEntityHelper().prepareLocalJMSServer(deployment.getName(), (JMSServerMBean)bean);

            try {
               jmsService.startAddJMSServers(deployment, this.migrationInProgress);
            } catch (BeanUpdateRejectedException var15) {
               throw new DeploymentException(var15);
            }
         }

         if (mbean.isBytesPagingEnabled() || mbean.isMessagesPagingEnabled()) {
            JMSLogger.logServerPagingParametersDeprecated(bean.getName());
         }
      } catch (JMSException var18) {
         JMSLogger.logErrorCreateBE(deployment.getName(), var18);
         throw new DeploymentException("Error preparing the JMS Server " + deployment.getName() + ": " + var18.toString(), var18);
      } catch (ManagementException var19) {
         JMSLogger.logErrorCreateBE(deployment.getName(), var19);
         throw new DeploymentException("Error preparing the JMS Server " + deployment.getName() + ": " + var19.toString(), var19);
      } catch (Throwable var20) {
         JMSLogger.logErrorCreateBE(deployment.getName(), var20);
         throw new DeploymentException("Internal error preparing the JMS server " + deployment.getName() + ": " + var20.toString(), var20);
      }

      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Successfully prepared JMSServer: " + deployment.getName());
      }

   }

   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEAdminHandler.activate(" + deployment + "), this=@" + this.hashCode());
      }

      DeploymentMBean bean = deployment.getMBean();
      JMSServerMBean mbean = (JMSServerMBean)bean;
      DeploymentException de = null;
      if (this.changeListener != null) {
         this.changeListener.close();
         this.changeListener = null;
      }

      BackEnd backEnd = this.getBackEndWithDeploymentException(deployment);
      this.changeListener = new GenericBeanListener(mbean, backEnd, backendBeanSignatures, backendAdditionSignatures);
      this.changeListener.setCustomizer(backEnd);
      if (backEnd == null) {
         JMSLogger.logErrorDeployingBE(deployment.getName(), "activate", "prepare");
         throw new DeploymentException("Cannot activate the JMS Server " + deployment.getName() + " because it was not prepared");
      } else {
         boolean fatal = true;

         try {
            MigratableTargetMBean migratableTarget = null;
            TargetMBean[] targets = mbean.getTargets();
            if (targets.length > 0) {
               TargetMBean target = targets[0];
               if (target instanceof MigratableTargetMBean) {
                  backEnd.setIsMigratable(true);
                  migratableTarget = (MigratableTargetMBean)target;
               } else {
                  backEnd.setIsMigratable(false);
               }

               backEnd.setTarget(target.getName());
            }

            try {
               backEnd.setPersistentStore(this.findPersistentStore(deployment));
            } catch (DeploymentException var12) {
               if (this.isRestartOnFailureEnabled(migratableTarget) || this.isRestartInPlaceEnabled(deployment)) {
                  fatal = false;
               }

               throw var12;
            }

            backEnd.setPagingDirectory(this.findPagingDirectory(mbean, backEnd));
            backEnd.setPagingFileLockingEnabled(mbean.isPagingFileLockingEnabled());
            backEnd.setJMSMessageLogger(this.findJMSMessageLogger(mbean, backEnd));
            backEnd.open();
         } catch (JMSException var13) {
            JMSLogger.logErrorStartBE(bean.getName(), var13);
            de = new DeploymentException("Error activating the JMS Server " + bean.getName() + ": " + var13.toString(), var13);
         } catch (Throwable var14) {
            JMSLogger.logErrorStartBE(bean.getName(), var14);
            de = new DeploymentException("Internal error activating the JMS Server " + bean.getName() + ": " + var14.toString(), var14);
         }

         if (de != null) {
            if (fatal) {
               backEnd.setHealthFailed(de);
            } else {
               backEnd.setHealthFailedNonFatal(de);
            }

            throw de;
         } else {
            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("Successfully activated JMSServer: " + bean.getName());
            }

            synchronized(this) {
               backEnd.getJmsService().getUddEntityHelper().activateLocalJMSServer(deployment.getName(), (JMSServerMBean)bean);
               backEnd.getJmsService().finishAddJMSServers(deployment, true);
            }
         }
      }
   }

   public void deactivate(GenericManagedDeployment deployment) throws UndeploymentException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEAdminHandler.deactivate(" + deployment + "), this=@" + this.hashCode());
      }

      DeploymentMBean bean = deployment.getMBean();
      BackEnd backEnd = this.getBackEndWithUndeploymentException(deployment);
      if (backEnd == null) {
         JMSLogger.logErrorDeployingBE(bean.getName(), "deactivate", "activate");
         throw new UndeploymentException(new ManagedDeploymentNotPreparedException("Cannot deactivate the JMS Server " + bean.getName() + " because it was not activated"));
      } else {
         this.removeJMSMessageLogger(backEnd);
         backEnd.close();
         synchronized(this) {
            backEnd.getJmsService().getUddEntityHelper().deactivateLocalJMSServer(deployment.getName(), (JMSServerMBean)bean);

            try {
               backEnd.getJmsService().startRemoveJMSServers(deployment, this.migrationInProgress);
            } catch (BeanUpdateRejectedException var7) {
               throw new UndeploymentException(var7);
            }
         }

         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Successffuly deactivated JMSServer: " + bean.getName());
         }

      }
   }

   public void unprepare(GenericManagedDeployment deployment) throws UndeploymentException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEAdminHandler.unprepare(" + deployment + "), this=@" + this.hashCode());
      }

      DeploymentMBean bean = deployment.getMBean();
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Removing JMSServer: " + deployment.getName());
      }

      BackEnd backEnd = this.getBackEndWithUndeploymentException(deployment);
      if (backEnd == null) {
         JMSLogger.logErrorDeployingBE(deployment.getName(), "unprepare", "deactivate");
         throw new UndeploymentException(new ManagedDeploymentNotPreparedException("Cannot unprepare the JMS Server " + deployment.getName() + " because it was not deactivated"));
      } else {
         backEnd.destroy();
         if (this.changeListener != null) {
            this.changeListener.close();
            this.changeListener = null;
         }

         this.setBackEndName((String)null);
         backEnd.getJmsService().getBEDeployer().removeBackEnd(backEnd);
         backEnd.getJmsService().getUddEntityHelper().unprepareLocalJMSServer(deployment.getName(), (JMSServerMBean)bean);
         synchronized(this) {
            backEnd.getJmsService().finishRemoveJMSServers(deployment, true);
         }

         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Successfuly removed JMSServer: " + deployment.getName());
         }

      }
   }

   private boolean isRestartInPlaceEnabled(GenericManagedDeployment deployment) {
      JMSServerMBean mbean = (JMSServerMBean)deployment.getMBean();
      if (!mbean.getStoreEnabled()) {
         return false;
      } else {
         return mbean.getPersistentStore() != null ? mbean.getPersistentStore().getRestartInPlace() : false;
      }
   }

   private boolean isRestartOnFailureEnabled(MigratableTargetMBean migratableTarget) {
      return migratableTarget == null ? false : migratableTarget.getRestartOnFailure();
   }

   private PersistentStoreXA findPersistentStore(GenericManagedDeployment deployment) throws DeploymentException {
      JMSServerMBean mbean = (JMSServerMBean)deployment.getMBean();
      if (!mbean.getStoreEnabled()) {
         return null;
      } else {
         PersistentStoreXA ret = null;
         if (mbean.getPersistentStore() != null) {
            String storeName = mbean.getPersistentStore().getName();
            if (deployment.isClustered()) {
               if (deployment.isDistributed()) {
                  storeName = GenericDeploymentManager.getDecoratedDistributedInstanceName(mbean.getPersistentStore(), deployment.getInstanceName());
               } else {
                  storeName = GenericDeploymentManager.getDecoratedSingletonInstanceName(mbean.getPersistentStore(), deployment.getInstanceName());
               }
            }

            if (injectFaultOnStore != null && storeName.equals(injectFaultOnStore) && faultInjectionEnabledForThisServer) {
               injectFaultOnStore = null;
               throw new DeploymentException("Deliberate exception in BEAdminHandler#findPersistentStore");
            }

            ret = (PersistentStoreXA)PersistentStoreManager.getManager().getStore(storeName);
            if (ret == null) {
               throw new DeploymentException("The persistent store \"" + storeName + "\" does not exist");
            }

            if (JMSDebug.JMSBoot.isDebugEnabled()) {
               JMSDebug.JMSBoot.debug("JMSServer " + deployment.getName() + " using the 9.0 store " + storeName);
            }
         } else {
            ret = (PersistentStoreXA)PersistentStoreManager.getManager().getDefaultStore();
            if (ret == null) {
               throw new DeploymentException("The default persistent store does not exist");
            }

            if (JMSDebug.JMSBoot.isDebugEnabled()) {
               JMSDebug.JMSBoot.debug("JMSServer " + deployment.getName() + " using the default store");
            }
         }

         return ret;
      }
   }

   private String findPagingDirectory(JMSServerMBean mbean, BackEnd backEnd) {
      JMSService jmsService = backEnd.getJmsService();
      String serverName = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName();
      ComponentInvocationContext cic = jmsService.getComponentInvocationContext();
      String dir = PartitionFileSystemUtils.locatePagingDirectory(cic, serverName, mbean.getPagingDirectory());
      return dir;
   }

   private JMSMessageLogger findJMSMessageLogger(JMSServerMBean mbean, BackEnd backEnd) throws DeploymentException {
      try {
         JMSMessageLogFileMBean logFile = mbean.getJMSMessageLogFile();
         return JMSMessageLoggerFactory.findOrCreateJMSMessageLogger(logFile, backEnd.getRuntimeMBean());
      } catch (IOException var4) {
         throw new DeploymentException("Cannot find or create JMS message log file for JMS server " + mbean.getName(), var4);
      }
   }

   private void removeJMSMessageLogger(BackEnd backEnd) {
      try {
         if (backEnd.getRuntimeMBean() != null) {
            JMSMessageLoggerFactory.removeJMSMessageLogger(backEnd.getRuntimeMBean());
         }
      } catch (IOException var3) {
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Exception thrown during removal of " + backEnd.getRuntimeMBean().getName() + " logger");
            var3.printStackTrace();
         }
      }

   }

   static {
      backendBeanSignatures.put("BytesMaximum", Long.TYPE);
      backendBeanSignatures.put("BytesThresholdHigh", Long.TYPE);
      backendBeanSignatures.put("BytesThresholdLow", Long.TYPE);
      backendBeanSignatures.put("MessagesMaximum", Long.TYPE);
      backendBeanSignatures.put("MessagesThresholdHigh", Long.TYPE);
      backendBeanSignatures.put("MessagesThresholdLow", Long.TYPE);
      backendBeanSignatures.put("BlockingSendPolicy", String.class);
      backendBeanSignatures.put("ExpirationScanInterval", Integer.TYPE);
      backendBeanSignatures.put("MaximumMessageSize", Integer.TYPE);
      backendBeanSignatures.put("MessageBufferSize", Long.TYPE);
      backendBeanSignatures.put("PagingDirectory", String.class);
      backendBeanSignatures.put("PagingFileLockingEnabled", Boolean.TYPE);
      backendBeanSignatures.put("PagingMinWindowBufferSize", Integer.TYPE);
      backendBeanSignatures.put("PagingMaxWindowBufferSize", Integer.TYPE);
      backendBeanSignatures.put("PagingIoBufferSize", Integer.TYPE);
      backendBeanSignatures.put("PagingBlockSize", Integer.TYPE);
      backendBeanSignatures.put("PagingMaxFileSize", Long.TYPE);
      backendBeanSignatures.put("HostingTemporaryDestinations", Boolean.TYPE);
      backendBeanSignatures.put("TemporaryTemplateName", String.class);
      backendBeanSignatures.put("TemporaryTemplateResource", String.class);
      backendBeanSignatures.put("ProductionPausedAtStartup", String.class);
      backendBeanSignatures.put("InsertionPausedAtStartup", String.class);
      backendBeanSignatures.put("ConsumptionPausedAtStartup", String.class);
      backendBeanSignatures.put("AllowsPersistentDowngrade", Boolean.TYPE);
      backendBeanSignatures.put("StoreMessageCompressionEnabled", Boolean.TYPE);
      backendBeanSignatures.put("PagingMessageCompressionEnabled", Boolean.TYPE);
      backendBeanSignatures.put("MessageCompressionOptions", String.class);
      backendBeanSignatures.put("MessageCompressionOptionsOverride", String.class);
      backendAdditionSignatures.put("JMSSessionPools", JMSSessionPoolMBean.class);
      UOWSequenceImpl.setCallbackFactory(new BEUOWCallbackFactory());
      faultInjectionEnabledForThisServer = false;
      injectFaultOnStore = System.getProperty("weblogic.jms.deployer.BEAdminHandler.injectFaultOnStore");
      if (injectFaultOnStore != null) {
         String injectFaultOnStoreOnServerName = System.getProperty("weblogic.jms.deployer.BEAdminHandler.injectFaultOnStoreOnServerName");
         faultInjectionEnabledForThisServer = injectFaultOnStoreOnServerName == null || injectFaultOnStoreOnServerName.equals(System.getProperty("weblogic.Name"));
      }

   }
}
