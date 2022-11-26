package weblogic.management.mbeans.custom;

import java.util.HashSet;
import java.util.Set;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class SAFAgent extends ConfigurationMBeanCustomizer {
   public SAFAgent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public Set getServerNames() {
      TargetMBean[] targets = ((SAFAgentMBean)this.getMbean()).getTargets();
      Set serverNames = new HashSet();

      for(int j = 0; j < targets.length; ++j) {
         serverNames.addAll(targets[j].getServerNames());
      }

      return serverNames;
   }
}
