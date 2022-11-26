package weblogic.nodemanager.server;

import java.io.IOException;
import weblogic.nodemanager.common.ConfigException;

public enum ServerManagerFactory {
   instance;

   public ServerManagerI createServerManager(DomainManager domainMgr, String name, String serverType) throws IOException, ConfigException {
      assert domainMgr != null;

      assert name != null;

      assert serverType != null : "server type null";

      Customizer customizer = null;
      ServerManagerI serverMgr = null;
      if ("WebLogic".equals(serverType)) {
         customizer = new WLSCustomizer(domainMgr.getNMServer().getConfig().getProcessControl());
      } else if ("Coherence".equals(serverType)) {
         customizer = new CoherenceCustomizer(domainMgr.getNMServer().getConfig().getProcessControl());
      } else {
         ProcessPluginProxy plugin = domainMgr.getPluginManager().getProcessPluginProxy(serverType);
         if (plugin.isMonitoringSupported()) {
            customizer = new SystemComponentCustomizer(plugin.getProcessManagementPlugin());
         } else {
            serverMgr = new SimpleServerManager(plugin.getSimpleProcessPlugin(), domainMgr, name);
         }
      }

      if (customizer != null) {
         serverMgr = new ServerManager((Customizer)customizer, domainMgr, name, serverType);
      }

      return (ServerManagerI)serverMgr;
   }
}
