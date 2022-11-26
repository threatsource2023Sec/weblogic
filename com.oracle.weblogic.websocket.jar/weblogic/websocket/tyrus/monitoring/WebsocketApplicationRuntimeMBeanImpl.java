package weblogic.websocket.tyrus.monitoring;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WebsocketApplicationRuntimeMBean;
import weblogic.management.runtime.WebsocketEndpointRuntimeMBean;

class WebsocketApplicationRuntimeMBeanImpl extends WebsocketBaseRuntimeMBeanImpl implements WebsocketApplicationRuntimeMBean {
   private final Map endpointMBeans = new ConcurrentHashMap();

   WebsocketApplicationRuntimeMBeanImpl(String name, RuntimeMBean parentMBean, MessageStatisticsSource sentMessageStatistics, MessageStatisticsSource receivedMessageStatistics, Callable openSessionsCount, Callable maxOpenSessionsCount, Callable errorCounts) throws ManagementException {
      super(name, parentMBean, sentMessageStatistics, receivedMessageStatistics, openSessionsCount, maxOpenSessionsCount, errorCounts);
   }

   public WebsocketEndpointRuntimeMBean[] getEndpointMBeans() {
      Collection values = this.endpointMBeans.values();
      WebsocketEndpointRuntimeMBean[] result = new WebsocketEndpointRuntimeMBean[values.size()];
      values.toArray(result);
      return result;
   }

   void addEndpointMBean(String endpointPath, WebsocketEndpointRuntimeMBean endpointMBean) {
      this.endpointMBeans.put(endpointPath, endpointMBean);
   }

   void removeEndpointMBean(String endpointPath) {
      this.endpointMBeans.remove(endpointPath);
   }
}
