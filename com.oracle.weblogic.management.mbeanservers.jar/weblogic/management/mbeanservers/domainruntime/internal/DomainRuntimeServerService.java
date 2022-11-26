package weblogic.management.mbeanservers.domainruntime.internal;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;
import javax.management.OperationsException;
import javax.naming.Context;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMXMBean;
import weblogic.management.eventbus.InternalEventBusLogger;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBus;
import weblogic.management.eventbus.apis.InternalEventBusFactory;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.eventbus.apis.InternalEvent.EventType;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerBuilder;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.MBeanInfoBuilder;
import weblogic.management.mbeanservers.MBeanServerType;
import weblogic.management.mbeanservers.internal.CallerPartitionContextInterceptor;
import weblogic.management.mbeanservers.internal.JMXContextInterceptor;
import weblogic.management.mbeanservers.internal.MBeanCICInterceptor;
import weblogic.management.mbeanservers.internal.MBeanServerServiceBase;
import weblogic.management.mbeanservers.internal.PartitionJMXInterceptor;
import weblogic.management.mbeanservers.internal.RuntimeMBeanAgent;
import weblogic.management.mbeanservers.internal.SecurityInterceptor;
import weblogic.management.mbeanservers.internal.SecurityMBeanMgmtOpsInterceptor;
import weblogic.management.mbeanservers.internal.WLSObjectSecurityManagerImpl;
import weblogic.management.mbeanservers.partition.PartitionedDomainRuntimeMbsManager;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.DomainConfiguration;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class DomainRuntimeServerService extends MBeanServerServiceBase {
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;
   @Inject
   @Named("EditSessionConfigurationManagerService")
   private ServerService dependencyOnEditSessionConfigurationManagerService;
   @Inject
   PartitionedDomainRuntimeMbsManager pmm;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");
   private MBeanServerConnectionManager connectionManager = null;
   private FederatedObjectNameManager objectNameManager = null;
   private DomainAccess domainAccess;
   private DomainRuntimeMBean domainRuntime;
   WLSModelMBeanContext context = null;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public String getName() {
      return "DomainRuntimeService";
   }

   public String getVersion() {
      return null;
   }

   public void start() throws ServiceFailureException {
      boolean isEnabled = this.isEnabled();
      if (debug.isDebugEnabled()) {
         debug.debug("DomainRuntimeServerService start: isEnabled(" + isEnabled + ") jndiName(" + "weblogic.management.mbeanservers.domainruntime" + ")");
      }

      if (isEnabled) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         WLSMBeanServerBuilder builder = new WLSMBeanServerBuilder();
         this.connectionManager = new MBeanServerConnectionManager();
         MBeanServerDelegate delegate = builder.newMBeanServerDelegate();
         FederatedMBeanServerDelegate federatedDelegate = new FederatedMBeanServerDelegate(delegate, this.connectionManager);
         String defaultDomain = runtimeAccess.getDomainName();
         FederatedMBeanServerInterceptor fedInterceptor = new FederatedMBeanServerInterceptor(this.connectionManager, defaultDomain);
         WLSMBeanServer mbeanServer = (WLSMBeanServer)builder.newMBeanServer(defaultDomain, (MBeanServer)null, federatedDelegate);
         this.objectNameManager = new FederatedObjectNameManager(this.connectionManager, defaultDomain);
         CallerPartitionContextInterceptor callerPartitionContextInterceptor = new CallerPartitionContextInterceptor();
         mbeanServer.addInterceptor(callerPartitionContextInterceptor);
         mbeanServer.addInterceptor(fedInterceptor);
         if (MBeanInfoBuilder.globalMBeansVisibleToPartitions) {
            ManagementLogger.logGlobalMBeansVisibleToPartitionsTrue();
         }

         PartitionJMXInterceptor partitionJMXInterceptor = new PartitionJMXInterceptor(MBeanServerType.DOMAIN_RUNTIME);
         mbeanServer.addInterceptor(partitionJMXInterceptor);
         MBeanCICInterceptor mbeanCICInterceptor = new MBeanCICInterceptor(defaultDomain);
         mbeanServer.addInterceptor(mbeanCICInterceptor);
         SecurityInterceptor secInterceptor = new SecurityInterceptor(mbeanServer, "weblogic.management.mbeanservers.domainruntime");
         mbeanServer.addInterceptor(secInterceptor);
         SecurityMBeanMgmtOpsInterceptor securityMBeanMgmtOpersInterceptor = new SecurityMBeanMgmtOpsInterceptor(2);
         mbeanServer.addInterceptor(securityMBeanMgmtOpersInterceptor);
         JMXContextInterceptor jmxContextInterceptor = new JMXContextInterceptor(defaultDomain);
         mbeanServer.addInterceptor(jmxContextInterceptor);
         this.context = new WLSModelMBeanContext(mbeanServer, this.objectNameManager, WLSObjectSecurityManagerImpl.getInstance());
         this.context.setVersion("99.0.0.0");
         this.initialize("weblogic.management.mbeanservers.domainruntime", mbeanServer);
         runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         this.domainAccess = ManagementService.getDomainAccess(kernelId);
         this.domainRuntime = this.domainAccess.getDomainRuntime();
         this.domainAccess.setDomainRuntimeService(new DomainRuntimeServiceMBeanImpl(this.connectionManager, this.domainRuntime));
         mbeanServer.setFirstAccessCallback(this.createAccessCallback());
         ManagementService.initializeDomainRuntimeMBeanServer(kernelId, mbeanServer);
         javaURLContextFactory.setDomainRuntimeMBeanServer(mbeanServer);
         this.pmm.newMbs(kernelId, mbeanServer);
         if (!debug.isDebugEnabled()) {
            Logger.getLogger("javax.management.remote.misc").setLevel(Level.OFF);
            Logger.getLogger("javax.management.remote.rmi").setLevel(Level.OFF);
         }

         super.start();
         if (debug.isDebugEnabled()) {
            debug.debug("DomainRuntimeService start: completed - weblogic.management.mbeanservers.domainruntime");
         }

      }
   }

   private void registerAllMBeans() {
      if (debug.isDebugEnabled()) {
         debug.debug("DomainRuntimeServerService.registerAllMBeans - starting ");
      }

      try {
         DomainMBean domainConfig;
         try {
            domainConfig = DomainConfiguration.getInstance().getDomainMBean();
         } catch (IOException var14) {
            throw new AssertionError(var14);
         }

         this.domainAccess.invokeAccessCallbacks(domainConfig);
         WLSModelMBeanContext runtimeContext = new WLSModelMBeanContext(this.getMBeanServer(), this.objectNameManager, WLSObjectSecurityManagerImpl.getInstance());
         runtimeContext.setRecurse(false);
         runtimeContext.setVersion("99.0.0.0");
         runtimeContext.setReadOnly(false);
         new RuntimeMBeanAgent(runtimeContext, this.domainAccess);
         this.registerTypeService(this.context);
         WLSModelMBeanContext readContext = new WLSModelMBeanContext(this.getMBeanServer(), this.objectNameManager, WLSObjectSecurityManagerImpl.getInstance());
         readContext.setRecurse(true);
         readContext.setVersion("99.0.0.0");
         readContext.setReadOnly(true);
         WLSModelMBeanFactory.registerWLSModelMBean(domainConfig, readContext);
         WLSModelMBeanContext editContext = new WLSModelMBeanContext(this.getMBeanServer(), this.objectNameManager, WLSObjectSecurityManagerImpl.getInstance());
         editContext.setRecurse(true);
         editContext.setVersion("99.0.0.0");
         editContext.setReadOnly(true);
         editContext.setFilteringEnabled(true);

         DomainMBean domainEdit;
         try {
            EditAccess edit = ManagementServiceRestricted.getEditAccess(kernelId);
            domainEdit = edit.getDomainBeanWithoutLock();
         } catch (EditFailedException var13) {
            throw new AssertionError(var13);
         }

         WLSModelMBeanFactory.registerWLSModelMBean(domainEdit, editContext);
         this.connectionManager.initializeConnectivity();
         InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
         Map eventPayload = new HashMap();
         eventPayload.put(MBeanServer.class.getName(), this.getMBeanServer());
         InternalEvent domainRuntimeMBeanServerPostLazyInit = new InternalEventImpl(EventType.MANAGEMENT_MBEANSERVERS_DOMAIN_MBEANSERVER_POST_INITIALIZATION, eventPayload);
         Future result = internalEventbus.send(domainRuntimeMBeanServerPostLazyInit);

         try {
            result.get();
         } catch (ExecutionException var11) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(domainRuntimeMBeanServerPostLazyInit.toString(), var11.getCause());
         } catch (InterruptedException var12) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(domainRuntimeMBeanServerPostLazyInit.toString(), var12);
         }
      } catch (InstanceAlreadyExistsException var15) {
      } catch (OperationsException var16) {
         ManagementLogger.logMBeanServerInitException(var16);
         throw new Error("Unable to register Federated Domain Runtime Access ", var16);
      } catch (MBeanRegistrationException var17) {
         ManagementLogger.logMBeanServerInitException(var17);
         throw new Error("Unable to register Federated Domain Runtime Access ", var17);
      } catch (RuntimeException var18) {
         ManagementLogger.logMBeanServerInitException(var18);
         throw var18;
      } catch (Error var19) {
         ManagementLogger.logMBeanServerInitException(var19);
         throw var19;
      }

      if (debug.isDebugEnabled()) {
         debug.debug("DomainRuntimeServerService.registerAllMBeans - completed ");
      }

   }

   private WLSMBeanServer.FirstAccessCallback createAccessCallback() {
      final ClassLoader origLoader = Thread.currentThread().getContextClassLoader();
      return new WLSMBeanServer.FirstAccessCallback() {
         public void accessed(MBeanServer accessedMBeanServer) {
            SecurityServiceManager.runAs(DomainRuntimeServerService.kernelId, DomainRuntimeServerService.kernelId, new PrivilegedAction() {
               public Object run() {
                  ClassLoader currLoader = Thread.currentThread().getContextClassLoader();

                  Object var2;
                  try {
                     Thread.currentThread().setContextClassLoader(origLoader);
                     javaURLContextFactory.pushContext((Context)null);
                     ComponentInvocationContextManager.runAs(DomainRuntimeServerService.kernelId, ComponentInvocationContextManager.getInstance(DomainRuntimeServerService.kernelId).createComponentInvocationContext("DOMAIN"), new Runnable() {
                        public void run() {
                           DomainRuntimeServerService.this.registerAllMBeans();
                        }
                     });
                     var2 = null;
                  } catch (ExecutionException var6) {
                     throw new RuntimeException(var6.getCause());
                  } finally {
                     javaURLContextFactory.popContext();
                     Thread.currentThread().setContextClassLoader(currLoader);
                  }

                  return var2;
               }
            });
         }
      };
   }

   private boolean isEnabled() {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      if (!runtime.isAdminServer()) {
         return false;
      } else {
         JMXMBean jmx = runtime.getDomain().getJMX();
         return jmx.isDomainMBeanServerEnabled() || jmx.isManagementEJBEnabled();
      }
   }

   public void stop() throws ServiceFailureException {
      boolean isEnabled = this.isEnabled();
      if (debug.isDebugEnabled()) {
         debug.debug("DomainRuntimeServerService stop: isEnabled(" + isEnabled + ") jndiName(" + "weblogic.management.mbeanservers.domainruntime" + ")");
      }

      if (isEnabled) {
         this.connectionManager.stop();
         super.stop();
         if (debug.isDebugEnabled()) {
            debug.debug("DomainRuntimeService stop: completed - weblogic.management.mbeanservers.domainruntime");
         }

      }
   }
}
