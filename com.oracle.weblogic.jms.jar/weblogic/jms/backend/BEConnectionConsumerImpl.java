package weblogic.jms.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.client.JMSServerSessionPool;
import weblogic.jms.client.SessionInternal;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.Subscription;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.frontend.FESession;
import weblogic.management.configuration.JMSConnectionConsumerMBean;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public final class BEConnectionConsumerImpl extends BEConsumerImpl implements BEConnectionConsumerCommon {
   private ServerSessionPool sessionPool;
   private BEConnectionConsumerRuntimeDelegate delegate;

   BEConnectionConsumerImpl(JMSID id, BEDestinationImpl parentDestination, ServerSessionPool sessionPool, Queue queue, Expression filterExpression, String selector, int messagesMaximum, long redeliveryDelay, int flags) throws JMSException {
      super(parentDestination.getBackEnd());
      this.init(sessionPool);
      BEConsumerCreateRequest createRequest = new BEConsumerCreateRequest((JMSID)null, (JMSID)null, id, (String)null, (String)null, false, (JMSID)null, selector, false, messagesMaximum, flags, redeliveryDelay, (String)null, (ConsumerReconnectInfo)null);
      super.init((BESessionImpl)null, parentDestination, queue, filterExpression, flags, false, createRequest, (Subscription)null);
   }

   BEConnectionConsumerImpl(JMSID id, BEDestinationImpl parentDestination, ServerSessionPool sessionPool, Queue queue, String selector, boolean noLocal, String clientId, String name, boolean isDurable, int messagesMaximum, long redeliveryDelay, int flags) throws JMSException {
      super(parentDestination.getBackEnd());
      this.init(sessionPool);
      BEConsumerCreateRequest createRequest = new BEConsumerCreateRequest((JMSID)null, (JMSID)null, id, clientId, name, isDurable, (JMSID)null, selector, noLocal, messagesMaximum, flags, redeliveryDelay, (String)null, (ConsumerReconnectInfo)null);
      super.init((BESessionImpl)null, parentDestination, queue, (Expression)null, flags, false, createRequest, (Subscription)null);
   }

   private void init(ServerSessionPool sessionPool) {
      this.sessionPool = sessionPool;
      this.setStateFlag(4);
   }

   public void initialize(JMSConnectionConsumerMBean mbean) throws JMSException {
      this.delegate = new BEConnectionConsumerRuntimeDelegate(this, mbean);
   }

   public void close() throws JMSException {
      this.sessionPool = null;
      if (this.delegate != null) {
         this.delegate.close();
      }

      super.close(0L);
   }

   public ServerSessionPool getServerSessionPool() throws JMSException {
      if (this.sessionPool == null) {
         throw new weblogic.jms.common.JMSException("ConnectionConsumer closed");
      } else {
         return this.sessionPool;
      }
   }

   public synchronized int getMessagesMaximum() {
      return this.windowSize;
   }

   public synchronized void setMessagesMaximum(int max) {
      this.windowSize = max;
   }

   protected void pushMessages(List messages) {
      ServerSession serverSession = null;
      ArrayList deliveredMessages = new ArrayList(messages.size());

      try {
         serverSession = this.getServerSessionPool().getServerSession();
         SessionInternal clientSession = (SessionInternal)serverSession.getSession();
         FESession feSession = (FESession)this.destination.getBackEnd().getJmsService().getInvocableManagerDelegate().invocableFind(8, clientSession.getJMSID());
         feSession.setUpBackEndSession(this.destination.getDestinationImpl().getDispatcherId());
         BESessionImpl beSession = (BESessionImpl)this.destination.getBackEnd().getJmsService().getInvocableManagerDelegate().invocableFind(16, clientSession.getJMSID());
         JMSDispatcher dispatcher = feSession.getConnection().getFrontEnd().getService().localDispatcherFind();
         synchronized(beSession) {
            Iterator i = messages.iterator();

            while(true) {
               MessageElement elt;
               boolean clientResponsible;
               do {
                  do {
                     if (!i.hasNext()) {
                        if (!deliveredMessages.isEmpty()) {
                           if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                              JMSDebug.JMSBackEnd.debug("Starting server session with " + deliveredMessages.size() + " messages");
                           }

                           serverSession.start();
                        }

                        serverSession = null;
                        return;
                     }

                     elt = (MessageElement)i.next();
                     long seqNum = beSession.getNextSequenceNumber();
                     elt.setUserSequenceNum(seqNum);
                     elt.setUserData(this);
                     deliveredMessages.add(elt);
                     clientResponsible = this.allowsImplicitAcknowledge();
                     if (!clientResponsible) {
                        beSession.addPendingMessage(elt, this);
                     }

                     if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                        JMSDebug.JMSBackEnd.debug("Pushing " + elt.getMessage() + " as seq number " + seqNum + ". implicitAcknowledge = " + clientResponsible);
                     }

                     JMSPushEntry pushEntry = new JMSPushEntry((JMSID)null, this.id, seqNum, 0L, elt.getDeliveryCount(), 0);
                     pushEntry.setDispatcher(dispatcher);
                     pushEntry.setClientResponsibleForAcknowledge(clientResponsible);
                     MessageImpl message = (MessageImpl)elt.getMessage();
                     feSession.pushMessage(message, pushEntry);
                     clientSession.setPipelineGeneration(0);
                     clientSession.pushMessage(message, pushEntry);
                     this.adjustUnackedCount(1);
                  } while(!clientResponsible);
               } while(this.isKernelAutoAcknowledge());

               try {
                  KernelRequest req = this.queue.acknowledge(elt);
                  if (req != null) {
                     req.getResult();
                  }
               } catch (KernelException var26) {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("Unexpected exception while implicitly acknowledging: " + var26, var26);
                  }
               }
            }
         }
      } catch (JMSException var28) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Error pushing messages: " + var28, var28);
         }

         try {
            KernelRequest kernelRequest = new KernelRequest();
            this.listenRequest.getQueue().negativeAcknowledge(deliveredMessages, this.getRedeliveryDelay(), kernelRequest);
            kernelRequest.getResult();
         } catch (KernelException var25) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Error nacking kernel messages that were delivered", var25);
            }
         }
      } finally {
         if (serverSession != null) {
            ((BEServerSession)serverSession).goBackInPool();
         }

      }

   }

   public Runnable deliver(ListenRequest request, List messageList) {
      return this.deliver(messageList);
   }

   public Runnable deliver(ListenRequest request, MessageElement elt) {
      return this.deliver(elt);
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("ConnectionConsumer");
      this.dumpCommon(xsw);
      String pool = "";
      if (this.sessionPool != null && this.sessionPool instanceof JMSServerSessionPool) {
         JMSID id = ((JMSServerSessionPool)this.sessionPool).getServerSessionPoolId();
         if (id != null) {
            pool = id.toString();
         }
      }

      xsw.writeAttribute("serverSessionPoolID", pool);
      xsw.writeEndElement();
   }
}
