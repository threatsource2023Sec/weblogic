package weblogic.diagnostics.runtimecontrol.internal;

import java.util.Map;
import javax.management.JMException;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.harvester.HarvesterCollector;
import weblogic.diagnostics.harvester.HarvesterCollectorFactory;
import weblogic.diagnostics.runtimecontrol.RuntimeProfileDriver;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFHarvesterManagerRuntimeMBean;

public class HarvesterManagerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFHarvesterManagerRuntimeMBean {
   private WLDFResourceBean resource;

   HarvesterManagerRuntimeMBeanImpl(WLDFResourceBean resource, RuntimeMBean parent) throws ManagementException {
      super(resource.getHarvester().getName(), parent);
      this.resource = resource;
   }

   public long getAverageSamplingTime() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : collector.getStatistics().getAverageSamplingTimeNanos();
   }

   public long getCurrentDataSampleCount() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : (long)collector.getStatistics().getCurrentDataSampleCount();
   }

   public long getCurrentSnapshotElapsedTime() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : collector.getStatistics().getCurrentSnapshotElapsedTimeNanos();
   }

   public long getCurrentSnapshotStartTime() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? -1L : collector.getStatistics().getCurrentSnapshotStartTimeMillis();
   }

   public long getMaximumSamplingTime() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : collector.getStatistics().getMaximumSamplingTimeNanos();
   }

   public long getMinimumSamplingTime() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : collector.getStatistics().getMinimumSamplingTimeNanos();
   }

   public long getTotalDataSampleCount() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : collector.getStatistics().getTotalConfiguredDataSampleCount();
   }

   public long getTotalSamplingCycles() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : (long)collector.getStatistics().getTotalSamplingCycles();
   }

   public long getTotalSamplingTime() {
      HarvesterCollector collector = this.getCollector();
      return collector == null ? 0L : collector.getStatistics().getTotalSamplingTimeNanos();
   }

   public Map retrieveSnapshot() throws JMException {
      Map snapshot = null;

      try {
         RuntimeProfileDriver.getInstance().deploy(this.resource);
         HarvesterCollector collector = this.getCollector();
         if (collector != null) {
            snapshot = collector.retrieveSnapshot();
         }

         return snapshot;
      } catch (Exception var4) {
         JMException ex = new JMException(var4.getMessage());
         ex.initCause(var4);
         throw ex;
      }
   }

   private HarvesterCollector getCollector() {
      return HarvesterCollectorFactory.getFactoryInstance().lookupHarvesterCollector(this.resource);
   }
}
