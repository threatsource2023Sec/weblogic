package weblogic.websocket.tyrus.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WebsocketEndpointRuntimeMBean;

class WebsocketEndpointRuntimeMBeanImpl extends WebsocketBaseRuntimeMBeanImpl implements WebsocketEndpointRuntimeMBean {
   private final String endpointPath;
   private final String endpointClassName;

   WebsocketEndpointRuntimeMBeanImpl(RuntimeMBean parentMBean, MessageStatisticsSource sentMessageStatistics, MessageStatisticsSource receivedMessageStatistics, String endpointPath, String endpointClassName, Callable openSessionsCount, Callable maxOpenSessionsCount, Callable errorCounts) throws ManagementException {
      super(endpointPath, parentMBean, sentMessageStatistics, receivedMessageStatistics, openSessionsCount, maxOpenSessionsCount, errorCounts);
      this.endpointPath = endpointPath;
      this.endpointClassName = endpointClassName;
   }

   public String getEndpointPath() {
      return this.endpointPath;
   }

   public String getEndpointClassName() {
      return this.endpointClassName;
   }
}
