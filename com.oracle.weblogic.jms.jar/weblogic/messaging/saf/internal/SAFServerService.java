package weblogic.messaging.saf.internal;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceNotFoundException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSService;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.utils.GenericManagedService;
import weblogic.management.utils.GenericServiceManager;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.saf.SAFEndpointManager;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFLogger;
import weblogic.messaging.saf.SAFTransport;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public final class SAFServerService extends AbstractServerService {
   private static final int STATE_INITIALIZING = 0;
   private static final int STATE_SUSPENDING = 1;
   private static final int STATE_SUSPENDED = 2;
   private static final int STATE_STARTED = 4;
   private static final int STATE_SHUTTING_DOWN = 8;
   private static final int STATE_CLOSED = 16;
   private int state;
   private static Map services = new HashMap();
   private SAFServiceAdmin safAdmin;
   private SAFManagerImpl safManager;
   private boolean registered;
   private GenericManagedService safGMS;
   private HashMap agents;
   static final String IMAGE_NAME = "SAF";
   private final ImageSource IMAGE_SOURCE;
   private final ComponentInvocationContext cic;

   public SAFServerService() throws ServiceFailureException {
      this(JMSCICHelper.getCurrentCIC());
   }

   public SAFServerService(ComponentInvocationContext cic) throws ServiceFailureException {
      this.state = 0;
      this.agents = new HashMap();
      this.IMAGE_SOURCE = new SAFDiagnosticImageSource();
      this.cic = cic;
      this.safManager = (SAFManagerImpl)SAFManagerImpl.getManager();
      synchronized(this) {
         this.state = 2;
      }

      Class var2 = SAFServerService.class;
      synchronized(SAFServerService.class) {
         services.put(this.getPartitionName(), this);
      }
   }

   public static synchronized SAFServerService getService() throws ServiceFailureException {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      SAFServerService service = (SAFServerService)services.get(partitionName);
      if (service == null) {
         if ("DOMAIN".equals(partitionName)) {
            throw new ServiceFailureException("SAFServerService for " + partitionName + " has not created yet");
         }

         service = new SAFServerService(JMSCICHelper.getCurrentCIC());
         service.startInternal();
      }

      return service;
   }

   public static synchronized SAFServerService getService(String partitionName) {
      return (SAFServerService)services.get(partitionName);
   }

   public static synchronized SAFServerService removeService(String partitionName) {
      return (SAFServerService)services.remove(partitionName);
   }

   private String getPartitionName() {
      return JMSService.getSafePartitionKey(this.cic);
   }

   public void stop() throws ServiceFailureException {
      if (this.registered) {
         this.registered = false;
         this.safGMS.stop();
         this.unregisterDiagnosticImageSource();
      }

      if ("DOMAIN".equals(this.getPartitionName())) {
      }

      this.stopInternal(false);
   }

   public void halt() throws ServiceFailureException {
      if (this.registered) {
         this.registered = false;
         this.safGMS.stop();
         this.unregisterDiagnosticImageSource();
      }

      if ("DOMAIN".equals(this.getPartitionName())) {
      }

      this.stopInternal(true);
   }

   private void stopInternalWithCIC(boolean force) throws ServiceFailureException {
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var3 = null;

      try {
         this.stopInternal(force);
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

   void stop(boolean forceful) throws ServiceFailureException {
      this.stopInternal(forceful);
   }

   private void stopInternal(boolean forceful) throws ServiceFailureException {
      this.suspend(forceful);
      this.shutdown();
   }

   private void stopAll(boolean forceful) throws ServiceFailureException {
      ServiceFailureException e = null;
      Class var3 = SAFServerService.class;
      synchronized(SAFServerService.class) {
         Iterator itr = services.values().iterator();

         while(true) {
            if (!itr.hasNext()) {
               break;
            }

            SAFServerService safServerService = (SAFServerService)itr.next();

            try {
               safServerService.stopInternalWithCIC(forceful);
               itr.remove();
            } catch (ServiceFailureException var8) {
               if (e == null) {
                  e = var8;
               }
            }
         }
      }

      if (e != null) {
         throw e;
      }
   }

   public void start() throws ServiceFailureException {
      if (!this.registered) {
         GenericServiceManager manager = GenericServiceManager.getManager();
         this.safGMS = manager.register(SAFAgentMBean.class, SAFServiceAdmin.class, true);
         this.registered = true;
         this.safGMS.start();
         this.registerDiagnosticImageSource();
         SAFLogger.logSAFInitialized();
      }

      this.startInternal();
   }

   private void startInternal() throws ServiceFailureException {
      synchronized(this) {
         if (this.state == 4) {
            return;
         }

         this.processAgentServerLifeCycleEvent(4, true);
         this.state = 4;
      }

      SAFLogger.logSAFStarted();
   }

   public void registerTransport(SAFTransport transport) {
      this.safManager.registerTransport(transport);
   }

   public void registerEndpointManager(int type, SAFEndpointManager manager) {
      this.safManager.registerEndpointManager(type, manager);
   }

   private void suspend(boolean force) throws ServiceFailureException {
      synchronized(this) {
         try {
            this.processAgentServerLifeCycleEvent(1, force);
         } finally {
            this.state = 2;
         }
      }

      SAFLogger.logSAFSuspended();
   }

   private boolean isShutdown() {
      return this.state == 16 || this.state == 8;
   }

   synchronized boolean isStarted() {
      return this.state != 0 && this.state != 8 && this.state != 16;
   }

   void checkShutdown() throws ServiceFailureException {
      if (this.isShutdown()) {
         throw new ServiceFailureException("Store-and-forward Service is shutdown.");
      }
   }

   private void shutdown() throws ServiceFailureException {
      synchronized(this) {
         if (this.state == 16) {
            return;
         }

         this.state = 8;
      }

      boolean var11 = false;

      try {
         var11 = true;
         this.processAgentServerLifeCycleEvent(8, true);
         var11 = false;
      } finally {
         if (var11) {
            synchronized(this) {
               this.state = 16;
            }

            SAFLogger.logSAFShutdown();
         }
      }

      synchronized(this) {
         this.state = 16;
      }

      SAFLogger.logSAFShutdown();
   }

   private void processAgentServerLifeCycleEvent(int event, boolean force) throws ServiceFailureException {
      Iterator itr;
      synchronized(this) {
         itr = ((HashMap)this.agents.clone()).values().iterator();
         if (event == 8) {
            this.agents.clear();
         }
      }

      while(itr.hasNext()) {
         SAFAgentAdmin agent = (SAFAgentAdmin)itr.next();
         switch (event) {
            case 1:
               agent.suspend(force);
               break;
            case 4:
               try {
                  agent.resume();
                  break;
               } catch (SAFException var6) {
                  SAFLogger.logErrorResumeAgent(agent.getName(), var6);
                  throw new ServiceFailureException("Failed to start SAF agent '" + agent.getName() + "', due to " + var6.getMessage());
               }
            case 8:
               agent.close();
         }
      }

   }

   public void registerDiagnosticImageSource() {
      ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      imageManager.registerImageSource("SAF", this.IMAGE_SOURCE);
   }

   private void unregisterDiagnosticImageSource() {
      ImageManager im = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);

      try {
         im.unregisterImageSource("SAF");
      } catch (ImageSourceNotFoundException var3) {
      }

   }

   public boolean isTargetsChangeAllowed(SAFAgentMBean agent) {
      TargetMBean[] targets = agent.getTargets();
      if (targets != null && targets.length != 0 && targets[0] instanceof MigratableTargetMBean) {
         SAFAgentAdmin agentAdmin = this.getAgent(agent.getName());
         if (agentAdmin != null && agentAdmin.isActiveForWSRM()) {
            return false;
         } else {
            if (agentAdmin != null && agentAdmin.getMBean() != null) {
               TargetMBean[] oldTargets = agentAdmin.getMBean().getTargets();
               if (oldTargets == null || oldTargets.length == 0) {
                  return true;
               }

               for(int i = 0; i < oldTargets.length; ++i) {
                  if ((oldTargets[i] instanceof ServerMBean || oldTargets[i] instanceof ClusterMBean) && targets[0] instanceof MigratableTargetMBean) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   synchronized void addAgent(SAFAgentAdmin agent) {
      this.agents.put(agent.getDecoratedName(), agent);
   }

   synchronized SAFAgentAdmin getAgent(String name) {
      return (SAFAgentAdmin)this.agents.get(name);
   }

   synchronized SAFAgentAdmin removeAgent(String name) {
      return (SAFAgentAdmin)this.agents.get(name);
   }
}
