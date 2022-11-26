package weblogic.management.mbeans.custom;

import weblogic.management.configuration.ManagedExternalServerStartMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class SystemComponent extends ConfigurationMBeanCustomizer {
   public SystemComponent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   private SystemComponentMBean getServer() {
      return (SystemComponentMBean)this.getMbean();
   }

   public ManagedExternalServerStartMBean getManagedExternalServerStart() {
      return this.getServer().getSystemComponentStart();
   }

   public String getManagedExternalType() {
      return this.getServer().getComponentType();
   }
}
