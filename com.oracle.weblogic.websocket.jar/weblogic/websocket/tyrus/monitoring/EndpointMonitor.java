package weblogic.websocket.tyrus.monitoring;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame.FrameType;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import weblogic.management.ManagementException;

class EndpointMonitor extends BaseMonitor implements EndpointEventListener {
   private static final Logger LOGGER = Logger.getLogger(EndpointMonitor.class.getName());
   private final String endpointPath;
   private final Object maxOpenSessionsCountLock = new Object();
   private final ApplicationMonitor applicationMonitor;
   private final WebsocketApplicationRuntimeMBeanImpl applicationMBean;
   private final AtomicInteger openSessionsCount = new AtomicInteger();
   private final ConcurrentMessageStatistics sentTextMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics sentBinaryMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics sentControlMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics receivedTextMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics receivedBinaryMessageStatistics = new ConcurrentMessageStatistics();
   private final ConcurrentMessageStatistics receivedControlMessageStatistics = new ConcurrentMessageStatistics();
   protected volatile int maxOpenSessionsCount = 0;
   private volatile WebsocketEndpointRuntimeMBeanImpl endpointMBean;
   private volatile WebsocketMessageStatisticsRuntimeMBeanImpl textMessagesMBean;
   private volatile WebsocketMessageStatisticsRuntimeMBeanImpl binaryMessagesMBean;
   private volatile WebsocketMessageStatisticsRuntimeMBeanImpl controlMessagesMBean;

   EndpointMonitor(ApplicationMonitor applicationJmx, WebsocketApplicationRuntimeMBeanImpl applicationMBean, String endpointPath, String endpointClassName) {
      this.endpointPath = endpointPath;
      this.applicationMonitor = applicationJmx;
      this.applicationMBean = applicationMBean;

      try {
         this.endpointMBean = new WebsocketEndpointRuntimeMBeanImpl(applicationMBean, new MessageStatisticsAggregator(new MessageStatisticsSource[]{this.sentTextMessageStatistics, this.sentBinaryMessageStatistics, this.sentControlMessageStatistics}), new MessageStatisticsAggregator(new MessageStatisticsSource[]{this.receivedTextMessageStatistics, this.receivedBinaryMessageStatistics, this.receivedControlMessageStatistics}), endpointPath, endpointClassName, this.getOpenSessionsCount(), this.getMaxOpenSessionsCount(), this.getErrorCounts());
         this.textMessagesMBean = new WebsocketMessageStatisticsRuntimeMBeanImpl("text", this.endpointMBean, this.sentTextMessageStatistics, this.receivedTextMessageStatistics);
         this.endpointMBean.setTextMessageStatisticsRuntimeMBean(this.textMessagesMBean);
         this.binaryMessagesMBean = new WebsocketMessageStatisticsRuntimeMBeanImpl("binary", this.endpointMBean, this.sentBinaryMessageStatistics, this.receivedBinaryMessageStatistics);
         this.endpointMBean.setBinaryMessageStatisticsRuntimeMBean(this.binaryMessagesMBean);
         this.controlMessagesMBean = new WebsocketMessageStatisticsRuntimeMBeanImpl("control", this.endpointMBean, this.sentControlMessageStatistics, this.receivedControlMessageStatistics);
         this.endpointMBean.setControlMessageStatisticsRuntimeMBean(this.controlMessagesMBean);
      } catch (ManagementException var6) {
         LOGGER.log(Level.WARNING, "Websocket endpoint MBean could not be registered " + var6.getMessage());
      }

      applicationMBean.addEndpointMBean(endpointPath, this.endpointMBean);
   }

   public MessageEventListener onSessionOpened(String sessionId) {
      this.applicationMonitor.onSessionOpened();
      this.openSessionsCount.incrementAndGet();
      if (this.openSessionsCount.get() > this.maxOpenSessionsCount) {
         synchronized(this.maxOpenSessionsCountLock) {
            if (this.openSessionsCount.get() > this.maxOpenSessionsCount) {
               this.maxOpenSessionsCount = this.openSessionsCount.get();
            }
         }
      }

      return new MessageEventListenerImpl();
   }

   public void onSessionClosed(String sessionId) {
      this.applicationMonitor.onSessionClosed();
      this.openSessionsCount.decrementAndGet();
   }

   public void onError(String sessionId, Throwable t) {
      this.onError(t);
      this.applicationMonitor.onError(t);
   }

   void unregister() {
      this.applicationMBean.removeEndpointMBean(this.endpointPath);

      try {
         this.endpointMBean.unregister();
         this.textMessagesMBean.unregister();
         this.controlMessagesMBean.unregister();
         this.binaryMessagesMBean.unregister();
      } catch (ManagementException var2) {
         LOGGER.log(Level.FINE, "Websocket endpoint MBean could not be unregistered " + var2.getMessage());
      }

   }

   private Callable getOpenSessionsCount() {
      return new Callable() {
         public Integer call() {
            return EndpointMonitor.this.openSessionsCount.get();
         }
      };
   }

   private Callable getMaxOpenSessionsCount() {
      return new Callable() {
         public Integer call() {
            return EndpointMonitor.this.maxOpenSessionsCount;
         }
      };
   }

   private class MessageEventListenerImpl implements MessageEventListener {
      private MessageEventListenerImpl() {
      }

      public void onFrameSent(TyrusFrame.FrameType frameType, long payloadLength) {
         if (frameType == FrameType.TEXT || frameType == FrameType.TEXT_CONTINUATION) {
            EndpointMonitor.this.sentTextMessageStatistics.onMessage(payloadLength);
            EndpointMonitor.this.applicationMonitor.onTextMessageSent(payloadLength);
         }

         if (frameType == FrameType.BINARY || frameType == FrameType.BINARY_CONTINUATION) {
            EndpointMonitor.this.sentBinaryMessageStatistics.onMessage(payloadLength);
            EndpointMonitor.this.applicationMonitor.onBinaryMessageSent(payloadLength);
         }

         if (frameType == FrameType.PING || frameType == FrameType.PONG) {
            EndpointMonitor.this.sentControlMessageStatistics.onMessage(payloadLength);
            EndpointMonitor.this.applicationMonitor.onControlMessageSent(payloadLength);
         }

      }

      public void onFrameReceived(TyrusFrame.FrameType frameType, long payloadLength) {
         if (frameType == FrameType.TEXT || frameType == FrameType.TEXT_CONTINUATION) {
            EndpointMonitor.this.receivedTextMessageStatistics.onMessage(payloadLength);
            EndpointMonitor.this.applicationMonitor.onTextMessageReceived(payloadLength);
         }

         if (frameType == FrameType.BINARY || frameType == FrameType.BINARY_CONTINUATION) {
            EndpointMonitor.this.receivedBinaryMessageStatistics.onMessage(payloadLength);
            EndpointMonitor.this.applicationMonitor.onBinaryMessageReceived(payloadLength);
         }

         if (frameType == FrameType.PING || frameType == FrameType.PONG) {
            EndpointMonitor.this.receivedControlMessageStatistics.onMessage(payloadLength);
            EndpointMonitor.this.applicationMonitor.onControlMessageReceived(payloadLength);
         }

      }

      // $FF: synthetic method
      MessageEventListenerImpl(Object x1) {
         this();
      }
   }
}
