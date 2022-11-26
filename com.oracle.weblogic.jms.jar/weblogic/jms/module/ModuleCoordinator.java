package weblogic.jms.module;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.naming.Context;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.cluster.migration.ReadyListener;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.LateDescriptorUpdateListener;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSTargetsListener;
import weblogic.jms.common.ModuleName;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.saf.SAFService;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.restart.RPException;
import weblogic.restart.RPManager;
import weblogic.restart.RPService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public abstract class ModuleCoordinator implements Module, UpdateListener, LateDescriptorUpdateListener {
   public TargetingHelper targeter;
   private static final String DOT = ".";
   public static final int MOD_TYPE_DEPLOYMENT = 0;
   public static final int MOD_TYPE_SYSTEM = 1;
   public static final int MOD_TYPE_INTEROP = 2;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String earModuleName;
   private String uri;
   private ApplicationContextInternal appCtx;
   protected ModuleName moduleName;
   private JMSComponent jmsComponent;
   private int moduleType;
   private DeploymentListener deploymentListener = new DeploymentListener();
   private GenericBeanListener targetingAdditionsListener;
   private GenericBeanListener earBasicListener;
   private JMSTargetsListener jmsListener = new JMSTargetsListenerImpl();
   private JMSTargetsListener safListener = new JMSTargetsListenerImpl();
   private HashMap targetingListenees = new HashMap();
   private HashMap registeredMigratableTargets = new HashMap();
   private boolean hasUpdate = false;
   private Object update;
   private boolean hasCalledPrepareUpdate = false;
   private String hasGottenModulePrepareUpdate = null;
   private boolean hasMigratableUpdate = false;
   private DomainMBean hasMigratableUpdateDomain = null;
   private boolean hasCalledMigratablePrepareUpdate = false;
   private boolean isActive = false;
   private static final HashMap deploymentAdditions;
   private ASMActivateHandler asmActivateHandler = null;
   private ASMDeactivateHandler asmDeactivateHandler = null;
   private RPActivateHandler rpActivateHandler = null;
   private volatile ComponentInvocationContext cic = null;
   private static ComponentInvocationContextManager CICM;
   protected DescriptorUpdateEvent descriptorUpdateEvent = null;
   protected boolean isDestroyed = true;
   private boolean initFailed = false;
   protected boolean isJMSResourceDefinition;

   protected ModuleCoordinator(String paramEARModuleName, String paramURI) {
      this.earModuleName = paramEARModuleName;
      this.uri = paramURI;
   }

   protected abstract DescriptorBean getModuleDescriptor();

   protected abstract void initializeModule(ApplicationContextInternal var1, DomainMBean var2) throws ModuleException;

   protected abstract void initializeModule(ApplicationContextInternal var1, DomainMBean var2, JMSBean var3, Context var4, String var5, String var6, boolean var7) throws ModuleException;

   protected abstract void prepare(DomainMBean var1) throws ModuleException;

   protected abstract void activate(DomainMBean var1) throws ModuleException;

   protected abstract void deactivate(DomainMBean var1) throws ModuleException;

   protected abstract void unprepare(DomainMBean var1) throws ModuleException;

   protected abstract void destroy(DomainMBean var1) throws ModuleException;

   protected abstract void remove(DomainMBean var1) throws ModuleException;

   protected abstract Object prepareUpdate(DomainMBean var1) throws ModuleException;

   protected abstract void activateUpdate(DomainMBean var1, Object var2) throws ModuleException;

   protected abstract void rollbackUpdate(DomainMBean var1, Object var2);

   protected abstract void activateForRP(DomainMBean var1, Set var2) throws ModuleException;

   protected int getModuleType() {
      return this.moduleType;
   }

   public String getId() {
      return this.uri;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[]{this.jmsComponent};
   }

   public DescriptorBean[] getDescriptors() {
      DescriptorBean[] retVal = new DescriptorBean[]{this.getModuleDescriptor()};
      return retVal;
   }

   public GenericClassLoader init(ApplicationContext paramAppCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.internalInit((ApplicationContextInternal)paramAppCtx, reg);
      return parent;
   }

   public void initUsingLoader(ApplicationContext paramAppCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.internalInit((ApplicationContextInternal)paramAppCtx, reg);
   }

   public void prepare() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module prepare called in " + this.moduleName + ", partition = " + this.getAppCtx().getPartitionName());
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var2 = null;

      try {
         DomainMBean domain = this.getDomainFromAppCtx();
         this.prepare(domain);
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void activate() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module activate called in " + this.moduleName);
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var2 = null;

      try {
         this.internalActivate();
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void setCIC(ApplicationContext paramAppCtx) {
      if (this.cic == null) {
         ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
         String threadPartitionName = JMSService.getSafePartitionKey(cic);
         String partitionName = ((ApplicationContextInternal)paramAppCtx).getPartitionName();
         if (PartitionUtils.isSamePartition(threadPartitionName, partitionName)) {
            this.cic = cic;
         } else {
            if (PartitionUtils.isDomain(partitionName)) {
               partitionName = "DOMAIN";
            }

            this.cic = CICM.createComponentInvocationContext(partitionName);
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("ModuleCoordinator setCIC manufactured cic " + this.debugCIC(cic));
            }
         }

      }
   }

   private String debugCIC(ComponentInvocationContext cic) {
      return cic + " id-class " + (cic != null ? System.identityHashCode(cic) + cic.getClass().getName() : "Nil");
   }

   private void internalActivate() throws ModuleException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      this.activate(domain);
      TargetInfoMBean timber = this.getTargetingBean(domain);
      BasicDeploymentMBean bdmb;
      if (timber != null && timber instanceof BasicDeploymentMBean) {
         bdmb = (BasicDeploymentMBean)timber;
         SubDeploymentMBean[] subs = bdmb.getSubDeployments();

         for(int lcv = 0; lcv < subs.length; ++lcv) {
            SubDeploymentMBean sub = subs[lcv];
            sub.addBeanUpdateListener(this.deploymentListener);
            this.targetingListenees.put(sub.getName(), sub);
         }

         this.targetingAdditionsListener = new GenericBeanListener(bdmb, this, (Map)null, deploymentAdditions, true);
      } else {
         if (timber != null) {
            this.activateSubDeploymentListener((SubDeploymentMBean)timber);
            this.targetingAdditionsListener = new GenericBeanListener(timber, this, (Map)null, deploymentAdditions, true);
         }

         bdmb = this.getBasicDeployment(domain);
         this.earBasicListener = new GenericBeanListener(bdmb, new EARSubDeploymentListener(), (Map)null, deploymentAdditions, true);
      }

      Descriptor descriptor = domain.getDescriptor();
      descriptor.addUpdateListener(this);
      this.registerMTService(false);

      JMSService jmsService;
      try {
         jmsService = JMSService.getStartedService();
      } catch (JMSException | ServiceFailureException | ManagementException var8) {
         throw new ModuleException(var8);
      }

      jmsService.addJMSServerListener(this.jmsListener);
      SAFService.getSAFService().getDeployer().addSAFAgentListener(this.safListener);
      this.isActive = true;
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module activate finished in " + this.moduleName);
      }

   }

   private static RPManager getRPManager() {
      try {
         return (RPManager)GlobalServiceLocator.getServiceLocator().getService(RPManager.class, new Annotation[0]);
      } catch (Exception var1) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("Error to getRPManager in JMSModule ", var1);
         }

         return null;
      }
   }

   private void activateSubDeploymentListener(SubDeploymentMBean timber) {
      SubDeploymentMBean[] subs = timber.getSubDeployments();

      for(int lcv = 0; lcv < subs.length; ++lcv) {
         SubDeploymentMBean sub = subs[lcv];
         DescriptorBean db = subs[lcv];
         db.addBeanUpdateListener(this.deploymentListener);
         this.targetingListenees.put(sub.getName(), sub);
      }

   }

   private MigrationManager lookupMigrationManager() throws ModuleException {
      try {
         ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
         MigrationManager mm = (MigrationManager)sl.getService(MigrationManager.class, new Annotation[0]);
         return mm;
      } catch (MultiException | IllegalStateException var3) {
         throw new ModuleException(var3);
      }
   }

   public void start() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: module start called in " + this.moduleName);
      }

   }

   public void deactivate() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module deactivate called in " + this.moduleName);
      }

      this.isActive = false;
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var2 = null;

      try {
         this.internalDeactivate();
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void internalDeactivate() throws ModuleException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      String partitionName = JMSService.getSafePartitionKey(cic);
      JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
      if (jmsService != null) {
         jmsService.removeJMSServerListener(this.jmsListener);
      }

      SAFService safService = SAFService.getSAFService(partitionName);
      if (safService != null) {
         safService.getDeployer().removeSAFAgentListener(this.safListener);
      }

      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      Descriptor descriptor = domain.getDescriptor();
      descriptor.removeUpdateListener(this);
      MigrationManager mm = this.lookupMigrationManager();
      Iterator it = this.registeredMigratableTargets.values().iterator();

      while(it.hasNext()) {
         MigratableTargetMBean mtMBean = (MigratableTargetMBean)it.next();

         try {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("INFO: Unregistering JMS module =" + this.getName() + " for MT =" + mtMBean.getName());
            }

            mm.unregister(this.asmActivateHandler, mtMBean);
            mm.unregister(this.asmDeactivateHandler, mtMBean);
            it.remove();
         } catch (MigrationException var11) {
            throw new ModuleException(var11);
         }
      }

      if (getRPManager() != null) {
         getRPManager().removeServiceFromGroup((String)null, this.rpActivateHandler);
      }

      if (this.earBasicListener != null) {
         this.earBasicListener.close();
         this.earBasicListener = null;
      }

      this.deactivateSubDeploymentListener();
      Object localUpdate;
      if (this.hasCalledPrepareUpdate) {
         this.hasUpdate = false;
         localUpdate = this.update;
         this.update = null;
         this.hasCalledPrepareUpdate = false;
         this.hasGottenModulePrepareUpdate = null;
         this.rollbackUpdate(domain, localUpdate);
      } else if (this.hasCalledMigratablePrepareUpdate) {
         this.hasMigratableUpdate = false;
         localUpdate = this.update;
         this.update = null;
         this.hasCalledMigratablePrepareUpdate = false;
         this.hasGottenModulePrepareUpdate = null;
         this.rollbackUpdate(domain, localUpdate);
      }

      this.deactivate(domain);
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module deactivate finished in " + this.moduleName);
      }

   }

   public void deactivateSubDeploymentListener() {
      if (this.targetingAdditionsListener != null) {
         this.targetingAdditionsListener.close();
         this.targetingAdditionsListener = null;
      }

      Iterator it = this.targetingListenees.keySet().iterator();

      while(it.hasNext()) {
         String beanName = (String)it.next();
         DescriptorBean db = (DescriptorBean)this.targetingListenees.get(beanName);
         db.removeBeanUpdateListener(this.deploymentListener);
      }

      this.targetingListenees.clear();
   }

   public void unprepare() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module unprepare called in " + this.moduleName);
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var2 = null;

      try {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.unprepare(domain);
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module destroy called in " + this.moduleName);
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var3 = null;

      try {
         reg.removeUpdateListener(this);
         this.closeJMSComponent();
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.destroy(domain);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void remove() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module remove called in " + this.moduleName);
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var2 = null;

      try {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.remove(domain);
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
   }

   public void forceProductionToAdmin() throws ModuleException {
   }

   public boolean acceptURI(String proposedURI) {
      if (proposedURI != null && !this.uri.equals(proposedURI) && !".".equals(proposedURI)) {
         if (this.targetingListenees.containsKey(proposedURI)) {
            return true;
         } else if (this.earModuleName != null && proposedURI.startsWith(this.earModuleName)) {
            return true;
         } else {
            DomainMBean proposedDomain = this.appCtx.getProposedDomain();
            if (proposedDomain == null) {
               return false;
            } else {
               BasicDeploymentMBean deployment = this.getBasicDeployment(proposedDomain);
               SubDeploymentMBean[] subs = deployment.getSubDeployments();

               for(int lcv = 0; lcv < subs.length; ++lcv) {
                  SubDeploymentMBean sub = subs[lcv];
                  if (this.earModuleName == null) {
                     if (sub.getName().equals(proposedURI)) {
                        return true;
                     }
                  } else if (this.earModuleName.equals(sub.getName())) {
                     SubDeploymentMBean[] innerSubs = sub.getSubDeployments();

                     for(int inner = 0; inner < innerSubs.length; ++inner) {
                        SubDeploymentMBean innerSub = innerSubs[inner];
                        if (innerSub.getName().equals(proposedURI)) {
                           return true;
                        }
                     }
                  }
               }

               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("INFO: Saying no because no criteria matched " + this.moduleName + " with proposed uri=\"" + proposedURI + "\" local uri=\"" + this.uri + "\" ear name=\"" + this.earModuleName + "\"");
               }

               return false;
            }
         }
      } else {
         return true;
      }
   }

   public void prepareUpdate(String paramURI) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module prepareUpdate called in " + this.moduleName + " with uri " + paramURI + " globalURI=" + this.uri);
      }

      if (!this.isActive) {
         StackTraceUtils.dumpStack();
         throw new AssertionError("We got a prepareUpdate call, but we are not in the ACTIVE state in module " + this.moduleName);
      } else if (this.hasCalledPrepareUpdate) {
         if (this.hasGottenModulePrepareUpdate == null) {
            this.hasGottenModulePrepareUpdate = paramURI;
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module prepareUpdate exits, work already done for " + this.moduleName);
         }

      } else {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
         Throwable var3 = null;

         try {
            this.update = this.prepareUpdate(this.getProposedDomain());
         } catch (Throwable var12) {
            var3 = var12;
            throw var12;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var11) {
                     var3.addSuppressed(var11);
                  }
               } else {
                  mic.close();
               }
            }

         }

         this.hasUpdate = true;
         this.hasCalledPrepareUpdate = true;
         this.hasGottenModulePrepareUpdate = paramURI;
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module prepareUpdate exits normally in " + this.moduleName);
         }

      }
   }

   public void activateUpdate(String uri) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module activateUpdate called in " + this.moduleName);
      }

      if (!this.hasUpdate) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module activateUpdate exiting with no updates in " + this.moduleName);
         }

      } else {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.hasUpdate = false;
         Object localUpdate = this.update;
         this.update = null;
         this.hasCalledPrepareUpdate = false;
         this.hasGottenModulePrepareUpdate = null;
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
         Throwable var5 = null;

         try {
            this.activateUpdate(domain, localUpdate);
         } catch (Throwable var14) {
            var5 = var14;
            throw var14;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var13) {
                     var5.addSuppressed(var13);
                  }
               } else {
                  mic.close();
               }
            }

         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module activateUpdate exiting successfully in " + this.moduleName);
         }

      }
   }

   public void rollbackUpdate(String uri) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: module rollbackUpdate called in " + this.moduleName);
      }

      if (!this.hasUpdate) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module rollbackUpdate exiting with no updates in " + this.moduleName);
         }

      } else {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.hasUpdate = false;
         Object localUpdate = this.update;
         this.update = null;
         this.hasCalledPrepareUpdate = false;
         this.hasGottenModulePrepareUpdate = null;
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
         Throwable var5 = null;

         try {
            this.rollbackUpdate(domain, localUpdate);
         } catch (Throwable var14) {
            var5 = var14;
            throw var14;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var13) {
                     var5.addSuppressed(var13);
                  }
               } else {
                  mic.close();
               }
            }

         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module rollbackUpdate exiting successfully in " + this.moduleName);
         }

      }
   }

   private void prepareMigratableUpdate() throws DescriptorUpdateRejectedException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: finisher prepareMigratableUpdate called in " + this.moduleName);
      }

      if (this.hasMigratableUpdate && this.hasMigratableUpdateDomain != null) {
         if (!this.hasCalledMigratablePrepareUpdate) {
            try {
               this.update = this.prepareUpdate(this.hasMigratableUpdateDomain);
               this.hasCalledMigratablePrepareUpdate = true;
            } catch (ModuleException var2) {
               throw new DescriptorUpdateRejectedException(var2.getMessage(), var2);
            }
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher prepareMigratableUpdate exits in " + this.moduleName);
         }

      } else {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher prepareMigratableUpdate exits with no updates in " + this.moduleName + " hasMigratableUpdate=" + this.hasMigratableUpdate + ", hasMigratableUpdateDomain=" + this.hasMigratableUpdateDomain);
         }

      }
   }

   public void prepareUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateRejectedException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: finisher prepareUpdate called in " + this.moduleName);
      }

      if (!this.hasUpdate) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher prepareUpdate exits with no updates in " + this.moduleName + " hasUpdate=" + this.hasUpdate);
         }

      } else {
         this.descriptorUpdateEvent = event;
         Descriptor descriptor = event.getProposedDescriptor();
         DescriptorBean db = descriptor.getRootBean();
         DomainMBean proposedDomain = (DomainMBean)db;
         if (!this.hasCalledPrepareUpdate) {
            try {
               ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
               Throwable var6 = null;

               try {
                  this.update = this.prepareUpdate(proposedDomain);
                  this.hasCalledPrepareUpdate = true;
               } catch (Throwable var16) {
                  var6 = var16;
                  throw var16;
               } finally {
                  if (mic != null) {
                     if (var6 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var15) {
                           var6.addSuppressed(var15);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            } catch (ModuleException var18) {
               throw new DescriptorUpdateRejectedException(var18.getMessage(), var18);
            }
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher prepareUpdate exits in " + this.moduleName);
         }

      }
   }

   private void activateMigratableUpdate() throws DescriptorUpdateFailedException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: finisher activateMigratableUpdate called in " + this.moduleName);
      }

      if (!this.hasMigratableUpdate) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher activateMigratableUpdate exiting with no changes in " + this.moduleName + " hasMigratableUpdate=" + this.hasMigratableUpdate + " hasGottenModulePrepareUpdate=" + this.hasGottenModulePrepareUpdate);
         }

      } else {
         this.hasMigratableUpdate = false;
         this.resetMigratableUpdateInfo();
         Object localUpdate = this.update;
         this.update = null;
         this.hasCalledMigratablePrepareUpdate = false;
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();

         try {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("INFO: finisher activateMigratableUpdate, before calling " + this.moduleName + " module's activateUpdate() domain=" + domain.getName() + ", localUpdate=" + (UpdateInformation)localUpdate);
            }

            this.activateUpdate(domain, localUpdate);
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("INFO: finisher activateMigratableUpdate, after calling " + this.moduleName + " module's activateUpdate() domain=" + domain.getName() + ", localUpdate=" + (UpdateInformation)localUpdate);
            }
         } catch (ModuleException var4) {
            JMSLogger.logActivateFailedDuringTargetingChange(this.moduleName.toString(), var4.toString());
            throw new DescriptorUpdateFailedException(var4.getMessage(), var4);
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher activateMigratableUpdate exits in " + this.moduleName);
         }

      }
   }

   public void activateUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateFailedException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: finisher activateUpdate called in " + this.moduleName);
      }

      if (this.hasUpdate && this.hasGottenModulePrepareUpdate == null) {
         this.hasUpdate = false;
         Object localUpdate = this.update;
         this.update = null;
         this.hasCalledPrepareUpdate = false;
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();

         try {
            ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
            Throwable var5 = null;

            try {
               this.activateUpdate(domain, localUpdate);
            } catch (Throwable var15) {
               var5 = var15;
               throw var15;
            } finally {
               if (mic != null) {
                  if (var5 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } catch (ModuleException var17) {
            JMSLogger.logActivateFailedDuringTargetingChange(this.moduleName.toString(), var17.toString());
            throw new DescriptorUpdateFailedException(var17.getMessage(), var17);
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher activateUpdate exits in " + this.moduleName);
         }

      } else {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher activateUpdate exiting with no changes in " + this.moduleName + " hasUpdate=" + this.hasUpdate + " hasGottenModulePrepareUpdate=" + this.hasGottenModulePrepareUpdate);
         }

      }
   }

   private void rollbackMigratableUpdate() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: finisher rollbackMigratableUpdate called in " + this.moduleName);
      }

      if (!this.hasMigratableUpdate) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher rollbackMigratableUpdate exiting with no changes in " + this.moduleName + " hasMigratableUpdate=" + this.hasMigratableUpdate);
         }

      } else {
         this.hasMigratableUpdate = false;
         Object localUpdate = this.update;
         this.update = null;
         this.hasCalledMigratablePrepareUpdate = false;
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher rollbackMigratableUpdate, before calling " + this.moduleName + " module's rollbackUpdate() domain=" + domain.getName() + ", localUpdate=" + (UpdateInformation)localUpdate);
         }

         this.rollbackUpdate(domain, localUpdate);
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher rollbackMigratableUpdate, after calling " + this.moduleName + " module's rollbackUpdate() domain=" + domain.getName() + ", localUpdate=" + (UpdateInformation)localUpdate);
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher rollbackMigratableUpdate exits in " + this.moduleName);
         }

      }
   }

   public void rollbackUpdate(DescriptorUpdateEvent event) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: finisher rollbackUpdate called in " + this.moduleName);
      }

      if (!this.hasUpdate) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher rollbackUpdate exiting with no changes in " + this.moduleName + " hasUpdate=" + this.hasUpdate);
         }

      } else {
         this.hasUpdate = false;
         Object localUpdate = this.update;
         this.update = null;
         this.hasCalledPrepareUpdate = false;
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.rollbackUpdate(domain, localUpdate);
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: finisher rollbackUpdate exits in " + this.moduleName);
         }

      }
   }

   public void migratableUpdate() throws MigrationException {
      synchronized(this) {
         try {
            ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
            Throwable var3 = null;

            try {
               try {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("INFO: Calling prepareMigratableUpdate for " + this);
                  }

                  this.prepareMigratableUpdate();
               } catch (Exception var20) {
                  this.resetMigratableUpdateInfo();
                  throw new MigrationException(var20);
               }

               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("INFO: Calling activateMigratableUpdate for " + this);
               }

               try {
                  this.activateMigratableUpdate();
               } catch (Exception var19) {
                  this.rollbackMigratableUpdate();
                  this.resetMigratableUpdateInfo();
                  throw new MigrationException(var19);
               }

               this.prepareMigratableUpdate();
            } catch (Throwable var21) {
               var3 = var21;
               throw var21;
            } finally {
               if (mic != null) {
                  if (var3 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var17) {
                        var3.addSuppressed(var17);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } catch (Exception var23) {
            this.resetMigratableUpdateInfo();
            throw new MigrationException(var23);
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Calling activateMigratableUpdate for " + this);
         }

         try {
            this.activateMigratableUpdate();
         } catch (Exception var18) {
            this.resetMigratableUpdateInfo();
            throw new MigrationException(var18);
         }

      }
   }

   private void resetMigratableUpdateInfo() {
      synchronized(this) {
         if (this.hasMigratableUpdateDomain != null) {
            this.hasMigratableUpdateDomain = null;
         }

         if (this.targeter != null) {
            synchronized(this.targeter) {
               this.targeter.setUpdateAction(-1);
               this.targeter.setHasUpdate(false);
               this.targeter.setUpdatedTargetMBean((TargetMBean)null);
            }
         }

      }
   }

   private void openJMSComponent() throws ModuleException {
      try {
         this.jmsComponent = new JMSComponent(this.moduleName.toString(), this.getId(), this.appCtx);
         this.jmsComponent.open();
      } catch (ManagementException var2) {
         throw new ModuleException(var2.getMessage(), var2);
      }
   }

   private void closeJMSComponent() {
      if (this.jmsComponent != null) {
         try {
            this.jmsComponent.close();
         } catch (ManagementException var2) {
            JMSLogger.logComponentCloseFailure(this.moduleName.toString(), var2.toString());
         }

         this.jmsComponent = null;
      }

   }

   private String getEARModuleName() {
      return this.earModuleName;
   }

   ApplicationContextInternal getAppCtx() {
      return this.appCtx;
   }

   private void internalInit(ApplicationContextInternal paramAppCtx, UpdateListener.Registration reg) throws ModuleException {
      this.setCIC(paramAppCtx);
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var4 = null;

      try {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module internalInit called in " + this.moduleName);
         }

         JMSService jmsService;
         try {
            jmsService = JMSService.getStartedService();
         } catch (JMSException | ServiceFailureException | ManagementException var18) {
            throw new ModuleException(var18);
         }

         if (jmsService.getFrontEnd() == null) {
            throw new ModuleException(JMSExceptionLogger.logJMSServiceNotInitialized2Loggable().getMessage());
         }

         this.appCtx = paramAppCtx;
         reg.addUpdateListener(this);
         this.moduleName = new ModuleName(this.appCtx.getApplicationId(), this.getEARModuleName());
         this.moduleType = this.getModuleTypeFromAppCtx();
         this.openJMSComponent();
         this.registerRPService();
         this.registerMTService(true);
         DomainMBean domain = this.getDomainFromAppCtx();

         try {
            this.initializeModule(this.appCtx, domain);
         } catch (ModuleException var17) {
            this.initFailed = true;
            throw var17;
         }
      } catch (Throwable var19) {
         var4 = var19;
         throw var19;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var16) {
                  var4.addSuppressed(var16);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   void internalInit(ApplicationContextInternal paramAppCtx, UpdateListener.Registration reg, JMSBean jmsBean, Context context, String jmsServerName, String jndiName, boolean isJMSDestinationDefinition) throws ModuleException {
      this.setCIC(paramAppCtx);
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var9 = null;

      try {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: module internalInit called in " + this.moduleName + ", with jmsServerName " + jmsServerName);
         }

         if (JMSService.getJMSServiceWithModuleException().getFrontEnd() == null) {
            throw new ModuleException(JMSExceptionLogger.logJMSServiceNotInitialized2Loggable().getMessage());
         }

         this.appCtx = paramAppCtx;
         this.moduleName = new ModuleName(this.appCtx.getApplicationId(), this.getEARModuleName());
         this.moduleType = this.getModuleTypeFromAppCtx();
         this.openJMSComponent();
         this.registerRPService();
         DomainMBean domain = this.getDomainFromAppCtx();
         this.initializeModule(this.appCtx, domain, jmsBean, context, jmsServerName, jndiName, isJMSDestinationDefinition);
      } catch (Throwable var18) {
         var9 = var18;
         throw var18;
      } finally {
         if (mic != null) {
            if (var9 != null) {
               try {
                  mic.close();
               } catch (Throwable var17) {
                  var9.addSuppressed(var17);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void registerRPService() {
      if (getRPManager() != null) {
         this.rpActivateHandler = new RPActivateHandler();
         getRPManager().addServiceToGroup((String)null, this.rpActivateHandler);
      }

   }

   private void registerMTService(boolean isInitPhase) throws ModuleException {
      this.asmActivateHandler = new ASMActivateHandler();
      this.asmDeactivateHandler = new ASMDeactivateHandler();
      MigratableTargetMBean[] migratableTargetMBeans = this.getRuntimeDomain().getMigratableTargets();
      MigrationManager mm = this.lookupMigrationManager();

      for(int i = 0; i < migratableTargetMBeans.length; ++i) {
         try {
            if (isInitPhase) {
               if ("manual".equals(migratableTargetMBeans[i].getMigrationPolicy())) {
                  this.registerModuleToMigratableTarget(mm, migratableTargetMBeans[i]);
               }
            } else if (this.isJMSResourceDefinition) {
               this.registerModuleToMigratableTarget(mm, migratableTargetMBeans[i]);
            } else if (!"manual".equals(migratableTargetMBeans[i].getMigrationPolicy())) {
               this.registerModuleToMigratableTarget(mm, migratableTargetMBeans[i]);
            }
         } catch (MigrationException var6) {
            throw new ModuleException(var6);
         }
      }

   }

   private void registerModuleToMigratableTarget(MigrationManager migrationManager, MigratableTargetMBean migratableTargetMBean) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: Registering JMS module =" + this.getName() + " for MT =" + migratableTargetMBean.getName());
      }

      migrationManager.register(this.asmActivateHandler, migratableTargetMBean, false, (ReadyListener)null);
      migrationManager.register(this.asmDeactivateHandler, migratableTargetMBean, true, (ReadyListener)null);
      this.registeredMigratableTargets.put(migratableTargetMBean.getName(), migratableTargetMBean);
   }

   private int getModuleTypeFromAppCtx() throws ModuleException {
      if (this.appCtx.getAppDeploymentMBean() != null) {
         return 0;
      } else if (this.appCtx.getSystemResourceMBean() != null) {
         return 1;
      } else {
         throw new ModuleException(JMSExceptionLogger.logUnknownJMSModuleTypeLoggable(this.moduleName.toString()).getMessage());
      }
   }

   private DomainMBean getDomainFromAppCtx() {
      BasicDeploymentMBean basicDeployment = null;
      switch (this.moduleType) {
         case 0:
            basicDeployment = this.appCtx.getAppDeploymentMBean();
            break;
         case 1:
            basicDeployment = this.appCtx.getSystemResourceMBean();
            break;
         default:
            throw new AssertionError("Unknown module type: " + this.moduleType);
      }

      return JMSLegalHelper.getDomain((WebLogicMBean)basicDeployment);
   }

   protected TargetInfoMBean getTargetingBean(DomainMBean domain) {
      switch (this.moduleType) {
         case 0:
            AppDeploymentMBean appDeployment = this.appCtx.getAppDeploymentMBean();
            appDeployment = domain.lookupAppDeployment(appDeployment.getName());
            if (appDeployment != null && this.earModuleName != null) {
               return appDeployment.lookupSubDeployment(this.earModuleName);
            }

            return appDeployment;
         case 1:
            TargetInfoMBean sysRes = this.appCtx.getSystemResourceMBean();
            TargetInfoMBean sysRes = domain.lookupJMSSystemResource(sysRes.getName());
            return sysRes;
         default:
            throw new AssertionError("Only file-based modules have TargetInfos");
      }
   }

   private DomainMBean getProposedDomain() {
      DomainMBean retVal = this.appCtx.getProposedDomain();
      if (retVal == null) {
         retVal = ManagementService.getRuntimeAccess(kernelId).getDomain();
      }

      return retVal;
   }

   private DomainMBean getRuntimeDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   protected BasicDeploymentMBean getBasicDeployment(DomainMBean domain) {
      switch (this.moduleType) {
         case 0:
            AppDeploymentMBean ctxAppDeployment = this.appCtx.getAppDeploymentMBean();
            AppDeploymentMBean appDeployment = domain.lookupAppDeployment(ctxAppDeployment.getName());
            if (appDeployment == null) {
               appDeployment = ctxAppDeployment;
            }

            return appDeployment;
         case 1:
            SystemResourceMBean ctxSysRes = this.appCtx.getSystemResourceMBean();
            SystemResourceMBean sysRes = domain.lookupJMSSystemResource(ctxSysRes.getName());
            if (sysRes == null) {
               sysRes = ctxSysRes;
            }

            return (BasicDeploymentMBean)sysRes;
         default:
            return null;
      }
   }

   public void startAddSubDeployments(SubDeploymentMBean toAdd) throws BeanUpdateRejectedException {
      this.hasUpdate = true;
   }

   public void finishAddSubDeployments(SubDeploymentMBean toAdd, boolean isActivate) {
      if (isActivate && this.isActive) {
         toAdd.addBeanUpdateListener(this.deploymentListener);
         this.targetingListenees.put(toAdd.getName(), toAdd);
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Now listening on subdeployment " + toAdd.getName() + " in module " + this.moduleName);
         }

      } else {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Not listening on sub-deployment " + toAdd.getName() + " either because the module is shutdown(" + !this.isActive + ") or this is a rollback which included an addition of a subdeployment(" + !isActivate + ") in module " + this + " of name " + this.moduleName);
         }

      }
   }

   public void startRemoveSubDeployments(SubDeploymentMBean toRemove) throws BeanUpdateRejectedException {
      Object object = this.targetingListenees.get(toRemove.getName());
      if (object == null) {
         throw new BeanUpdateRejectedException(JMSExceptionLogger.logUnknownSubDeploymentLoggable(this.moduleName.toString(), toRemove.getName()).getMessage());
      } else {
         this.hasUpdate = true;
      }
   }

   public void finishRemoveSubDeployments(SubDeploymentMBean toRemove, boolean isActivate) {
      if (isActivate) {
         toRemove.removeBeanUpdateListener(this.deploymentListener);
         this.targetingListenees.remove(toRemove.getName());
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: listener on subdeployment " + toRemove.getName() + " in module " + this.moduleName + " has been removed");
         }

         this.targeter.subModuleListenerContextMap.remove(toRemove.getName());
      }
   }

   public String getName() {
      return this.moduleName.toString();
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_JMS;
   }

   public String toString() {
      return "JMSModule(" + System.identityHashCode(this) + ",uri=" + this.uri + ",EARModuleName=" + this.earModuleName + "), Type(" + this.getType() + ")";
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(kernelId);
      deploymentAdditions = new HashMap();
      deploymentAdditions.put("SubDeployments", SubDeploymentMBean.class);
   }

   public class RPActivateHandler implements RPService {
      public String getName() {
         return "[RPActivateHandler for JMSModule " + ModuleCoordinator.this.moduleName.toString() + "]";
      }

      public int getType() {
         return 5;
      }

      public void rpDeactivate(String groupName) throws RPException {
      }

      public void rpActivate(String groupName) throws RPException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: RPActivateHandler.rpActivate called in " + ModuleCoordinator.this.moduleName);
         }

         DomainMBean domain = ModuleCoordinator.this.getDomainFromAppCtx();
         synchronized(this) {
            try {
               if (ModuleCoordinator.this.isDestroyed) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("RPActivateHandler: to initialize the whole module " + ModuleCoordinator.this.moduleName);
                  }

                  int step = 0;

                  try {
                     ModuleCoordinator.this.initializeModule(ModuleCoordinator.this.appCtx, domain);
                     ++step;
                     ModuleCoordinator.this.prepare(domain);
                     ++step;
                     ModuleCoordinator.this.activate(domain);
                     ++step;
                     return;
                  } catch (Throwable var12) {
                     try {
                        if (step >= 2) {
                           try {
                              ModuleCoordinator.this.deactivate(domain);
                           } catch (Throwable var10) {
                           }
                        }

                        if (step >= 1) {
                           try {
                              ModuleCoordinator.this.unprepare(domain);
                           } catch (Throwable var9) {
                           }
                        }

                        ModuleCoordinator.this.destroy(domain);
                     } catch (Throwable var11) {
                     }

                     return;
                  }
               }

               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("RPActivateHandler: to restart the affected entities in module " + ModuleCoordinator.this.moduleName);
               }

               if (ModuleCoordinator.getRPManager() != null) {
                  Set services = ModuleCoordinator.getRPManager().getServicesFromGroup(groupName);
                  if (services == null || services.isEmpty()) {
                     return;
                  }

                  Set jmsservers = new HashSet();
                  Iterator var6 = services.iterator();

                  while(var6.hasNext()) {
                     RPService oneService = (RPService)var6.next();
                     if (oneService.getType() == 2) {
                        jmsservers.add(oneService.getName());
                     }
                  }

                  if (jmsservers.isEmpty()) {
                     return;
                  }

                  ModuleCoordinator.this.activateForRP(ModuleCoordinator.this.getRuntimeDomain(), jmsservers);
                  return;
               }

               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("Error to get RPManager, do nothing.");
               }
            } catch (Exception var13) {
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("Error in RPActivateHandler.pActivate " + ModuleCoordinator.this.moduleName, var13);
               }

               return;
            }

         }
      }

      public int getOrder() {
         return 1100;
      }
   }

   public class EARSubDeploymentListener {
      public void startAddSubDeployments(SubDeploymentMBean toAdd) throws BeanUpdateRejectedException {
         if (ModuleCoordinator.this.earModuleName != null) {
            if (ModuleCoordinator.this.earModuleName.equals(toAdd.getName())) {
               ModuleCoordinator.this.hasUpdate = true;
            }

         }
      }

      public void finishAddSubDeployments(SubDeploymentMBean toAdd, boolean isActivate) {
         if (isActivate && ModuleCoordinator.this.isActive) {
            if (ModuleCoordinator.this.earModuleName != null) {
               if (ModuleCoordinator.this.earModuleName.equals(toAdd.getName())) {
                  ModuleCoordinator.this.activateSubDeploymentListener(toAdd);
                  ModuleCoordinator.this.targetingAdditionsListener = new GenericBeanListener(toAdd, ModuleCoordinator.this, (Map)null, ModuleCoordinator.deploymentAdditions, true);
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("INFO: EAR sub-deployment " + ModuleCoordinator.this.earModuleName + " in module " + ModuleCoordinator.this.moduleName + " was added");
                  }

               }
            }
         } else {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("INFO: Not listening on sub-deployment " + toAdd.getName() + " either because the module is shutdown(" + !ModuleCoordinator.this.isActive + ") or this is a rollback which included an addition of a subdeployment(" + !isActivate + ") in module " + this + " of name " + ModuleCoordinator.this.moduleName);
            }

         }
      }

      public void startRemoveSubDeployments(SubDeploymentMBean toRemove) throws BeanUpdateRejectedException {
         if (ModuleCoordinator.this.earModuleName != null) {
            if (ModuleCoordinator.this.earModuleName.equals(toRemove.getName())) {
               ModuleCoordinator.this.hasUpdate = true;
            }

         }
      }

      public void finishRemoveSubDeployments(SubDeploymentMBean toRemove, boolean isActivate) {
         if (isActivate) {
            if (ModuleCoordinator.this.earModuleName != null) {
               if (ModuleCoordinator.this.earModuleName.equals(toRemove.getName())) {
                  ModuleCoordinator.this.deactivateSubDeploymentListener();
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("INFO: EAR sub-deployment " + ModuleCoordinator.this.earModuleName + " in module " + ModuleCoordinator.this.moduleName + " was removed");
                  }

               }
            }
         }
      }
   }

   public class ASMDeactivateHandler implements Migratable {
      public void migratableInitialize() {
      }

      public void migratableActivate() throws MigrationException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Got migratableActivate for " + this);
         }

      }

      public void migratableDeactivate() throws MigrationException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Got migratableDeactivate for " + this);
         }

         if (!ModuleCoordinator.this.isDestroyed) {
            ModuleCoordinator.this.migratableUpdate();
         }

      }

      public String getName() {
         return "[ASMDeactivateHandler for JMSModule " + ModuleCoordinator.this.moduleName.toString() + "]";
      }

      public String toString() {
         return "[ASMDeactivateHandler for JMSModule " + ModuleCoordinator.this.moduleName.toString() + "]";
      }

      public int getOrder() {
         return -1901;
      }
   }

   public class ASMActivateHandler implements Migratable {
      public void migratableInitialize() {
      }

      public void migratableActivate() throws MigrationException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Got migratableActivate for " + this);
         }

         DomainMBean domain = ModuleCoordinator.this.getDomainFromAppCtx();
         synchronized(this) {
            if (ModuleCoordinator.this.initFailed) {
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("ASMActivateHandler: to initialize the whole module " + ModuleCoordinator.this.moduleName);
               }

               int step = 0;

               try {
                  ModuleCoordinator.this.initializeModule(ModuleCoordinator.this.appCtx, domain);
                  ++step;
                  ModuleCoordinator.this.prepare();
                  ++step;
                  ModuleCoordinator.this.activate();
                  ++step;
                  ModuleCoordinator.this.initFailed = false;
               } catch (Throwable var10) {
                  try {
                     if (step >= 2) {
                        try {
                           ModuleCoordinator.this.deactivate();
                        } catch (Throwable var8) {
                        }
                     }

                     if (step >= 1) {
                        try {
                           ModuleCoordinator.this.unprepare();
                        } catch (Throwable var7) {
                        }
                     }

                     ModuleCoordinator.this.destroy(domain);
                  } catch (Throwable var9) {
                  }

                  return;
               }

               return;
            } else if (!ModuleCoordinator.this.isDestroyed) {
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("ASMActivateHandler: to update the module " + ModuleCoordinator.this.moduleName + ",isDestroyed=" + ModuleCoordinator.this.isDestroyed);
               }

               ModuleCoordinator.this.migratableUpdate();
            }

         }
      }

      public void migratableDeactivate() throws MigrationException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Got migratableDeactivate for " + this);
         }

      }

      public String getName() {
         return ModuleCoordinator.this.moduleName.getFullyQualifiedModuleName();
      }

      public String toString() {
         return "[ASMActivateHandler for JMSModule " + ModuleCoordinator.this.moduleName.toString() + "]";
      }

      public int getOrder() {
         return 2147483646;
      }
   }

   public class JMSTargetsListenerImpl implements JMSTargetsListener {
      public void prepareUpdate(DomainMBean domain, TargetMBean target, int action, boolean migrationInProgress) throws BeanUpdateRejectedException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: jmsListener prepareUpdate called in " + ModuleCoordinator.this.moduleName);
         }

         synchronized(this) {
            if (!migrationInProgress) {
               ModuleCoordinator.this.hasUpdate = true;
            } else {
               ModuleCoordinator.this.hasMigratableUpdate = true;
            }

            ModuleCoordinator.this.hasMigratableUpdateDomain = domain;
            if (ModuleCoordinator.this.targeter != null) {
               synchronized(ModuleCoordinator.this.targeter) {
                  ModuleCoordinator.this.targeter.setHasUpdate(true);
                  ModuleCoordinator.this.targeter.setUpdatedTargetMBean(target);
                  ModuleCoordinator.this.targeter.setUpdateAction(action);
               }
            }

         }
      }

      public void rollbackUpdate() {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: jmsListener rollbackUpdate called in " + ModuleCoordinator.this.moduleName);
         }

      }

      public void activateUpdate() {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: jmsListener activateUpdate called in " + ModuleCoordinator.this.moduleName);
         }

      }
   }

   private class DeploymentListener implements BeanUpdateListener {
      private DeploymentListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: deploymentListener prepareUpdate called in " + ModuleCoordinator.this.moduleName);
         }

         ModuleCoordinator.this.hasUpdate = true;
      }

      public void activateUpdate(BeanUpdateEvent event) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: deploymentListener activateUpdate called in " + ModuleCoordinator.this.moduleName);
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: deploymentListener rollbackUpdate called in " + ModuleCoordinator.this.moduleName);
         }

      }

      // $FF: synthetic method
      DeploymentListener(Object x1) {
         this();
      }
   }
}
