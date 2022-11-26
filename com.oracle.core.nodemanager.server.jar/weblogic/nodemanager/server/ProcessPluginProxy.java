package weblogic.nodemanager.server;

import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.plugin.SimpleProcessPlugin;

public class ProcessPluginProxy {
   private SimpleProcessPlugin simpleProcessPlugin;
   private ProcessManagementPlugin processManagementPlugin;
   private boolean monitoringSupported = true;

   public ProcessPluginProxy(ProcessManagementPlugin processManagementPlugin) {
      this.processManagementPlugin = processManagementPlugin;
      this.monitoringSupported = true;
   }

   public ProcessPluginProxy(SimpleProcessPlugin simpleProcessPlugin) {
      this.simpleProcessPlugin = simpleProcessPlugin;
      this.monitoringSupported = false;
   }

   public SimpleProcessPlugin getSimpleProcessPlugin() {
      return this.simpleProcessPlugin;
   }

   public ProcessManagementPlugin getProcessManagementPlugin() {
      return this.processManagementPlugin;
   }

   public boolean isMonitoringSupported() {
      return this.monitoringSupported;
   }
}
