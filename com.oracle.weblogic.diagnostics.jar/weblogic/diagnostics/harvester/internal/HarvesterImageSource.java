package weblogic.diagnostics.harvester.internal;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.harvester.HarvesterCollector;
import weblogic.diagnostics.harvester.HarvesterCollectorFactory;
import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;
import weblogic.diagnostics.harvester.HarvesterDataSample;
import weblogic.diagnostics.harvester.LogSupport;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.descriptor.HarvesterImageSourceBean;
import weblogic.diagnostics.image.descriptor.HarvesterModuleBean;
import weblogic.diagnostics.image.descriptor.HarvesterModuleStatisticsBean;
import weblogic.diagnostics.image.descriptor.HarvesterStatisticsBean;
import weblogic.diagnostics.utils.DateUtils;
import weblogic.management.runtime.WLDFPartitionHarvesterRuntimeMBean;

public class HarvesterImageSource implements ImageSource {
   private boolean timeoutRequested;
   private WLDFPartitionHarvesterRuntimeMBean harvesterRuntime;
   private static final long NANOS_PER_MILLI = 1000000L;

   public HarvesterImageSource(WLDFPartitionHarvesterRuntimeMBean runtimeMBean) {
      this.setHarvester(runtimeMBean);
   }

   private void setHarvester(WLDFPartitionHarvesterRuntimeMBean s) {
      this.harvesterRuntime = s;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      DescriptorManager dm = new DescriptorManager();
      Descriptor desc = dm.createDescriptorRoot(HarvesterImageSourceBean.class);
      DescriptorBean rootBean = desc.getRootBean();
      HarvesterImageSourceBean imageSourceBean = (HarvesterImageSourceBean)rootBean;
      HarvesterCollectorFactory factory = HarvesterCollectorFactory.getFactoryInstance();
      this.recordAggregateStatistics(imageSourceBean, factory);
      HarvesterCollector[] collectors = factory.listHarvesterCollectors();
      HarvesterCollector[] var8 = collectors;
      int var9 = collectors.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         HarvesterCollector collector = var8[var10];
         if (this.timeoutRequested) {
            break;
         }

         this.writeHarvesterModuleImage(collector, imageSourceBean);
      }

      try {
         dm.writeDescriptorAsXML(rootBean.getDescriptor(), out);
      } catch (Exception var12) {
         throw new ImageSourceCreationException(var12);
      }
   }

   private void recordAggregateStatistics(HarvesterImageSourceBean imageSourceBean, HarvesterCollectorFactory factory) {
      HarvesterStatisticsBean globalStats = imageSourceBean.createStatistics();
      globalStats.setActiveModulesCount(factory.getNumActiveHarvesterModules());
      globalStats.setAverageSamplingTime(this.harvesterRuntime.getAverageSamplingTime());
      globalStats.setMaximumSamplingTime(this.harvesterRuntime.getMaximumSamplingTime());
      globalStats.setMinimumSamplingTime(this.harvesterRuntime.getMinimumSamplingTime());

      try {
         globalStats.setTotalDataSampleCount(this.harvesterRuntime.getTotalConfiguredDataSampleCount() + this.harvesterRuntime.getTotalImplicitDataSampleCount());
      } catch (Exception var5) {
         globalStats.setTotalDataSampleCount(0L);
      }

      globalStats.setTotalSamplingCycles(this.harvesterRuntime.getTotalSamplingCycles());
   }

   public void timeoutImageCreation() {
      this.timeoutRequested = true;
   }

   private void writeHarvesterModuleImage(HarvesterCollector collector, HarvesterImageSourceBean bean) {
      try {
         HarvesterModuleBean moduleBean = bean.createHarvesterModule();
         moduleBean.setModuleName(collector.getName());
         HarvesterSnapshot snapShot = ((HarvesterCollectorImpl)collector).getCurrentSnapshot();
         if (snapShot != null) {
            long timeStamp = snapShot.getSnapshotStartTimeMillis();
            timeStamp *= 1000000L;
            moduleBean.setHarvesterCycleStartTime(DateUtils.nanoDateToString(timeStamp));
            long elapsedTime = snapShot.getSnapshotElapsedTimeNanos();
            moduleBean.setHarvesterCycleDurationNanos(elapsedTime);
            Collection samples = snapShot.getHarvesterDataSamples();
            Iterator i = samples.iterator();

            while(i.hasNext()) {
               HarvesterDataSample sample = (HarvesterDataSample)i.next();
               moduleBean.addHarvesterSample(sample.toStringLong());
            }
         }

         this.writeCollectorStats(collector, moduleBean);
      } catch (Exception var12) {
         LogSupport.logUnexpectedException("Error in HarvesterImageSource.", var12);
      }

   }

   private void writeCollectorStats(HarvesterCollector collector, HarvesterModuleBean moduleBean) {
      HarvesterModuleStatisticsBean moduleStats = moduleBean.createModuleStatistics();
      HarvesterCollectorStatistics collectorStats = collector.getStatistics();
      moduleStats.setAverageSamplingTime(collectorStats.getAverageSamplingTimeNanos());
      moduleStats.setCurrentDataSampleCount((long)collectorStats.getCurrentDataSampleCount());
      moduleStats.setMaximumSamplingTime(collectorStats.getMaximumSamplingTimeNanos());
      moduleStats.setMinimumSamplingTime(collectorStats.getMinimumSamplingTimeNanos());
      moduleStats.setTotalDataSampleCount(collectorStats.getTotalConfiguredDataSampleCount());
      moduleStats.setTotalSamplingCycles((long)collectorStats.getTotalSamplingCycles());
   }
}
