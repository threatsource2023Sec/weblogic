package weblogic.jms;

import java.util.HashMap;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.bridge.MessagingBridgeAdminHandler;
import weblogic.jms.bridge.internal.BridgeDebug;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.utils.GenericManagedService;
import weblogic.management.utils.GenericServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class BridgeService extends AbstractServerService {
   @Inject
   @Named("JMSServiceActivator")
   private ServerService dependencyOnJMSServiceActivator;
   private static HashMap bridgeServices = new HashMap();
   private static BridgeService singleton;
   private GenericManagedService bridgeAdminManager;

   private static void setSingleton(BridgeService theOne) {
      singleton = theOne;
   }

   public BridgeService() throws ServiceFailureException {
      setSingleton(this);
      this.bridgeAdminManager = GenericServiceManager.getManager().register(MessagingBridgeMBean.class, MessagingBridgeAdminHandler.class, true);
      getPartitionBridgeService();
   }

   public static BridgeService getService() {
      return singleton;
   }

   public void start() throws ServiceFailureException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeService.start() is called.");
      }

      this.bridgeAdminManager.start();
   }

   public void stop() throws ServiceFailureException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeService.stop() is called.");
      }

      this.stopInternal(false);
   }

   public void halt() throws ServiceFailureException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeService.halt() is called.");
      }

      this.stopInternal(true);
   }

   private void stopInternal(boolean force) throws ServiceFailureException {
      this.bridgeAdminManager.stop();
      PartitionBridgeService service = getPartitionBridgeService();
      service.stop(force);
   }

   public static PartitionBridgeService getPartitionBridgeService() throws ServiceFailureException {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeService.getPartitionBridgeService for partition: " + partitionName);
      }

      Class var1 = BridgeService.class;
      synchronized(BridgeService.class) {
         PartitionBridgeService service = getPartitionBridgeService(partitionName);
         if (service == null) {
            service = new PartitionBridgeService(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext());
            bridgeServices.put(partitionName, service);
            service.start();
         }

         return service;
      }
   }

   public static synchronized PartitionBridgeService getPartitionBridgeService(String pName) {
      return (PartitionBridgeService)bridgeServices.get(pName);
   }

   public static synchronized PartitionBridgeService removePartitionBridgeService(String pName) {
      return (PartitionBridgeService)bridgeServices.remove(pName);
   }
}
