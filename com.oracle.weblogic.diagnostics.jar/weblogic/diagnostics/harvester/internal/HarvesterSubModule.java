package weblogic.diagnostics.harvester.internal;

import weblogic.application.ApplicationContext;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.harvester.HarvesterCollector;
import weblogic.diagnostics.harvester.HarvesterCollectorFactory;
import weblogic.diagnostics.module.WLDFBaseSubModule;
import weblogic.diagnostics.module.WLDFModuleException;
import weblogic.diagnostics.module.WLDFSubModule;

public class HarvesterSubModule extends WLDFBaseSubModule {
   private HarvesterCollector collector;
   private WLDFResourceBean resourceBean;

   private HarvesterSubModule() {
   }

   public static final WLDFSubModule createInstance() {
      return new HarvesterSubModule();
   }

   public void init(String partition, ApplicationContext appCtx, WLDFResourceBean wldfResource) throws WLDFModuleException {
      super.init(partition, appCtx, wldfResource);
      this.resourceBean = wldfResource;
   }

   public void prepare() throws WLDFModuleException {
   }

   public void activate() throws WLDFModuleException {
      HarvesterCollectorFactory factory = HarvesterCollectorFactory.getFactoryInstance(this.getPartitionName());
      this.collector = factory.findOrCreateHarvesterCollector(this.resourceBean);
      this.collector.initialize();
      this.collector.enable();
   }

   public void deactivate() throws WLDFModuleException {
      if (this.collector != null) {
         this.collector.disable();
      }

   }

   public void unprepare() throws WLDFModuleException {
   }

   public void destroy() throws WLDFModuleException {
      HarvesterCollectorFactory factory = HarvesterCollectorFactory.getFactoryInstance(this.getPartitionName());
      factory.destroyHarvesterCollector(this.resourceBean);
      this.collector = null;
   }

   public void prepareUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
   }

   public void activateUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
      this.deactivate();
      this.destroy();
      this.resourceBean = proposedBean;
      this.activate();
   }

   public void rollbackUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) {
   }
}
