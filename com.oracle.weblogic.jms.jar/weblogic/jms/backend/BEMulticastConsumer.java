package weblogic.jms.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.multicast.JMSTMSocket;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;

final class BEMulticastConsumer extends BEDeliveryList implements Listener {
   private static final int DEFAULT_WINDOW_SIZE = 256;
   private final DestinationImpl destination;
   private final Queue queue;
   private final int port;
   private final byte ttl;
   private final InetAddress group;
   private long sequenceNum;
   private final JMSTMSocket socket;
   private ListenRequest listenRequest;
   private boolean started;

   BEMulticastConsumer(BackEnd backEnd, Queue queue, DestinationImpl destination, InetAddress group, int port, byte ttl, JMSTMSocket socket) {
      super(backEnd);
      this.setWorkManager(backEnd.getWorkManager());
      this.queue = queue;
      this.destination = destination;
      this.group = group;
      this.port = port;
      this.ttl = ttl;
      this.socket = socket;
   }

   Queue getQueue() {
      return this.queue;
   }

   synchronized void stop() {
      if (this.started) {
         if (this.listenRequest != null) {
            this.listenRequest.stop();
         }

         this.started = false;
      }
   }

   synchronized void start() throws JMSException {
      if (!this.started) {
         try {
            this.setWorkManager(this.getBackEnd().getWorkManager());
            this.listenRequest = this.queue.listen((Expression)null, 256, true, this, this, (String)null, this.getBackEnd().getWorkManager());
         } catch (KernelException var2) {
            throw new weblogic.jms.common.JMSException("Error creating consumer on kernel queue", var2);
         }

         this.started = true;
      }
   }

   protected void pushMessages(List messages) {
      ListenRequest listenRequest;
      synchronized(this) {
         listenRequest = this.listenRequest;
      }

      Iterator i = messages.iterator();

      while(i.hasNext()) {
         MessageImpl msg = (MessageImpl)((MessageElement)i.next()).getMessage();

         try {
            synchronized(this.socket) {
               this.socket.send(msg, this.destination, msg.getConnectionId(), this.group, this.port, this.ttl, (long)(this.sequenceNum++));
            }
         } catch (IOException var9) {
            JMSDebug.JMSBackEnd.debug("Error forwarding multicast message", var9);
         }

         try {
            listenRequest.incrementCount(1);
         } catch (KernelException var7) {
            JMSDebug.JMSBackEnd.debug("Error incrementing window for multicast request", var7);
         }
      }

   }
}
