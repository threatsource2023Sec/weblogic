package weblogic.management;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.ConfigLogger;
import weblogic.management.internal.Utils;
import weblogic.management.provider.UpdateException;

public class SpecialPropertiesProcessor {
   public static void updateConfiguration(DomainMBean root) throws UpdateException {
      updateConfiguration(root, false);
   }

   public static void updateConfiguration(DomainMBean root, boolean inUpdate) throws UpdateException {
      String serverName = Utils.findServerName(root);
      if (serverName == null) {
         throw new UpdateException(ConfigLogger.logCouldNotDetermineServerNameLoggable().getMessage());
      } else {
         ServerMBean server = root.lookupServer(serverName);
         if (server == null) {
            if (inUpdate) {
               throw new UpdateException(ConfigLogger.logCannotDeleteServerWhenRunningLoggable(serverName, root.getName()).getMessage());
            }
         } else {
            boolean isCluster = server.getCluster() != null;
            if (isCluster) {
               SpecialPropertiesHelper.configureFromSystemProperties(server, isCluster, false);
            } else {
               SpecialPropertiesHelper.configureFromSystemProperties(server);
            }

         }
      }
   }
}
