package com.oracle.weblogic.lifecycle.config.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;

@Service
public class PluginConfigManager {
   @Inject
   public IterableProvider allPluginServices;
   @Inject
   private LifecycleConfigManager lcm;

   public PluginConfigService createPlugin(String name, String path, String type) {
      Map map = new HashMap();
      map.put("name", name);
      map.put("path", path);
      map.put("type", type);
      this.createPlugin(map);
      return this.getPluginByName(name);
   }

   public void createPlugin(Map map) {
      this.lcm.add("/lifecycle-config/plugins", PluginConfigService.getInstanceId(map), map);
   }

   public PluginConfigService deletePlugin(PluginConfigService pluginService) {
      this.lcm.delete("/lifecycle-config/plugins", pluginService.getInstanceId());
      return pluginService;
   }

   public List getPlugins() {
      List list = new ArrayList();
      Iterator var2 = this.allPluginServices.iterator();

      while(var2.hasNext()) {
         PluginConfigService ps = (PluginConfigService)var2.next();
         if (ps.getName() != null) {
            list.add(ps);
         }
      }

      return list;
   }

   public PluginConfigService getPluginByName(String name) {
      Iterator var2 = this.allPluginServices.iterator();

      PluginConfigService pluginService;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         pluginService = (PluginConfigService)var2.next();
      } while(!name.equals(pluginService.getName()));

      return pluginService;
   }
}
