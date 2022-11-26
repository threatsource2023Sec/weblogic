package weblogic.management.runtime;

public interface JaxRsResourceMethodBaseRuntimeMBean extends JaxRsMonitoringInfoRuntimeMBean {
   String getPath();

   boolean isExtended();

   String getClassName();

   String getMethodName();

   String[] getParameterTypes();

   String getReturnType();

   JaxRsExecutionStatisticsRuntimeMBean getMethodStatistics();

   JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics();
}
