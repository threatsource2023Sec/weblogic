package weblogic.management.patching.agent;

import weblogic.admin.plugin.Plugin;
import weblogic.admin.plugin.PluginFactory;
import weblogic.nodemanager.plugin.NMEnvironment;

public class ZdtAgentPluginFactory implements PluginFactory {
   private static final String ZDT_AGENT = "ZdtAgent";
   private NMEnvironment nmEnv;

   public String getSystemComponentType() {
      return "ZdtAgent";
   }

   public String[] getSupportedPluginTypes() {
      return new String[]{ZdtPluginType.UPDATE_ORACLE_HOME.displayString, ZdtPluginType.UPDATE_APPLICATION.displayString};
   }

   public Plugin createPlugin(String pluginType) {
      ZdtPluginType zdtPluginType = ZdtPluginType.get(pluginType);
      switch (zdtPluginType) {
         case UPDATE_ORACLE_HOME:
            return new ZdtUpdateOracleHomePlugin(this.nmEnv.getNMLogger());
         case UPDATE_APPLICATION:
            return new ZdtUpdateApplicationPlugin(this.nmEnv.getNMLogger());
         default:
            throw new UnsupportedOperationException("TODO: this operation is not supported");
      }
   }

   public void setNMEnvironment(NMEnvironment nmEnvironment) {
      this.nmEnv = nmEnvironment;
   }
}
