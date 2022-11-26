package weblogic.jaxrs.monitoring.impl;

import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean;
import weblogic.management.runtime.JaxRsMonitoringInfoRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

/** @deprecated */
@Deprecated
abstract class JaxRsMonitoringInfoMBeanImpl extends RuntimeMBeanDelegate implements JaxRsMonitoringInfoRuntimeMBean {
   private long startTime = 0L;

   public JaxRsMonitoringInfoMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
      this.startTime = System.currentTimeMillis();
   }

   protected abstract JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics();

   public long getStartTime() {
      return this.startTime;
   }

   public long getInvocationCount() {
      JaxRsExecutionStatisticsRuntimeMBean requestStatistics = this.getRequestStatistics();
      return requestStatistics != null ? requestStatistics.getRequestCountTotal() : 0L;
   }

   public long getExecutionTimeAverage() {
      JaxRsExecutionStatisticsRuntimeMBean requestStatistics = this.getRequestStatistics();
      return requestStatistics != null ? requestStatistics.getAvgTimeTotal() : 0L;
   }

   public long getExecutionTimeLow() {
      JaxRsExecutionStatisticsRuntimeMBean requestStatistics = this.getRequestStatistics();
      return requestStatistics != null ? requestStatistics.getMinTimeTotal() : 0L;
   }

   public long getExecutionTimeHigh() {
      JaxRsExecutionStatisticsRuntimeMBean requestStatistics = this.getRequestStatistics();
      return requestStatistics != null ? requestStatistics.getMaxTimeTotal() : 0L;
   }

   public long getLastInvocationTime() {
      return -1L;
   }

   public long getExecutionTimeTotal() {
      return -1L;
   }
}
