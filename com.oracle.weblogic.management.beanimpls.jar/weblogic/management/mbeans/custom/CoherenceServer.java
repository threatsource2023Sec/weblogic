package weblogic.management.mbeans.custom;

import weblogic.management.configuration.CoherenceServerMBean;
import weblogic.management.configuration.ManagedExternalServerStartMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class CoherenceServer extends ConfigurationMBeanCustomizer {
   public CoherenceServer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   private CoherenceServerMBean getServer() {
      return (CoherenceServerMBean)this.getMbean();
   }

   public ManagedExternalServerStartMBean getManagedExternalServerStart() {
      return this.getServer().getCoherenceServerStart();
   }

   public String getManagedExternalType() {
      return "Coherence";
   }
}
