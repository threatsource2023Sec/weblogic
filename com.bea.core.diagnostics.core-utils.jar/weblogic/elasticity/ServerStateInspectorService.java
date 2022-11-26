package weblogic.elasticity;

import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.nodemanager.mbean.NodeManagerRuntime;

@Service(
   name = "ServerStateInspectorService"
)
public class ServerStateInspectorService implements ServerStateInspector {
   @Inject
   private RuntimeAccess runtimeAccess;

   public boolean isNodemanagerForServerReachable(String serverName) {
      if (this.runtimeAccess == null) {
         return false;
      } else {
         DomainMBean domain = this.runtimeAccess.getDomain();
         ServerMBean server = domain.lookupServer(serverName);
         if (server == null) {
            return false;
         } else {
            MachineMBean machine = server.getMachine();
            if (machine == null) {
               return false;
            } else {
               NodeManagerMBean nodemanager = machine.getNodeManager();
               if (nodemanager == null) {
                  return false;
               } else {
                  boolean reachable = NodeManagerRuntime.isReachable(nodemanager);
                  return reachable;
               }
            }
         }
      }
   }
}
