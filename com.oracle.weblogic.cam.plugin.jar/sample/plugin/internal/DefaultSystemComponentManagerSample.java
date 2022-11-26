package sample.plugin.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import weblogic.nodemanager.plugin.DefaultSystemComponentManagerImpl;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.plugin.Provider;

public class DefaultSystemComponentManagerSample extends DefaultSystemComponentManagerImpl {
   public DefaultSystemComponentManagerSample(String name, Provider provider) {
      super(name, provider);
   }

   public List specifyCmdLine(Properties startProps) {
      ArrayList cmd = new ArrayList();
      cmd.add("java or other");
      cmd.add("fooSample executable");
      cmd.add(this.getInstanceName());
      cmd.add("some other information specific to the commandline");
      return cmd;
   }

   public Map specifyEnvironmentValues(Properties startProps) {
      Map env = super.specifyEnvironmentValues(startProps);
      env.put("SOME_ENV_VAR", "some_env_value");
      return env;
   }

   public ProcessManagementPlugin.SystemComponentState getState() {
      return this.queryActualState();
   }

   private ProcessManagementPlugin.SystemComponentState queryActualState() {
      return null;
   }
}
