package weblogic.websocket.tyrus.monitoring;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import weblogic.management.ManagementException;
import weblogic.servlet.internal.WebAppRuntimeMBeanImpl;

public class ApplicationMonitor extends BaseMonitor implements ApplicationEventListener {
   private final Map endpoints = new ConcurrentHashMap();
   private final AtomicInteger openSessionsCount = new AtomicInteger(0);
   private final Object maxOpenSessionsCountLock = new Object();
   private final WebAppRuntimeMBeanImpl webAppMBean;
   private final ConcurrentMessageStatistics sentTextMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics sentBinaryMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics sentControlMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics receivedTextMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics receivedBinaryMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics receivedControlMessageStatistics = new ConcurrentMessageStatistics();
   private volatile int maxOpenSessionCount = 0;
   private volatile WebsocketApplicationRuntimeMBeanImpl applicationMBean;
   private volatile WebsocketMessageStatisticsRuntimeMBeanImpl textMessagesMBean;
   private volatile WebsocketMessageStatisticsRuntimeMBeanImpl controlMessagesMBean;
   private volatile WebsocketMessageStatisticsRuntimeMBeanImpl binaryMessagesMBean;
   private static final Logger LOGGER = Logger.getLogger(ApplicationMonitor.class.getName());

   public ApplicationMonitor(WebAppRuntimeMBeanImpl webAppMBean) {
      this.webAppMBean = webAppMBean;
   }

   public void onApplicationInitialized(String applicationName) {
      try {
         MessageStatisticsAggregator sentAggregatedStatistics = new MessageStatisticsAggregator(new MessageStatisticsSource[]{this.sentTextMessageStatistics, this.sentBinaryMessageStatistics, this.sentControlMessageStatistics});
         MessageStatisticsAggregator receivedAggregatedStatistics = new MessageStatisticsAggregator(new MessageStatisticsSource[]{this.receivedTextMessageStatistics, this.receivedBinaryMessageStatistics, this.receivedControlMessageStatistics});
         this.applicationMBean = new WebsocketApplicationRuntimeMBeanImpl(applicationName, this.webAppMBean, sentAggregatedStatistics, receivedAggregatedStatistics, this.getOpenSessionsCount(), this.getMaxOpenSessionsCount(), this.getErrorCounts());
         this.webAppMBean.setWebsocketApplicationRuntimeMBean(this.applicationMBean);
         this.textMessagesMBean = new WebsocketMessageStatisticsRuntimeMBeanImpl("text", this.applicationMBean, this.sentTextMessageStatistics, this.receivedTextMessageStatistics);
         this.applicationMBean.setTextMessageStatisticsRuntimeMBean(this.textMessagesMBean);
         this.controlMessagesMBean = new WebsocketMessageStatisticsRuntimeMBeanImpl("control", this.applicationMBean, this.sentControlMessageStatistics, this.receivedControlMessageStatistics);
         this.applicationMBean.setControlMessageStatisticsRuntimeMBean(this.controlMessagesMBean);
         this.binaryMessagesMBean = new WebsocketMessageStatisticsRuntimeMBeanImpl("binary", this.applicationMBean, this.sentBinaryMessageStatistics, this.receivedBinaryMessageStatistics);
         this.applicationMBean.setBinaryMessageStatisticsRuntimeMBean(this.binaryMessagesMBean);
      } catch (ManagementException var4) {
         LOGGER.log(Level.WARNING, "Websocket application MBean could not be registered " + var4.getMessage());
      }

   }

   public void onApplicationDestroyed() {
      try {
         Iterator var1 = this.endpoints.values().iterator();

         while(var1.hasNext()) {
            EndpointMonitor endpoint = (EndpointMonitor)var1.next();
            endpoint.unregister();
         }

         this.applicationMBean.unregister();
         this.textMessagesMBean.unregister();
         this.controlMessagesMBean.unregister();
         this.binaryMessagesMBean.unregister();
      } catch (ManagementException var3) {
         LOGGER.log(Level.FINE, "Websocket application MBean could not be unregistered " + var3.getMessage());
      }

   }

   public EndpointEventListener onEndpointRegistered(String endpointPath, Class endpointClass) {
      EndpointMonitor endpointMonitor = new EndpointMonitor(this, this.applicationMBean, endpointPath, endpointClass.getName());
      this.endpoints.put(endpointPath, endpointMonitor);
      return endpointMonitor;
   }

   public void onEndpointUnregistered(String endpointPath) {
      EndpointMonitor endpoint = (EndpointMonitor)this.endpoints.remove(endpointPath);
      endpoint.unregister();
   }

   void onSessionOpened() {
      this.openSessionsCount.incrementAndGet();
      if (this.openSessionsCount.get() > this.maxOpenSessionCount) {
         synchronized(this.maxOpenSessionsCountLock) {
            if (this.openSessionsCount.get() > this.maxOpenSessionCount) {
               this.maxOpenSessionCount = this.openSessionsCount.get();
            }
         }
      }

   }

   void onSessionClosed() {
      this.openSessionsCount.decrementAndGet();
   }

   public void onTextMessageSent(long length) {
      this.sentTextMessageStatistics.onMessage(length);
   }

   public void onBinaryMessageSent(long length) {
      this.sentBinaryMessageStatistics.onMessage(length);
   }

   public void onControlMessageSent(long length) {
      this.sentControlMessageStatistics.onMessage(length);
   }

   public void onTextMessageReceived(long length) {
      this.receivedTextMessageStatistics.onMessage(length);
   }

   public void onBinaryMessageReceived(long length) {
      this.receivedBinaryMessageStatistics.onMessage(length);
   }

   public void onControlMessageReceived(long length) {
      this.receivedControlMessageStatistics.onMessage(length);
   }

   private Callable getOpenSessionsCount() {
      return new Callable() {
         public Integer call() {
            return ApplicationMonitor.this.openSessionsCount.get();
         }
      };
   }

   private Callable getMaxOpenSessionsCount() {
      return new Callable() {
         public Integer call() {
            return ApplicationMonitor.this.maxOpenSessionCount;
         }
      };
   }
}
