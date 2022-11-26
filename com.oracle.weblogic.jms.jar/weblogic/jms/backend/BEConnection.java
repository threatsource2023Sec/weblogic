package weblogic.jms.backend;

import java.util.HashMap;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPeerGoneListener;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public final class BEConnection implements Invocable, JMSPeerGoneListener {
   private final HashMap connectionConsumers = new HashMap();
   private final HashMap sessions = new HashMap();
   private final HashMap tempDestinations = new HashMap();
   private long startStopSequenceNumber;
   private final JMSID connectionId;
   private JMSDispatcher feDispatcher;
   private boolean stopped = true;
   private int state = 0;
   private String connectionAddress = null;
   private final InvocableMonitor invocableMonitor;
   private final JMSService service;
   private transient int refCount;

   BEConnection(JMSDispatcher feDispatcher, JMSID connectionId, boolean stopped, String connectionAddress, JMSService jmsservice) {
      this.feDispatcher = feDispatcher;
      this.connectionId = connectionId;
      this.stopped = stopped;
      this.connectionAddress = connectionAddress;
      this.feDispatcher.addDispatcherPeerGoneListener(this);
      this.service = jmsservice;
      this.invocableMonitor = this.service.getInvocableMonitor();
   }

   JMSService getService() {
      return this.service;
   }

   public synchronized long getStartStopSequenceNumber() {
      return this.startStopSequenceNumber;
   }

   public void setStartStopSequenceNumber(long sequenceNumber) {
      this.startStopSequenceNumber = sequenceNumber;
   }

   void setDispatcher(JMSDispatcher feDispatcher) {
      this.feDispatcher.removeDispatcherPeerGoneListener(this);
      this.feDispatcher = feDispatcher;
      feDispatcher.addDispatcherPeerGoneListener(this);
   }

   public JMSDispatcher getDispatcher() {
      return this.feDispatcher;
   }

   public String getAddress() {
      return this.connectionAddress;
   }

   public synchronized void tempDestinationAdd(BEDestinationImpl tempDestination) throws JMSException {
      this.checkShutdownOrSuspended("create temporary destiantion");
      JMSID destinationId = tempDestination.getJMSID();
      if (this.tempDestinations.get(destinationId) == null) {
         this.tempDestinations.put(destinationId, tempDestination);
      } else {
         throw new weblogic.jms.common.JMSException("Temporary destination exists, " + destinationId);
      }
   }

   public synchronized void tempDestinationRemove(JMSID destinationId) throws JMSException {
      BEDestinationImpl destination = (BEDestinationImpl)this.tempDestinations.remove(destinationId);
      if (destination == null) {
         throw new weblogic.jms.common.JMSException("Temporary destination not found, " + destinationId);
      } else {
         if (this.needToClose()) {
            this.close();
         }

      }
   }

   public synchronized void sessionAdd(BESession session) throws JMSException {
      this.checkShutdownOrSuspended("create session");
      this.sessions.put(session.getJMSID(), session);
      this.service.getInvocableManagerDelegate().invocableAdd(16, session);
   }

   public synchronized void sessionRemove(JMSID sessionId) {
      this.sessions.remove(sessionId);
      this.service.getInvocableManagerDelegate().invocableRemove(16, sessionId);
      if (this.needToClose()) {
         this.close();
      }

   }

   public synchronized void connectionConsumerAdd(BEConnectionConsumerCommon connectionConsumer) throws JMSException {
      this.checkShutdownOrSuspended("create connection consumer");
      this.connectionConsumers.put(connectionConsumer.getJMSID(), connectionConsumer);
      this.service.getInvocableManagerDelegate().invocableAdd(17, connectionConsumer);
   }

   private void connectionConsumerClose(BEConnectionConsumerCloseRequest request) throws JMSException {
      JMSID connectionConsumerId = request.getConnectionConsumerId();
      BEConnectionConsumerCommon connectionConsumer = (BEConnectionConsumerCommon)this.service.getInvocableManagerDelegate().invocableFind(17, connectionConsumerId);
      connectionConsumer.close();
      this.connectionConsumerRemove(connectionConsumerId);
   }

   private synchronized void connectionConsumerRemove(JMSID connectionConsumerId) {
      this.connectionConsumers.remove(connectionConsumerId);
      this.service.getInvocableManagerDelegate().invocableRemove(17, connectionConsumerId);
      if (this.needToClose()) {
         this.close();
      }

   }

   private synchronized boolean needToClose() {
      return this.sessions.isEmpty() && this.tempDestinations.isEmpty() && this.connectionConsumers.isEmpty();
   }

   synchronized boolean isStopped() {
      return this.stopped;
   }

   synchronized void stop(long sequenceNumber, boolean stopForSuspend) {
      if (this.startStopSequenceNumber < sequenceNumber) {
         this.startStopSequenceNumber = sequenceNumber;
         if (!this.stopped) {
            this.stopped = true;
            if (stopForSuspend) {
               this.state = 2;
            }

            Iterator iterator = this.sessions.values().iterator();

            while(iterator.hasNext()) {
               BESession session = (BESession)iterator.next();
               session.stop();
            }

            iterator = this.connectionConsumers.values().iterator();

            while(iterator.hasNext()) {
               BEConnectionConsumerCommon cc = (BEConnectionConsumerCommon)iterator.next();
               cc.stop();
            }

         }
      }
   }

   private void checkShutdownOrSuspended(String operation) throws JMSException {
      if ((this.state & 27) != 0) {
         throw new weblogic.jms.common.JMSException("Failed to " + operation + " because JMS server shutdown or suspended");
      }
   }

   synchronized void checkShutdownOrSuspendedNeedLock(String operation) throws JMSException {
      if ((this.state & 27) != 0) {
         throw new weblogic.jms.common.JMSException("Failed to " + operation + " bacause JMS server shutdown or suspended");
      }
   }

   public int incrementRefCount() {
      return ++this.refCount;
   }

   public int decrementRefCount() {
      return --this.refCount;
   }

   public void dispatcherPeerGone(Exception e, Dispatcher dispatcher) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("BEConnection.jmsPeerGone()");
      }

      this.peerGone();
   }

   private synchronized void peerGone() {
      JMSException savedException = null;
      this.service.getInvocableManagerDelegate().invocableRemove(15, this.connectionId);
      this.feDispatcher.removeDispatcherPeerGoneListener(this);
      Iterator iterator = ((HashMap)this.sessions.clone()).values().iterator();

      while(iterator.hasNext()) {
         try {
            ((BESession)iterator.next()).peerGone();
         } catch (JMSException var7) {
            if (savedException == null) {
               savedException = var7;
            }
         }
      }

      iterator = ((HashMap)this.tempDestinations.clone()).values().iterator();

      while(iterator.hasNext()) {
         BEDestinationImpl destination = (BEDestinationImpl)iterator.next();

         try {
            destination.deleteTempDestination();
            destination.getBackEnd().removeDestination(destination);
         } catch (JMSException var6) {
            if (savedException == null) {
               savedException = var6;
            }
         }
      }

      iterator = ((HashMap)this.connectionConsumers.clone()).values().iterator();

      while(iterator.hasNext()) {
         try {
            BEConnectionConsumerCommon connectionConsumer = (BEConnectionConsumerCommon)iterator.next();
            connectionConsumer.close();
            this.connectionConsumerRemove(connectionConsumer.getJMSID());
         } catch (JMSException var5) {
            if (savedException == null) {
               savedException = var5;
            }
         }
      }

      if (savedException != null) {
         JMSLogger.logJMSServerShutdownError(this.getDispatcher().getId().getName(), savedException.getMessage(), savedException);
      }

   }

   private synchronized void close() {
      if (this.needToClose()) {
         this.service.getInvocableManagerDelegate().invocableRemove(15, this.connectionId);
         this.feDispatcher.removeDispatcherPeerGoneListener(this);
      }
   }

   synchronized void start(long sequenceNumber) throws JMSException {
      this.checkShutdownOrSuspended("start connection");
      if (this.startStopSequenceNumber < sequenceNumber) {
         this.startStopSequenceNumber = sequenceNumber;
         if (this.stopped) {
            this.stopped = false;
            Iterator iterator = this.sessions.values().iterator();

            while(iterator.hasNext()) {
               BESession session = (BESession)iterator.next();
               session.start();
            }

            iterator = this.connectionConsumers.values().iterator();

            while(iterator.hasNext()) {
               BEConnectionConsumerCommon cc = (BEConnectionConsumerCommon)iterator.next();
               cc.start();
            }

         }
      }
   }

   public JMSID getJMSID() {
      return this.connectionId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.service.getDispatcherPartitionContext();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.service, "BEConnection");
      switch (request.getMethodId()) {
         case 8975:
            this.connectionConsumerClose((BEConnectionConsumerCloseRequest)request);
            break;
         case 9487:
            this.start(((BEConnectionStartRequest)request).getStartStopSequenceNumber());
            break;
         case 9743:
            this.stop(((BEConnectionStopRequest)request).getStartStopSequenceNumber(), ((BEConnectionStopRequest)request).isStopForSuspend());
            break;
         default:
            throw new weblogic.jms.common.JMSException("No such method " + request.getMethodId());
      }

      request.setResult(new VoidResponse());
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Connection");
      xsw.writeAttribute("id", this.connectionId != null ? this.connectionId.toString() : "");
      xsw.writeAttribute("state", JMSService.getStateName(this.state));
      xsw.writeAttribute("connectionAddress", this.connectionAddress != null ? this.connectionAddress : "");
      xsw.writeStartElement("Sessions");
      HashMap tempSessions = (HashMap)this.sessions.clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempSessions.size()));
      Iterator it = tempSessions.values().iterator();

      while(it.hasNext()) {
         BESession session = (BESession)it.next();
         session.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }
}
