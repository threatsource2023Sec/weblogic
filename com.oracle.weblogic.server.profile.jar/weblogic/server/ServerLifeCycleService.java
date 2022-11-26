package weblogic.server;

import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.CoherenceServerMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.CoherenceServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Named
@RunLevel(10)
public class ServerLifeCycleService extends AbstractServerService {
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   private static ServerLifeCycleService singleton;
   private Map instances;
   private Map instancesCoh;
   private Map instancesSystemComponents;
   private boolean started;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ServerLifeCycleService() {
      if (singleton != null) {
         throw new AssertionError("ServerLifeCycleServices can be initialized only once");
      } else {
         singleton = this;
      }
   }

   public static final ServerLifeCycleService getInstance() {
      SecurityHelper.assertIfNotKernel();
      if (singleton == null) {
         throw new AssertionError("ServerLifeCycleServices Not Yet Initialized");
      } else {
         return singleton;
      }
   }

   public final ServerLifeCycleRuntimeMBean[] getServerLifecycleRuntimes() {
      return (ServerLifeCycleRuntimeMBean[])this.instances.values().toArray(new ServerLifeCycleRuntimeMBean[this.instances.size()]);
   }

   public final ServerLifeCycleRuntimeMBean lookupServerLifecycleRuntime(String name) {
      return (ServerLifeCycleRuntimeMBean)this.instances.get(name);
   }

   public final CoherenceServerLifeCycleRuntimeMBean[] getCoherenceServerLifecycleRuntimes() {
      return (CoherenceServerLifeCycleRuntimeMBean[])this.instancesCoh.values().toArray(new CoherenceServerLifeCycleRuntimeMBean[this.instancesCoh.size()]);
   }

   public final CoherenceServerLifeCycleRuntimeMBean lookupCoherenceServerLifecycleRuntime(String name) {
      return (CoherenceServerLifeCycleRuntimeMBean)this.instancesCoh.get(name);
   }

   public final SystemComponentLifeCycleRuntimeMBean[] getSystemComponentLifecycleRuntimes() {
      return (SystemComponentLifeCycleRuntimeMBean[])this.instancesSystemComponents.values().toArray(new SystemComponentLifeCycleRuntimeMBean[this.instancesSystemComponents.size()]);
   }

   public final SystemComponentLifeCycleRuntimeMBean lookupSystemComponentLifecycleRuntime(String name) {
      return (SystemComponentLifeCycleRuntimeMBean)this.instancesSystemComponents.get(name);
   }

   public void start() throws ServiceFailureException {
      RuntimeAccess config = ManagementService.getRuntimeAccess(KERNEL_ID);
      if (config.isAdminServer()) {
         this.instances = Collections.synchronizedMap(new HashMap());
         ((DomainAccessSettable)ManagementService.getDomainAccess(KERNEL_ID)).startLifecycleService();
         DomainMBean domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
         ServerMBean[] servers = domain.getServers();
         ServerLifeCycleRuntime.cleanupStore(servers);

         for(int i = 0; i < servers.length; ++i) {
            try {
               this.createServerLifeCycleRuntime(servers[i]);
            } catch (ManagementException var6) {
               throw new Error("Unexpected exception creating server lifecycle", var6);
            }
         }

         domain.addBeanUpdateListener(this.createBeanUpdateListener());
         this.startCoherenceServers(domain);
         this.startSystemComponents(domain);
         this.started = true;
      }
   }

   private void startCoherenceServers(DomainMBean domain) {
      this.instancesCoh = Collections.synchronizedMap(new HashMap());
      CoherenceServerMBean[] servers = domain.getCoherenceServers();
      CoherenceServerMBean[] var3 = servers;
      int var4 = servers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         CoherenceServerMBean server = var3[var5];

         try {
            this.createCoherenceServerLifeCycleRuntime(server);
         } catch (ManagementException var8) {
            throw new Error("Unexpected exception creating coherence server lifecycle", var8);
         }
      }

   }

   private void startSystemComponents(DomainMBean domain) {
      this.instancesSystemComponents = Collections.synchronizedMap(new HashMap());
      SystemComponentMBean[] servers = domain.getSystemComponents();
      SystemComponentMBean[] var3 = servers;
      int var4 = servers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SystemComponentMBean server = var3[var5];

         try {
            this.createSystemComponentLifeCycleRuntime(server);
         } catch (ManagementException var8) {
            throw new Error("Unexpected exception creating system component lifecycle", var8);
         }
      }

   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         }

         public void activateUpdate(BeanUpdateEvent event) {
            BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

            for(int i = 0; i < updated.length; ++i) {
               BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
               String name;
               ServerMBean newServerxx;
               CoherenceServerMBean newServer;
               SystemComponentMBean newServerx;
               switch (propertyUpdate.getUpdateType()) {
                  case 2:
                     name = propertyUpdate.getPropertyName();
                     if ("Servers".equals(name)) {
                        newServerxx = (ServerMBean)propertyUpdate.getAddedObject();

                        try {
                           ServerLifeCycleService.this.createServerLifeCycleRuntime(newServerxx);
                        } catch (ManagementException var10) {
                           throw new Error("Unexpected exception creating server lifecycle", var10);
                        }
                     } else if ("CoherenceServers".equals(name)) {
                        newServer = (CoherenceServerMBean)propertyUpdate.getAddedObject();

                        try {
                           ServerLifeCycleService.this.createCoherenceServerLifeCycleRuntime(newServer);
                        } catch (ManagementException var9) {
                           throw new Error("Unexpected exception creating coherence server lifecycle", var9);
                        }
                     } else if ("SystemComponents".equals(name)) {
                        newServerx = (SystemComponentMBean)propertyUpdate.getAddedObject();

                        try {
                           ServerLifeCycleService.this.createSystemComponentLifeCycleRuntime(newServerx);
                        } catch (ManagementException var8) {
                           throw new Error("Unexpected exception creating system component lifecycle", var8);
                        }
                     }
                     break;
                  case 3:
                     name = propertyUpdate.getPropertyName();
                     if ("Servers".equals(name)) {
                        newServerxx = (ServerMBean)propertyUpdate.getRemovedObject();
                        ServerLifeCycleService.this.destroyServerLifecycleRuntime(newServerxx.getName());
                     } else if ("CoherenceServers".equals(name)) {
                        newServer = (CoherenceServerMBean)propertyUpdate.getRemovedObject();
                        ServerLifeCycleService.this.destroyCoherenceServerLifecycleRuntime(newServer.getName());
                     } else if ("SystemComponents".equals(name)) {
                        newServerx = (SystemComponentMBean)propertyUpdate.getRemovedObject();
                        ServerLifeCycleService.this.destroySystemComponentLifecycleRuntime(newServerx.getName());
                     }
               }
            }

         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
   }

   private void createServerLifeCycleRuntime(ServerMBean server) throws ManagementException {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(KERNEL_ID);
      ServerLifeCycleRuntime slrt = new ServerLifeCycleRuntime(server);
      this.instances.put(server.getName(), slrt);
   }

   private void createCoherenceServerLifeCycleRuntime(CoherenceServerMBean server) throws ManagementException {
      CoherenceServerLifeCycleRuntime slrt = new CoherenceServerLifeCycleRuntime(server);
      this.instancesCoh.put(server.getName(), slrt);
   }

   private void createSystemComponentLifeCycleRuntime(SystemComponentMBean server) throws ManagementException {
      SystemComponentLifeCycleRuntime slrt = new SystemComponentLifeCycleRuntime(server);
      this.instancesSystemComponents.put(server.getName(), slrt);
   }

   private void destroyServerLifecycleRuntime(String name) {
      ServerLifeCycleRuntime oldLifecycle = (ServerLifeCycleRuntime)this.instances.get(name);
      oldLifecycle.cleanup();
      this.instances.remove(name);
      ManagementService.getDomainAccess(KERNEL_ID).unregisterBeanRelationship(oldLifecycle);
   }

   private void destroyCoherenceServerLifecycleRuntime(String name) {
      CoherenceServerLifeCycleRuntime oldLifecycle = (CoherenceServerLifeCycleRuntime)this.instancesCoh.get(name);
      oldLifecycle.cleanup();
      this.instancesCoh.remove(name);
      ManagementService.getDomainAccess(KERNEL_ID).unregisterBeanRelationship(oldLifecycle);
   }

   private void destroySystemComponentLifecycleRuntime(String name) {
      SystemComponentLifeCycleRuntime oldLifecycle = (SystemComponentLifeCycleRuntime)this.instancesSystemComponents.get(name);
      oldLifecycle.cleanup();
      this.instancesSystemComponents.remove(name);
      ManagementService.getDomainAccess(KERNEL_ID).unregisterBeanRelationship(oldLifecycle);
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }

   public static boolean isStarted() {
      return singleton != null ? singleton.started : false;
   }
}
