package weblogic.jms.dotnet.proxy.internal;

import java.security.PrivilegedExceptionAction;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import weblogic.jms.client.WLConsumerImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyBytesMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerCloseRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerReceiveRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerReceiveResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerSetListenerRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerSetListenerResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyHdrMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyMapMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyObjectMessageImpl;
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
import weblogic.security.subject.AbstractSubject;

public final class ConsumerProxy extends BaseProxy implements ServiceTwoWay {
   private MessageConsumer consumer;
   private WLAsyncSession wlAsyncSession;
   private WLConsumerImpl wlConsumerImpl;
   private ProxyMessageListener listener;
   private static final boolean USE_ASYNC;

   ConsumerProxy(long serviceId, SessionProxy session, MessageConsumer consumer) {
      super(serviceId, session);
      this.consumer = consumer;
      if (USE_ASYNC && session.getSession() instanceof WLAsyncSession) {
         this.wlAsyncSession = (WLAsyncSession)session.getSession();
      }

      if (consumer instanceof WLConsumerImpl) {
         this.wlConsumerImpl = (WLConsumerImpl)consumer;
      }

   }

   public final SessionProxy getSession() {
      return (SessionProxy)this.parent;
   }

   public final String toString() {
      return this.consumer.toString();
   }

   final void close() throws JMSException {
      this.close((ProxyConsumerCloseRequest)null);
   }

   final void close(final ProxyConsumerCloseRequest request) throws JMSException {
      this.unregister();
      this.parent.remove(this.serviceId);
      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            if (ConsumerProxy.this.wlConsumerImpl != null && request != null) {
               ConsumerProxy.this.wlConsumerImpl.close(request.getSequenceNumber());
            }

            if (ConsumerProxy.this.consumer != null) {
               ConsumerProxy.this.consumer.close();
            }

            ConsumerProxy.this.wlConsumerImpl = null;
            ConsumerProxy.this.consumer = null;
            return null;
         }
      });
   }

   void unregister() {
      this.getTransport().unregisterService(this.serviceId);
   }

   void remove(long id) {
   }

   private final void receiveAsync(ProxyConsumerReceiveRequest req, ReceivedTwoWay rr) {
      final ProxyConsumerReceiveRequest request = req;
      req.setReceivedTwoWay(rr);
      req.setConsumerProxy(this);
      final long timeout = req.getTimeout();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("ReceiveAsync(): timeout = " + timeout);
      }

      try {
         JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
            public Object run() {
               if (timeout == 0L) {
                  ConsumerProxy.this.wlAsyncSession.receiveNoWaitAsync(ConsumerProxy.this.consumer, request);
               } else if (timeout == -1L) {
                  ConsumerProxy.this.wlAsyncSession.receiveAsync(ConsumerProxy.this.consumer, request);
               } else {
                  ConsumerProxy.this.wlAsyncSession.receiveAsync(ConsumerProxy.this.consumer, timeout, request);
               }

               return request;
            }
         });
      } catch (Throwable var7) {
         req.onException(var7);
      }

   }

   public void receiveException(Throwable awaken, ReceivedTwoWay rr) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("ReceiveAsync() failed:  throwable = " + awaken);
      }

      this.sendResult(rr, new TransportError(awaken));
   }

   private void sendResult(final ReceivedTwoWay rr, final MarshalWritable result) {
      try {
         JMSSecurityHelper.doAs(anonymous, new PrivilegedExceptionAction() {
            public Object run() {
               rr.send(result);
               return rr;
            }
         });
      } catch (Throwable var4) {
      }

   }

   public void receiveCompletion(Object awaken, ReceivedTwoWay rr) {
      this.sendResult(rr, this.asyncCompletion(awaken));
   }

   private MarshalWritable asyncCompletion(Object awaken) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("ReceiveAsync() returned:  response = " + awaken);
      }

      if (awaken instanceof Throwable) {
         return new TransportError((Throwable)awaken);
      } else if (awaken != null && !(awaken instanceof Message)) {
         return new TransportError(new Error("Wrong response " + awaken + " is " + awaken.getClass().getName()));
      } else {
         try {
            return this.complete((Message)awaken);
         } catch (Throwable var3) {
            return new TransportError(var3);
         }
      }
   }

   private MarshalWritable complete(Message msg) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Received: JMS message = " + msg);
         if (msg instanceof ObjectMessage) {
            JMSDebug.JMSDotNetProxy.debug(" Received: JMS message = " + ((ObjectMessageImpl)msg).getPayload());
         }
      }

      ProxyMessageImpl message;
      if (msg == null) {
         message = null;
      } else {
         message = createProxyMessageImpl(msg);
      }

      return new ProxyConsumerReceiveResponse(message);
   }

   private final MarshalWritable receiveSync(ProxyConsumerReceiveRequest request) {
      final long timeout = request.getTimeout();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("ReceiveSync(): timeout = " + timeout);
      }

      try {
         Message msg = (Message)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               if (timeout == 0L) {
                  return ConsumerProxy.this.consumer.receiveNoWait();
               } else {
                  return timeout == -1L ? ConsumerProxy.this.consumer.receive() : ConsumerProxy.this.consumer.receive(timeout);
               }
            }
         });
         return this.complete(msg);
      } catch (JMSException var6) {
         return new TransportError(var6);
      } catch (Throwable var7) {
         return new TransportError(var7);
      }
   }

   private final MarshalWritable setListener(final ProxyConsumerSetListenerRequest request) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("SetListener: hasListener = " + request.getHasListener());
      }

      if (request.getHasListener()) {
         this.wlConsumerImpl.setProxyID(request.getListenerServiceId());
         this.listener = new ProxyMessageListener(this, request.getListenerServiceId());
      } else {
         this.listener = null;
      }

      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            if (ConsumerProxy.this.wlConsumerImpl != null) {
               ConsumerProxy.this.wlConsumerImpl.setMessageListener(ConsumerProxy.this.listener, request.getSequenceNumber());
               return null;
            } else {
               throw new JMSException("not implemented");
            }
         }
      });
      return new ProxyConsumerSetListenerResponse();
   }

   public final void invoke(ReceivedTwoWay rr) {
      MarshalReadable request = rr.getRequest();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("invoke():  code = " + request.getMarshalTypeCode() + " request = " + request);
      }

      MarshalWritable response = null;

      try {
         switch (request.getMarshalTypeCode()) {
            case 12:
               this.close((ProxyConsumerCloseRequest)request);
               response = ProxyVoidResponse.THE_ONE;
               break;
            case 16:
               if (this.wlAsyncSession != null) {
                  this.receiveAsync((ProxyConsumerReceiveRequest)request, rr);
                  return;
               }

               response = this.receiveSync((ProxyConsumerReceiveRequest)request);
               break;
            case 18:
               response = this.setListener((ProxyConsumerSetListenerRequest)request);
               break;
            default:
               response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
         }
      } catch (JMSException var5) {
         response = new TransportError(var5);
      }

      rr.send((MarshalWritable)response);
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

   static ProxyMessageImpl createProxyMessageImpl(Message msg) throws JMSException {
      if (msg instanceof TextMessage) {
         return new ProxyTextMessageImpl((TextMessage)msg);
      } else if (msg instanceof BytesMessage) {
         return new ProxyBytesMessageImpl((BytesMessage)msg);
      } else if (msg instanceof MapMessage) {
         return new ProxyMapMessageImpl((MapMessage)msg);
      } else if (msg instanceof StreamMessage) {
         return new ProxyStreamMessageImpl((StreamMessage)msg);
      } else if (msg instanceof ObjectMessage) {
         return new ProxyObjectMessageImpl((ObjectMessage)msg);
      } else if (msg instanceof Message) {
         return new ProxyHdrMessageImpl(msg);
      } else {
         throw new AssertionError("Unexpected message type " + msg.getClass().getName());
      }
   }

   static {
      String userString = null;
      String property = "weblogic.jms.dotnet.RecvAsync";
      boolean normal = true;
      boolean async = true;

      try {
         userString = System.getProperty("weblogic.jms.dotnet.RecvAsync");
         if (userString != null) {
            async = Boolean.valueOf(userString);
         }

         if (!async) {
            System.err.println("\n\nweblogic.jms.dotnet.proxy.internal.ConsumerProxy -Dweblogic.jms.dotnet.RecvAsync=" + async);
         }
      } catch (Throwable var5) {
         if (userString != null) {
            System.err.println("\n\nproblem processing -Dweblogic.jms.dotnet.RecvAsync=" + userString);
         }

         var5.printStackTrace();
      }

      USE_ASYNC = async;
   }

   public final class ProxyMessageListener implements MessageListener {
      private final ConsumerProxy consumer;
      private final long listenerServiceId;

      ProxyMessageListener(ConsumerProxy consumer, long listenerServiceId) {
         this.consumer = consumer;
         this.listenerServiceId = listenerServiceId;
      }

      SessionProxy getSession() {
         return (SessionProxy)this.consumer.getParent();
      }

      public final String toString() {
         return this.consumer.toString();
      }

      public void onMessage(Message msg) {
      }
   }
}
