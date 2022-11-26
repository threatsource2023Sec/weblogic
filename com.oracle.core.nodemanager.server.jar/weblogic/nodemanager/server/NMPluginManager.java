package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.admin.plugin.Plugin;
import weblogic.admin.plugin.PluginManager;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.plugin.ConfigurationPlugin;
import weblogic.nodemanager.plugin.InvocationPlugin;
import weblogic.nodemanager.plugin.MonitoringPlugin;
import weblogic.nodemanager.plugin.NMEnvironment;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.plugin.Provider;
import weblogic.nodemanager.plugin.SimpleProcessPlugin;

class NMPluginManager {
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private final PluginManager pluginManager;
   private final Provider provider;
   private final HashMap configPlugins = new HashMap();
   private final HashMap monitoringPlugins = new HashMap();
   private final HashMap invocationPlugins = new HashMap();
   private final HashMap processManagementPlugins = new HashMap();
   private final HashMap configurationManagers = new HashMap();
   private final ConcurrentHashMap nmEnvironments = new ConcurrentHashMap();

   NMPluginManager(PluginManager pluginManager, Provider provider) {
      if (pluginManager == null) {
         throw new IllegalArgumentException();
      } else {
         this.pluginManager = pluginManager;
         if (provider == null) {
            throw new IllegalArgumentException();
         } else {
            this.provider = provider;
         }
      }
   }

   ConfigurationPlugin getConfigurationPlugin(String systemComponentType) throws IOException {
      synchronized(this.configPlugins) {
         ConfigurationPlugin plugin = (ConfigurationPlugin)this.configPlugins.get(systemComponentType);
         if (plugin == null) {
            plugin = (ConfigurationPlugin)this.createPlugin(systemComponentType, "REPLICATION");
            if (plugin == null) {
               throw new IOException(nmText.getPluginNotFoundErrorMsg(systemComponentType, "REPLICATION"));
            }

            plugin.init(this.provider);
            this.configPlugins.put(systemComponentType, plugin);
         }

         return plugin;
      }
   }

   MonitoringPlugin getMonitoringPlugin(String systemComponentType) throws IOException {
      synchronized(this.monitoringPlugins) {
         MonitoringPlugin plugin = (MonitoringPlugin)this.monitoringPlugins.get(systemComponentType);
         if (plugin == null) {
            plugin = (MonitoringPlugin)this.createPlugin(systemComponentType, "MONITORING");
            if (plugin == null) {
               throw new IOException(nmText.getPluginNotFoundErrorMsg(systemComponentType, "MONITORING"));
            }

            plugin.init(this.provider);
            this.monitoringPlugins.put(systemComponentType, plugin);
         }

         return plugin;
      }
   }

   InvocationPlugin getInvocationPlugin(String pluginType, String systemComponentType) throws IOException {
      synchronized(this.invocationPlugins) {
         HashMap map = (HashMap)this.invocationPlugins.get(pluginType);
         if (map == null) {
            map = new HashMap();
         }

         InvocationPlugin plugin = (InvocationPlugin)map.get(systemComponentType);
         if (plugin == null) {
            plugin = (InvocationPlugin)this.createPlugin(systemComponentType, pluginType);
            if (plugin == null) {
               throw new IOException(nmText.getPluginNotFoundErrorMsg(systemComponentType, pluginType));
            }

            plugin.init(this.provider);
            map.put(systemComponentType, plugin);
            this.invocationPlugins.put(pluginType, map);
         }

         return plugin;
      }
   }

   ProcessPluginProxy getProcessPluginProxy(String systemComponentType) throws IOException {
      synchronized(this.processManagementPlugins) {
         ProcessPluginProxy pluginProxy = (ProcessPluginProxy)this.processManagementPlugins.get(systemComponentType);
         if (pluginProxy == null) {
            Plugin plugin = this.createPlugin(systemComponentType, "PROCESS");
            if (plugin == null) {
               throw new IOException(nmText.getPluginNotFoundErrorMsg(systemComponentType, "PROCESS"));
            }

            if (plugin instanceof SimpleProcessPlugin) {
               SimpleProcessPlugin simpleProcessPlugin = (SimpleProcessPlugin)plugin;
               simpleProcessPlugin.init(this.provider);
               pluginProxy = new ProcessPluginProxy(simpleProcessPlugin);
            } else {
               ProcessManagementPlugin processManagementPlugin = (ProcessManagementPlugin)plugin;
               processManagementPlugin.init(this.provider);
               pluginProxy = new ProcessPluginProxy(processManagementPlugin);
            }

            this.processManagementPlugins.put(systemComponentType, pluginProxy);
         }

         return pluginProxy;
      }
   }

   private Plugin createPlugin(String systemComponentType, String pluginType) {
      Object nmEnv;
      if (this.nmEnvironments.containsKey(systemComponentType)) {
         nmEnv = (NMEnvironment)this.nmEnvironments.get(systemComponentType);
      } else {
         nmEnv = new NMEnvironmentImpl(systemComponentType);
         this.nmEnvironments.put(systemComponentType, nmEnv);
      }

      return this.pluginManager.createPlugin(systemComponentType, pluginType, (NMEnvironment)nmEnv);
   }

   public ConfigurationManager getConfigurationManager(String systemComponentType) throws IOException {
      synchronized(this.configurationManagers) {
         ConfigurationManager manager = (ConfigurationManager)this.configurationManagers.get(systemComponentType);
         if (manager == null) {
            manager = this.createConfigurationManager(systemComponentType);
            this.configurationManagers.put(systemComponentType, manager);
         }

         return manager;
      }
   }

   public Collection getConfigurationManagers() {
      synchronized(this.configurationManagers) {
         return new ArrayList(this.configurationManagers.values());
      }
   }

   private ConfigurationManager createConfigurationManager(String systemComponentType) throws IOException {
      ConfigurationPlugin plugin = this.getConfigurationPlugin(systemComponentType);
      return new ConfigurationManager(plugin, systemComponentType, this.provider.getDomainDirectory());
   }
}
