package weblogic.connector.common;

import com.bea.connector.diagnostic.AdapterType;
import com.bea.connector.diagnostic.InboundAdapterType;
import com.bea.connector.diagnostic.OutboundAdapterType;
import com.bea.connector.diagnostic.WorkManagerType;
import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.integration.utils.InjectionBeanCreator;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.work.WorkException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.configuration.validation.BeanValidator;
import weblogic.connector.deploy.DeployerUtil;
import weblogic.connector.deploy.JNDIHandler;
import weblogic.connector.deploy.RarArchive;
import weblogic.connector.exception.RACommonException;
import weblogic.connector.exception.RAException;
import weblogic.connector.exception.RAInboundException;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.extensions.Suspendable;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.ConnectorUtils;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.RAComplianceChecker;
import weblogic.connector.external.RAComplianceException;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.external.SecurityIdentityInfo;
import weblogic.connector.inbound.RAInboundManager;
import weblogic.connector.lifecycle.BootstrapContext;
import weblogic.connector.monitoring.ConnectorComponentRuntimeMBeanImpl;
import weblogic.connector.monitoring.ServiceRuntimeMBeanImpl;
import weblogic.connector.monitoring.work.ConnectorWorkManagerRuntimeMBeanImpl;
import weblogic.connector.outbound.RAOutboundManager;
import weblogic.connector.security.SecurityHelper;
import weblogic.connector.security.SecurityHelperFactory;
import weblogic.connector.security.SecurityPermissions;
import weblogic.connector.security.layer.AdapterLayer;
import weblogic.connector.security.work.SecurityContextPrincipalMapperImpl;
import weblogic.connector.utils.PartitionUtils;
import weblogic.connector.utils.RuntimeAccessUtils;
import weblogic.connector.work.WorkContextProcessorFactory;
import weblogic.connector.work.WorkContextProcessorFactoryImpl;
import weblogic.connector.work.WorkManager;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.health.HealthState;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorWorkManagerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.container.jca.jaspic.ConnectorCallbackHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.WorkManagerCollection;
import weblogic.work.WorkManagerRuntimeMBeanImpl;
import weblogic.work.WorkManagerService;

public class RAInstanceManager {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String NEW = Debug.getStringNew();
   private final String INITIALIZED = Debug.getStringInitialized();
   private final String PREPARED = Debug.getStringPrepared();
   private final String ACTIVATED = Debug.getStringActivated();
   private final String SUSPENDED = Debug.getStringSuspended();
   private SuspendState suspendState = new SuspendState();
   ConnectorComponentRuntimeMBeanImpl connectorComponentRuntimeMBean = null;
   WorkManagerRuntimeMBean workManagerRuntime = null;
   boolean workManagerRuntimeCreated;
   private ConnectorWorkManagerRuntimeMBeanImpl connectorWorkManagerRuntime;
   private BootstrapContext bootstrapContext = null;
   private ResourceAdapter resourceAdapter = null;
   private RAInboundManager raInboundManager = null;
   protected RAOutboundManager raOutboundManager = null;
   private RAInfo raInfo = null;
   private GenericClassLoader classloader = null;
   private String connectorComponentName = null;
   private String componentURI = null;
   private String partitionName = null;
   private WorkManagerCollection workMgrCollection = null;
   protected RarArchive explodedRar;
   private boolean lateDeploy = false;
   private String state;
   private boolean suspendedBeforeDeactivate;
   private ApplicationContextInternal appCtx;
   private Hashtable adminObjects;
   private final AppDefinedAdminObjectManager appDefinedAdminObjects;
   private String moduleName;
   private String jndiName;
   private AdapterLayer adapterLayer;
   private SecurityHelper securityHelper;
   private List classFinders;
   private RAValidationInfo raValidationInfo;
   private volatile boolean waitingStartVersioningComplete;
   private BeanValidator beanValidator;
   private String oldVersionId;
   private ManagedBeanProducer managedBeanProducer;

   public RAInstanceManager(RAInfo aRAInfo, GenericClassLoader aClassloader, String componentName, String componentURI, RarArchive rar, ApplicationContextInternal appCtx, String moduleName, String oldVersionId, AuthenticatedSubject kernelId, List classFinders) throws RAException, RAComplianceException {
      this.state = this.NEW;
      this.appCtx = null;
      this.adminObjects = null;
      this.waitingStartVersioningComplete = false;
      Debug.enter(this, "Constructor");
      this.oldVersionId = oldVersionId;
      this.classFinders = classFinders;

      try {
         this.securityHelper = SecurityHelperFactory.getInstance();
         this.appCtx = appCtx;
         if (appCtx == null) {
            Debug.throwAssertionError("appCtx == null");
         }

         if (Debug.isWorkEnabled()) {
            Debug.work("Getting work manager collection for app with appId = " + appCtx.getApplicationId());
         }

         this.workMgrCollection = appCtx.getWorkManagerCollection();
         if (this.workMgrCollection == null) {
            Debug.throwAssertionError("appCtx.getWorkManagerCollection() == null ");
         }

         if (aRAInfo == null) {
            Debug.throwAssertionError("RAInfo == null");
         }

         this.setRAInfo(aRAInfo);
         this.classloader = aClassloader;
         this.connectorComponentName = componentName;
         this.componentURI = componentURI;
         this.explodedRar = rar;
         this.moduleName = moduleName;
         this.partitionName = PartitionUtils.getPartitionName();
         Debug.deployment("The resource adapter " + this.getApplicationName() + ":" + moduleName + " is deployed for the partition: name = " + this.partitionName);
         String linkRef;
         if (this.jndiName != null && JNDIHandler.verifyJNDIName(this.jndiName)) {
            Debug.deployment("Failing deployment with duplicate RA JNDI name of " + this.jndiName + " for module " + moduleName);
            linkRef = Debug.getExceptionJndiNameAlreadyBound(this.jndiName);
            throw new RAException(linkRef);
         }

         this.appDefinedAdminObjects = new AppDefinedAdminObjectManager(moduleName, this);
         this.adapterLayer = new AdapterLayer(this, this.securityHelper, kernelId);
         linkRef = this.raInfo.getLinkref();
         if (linkRef != null && linkRef.length() > 0) {
            Debug.deployment("A linkref is being deployed : " + linkRef);
            Debug.logDeprecatedLinkref(this.getModuleName());
            Debug.println((Object)this, (String)"() Check if the base has been deployed");
            RAInstanceManager baseRA = LinkrefManager.getBaseRA(linkRef);
            if (baseRA != null) {
               if (Debug.isDeploymentEnabled()) {
                  Debug.deployment("The base RA for the '" + linkRef + "'link-ref is already deployed; base RA module name = '" + baseRA.getModuleName() + "'");
               }

               this.lateDeploy = false;
               Debug.println((Object)this, (String)"() Update the RAInfo of this linkref with the base raInfo");
               this.raInfo.setBaseRA(baseRA.getRAInfo());
            } else {
               Debug.deployment("The base RA for the '" + linkRef + "'link-ref has not yet been deployed.");
               this.lateDeploy = true;
               Debug.println((Object)this, (String)"() Add the linkref to the linkref manager for future deployment");
               LinkrefManager.addLinkrefRA(this);
               Debug.logRarMarkedForLateDeployment(this.getModuleName());
            }
         }

         this.setupComponentRuntime();
         if (!this.lateDeploy) {
            Debug.println((Object)this, (String)"() initializing the RA object");
            this.initialize();
         }
      } finally {
         Debug.exit(this, "Constructor");
      }

   }

   public synchronized void prepare() throws RAException {
      Debug.enter(this, "prepare()");

      try {
         if (!this.lateDeploy) {
            if (this.state.equals(this.NEW)) {
               String exMsg = Debug.getExceptionPrepareUninitializedRA();
               throw new RAException(exMsg);
            }

            if (this.state.equals(this.INITIALIZED)) {
               Debug.println((Object)this, (String)".prepare() Set the security permissions for the resource adapter");
               SecurityPermissions.setSecurityPermissions(kernelId, this.raInfo);
               this.raOutboundManager = this.createOutboundManager();
               this.state = this.PREPARED;
            }
         }
      } finally {
         Debug.exit(this, "prepare");
      }

   }

   public synchronized void activate() throws RAException, RAComplianceException {
      Debug.enter(this, "activate()");

      try {
         if (!this.lateDeploy) {
            if (this.state.equals(this.NEW) || this.state.equals(this.INITIALIZED)) {
               String exMsg = Debug.getExceptionActivateUnpreparedRA(this.state);
               throw new RAException(exMsg);
            }

            if (this.state.equals(this.PREPARED)) {
               InjectionContainer injectionContainer = this.getInjectionContainer();
               if (injectionContainer != null) {
                  this.adapterLayer.setInjectionBeanCreator(this.createInjectionBeanCreator(injectionContainer));
               }

               try {
                  this.managedBeanProducer = (ManagedBeanProducer)this.adapterLayer.getReference(ManagedBeanProducer.class.getName(), true, this.classloader, kernelId);
               } catch (Throwable var6) {
                  throw new RAException("Unable to create managed bean producer.", var6);
               }

               this.createResourceAdapter(kernelId);
               if (this.suspendedBeforeDeactivate) {
                  String exMsg = Debug.getExceptionActivateSuspendedRA(this.state);
                  throw new RAException(exMsg);
               }

               Debug.println((Object)this, (String)".activate() Call activate on the outbound manager");
               this.raOutboundManager.activate();
               Debug.println((Object)this, (String)".activate() Call activate on the inbound manager");
               this.raInboundManager.activate();
               this.bindAdminObjects();
               if (this.resourceAdapter != null) {
                  WorkManager wm = (WorkManager)((WorkManager)this.bootstrapContext.getWorkManager());
                  wm.acceptDoWorkCalls();
                  this.putRAintoJNDITree(this.jndiName);
               }

               if (this.raInfo.getLinkref() == null || this.raInfo.getLinkref().length() == 0) {
                  Debug.println((Object)this, (String)".activate() Add the base RA to the linkref manager");
                  LinkrefManager.addBaseRA(this);
                  Debug.println((Object)this, (String)".activate() Deploy the dependent linkrefs");
                  LinkrefManager.deployDependentLinkrefs(this);
               }

               this.state = this.ACTIVATED;
            }
         }
      } finally {
         Debug.exit(this, "activate");
      }

   }

   public synchronized void deactivate() throws RAException {
      Debug.enter(this, "deactivate()");
      RAException rae = new RAException();

      try {
         if (!this.lateDeploy && (this.state.equals(this.ACTIVATED) || this.state.equals(this.SUSPENDED))) {
            this.suspendedBeforeDeactivate = this.state.equals(this.SUSPENDED);
            this.removeAdminObjsFromJndi(rae);
            this.removeRAfromJNDITree(rae);
            this.deactivateRAInboundMgr(rae);
            this.deactivateRAOutboundMgr(rae);
            if (!this.lateDeploy) {
               this.rollbackRAInboundMgr(rae);
               this.rollbackWorkMgr(rae);
               this.suspendWorkManager(rae);
               this.releaseLongRunningWorks(rae);
               this.cleanupWorkManagerRuntime();
               this.unsetRASecurity(rae);
               this.stopAdminObjects();
               this.stopAppDefinedAdminObjects();
               this.callStopOnRA(rae);
            }

            this.lateDeploy = false;
            if (this.raInfo.getLinkref() != null && this.raInfo.getLinkref().length() != 0) {
               LinkrefManager.removeLinkrefRA(this, rae);
            } else {
               LinkrefManager.removeBaseRA(this, rae);
            }

            this.state = this.PREPARED;
         }

         if (rae.size() > 0) {
            throw rae;
         }
      } finally {
         this.resourceAdapter = null;
         this.managedBeanProducer = null;
         Debug.exit(this, "deactivate");
      }

   }

   private void deactivateRAInboundMgr(RAException rae) {
      Debug.println((Object)this, (String)".deactivateRAInboundMgr() Call deactivate on the inbound manager");

      try {
         if (this.raInboundManager != null) {
            this.raInboundManager.deactivate();
         }
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   private void deactivateRAOutboundMgr(RAException rae) {
      Debug.println((Object)this, (String)".deactivateRAOutboundMgr() Call deactivate on the outbound manager");

      try {
         if (this.raOutboundManager != null) {
            this.raOutboundManager.deactivate();
         }
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   protected void checkForUnPrepare() throws RAException {
      this.appDefinedAdminObjects.checkForUnPrepare();
      this.raOutboundManager.checkForUnPrepare();
   }

   public synchronized void rollback() throws RAException {
      Debug.enter(this, "rollback()");
      RAException rae = new RAException();

      try {
         if (this.state.equals(this.ACTIVATED)) {
            String exMsg = Debug.getExceptionRollbackActivatedRA();
            throw new RAException(exMsg);
         }

         if (this.state.equals(this.PREPARED)) {
            this.checkForUnPrepare();
            this.unregisterRAInstance(rae);
            this.removeWLDFLogAccessor(rae);
            this.state = this.INITIALIZED;
            if (rae.size() > 0) {
               throw rae;
            }
         }
      } finally {
         this.cleanupWorkManagerRuntime();
         Debug.exit(this, "rollback");
      }

   }

   private void removeWLDFLogAccessor(RAException rae) {
      AccessRuntimeMBean accessMBean = RuntimeAccessUtils.getWLDFAccessRuntimeMBean();
      if (accessMBean != null) {
         Iterator var3 = this.raInfo.getOutboundInfos().iterator();

         while(var3.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)var3.next();
            String logFilename = outboundInfo.getLogFilename();
            if (logFilename != null && logFilename.length() > 0) {
               String logKey = PartitionUtils.appendPartitionName(outboundInfo.getJndiName(), this.partitionName);

               try {
                  accessMBean.removeAccessor(String.format("%s/%s", "ConnectorLog", logKey));
               } catch (ManagementException var8) {
                  rae.addError(var8);
               }
            }
         }

      }
   }

   private void rollbackRAInboundMgr(RAException rae) {
      Debug.println((Object)this, (String)".rollbackRAInboundMgr() Call rollback on the inbound manager");

      try {
         if (this.raInboundManager != null) {
            this.raInboundManager.rollback();
         }
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   private void rollbackWorkMgr(RAException rae) {
      Debug.work("RAInstanceManager.rollbackWorkMgr() Rollback work from the work manager");

      try {
         if (this.workMgrCollection != null) {
            this.workMgrCollection.removeModuleEntries(this.moduleName);
         }
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   private void unregisterRAInstance(RAException rae) {
      Debug.println((Object)this, (String)".unregisterRAInstance() Unregister this RAInstanceManager");

      try {
         RACollectionManager.unregister(this);
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   private void unsetRASecurity(RAException rae) {
      Debug.println((Object)this, (String)".unsetRASecurity() unset the security permissions for the resource adapter");

      try {
         SecurityPermissions.unSetSecurityPermissions(kernelId, this.raInfo);
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   public void stop() throws RAException {
      Debug.enter(this, "stop()");
      Utils.startManagement();

      try {
         this.shutdownRA();
      } finally {
         Utils.stopManagement();
         Debug.exit(this, "stop()");
      }

   }

   private void shutdownRA() throws RAException {
      RAException rae = new RAException();
      this.stopRAOutboundMgr(rae);
      this.stopRAInboundMgr(rae);
      this.suspendWorkManager(rae);
      this.releaseLongRunningWorks(rae);
      this.stopAdminObjects();
      this.stopAppDefinedAdminObjects();
      this.appDefinedAdminObjects.shutdown();
      this.callStopOnRA(rae);
      if (rae.size() > 0) {
         throw rae;
      }
   }

   protected void stopAdminObjects() {
      if (this.adminObjects != null) {
         HashSet adminObjMetaInfos = new HashSet();
         Enumeration enumer = this.adminObjects.elements();

         while(enumer.hasMoreElements()) {
            adminObjMetaInfos.add(enumer.nextElement());
         }

         Iterator var3 = adminObjMetaInfos.iterator();

         while(var3.hasNext()) {
            AdminObjectMetaInfo adminObjMetaInfo = (AdminObjectMetaInfo)var3.next();

            try {
               this.removeAdminObject(adminObjMetaInfo.adminInfo);
            } catch (RAException var6) {
               ConnectorLogger.logRemovingAdminObjectException(adminObjMetaInfo.adminInfo, var6);
            }
         }
      }

   }

   void stopAppDefinedAdminObjects() {
      this.appDefinedAdminObjects.deactivateResources();
   }

   void doStopAppDefinedAdminObject(UniversalResourceKey key, AdminObjectMetaInfo adminObjMetaInfo) {
      if (adminObjMetaInfo.adminObj != null) {
         JNDIHandler.unexportObject(adminObjMetaInfo.adminObj, key.getJndi());
         JNDIHandler.removeAdminObj(key);
         this.adapterLayer.invokePreDestroy(adminObjMetaInfo.adminObj, "admin object");
         adminObjMetaInfo.adminObj = null;
      }

   }

   private void stopRAOutboundMgr(RAException rae) {
      if (this.raOutboundManager != null) {
         try {
            this.raOutboundManager.stop();
         } catch (Throwable var3) {
            rae.addError(var3);
         }
      }

   }

   private void stopRAInboundMgr(RAException rae) {
      if (this.raInboundManager != null) {
         try {
            this.raInboundManager.stop();
         } catch (Throwable var3) {
            rae.addError(var3);
         }
      }

   }

   private void suspendWorkManager(RAException rae) {
      if (this.bootstrapContext != null) {
         try {
            WorkManager wm = (WorkManager)((WorkManager)this.bootstrapContext.getWorkManager());
            wm.suspend();
         } catch (Throwable var3) {
            rae.addError(var3);
         }
      }

   }

   private void releaseLongRunningWorks(RAException rae) {
      if (this.bootstrapContext != null) {
         try {
            WorkManager wm = (WorkManager)((WorkManager)this.bootstrapContext.getWorkManager());
            wm.getLongRunningWorkManager().cleanup();
         } catch (Throwable var3) {
            rae.addError(var3);
         }
      }

   }

   private void callStopOnRA(RAException rae) {
      ResourceAdapter localResourceAdapter = this.getResourceAdapter();
      if (localResourceAdapter != null) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         try {
            if (Debug.isRALifecycleEnabled()) {
               Debug.raLifecycle("Calling stop on() the ResouceAdapter JavaBean: " + this.adapterLayer.toString(localResourceAdapter, kernelId) + " in module '" + this.moduleName + "' having JNDI name '" + this.jndiName + "'");
            }
         } catch (Throwable var6) {
            rae.addError(var6);
         }

         try {
            this.adapterLayer.stop(localResourceAdapter, kernelId);
         } catch (Throwable var5) {
            rae.addError(var5);
         }

         this.adapterLayer.invokePreDestroy(localResourceAdapter, "resource adapter");
      }

   }

   public void halt() throws RAException {
      Debug.enter(this, "halt()");
      Utils.startManagement();

      try {
         this.shutdownRA();
      } finally {
         Utils.stopManagement();
         Debug.exit(this, "halt()");
      }

   }

   public void suspend(int type, Properties props) throws RAException {
      Debug.enter(this, "suspend( " + type + ", props )");
      Utils.startManagement();

      try {
         RAException raException = null;

         try {
            Debug.println((Object)this, (String)".suspend() Suspending the resource adapter");
            this.suspendResourceAdapter(type, props);
            this.state = this.SUSPENDED;
         } catch (ResourceException var12) {
            raException = Utils.consolidateException(raException, var12);
         }

         if (this.matches(type, 2)) {
            try {
               Debug.println((Object)this, (String)".suspend() Suspending all outbound");
               if (this.raOutboundManager != null) {
                  this.raOutboundManager.suspend();
                  this.suspendState.suspendOutbound();
               }
            } catch (RAException var11) {
               raException = Utils.consolidateException(raException, var11);
            }
         }

         if (this.matches(type, 1)) {
            try {
               Debug.println((Object)this, (String)".suspend() Suspending all inbound");
               if (this.raInboundManager != null) {
                  this.raInboundManager.suspend(props);
                  this.suspendState.suspendInbound();
               }
            } catch (RAException var10) {
               raException = Utils.consolidateException(raException, var10);
            }
         }

         if (this.matches(type, 4) && this.bootstrapContext != null) {
            ((WorkManager)this.bootstrapContext.getWorkManager()).suspend();
            this.suspendState.suspendWork();
         }

         if (raException != null) {
            throw raException;
         }
      } finally {
         Utils.stopManagement();
         Debug.exit(this, "suspend( " + type + ", props )");
      }

   }

   public void resume(int type, Properties props) throws RAException {
      Debug.enter(this, "resume( " + type + ", props )");
      Utils.startManagement();

      try {
         RAException raException = null;

         try {
            this.resumeResourceAdapter(type, props);
         } catch (ResourceException var12) {
            raException = Utils.consolidateException(raException, var12);
         }

         if (this.matches(type, 2)) {
            try {
               if (this.raOutboundManager != null) {
                  this.raOutboundManager.resume();
                  this.suspendState.resumeOutbound();
               }
            } catch (RAException var11) {
               raException = Utils.consolidateException(raException, var11);
            }
         }

         if (this.matches(type, 1)) {
            try {
               if (this.raInboundManager != null) {
                  this.raInboundManager.resume(props);
                  this.suspendState.resumeInbound();
               }
            } catch (RAException var10) {
               raException = Utils.consolidateException(raException, var10);
            }
         }

         if (this.matches(type, 4) && this.bootstrapContext != null) {
            ((WorkManager)this.bootstrapContext.getWorkManager()).resume();
            this.suspendState.resumeWork();
         }

         if (this.suspendState.isAllResumed()) {
            this.state = this.ACTIVATED;
         }

         if (raException != null) {
            throw raException;
         }
      } finally {
         Utils.stopManagement();
         Debug.exit(this, "resume( " + type + ", props )");
      }

   }

   public String toString() {
      return "Patition = " + this.partitionName + ", ModuleName = " + this.moduleName + ", jndiName = " + this.jndiName + ", state = " + this.state + "\nRAInfo = " + this.raInfo;
   }

   synchronized void initialize() throws RAException, RAComplianceException {
      Debug.enter(this, "initialize()");
      boolean var11 = false;

      String msg;
      try {
         var11 = true;
         if (this.state.equals(this.NEW)) {
            this.raValidationInfo = this.createRAComplianceChecker().validate(this.explodedRar.getOriginalRarFilename(), this.raInfo, this.classloader);

            try {
               Debug.println((Object)this, (String)".initialize() Register with the RAMAnager");
               RACollectionManager.register(this);
            } catch (ManagementException var15) {
               msg = ConnectorLogger.logRegisterConnectorComponentRuntimeMbeanFailed(this.toString());
               throw new RAException(msg, var15);
            }

            try {
               Debug.println((Object)this, (String)".initialize() Creating native lib");
               DeployerUtil.createNativeLibDir(this.explodedRar.getVirtualJarFile(), this.raInfo, this.appCtx);
            } catch (DeploymentException var14) {
               msg = Debug.getExceptionCreateNativeLib();
               throw new RAException(msg, var14);
            }

            this.beanValidator = new BeanValidator(this.explodedRar.getVirtualJarFile());
            this.state = this.INITIALIZED;
            var11 = false;
         } else {
            var11 = false;
         }
      } finally {
         if (var11) {
            if (!this.state.equals(this.INITIALIZED)) {
               Debug.println((Object)this, (String)".initialize() UnRegister with the RAMAnager");

               try {
                  RACollectionManager.unregister(this);
               } catch (ManagementException var12) {
                  String msg = ConnectorLogger.logFailedToUnregisterModuleRuntimeMBean(this.toString());
                  Debug.deployment(msg, var12);
               }
            }

            Debug.exit(this, "initialize");
         }
      }

      if (!this.state.equals(this.INITIALIZED)) {
         Debug.println((Object)this, (String)".initialize() UnRegister with the RAMAnager");

         try {
            RACollectionManager.unregister(this);
         } catch (ManagementException var13) {
            msg = ConnectorLogger.logFailedToUnregisterModuleRuntimeMBean(this.toString());
            Debug.deployment(msg, var13);
         }
      }

      Debug.exit(this, "initialize");
   }

   private void createResourceAdapter(AuthenticatedSubject kernelId) throws RAException, RAComplianceException {
      String resourceAdapterClassName = this.raInfo.getRAClass();
      WorkManagerService moduleWorkManagerService = null;
      String raJndiName;
      if (resourceAdapterClassName != null) {
         try {
            moduleWorkManagerService = this.setupWorkManagerAndBootstrapContext();
         } catch (WorkException var9) {
            raJndiName = Debug.getExceptionCreateBootstrap(this.getModuleName(), "");
            throw new RAException(raJndiName, var9);
         } catch (Throwable var10) {
            raJndiName = Debug.getExceptionStartRA(this.getModuleName(), "");
            throw new RAException(raJndiName, var10);
         }

         this.resourceAdapter = this.createAndInitAdapterBean(resourceAdapterClassName, this.raInfo.getRAConfigProps().values());
         WorkManager connectorWorkManager = this.bootstrapContext.getConnectorWorkManager();
         connectorWorkManager.initResourceAdapter(this.resourceAdapter);
         if (this.managedBeanProducer != null) {
            this.managedBeanProducer.setResourceAdapter(this.resourceAdapter);
         }
      }

      if (this.resourceAdapter != null) {
         try {
            if (this.isVersioned()) {
               RAInstanceManager oldRAIM = RACollectionManager.getRAInstanceManagerByAppName(this.appCtx.getApplicationName(), this.oldVersionId);
               if (!Utils.isRAVersionable(this, oldRAIM)) {
                  raJndiName = Debug.getExceptionAdapterNotVersionable();
                  throw new RAException(raJndiName);
               }

               if (oldRAIM != null) {
                  raJndiName = oldRAIM.getJndiName();
                  ConnectorLogger.logDeploySideBySide(raJndiName, oldRAIM.getVersionId(), this.getVersionId());
                  Debug.raLifecycle("Beginning side-by-side versioning of resource adapter with JNDI name = " + raJndiName + " by calling init() on new version");
                  this.adapterLayer.init((Suspendable)this.resourceAdapter, oldRAIM.getResourceAdapter(), (Properties)null, kernelId);
                  Debug.raLifecycle("Continuing side-by-side versioning of resource adapter with JNDI name = " + raJndiName + " by calling startVersioning() on old version");
                  oldRAIM.waitingStartVersioningComplete = true;
                  this.adapterLayer.startVersioning((Suspendable)oldRAIM.getResourceAdapter(), this.resourceAdapter, (Properties)null, kernelId);
                  Debug.raLifecycle("Completed side-by-side versioning of resource adapter with JNDI name = " + raJndiName);
               } else {
                  ConnectorLogger.logSkipSideBySide();
               }
            } else {
               ConnectorLogger.logAppNotSideBySide();
            }

            Debug.raLifecycle("Calling start() on the ResourceAdapter bean for " + this.adapterLayer.toString(this.resourceAdapter, kernelId));
            this.adapterLayer.start(this.resourceAdapter, this.bootstrapContext, kernelId);
         } catch (ResourceAdapterInternalException var6) {
            raJndiName = Debug.getExceptionStartRA(this.getModuleName(), "");
            throw new RAException(raJndiName, var6);
         } catch (ResourceException var7) {
            raJndiName = Debug.getExceptionVersionRA();
            throw new RAException(raJndiName, var7);
         } catch (Throwable var8) {
            raJndiName = Debug.getExceptionStartRA(this.getModuleName(), "");
            throw new RAException(raJndiName, var8);
         }
      }

      this.setupWorkManagerRuntime(moduleWorkManagerService);
      Debug.println((Object)this, (String)".initialize() Create the RAOutboundManager");
      this.raOutboundManager.createConnectionFactorys();
      Debug.println((Object)this, (String)".initialize() Create the RAInboundManager");
      this.raInboundManager = this.createInboundManager();
      this.raInboundManager.setupRuntimes(this.connectorComponentRuntimeMBean);
      this.createAdminObjects(kernelId);
      this.createAppDefinedAdminObjects();
      RACollectionManager.updateCounts(this);
   }

   public Class loadClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
      return this.adapterLayer.loadClass(classLoader, className, kernelId);
   }

   public ResourceAdapter createAndInitAdapterBean(String resourceAdapterClassName, Collection propertyValues) throws RAException {
      ResourceAdapter resourceAdapter;
      try {
         Debug.raLifecycle("Creating the ResourceAdapter JavaBean: " + resourceAdapterClassName);
         resourceAdapter = (ResourceAdapter)this.adapterLayer.createInstance(resourceAdapterClassName, true, this.classloader, kernelId);
         Utils.setProperties(this, resourceAdapter, propertyValues, this.raValidationInfo.getRAPropSetterTable());
         this.adapterLayer.invokePostConstruct(resourceAdapter);
      } catch (Throwable var5) {
         throw new RAException("Failed to create/init Resource Adapter bean " + resourceAdapterClassName, var5);
      }

      this.beanValidator.validate(resourceAdapter, "ResourceAdapter module '" + this.getModuleName() + "' with JNDI '" + this.getJndiName() + "'");
      return resourceAdapter;
   }

   private void createAdminObjects(AuthenticatedSubject kernelId) throws RAException {
      List adminBeanList = this.raInfo.getAdminObjs();
      if (adminBeanList != null) {
         Iterator var3 = adminBeanList.iterator();

         while(var3.hasNext()) {
            Object anAdminBeanList = var3.next();
            this.createAdminObject((AdminObjInfo)anAdminBeanList, kernelId);
         }
      }

   }

   void createAppDefinedAdminObjects() {
      this.appDefinedAdminObjects.activateResources();
   }

   private void verifyAdminJNDIAndSaveObj(Object adminObj, AdminObjInfo adminInfo, String versionId) throws RAException {
      String key = adminInfo.getKey();
      Debug.println((Object)this, (String)(".verifyAdminJNDIAndSaveObj() " + this.adapterLayer.toString(adminObj, kernelId) + " JNDI name '" + key + "' and versionId = " + versionId));
      boolean jndiNameAlreadyBound = JNDIHandler.isJndiNameBound(key) || this.adminObjects != null && this.adminObjects.containsKey(key);
      if (jndiNameAlreadyBound) {
         String exMsg = Debug.getExceptionJndiNameAlreadyBound(key);
         throw new RAException(exMsg);
      } else {
         if (this.adminObjects == null) {
            this.adminObjects = new Hashtable();
         }

         this.adminObjects.put(key, new AdminObjectMetaInfo(adminObj, versionId, adminInfo));
      }
   }

   private void bindAdminObjects() throws RACommonException {
      if (this.adminObjects != null && this.adminObjects.size() > 0) {
         Iterator var1 = this.adminObjects.keySet().iterator();

         while(var1.hasNext()) {
            String jndiName = (String)var1.next();
            this.bindAdminObject(jndiName);
         }
      }

   }

   private void suspendResourceAdapter(int type, Properties props) throws ResourceException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (this.resourceAdapter != null && this.resourceAdapter instanceof Suspendable && this.adapterLayer.supportsSuspend((Suspendable)this.resourceAdapter, type, kernelId)) {
         Debug.raLifecycle("Calling suspend() on the ResourceAdapter JavaBean: " + this.adapterLayer.toString(this.resourceAdapter, kernelId));
         this.adapterLayer.suspend((Suspendable)this.resourceAdapter, type, props, kernelId);
      } else if (this.resourceAdapter != null) {
         Debug.raLifecycle("Skipping suspend() call ResourceAdapter JavaBean " + this.adapterLayer.toString(this.resourceAdapter, kernelId) + " which does not support type '" + type + "' suspend()");
      }

   }

   private void resumeResourceAdapter(int type, Properties props) throws ResourceException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (this.resourceAdapter != null && this.resourceAdapter instanceof Suspendable && this.adapterLayer.supportsSuspend((Suspendable)this.resourceAdapter, type, kernelId)) {
         Debug.raLifecycle("Calling resume() on the ResourceAdapter JavaBean: " + this.resourceAdapter);
         ((Suspendable)this.resourceAdapter).resume(type, props);
      } else if (this.resourceAdapter != null) {
         Debug.raLifecycle("Skipping resume() call ResourceAdapter JavaBean " + this.adapterLayer.toString(this.resourceAdapter, kernelId) + " which does not support type " + type + " suspend()/resume()");
      }

   }

   private void putRAintoJNDITree(String jndiName) throws RAException {
      String versionId = this.getVersionId();
      Debug.deployment("Module '" + this.moduleName + "' binding RA with JNDI name '" + jndiName + "' and versionId = " + versionId);
      if (jndiName != null && !jndiName.equals("")) {
         JNDIHandler.bindRA(jndiName, this.resourceAdapter, versionId);
      } else if (this.raInboundManager.isInboundRA()) {
         Debug.logNoAdapterJNDInameSetForInboundRA(this.moduleName, this.appCtx.getApplicationName());
      }

   }

   private void removeRAfromJNDITree(RAException rae) {
      Debug.println((Object)this, (String)".removeRAfromJNDITree() Remove RA from JNDI tree");

      try {
         String versionId = this.getVersionId();
         Debug.deployment("Module '" + this.moduleName + "' unbinding RA with JNDI name '" + this.jndiName + "' and versionId = " + versionId);
         JNDIHandler.unbindRA(this.getJndiName(), this.resourceAdapter, versionId);
      } catch (Throwable var3) {
         rae.addError(var3);
      }

   }

   private boolean matches(int x, int y) {
      return (x & y) > 0;
   }

   private void removeAdminObjsFromJndi(RAException rae) {
      Debug.println((Object)this, (String)".removeAdminObjsFromJndi() Remove administered objects from JNDI tree");
      if (this.raInfo == null) {
         Debug.throwAssertionError("RAInfo is null");
      }

      if (this.adminObjects != null) {
         Iterator var2 = this.adminObjects.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry adminAndVersionEntry = (Map.Entry)var2.next();

            try {
               JNDIHandler.unbindAdminObj(((AdminObjectMetaInfo)adminAndVersionEntry.getValue()).adminInfo, this);
            } catch (Exception var5) {
               rae.add(var5);
            }
         }
      }

   }

   public void removeAdminObject(AdminObjInfo adminInfo) throws RAException {
      AdminObjectMetaInfo adminObjMetaInfo = null;

      try {
         adminObjMetaInfo = (AdminObjectMetaInfo)this.adminObjects.remove(adminInfo.getKey());
         JNDIHandler.unbindAdminObj(adminInfo, this);
      } catch (Throwable var7) {
         throw new RAException(var7);
      } finally {
         if (adminObjMetaInfo != null) {
            this.adapterLayer.invokePreDestroy(adminObjMetaInfo.adminObj, "admin object");
         }

      }

   }

   public void setLateDeploy(boolean flag) {
      this.lateDeploy = flag;
   }

   public RAOutboundManager getRAOutboundManager() {
      return this.raOutboundManager;
   }

   public RAInboundManager getRAInboundManager() {
      return this.raInboundManager;
   }

   public ResourceAdapter getResourceAdapter() {
      return this.resourceAdapter;
   }

   public RAInfo getRAInfo() {
      return this.raInfo;
   }

   public ClassLoader getClassloader() {
      return this.classloader;
   }

   public ConnectorComponentMBean getConnectorComponentMBean() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ApplicationMBean applicationMBean = domain.lookupApplication(this.getApplicationId());
      return applicationMBean == null ? null : applicationMBean.lookupConnectorComponent(this.connectorComponentName);
   }

   public RarArchive getRarArchive() {
      return this.explodedRar;
   }

   public int getAvailableConnectionPoolsCount() {
      return this.raOutboundManager != null ? this.raOutboundManager.getAvailableConnetionPoolsCount() : 0;
   }

   public ConnectorComponentRuntimeMBean getConnectorComponentRuntimeMBean() {
      return this.connectorComponentRuntimeMBean;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getJndiName() {
      return this.jndiName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getJndiNameWithPartitionName() {
      return PartitionUtils.appendPartitionName(this.jndiName, this.partitionName);
   }

   public synchronized boolean isActivated() {
      return this.state.equals(this.ACTIVATED);
   }

   public boolean isSuspended() {
      return this.state.equals(this.SUSPENDED);
   }

   AdapterType getXMLBean(ConnectorDiagnosticImageSource src) {
      AdapterType adapterXBean = AdapterType.Factory.newInstance();
      adapterXBean.setPartitionName(this.partitionName);
      adapterXBean.setJndiName(this.getRAInfo().getJndiName());
      adapterXBean.setState(this.getState());
      adapterXBean.addNewHealth();
      HealthState adapterHealth = this.getConnectorComponentRuntimeMBean().getHealthState();
      if (adapterHealth.getReasonCode() != null) {
         adapterXBean.getHealth().setReasonArray(adapterHealth.getReasonCode());
      }

      boolean timedout = src != null ? src.timedout() : false;
      if (timedout) {
         return adapterXBean;
      } else {
         OutboundAdapterType[] outboundXBeans = this.getRAOutboundManager().getXMLBeans(src);
         adapterXBean.setOutboundAdapterArray(outboundXBeans);
         InboundAdapterType[] inboundXBeans = this.getRAInboundManager().getXMLBeans(src);
         adapterXBean.setInboundAdapterArray(inboundXBeans);
         WorkManagerType wmXBean = WorkManagerType.Factory.newInstance();
         wmXBean.setWorkManagerName(this.jndiName);
         adapterXBean.setWorkManager(wmXBean);
         return adapterXBean;
      }
   }

   public String getState() {
      return this.state;
   }

   public int getSuspendedState() {
      return this.suspendState.getSuspendState();
   }

   public String getVersionId() {
      return ApplicationVersionUtils.getVersionId(this.appCtx.getApplicationId());
   }

   public String getActiveVersion() {
      String activeVersion = null;
      String version = ApplicationVersionUtils.getVersionId(this.appCtx.getApplicationId());
      boolean versioned = version != null && version.length() > 0;
      if (versioned) {
         activeVersion = ApplicationUtils.getActiveVersionId(this.appCtx.getApplicationName());
      }

      return activeVersion;
   }

   public boolean isActiveVersion() {
      boolean isActiveVersion = true;
      String version = ApplicationVersionUtils.getVersionId(this.appCtx.getApplicationId());
      boolean versioned = version != null && version.length() > 0;
      if (versioned) {
         String activeVersion = ApplicationUtils.getActiveVersionId(this.appCtx.getApplicationName());
         if (version.equals(activeVersion)) {
            isActiveVersion = true;
         }
      }

      return isActiveVersion;
   }

   public boolean isVersioned() {
      String version = ApplicationVersionUtils.getVersionId(this.appCtx.getApplicationId());
      boolean versioned = version != null && version.length() > 0;
      return versioned;
   }

   public ApplicationContextInternal getAppContext() {
      return this.appCtx;
   }

   public boolean isGlobalAccessible(String targetAppId) {
      if (this.getRAInfo().isEnableAccessOutsideApp()) {
         return true;
      } else if (!this.appCtx.isEar()) {
         return true;
      } else {
         String appId = this.appCtx.getApplicationId();
         return appId.equalsIgnoreCase(targetAppId);
      }
   }

   public BootstrapContext getBootstrapContext() {
      return this.bootstrapContext;
   }

   public AdapterLayer getAdapterLayer() {
      return this.adapterLayer;
   }

   public void setAdapterLayer(AdapterLayer adapterLayer) {
      this.adapterLayer = adapterLayer;
   }

   public SecurityHelper getSecurityHelper() {
      return this.securityHelper;
   }

   public List getClassFinders() {
      return this.classFinders;
   }

   public void setRAInfo(AuthenticatedSubject kernelId, RAInfo raInfo) {
      if (!this.securityHelper.isKernelIdentity(kernelId)) {
         throw new SecurityException("KernelId is required to call RAInstanceManager.setRAInfo, Subject '" + (kernelId == null ? "<null>" : kernelId.toString()) + "' is not the kernel identity");
      } else {
         this.raInfo = raInfo;
      }
   }

   public void createAdminObject(AdminObjInfo adminInfo, AuthenticatedSubject kernelId) throws RAException {
      try {
         Object adminObj = this.instantiateAdminObject(adminInfo, kernelId);
         this.verifyAdminJNDIAndSaveObj(adminObj, adminInfo, this.getVersionId());
      } catch (Throwable var5) {
         throw new RAException("Failed to create/init Admin Object bean " + adminInfo.getAdminObjClass(), var5);
      }
   }

   public synchronized Reference createAppDefinedConnectionFactory(ConnectionFactoryResourceBean cfBean, String appId, String moduleName, String compName) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      Debug.deployment("Begin createAppDefinedConnectionFactory:\n JNDIName " + cfBean.getName() + "\n AppName " + appName + "\n ModuleName" + moduleName + "\n CompName" + compName);
      if (!this.isGlobalAccessible(appId)) {
         throw new ResourceException(ConnectorLogger.getExceptionAccessDenyOutsideApp(cfBean.getResourceAdapter(), appName, moduleName, compName, cfBean.getName()));
      } else {
         UniversalResourceKey key = new UniversalResourceKey(appName, moduleName, compName, cfBean.getName(), this.getVersionId());
         Reference ref = this.raOutboundManager.createAppDefinedConnectionFactory(cfBean, key);
         Debug.deployment("End   createAppDefinedConnectionFactory:\n JNDIName " + cfBean.getName() + "\n AppName " + appName + "\n ModuleName" + moduleName + "\n CompName" + compName);
         return ref;
      }
   }

   public synchronized Object revokeAppdefinedConnectionFactory(UniversalResourceKey key) throws ResourceException {
      Debug.deployment("Begin destroyAppdefinedConnectionFactory:\n JNDIName " + key.getJndi() + "\n AppName " + key.getDefApp() + "\n ModuleName" + key.getDefModule() + "\n CompName" + key.getDefComp());

      Object var2;
      try {
         var2 = this.raOutboundManager.revokeAppdefinedConnectionFactory(key);
      } finally {
         Debug.deployment("End   destroyAppdefinedConnectionFactory:\n JNDIName " + key.getJndi() + "\n AppName " + key.getDefApp() + "\n ModuleName" + key.getDefModule() + "\n CompName" + key.getDefComp());
      }

      return var2;
   }

   public void destroyAppDefinedConnectionFactory(Object handle) throws ResourceException {
      this.raOutboundManager.destroyAppdefinedConnectionFactory(handle);
   }

   public synchronized Reference createAppDefinedAdminObject(AdministeredObjectBean administeredObjectBean, String appId, String moduleName, String compName) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      String resourceAdapterName = administeredObjectBean.getResourceAdapter();
      if (!this.isGlobalAccessible(appId)) {
         throw new ResourceException(ConnectorLogger.getExceptionAccessDenyOutsideApp(resourceAdapterName, appName, moduleName, compName, administeredObjectBean.getName()));
      } else {
         Debug.deployment("Begin createAppDefinedAdminObject:\n JNDIName " + administeredObjectBean.getName() + "\n AppName " + appName + "\n ModuleName" + moduleName + "\n CompName" + compName);
         UniversalResourceKey key = new UniversalResourceKey(appName, moduleName, compName, administeredObjectBean.getName(), this.getVersionId());
         AppDefinedAdminObjectInfo info = (AppDefinedAdminObjectInfo)this.appDefinedAdminObjects.findCompatibleResource(key, administeredObjectBean);
         Reference ref = null;
         if (info == null) {
            AdminObjInfo adminInfo = this.raInfo.buildAdminObjectInfo(administeredObjectBean, key);
            ref = JNDIHandler.createReference(adminInfo.getAdminObjClass(), key);
            AdminObjectMetaInfo metaInfo = new AdminObjectMetaInfo((Object)null, this.getVersionId(), adminInfo, ref);
            this.appDefinedAdminObjects.createAppDefinedAdminObjectInfo(key, metaInfo, administeredObjectBean);
         }

         Debug.deployment("End   createAppDefinedAdminObject:\n JNDIName " + administeredObjectBean.getName() + "\n AppName " + appName + "\n ModuleName" + moduleName + "\n CompName" + compName);
         return ref;
      }
   }

   void doCreateAppDefinedAdminObject(UniversalResourceKey key, AdminObjectMetaInfo adminObjMetaInfo) throws ResourceException {
      Object adminObj;
      try {
         adminObj = this.instantiateAdminObject(adminObjMetaInfo.adminInfo, kernelId);
         adminObjMetaInfo.adminObj = adminObj;
      } catch (Throwable var6) {
         throw new ResourceException("Failed to create/init Admin Object bean " + adminObjMetaInfo.adminInfo.getAdminObjClass(), var6);
      }

      JNDIHandler.setReference(adminObjMetaInfo.ref, adminObj);
      JNDIHandler.storeAdminObj(adminObj, key);

      try {
         JNDIHandler.exportObject(adminObj, key.getJndi());
      } catch (RemoteException var5) {
         throw new ResourceException(ConnectorLogger.getExceptionExportObject(adminObj.getClass().toString(), this.moduleName, key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi()));
      }
   }

   public synchronized Object revokeAppDefinedAdminObject(UniversalResourceKey key) throws ResourceException {
      Debug.deployment("Begin stopAppDefinedAdminObject:\n JNDIName " + key.getJndi() + "\n AppName " + key.getDefApp() + "\n ModuleName" + key.getDefModule() + "\n CompName" + key.getDefComp());

      AppDefinedObjectInfo var2;
      try {
         var2 = this.appDefinedAdminObjects.revokeResource(key);
      } finally {
         Debug.deployment("End   stopAppDefinedAdminObject:\n JNDIName " + key.getJndi() + "\n AppName " + key.getDefApp() + "\n ModuleName" + key.getDefModule() + "\n CompName" + key.getDefComp());
      }

      return var2;
   }

   public void destroyAppDefinedAdminObject(Object handle) throws ResourceException {
      this.appDefinedAdminObjects.destroyResource((AppDefinedObjectInfo)handle);
   }

   protected Object instantiateAdminObject(AdminObjInfo adminInfo, AuthenticatedSubject kernelId) throws Throwable {
      String adminClass = adminInfo.getAdminObjClass();
      String adminInterface = adminInfo.getInterface();
      Hashtable configProps = adminInfo.getConfigProps();
      Object adminObj = this.adapterLayer.createInstance(adminClass, true, this.classloader, kernelId);
      if (adminObj instanceof ResourceAdapterAssociation && this.resourceAdapter != null) {
         this.adapterLayer.setResourceAdapter((ResourceAdapterAssociation)adminObj, this.resourceAdapter, kernelId);
      }

      if (configProps != null && configProps.size() > 0) {
         Utils.setProperties(this, adminObj, configProps.values(), this.raValidationInfo.getAdminPropSetterTable(adminInterface, adminClass));
      }

      this.adapterLayer.invokePostConstruct(adminObj);
      this.beanValidator.validate(adminObj, "AdminObject '" + adminInfo.getKey() + "'");
      return adminObj;
   }

   public void bindAdminObject(String jndiName) throws RACommonException {
      AdminObjectMetaInfo adminObjMetaInfo = (AdminObjectMetaInfo)this.adminObjects.get(jndiName);
      JNDIHandler.bindAdminObj(adminObjMetaInfo.adminObj, jndiName, adminObjMetaInfo.versionId, this);
   }

   public String getComponentName() {
      return this.connectorComponentName;
   }

   public String getApplicationName() {
      return this.appCtx.getApplicationName();
   }

   public String getApplicationId() {
      return this.appCtx.getApplicationId();
   }

   public void cleanupRuntime() throws ManagementException {
      if (this.connectorComponentRuntimeMBean != null) {
         Utils.unregisterRuntimeMBean(this.connectorComponentRuntimeMBean);
         ConnectorService.getConnectorServiceRuntimeMBean(this.partitionName).removeConnectorRuntime(this.connectorComponentRuntimeMBean);
      }

   }

   public ComponentRuntimeMBean getRuntime() {
      return this.connectorComponentRuntimeMBean;
   }

   public RAValidationInfo getRAValidationInfo() {
      return this.raValidationInfo;
   }

   private String getRuntimeName() {
      String appName = ApplicationVersionUtils.replaceDelimiter(this.appCtx.getPartialApplicationId(false), '_');
      String rtName;
      if (!appName.endsWith(".rar") && !appName.equals(this.connectorComponentName)) {
         rtName = appName + "_" + this.connectorComponentName;
      } else {
         rtName = this.connectorComponentName;
      }

      if (rtName.endsWith(".rar")) {
         rtName = rtName.substring(0, rtName.length() - 4);
      }

      return rtName;
   }

   private void setupWorkManagerRuntime(WorkManagerService workManagerService) throws RAException {
      if (this.workManagerRuntime == null && workManagerService != null) {
         try {
            this.workManagerRuntime = WorkManagerRuntimeMBeanImpl.getWorkManagerRuntime(workManagerService.getDelegate(), (ApplicationRuntimeMBean)this.connectorComponentRuntimeMBean.getParent(), this.connectorComponentRuntimeMBean);
            this.workManagerRuntimeCreated = true;
         } catch (ManagementException var4) {
            throw new RAException(var4);
         }
      }

      if (this.workManagerRuntime != null) {
         this.connectorComponentRuntimeMBean.addWorkManagerRuntime(this.workManagerRuntime);
      }

      if (this.getBootstrapContext() != null) {
         try {
            this.connectorWorkManagerRuntime = this.createConnectorWorkManagerRuntimeMbean(this.getRuntimeName(), this.connectorComponentRuntimeMBean, this.getBootstrapContext().getConnectorWorkManager());
         } catch (ManagementException var3) {
            throw new RAException(var3);
         }
      }

   }

   protected ConnectorWorkManagerRuntimeMBeanImpl createConnectorWorkManagerRuntimeMbean(String name, ConnectorComponentRuntimeMBeanImpl parent, WorkManager wm) throws ManagementException {
      return new ConnectorWorkManagerRuntimeMBeanImpl(name, parent, wm);
   }

   public void cleanupWorkManagerRuntime() {
      if (this.workManagerRuntimeCreated) {
         Utils.unregisterRuntimeMBean((RuntimeMBeanDelegate)this.workManagerRuntime);
         if (this.workManagerRuntime != null) {
         }

         this.workManagerRuntime = null;
         this.workManagerRuntimeCreated = false;
      }

      Utils.unregisterRuntimeMBean(this.connectorWorkManagerRuntime);
      this.connectorWorkManagerRuntime = null;
   }

   public boolean isWorkManagerRuntimeCreated() {
      return this.workManagerRuntimeCreated;
   }

   public ConnectorComponentRuntimeMBean setupComponentRuntime() throws RAException {
      try {
         ServiceRuntimeMBeanImpl connectorServiceRuntime = ConnectorService.getConnectorServiceRuntimeMBean(this.partitionName);
         this.connectorComponentRuntimeMBean = new ConnectorComponentRuntimeMBeanImpl(this.getRuntimeName(), this.componentURI, this, this.appCtx.getRuntime(), connectorServiceRuntime);
         connectorServiceRuntime.addConnectorRuntime(this.connectorComponentRuntimeMBean);
      } catch (ManagementException var2) {
         throw new RAException(var2);
      }

      return this.connectorComponentRuntimeMBean;
   }

   public void rebindRA(String newJNDIName) throws RAException {
      RAException rae = new RAException();

      try {
         this.removeRAfromJNDITree(rae);
         this.putRAintoJNDITree(newJNDIName);
         this.jndiName = newJNDIName;
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Module '" + this.moduleName + "' rebinding RA with new JNDI name '" + newJNDIName + "' and versionId = " + this.getVersionId());
         }

      } catch (RAException var4) {
         if (rae.getErrors().hasNext()) {
            rae.addError(var4);
            throw rae;
         } else {
            throw var4;
         }
      }
   }

   public Object getAdminObjectInstance(String jndi) {
      if (this.adminObjects != null) {
         AdminObjectMetaInfo adminObjMetaInfo = (AdminObjectMetaInfo)this.adminObjects.get(jndi);
         if (adminObjMetaInfo != null) {
            return adminObjMetaInfo.adminObj;
         }
      }

      return null;
   }

   public BeanValidator getBeanValidator() {
      return this.beanValidator;
   }

   public boolean isWaitingStartVersioningComplete() {
      return this.waitingStartVersioningComplete;
   }

   public void clearWaitingStartVersioningComplete() {
      this.waitingStartVersioningComplete = false;
   }

   public WorkManagerRuntimeMBean getWorkManagerRuntime() {
      return this.workManagerRuntime;
   }

   public ConnectorWorkManagerRuntimeMBean getConnectorWorkManagerRuntime() {
      return this.connectorWorkManagerRuntime;
   }

   public RAInstanceManager() {
      this.state = this.NEW;
      this.appCtx = null;
      this.adminObjects = null;
      this.waitingStartVersioningComplete = false;
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.classFinders = new Vector(2);
      this.appDefinedAdminObjects = new AppDefinedAdminObjectManager(this.moduleName, this);
   }

   public void setAppContext(ApplicationContextInternal appCtx) {
      this.appCtx = appCtx;
      this.workMgrCollection = appCtx.getWorkManagerCollection();
   }

   protected void setWorkManagerCollection(WorkManagerCollection workManagerCollection) {
      this.workMgrCollection = workManagerCollection;
   }

   public void setRAInfo(RAInfo raInfo) {
      this.raInfo = raInfo;
      this.jndiName = raInfo.getJndiName();
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   protected void setClassLoader(GenericClassLoader classLoader) {
      this.classloader = classLoader;
   }

   protected weblogic.work.WorkManager getWorkManager(String moduleName, String workManagerName) {
      return this.workMgrCollection.get(moduleName, workManagerName, false);
   }

   protected RAOutboundManager createOutboundManager() throws RAOutboundException {
      return new RAOutboundManager(this);
   }

   protected RAInboundManager createInboundManager() throws RAInboundException {
      return new RAInboundManager(this);
   }

   protected InjectionBeanCreator createInjectionBeanCreator(InjectionContainer injectionContainer) {
      return new InjectionBeanCreator(injectionContainer, this.moduleName);
   }

   protected WorkContextProcessorFactory createWorkContextProcessorFactory() {
      ConnectorCallbackHandler.EISPrincipalMapper mapper = null;
      SecurityIdentityInfo securityInfo = this.raInfo.getSecurityIdentityInfo();
      if (securityInfo != null && securityInfo.isInboundMappingRequired()) {
         mapper = new SecurityContextPrincipalMapperImpl(securityInfo.getDefaultCallerPrincipalMapped(), securityInfo.getInboundCallerPrincipalMapping(), securityInfo.getDefaultGroupMappedPrincipal(), securityInfo.getInboundGroupPrincipalMapping());
      }

      WorkContextProcessorFactory workContextProcessorFactory = new WorkContextProcessorFactoryImpl(mapper, false, this.getAdapterLayer());
      return workContextProcessorFactory;
   }

   protected BootstrapContext createBootstrapContext(weblogic.work.WorkManager workManager) throws WorkException {
      return new BootstrapContext(this, this.appCtx, this.moduleName, workManager, this.beanValidator, this.createWorkContextProcessorFactory());
   }

   protected InjectionContainer getInjectionContainer() {
      ModuleContext moduleContext = this.getAppContext().getModuleContext(this.moduleName);
      ModuleRegistry moduleRegistry = moduleContext.getRegistry();
      return (InjectionContainer)moduleRegistry.get(InjectionContainer.class.getName());
   }

   private WorkManagerService setupWorkManagerAndBootstrapContext() throws DeploymentException, WorkException {
      WorkManagerService moduleWorkManagerService = null;
      ApplicationRuntimeMBean applicationRuntimeMBean = this.appCtx.getRuntime();
      Debug.work("RAInstanceManager.initialize() Associate the resource adapter with the WorkManagerCollection");
      String workManagerName;
      if (this.raInfo.getWorkManager() != null) {
         workManagerName = this.raInfo.getWorkManager().getName();
         moduleWorkManagerService = this.workMgrCollection.populate(this.moduleName, this.raInfo.getWorkManager());
      } else {
         workManagerName = this.moduleName;
      }

      weblogic.work.WorkManager wm = this.getWorkManager(this.moduleName, workManagerName);
      this.workManagerRuntime = applicationRuntimeMBean.lookupWorkManagerRuntime(wm);
      Debug.println((Object)this, (String)(".initializing BootstrapContext : " + this.moduleName));
      this.bootstrapContext = this.createBootstrapContext(wm);
      if (this.managedBeanProducer != null) {
         this.managedBeanProducer.setExtendedBootstrapContext(this.bootstrapContext);
      }

      this.setBeanManagerOnBoostrapContext();
      return moduleWorkManagerService;
   }

   private void setBeanManagerOnBoostrapContext() {
      InjectionContainer injectionContainer = this.getInjectionContainer();
      if (injectionContainer != null) {
         InjectionDeployment injectionDeployment = injectionContainer.getDeployment();
         BeanManager injectionBeanManager = injectionDeployment.getBeanManager(this.moduleName);
         this.bootstrapContext.setBeanManager((javax.enterprise.inject.spi.BeanManager)injectionBeanManager.getInternalBeanManager());
      }

   }

   protected ManagedBeanProducer getManagedBeanProducer() {
      return this.managedBeanProducer;
   }

   protected RAComplianceChecker createRAComplianceChecker() {
      return ConnectorUtils.createRAComplianceChecker();
   }

   protected void setConnectorComponentName(String connectorComponentName) {
      this.connectorComponentName = connectorComponentName;
   }

   public boolean isDeployAsAWhole() {
      return this.getRAInfo().isDeployAsAWhole();
   }

   private class SuspendState {
      private int suspendState = 0;

      SuspendState() {
      }

      void suspendInbound() {
         this.suspendState |= 1;
      }

      void resumeInbound() {
         this.suspendState &= -2;
      }

      void suspendOutbound() {
         this.suspendState |= 2;
      }

      void resumeOutbound() {
         this.suspendState &= -3;
      }

      void suspendWork() {
         this.suspendState |= 4;
      }

      void resumeWork() {
         this.suspendState &= -5;
      }

      void suspendAll() {
         this.suspendState = 7;
      }

      void resumeAll() {
         this.suspendState = 0;
      }

      boolean isAllSuspended() {
         return this.suspendState == 7;
      }

      boolean isAllResumed() {
         return this.suspendState == 0;
      }

      boolean isInboundSuspend() {
         return this.suspendState == 1;
      }

      boolean isOutboundSuspended() {
         return this.suspendState == 2;
      }

      boolean isWorkSuspended() {
         return this.suspendState == 4;
      }

      int getSuspendState() {
         return this.suspendState;
      }
   }
}
