package sample.plugin.internal;

import weblogic.nodemanager.plugin.DefaultProcessManagementPluginImpl;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;

public class DefaultProcessManagementPluginSample extends DefaultProcessManagementPluginImpl {
   public ProcessManagementPlugin.SystemComponentManager createSystemComponentManager(String name) {
      return new DefaultSystemComponentManagerSample(name, this.getProvider());
   }
}
