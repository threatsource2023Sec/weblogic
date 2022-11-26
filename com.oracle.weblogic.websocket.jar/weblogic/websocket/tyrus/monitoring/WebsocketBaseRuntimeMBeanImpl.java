package weblogic.websocket.tyrus.monitoring;

import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WebsocketBaseRuntimeMBean;
import weblogic.management.runtime.WebsocketErrorCount;
import weblogic.management.runtime.WebsocketMessageStatisticsRuntimeMBean;

class WebsocketBaseRuntimeMBeanImpl extends WebsocketMessageStatisticsRuntimeMBeanImpl implements WebsocketBaseRuntimeMBean {
   private final Callable openSessionsCount;
   private final Callable maxOpenSessionsCount;
   private final Callable errorCounts;
   private WebsocketMessageStatisticsRuntimeMBean textMessageStatisticsRuntimeMBean;
   private WebsocketMessageStatisticsRuntimeMBean binaryMessageStatisticsRuntimeMBean;
   private WebsocketMessageStatisticsRuntimeMBean controlMessageStatisticsRuntimeMBean;

   WebsocketBaseRuntimeMBeanImpl(String name, RuntimeMBean parentMBean, MessageStatisticsSource sentMessageStatistics, MessageStatisticsSource receivedMessageStatistics, Callable openSessionsCount, Callable maxOpenSessionsCount, Callable errorCounts) throws ManagementException {
      super(name, parentMBean, sentMessageStatistics, receivedMessageStatistics);
      this.openSessionsCount = openSessionsCount;
      this.maxOpenSessionsCount = maxOpenSessionsCount;
      this.errorCounts = errorCounts;
   }

   public int getOpenSessionsCount() {
      return (Integer)this.openSessionsCount.call();
   }

   public int getMaximalOpenSessionsCount() {
      return (Integer)this.maxOpenSessionsCount.call();
   }

   public WebsocketErrorCount[] getErrorCounts() {
      List websocketErrorCounts = (List)this.errorCounts.call();
      return (WebsocketErrorCount[])websocketErrorCounts.toArray(new WebsocketErrorCount[websocketErrorCounts.size()]);
   }

   public WebsocketMessageStatisticsRuntimeMBean getTextMessageStatisticsRuntimeMBean() {
      return this.textMessageStatisticsRuntimeMBean;
   }

   public WebsocketMessageStatisticsRuntimeMBean getBinaryMessageStatisticsRuntimeMBean() {
      return this.binaryMessageStatisticsRuntimeMBean;
   }

   public WebsocketMessageStatisticsRuntimeMBean getControlMessageStatisticsRuntimeMBean() {
      return this.controlMessageStatisticsRuntimeMBean;
   }

   void setTextMessageStatisticsRuntimeMBean(WebsocketMessageStatisticsRuntimeMBean textMessageStatisticsRuntimeMBean) {
      this.textMessageStatisticsRuntimeMBean = textMessageStatisticsRuntimeMBean;
   }

   void setBinaryMessageStatisticsRuntimeMBean(WebsocketMessageStatisticsRuntimeMBean binaryMessageStatisticsRuntimeMBean) {
      this.binaryMessageStatisticsRuntimeMBean = binaryMessageStatisticsRuntimeMBean;
   }

   void setControlMessageStatisticsRuntimeMBean(WebsocketMessageStatisticsRuntimeMBean controlMessageStatisticsRuntimeMBean) {
      this.controlMessageStatisticsRuntimeMBean = controlMessageStatisticsRuntimeMBean;
   }
}
