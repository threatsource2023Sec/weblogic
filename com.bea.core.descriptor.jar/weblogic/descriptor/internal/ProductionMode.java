package weblogic.descriptor.internal;

import weblogic.descriptor.BootstrapProperties;

public class ProductionMode {
   private boolean enabled = false;
   private static ProductionMode instance = null;

   private ProductionMode() {
      if (System.getProperty(BootstrapProperties.PRODUCTION_MODE.getName()) != null) {
         this.enabled = BootstrapProperties.PRODUCTION_MODE.isEnabled();
      }

   }

   public static ProductionMode instance() {
      if (instance == null) {
         instance = new ProductionMode();
      }

      return instance;
   }

   public boolean getProductionMode() {
      return this.enabled;
   }
}
