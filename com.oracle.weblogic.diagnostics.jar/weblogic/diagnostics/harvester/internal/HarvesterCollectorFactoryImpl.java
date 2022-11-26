package weblogic.diagnostics.harvester.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.harvester.HarvesterCollector;
import weblogic.diagnostics.harvester.HarvesterCollectorFactory;

public class HarvesterCollectorFactoryImpl extends HarvesterCollectorFactory {
   private String partitionName = "";
   private Map collectorsMap = new ConcurrentHashMap(8);

   public HarvesterCollectorFactoryImpl(String name) {
      this.partitionName = name;
   }

   public synchronized HarvesterCollector findOrCreateHarvesterCollector(WLDFResourceBean bean) {
      HarvesterCollector hc = (HarvesterCollector)this.collectorsMap.get(bean);
      if (hc == null) {
         hc = new HarvesterCollectorImpl(this.partitionName, bean);
         this.collectorsMap.put(bean, hc);
      }

      return (HarvesterCollector)hc;
   }

   public void destroyHarvesterCollector(WLDFResourceBean bean) {
      HarvesterCollector found = (HarvesterCollector)this.collectorsMap.remove(bean);
      if (found != null) {
         ((HarvesterCollectorImpl)found).destroy();
      }

   }

   public HarvesterCollector lookupHarvesterCollector(WLDFResourceBean bean) {
      return (HarvesterCollector)this.collectorsMap.get(bean);
   }

   public HarvesterCollector[] listHarvesterCollectors() {
      HarvesterCollector[] result = new HarvesterCollector[0];
      if (this.collectorsMap != null) {
         result = (HarvesterCollector[])this.collectorsMap.values().toArray(new HarvesterCollector[this.collectorsMap.size()]);
      }

      return result;
   }

   public int getNumActiveHarvesterModules() {
      return this.collectorsMap == null ? 0 : this.collectorsMap.size();
   }
}
