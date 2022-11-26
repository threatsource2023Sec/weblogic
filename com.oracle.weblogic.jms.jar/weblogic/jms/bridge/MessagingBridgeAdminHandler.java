package weblogic.jms.bridge;

import weblogic.jms.BridgeLogger;
import weblogic.jms.BridgeService;
import weblogic.jms.PartitionBridgeService;
import weblogic.jms.bridge.internal.BridgeDebug;
import weblogic.jms.bridge.internal.MessagingBridge;
import weblogic.jms.bridge.internal.MessagingBridgeException;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.utils.GenericAdminHandler;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.store.common.PartitionNameUtils;

public class MessagingBridgeAdminHandler implements GenericAdminHandler {
   final BridgeDebug bridgeDebug = new BridgeDebug();

   public void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("MessagingBridgeAdminHandler.prepare(" + deployment + "), this=@" + this.hashCode());
      }

      String bridgeName = PartitionNameUtils.stripDecoratedPartitionName(deployment.getName());
      if (this.getPartitionBridgeService().findBridge(bridgeName) == null) {
         try {
            MessagingBridge bridge = this.createMessagingBridge(deployment);
            bridge.initialize();
            this.getPartitionBridgeService().addMessagingBridge(bridgeName, bridge);
            if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeStartup.debug("Bridge " + bridgeName + " created for partition [" + this.getPartitionBridgeService().getPartitionName() + "]");
            }
         } catch (MessagingBridgeException var5) {
            var5.printStackTrace();
            throwDeploymentException("Failed to add bridge " + bridgeName + " for partition [" + this.getPartitionBridgeService().getPartitionName() + "]", var5);
         }
      } else {
         if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeStartup.debug("Bridge " + bridgeName + " already exists in partition [" + this.getPartitionBridgeService().getPartitionName() + "]");
         }

         throwDeploymentException("Bridge " + bridgeName + " already exists in partition [" + this.getPartitionBridgeService().getPartitionName() + "]", (Exception)null);
      }

   }

   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("MessagingBridgeAdminHandler.activate(" + deployment + "), this=@" + this.hashCode());
      }

      String bridgeName = PartitionNameUtils.stripDecoratedPartitionName(deployment.getName());
      synchronized(this) {
         try {
            this.getPartitionBridgeService().checkShutdown();
         } catch (MessagingBridgeException var8) {
            BridgeLogger.logErrorCreateBridgeWhenShutdown(bridgeName);
            throwDeploymentException("Error activating messaging bridge " + bridgeName + " for partition [" + this.getPartitionBridgeService().getPartitionName() + "]", var8);
         }
      }

      MessagingBridge bridge = this.getPartitionBridgeService().findBridge(bridgeName);
      if (bridge != null) {
         try {
            bridge.resume((MessagingBridgeMBean)deployment.getMBean());
            BridgeLogger.logBridgeDeployed(bridge.getName());
         } catch (MessagingBridgeException var7) {
            BridgeLogger.logErrorStartBridge(bridge.getName(), var7);
            throwDeploymentException("Error activating messaging bridge " + bridge.getName() + " for partition [" + this.getPartitionBridgeService().getPartitionName() + "]", var7);
         }
      } else if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("Error activating Bridge " + bridge + ": instance doesn't exist under partition [" + this.getPartitionBridgeService().getPartitionName() + "]");
      }

   }

   public void deactivate(GenericManagedDeployment deployment) throws UndeploymentException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("MessagingBridgeAdminHandler.deactivate(" + deployment + "), this=@" + this.hashCode());
      }

      if (!this.getPartitionBridgeService().isShutdown()) {
         String bridgeName = PartitionNameUtils.stripDecoratedPartitionName(deployment.getName());
         MessagingBridge bridge = this.getPartitionBridgeService().findBridge(bridgeName);
         if (bridge != null) {
            try {
               bridge.suspend(true);
               bridge.shutdown();
            } catch (Exception var5) {
               throwUndeploymentException("Error deactivating Bridge " + bridge, var5);
            }
         } else if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeStartup.debug("Error removing Bridge " + bridge + ": instance doesn't exist in partition [" + this.getPartitionBridgeService().getPartitionName() + "]");
         }

      }
   }

   public void unprepare(GenericManagedDeployment deployment) throws UndeploymentException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("MessagingBridgeAdminHandler.unprepare(" + deployment + "), this=@" + this.hashCode());
      }

      if (!this.getPartitionBridgeService().isShutdown()) {
         String bridgeName = PartitionNameUtils.stripDecoratedPartitionName(deployment.getName());
         MessagingBridge bridge = this.getPartitionBridgeService().findBridge(bridgeName);
         if (bridge != null) {
            try {
               this.getPartitionBridgeService().removeMessagingBridge(bridgeName);
            } catch (Exception var5) {
               throwUndeploymentException("Error unpreparing Bridge " + bridge, var5);
            }
         } else if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeStartup.debug("Error removing Bridge " + bridge + ": instance doesn't exist in partition [" + this.getPartitionBridgeService().getPartitionName() + "]");
         }

      }
   }

   private static void throwDeploymentException(String info, Exception e) throws DeploymentException {
      DeploymentException de;
      if (e != null) {
         de = new DeploymentException(info, e);
      } else {
         de = new DeploymentException(info);
      }

      throw de;
   }

   private static void throwUndeploymentException(String info, Exception e) throws UndeploymentException {
      UndeploymentException ude;
      if (e != null) {
         ude = new UndeploymentException(info, e);
      } else {
         ude = new UndeploymentException(info);
      }

      throw ude;
   }

   private MessagingBridge createMessagingBridge(GenericManagedDeployment deployment) throws DeploymentException {
      String bridgeName = PartitionNameUtils.stripDecoratedPartitionName(deployment.getName());
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("Creating bridge " + bridgeName + " for partition [" + this.getPartitionBridgeService().getPartitionName() + "]");
      }

      synchronized(this) {
         try {
            this.getPartitionBridgeService().checkShutdown();
         } catch (MessagingBridgeException var6) {
            BridgeLogger.logErrorCreateBridgeWhenShutdown(bridgeName);
            throwDeploymentException("Error creating messaging bridge " + bridgeName + " for partition [" + this.getPartitionBridgeService().getPartitionName() + "]", var6);
         }
      }

      MessagingBridge bridge = null;

      try {
         bridge = new MessagingBridge(deployment, this.getPartitionBridgeService());
      } catch (Exception var8) {
         if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeStartup.debug("Error creating bridge " + bridgeName + " for partition [" + this.getPartitionBridgeService().getPartitionName() + "]", var8);
         }

         BridgeLogger.logErrorCreateBridge(bridgeName, var8);
         throwDeploymentException("Error deploying messaging bridge " + bridgeName + " to partition [" + this.getPartitionBridgeService().getPartitionName() + "]", var8);
      }

      return bridge;
   }

   private synchronized PartitionBridgeService getPartitionBridgeService() {
      try {
         return BridgeService.getPartitionBridgeService();
      } catch (Exception var2) {
         var2.printStackTrace(System.out);
         return null;
      }
   }
}
