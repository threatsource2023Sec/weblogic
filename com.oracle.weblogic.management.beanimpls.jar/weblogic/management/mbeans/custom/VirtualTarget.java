package weblogic.management.mbeans.custom;

import java.util.HashSet;
import java.util.Set;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class VirtualTarget extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = -2796604490187331969L;

   public VirtualTarget(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public Set getServerNames() {
      TargetMBean[] targets = ((VirtualTargetMBean)this.getMbean()).getTargets();
      Set serverNames = new HashSet();
      TargetMBean[] var3 = targets;
      int var4 = targets.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean target = var3[var5];
         serverNames.addAll(target.getServerNames());
      }

      return serverNames;
   }
}
