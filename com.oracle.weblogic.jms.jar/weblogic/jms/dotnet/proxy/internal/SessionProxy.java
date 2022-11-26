package weblogic.jms.dotnet.proxy.internal;

import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Iterator;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicSession;
import weblogic.jms.client.MMessageListener;
import weblogic.jms.client.MessageWrapper;
import weblogic.jms.client.WLSessionImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerCreateRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyConsumerCreateResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyDestinationCreateRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyDestinationCreateResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyDestinationImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyMessageImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyProducerCreateRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyProducerCreateResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyPushMessageListRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyPushMessageRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyRemoveSubscriptionRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionAcknowledgeRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionCloseRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionRecoverRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionRecoverResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionWindowTurnRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyVoidResponse;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedOneWay;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.SendHandlerOneWay;
import weblogic.jms.dotnet.transport.ServiceOneWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.extensions.WLAsyncSession;
import weblogic.security.subject.AbstractSubject;

public class SessionProxy extends BaseProxy implements ServiceTwoWay, ServiceOneWay, MMessageListener {
   private Session session;
   private int ackMode;
   private final long sessionMsgListenerServiceId;
   private WLAsyncSession wlAsyncSession;
   private WLSessionImpl wlSessionImpl;
   private HashSet serviceIds = new HashSet();
   private static final int MAX_PUSH_COUNT = Integer.MAX_VALUE;

   protected SessionProxy(long serviceId, ConnectionProxy connectionProxy, Session session, int ackMode, long sessionMsgListenerServiceId) {
      super(serviceId, connectionProxy);
      this.session = session;
      this.ackMode = ackMode;
      this.sessionMsgListenerServiceId = sessionMsgListenerServiceId;
      if (session instanceof WLAsyncSession) {
         this.wlAsyncSession = (WLAsyncSession)session;
         this.wlSessionImpl = (WLSessionImpl)session;
         this.wlSessionImpl.setMMessageListener(this);
      } else {
         this.wlAsyncSession = null;
         this.wlSessionImpl = null;
      }

   }

   public final String toString() {
      return this.session.toString();
   }

   boolean getTransacted() throws JMSException {
      return this.session.getTransacted();
   }

   final int getAcknowledgeMode() {
      return this.ackMode;
   }

   private final void unsubscribe(final String name) throws JMSException {
      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            synchronized(SessionProxy.this) {
               ((TopicSession)SessionProxy.this.session).unsubscribe(name);
               return null;
            }
         }
      });
   }

   private final TemporaryQueue createTemporaryQueue(String name) throws JMSException {
      throw new JMSException("createTemporaryQueue() is not supported");
   }

   private final TemporaryTopic createTemporaryTopic(String name) throws JMSException {
      throw new JMSException("createTemporaryTopic() is not supported");
   }

   void unregister() {
      this.getTransport().unregisterService(this.serviceId);
      synchronized(this) {
         Iterator itr = this.serviceIds.iterator();

         while(itr.hasNext()) {
            long id = (Long)itr.next();
            this.getTransport().unregisterService(id);
         }

         this.serviceIds.clear();
      }
   }

   private final void close(long sequenceNumber) throws JMSException {
      this.closeInner(true, sequenceNumber);
   }

   final void close() throws JMSException {
      this.closeInner(false, 0L);
   }

   private final void closeInner(final boolean hasSequence, final long sequenceNumber) throws JMSException {
      synchronized(this) {
         if ((this.state & 1) != 0) {
            return;
         }

         this.state = 1;
      }

      this.unregister();
      this.parent.remove(this.serviceId);
      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            synchronized(SessionProxy.this) {
               if (hasSequence && SessionProxy.this.wlSessionImpl != null) {
                  SessionProxy.this.wlSessionImpl.close(sequenceNumber);
               } else {
                  SessionProxy.this.session.close();
               }

               return null;
            }
         }
      });
   }

   private final MarshalWritable createConsumer(final ProxyConsumerCreateRequest request) throws JMSException {
      final ProxyDestinationImpl destination = request.getDestination();
      String clientId = request.getClientId();
      final String name = request.getName();
      MessageConsumer consumer = null;
      String selector = request.getSelector();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Creating consumer on " + destination.getJMSDestination() + " selector = " + selector + " noLocal = " + request.getNoLocal() + " name = " + name);
      }

      if (name == null) {
         consumer = (MessageConsumer)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               synchronized(SessionProxy.this) {
                  return SessionProxy.this.session.createConsumer(destination.getJMSDestination(), request.getSelector(), request.getNoLocal());
               }
            }
         });
      } else {
         consumer = (MessageConsumer)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               synchronized(SessionProxy.this) {
                  return SessionProxy.this.session.createDurableSubscriber((Topic)destination.getJMSDestination(), name, request.getSelector(), request.getNoLocal());
               }
            }
         });
      }

      long serviceId = this.getTransport().allocateServiceID();
      ConsumerProxy consumerProxy = new ConsumerProxy(serviceId, this, consumer);
      this.getTransport().registerService(serviceId, consumerProxy);
      this.addServiceId(serviceId);
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Created consumer: id = " + serviceId);
      }

      return new ProxyConsumerCreateResponse(serviceId);
   }

   private final MarshalWritable createProducer(ProxyProducerCreateRequest request) throws JMSException {
      ProxyDestinationImpl destination = request.getDestination();
      final Destination jmsDest = destination == null ? null : destination.getJMSDestination();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Creating producer on " + jmsDest);
      }

      MessageProducer producer = (MessageProducer)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            synchronized(SessionProxy.this) {
               return SessionProxy.this.session.createProducer(jmsDest);
            }
         }
      });
      long serviceId = this.getTransport().allocateServiceID();
      ProducerProxy producerProxy = new ProducerProxy(serviceId, this, producer, destination);
      this.getTransport().registerService(serviceId, producerProxy);
      this.addServiceId(serviceId);
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Created producer: id = " + serviceId);
      }

      return new ProxyProducerCreateResponse(serviceId, producer);
   }

   private void acknowledgeAsync(ProxySessionAcknowledgeRequest req, ReceivedTwoWay rr) {
      final ProxySessionAcknowledgeRequest request = req;
      req.setReceivedTwoWay(rr);
      req.setSessionProxy(this);

      try {
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("AcknowledgeAsync(): transacted=" + this.session.getTransacted() + " doCommit=" + request.isDoCommit() + " seq=" + request.getSequenceNumber());
         }

         JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               synchronized(SessionProxy.this) {
                  SessionProxy.this.wlAsyncSession.acknowledgeAsync(request, request);
                  return null;
               }
            }
         });
      } catch (JMSException var5) {
         req.onException(var5);
      }

   }

   public void receiveCompletion(ProxySessionAcknowledgeRequest request, Object awaken, ReceivedTwoWay rr) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("AcknowledgeAsync() returned:  response = " + awaken);
      }

      if (awaken instanceof Throwable) {
         this.receiveException((Throwable)awaken, rr);
      } else {
         MarshalWritable response = ProxyVoidResponse.THE_ONE;
         rr.send(response);
      }
   }

   public void receiveException(Throwable awaken, ReceivedTwoWay rr) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("AcknowledgeAsync() failed:  throwable = " + awaken);
      }

      rr.send(new TransportError(awaken));
   }

   private MarshalWritable acknowledgeSync(final ProxySessionAcknowledgeRequest request) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("AcknowledgeSync(): transacted=" + this.session.getTransacted() + " doCommit=" + request.isDoCommit() + " seq=" + request.getSequenceNumber());
      }

      JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            synchronized(SessionProxy.this) {
               if (request.isDoCommit()) {
                  if (SessionProxy.this.wlSessionImpl != null) {
                     SessionProxy.this.wlSessionImpl.commit(request.getSequenceNumber());
                  } else {
                     SessionProxy.this.session.commit();
                  }

                  return null;
               } else {
                  throw new AssertionError("not implemented");
               }
            }
         }
      });
      return ProxyVoidResponse.THE_ONE;
   }

   private MarshalWritable recover(final ProxySessionRecoverRequest request) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("recover(): transacted=" + this.session.getTransacted() + " doRollback=" + request.isDoRollback() + " seq=" + request.getSequenceNumber());
      }

      int gen = (Integer)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            synchronized(SessionProxy.this) {
               int tempGeneration = 0;
               if (SessionProxy.this.wlSessionImpl != null) {
                  if (request.isDoRollback()) {
                     tempGeneration = SessionProxy.this.wlSessionImpl.rollback(request.getSequenceNumber());
                  } else {
                     tempGeneration = SessionProxy.this.wlSessionImpl.recover(request.getSequenceNumber());
                  }

                  return new Integer(tempGeneration);
               } else {
                  if (request.isDoRollback()) {
                     SessionProxy.this.session.rollback();
                  } else {
                     SessionProxy.this.session.recover();
                  }

                  return new Integer(tempGeneration);
               }
            }
         }
      });
      return new ProxySessionRecoverResponse(gen);
   }

   private synchronized void windowTurn(ProxySessionWindowTurnRequest request) {
      if (this.wlSessionImpl != null) {
         try {
            this.wlSessionImpl.removePendingWTMessage(request.getSequenceNumber());
         } catch (Exception var3) {
         }
      }

   }

   private final MarshalWritable close(ProxySessionCloseRequest request) throws JMSException {
      this.close(request.getSequenceNumber());
      return ProxyVoidResponse.THE_ONE;
   }

   private final MarshalWritable createDestination(ProxyDestinationCreateRequest request) throws JMSException {
      final int type = request.getDestinationType();
      final boolean isTemp = request.isTemporary();
      final String name = request.getDestinationName();
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("CreateDestination(): type = " + (type == 1 ? "Queue" : "Topic") + " name =  " + name + " isTemp = " + isTemp);
      }

      Destination destination = (Destination)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            synchronized(SessionProxy.this) {
               if (type == 1) {
                  return isTemp ? SessionProxy.this.createTemporaryQueue(name) : SessionProxy.this.session.createQueue(name);
               } else if (type == 2) {
                  return isTemp ? SessionProxy.this.createTemporaryTopic(name) : SessionProxy.this.session.createTopic(name);
               } else {
                  return null;
               }
            }
         }
      });
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("CreateDestination(): found destination = " + destination);
      }

      return new ProxyDestinationCreateResponse(new ProxyDestinationImpl(name, destination));
   }

   private final ProxyPushMessageRequest createPush(MessageWrapper mw, boolean suggestWindowTurn) throws JMSException {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("CreatePush:  Pushing  msg=" + mw.getMessageImpl() + " seq=" + mw.getSequence() + " gen=" + mw.getGeneration());
      }

      ProxyMessageImpl proxyMessage = ConsumerProxy.createProxyMessageImpl(mw.getMessageImpl());
      proxyMessage.setSequenceNumber(mw.getSequence());
      proxyMessage.setPipelineGeneration(mw.getGeneration());
      proxyMessage.setDeliveryCount(mw.getDeliveryCount());
      if (mw.getDeliveryCount() > 1) {
         proxyMessage.setRedelivered(true);
      }

      return new ProxyPushMessageRequest(mw.getProxyId(), proxyMessage, suggestWindowTurn);
   }

   private final void pushMany(final ProxyPushMessageListRequest mList) throws JMSException {
      final SendHandlerOneWay sow = this.getTransport().createOneWay(this.sessionMsgListenerServiceId, this.sessionMsgListenerServiceId);
      JMSSecurityHelper.doAs(anonymous, new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            sow.send(mList);
            return null;
         }
      });
   }

   public final void onMessages(MessageWrapper mw, int pipelineSize) {
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Starting onMessages");
      }

      Throwable t = null;

      try {
         ProxyPushMessageListRequest mList;
         try {
            for(MessageWrapper cur = mw; cur != null; this.pushMany(mList)) {
               int cnt = 0;

               for(mList = new ProxyPushMessageListRequest(pipelineSize); cur != null; cur = cur.next()) {
                  boolean suggestWindowTurn = cur.next() == null;
                  mList.add(this.createPush(cur, suggestWindowTurn));
                  ++cnt;
                  if (cnt == Integer.MAX_VALUE) {
                     cur = cur.next();
                     break;
                  }
               }
            }
         } catch (JMSException var13) {
            t = var13;
            throw new RuntimeException(var13);
         } catch (RuntimeException var14) {
            t = var14;
            throw var14;
         } catch (Error var15) {
            t = var15;
            throw var15;
         }
      } finally {
         if (t != null && JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("should never happen " + t);
            ((Throwable)t).printStackTrace();
         }

      }

   }

   public final void invoke(ReceivedOneWay rr) {
      if (!this.isShutdown()) {
         MarshalReadable request = rr.getRequest();
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("invoke(): one-way:  code = " + request.getMarshalTypeCode() + " request = " + request);
         }

         switch (request.getMarshalTypeCode()) {
            case 49:
               this.windowTurn((ProxySessionWindowTurnRequest)request);
               break;
            default:
               if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
                  JMSDebug.JMSDotNetProxy.debug("DEBUG: should never reach here, Session one-way: Unexpected type code" + request.getMarshalTypeCode());
               }
         }

      }
   }

   private synchronized void addServiceId(long id) throws JMSException {
      this.checkShutdownOrClosed("The session has been closed");
      this.serviceIds.add(id);
   }

   synchronized void remove(long id) {
      this.serviceIds.remove(id);
   }

   public final void invoke(ReceivedTwoWay rr) {
      if (this.isShutdown()) {
         rr.send(new TransportError(new JMSException("The JMS service is shutting down")));
      } else {
         MarshalReadable request = rr.getRequest();
         MarshalWritable response = ProxyVoidResponse.THE_ONE;
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Invoking: code = " + request.getMarshalTypeCode() + " request = " + request);
         }

         Object response;
         try {
            switch (request.getMarshalTypeCode()) {
               case 13:
                  response = this.createConsumer((ProxyConsumerCreateRequest)request);
                  break;
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 21:
               case 22:
               case 24:
               case 25:
               case 26:
               case 30:
               case 31:
               default:
                  response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
                  break;
               case 20:
                  response = this.createDestination((ProxyDestinationCreateRequest)request);
                  break;
               case 23:
                  response = this.createProducer((ProxyProducerCreateRequest)request);
                  break;
               case 27:
                  this.unsubscribe(((ProxyRemoveSubscriptionRequest)request).getName());
                  response = ProxyVoidResponse.THE_ONE;
                  break;
               case 28:
                  if (this.wlAsyncSession != null && !((ProxySessionAcknowledgeRequest)request).isDoCommit()) {
                     this.acknowledgeAsync((ProxySessionAcknowledgeRequest)request, rr);
                     return;
                  }

                  response = this.acknowledgeSync((ProxySessionAcknowledgeRequest)request);
                  break;
               case 29:
                  this.close((ProxySessionCloseRequest)request);
                  response = ProxyVoidResponse.THE_ONE;
                  break;
               case 32:
                  response = this.recover((ProxySessionRecoverRequest)request);
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

   Session getSession() {
      return this.session;
   }

   AbstractSubject getSubject() {
      return this.parent.getSubject();
   }
}
