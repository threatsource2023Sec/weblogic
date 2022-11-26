package weblogic.management.runtime;

public interface WebsocketBaseRuntimeMBean extends WebsocketMessageStatisticsRuntimeMBean {
   int getOpenSessionsCount();

   int getMaximalOpenSessionsCount();

   WebsocketErrorCount[] getErrorCounts();

   WebsocketMessageStatisticsRuntimeMBean getTextMessageStatisticsRuntimeMBean();

   WebsocketMessageStatisticsRuntimeMBean getBinaryMessageStatisticsRuntimeMBean();

   WebsocketMessageStatisticsRuntimeMBean getControlMessageStatisticsRuntimeMBean();
}
