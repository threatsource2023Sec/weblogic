package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Plugin;
import com.oracle.weblogic.lifecycle.config.Plugins;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class PluginsCustomizer {
   @Inject
   private XmlService xmlService;

   public Plugin createPlugin(Plugins plugins, String name, String type, String path) {
      Plugin plugin = (Plugin)this.xmlService.createBean(Plugin.class);
      plugin.setName(name);
      plugin.setType(type);
      plugin.setPath(path);
      return plugins.addPlugin(plugin);
   }

   public Plugin getPluginByName(Plugins plugins, String name) {
      return plugins.lookupPlugin(name);
   }

   public Plugin deletePlugin(Plugins plugins, Plugin plugin) {
      return plugins.removePlugin(plugin);
   }
}
