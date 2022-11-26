package weblogic.admin.plugin;

import weblogic.nodemanager.plugin.NMEnvironment;

public interface PluginFactory {
   String CONFIGURATION = "CONFIGURATION";
   String REPLICATION = "REPLICATION";
   String MONITORING = "MONITORING";
   String INVOCATION = "INVOCATION";
   String PROCESS = "PROCESS";

   String getSystemComponentType();

   String[] getSupportedPluginTypes();

   Plugin createPlugin(String var1);

   void setNMEnvironment(NMEnvironment var1);
}
