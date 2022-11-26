package weblogic.management.mbeanservers.runtime.internal;

import java.lang.annotation.Annotation;
import java.lang.management.ManagementFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.OperationsException;
import javax.naming.Context;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSourceNotFoundException;
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
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptor;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.MBeanServerType;
import weblogic.management.mbeanservers.internal.CallerPartitionContextInterceptor;
import weblogic.management.mbeanservers.internal.JMXContextInterceptor;
import weblogic.management.mbeanservers.internal.MBeanCICInterceptor;
import weblogic.management.mbeanservers.internal.MBeanServerServiceBase;
import weblogic.management.mbeanservers.internal.PartitionJMXInterceptor;
import weblogic.management.mbeanservers.internal.RuntimeMBeanAgent;
import weblogic.management.mbeanservers.internal.SecurityInterceptor;
import weblogic.management.mbeanservers.internal.WLSObjectNameManager;
import weblogic.management.mbeanservers.internal.WLSObjectSecurityManagerImpl;
import weblogic.management.mbeanservers.partition.PartitionedRuntimeMbsManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class RuntimeServerService extends MBeanServerServiceBase {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   private static final String MANAGEMENT_RUNTIME_SOURCE = "ManagementRuntimeImageSource";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXRuntime");
   @Inject
   RuntimeAccess runtimeAccess;
   @Inject
   private PartitionedRuntimeMbsManager pmm;
   static DiagnosticSupportService diagnosticSupportService = null;
   private ManagementRuntimeImageSource imageSource;

   static void setSupportService(DiagnosticSupportService supportService) {
      diagnosticSupportService = supportService;
   }

   private ObjectNameManager getObjectNameManager() {
      return (ObjectNameManager)(diagnosticSupportService != null ? diagnosticSupportService.getObjectNameManager() : new WLSObjectNameManager(this.runtimeAccess.getDomainName()));
   }

   public void start() throws ServiceFailureException {
      if (!this.isEnabled()) {
         if (debug.isDebugEnabled()) {
            debug.debug("Runtime MBeanServer Disabledweblogic.management.mbeanservers.runtime");
         }

      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("Starting MBeanServer weblogic.management.mbeanservers.runtime");
         }

         MBeanServer platformMBeanServer = null;
         ArrayList mbeanServers = MBeanServerFactory.findMBeanServer((String)null);
         if (mbeanServers.size() != 0 || this.isPlatformMBeanServerEnabled() || this.isPlatformMBeanServerUsed()) {
            platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
         }

         WLSMBeanServer mbs;
         if (this.isPlatformMBeanServerUsed()) {
            if (platformMBeanServer != null && !(platformMBeanServer instanceof WLSMBeanServer)) {
               ManagementLogger.logPlatformMBeanServerInitFailure();
               this.initialize("weblogic.management.mbeanservers.runtime", (MBeanServer)null, (MBeanServer)null);
            } else {
               if (platformMBeanServer instanceof WLSMBeanServer) {
                  mbs = (WLSMBeanServer)platformMBeanServer;
                  mbs.setDefaultDomainName(this.runtimeAccess.getDomainName());
               }

               this.initialize("weblogic.management.mbeanservers.runtime", (MBeanServer)null, platformMBeanServer);
            }
         } else {
            this.initialize("weblogic.management.mbeanservers.runtime", (MBeanServer)null, (MBeanServer)null);
         }

         mbs = (WLSMBeanServer)this.getMBeanServer();
         CallerPartitionContextInterceptor callerPartitionContextInterceptor = new CallerPartitionContextInterceptor();
         mbs.addInterceptor(callerPartitionContextInterceptor);
         PartitionJMXInterceptor partitionJMXInterceptor = new PartitionJMXInterceptor(MBeanServerType.RUNTIME);
         mbs.addInterceptor(partitionJMXInterceptor);
         MBeanCICInterceptor mbeanCICInterceptor = new MBeanCICInterceptor(this.runtimeAccess.getDomainName());
         mbs.addInterceptor(mbeanCICInterceptor);
         WLSMBeanServerInterceptor secInterceptor = new SecurityInterceptor(mbs, "weblogic.management.mbeanservers.runtime");
         mbs.addInterceptor(secInterceptor);
         JMXContextInterceptor jmxContextInterceptor = new JMXContextInterceptor();
         mbs.addInterceptor(jmxContextInterceptor);
         new RuntimeServiceMBeanImpl(this.runtimeAccess);
         mbs.setFirstAccessCallback(this.createAccessCallback());
         ManagementService.initializeRuntimeMBeanServer(kernelId, mbs);
         javaURLContextFactory.setRuntimeMBeanServer(mbs);
         this.pmm.newMbs(kernelId, mbs);
         super.start();

         try {
            this.imageSource = new ManagementRuntimeImageSource(mbs);
            ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).registerImageSource("ManagementRuntimeImageSource", this.imageSource);
         } catch (Exception var10) {
            if (debug.isDebugEnabled()) {
               debug.debug("Caught exception registering Diagnostics image source for RuntimeService", var10);
            }
         }

      }
   }

   private void registerAllMBeans() {
      try {
         DomainMBean domain = this.runtimeAccess.getDomain();
         ObjectNameManager nameManager = this.getObjectNameManager();
         WLSModelMBeanContext runtimeContext = new WLSModelMBeanContext(this.getMBeanServer(), nameManager, WLSObjectSecurityManagerImpl.getInstance());
         runtimeContext.setRecurse(false);
         runtimeContext.setVersion("99.0.0.0");
         runtimeContext.setReadOnly(false);
         new RuntimeMBeanAgent(runtimeContext, this.runtimeAccess);
         this.registerTypeService(runtimeContext);
         WLSModelMBeanContext context = new WLSModelMBeanContext(this.getMBeanServer(), nameManager, WLSObjectSecurityManagerImpl.getInstance());
         context.setRecurse(true);
         context.setVersion("99.0.0.0");
         context.setReadOnly(true);
         WLSModelMBeanFactory.registerWLSModelMBean(domain, context);
         InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
         Map eventPayload = new HashMap();
         eventPayload.put(MBeanServer.class.getName(), this.getMBeanServer());
         InternalEvent runtimeMBeanServerPostLazyInit = new InternalEventImpl(EventType.MANAGEMENT_MBEANSERVERS_RUNTIME_MBEANSERVER_POST_INITIALIZATION, eventPayload);
         Future result = internalEventbus.send(runtimeMBeanServerPostLazyInit);

         try {
            result.get();
         } catch (ExecutionException var10) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(runtimeMBeanServerPostLazyInit.toString(), var10.getCause());
         } catch (InterruptedException var11) {
            InternalEventBusLogger.logErrorProcessingInternalEvent(runtimeMBeanServerPostLazyInit.toString(), var11);
         }

      } catch (OperationsException var12) {
         ManagementLogger.logMBeanServerInitException(var12);
         throw new Error(var12);
      } catch (MBeanRegistrationException var13) {
         ManagementLogger.logMBeanServerInitException(var13);
         throw new Error(var13);
      } catch (RuntimeException var14) {
         ManagementLogger.logMBeanServerInitException(var14);
         throw var14;
      } catch (Error var15) {
         ManagementLogger.logMBeanServerInitException(var15);
         throw var15;
      }
   }

   private WLSMBeanServer.FirstAccessCallback createAccessCallback() {
      final ClassLoader origLoader = Thread.currentThread().getContextClassLoader();
      return new WLSMBeanServer.FirstAccessCallback() {
         public void accessed(MBeanServer accessedMBeanServer) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  SecurityServiceManager.runAs(RuntimeServerService.kernelId, RuntimeServerService.kernelId, new PrivilegedAction() {
                     public Object run() {
                        ClassLoader currLoader = Thread.currentThread().getContextClassLoader();

                        Object var2;
                        try {
                           Thread.currentThread().setContextClassLoader(origLoader);
                           javaURLContextFactory.pushContext((Context)null);
                           ComponentInvocationContextManager.runAs(RuntimeServerService.kernelId, ComponentInvocationContextManager.getInstance(RuntimeServerService.kernelId).createComponentInvocationContext("DOMAIN"), new Runnable() {
                              public void run() {
                                 RuntimeServerService.this.registerAllMBeans();
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
                  return null;
               }
            });
         }
      };
   }

   private boolean isPlatformMBeanServerEnabled() {
      return this.runtimeAccess.getDomain().getJMX().isPlatformMBeanServerEnabled();
   }

   private boolean isPlatformMBeanServerUsed() {
      return this.runtimeAccess.getDomain().getJMX().isPlatformMBeanServerUsed();
   }

   private boolean isEnabled() {
      JMXMBean jmx = this.runtimeAccess.getDomain().getJMX();
      return jmx.isRuntimeMBeanServerEnabled() || jmx.isDomainMBeanServerEnabled() || jmx.isManagementEJBEnabled();
   }

   public void stop() throws ServiceFailureException {
      if (this.isEnabled()) {
         try {
            ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).unregisterImageSource("ManagementRuntimeImageSource");
         } catch (ImageSourceNotFoundException var2) {
            if (debug.isDebugEnabled()) {
               debug.debug("Caught exception unregistering RuntimeService image source:", var2);
            }
         }

         super.stop();
      }
   }
}
