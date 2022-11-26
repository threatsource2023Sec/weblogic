package weblogic.management.mbeans.custom;

import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class NodeManager extends ConfigurationMBeanCustomizer {
   public NodeManager(ConfigurationMBeanCustomized base) {
      super(base);
   }

   protected NodeManagerMBean getMBean() {
      return (NodeManagerMBean)this.getMbean();
   }
}
