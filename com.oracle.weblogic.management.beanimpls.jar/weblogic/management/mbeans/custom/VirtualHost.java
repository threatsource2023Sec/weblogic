package weblogic.management.mbeans.custom;

import java.util.HashSet;
import java.util.Set;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public final class VirtualHost extends WebServer {
   private static final long serialVersionUID = -1880586135310564482L;

   public VirtualHost(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public Set getServerNames() {
      TargetMBean[] targets = ((VirtualHostMBean)this.getMbean()).getTargets();
      Set serverNames = new HashSet();

      for(int j = 0; j < targets.length; ++j) {
         serverNames.addAll(targets[j].getServerNames());
      }

      return serverNames;
   }
}
