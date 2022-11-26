package weblogic.management.mbeanservers.edit.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.naming.Context;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.management.EditSessionTool;
import weblogic.management.ManagementLogger;
import weblogic.management.eventbus.InternalEventBusLogger;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBusFactory;
import weblogic.management.eventbus.apis.InternalEvent.EventType;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerBuilder;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.MBeanServerType;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean;
import weblogic.management.mbeanservers.edit.RecordingManagerMBean;
import weblogic.management.mbeanservers.internal.CallerPartitionContextInterceptor;
import weblogic.management.mbeanservers.internal.EditSessionContextInterceptor;
import weblogic.management.mbeanservers.internal.JMXContextInterceptor;
import weblogic.management.mbeanservers.internal.MBeanCICInterceptor;
import weblogic.management.mbeanservers.internal.MBeanServerServiceBase;
import weblogic.management.mbeanservers.internal.PartitionJMXInterceptor;
import weblogic.management.mbeanservers.internal.SecurityInterceptor;
import weblogic.management.mbeanservers.internal.SecurityMBeanMgmtOpsInterceptor;
import weblogic.management.mbeanservers.internal.WLSObjectNameManager;
import weblogic.management.mbeanservers.internal.WLSObjectSecurityManagerImpl;
import weblogic.management.mbeanservers.partition.PartitionedEditMbsManager;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.EditSessionServerManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

abstract class BaseEditServerService extends MBeanServerServiceBase {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXEdit");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final boolean initManagementService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   private PartitionedEditMbsManager pmm;
   @Inject
   private EditSessionServerManager editSessionServerManager;
   @Inject
   private EditSessionTool editSessionTool;
   WLSModelMBeanContext context;
   private volatile EditServiceMBean editService;
   private volatile EditLockInterceptor editLockInterceptor;
   private volatile RecordingInterceptor recordingInterceptor;

   BaseEditServerService() {
      this(true);
   }

   public WLSMBeanServer getMBeanServer() {
      MBeanServer result = super.getMBeanServer();
      return result instanceof EditMBeanServerForwarder ? ((EditMBeanServerForwarder)result).getDefaultServer() : (WLSMBeanServer)result;
   }

   public BaseEditServerService(boolean initManagementService) {
      this.initManagementService = initManagementService;
   }

   public void start() throws ServiceFailureException {
      if (this.isEnabled()) {
         if (debug.isDebugEnabled()) {
            debug.debug("Starting MBeanServer " + this.getEditServiceJndiName());
         }

         WLSMBeanServerBuilder builder = new WLSMBeanServerBuilder();
         RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
         String defaultDomain = runtime.getDomainName();
         MBeanServer mbs = builder.newMBeanServer(defaultDomain, (MBeanServer)null, (MBeanServerDelegate)null, (MBeanServer)null);
         EditAccess editAccess = this.getEditAccess();
         if (editAccess.isDefault() && (editAccess.getPartitionName() == null || "DOMAIN".equals(editAccess.getPartitionName()))) {
            mbs = new EditMBeanServerForwarder((WLSMBeanServer)mbs, this.getEditSessionServerManager());
         }

         this.initialize(this.getEditServiceJndiName(), (MBeanServer)mbs);
         WLSMBeanServer mbeanServer = this.getMBeanServer();
         CallerPartitionContextInterceptor callerPartitionContextInterceptor = new CallerPartitionContextInterceptor();
         mbeanServer.addInterceptor(callerPartitionContextInterceptor);
         EditSessionContextInterceptor editSessionContextInterceptor = new EditSessionContextInterceptor(this.getEditAccess(), this.editSessionTool);
         mbeanServer.addInterceptor(editSessionContextInterceptor);
         PartitionJMXInterceptor partitionJMXInterceptor = new PartitionJMXInterceptor(MBeanServerType.EDIT);
         mbeanServer.addInterceptor(partitionJMXInterceptor);
         MBeanCICInterceptor mbeanCICInterceptor = new MBeanCICInterceptor(runtime.getDomainName());
         mbeanServer.addInterceptor(mbeanCICInterceptor);
         SecurityInterceptor secInterceptor = new SecurityInterceptor(mbeanServer);
         mbeanServer.addInterceptor(secInterceptor);
         this.editLockInterceptor = new EditLockInterceptor(editAccess, mbeanServer);
         mbeanServer.addInterceptor(this.editLockInterceptor);
         SecurityMBeanMgmtOpsInterceptor securityMBeanMgmtOpersInterceptor = new SecurityMBeanMgmtOpsInterceptor(1);
         mbeanServer.addInterceptor(securityMBeanMgmtOpersInterceptor);
         this.recordingInterceptor = new RecordingInterceptor(editAccess);
         mbeanServer.addInterceptor(this.recordingInterceptor);
         JMXContextInterceptor jmxContextInterceptor = new JMXContextInterceptor();
         mbeanServer.addInterceptor(jmxContextInterceptor);
         mbeanServer.setFirstAccessCallback(this.createAccessCallback());
         if (this.initManagementService) {
            ManagementService.initializeEditMBeanServer(kernelId, mbeanServer);
            javaURLContextFactory.setEditMBeanServer(mbeanServer);
            this.pmm.newMbs(kernelId, mbeanServer);
         }

         super.start();
         InternalEvent mbeanServerSet = this.createEvent(EventType.MANAGEMENT_MBEANSERVERS_EDIT_MBEANSERVER_SET);
         InternalEventBusFactory.getInstance().send(mbeanServerSet);
         if (debug.isDebugEnabled()) {
            debug.debug("Starting MBeanServer " + this.getEditServiceJndiName());
         }

      }
   }

   private WLSMBeanServer.FirstAccessCallback createAccessCallback() {
      final ClassLoader origLoader = Thread.currentThread().getContextClassLoader();
      return new WLSMBeanServer.FirstAccessCallback() {
         public void accessed(MBeanServer accessedMBeanServer) {
            SecurityServiceManager.runAs(BaseEditServerService.kernelId, BaseEditServerService.kernelId, new PrivilegedAction() {
               public Object run() {
                  ClassLoader currLoader = Thread.currentThread().getContextClassLoader();

                  Object var2;
                  try {
                     Thread.currentThread().setContextClassLoader(origLoader);
                     javaURLContextFactory.pushContext((Context)null);
                     ComponentInvocationContextManager.runAs(BaseEditServerService.kernelId, ComponentInvocationContextManager.getInstance(BaseEditServerService.kernelId).createComponentInvocationContext("DOMAIN"), new Runnable() {
                        public void run() {
                           EditAccess editAccess = BaseEditServerService.this.getEditAccess();
                           EditDirectoryManager.ensureEditDirectoryCreated(editAccess.getPartitionName(), editAccess.getEditSessionName());
                           BaseEditServerService.this.registerAllMBeans();
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

   private void registerAllMBeans() {
      try {
         RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
         ObjectNameManager nameManager = new WLSObjectNameManager(runtime.getDomainName());
         WLSMBeanServer mbeanServer = this.getMBeanServer();
         this.context = new WLSModelMBeanContext(mbeanServer, nameManager, WLSObjectSecurityManagerImpl.getInstance());
         this.context.setRecurse(false);
         this.context.setVersion("99.0.0.0");
         this.context.setReadOnly(false);
         this.editService = this.createEditService(this.context);
         this.registerTypeService(this.context);
         WLSModelMBeanFactory.registerWLSModelMBean(this.editService, new ObjectName(this.getEditServiceObjectName()), this.context);
         WLSModelMBeanFactory.registerWLSModelMBean(this.editService.getConfigurationManager(), new ObjectName(ConfigurationManagerMBean.OBJECT_NAME), this.context);
         WLSModelMBeanFactory.registerWLSModelMBean(this.editService.getPortablePartitionManager(), new ObjectName(PortablePartitionManagerMBean.OBJECT_NAME), this.context);
         WLSModelMBeanFactory.registerWLSModelMBean(this.editService.getRecordingManager(), new ObjectName(RecordingManagerMBean.OBJECT_NAME), this.context);
         this.context.setRecurse(true);
         WLSModelMBeanFactory.registerWLSModelMBean(this.editService.getDomainConfiguration(), this.context);
         InternalEvent mbeanServerPostInit = this.createEvent(EventType.MANAGEMENT_MBEANSERVERS_EDIT_MBEANSERVER_POST_INITIALIZATION);
         Future result = InternalEventBusFactory.getInstance().send(mbeanServerPostInit);

         try {
            result.get();
         } catch (ExecutionException var7) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(mbeanServerPostInit.toString(), var7.getCause());
         } catch (InterruptedException var8) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(mbeanServerPostInit.toString(), var8);
         }
      } catch (InstanceAlreadyExistsException var9) {
      } catch (OperationsException var10) {
         if (debug.isDebugEnabled()) {
            debug.debug("Operations exception starting MBeanServer ", var10);
         }

         ManagementLogger.logMBeanServerInitException(var10);
         throw new Error("Unable to register Edit Access ", var10);
      } catch (MBeanRegistrationException var11) {
         if (debug.isDebugEnabled()) {
            debug.debug("MBean registration exception starting MBeanServer ", var11);
         }

         ManagementLogger.logMBeanServerInitException(var11);
         throw new Error("Unable to register Edit Access ", var11);
      } catch (RuntimeException var12) {
         ManagementLogger.logMBeanServerInitException(var12);
         throw var12;
      } catch (Error var13) {
         ManagementLogger.logMBeanServerInitException(var13);
         throw var13;
      }

   }

   private boolean isEnabled() {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      return !runtime.isAdminServer() ? false : runtime.getDomain().getJMX().isEditMBeanServerEnabled();
   }

   public void stop() throws ServiceFailureException {
      if (this.isEnabled()) {
         if (debug.isDebugEnabled()) {
            debug.debug("Stopping MBeanServer " + this.getEditServiceJndiName());
         }

         if (this.editService != null) {
            this.context.setRecurse(true);
            WLSModelMBeanFactory.unregisterWLSModelMBean(this.editService, this.context);
            this.editService.getConfigurationManager().releaseEditAccess();
            WLSModelMBeanFactory.unregisterWLSModelMBean(this.editService.getConfigurationManager(), this.context);
            WLSModelMBeanFactory.unregisterWLSModelMBean(this.editService.getPortablePartitionManager(), this.context);
            this.editService.getRecordingManager().releaseEditAccess();
            WLSModelMBeanFactory.unregisterWLSModelMBean(this.editService.getRecordingManager(), this.context);
            this.unregisterTypeService(this.context);
         }

         if (this.editLockInterceptor != null) {
            this.editLockInterceptor.releaseEditAccess();
         }

         if (this.recordingInterceptor != null) {
            this.recordingInterceptor.releaseEditAccess();
         }

         super.stop();
      }
   }

   protected abstract String getEditServiceJndiName();

   protected abstract String getEditServiceObjectName();

   protected abstract EditAccess getEditAccess();

   protected abstract EditServiceMBean createEditService(WLSModelMBeanContext var1);

   protected abstract InternalEvent createEvent(InternalEvent.EventType var1);

   protected EditSessionServerManager getEditSessionServerManager() {
      return this.editSessionServerManager;
   }
}
