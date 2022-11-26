package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.messaging.kernel.SendOptions;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.utils.collections.CircularQueue;

final class TopicResequencer implements Listener, Runnable {
   private static final int WINDOW_SIZE = 256;
   private QueueImpl queue;
   private TopicImpl topic;
   private ListenRequest request;
   private boolean running;
   private KernelImpl kernel;
   private final CircularQueue messages = new CircularQueue();

   TopicResequencer(QueueImpl queue, TopicImpl topic, KernelImpl kernel) {
      this.queue = queue;
      this.topic = topic;
      this.kernel = kernel;
   }

   synchronized void start() throws KernelException {
      this.request = this.queue.listen((Expression)null, 256, false, this, this, this.queue.getName(), this.kernel.getWorkManager());
   }

   void stop() {
      this.request.stop();
   }

   public synchronized Runnable deliver(ListenRequest request, List list) {
      this.messages.addAll(list);
      if (!this.running) {
         this.running = true;
         return this;
      } else {
         return null;
      }
   }

   public synchronized Runnable deliver(ListenRequest request, MessageElement element) {
      this.messages.add(element);
      if (!this.running) {
         this.running = true;
         return this;
      } else {
         return null;
      }
   }

   public void run() {
      ArrayList msgBatch = new ArrayList(256);

      do {
         msgBatch.clear();
         synchronized(this) {
            Object o;
            while((o = this.messages.remove()) != null) {
               msgBatch.add(o);
            }

            if (msgBatch.isEmpty()) {
               this.running = false;
               return;
            }
         }

         GXALocalTransaction tran = null;

         try {
            tran = this.kernel.startLocalGXATransaction();
            Iterator i = msgBatch.iterator();

            while(i.hasNext()) {
               MessageElementImpl element = (MessageElementImpl)i.next();
               this.queue.associateInternal(element, tran, (RedeliveryParameters)null);
               SendOptions options = element.getMessageReference().getMessageHandle().createSendOptions();
               this.topic.sendRedirected(element.getMessage(), options, tran);
            }

            this.request.incrementCount(msgBatch.size());
            tran.commit();
         } catch (KernelException var8) {
            if (tran != null) {
               tran.rollback();
            }
         } catch (GXAException var9) {
         }

         synchronized(this) {
            if (this.messages.isEmpty()) {
               this.running = false;
               return;
            }
         }
      } while(!this.kernel.getWorkManager().scheduleIfBusy(this));

   }
}
