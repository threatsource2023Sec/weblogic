package weblogic.jms.dotnet.proxy.internal;

import java.security.PrivilegedExceptionAction;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import weblogic.jms.common.AsyncSendResponseInfo;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyBytesMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyDestinationImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyHdrMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyMapMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyObjectMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyProducerSendRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyProducerSendResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyStreamMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyTextMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyVoidResponse;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.extensions.WLAsyncSession;
import weblogic.jms.extensions.WLMessage;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.security.subject.AbstractSubject;

public final class ProducerProxy extends BaseProxy implements ServiceTwoWay {
   private MessageProducer producer;
   private final ProxyDestinationImpl destination;
   private final WLAsyncSession wlAsyncSession;
   private static final boolean USE_ASYNC;

   ProducerProxy(long serviceId, SessionProxy session, MessageProducer producer, ProxyDestinationImpl destination) throws JMSException {
      super(serviceId, session);
      this.producer = producer;
      this.destination = destination;
      if (USE_ASYNC && session.getSession() instanceof WLAsyncSession) {
         this.wlAsyncSession = (WLAsyncSession)session.getSession();
      } else {
         this.wlAsyncSession = null;
      }

      if (producer instanceof WLMessageProducer && ((WLMessageProducer)producer).getRedeliveryLimit() == -1) {
         ((WLMessageProducer)producer).setRedeliveryLimit(1073741823);
      }

   }

   private void send(final Destination destination, final Message message, final int deliveryMode, final int priority, final long timeToLive) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendSync(): destination = " + destination + " message = " + message + " deliveryMode = " + deliveryMode + " priority = " + priority + " timeToLive = " + timeToLive);
      }

      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            ProducerProxy.this.producer.send(destination, message, deliveryMode, priority, timeToLive);
            return null;
         }
      });
   }

   private void send(final Message message, final int deliveryMode, final int priority, final long timeToLive) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendSync(): message = " + message + " deliveryMode = " + deliveryMode + " priority = " + priority + " timeToLive = " + timeToLive);
      }

      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            ProducerProxy.this.producer.send(message, deliveryMode, priority, timeToLive);
            return null;
         }
      });
   }

   private void sendAsync(final ProxyProducerSendRequest request, final Destination destinatn, final Message msg, final int delivMode, final int prio, final long time2Live) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendAsync(): destination = " + this.destination + " message = " + msg + " deliveryMode = " + delivMode + " priority = " + prio + " timeToLive = " + time2Live);
      }

      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            ProducerProxy.this.wlAsyncSession.sendAsync((WLMessageProducer)ProducerProxy.this.producer, destinatn, msg, delivMode, prio, time2Live, request);
            return null;
         }
      });
   }

   private void sendAsync(final ProxyProducerSendRequest request, final Message msg, final int delivMode, final int prio, final long time2Live) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendAsync(): message = " + msg + " deliveryMode = " + delivMode + " priority = " + prio + " timeToLive = " + time2Live);
      }

      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            ProducerProxy.this.wlAsyncSession.sendAsync((WLMessageProducer)ProducerProxy.this.producer, msg, delivMode, prio, time2Live, request);
            return null;
         }
      });
   }

   synchronized void close() throws JMSException {
      this.unregister();
      this.parent.remove(this.serviceId);
      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            if (ProducerProxy.this.producer != null) {
               ProducerProxy.this.producer.close();
            }

            ProducerProxy.this.producer = null;
            return null;
         }
      });
   }

   void unregister() {
      this.getTransport().unregisterService(this.serviceId);
   }

   void remove(long id) {
   }

   public String toString() {
      return this.producer.toString();
   }

   private final void sendAsync(ProxyProducerSendRequest request, ReceivedTwoWay rr) {
      request.setReceivedTwoWay(rr);
      request.setProducerProxy(this);
      ProxyMessageImpl message = request.getMessage();
      ProxyDestinationImpl dest = request.getDestination();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendAsync() starts, destination on the request = " + dest + " destination on the message = " + message.getDestination());
      }

      Destination jmsDestination = null;
      String cachedUOO = null;

      try {
         if (this.producer instanceof WLMessageProducer) {
            try {
               cachedUOO = ((WLMessageProducer)this.producer).getUnitOfOrder();
            } catch (JMSException var17) {
            }

            if (message.propertyExists("JMS_BEA_UnitOfOrder")) {
               ((WLMessageProducer)this.producer).setUnitOfOrder((String)message.getProperty("JMS_BEA_UnitOfOrder"));
               message.removeProperty("JMS_BEA_UnitOfOrder");
            }
         }

         Message msg = this.createJMSMessage(message);
         if (dest != null) {
            jmsDestination = dest.getJMSDestination();
            if (jmsDestination != null) {
               this.sendAsync(request, jmsDestination, msg, message.getDeliveryMode(), message.getPriority(), message.getExpiration());
            } else {
               request.onException(new JMSException("Destination not found"));
            }
         } else {
            this.sendAsync(request, msg, message.getDeliveryMode(), message.getPriority(), message.getExpiration());
         }
      } catch (Throwable var18) {
         request.onException(var18);
      } finally {
         if (this.producer instanceof WLMessageProducer) {
            try {
               ((WLMessageProducer)this.producer).setUnitOfOrder(cachedUOO);
            } catch (JMSException var16) {
            }
         }

      }

   }

   public void receiveCompletion(ProxyProducerSendRequest request, Object awaken, ReceivedTwoWay rr) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendAsync() returned:  response = " + awaken);
      }

      if (awaken instanceof Throwable) {
         this.receiveException((Throwable)awaken, rr);
      } else {
         assert awaken instanceof AsyncSendResponseInfo;

         AsyncSendResponseInfo info = (AsyncSendResponseInfo)awaken;
         MarshalWritable response = null;

         try {
            response = this.setupResponse(info, info.getMessage(), request.getMessage());
            if (info.getAsyncFlowControlTime() != 0L) {
               ((ProxyProducerSendResponse)response).setFlowControlTime(info.getAsyncFlowControlTime());
            }
         } catch (JMSException var7) {
            response = new TransportError(var7);
         }

         this.sendResult(rr, (MarshalWritable)response);
      }
   }

   private void sendResult(final ReceivedTwoWay rr, final MarshalWritable result) {
      try {
         JMSSecurityHelper.getJMSSecurityHelper();
         JMSSecurityHelper.doAs(JMSSecurityHelper.getAnonymousSubject(), new PrivilegedExceptionAction() {
            public Object run() {
               rr.send(result);
               return rr;
            }
         });
      } catch (Throwable var4) {
      }

   }

   public void receiveException(Throwable awaken, ReceivedTwoWay rr) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendAsync() failed:  throwable = " + awaken);
      }

      this.sendResult(rr, new TransportError(awaken));
   }

   private Message createJMSMessage(ProxyMessageImpl message) throws JMSException {
      Session session = ((SessionProxy)this.getParent()).getSession();
      int messageType = message.getType();
      Message msg = null;
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         switch (messageType) {
            case 1:
               JMSDebug.JMSDotNetProxy.debug("Sending bytes message");
               break;
            case 2:
               JMSDebug.JMSDotNetProxy.debug("Sending header message");
               break;
            case 3:
               JMSDebug.JMSDotNetProxy.debug("Sending map message");
               break;
            case 4:
               JMSDebug.JMSDotNetProxy.debug("Sending text message");
               break;
            case 5:
               JMSDebug.JMSDotNetProxy.debug("Sending object message");
               break;
            case 6:
               JMSDebug.JMSDotNetProxy.debug("Sending text message");
         }
      }

      if (!message.propertyExists("JMS_BEA_UnitOfWork") && !message.propertyExists("JMS_BEA_UnitOfWorkSequenceNumber") && !message.propertyExists("JMS_BEA_IsUnitOfWorkEnd")) {
         switch (messageType) {
            case 1:
               msg = session.createBytesMessage();
               ((ProxyBytesMessageImpl)message).populateJMSMessage((BytesMessage)msg);
               break;
            case 2:
               msg = session.createMessage();
               ((ProxyHdrMessageImpl)message).populateJMSMessage((Message)msg);
               break;
            case 3:
               msg = session.createMapMessage();
               ((ProxyMapMessageImpl)message).populateJMSMessage((MapMessage)msg);
               break;
            case 4:
               msg = session.createObjectMessage();
               ((ProxyObjectMessageImpl)message).populateJMSMessage((ObjectMessage)msg);
               break;
            case 5:
               msg = session.createStreamMessage();
               ((ProxyStreamMessageImpl)message).populateJMSMessage((StreamMessage)msg);
               break;
            case 6:
               msg = session.createTextMessage();
               ((ProxyTextMessageImpl)message).populateJMSMessage((TextMessage)msg);
         }

         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Message = " + msg);
         }

         return (Message)msg;
      } else {
         throw new JMSException("Unit-of-Work is not supported for .NET client");
      }
   }

   private final MarshalWritable sendSync(ProxyProducerSendRequest request) throws JMSException {
      ProxyMessageImpl message = request.getMessage();
      ProxyDestinationImpl dest = request.getDestination();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SendSync() starts, destination on the request = " + dest + " destiantion on the message = " + message.getDestination());
      }

      Destination jmsDestination = null;

      ProxyProducerSendResponse var20;
      try {
         TransportError var6;
         try {
            if (this.producer instanceof WLMessageProducer) {
               ((WLMessageProducer)this.producer).setUnitOfOrder((String)message.getProperty("JMS_BEA_UnitOfOrder"));
               message.removeProperty("JMS_BEA_UnitOfOrder");
            }

            Message msg = this.createJMSMessage(message);
            if (dest == null) {
               this.send(msg, message.getDeliveryMode(), message.getPriority(), message.getExpiration());
               var20 = this.setupResponse((AsyncSendResponseInfo)null, msg, message);
               return var20;
            }

            jmsDestination = dest.getJMSDestination();
            if (jmsDestination == null) {
               var6 = new TransportError("Destination not found", false);
               return var6;
            }

            this.send(jmsDestination, msg, message.getDeliveryMode(), message.getPriority(), message.getExpiration());
            var20 = this.setupResponse((AsyncSendResponseInfo)null, msg, message);
         } catch (JMSException var18) {
            var6 = new TransportError(var18);
            return var6;
         }
      } finally {
         if (this.producer instanceof WLMessageProducer) {
            try {
               ((WLMessageProducer)this.producer).setUnitOfOrder((String)null);
            } catch (JMSException var17) {
            }
         }

      }

      return var20;
   }

   private ProxyProducerSendResponse setupResponse(AsyncSendResponseInfo info, Message msg, ProxyMessageImpl message) throws JMSException {
      ProxyProducerSendResponse response;
      if (info != null) {
         JMSMessageId tmp = ((MessageImpl)info.getMessage()).getId();
         response = new ProxyProducerSendResponse(tmp.getSeed(), tmp.getCounter());
      } else {
         response = new ProxyProducerSendResponse(msg.getJMSMessageID());
      }

      response.setTimestamp(msg.getJMSTimestamp());
      response.setExpirationTime(msg.getJMSExpiration());
      int mode;
      int redelivery;
      boolean isDispatchOneWay;
      int prio;
      if (info == null) {
         prio = msg.getJMSPriority();
         mode = msg.getJMSDeliveryMode();
         redelivery = 0;
         isDispatchOneWay = false;
      } else {
         prio = info.getPriority();
         mode = info.getDeliveryMode();
         redelivery = info.getRedeliveryLimit();
         isDispatchOneWay = info.isDispatchOneWay();
      }

      if (isDispatchOneWay || mode != message.getDeliveryMode()) {
         response.setDeliveryMode(mode);
      }

      if (isDispatchOneWay || prio != message.getPriority()) {
         response.setPriority(prio);
      }

      if (msg instanceof WLMessage) {
         if (msg.propertyExists("JMS_BEA_DeliveryTime")) {
            response.setDeliveryTime(msg.getLongProperty("JMS_BEA_DeliveryTime"));
         }

         if (msg.propertyExists("JMS_BEA_RedeliveryLimit") && msg.getIntProperty("JMS_BEA_RedeliveryLimit") != 0) {
            response.setRedeliveryLimit(msg.getIntProperty("JMS_BEA_RedeliveryLimit"));
         } else if (redelivery != 0) {
            response.setRedeliveryLimit(redelivery);
         }
      }

      return response;
   }

   public final void invoke(ReceivedTwoWay rr) {
      if (this.isShutdown()) {
         rr.send(new TransportError(new JMSException("The JMS service is shutting down")));
      } else {
         MarshalReadable request = rr.getRequest();
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("invoke():  code = " + request.getMarshalTypeCode() + " request = " + request);
         }

         MarshalWritable response = null;

         try {
            switch (request.getMarshalTypeCode()) {
               case 22:
                  this.close();
                  response = ProxyVoidResponse.THE_ONE;
                  break;
               case 25:
                  if (this.wlAsyncSession != null) {
                     this.sendAsync((ProxyProducerSendRequest)request, rr);
                     return;
                  }

                  response = this.sendSync((ProxyProducerSendRequest)request);
                  break;
               default:
                  response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
            }
         } catch (JMSException var5) {
            response = new TransportError(var5);
         }

         rr.send((MarshalWritable)response);
      }
   }

   public void onPeerGone(TransportError te) {
   }

   public void onShutdown() {
   }

   public void onUnregister() {
   }

   public Transport getTransport() {
      return this.parent.getTransport();
   }

   InitialContextProxy getContext() {
      return this.parent.getContext();
   }

   public AbstractSubject getSubject() {
      return this.parent.getSubject();
   }

   static {
      String userString = null;
      String property = "weblogic.jms.dotnet.SendAsync";
      boolean normal = true;
      boolean async = true;

      try {
         userString = System.getProperty("weblogic.jms.dotnet.SendAsync");
         if (userString != null) {
            async = Boolean.valueOf(userString);
         }

         if (!async) {
            System.err.println("\n\nweblogic.jms.dotnet.proxy.internal.ProducerProxy -Dweblogic.jms.dotnet.SendAsync=" + async);
         }
      } catch (Throwable var5) {
         if (userString != null) {
            System.err.println("\n\nproblem processing -Dweblogic.jms.dotnet.SendAsync=" + userString);
         }

         var5.printStackTrace();
      }

      USE_ASYNC = async;
   }
}
