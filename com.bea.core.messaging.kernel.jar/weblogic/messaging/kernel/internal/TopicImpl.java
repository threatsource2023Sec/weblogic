package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.CompletionRequest;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.store.gxa.GXATransaction;
import weblogic.transaction.TransactionHelper;

public final class TopicImpl extends DestinationImpl implements Topic {
   static final String SEQUENCING_QUEUE_PREFIX = "_weblogic.messaging.SequencingQueue.";
   private static boolean disableMultiSender = false;
   private QueueImpl sequencingQueue;
   private TopicResequencer resequencer;
   private final AtomicLong liveSequence = new AtomicLong(1L);

   TopicImpl(String name, Map properties, KernelImpl kernel) throws KernelException {
      super(name);
      this.initialize(properties, kernel);
   }

   public TopicImpl() {
   }

   public TopicImpl(String name) {
      super(name);
   }

   protected void close() {
      if (this.resequencer != null) {
         this.resequencer.stop();
      }

   }

   void initialize(Map properties, KernelImpl kernel) throws KernelException {
      super.initialize(kernel);
      this.setProperty("Durable", Boolean.TRUE);
      this.setProperty("MaximumMessageSize", Integer.MAX_VALUE);
      if (properties != null) {
         this.setProperties(properties);
      }

      this.created = true;
   }

   protected synchronized void activate() throws KernelException {
      super.activate();
      this.findSequencingQueue();
      if (this.sequencingQueue != null) {
         this.activateSequencingQueue();
      }

   }

   protected synchronized void deactivate() throws KernelException {
      this.findSequencingQueue();
      if (this.sequencingQueue != null) {
         this.deactivateSequencingQueue();
      }

   }

   public KernelRequest send(Message message, SendOptions userOptions) throws KernelException {
      SendOptions options = this.initializeSendOptions(userOptions);
      this.checkDestinationState(message, options);
      if (options.getSequence() != null) {
         SequenceImpl sequence = (SequenceImpl)options.getSequence();
         if (sequence.getDestination() == this && sequence.getQueue() == this.sequencingQueue) {
            return this.sequencingQueue.send(message, options);
         } else {
            throw new KernelException("Sequence does not match this topic");
         }
      } else {
         Collection matches = this.filter.match(new MessageElementImpl(message));
         if (matches != null && !matches.isEmpty()) {
            if (matches.size() == 1) {
               Iterator i = matches.iterator();
               QueueImpl q = (QueueImpl)i.next();

               KernelRequest var16;
               try {
                  q.adjust(this.subscriptionMsgsLimit);
                  var16 = q.send(message, options);
               } finally {
                  q.adjustCleaup(this.subscriptionMsgsLimit);
               }

               return var16;
            } else {
               GXATransaction transaction = this.kernel.getGXATransaction();
               MultiMessageHandle handle = new MultiMessageHandle(this.kernel, message, options);

               try {
                  handle.setQueueReferenceCount(matches.size());
                  handle.setQuotaReferenceCount(matches.size());
                  handle.setQuota(this.quota);
                  SendRequest request = new SendRequest(this, handle, options);
                  request.setMatchedQueues(matches);
                  this.sendAllocateQuota(request, handle, transaction, options);
                  if (request.getState() == 1) {
                     handle.unPin(this.kernel);
                     return request;
                  } else {
                     KernelRequest kernelRequest = this.sendAddAndPersist(request, options, handle, transaction, false);
                     handle.unPin(this.kernel);
                     return kernelRequest;
                  }
               } catch (KernelException var12) {
                  throw var12;
               }
            }
         } else {
            this.sendNowhere(message);
            return null;
         }
      }
   }

   protected void sendRedirected(Message message, SendOptions userOptions, GXATransaction transaction) throws KernelException {
      SendOptions options = this.initializeSendOptions(userOptions);
      Collection matches = this.filter.match(new MessageElementImpl(message));
      if (matches != null && !matches.isEmpty()) {
         if (matches.size() == 1) {
            Iterator i = matches.iterator();
            QueueImpl q = (QueueImpl)i.next();

            try {
               q.adjust(this.subscriptionMsgsLimit);
               q.sendRedirected(message, options, transaction);
            } finally {
               q.adjustCleaup(this.subscriptionMsgsLimit);
            }

         } else {
            MultiMessageHandle handle = new MultiMessageHandle(this.kernel, message, options);

            try {
               handle.setQueueReferenceCount(matches.size());
               handle.setQuota(this.quota);
               SendRequest request = new SendRequest(this, handle, options);
               request.setMatchedQueues(matches);
               if (this.isQuotaCheckEnabled()) {
                  this.quota.allocateNoCheck(handle, matches.size());
               } else {
                  handle.setQuotaReferenceCount(matches.size());
               }

               this.sendAddAndPersist(request, options, handle, transaction, true);
               request.getResult();
            } finally {
               handle.unPin(this.kernel);
            }

         }
      } else {
         this.sendNowhere(message);
      }
   }

   protected SendRequest sendAddAndPersist(SendRequest sendRequest, SendOptions options, MessageHandle handle, GXATransaction jtaTransaction, boolean localTran) {
      InstrumentationHelper.beforeSendInterceptionPoint(handle);
      GXALocalTransaction myLocalTran = null;
      MultiSender multiSender = null;
      if (!disableMultiSender) {
         multiSender = handle.startMultiSend(this.kernel);
      }

      try {
         boolean persistent = handle.isPersistent();
         int persistentCount = 0;
         ArrayList persHandles = null;
         MultiPersistenceHandle persHandle = null;
         int packCount = 0;
         Iterator iterator = sendRequest.getMatchedQueues().iterator();

         while(iterator.hasNext()) {
            QueueImpl queue = (QueueImpl)iterator.next();
            GXATransaction msgTransaction = jtaTransaction;
            MultiMessageReference ref;
            if (persistent && queue.isDurable()) {
               if (jtaTransaction == null) {
                  if (myLocalTran == null) {
                     myLocalTran = this.kernel.startLocalGXATransaction();
                  }

                  msgTransaction = myLocalTran;
               }

               if (persHandles == null) {
                  persHandles = new ArrayList();
               }

               ++persistentCount;
               if (persHandle == null || packCount >= this.kernel.getTopicPackSize()) {
                  persHandle = new MultiPersistenceHandle(handle, this.serialNumber, this.liveSequence.getAndIncrement());
                  persHandles.add(persHandle);
               }

               ref = new MultiMessageReference(queue, handle, persHandle);
               ++packCount;
            } else {
               ref = new MultiMessageReference(queue, handle, (MultiPersistenceHandle)null);
            }

            try {
               queue.adjust(this.subscriptionMsgsLimit);
               queue.sendAdd(ref, handle, (GXATransaction)msgTransaction, true, (SequenceImpl)null, 0L, localTran || myLocalTran != null);
               sendRequest.addMessageReference(ref);
            } finally {
               queue.adjustCleaup(this.subscriptionMsgsLimit);
            }
         }

         if (multiSender != null) {
            handle.multiSendComplete();
            multiSender.push();
         }

         if (myLocalTran != null) {
            handle.pin(this.kernel);
            CompletionRequest ioRequest = new CompletionRequest();
            sendRequest.setState(3);
            ioRequest.addListener(sendRequest, this.kernel.getWorkManager());
            myLocalTran.commit(ioRequest, this.kernel.getWorkManager());
            myLocalTran = null;
         } else {
            sendRequest.setResult((Object)null);
         }

         InstrumentationHelper.afterSendInterceptionPoint(handle);
      } catch (KernelException var26) {
         if (myLocalTran == null) {
            this.sendUndoQuota(handle);
         }

         sendRequest.setResult(var26);
      } finally {
         if (myLocalTran != null) {
            myLocalTran.rollback();
         }

      }

      return sendRequest;
   }

   private void sendNowhere(Message message) throws KernelException {
      Transaction tran = TransactionHelper.getTransactionHelper().getTransaction();
      if (tran == null) {
         this.statistics.incrementReceived(message.size());
      } else {
         try {
            tran.registerSynchronization(new StatisticsIncrementer(message));
         } catch (RollbackException var4) {
         } catch (SystemException var5) {
            throw new KernelException("Error registering with JTA transaction", var5);
         }
      }

   }

   public void subscribe(Queue q, Object selector, KernelRequest request) throws KernelException {
      this.checkDeleted();
      QueueImpl queue = (QueueImpl)q;
      synchronized(queue) {
         if (queue.getKernel() != this.kernel) {
            throw new IllegalArgumentException("Queues subscribed to topics must use the same kernel");
         }

         if (queue.getQuota() != this.quota) {
            throw new IllegalArgumentException("Queues subscribed to topics must use the same quota");
         }

         AbstractStatistics queueStats = (AbstractStatistics)queue.getStatistics();

         assert queueStats.getParent() == this.kernel.getStatistics();

         queue.setStatistics((AbstractStatistics)null);
         queueStats.setParent(this.statistics);
         queue.setStatistics(queueStats);
      }

      Expression expression = this.filter.createExpression(selector);
      this.filter.subscribe(queue, expression);
      request.setResult((Object)null);
   }

   public void unsubscribe(Queue q, KernelRequest request) {
      QueueImpl queue = (QueueImpl)q;
      AbstractStatistics queueStats = (AbstractStatistics)queue.getStatistics();

      assert queueStats.getParent() == this.statistics;

      queue.setStatistics((AbstractStatistics)null);
      queueStats.setParent((AbstractStatistics)this.kernel.getStatistics());
      queue.setStatistics(queueStats);
      this.filter.unsubscribe(queue);
      request.setResult((Object)null);
   }

   public void delete(KernelRequest request) throws KernelException {
      if (this.durable && this.kernel.isOpened()) {
         this.kernel.getPersistence().deleteDestination(this);
      }

      this.kernel.topicDeleted(this);
      this.setDeleted(true);
      request.setResult((Object)null);
      if (this.quota != null) {
         this.quota.removeDestination(this);
      }

   }

   private void createSequencingQueue() throws KernelException {
      Map props = new HashMap();
      props.put("Durable", this.isDurable());
      props.put("MaximumMessageSize", this.maximumMessageSize);
      if (this.quota != null) {
         props.put("Quota", this.quota);
      }

      if (this.redirectionListener != null) {
         props.put("RedirectionListener", this.redirectionListener);
      }

      this.sequencingQueue = (QueueImpl)this.kernel.createQueue("_weblogic.messaging.SequencingQueue." + this.name, props);
      if (!this.isSuspended(16384)) {
         this.sequencingQueue.resume(16384);
         this.resequencer = new TopicResequencer(this.sequencingQueue, this, this.kernel);
         this.resequencer.start();
      }

   }

   private void findSequencingQueue() {
      this.sequencingQueue = (QueueImpl)this.kernel.findQueue("_weblogic.messaging.SequencingQueue." + this.name);
   }

   private void activateSequencingQueue() throws KernelException {
      this.sequencingQueue.resume(16384);
      this.resequencer = new TopicResequencer(this.sequencingQueue, this, this.kernel);
      this.resequencer.start();
   }

   private void deactivateSequencingQueue() throws KernelException {
      if (this.resequencer != null) {
         this.resequencer.stop();
      }

      this.sequencingQueue.suspend(16384);
   }

   public Sequence createSequence(String name, int sequenceMode) throws KernelException {
      if ((sequenceMode & 1) != 0) {
         throw new KernelException("ASSIGN sequence mode is not supported on a topic");
      } else {
         this.findSequencingQueue();
         if (this.sequencingQueue == null) {
            this.createSequencingQueue();
         }

         SequenceImpl ret = (SequenceImpl)this.sequencingQueue.createSequence(name, sequenceMode);
         ret.setActualDestination(this);
         return ret;
      }
   }

   public Sequence findOrCreateSequence(String name, int sequenceMode) throws KernelException {
      if ((sequenceMode & 1) != 0) {
         throw new KernelException("ASSIGN sequence mode is not supported on a topic");
      } else {
         this.findSequencingQueue();
         if (this.sequencingQueue == null) {
            this.createSequencingQueue();
         }

         SequenceImpl ret = (SequenceImpl)this.sequencingQueue.findOrCreateSequence(name, sequenceMode);
         ret.setActualDestination(this);
         return ret;
      }
   }

   public Sequence findSequence(String name) {
      this.findSequencingQueue();
      if (this.sequencingQueue == null) {
         return null;
      } else {
         SequenceImpl ret = (SequenceImpl)this.sequencingQueue.findSequence(name);
         if (ret != null) {
            ret.setActualDestination(this);
         }

         return ret;
      }
   }

   public Collection getSequences() {
      this.findSequencingQueue();
      if (this.sequencingQueue == null) {
         ArrayList ret = new ArrayList();
         return Collections.unmodifiableCollection(ret);
      } else {
         return this.sequencingQueue.getSequences();
      }
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Topic");
      super.dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   static {
      String checkMultiSend = System.getProperty("weblogic.messaging.DisableTopicMultiSender");
      disableMultiSender = checkMultiSend != null && checkMultiSend.equalsIgnoreCase("true");
   }

   private final class StatisticsIncrementer implements Synchronization {
      private long size;

      StatisticsIncrementer(Message message) {
         this.size = message.size();
      }

      public void beforeCompletion() {
      }

      public void afterCompletion(int status) {
         if (status == 3) {
            TopicImpl.this.statistics.incrementReceived(this.size);
         }

      }
   }
}
