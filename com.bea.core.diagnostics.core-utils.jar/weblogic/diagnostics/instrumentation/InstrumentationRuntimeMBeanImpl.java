package weblogic.diagnostics.instrumentation;

import com.bea.adaptive.harvester.WatchedValues;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.diagnostics.harvester.internal.TreeBeanHarvestableDataProviderHelper;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFInstrumentationRuntimeMBean;

public class InstrumentationRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFInstrumentationRuntimeMBean {
   private static final long NANOS_PER_MILLI = 1000000L;
   private InstrumentationScope scope;
   private HarvesterAttributeNormalizer attrNormalizer = new HarvesterAttributeNormalizer();

   public InstrumentationRuntimeMBeanImpl(InstrumentationScope scope, RuntimeMBean parent) throws ManagementException {
      super(scope.getName(), parent);
      this.scope = scope;
   }

   private InstrumentationStatistics getInstrumentationStatistics() {
      return this.scope != null ? this.scope.getInstrumentationStatistics() : null;
   }

   public int getInspectedClassesCount() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getInspectedClassesCount() : 0;
   }

   public int getModifiedClassesCount() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getModifiedClassesCount() : 0;
   }

   public long getMinWeavingTime() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getMinWeavingTime() / 1000000L : 0L;
   }

   public long getMaxWeavingTime() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getMaxWeavingTime() / 1000000L : 0L;
   }

   public long getTotalWeavingTime() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getTotalWeavingTime() / 1000000L : 0L;
   }

   public int getExecutionJoinpointCount() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getExecutionJoinpointCount() : 0;
   }

   public int getCallJoinpointCount() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getCallJoinpointCount() : 0;
   }

   public int getClassweaveAbortCount() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return stats != null ? stats.getClassweaveAbortCount() : 0;
   }

   public Map getMethodInvocationStatistics() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return (Map)(stats != null ? stats.getMethodInvocationStatistics() : new HashMap());
   }

   public Object getMethodInvocationStatisticsData(String expr) throws ManagementException {
      expr = this.attrNormalizer.getPartiallyNormalizedInvocationAttributeName(expr);

      try {
         String instName = TreeBeanHarvestableDataProviderHelper.getObjectNameForBean(this);
         Object value = WLDFHarvesterUtils.getValue("ServerRuntime", WLDFInstrumentationRuntimeMBean.class.getName(), instName, expr);
         if (value != null && value instanceof WatchedValues.AttributeTrackedDataItem) {
            value = ((WatchedValues.AttributeTrackedDataItem)value).getData();
         }

         if (value != null && value.getClass().isArray()) {
            value = WLDFHarvesterUtils.getLeafValues((Object[])((Object[])value));
         }

         return value;
      } catch (Exception var4) {
         ManagementException ex = new ManagementException(var4.getMessage());
         ex.setStackTrace(var4.getStackTrace());
         throw ex;
      }
   }

   public void resetMethodInvocationStatisticsData(String expr) throws ManagementException {
      Object data = this.getMethodInvocationStatisticsData(expr);
      if (data != null) {
         if (data instanceof Map) {
            synchronized(data) {
               Map map = (Map)data;
               map.clear();
            }
         }

      }
   }

   public Map getMethodMemoryAllocationStatistics() {
      InstrumentationStatistics stats = this.getInstrumentationStatistics();
      return (Map)(stats != null ? stats.getMethodMemoryAllocationStatistics() : new HashMap());
   }

   public Object getMethodMemoryAllocationStatisticsData(String expr) throws ManagementException {
      expr = this.attrNormalizer.getPartiallyNormalizedAllocationAttributeName(expr);

      try {
         String instName = TreeBeanHarvestableDataProviderHelper.getObjectNameForBean(this);
         Object value = WLDFHarvesterUtils.getValue("ServerRuntime", WLDFInstrumentationRuntimeMBean.class.getName(), instName, expr);
         if (value != null && value instanceof WatchedValues.AttributeTrackedDataItem) {
            value = ((WatchedValues.AttributeTrackedDataItem)value).getData();
         }

         if (value != null && value.getClass().isArray()) {
            value = WLDFHarvesterUtils.getLeafValues((Object[])((Object[])value));
         }

         return value;
      } catch (Exception var4) {
         ManagementException ex = new ManagementException(var4.getMessage());
         ex.setStackTrace(var4.getStackTrace());
         throw ex;
      }
   }

   public void resetMethodMemoryAllocationStatisticsData(String expr) throws ManagementException {
      Object data = this.getMethodMemoryAllocationStatisticsData(expr);
      if (data != null) {
         if (data instanceof Map) {
            synchronized(data) {
               Map map = (Map)data;
               map.clear();
            }
         }

      }
   }
}
