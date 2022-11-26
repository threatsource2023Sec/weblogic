package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.jmx.MBeanHarvesterManager;
import weblogic.diagnostics.harvester.HarvesterRuntimeException;

class JMXHarvesterControlImpl extends BaseHarvesterControlImpl {
   private static HarvesterExecutor EXECUTOR = HarvesterExecutor.getInstance();
   private static MBeanHarvesterManager harvesterLauncher;
   private JMXHarvesterConfig config;

   public JMXHarvesterControlImpl(JMXHarvesterConfig cfg, boolean isDefault) {
      super(cfg.getHarvesterName(), cfg.getNamespace(), DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST, isDefault);
      this.config = cfg;
   }

   public JMXHarvesterControlImpl(JMXHarvesterConfig cfg, DelegateHarvesterControl.ActivationPolicy policy) {
      super(cfg.getHarvesterName(), cfg.getNamespace(), policy, false);
      this.config = cfg;
   }

   private void initHarvesterManager() {
      if (harvesterLauncher == null) {
         harvesterLauncher = new MBeanHarvesterManager();
         harvesterLauncher.prepareHarvesterManager();
         harvesterLauncher.activateHarvesterManager();
      }

   }

   public void activate() {
      synchronized(this) {
         if (!this.isActive()) {
            this.initHarvesterManager();

            try {
               Harvester harvester = harvesterLauncher.allocateHarvester(this.config.getHarvesterName(), this.config.getNamespace(), this.config.getMbeanServer(), this.config.getCategorizer(), (String[])null, EXECUTOR, (Throwable[])null);
               harvester.setAttributeValidationEnabled(this.isAttributeValidationEnabled());
               harvester.setRemoveAttributesWithProblems(false);
               this.setDelegate(harvester);
               this.setActive(true);
            } catch (Exception var4) {
               throw new HarvesterRuntimeException(var4);
            }
         }

      }
   }

   public void deactivate() {
      if (this.isActive()) {
         super.deactivate();
         Harvester delegate = this.getDelegate();
         if (delegate != null) {
            delegate.deallocate();
         }
      }

   }
}
