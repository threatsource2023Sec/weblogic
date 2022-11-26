package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.messaging.Message;
import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.MultiListener;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.ReceiveRequest;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.internal.events.EventImpl;
import weblogic.messaging.kernel.internal.events.GroupAddEventImpl;
import weblogic.messaging.kernel.internal.events.GroupRemoveEventImpl;
import weblogic.messaging.kernel.internal.events.MessageReceiveEventImpl;
import weblogic.messaging.kernel.internal.events.MessageRemoveEventImpl;
import weblogic.messaging.kernel.internal.events.MessageSendEventImpl;
import weblogic.messaging.kernel.internal.persistence.PersistedSequenceRecord;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATransaction;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.utils.collections.CircularQueue;
import weblogic.utils.collections.CombinedIterator;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicLong;
import weblogic.work.WorkManager;

public final class QueueImpl extends DestinationImpl implements Queue, Runnable {
   static final SequenceNumComparator SEQUENCE_NUM_COMPARATOR = new SequenceNumComparator();
   static final SequenceNumComparator2 SEQUENCE_NUM_COMPARATOR2 = new SequenceNumComparator2();
   private final EmbeddedList readerList = new EmbeddedList();
   private final Map groups = new HashMap();
   private Map sequences;
   private Map sequencesByID;
   private final CircularQueue unmatchedGroupList = new CircularQueue();
   private MessageReference lastIteratorMessage;
   private MessageReference lastGroupAvailable;
   private MessageList messageList;
   private MessageList pendingMessageList;
   private final CircularQueue matchList = new CircularQueue();
   private final Redirector redirector = new Redirector(this);
   private final RunnableTrain runnableTrain = new RunnableTrain(512);
   private List recoveryMessageList;
   private List recoveryOperationList;
   private boolean running = false;
   private long lastSequenceNumber;
   private AtomicLong lastMessagesReceivedTime = AtomicFactory.createAtomicLong();
   private Comparator delegatingComparator;
   private ThreadLocal reservedStatus;
   private int reservedSize;
   private int deletingSize;
   private static final String MAX_DELETED_COUNT_PROP = "weblogic.jms.topic.DurableSubscriptionMessagesLimit.MaxDeleteCount";
   private static int MAX_DELETED_COUNT = 20;

   QueueImpl(String name, Map properties, KernelImpl kernel) throws KernelException {
      super(name);
      this.delegatingComparator = SEQUENCE_NUM_COMPARATOR;
      this.reservedStatus = new ThreadLocal();
      this.reservedSize = 0;
      this.deletingSize = 0;
      this.initialize(properties, kernel);
      this.updateLastMessagesReceivedTime();
   }

   public QueueImpl() {
      this.delegatingComparator = SEQUENCE_NUM_COMPARATOR;
      this.reservedStatus = new ThreadLocal();
      this.reservedSize = 0;
      this.deletingSize = 0;
      this.updateLastMessagesReceivedTime();
   }

   public QueueImpl(String name) {
      super(name);
      this.delegatingComparator = SEQUENCE_NUM_COMPARATOR;
      this.reservedStatus = new ThreadLocal();
      this.reservedSize = 0;
      this.deletingSize = 0;
      this.updateLastMessagesReceivedTime();
   }

   MessageList getMessageList() {
      return this.messageList;
   }

   MessageList getPendingMessageList() {
      return this.pendingMessageList;
   }

   Iterator getAllMessageIterator() {
      return new CombinedIterator(this.messageList.iterator(), this.pendingMessageList.iterator());
   }

   Redirector getRedirector() {
      return this.redirector;
   }

   void initialize(Map properties, KernelImpl kernel) throws KernelException {
      super.initialize(kernel);
      this.setProperty("MaximumMessageSize", Integer.MAX_VALUE);
      this.setProperty("Durable", Boolean.TRUE);
      this.messageList = new MessageList((Comparator)null, kernel);
      this.pendingMessageList = new MessageList((Comparator)null, kernel);
      if (properties != null) {
         this.setProperties(properties);
      }

      this.created = true;
      if (logger.isDebugEnabled()) {
         logger.debug("Queue " + this.name + " created");
      }

   }

   public synchronized void setComparator(Comparator comparator) {
      super.setComparator(comparator);
      this.messageList.setComparator(comparator);
      if (comparator == null) {
         this.delegatingComparator = SEQUENCE_NUM_COMPARATOR;
      } else {
         this.delegatingComparator = new SortingComparator(comparator, this.kernel, false);
      }

   }

   void moveToPendingList(MessageReference ref) throws KernelException {
      assert (ref.getState() & 536870962) != 0;

      if (this.messageList.contains(ref)) {
         this.messageList.remove(ref);
         this.pendingMessageList.add(ref, (MessageReference)null);
      }

   }

   void moveToActiveList(MessageReference ref) throws KernelException {
      if (this.pendingMessageList.contains(ref)) {
         this.pendingMessageList.remove(ref);
         this.messageList.addUsingSequenceNumbers(ref);
      }

   }

   List get(Expression expression, int count, Object owner, Reader reader) throws KernelException {
      assert Thread.holdsLock(this);

      if (this.isSuspended(2)) {
         return null;
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Searching queue " + this.name + " for up to " + count + " messages for " + reader);
         }

         List list = null;
         Iterator iterator = this.messageList.iterator();
         this.beginTraverse();

         do {
            MessageReference ref = this.nextMatchFromIteratorOrGroup(iterator, expression, owner);
            if (ref == null) {
               break;
            }

            MessageHandle handle = ref.getMessageHandle();
            handle.pin(this.kernel);

            try {
               if (reader.acknowledge()) {
                  ref.setStatistics((AbstractStatistics)null);
               }

               ref.setState(2);
               ref.incrementDeliveryCount();
               Message useMessage = this.replaceMessage(ref);
               if (useMessage == null) {
                  useMessage = handle.getMessage();
               }

               MessageElementImpl element = new MessageElementImpl(ref, useMessage, true);
               element.setConsumerID(reader.getConsumerID());
               if (list == null) {
                  list = new ArrayList(count > 100 ? 100 : count);
               }

               list.add(element);
               if (reader.acknowledge()) {
                  this.autoAcknowledge(element, reader.getConsumerID());
               } else {
                  this.moveToPendingList(ref);
               }
            } finally {
               handle.unPin(this.kernel);
            }

            if (logger.isDebugEnabled()) {
               logger.debug("Found message element " + ref + " " + ref.getMessageHandle().getMessage() + " for reader " + reader);
            }

            --count;
         } while(count != 0);

         this.scheduleGroupMessages();
         return list;
      }
   }

   public KernelRequest send(Message message, SendOptions userOptions) throws KernelException {
      SendOptions options = this.initializeSendOptions(userOptions);
      this.checkDestinationState(message, options);
      GXATransaction transaction = this.kernel.getGXATransaction();
      MessageHandle handle = new MessageHandle(this.kernel, message, options);

      try {
         handle.setQueueReferenceCount(1);
         handle.setQuotaReferenceCount(1);
         handle.setQuota(this.quota);
         SendRequest sendRequest = this.sendAllocateQuota((SendRequest)null, handle, transaction, options);
         if (sendRequest != null) {
            handle.unPin(this.kernel);
            return sendRequest;
         } else {
            KernelRequest kernelRequest = this.sendAddAndPersist((SendRequest)null, options, handle, transaction, false);
            handle.unPin(this.kernel);
            return kernelRequest;
         }
      } catch (KernelException var8) {
         throw var8;
      }
   }

   private void beginTraverse() {
      this.lastGroupAvailable = this.lastIteratorMessage = null;
   }

   private MessageReference nextMatchFromIteratorOrGroup(Iterator iterator, Expression expression, Object owner) throws KernelException {
      do {
         MessageReference element = this.nextFromIteratorOrGroup(iterator, expression);
         if (element == null) {
            return null;
         }

         GroupImpl group = element.getGroup();
         if (group == null) {
            assert this.lastGroupAvailable == null;

            return element;
         }

         if (group.allocate(element, owner)) {
            this.rememberGroupNext(group.next(element));
            return element;
         }
      } while($assertionsDisabled || this.lastGroupAvailable == null);

      throw new AssertionError();
   }

   private boolean filterMatches(MessageReference ref, Expression expression) {
      assert ref.getMessageHandle().getPinCount() > 0;

      if (expression == null) {
         return true;
      } else {
         MessageElementImpl elt = new MessageElementImpl(ref, ref.getMessageHandle().getMessage());
         return expression.getFilter().match(elt, expression);
      }
   }

   private MessageReference nextFromIteratorOrGroup(Iterator iterator, Expression expression) throws KernelException {
      if (this.lastGroupAvailable != null) {
         this.lastGroupAvailable.getMessageHandle().pin(this.kernel);

         try {
            boolean iteratorPassedGroupAvailable = this.delegatingComparator.compare(this.lastGroupAvailable, this.lastIteratorMessage) < 0;
            if (iteratorPassedGroupAvailable && this.filterMatches(this.lastGroupAvailable, expression)) {
               MessageReference tmp = this.lastGroupAvailable;
               this.lastGroupAvailable.getMessageHandle().unPin(this.kernel);
               this.lastGroupAvailable = null;
               MessageReference var5 = tmp;
               return var5;
            }
         } finally {
            if (this.lastGroupAvailable != null) {
               this.lastGroupAvailable.getMessageHandle().unPin(this.kernel);
            }

         }

         this.lastGroupAvailable = null;
      }

      this.lastIteratorMessage = this.messageList.findNextVisible(iterator, expression);
      return this.lastIteratorMessage;
   }

   private void rememberGroupNext(MessageReference element) {
      if (element == null) {
         this.lastGroupAvailable = null;
      } else {
         this.unmatchedGroupList.add(element);
         if (element.isVisible()) {
            this.lastGroupAvailable = element;
         } else {
            this.lastGroupAvailable = null;
         }

      }
   }

   private void scheduleGroupMessages() {
      if (!this.unmatchedGroupList.isEmpty()) {
         ArrayList matchList = new ArrayList();

         MessageReference ref;
         do {
            ref = (MessageReference)this.unmatchedGroupList.remove();
            if (ref != null && !ref.isReceived()) {
               matchList.add(ref);
            }
         } while(ref != null);

         if (!matchList.isEmpty()) {
            Collections.sort(matchList, this.delegatingComparator);
            Iterator i = matchList.iterator();

            while(i.hasNext()) {
               this.scheduleMatch((MessageReference)i.next());
            }
         }
      }

      this.lastGroupAvailable = this.lastIteratorMessage = null;
   }

   protected void sendRedirected(Message message, SendOptions userOptions, GXATransaction transaction) throws KernelException {
      SendOptions options = this.initializeSendOptions(userOptions);
      MessageHandle handle = new MessageHandle(this.kernel, message, options);

      try {
         handle.setQueueReferenceCount(1);
         handle.setQuota(this.quota);
         this.allocateNoCheck(handle);
         SendRequest request = this.sendAddAndPersist((SendRequest)null, options, handle, transaction, true);
         if (request != null) {
            request.getResult();
         }
      } finally {
         handle.unPin(this.kernel);
      }

   }

   protected SendRequest sendAddAndPersist(SendRequest sendRequest, SendOptions options, MessageHandle handle, GXATransaction jtaTransaction, boolean localTran) {
      assert handle.getPinCount() > 0;

      InstrumentationHelper.beforeSendInterceptionPoint(handle);
      GXATransaction transaction = jtaTransaction;
      boolean hasLocalTransaction = false;

      SendRequest var41;
      try {
         QueueMessageReference ref = new QueueMessageReference(this, handle);
         ref.setDeliveryCount(options.getDeliveryCount());
         SequenceImpl sequence = this.setupSequence(ref, options, transaction != null);
         long seqNum = options.getSequenceNum();
         SequenceImpl inboundSequence = this.setupInboundSequence(options, transaction != null);
         long inboundSeqNum = options.getInboundSequenceNum();
         if (logger.isDebugEnabled()) {
            String seqName = null;
            String inboundSeqName = null;
            if (sequence != null) {
               seqName = sequence.getName();
            }

            if (inboundSequence != null) {
               inboundSeqName = inboundSequence.getName();
            }

            logger.debug("sendAddAndPersist() Queue=" + this.name + "\n sequence name=" + seqName + " sequence number=" + options.getSequenceNum() + "\n inboundSequenceName=" + inboundSeqName + " inboundSequenceNumber=" + options.getInboundSequenceNum() + " isSAF " + this.isSAFImportedDestination() + "\n msg ref=" + ref + " handle=" + handle + " msg=" + handle.getMessage());
         }

         if (transaction == null && (ref.isPersistent() || sequence != null)) {
            transaction = this.kernel.startLocalGXATransaction();
            hasLocalTransaction = true;
         }

         if (sequence != null) {
            sequence.lock(transaction);
         }

         if (inboundSequence != null) {
            inboundSequence.lock(transaction);
         }

         boolean isDupe;
         try {
            isDupe = this.sendAdd(ref, handle, (GXATransaction)transaction, false, sequence, seqNum, hasLocalTransaction || localTran, inboundSequence, inboundSeqNum);
         } finally {
            if (inboundSequence != null) {
               inboundSequence.unlock(transaction);
            }

            if (sequence != null) {
               sequence.unlock(transaction);
            }

         }

         if (!isDupe) {
            if (hasLocalTransaction) {
               assert !ref.isVisible();

               handle.pin(this.kernel);
               hasLocalTransaction = false;
               CompletionRequest ioRequest = new CompletionRequest();

               try {
                  ((GXALocalTransaction)transaction).commit(ioRequest, this.kernel.getWorkManager());
               } catch (Throwable var35) {
                  ((GXALocalTransaction)transaction).commitFailed();
                  throw new KernelException("Local Transaction " + transaction + " commit failure: " + var35, var35);
               }

               synchronized(ioRequest) {
                  if (ioRequest.hasResult()) {
                     try {
                        ioRequest.getResult();
                     } catch (Throwable var33) {
                        ((GXALocalTransaction)transaction).commitFailed();
                        throw new KernelException("Store I/O failure: " + var33, var33);
                     }

                     if (sendRequest != null) {
                        sendRequest.setResult((Object)null);
                     }
                  } else {
                     if (sendRequest == null) {
                        sendRequest = new SendRequest(this, handle, options);
                     }

                     sendRequest.setMessageReference(ref);
                     sendRequest.setState(3);
                     ioRequest.addListener(sendRequest, this.kernel.getWorkManager());
                  }
               }
            } else if (sendRequest != null) {
               sendRequest.setResult((Object)null);
            }

            InstrumentationHelper.afterSendInterceptionPoint(handle);
            var41 = sendRequest;
            return var41;
         }

         this.sendUndoQuota(handle);
         var41 = sendRequest;
      } catch (KernelException var37) {
         if (!hasLocalTransaction) {
            this.sendUndoQuota(handle);
         }

         if (sendRequest == null) {
            sendRequest = new SendRequest(this, handle, options);
         }

         sendRequest.setResult(var37);
         SendRequest var9 = sendRequest;
         return var9;
      } finally {
         if (hasLocalTransaction) {
            ((GXALocalTransaction)transaction).rollback();
         }

      }

      return var41;
   }

   private void checkSequence(SequenceImpl sequence, boolean isJTATransaction) throws KernelException {
      if (sequence.getQueue() != this) {
         throw new KernelException("The specified Sequence ('" + sequence.getName() + "') does not come from this destination");
      } else if (sequence.isDeleted()) {
         throw new KernelException("The specified Sequence ('" + sequence.getName() + "') has been deleted");
      } else if (isJTATransaction && !sequence.supportsJTATransactions()) {
         throw new KernelException("The specified Sequence ('" + sequence.getName() + "') has a mode that does not support JTA transactions");
      }
   }

   private SequenceImpl setupSequence(MessageReference ref, SendOptions options, boolean isJTATransaction) throws KernelException {
      SequenceImpl sequence = (SequenceImpl)options.getSequence();
      if (sequence != null) {
         this.checkSequence(sequence, isJTATransaction);
      }

      return sequence;
   }

   private SequenceImpl setupInboundSequence(SendOptions options, boolean isJTATransaction) throws KernelException {
      SequenceImpl sequence = (SequenceImpl)options.getInboundSequence();
      if (sequence != null) {
         this.checkSequence(sequence, isJTATransaction);
      }

      return sequence;
   }

   boolean sendAdd(MessageReference ref, MessageHandle handle, GXATransaction transaction, boolean multiSend, SequenceImpl sequence, long sequenceNum, boolean localTran) throws KernelException {
      return this.sendAdd(ref, handle, transaction, multiSend, sequence, sequenceNum, localTran, (SequenceImpl)null, 0L);
   }

   boolean sendAdd(MessageReference ref, MessageHandle handle, GXATransaction transaction, boolean multiSend, SequenceImpl sequence, long sequenceNum, boolean localTran, SequenceImpl inboundSequence, long inboundSequenceNum) throws KernelException {
      EventImpl event = null;
      if (logger.isDebugEnabled()) {
         logger.debug("Queue " + this.name + " sending message element: " + ref + " handle: " + handle + " msg: " + handle.getMessage() + "\n seq " + sequence + " seq num " + sequenceNum + "\n inbound seq " + inboundSequence + " inbound seq num " + inboundSequenceNum + "\n isSAF " + this.isSAFImportedDestination());
      }

      boolean transactional = transaction != null;
      if ((this.getLogMask() & 1) != 0 && !transactional && !ref.isPersistent()) {
         event = new MessageSendEventImpl(SecurityHelper.getCurrentSubjectName(), this, handle.getMessage(), (Xid)null, 0);
      }

      boolean isDupe;
      if (inboundSequence != null) {
         assert inboundSequence.isOverride();

         isDupe = inboundSequence.sendMessage(transaction, ref, inboundSequenceNum);
         if (isDupe) {
            if (logger.isDebugEnabled()) {
               logger.debug("Discarding message " + ref + " " + handle.getMessage() + " because it is a duplicate ");
            }

            return true;
         }
      }

      if (sequence != null) {
         assert !sequence.isOverride();

         isDupe = sequence.sendMessage(transaction, ref, sequenceNum);
         if (isDupe) {
            if (logger.isDebugEnabled()) {
               logger.debug("Discarding message " + ref + " " + handle.getMessage() + " because it is a duplicate ");
            }

            return true;
         }
      }

      this.add(ref, handle, false, transaction, event, sequence);
      if (transactional) {
         assert !ref.isVisible();

         if (logger.isDebugEnabled()) {
            logger.debug("Enlisting message " + ref + " " + handle.getMessage() + " in transaction " + transaction);
         }

         int opType = multiSend ? 6 : 1;
         SendOperation operation = new SendOperation(opType, this, ref, this.kernel, localTran);
         this.enlistOperation(transaction, operation);
      }

      return false;
   }

   private void allocateNoCheck(MessageHandle handle) {
      if (this.isQuotaCheckEnabled()) {
         this.quota.allocateNoCheck(handle);
      } else {
         handle.adjustQuotaReferenceCount(1);
      }

   }

   void free(MessageHandle handle) {
      if (this.isQuotaCheckEnabled()) {
         this.quota.free(handle);
      } else {
         handle.adjustQuotaReferenceCount(-1);
      }

   }

   private void checkAutoAck(boolean acknowledge) throws KernelException {
      if (this.durable && acknowledge) {
         throw new KernelException("Auto-acknowledge listeners not allowed on a durable queue");
      }
   }

   public ReceiveRequest receive(Expression expression, int count, boolean acknowledge, Object owner, long timeout, boolean started, String userBlob) throws KernelException {
      this.checkActivation();
      this.checkAutoAck(acknowledge);
      this.updateLastMessagesReceivedTime();
      return new ReceiveRequestImpl(this, expression, count, acknowledge, owner, started, timeout, this.getLimitedTimerManager(), userBlob);
   }

   public ListenRequest listen(Expression expression, int count, boolean acknowledge, Object owner, Listener listener, String userBlob, WorkManager workManager) throws KernelException {
      this.checkActivation();
      this.checkAutoAck(acknowledge);
      return new ListenRequestImpl(this, expression, count, acknowledge, owner, listener, (MultiListener)null, userBlob, workManager);
   }

   public ListenRequest listen(Expression expression, int count, boolean acknowledge, Object owner, Listener listener, MultiListener multiListener, String userBlob, WorkManager workManager) throws KernelException {
      this.checkActivation();
      this.checkAutoAck(acknowledge);
      return new ListenRequestImpl(this, expression, count, acknowledge, owner, listener, multiListener, userBlob, workManager);
   }

   public KernelRequest delete(MessageElement ref) throws KernelException {
      this.checkActivation();
      SequenceReference seqRef = ((MessageElementImpl)ref).getMessageReference().getSequenceRef();
      if (seqRef != null) {
         seqRef.getSequence().adminDeletedMessage(seqRef);
      }

      return this.delete(((MessageElementImpl)ref).getMessageReference());
   }

   public KernelRequest delete(List refs) throws KernelException {
      this.checkActivation();
      synchronized(this) {
         Iterator iter = refs.iterator();

         while(true) {
            if (!iter.hasNext()) {
               break;
            }

            MessageReference msgRef = (MessageReference)iter.next();
            if (msgRef.getQueue() != this) {
               throw new KernelException("Message " + msgRef.getMessageHandle().getMessage() + " not found on this queue " + this.getName());
            }

            if (!this.messageList.contains(msgRef) && !this.pendingMessageList.contains(msgRef)) {
               throw new KernelException("Message " + msgRef.getMessageHandle().getMessage() + " not found on message list");
            }

            if (!msgRef.isRemovable()) {
               throw new KernelException("Message " + msgRef.getMessageHandle().getMessage() + " with state: " + msgRef.getState() + " is in-use and is not removable");
            }

            msgRef.setState(536870912);
            if (this.messageList.contains(msgRef)) {
               this.moveToPendingList(msgRef);
            }
         }
      }

      boolean localTransaction = false;
      GXATransaction tran = this.kernel.getGXATransaction();
      if (tran == null) {
         tran = this.kernel.startLocalGXATransaction();
         localTransaction = true;
      }

      Iterator iter = refs.iterator();

      while(iter.hasNext()) {
         MessageReference msgRef = (MessageReference)iter.next();
         msgRef.setTransaction((GXATransaction)tran);
         this.enlistOperation((GXATransaction)tran, new ReceiveOperation(5, this, msgRef, (String)null, (RedeliveryParameters)null, this.kernel, localTransaction, false));
      }

      if (localTransaction) {
         CompletionRequest storeRequest = new CompletionRequest();
         KernelRequest kernelRequest = new KernelRequest();
         storeRequest.addListener(new GXATranCompletionListener(kernelRequest), this.kernel.getWorkManager());

         try {
            ((GXALocalTransaction)tran).commit(storeRequest, this.kernel.getWorkManager());
            return kernelRequest;
         } catch (Throwable var8) {
            ((GXALocalTransaction)tran).commitFailed();
            throw new KernelException("Local Transaction " + tran + " commit failure: " + var8, var8);
         }
      } else {
         return null;
      }
   }

   public KernelRequest delete(MessageReference msgRef) throws KernelException {
      this.checkActivation();
      if (msgRef.getQueue() != this) {
         throw new KernelException("Message not found");
      } else {
         synchronized(this) {
            if (!this.messageList.contains(msgRef) && !this.pendingMessageList.contains(msgRef)) {
               MessageReference temp = null;
               Iterator iterator = this.getAllMessageIterator();

               while(iterator.hasNext()) {
                  temp = (MessageReference)iterator.next();
                  if (msgRef.getSequenceNumber() == temp.getSequenceNumber()) {
                     break;
                  }
               }

               if (temp == null) {
                  throw new KernelException("Message not found");
               }

               msgRef = temp;
            }

            if (!msgRef.isRemovable()) {
               throw new KernelException("Message is in-use");
            }

            msgRef.setState(536870912);
            if (this.messageList.contains(msgRef)) {
               this.moveToPendingList(msgRef);
            }
         }

         boolean localTransaction = false;
         GXATransaction tran = this.kernel.getGXATransaction();
         if (tran == null) {
            tran = this.kernel.startLocalGXATransaction();
            localTransaction = true;
         }

         msgRef.setTransaction((GXATransaction)tran);
         this.enlistOperation((GXATransaction)tran, new ReceiveOperation(5, this, msgRef, (String)null, (RedeliveryParameters)null, this.kernel, localTransaction, false));
         if (localTransaction) {
            CompletionRequest storeRequest = new CompletionRequest();
            KernelRequest kernelRequest = new KernelRequest();
            storeRequest.addListener(new GXATranCompletionListener(kernelRequest), this.kernel.getWorkManager());

            try {
               ((GXALocalTransaction)tran).commit(storeRequest, this.kernel.getWorkManager());
               return kernelRequest;
            } catch (Throwable var7) {
               ((GXALocalTransaction)tran).commitFailed();
               throw new KernelException("Local Transaction " + tran + " commit failure: " + var7, var7);
            }
         } else {
            return null;
         }
      }
   }

   synchronized void addReader(Reader reader) throws KernelException {
      assert !this.readerList.contains(reader);

      synchronized(reader) {
         int reservedCount = reader.getCount();
         reader.incrementReserveCount(reservedCount);
         List list = this.get(reader.getExpression(), reservedCount, reader.getOwner(), reader);
         int newCount;
         if (list != null) {
            reservedCount -= list.size();
            if (reservedCount > 0) {
               reader.incrementReserveCount(-reservedCount);
            }

            newCount = reader.deliver(list);
         } else {
            reader.incrementReserveCount(-reservedCount);
            newCount = reader.getCount();
         }

         if (newCount > 0) {
            logger.debug("Adding consumer to reader list");
            this.readerList.add(reader);
         }

      }
   }

   synchronized void removeReader(Reader reader) {
      if (this.readerList.contains(reader)) {
         this.readerList.remove(reader);
      }

   }

   public KernelRequest acknowledge(MessageElement ref) throws KernelException {
      ArrayList ackList = new ArrayList(1);
      ackList.add(ref);
      return this.acknowledge((List)ackList);
   }

   public KernelRequest acknowledge(List list) throws KernelException {
      this.checkActivation();
      if (list.isEmpty()) {
         return null;
      } else if (!this.isActive()) {
         return null;
      } else {
         boolean logRemove = (this.getLogMask() & 2) != 0;
         List eventList = logRemove ? new ArrayList() : null;
         Collections.sort(list, SEQUENCE_NUM_COMPARATOR2);
         GXALocalTransaction localTran = null;
         Iterator i = list.iterator();
         ArrayList npList = new ArrayList(list.size());

         while(i.hasNext()) {
            MessageElementImpl element = (MessageElementImpl)i.next();
            MessageReference ref = element.getMessageReference();
            if (logger.isDebugEnabled()) {
               logger.debug("Acknowledge called for " + element + ' ' + ref + " " + ref.getMessageHandle().getMessage());
            }

            if (ref.isVisible()) {
               throw new KernelException("Cannot acknowledge a non-pending message");
            }

            InstrumentationHelper.beforeAcknowledgeInterceptionPoint(ref, this.kernel);
            if (ref.isPersistent()) {
               if (localTran == null) {
                  localTran = this.kernel.startLocalGXATransaction();
               }

               ReceiveOperation op = new ReceiveOperation(2, this, ref, element.getConsumerID(), (RedeliveryParameters)null, this.kernel, true, false);
               this.enlistOperation(localTran, op);
            } else {
               if (eventList != null) {
                  eventList.add(new MessageReceiveEventImpl(SecurityHelper.getCurrentSubjectName(), this, ref.getMessage(this.kernel), (Xid)null, element.getConsumerID(), element.getDeliveryCount()));
               }

               npList.add(element);
            }
         }

         if (!npList.isEmpty()) {
            this.acknowledgeFinish(npList, eventList);
         }

         if (localTran != null) {
            CompletionRequest storeRequest = new CompletionRequest();
            KernelRequest kernelRequest = new KernelRequest();
            storeRequest.addListener(new GXATranCompletionListener(kernelRequest), this.kernel.getWorkManager());

            try {
               localTran.commit(storeRequest, this.kernel.getWorkManager());
               return kernelRequest;
            } catch (Throwable var10) {
               localTran.commitFailed();
               throw new KernelException("Local Transaction " + localTran + " commit failure: " + var10, var10);
            }
         } else {
            return null;
         }
      }
   }

   void acknowledgeFinish(List list, List eventList) {
      Iterator i = list.iterator();

      while(i.hasNext()) {
         MessageReference ref = ((MessageElementImpl)i.next()).getMessageReference();
         this.remove(ref);
      }

      if (eventList != null) {
         this.addEvent(eventList);
      }

   }

   private void autoAcknowledge(MessageElementImpl ref, String consumerID) {
      MessageReference element = ref.getMessageReference();

      assert !element.isPersistent();

      if ((this.getLogMask() & 2) != 0) {
         this.addEvent(new MessageReceiveEventImpl(SecurityHelper.getCurrentSubjectName(), this, ref.getMessage(), (Xid)null, consumerID, ref.getDeliveryCount()));
      }

      this.remove(element);
   }

   void negativeAcknowledgeInternal(MessageReference ref, long delay, boolean incrementDeliveryCount, String newGroupName) throws KernelException {
      assert Thread.holdsLock(this);

      this.checkActivation();
      if (this.isActive()) {
         if ((ref.getState() & 536870914) == 0) {
            throw new KernelException("Cannot NACK a non-pending message");
         } else if (!this.pendingMessageList.contains(ref)) {
            throw new KernelException("Message has already been acknowledged");
         } else {
            GroupImpl newGroup = null;
            if (newGroupName != null) {
               newGroup = this.createGroup(newGroupName);
               if (ref.getGroupRef() != null && ref.getGroupRef().getGroup() != newGroup) {
                  throw new KernelException("The message is already part of a  different group");
               }
            }

            if (logger.isDebugEnabled()) {
               logger.debug("Negatively acknowledging " + ref);
            }

            InstrumentationHelper.beforeNegativeAcknowledgeInterceptionPoint(ref, this.kernel);
            if (incrementDeliveryCount) {
               if (ref.isPersistent()) {
                  this.persistDeliveryCount(ref);
               }
            } else {
               ref.decrementDeliveryCount();
            }

            MessageHandle handle = ref.getMessageHandle();
            boolean stillPending = ref.isExpired();
            if (ref.getDeliveryCount() > handle.getRedeliveryLimit()) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Message " + ref + " exceeded redelivery limit");
               }

               ref.setState(64);
               stillPending = true;
            } else if (delay > 0L) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Delaying redelivery of " + ref + " by " + delay + " milliseconds");
               }

               ref.setState(16);
               Timer timer = this.getLimitedTimerManager().schedule(new DeliveryListener(ref), delay);
               ref.setDeliveryTimer(timer);
               stillPending = true;
            }

            if (!stillPending) {
               this.moveToActiveList(ref);
            }

            MessageReference makeAvailable = ref;
            if (newGroup != null && !newGroup.contains(ref)) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Adding message " + ref + " to group " + newGroup);
               }

               ref.clearState(536870914);
               newGroup.add(ref);
            } else if (ref.getGroup() != null) {
               makeAvailable = ref.getGroup().free(ref, 536870914);
            } else {
               ref.clearState(536870914);
            }

            if (makeAvailable != null) {
               this.makeMessageAvailable(makeAvailable);
            }

         }
      }
   }

   private void persistDeliveryCount(MessageReference ref) throws KernelException {
      PersistentStoreTransaction tran = this.kernel.getPersistence().startStoreTransaction();
      MultiPersistenceHandle persHandle = null;
      if (ref instanceof MultiMessageReference) {
         persHandle = ((MultiMessageReference)ref).getPersistenceHandle();
         persHandle.lock(tran);
         if (persHandle.getPersistentHandle() == null) {
            persHandle.unlock(tran);
            return;
         }
      } else if (((QueueMessageReference)ref).getPersistentHandle() == null) {
         return;
      }

      ref.getMessageHandle().pin(this.kernel);

      try {
         this.kernel.getPersistence().updateMessage(tran, ref);
         CompletionRequest completion = new CompletionRequest();
         tran.commit(completion);
      } finally {
         try {
            ref.getMessageHandle().unPin(this.kernel);
         } finally {
            if (persHandle != null) {
               persHandle.unlock(tran);
            }

         }
      }

   }

   public synchronized void negativeAcknowledge(MessageElement ref, long delay, KernelRequest request) throws KernelException {
      this.negativeAcknowledge((MessageElement)ref, delay, true, (String)null, request);
   }

   public synchronized void negativeAcknowledge(MessageElement ref, long delay, boolean incrementDeliveryCount, KernelRequest request) throws KernelException {
      this.negativeAcknowledge((MessageElement)ref, delay, incrementDeliveryCount, (String)null, request);
   }

   public synchronized void negativeAcknowledge(MessageElement ref, long delay, boolean incrementDeliveryCount, String newGroup, KernelRequest request) throws KernelException {
      MessageReference element = ((MessageElementImpl)ref).getMessageReference();
      this.negativeAcknowledgeInternal(element, delay, incrementDeliveryCount, newGroup);
      request.setResult((Object)null);
   }

   public synchronized void negativeAcknowledge(List list, long delay, KernelRequest request) throws KernelException {
      this.negativeAcknowledge((List)list, delay, true, (String)null, request);
   }

   public synchronized void negativeAcknowledge(List list, long delay, boolean incrementDeliveryCount, KernelRequest request) throws KernelException {
      this.negativeAcknowledge((List)list, delay, incrementDeliveryCount, (String)null, request);
   }

   public synchronized void negativeAcknowledge(List list, long delay, boolean incrementDeliveryCount, String newGroup, KernelRequest request) throws KernelException {
      Iterator i = list.iterator();

      while(i.hasNext()) {
         MessageReference element = ((MessageElementImpl)i.next()).getMessageReference();
         this.negativeAcknowledgeInternal(element, delay, incrementDeliveryCount, newGroup);
      }

      request.setResult((Object)null);
   }

   public void associate(MessageElement ref, RedeliveryParameters redeliveryParams) throws KernelException {
      this.checkActivation();
      if (this.isActive()) {
         GXATransaction transaction = null;
         KernelException ke = null;

         try {
            transaction = this.kernel.getGXATransaction();
         } catch (KernelException var10) {
            transaction = this.kernel.startLocalGXATransaction();
            if (logger.isDebugEnabled()) {
               logger.debug("getGXATransaction() failed, enlist local transaction " + transaction + " message " + (MessageElementImpl)ref);
            }

            ke = var10;
         }

         if (transaction == null) {
            throw new KernelException("associate may not be called outside a transaction");
         } else {
            try {
               this.associateInternal((MessageElementImpl)ref, (GXATransaction)transaction, redeliveryParams);
            } finally {
               if (ke != null) {
                  if (logger.isDebugEnabled()) {
                     logger.debug("rollback local transaction " + transaction + " message " + (MessageElementImpl)ref);
                  }

                  ((GXALocalTransaction)transaction).rollback();
                  throw ke;
               }

            }

         }
      }
   }

   public void associate(List list, RedeliveryParameters redeliveryParams) throws KernelException {
      this.checkActivation();
      if (this.isActive()) {
         GXATransaction transaction = null;
         KernelException ke = null;

         try {
            transaction = this.kernel.getGXATransaction();
         } catch (KernelException var10) {
            transaction = this.kernel.startLocalGXATransaction();
            if (logger.isDebugEnabled()) {
               logger.debug("getGXATransaction() failed, enlist local transaction " + transaction);
            }

            ke = var10;
         }

         if (transaction == null) {
            throw new KernelException("associate may not be called outside a transaction");
         } else {
            Iterator i = list.iterator();

            try {
               while(i.hasNext()) {
                  this.associateInternal((MessageElementImpl)i.next(), (GXATransaction)transaction, redeliveryParams);
               }
            } finally {
               if (ke != null) {
                  if (logger.isDebugEnabled()) {
                     logger.debug("rollback local transaction " + transaction);
                  }

                  ((GXALocalTransaction)transaction).rollback();
                  throw ke;
               }

            }

         }
      }
   }

   void associateInternal(MessageElementImpl ref, GXATransaction transaction, RedeliveryParameters redeliveryParams) throws KernelException {
      if (logger.isDebugEnabled()) {
         logger.debug("Message " + ref + " now part of a pending tran");
      }

      MessageReference element = ref.getMessageReference();
      element.setTransaction(transaction);
      this.enlistOperation(transaction, new ReceiveOperation(2, this, element, ref.getConsumerID(), redeliveryParams, this.kernel, false, false));
   }

   public synchronized void suspend(int mask) throws KernelException {
      int oldMask = this.getMask();
      super.suspend(mask);
      if ((mask & 4) != 0 && (oldMask & 4) == 0) {
         if (logger.isDebugEnabled()) {
            logger.debug("Suspending visibility of destination " + this.name);
         }

         Iterator iterator = this.getAllMessageIterator();

         while(iterator.hasNext()) {
            MessageReference msgRef = (MessageReference)iterator.next();
            if (!msgRef.isVisible()) {
               msgRef.setState(128);
            }
         }
      }

   }

   protected synchronized void activate() throws KernelException {
      super.activate();
      if (logger.isDebugEnabled()) {
         logger.debug("Queue " + this.name + " activating");
      }

      if (this.recoveryMessageList != null) {
         MessageReference[] refs = new MessageReference[this.recoveryMessageList.size()];
         refs = (MessageReference[])((MessageReference[])this.recoveryMessageList.toArray(refs));
         Arrays.sort(refs, SEQUENCE_NUM_COMPARATOR);
         if (logger.isDebugEnabled()) {
            logger.debug("Restoring " + refs.length + " messages to " + this.name);
         }

         Set allSequences = new HashSet();

         for(int inc = 0; inc < refs.length; ++inc) {
            MessageReference ref = refs[inc];
            MessageHandle handle = ref.getMessageHandle();
            handle.setQuota(this.quota);
            this.allocateNoCheck(handle);
            this.add(ref, handle, true, (GXATransaction)null, (EventImpl)null, (SequenceImpl)null);
            if (ref.getSequenceRef() != null) {
               allSequences.add(ref.getSequenceRef().getSequence());
               ref.getSequenceRef().getSequence().recoverMessage(ref);
               if (ref.getSequenceRef().getSequence().getMode() == 8) {
                  List assignedList = ref.getSequenceRef().getSequence().getAssignedMessages(256);
                  if (assignedList != null) {
                     Iterator i = assignedList.iterator();

                     while(i.hasNext()) {
                        this.makeMessageAvailable((MessageReference)i.next());
                     }
                  }
               }
            }
         }

         Iterator iter = allSequences.iterator();

         while(iter.hasNext()) {
            SequenceImpl sequence = (SequenceImpl)iter.next();
            sequence.recoveryComplete(this);
         }

         this.recoveryMessageList = null;
      }

      if (this.recoveryOperationList != null) {
         GXAResource resource = this.kernel.getGXAResource();
         resource.addRecoveredOperations(this.recoveryOperationList);
         this.recoveryOperationList = null;
      }

      this.redirector.start();
      if (logger.isDebugEnabled()) {
         logger.debug("Queue " + this.name + " fully activated");
      }

   }

   protected synchronized void deactivate() {
      if (logger.isDebugEnabled()) {
         logger.debug("Queue " + this.name + " deactivating");
      }

      this.redirector.stop();
      if (this.messageList.size() == 0) {
         if (logger.isDebugEnabled()) {
            logger.debug("Queue " + this.name + " fully deactivated");
         }

      } else {
         this.recoveryMessageList = new ArrayList(this.messageList.size());
         Iterator iterator = this.getAllMessageIterator();

         while(iterator.hasNext()) {
            MessageReference ref = (MessageReference)iterator.next();
            this.deactivate(ref);
         }

      }
   }

   private void deactivate(MessageReference ref) {
      this.free(ref.getMessageHandle());
      if (ref.getStatistics() != null) {
         ref.setStatistics((AbstractStatistics)null);
      }

      this.cleanup(ref);
      ref.resetState();
      this.recoveryMessageList.add(ref);
   }

   private synchronized void resumeFromReceive() throws KernelException {
      if (logger.isDebugEnabled()) {
         logger.debug("Resuming receive of destination " + this.name);
      }

      EmbeddedList tmpList = new EmbeddedList();
      tmpList.moveToEnd(this.readerList);
      Iterator i = tmpList.iterator();

      while(i.hasNext()) {
         Reader tmpRdr = (Reader)i.next();
         i.remove();
         this.addReader(tmpRdr);
      }

   }

   private synchronized void resumeFromVisibility() throws KernelException {
      if (logger.isDebugEnabled()) {
         logger.debug("Resuming visibility of destination " + this.name);
      }

      Iterator iterator = this.getAllMessageIterator();

      while(iterator.hasNext()) {
         MessageReference msgRef = (MessageReference)iterator.next();
         if ((msgRef.getState() & 128) != 0) {
            msgRef.clearState(128);
            this.makeMessageAvailable(msgRef);
         }
      }

   }

   public void resume(int mask) throws KernelException {
      int oldMask = this.getMask();
      super.resume(mask);
      if ((mask & oldMask & 2) != 0) {
         this.resumeFromReceive();
      }

      if ((mask & oldMask & 4) != 0) {
         this.resumeFromVisibility();
      }

   }

   protected synchronized void enableQuotaCheck() {
      if (!this.isSuspended(16384)) {
         Iterator iterator = this.getAllMessageIterator();

         while(iterator.hasNext()) {
            MessageReference ref = (MessageReference)iterator.next();
            this.allocateNoCheck(ref.getMessageHandle());
         }
      }

   }

   protected synchronized void setQuota(QuotaImpl newQuota) throws KernelException {
      if (!this.isSuspended(16384)) {
         Iterator iterator = this.getAllMessageIterator();

         while(iterator.hasNext()) {
            MessageReference ref = (MessageReference)iterator.next();
            this.setQuota(ref, newQuota);
         }
      }

      super.setQuota(newQuota);
   }

   private void setQuota(MessageReference ref, QuotaImpl newQuota) {
      MessageHandle handle = ref.getMessageHandle();
      if (handle.getQuota() != newQuota) {
         if (handle.getQuota() != null) {
            handle.getQuota().adjustDownWard(handle);
         }

         newQuota.adjustUpWard(handle);
         handle.setQuota(newQuota);
      }

   }

   synchronized void setStatistics(AbstractStatistics newStats) {
      Iterator i = this.getAllMessageIterator();

      while(i.hasNext()) {
         ((MessageReference)i.next()).setStatistics(newStats);
      }

   }

   protected synchronized void updateIgnoreExpiration(boolean ignore) {
      boolean wasIgnored = this.ignoreExpiration;
      super.updateIgnoreExpiration(ignore);
      if (wasIgnored && !ignore) {
         long now = System.currentTimeMillis();
         Iterator iterator = this.getAllMessageIterator();

         while(iterator.hasNext()) {
            MessageReference ref = (MessageReference)iterator.next();
            this.stopIgnoringExpiration(ref, now);
         }
      }

   }

   private void stopIgnoringExpiration(MessageReference ref, long now) {
      long expTime = ref.getMessageHandle().getExpirationTime();
      if (expTime > 0L && !ref.isExpired() && expTime <= now) {
         this.expireNow(ref);
      }

   }

   void triggerExpirationTimerNow(MessageReference ref) {
      Timer timer;
      synchronized(this) {
         timer = ref.getExpirationTimer();
      }

      assert timer != null;

      if (timer != null) {
         timer.cancel();
      }

      this.expireHandler(ref);
   }

   void expireNow(MessageReference ref) {
      try {
         this.cleanupTimers(ref);
         this.markMessageExpired(ref);
         this.makeMessageAvailable(ref);
      } catch (KernelException var3) {
         if (logger.isDebugEnabled()) {
            logger.debug("Error making message available: " + var3, var3);
         }
      }

   }

   private void expireHandler(MessageReference ref) {
      synchronized(this) {
         if (ref.getExpirationTimer() != null) {
            ref.setExpirationTimer((Timer)null);
         }

         try {
            this.markMessageExpired(ref);
            this.makeMessageAvailable(ref);
         } catch (KernelException var5) {
            if (logger.isDebugEnabled()) {
               logger.debug("Error making newly-visible message available: " + var5, var5);
            }
         }

      }
   }

   public Cursor createCursor(boolean snapshot, Expression expression, int state) throws KernelException {
      return new CursorImpl(this.kernel, this, snapshot, expression, state);
   }

   private synchronized void empty(boolean deleteAll, KernelRequest request, Runnable cleanUpWork) throws KernelException {
      if (logger.isDebugEnabled()) {
         logger.debug("Deleting all messages from " + this.getName() + " deleteAll = " + deleteAll);
      }

      Object persistentList;
      if (this.isSuspended(16384)) {
         persistentList = this.recoveryMessageList;
         this.recoveryMessageList = null;
      } else {
         persistentList = new ArrayList();
         Iterator iterator = this.getAllMessageIterator();

         label44:
         while(true) {
            MessageReference ref;
            do {
               if (!iterator.hasNext()) {
                  break label44;
               }

               ref = (MessageReference)iterator.next();
            } while(!deleteAll && !ref.isVisible());

            if ((this.getLogMask() & 2) != 0) {
               this.addEvent(new MessageRemoveEventImpl(SecurityHelper.getCurrentSubjectName(), this, ref.getMessage(this.kernel), (Xid)null, ref.getDeliveryCount()));
            }

            this.remove(ref);
            if (ref.isPersistent()) {
               ((List)persistentList).add(ref);
            }
         }
      }

      if (persistentList != null && !((List)persistentList).isEmpty()) {
         this.kernel.getPersistence().deleteMessages((List)persistentList, request, cleanUpWork);
      } else {
         if (cleanUpWork != null) {
            cleanUpWork.run();
         }

         request.setResult((Object)null);
      }

   }

   public void empty(KernelRequest request) throws KernelException {
      this.empty(false, request, (Runnable)null);
   }

   public synchronized void delete(KernelRequest request) throws KernelException {
      this.kernel.checkOpened();
      this.setDeleted(true);
      if (this.durable && this.kernel.isOpened()) {
         this.kernel.getPersistence().updateDestination(this);
      }

      this.readerList.clear();
      if (this.quota != null) {
         this.quota.removeDestination(this);
      }

      this.kernel.queueDeleted(this, false);
      this.empty(true, request, new DestroyCompletionListener());
      super.delete(request);
   }

   void emptyAll() throws KernelException {
      KernelRequest request = new KernelRequest();
      this.empty(true, request, new DestroyCompletionListener());
   }

   synchronized KernelRequest deleteSequenceMessages(SequenceImpl sequence, Runnable cleanUpWork) throws KernelException {
      if (logger.isDebugEnabled()) {
         logger.debug("Deleting all messages from " + this.getName() + " for sequence " + sequence);
      }

      List persistentList = null;
      if (!this.isSuspended(16384)) {
         persistentList = new ArrayList();
         Iterator iterator = this.getAllMessageIterator();

         while(iterator.hasNext()) {
            MessageReference ref = (MessageReference)iterator.next();
            if (ref.getSequenceRef() != null && ref.getSequenceRef().getSequence() == sequence) {
               if ((this.getLogMask() & 2) != 0) {
                  this.addEvent(new MessageRemoveEventImpl(SecurityHelper.getCurrentSubjectName(), this, ref.getMessage(this.kernel), (Xid)null, ref.getDeliveryCount()));
               }

               this.remove(ref);
               if (ref.isPersistent()) {
                  persistentList.add(ref);
               }
            }
         }
      }

      if (persistentList != null && !persistentList.isEmpty()) {
         KernelRequest request = new KernelRequest();
         this.kernel.getPersistence().deleteMessages(persistentList, request, cleanUpWork);
         return request;
      } else {
         if (cleanUpWork != null) {
            cleanUpWork.run();
         }

         return null;
      }
   }

   void makeMessageAvailable(MessageReference element) throws KernelException {
      assert Thread.holdsLock(this);

      if (element.isOnMessageList()) {
         if (element.isVisible()) {
            this.match(element);
         } else if (element.isRedirectable()) {
            this.redirector.scheduleRedirection(element);
         }
      }

   }

   void markMessageExpired(MessageReference ref) throws KernelException {
      assert Thread.holdsLock(this);

      if (this.ignoreExpiration) {
         this.redirector.scheduleRedirection(ref);
      } else {
         assert (ref.getState() & 32) == 0;

         ref.setState(32);
         this.moveToPendingList(ref);
      }

   }

   private long setupDeliveryTime(MessageReference ref, long deliveryTime, boolean noDeliveryDelay) {
      long additionalDelayRequired;
      if (noDeliveryDelay) {
         additionalDelayRequired = 0L;
      } else {
         additionalDelayRequired = deliveryTime - System.currentTimeMillis();
      }

      if (additionalDelayRequired > 0L) {
         if (logger.isDebugEnabled()) {
            logger.debug("Scheduling delivery in " + deliveryTime + " milliseconds for " + ref);
         }

         ref.setState(16);
         Timer timer = this.getLimitedTimerManager().schedule(new DeliveryListener(ref), additionalDelayRequired);
         ref.setDeliveryTimer(timer);
      }

      return additionalDelayRequired;
   }

   boolean setupExpiration(MessageReference ref, long expirationTime, long deliveryTime) throws KernelException {
      expirationTime -= System.currentTimeMillis();
      if (expirationTime > 0L && expirationTime > deliveryTime && (ref.getState() & 32) == 0) {
         if (logger.isDebugEnabled()) {
            logger.debug("Scheduling expiration in " + expirationTime + " milliseconds for " + ref + " " + ref.getMessageHandle().getMessage());
         }

         Timer timer = this.getDirectTimerManager().schedule(new ExpirationListener(ref), expirationTime);
         ref.setExpirationTimer(timer);
         return false;
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Message " + ref + " has already expired");
         }

         this.markMessageExpired(ref);
         return true;
      }
   }

   private GroupImpl setupGroup(MessageReference ref, String groupName, SequenceImpl sequence) {
      if (logger.isDebugEnabled()) {
         logger.debug("Adding group " + groupName + " for element " + ref);
      }

      GroupImpl group = this.createGroup(groupName);
      if ((sequence == null || !sequence.requiresUpdate()) && !ref.isOutOfOrder()) {
         group.add(ref);
      } else {
         group.addGroupRef(ref);
      }

      return group;
   }

   private Reader matchWithReader(MessageReference ref, MessageHandle handle) {
      Reader matchedReader = this.findReader(ref);
      if (matchedReader != null) {
         if (logger.isDebugEnabled()) {
            logger.debug("Found a reader: " + matchedReader + " acknowledge = " + matchedReader.acknowledge() + " element = " + ref + " " + handle.getMessage());
         }

         ref.setState(2);
         if (matchedReader.acknowledge()) {
            handle.adjustQueueReferenceCount(-1);
            this.free(handle);
            this.cleanupTimers(ref);
         }
      }

      return matchedReader;
   }

   private void addMessageToList(MessageReference ref, MessageReference subsequentRef, boolean recovering, boolean pending) throws KernelException {
      if (!recovering) {
         ref.setSequenceNumber(++this.lastSequenceNumber);
      }

      if (pending) {
         assert !ref.isVisible();

         this.pendingMessageList.add(ref, (MessageReference)null);
      } else {
         this.messageList.add(ref, subsequentRef);
      }

      if (this.reservedStatus.get() != null && (Integer)this.reservedStatus.get() == 1) {
         --this.reservedSize;
         this.reservedStatus.set(2);
         if (logger.isDebugEnabled()) {
            logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", addMessageToList, reservedSize--: " + this.reservedSize);
         }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Added message to the queue list: " + ref + " " + ref.getMessageHandle().getMessage());
      }

      ref.setStatistics(this.statistics);
      if (ref.isRedirectable()) {
         this.redirector.scheduleRedirection(ref);
      }

   }

   private void add(MessageReference ref, MessageHandle handle, boolean recovering, GXATransaction transaction, EventImpl event, SequenceImpl sequence) throws KernelException {
      synchronized(this) {
         if (this.isDeleted()) {
            return;
         }

         MessageReference subsequentRef = null;
         boolean pending = false;
         if (sequence != null) {
            subsequentRef = sequence.getSubsequentMessage(ref);
         }

         long deliveryTime = handle.getDeliveryTime();
         if (deliveryTime != 0L) {
            deliveryTime = this.setupDeliveryTime(ref, deliveryTime, handle.isNoDeliveryDelay());
            if (deliveryTime > 0L) {
               pending = true;
            }
         }

         long expirationTime = handle.getExpirationTime();
         if (expirationTime != 0L && this.setupExpiration(ref, expirationTime, deliveryTime)) {
            pending = true;
         }

         GroupImpl group = null;
         String groupName = handle.getGroupName();
         if (groupName != null) {
            if (this.isSAFImportedDestination() && ref.getSequenceRef() != null) {
               groupName = this.getHashedBasedName(groupName, ref.getSequenceRef().getSequence().getName());
               if (logger.isDebugEnabled()) {
                  logger.debug("QeuueImpl.add() isSaf true  handle group BEFORE " + handle.getGroupName() + " handle group AFTER " + groupName);
               }

               handle.setSAFGroupName(groupName);
            }

            group = this.setupGroup(ref, groupName, sequence);
         }

         Reader matchedReader = null;
         if (!recovering) {
            assert handle.getPinCount() > 0;

            if (transaction != null) {
               ref.setState(1);
            } else if (ref.isVisible()) {
               assert !ref.isPersistent();

               matchedReader = this.matchWithReader(ref, handle);
               if (matchedReader != null) {
                  pending = true;
               }
            }
         }

         if (event != null) {
            this.addEvent(event);
         }

         if (matchedReader != null && matchedReader.acknowledge()) {
            if (group != null) {
               MessageReference groupAvail = group.remove(ref);
               if (groupAvail != null) {
                  this.makeMessageAvailable(groupAvail);
               }
            }
         } else {
            this.addMessageToList(ref, subsequentRef, recovering, pending);
         }

         if (matchedReader != null) {
            this.deliverToReader(ref, matchedReader);
         }
      }

      if (!recovering && transaction == null) {
         this.statistics.incrementReceived(ref);
      }

   }

   void addUnorderedMessages(SequenceImpl sequence) throws KernelException {
      assert Thread.holdsLock(this);

      List refs = sequence.getUnorderedMessages();
      if (refs != null) {
         MessageReference ref;
         for(Iterator i = refs.iterator(); i.hasNext(); this.makeMessageAvailable(ref)) {
            ref = (MessageReference)i.next();
            GroupImpl group = ref.getGroup();
            if (group != null) {
               group.add(ref);
            }
         }

      }
   }

   synchronized void updateSequence(PersistentStoreTransaction tran, MessageReference ref, boolean alreadyPersisted, int stateToClear) {
      assert ref.getSequenceRef() != null;

      if ((ref.getState() & ~(stateToClear | 32)) == 0) {
         SequenceImpl sequence = ref.getSequenceRef().getSequence();

         assert sequence.requiresUpdate();

         MessageReference prevMsg = sequence.updateVisibleMessage(ref, this.kernel, tran);
         if (prevMsg != null && this.messageList.contains(ref)) {
            this.messageList.moveAfter(ref, prevMsg);
         }

         GroupImpl group = ref.getGroup();
         if (group != null && !group.contains(ref)) {
            group.add(ref);
         }

         if (alreadyPersisted && this.durable && ref.isPersistent()) {
            assert !(ref instanceof MultiMessageReference);

            this.kernel.getPersistence().updateMessage(tran, ref);
         }

      }
   }

   void messageSendComplete(MessageReference ref) throws KernelException {
      assert Thread.holdsLock(this);

      if (this.statistics != null) {
         this.statistics.incrementReceived(ref);
      }

      this.makeMessageAvailable(ref);
      if (ref.getSequenceRef() != null) {
         this.addUnorderedMessages(ref.getSequenceRef().getSequence());
      }

   }

   void cleanupTimers(MessageReference element) {
      Timer timer = element.getExpirationTimer();
      if (timer != null) {
         timer.cancel();
      }

      element.setExpirationTimer((Timer)null);
      timer = element.getDeliveryTimer();
      if (timer != null) {
         timer.cancel();
      }

      element.setDeliveryTimer((Timer)null);
   }

   void removeFromGroup(MessageReference ref) {
      if (ref.getGroup() != null) {
         MessageReference msg = ref.getGroup().remove(ref);
         if (msg != null) {
            this.scheduleMatch(msg);
         }
      }

   }

   void cleanup(MessageReference ref) {
      if (this.messageList.contains(ref)) {
         this.messageList.remove(ref);
      } else if (this.pendingMessageList.contains(ref)) {
         this.pendingMessageList.remove(ref);
      } else {
         assert false : "Message not on any correct message list";
      }

      this.cleanupTimers(ref);
      this.removeFromGroup(ref);
      SequenceReference seqRef = ref.getSequenceRef();
      if (seqRef != null) {
         seqRef.getSequence().removeMessage(seqRef);
      }

   }

   void remove(MessageReference element) {
      if (logger.isDebugEnabled()) {
         logger.debug("Removing message " + element + " " + element.getMessageHandle().getMessage());
      }

      MessageHandle handle = element.getMessageHandle();
      handle.adjustQueueReferenceCount(-1);
      this.free(handle);
      synchronized(this) {
         assert element.isOnMessageList();

         if (element.getStatistics() != null) {
            element.setStatistics((AbstractStatistics)null);
         }

         this.cleanup(element);
      }

      handle.removePagedState(this.kernel);
   }

   synchronized void addRecoveredMessage(MessageReference element) {
      if (element.getMessageHandle().getMessage() == null && element.getMessageHandle().isPersistBody()) {
         MessagingLogger.logUnexpectedNullMessage(element.toString());
      } else {
         if (this.recoveryMessageList == null) {
            this.recoveryMessageList = new ArrayList();
         }

         this.recoveryMessageList.add(element);
         if (element.getSequenceNumber() > this.lastSequenceNumber) {
            this.lastSequenceNumber = element.getSequenceNumber();
         }

      }
   }

   synchronized void addRecoveredOperation(AbstractOperation op) {
      if (this.recoveryOperationList == null) {
         this.recoveryOperationList = new ArrayList();
      }

      this.recoveryOperationList.add(op);
   }

   private Reader findReader(MessageReference element) {
      assert Thread.holdsLock(this);

      assert element.getMessageHandle().getPinCount() > 0;

      if (this.isSuspended(2)) {
         return null;
      } else {
         Iterator readers = this.readerList.iterator();

         while(true) {
            if (readers.hasNext()) {
               Reader reader = (Reader)readers.next();
               synchronized(reader) {
                  if (reader.getCount() <= 0) {
                     continue;
                  }

                  reader.incrementReserveCount(1);
               }

               Expression expression = reader.getExpression();
               if (!this.filterMatches(element, expression)) {
                  continue;
               }

               GroupImpl group = element.getGroup();
               if (group != null) {
                  if (!group.allocate(element, reader.getOwner())) {
                     continue;
                  }

                  MessageReference next = group.next(element);
                  if (next != null && !next.isOrdered()) {
                     this.scheduleMatch(next);
                  }
               }

               element.incrementDeliveryCount();
               readers.remove();
               return reader;
            }

            return null;
         }
      }
   }

   private void match(MessageReference ref) throws KernelException {
      assert Thread.holdsLock(this);

      MessageHandle handle = ref.getMessageHandle();
      handle.pin(this.kernel);

      try {
         Reader reader = this.findReader(ref);
         if (reader != null) {
            if (reader.acknowledge()) {
               ref.setStatistics((AbstractStatistics)null);
            }

            ref.setState(2);
            MessageElementImpl delivered = this.deliverToReader(ref, reader);
            if (reader.acknowledge()) {
               this.autoAcknowledge(delivered, reader.getConsumerID());
            } else {
               this.moveToPendingList(ref);
            }
         }
      } finally {
         handle.unPin(this.kernel);
      }

   }

   public Message replaceMessage(MessageReference ref) {
      Message message = ref.getMessageHandle().getMessage();
      if (ref.getSequenceRef() != null) {
         Message tmp = ref.getSequenceRef().getSequence().replaceMessage(message);
         if (tmp != null) {
            return tmp;
         }
      }

      return null;
   }

   private MessageElementImpl deliverToReader(MessageReference mref, Reader reader) {
      assert Thread.holdsLock(this);

      assert mref.getMessageHandle().getPinCount() > 0;

      Message useMessage = this.replaceMessage(mref);
      if (useMessage == null) {
         useMessage = mref.getMessageHandle().getMessage();
      }

      MessageElementImpl element = new MessageElementImpl(mref, useMessage, true);
      element.setConsumerID(reader.getConsumerID());
      if (logger.isDebugEnabled()) {
         logger.debug("Message " + element + " delivered to reader " + reader.getConsumerID());
      }

      if (reader.deliver((MessageElement)element) > 0) {
         assert !this.readerList.contains(reader);

         this.readerList.add(reader);
      }

      this.updateLastMessagesReceivedTime();
      return element;
   }

   void updateLastMessagesReceivedTime() {
      this.lastMessagesReceivedTime.set(System.currentTimeMillis());
   }

   public long getLastMessagesReceivedTime() {
      return this.lastMessagesReceivedTime.get();
   }

   void scheduleMatch(MessageReference element) {
      synchronized(this) {
         this.matchList.add(element);
         if (this.running) {
            return;
         }

         this.running = true;
      }

      this.kernel.getWorkManager().schedule(this);
   }

   public synchronized void run() {
      MessageReference element;
      while((element = (MessageReference)this.matchList.remove()) != null) {
         try {
            this.makeMessageAvailable(element);
         } catch (KernelException var3) {
         }
      }

      this.running = false;
   }

   private GroupImpl createGroup(String name) {
      GroupImpl group = (GroupImpl)this.groups.get(name);
      if (group != null) {
         return group;
      } else {
         this.groups.put(name, group = new GroupImpl(name, this));
         if ((this.getLogMask() & 16) != 0) {
            this.addEvent(new GroupAddEventImpl((String)null, group));
         }

         return group;
      }
   }

   void deleteGroup(GroupImpl group) {
      if ((group = (GroupImpl)this.groups.remove(group.getName())) != null) {
         if ((this.getLogMask() & 16) != 0) {
            this.addEvent(new GroupRemoveEventImpl((String)null, group));
         }

      }
   }

   public synchronized Sequence createSequence(String name, int sequenceMode) throws KernelException {
      this.checkDeleted();
      this.checkActivation();
      Sequence seq = this.findSequence(name);
      if (seq != null) {
         throw new KernelException("The Sequence " + name + " already exists");
      } else {
         long id = this.kernel.getNextSequenceID();
         SequenceImpl newSeq = SequenceImpl.createSequence(name, sequenceMode, id, this);
         PersistedSequenceRecord seqRec = new PersistedSequenceRecord(newSeq);
         newSeq.setNumberRecord(seqRec);
         if (this.durable) {
            this.kernel.getPersistence().createSequence(newSeq);
            PersistentHandle numberHandle = this.kernel.getPersistence().createSequenceNumber(seqRec);
            newSeq.setNumberPersistentHandle(numberHandle);
         }

         this.addSequence(newSeq);
         return newSeq;
      }
   }

   public synchronized Sequence findOrCreateSequence(String name, int sequenceMode) throws KernelException {
      this.checkDeleted();
      this.checkActivation();
      Sequence seq = this.findSequence(name);
      return seq != null ? seq : this.createSequence(name, sequenceMode);
   }

   public synchronized Sequence findSequence(String name) {
      if (this.sequences != null) {
         Sequence ret = (Sequence)this.sequences.get(name);
         return ret;
      } else {
         return null;
      }
   }

   public SequenceImpl findSequenceUnsync(long id) {
      return this.sequencesByID != null ? (SequenceImpl)this.sequencesByID.get(id) : null;
   }

   public synchronized SequenceImpl findSequence(long id) {
      return this.findSequenceUnsync(id);
   }

   synchronized void addSequence(SequenceImpl seq) {
      if (this.sequences == null) {
         this.sequences = new HashMap();
      }

      this.sequences.put(seq.getName(), seq);
      if (this.sequencesByID == null) {
         this.sequencesByID = new HashMap();
      }

      this.sequencesByID.put(seq.getSerialNumber(), seq);
   }

   synchronized void sequenceDeleted(SequenceImpl seq) {
      if (this.sequences != null) {
         this.sequences.remove(seq.getName());
      }

      if (this.sequencesByID != null) {
         this.sequencesByID.remove(seq.getSerialNumber());
      }

   }

   public synchronized Collection getSequences() {
      if (this.sequences == null) {
         return new ArrayList();
      } else {
         ArrayList ret = new ArrayList(this.sequences.values());
         return Collections.unmodifiableCollection(ret);
      }
   }

   void decreaseDeletingSize(boolean isCommit) {
      --this.deletingSize;
      if (isCommit) {
         this.statistics.incrementSubscriptionLimitDeleted();
      }

      if (logger.isDebugEnabled()) {
         logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", deletingSize--: " + this.deletingSize);
      }

   }

   void adjustCleaup(long msgsLimit) {
      if (msgsLimit > 0L) {
         synchronized(this) {
            if (this.reservedStatus.get() != null) {
               if ((Integer)this.reservedStatus.get() == 1) {
                  --this.reservedSize;
                  if (logger.isDebugEnabled()) {
                     logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", sending opt failed in the middle, reservedSize--: " + this.reservedSize);
                  }
               }

               this.reservedStatus.remove();
            }

         }
      }
   }

   void adjust(long msgsLimit) throws KernelException {
      if (msgsLimit > 0L) {
         ArrayList deleteList = null;
         synchronized(this) {
            label113: {
               this.reservedStatus.set(1);
               ++this.reservedSize;
               if (logger.isDebugEnabled()) {
                  logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", reservedSize++: " + this.reservedSize);
               }

               long currentSize = (long)(this.messageList.size() + this.pendingMessageList.size() - this.deletingSize + this.reservedSize);
               if (logger.isDebugEnabled()) {
                  logger.debug("SubscriptionLimit: name=" + this.name + ", msgSize=" + this.messageList.size() + ", pendingSize=" + this.pendingMessageList.size() + ", deletingSize=" + this.deletingSize + ", reserveSize=" + this.reservedSize + ", currentSize=" + currentSize);
               }

               if (currentSize <= msgsLimit) {
                  logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", no need to remove messages.");
                  return;
               }

               long count = 0L;
               long total = Math.min(currentSize - msgsLimit, (long)MAX_DELETED_COUNT);
               if (logger.isDebugEnabled()) {
                  logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", need to remove " + total + " messages.");
               }

               Iterator msgIterator = this.messageList.iterator();
               Iterator pendingIterator = null;
               MessageReference msgRef = null;
               deleteList = new ArrayList();

               try {
                  while(true) {
                     if (count < total) {
                        msgRef = this.findNextRemovableByLimit(msgIterator);
                        if (msgRef == null) {
                           if (logger.isDebugEnabled()) {
                              logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", trying to find removable message from pending list.");
                           }

                           if (pendingIterator == null) {
                              pendingIterator = this.pendingMessageList.iterator();
                           }

                           msgRef = this.findNextRemovableByLimit(pendingIterator);
                        }

                        if (msgRef != null) {
                           deleteList.add(msgRef);
                           ++count;
                           continue;
                        }
                     }

                     if (count > 0L) {
                        this.markAsDeleted(deleteList);
                        this.deletingSize += deleteList.size();
                        if (logger.isDebugEnabled()) {
                           logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", deletingSize=" + this.deletingSize + ", adding " + count + " deleting messages.");
                        }
                        break label113;
                     }

                     if (logger.isDebugEnabled()) {
                        logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", no removable messages found.");
                     }
                     break;
                  }
               } catch (KernelException var19) {
                  if (logger.isDebugEnabled()) {
                     logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", error occured during delete oldest messages.", var19);
                  }

                  throw var19;
               }

               return;
            }
         }

         try {
            if (logger.isDebugEnabled()) {
               logger.debug("SubscriptionLimit: name=" + this.name + ", tid:" + Thread.currentThread().getId() + ", do the actual msg deletion, count=" + deleteList.size());
            }

            this.doDeleteWithTx(deleteList);
         } catch (KernelException var18) {
            synchronized(this) {
               this.deletingSize -= deleteList.size();
            }

            if (logger.isDebugEnabled()) {
               logger.debug("SubscriptionLimit: name=" + this.name + "error occured in doDeleteWithTx.", var18);
            }

            throw var18;
         }
      }
   }

   private void markAsDeleted(List refs) throws KernelException {
      Iterator iter = refs.iterator();

      while(iter.hasNext()) {
         MessageReference msgRef = (MessageReference)iter.next();
         msgRef.setState(536870912);
         if (this.messageList.contains(msgRef)) {
            this.moveToPendingList(msgRef);
         }
      }

   }

   private KernelRequest doDeleteWithTx(List refs) throws KernelException {
      boolean localTransaction = false;
      GXATransaction tran = this.kernel.getGXATransaction();
      if (tran == null) {
         tran = this.kernel.startLocalGXATransaction();
         localTransaction = true;
      }

      Iterator iter = refs.iterator();

      while(iter.hasNext()) {
         MessageReference msgRef = (MessageReference)iter.next();
         msgRef.setTransaction((GXATransaction)tran);
         this.enlistOperation((GXATransaction)tran, new ReceiveOperation(5, this, msgRef, (String)null, (RedeliveryParameters)null, this.kernel, localTransaction, true));
      }

      if (localTransaction) {
         CompletionRequest storeRequest = new CompletionRequest();
         KernelRequest kernelRequest = new KernelRequest();
         storeRequest.addListener(new GXATranCompletionListener(kernelRequest), this.kernel.getWorkManager());

         try {
            ((GXALocalTransaction)tran).commit(storeRequest, this.kernel.getWorkManager());
            return kernelRequest;
         } catch (Throwable var8) {
            ((GXALocalTransaction)tran).commitFailed();
            throw new KernelException("Local Transaction " + tran + " commit failure: " + var8, var8);
         }
      } else {
         return null;
      }
   }

   private MessageReference findNextRemovableByLimit(Iterator iterator) {
      while(true) {
         if (iterator.hasNext()) {
            MessageReference msgRef = (MessageReference)iterator.next();
            if (!msgRef.isRemovableByLimit()) {
               continue;
            }

            return msgRef;
         }

         return null;
      }
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Queue");
      super.dump(imageSource, xsw);
      xsw.writeStartElement("Messages");
      Cursor snapshot = null;

      try {
         snapshot = this.createCursor(true, (Expression)null, Integer.MAX_VALUE);
         xsw.writeAttribute("currentCount", String.valueOf(snapshot.size()));

         MessageElementImpl element;
         while((element = (MessageElementImpl)snapshot.next()) != null) {
            MessageReference reference = element.getMessageReference();
            reference.dump(imageSource, xsw);
         }
      } catch (KernelException var17) {
         var17.printStackTrace();
      } finally {
         if (snapshot != null) {
            snapshot.close();
         }

      }

      xsw.writeEndElement();
      xsw.writeStartElement("Readers");
      Object[] tmpReaders;
      synchronized(this) {
         tmpReaders = this.readerList.toArray();
      }

      xsw.writeAttribute("currentCount", String.valueOf(tmpReaders.length));

      for(int inc = 0; inc < tmpReaders.length; ++inc) {
         Reader reader = (Reader)tmpReaders[inc];
         xsw.writeStartElement("Reader");
         String subjectName = reader.getSubjectName();
         xsw.writeAttribute("principal", subjectName != null ? subjectName : "");
         String userBlob = reader.getConsumerID();
         xsw.writeAttribute("consumerID", userBlob != null ? userBlob : "");
         Object owner = reader.getOwner();
         if (owner != null) {
            xsw.writeAttribute("owner", owner.toString());
         }

         xsw.writeAttribute("count", String.valueOf(reader.getCount()));
         xsw.writeAttribute("acknowledge", String.valueOf(reader.acknowledge()));
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      ArrayList seqs = null;
      synchronized(this) {
         if (this.sequences != null && !this.sequences.isEmpty()) {
            seqs = new ArrayList(this.sequences.values());
         }
      }

      xsw.writeStartElement("Sequences");
      if (seqs != null) {
         Iterator i = seqs.iterator();

         while(i.hasNext()) {
            ((SequenceImpl)i.next()).dump(imageSource, xsw);
         }
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   static {
      try {
         String strValue = System.getProperty("weblogic.jms.topic.DurableSubscriptionMessagesLimit.MaxDeleteCount");
         if (strValue != null) {
            int intValue = Integer.parseInt(strValue);
            if (intValue > 0) {
               MAX_DELETED_COUNT = intValue;
            }
         }
      } catch (Exception var2) {
      }

   }

   public class RunnableTrain implements Runnable {
      private boolean scheduled;
      private final CircularQueue train = new CircularQueue(16);
      private final int maxBoxCar;

      RunnableTrain(int _maxBoxCar) {
         this.maxBoxCar = _maxBoxCar;
      }

      void add(Runnable runnable) {
         synchronized(this) {
            this.train.add(runnable);
            if (this.scheduled) {
               return;
            }

            this.scheduled = true;
         }

         QueueImpl.this.kernel.getLimitedWorkManager().schedule(this);
      }

      public void run() {
         Runnable runnable;
         for(int lboxcar = 0; lboxcar++ <= this.maxBoxCar; runnable.run()) {
            synchronized(this) {
               runnable = (Runnable)this.train.remove();
               if (runnable == null) {
                  this.scheduled = false;
                  return;
               }
            }
         }

         QueueImpl.this.kernel.getLimitedWorkManager().schedule(this);
      }
   }

   static final class SequenceNumComparator2 implements Comparator {
      private int compareLongs(long l1, long l2) {
         if (l1 < l2) {
            return -1;
         } else {
            return l1 > l2 ? 1 : 0;
         }
      }

      public int compare(MessageElementImpl o1, MessageElementImpl o2) {
         return this.compareLongs(o1.getMessageReference().getMessageHandle().getID(), o2.getMessageReference().getMessageHandle().getID());
      }

      public boolean equals(Object o) {
         return o instanceof SequenceNumComparator2;
      }

      public int hashCode() {
         return 0;
      }
   }

   static final class SequenceNumComparator implements Comparator {
      private int compareLongs(long l1, long l2) {
         if (l1 < l2) {
            return -1;
         } else {
            return l1 > l2 ? 1 : 0;
         }
      }

      public int compare(Object o1, Object o2) {
         MessageReference m1 = (MessageReference)o1;
         MessageReference m2 = (MessageReference)o2;
         if (m1.getSequenceRef() != null && m2.getSequenceRef() != null && m1.getSequenceRef().getSequence() == m2.getSequenceRef().getSequence()) {
            long seqNum1 = m1.getSequenceRef().getSequenceNum();
            long seqNum2 = m2.getSequenceRef().getSequenceNum();
            if (seqNum1 == 0L) {
               seqNum1 = Long.MAX_VALUE;
            }

            if (seqNum2 == 0L) {
               seqNum2 = Long.MAX_VALUE;
            }

            int result = this.compareLongs(seqNum1, seqNum2);
            if (result != 0) {
               return result;
            }
         }

         return this.compareLongs(m1.getSequenceNumber(), m2.getSequenceNumber());
      }

      public boolean equals(Object o) {
         return o instanceof SequenceNumComparator;
      }

      public int hashCode() {
         return 0;
      }
   }

   private class ExpirationListener implements NakedTimerListener, Runnable {
      private MessageReference element;

      ExpirationListener(MessageReference element) {
         this.element = element;
      }

      public void timerExpired(Timer timer) {
         QueueImpl.this.runnableTrain.add(this);
      }

      public void run() {
         QueueImpl.this.expireHandler(this.element);
      }
   }

   private class DeliveryListener implements NakedTimerListener {
      private MessageReference ref;

      DeliveryListener(MessageReference element) {
         this.ref = element;
      }

      public void timerExpired(Timer timer) {
         if (DestinationImpl.logger.isDebugEnabled()) {
            DestinationImpl.logger.debug("Message " + this.ref + " reached delivery time");
         }

         assert (this.ref.getState() & 16) != 0;

         if (this.ref.getDeliveryTimer() != null) {
            this.ref.getDeliveryTimer().cancel();
            this.ref.setDeliveryTimer((Timer)null);
         }

         synchronized(QueueImpl.this) {
            try {
               this.ref.clearState(16);
               if (!this.ref.isExpired()) {
                  QueueImpl.this.moveToActiveList(this.ref);
               }

               SequenceImpl sequence;
               CompletionRequest storeReq;
               label186: {
                  SequenceReference seqRef = this.ref.getSequenceRef();
                  if (seqRef != null) {
                     sequence = seqRef.getSequence();
                     if (seqRef.getSequenceNum() == 0L && sequence.requiresUpdate()) {
                        PersistentStoreTransaction tran = null;
                        storeReq = null;
                        seqRef.getSequence().lock(this);

                        try {
                           if (QueueImpl.this.isDurable()) {
                              tran = QueueImpl.this.kernel.getPersistence().startStoreTransaction();
                           }

                           QueueImpl.this.updateSequence(tran, this.ref, true, 0);
                           if (tran != null) {
                              storeReq = new CompletionRequest();
                              tran.commit(storeReq);
                           }
                           break label186;
                        } finally {
                           seqRef.getSequence().unlock(this);
                        }
                     }
                  }

                  QueueImpl.this.makeMessageAvailable(this.ref);
                  return;
               }

               if (storeReq != null) {
                  try {
                     storeReq.getResult();
                  } catch (PersistentStoreException var16) {
                     throw var16;
                  } catch (Throwable var17) {
                     throw new RuntimeException(var17);
                  }
               }

               List assignedList = sequence.getAssignedMessages(17);
               if (assignedList != null) {
                  Iterator i = assignedList.iterator();

                  while(i.hasNext()) {
                     QueueImpl.this.makeMessageAvailable((MessageReference)i.next());
                  }
               }
            } catch (KernelException var19) {
               MessagingLogger.logDelayedAvailable(var19.toString(), var19);
               return;
            } catch (PersistentStoreException var20) {
               MessagingLogger.logDelayedAvailable(var20.toString(), var20);
               return;
            }

         }
      }
   }

   private final class DestroyCompletionListener implements Runnable {
      private DestroyCompletionListener() {
      }

      public void run() {
         if (QueueImpl.this.durable && QueueImpl.this.kernel.isOpened()) {
            try {
               QueueImpl.this.kernel.getPersistence().deleteDestination(QueueImpl.this);
            } catch (KernelException var2) {
            }
         }

         QueueImpl.this.kernel.queueDeleted(QueueImpl.this, true);
      }

      // $FF: synthetic method
      DestroyCompletionListener(Object x1) {
         this();
      }
   }

   private static final class GXATranCompletionListener implements CompletionListener {
      private KernelRequest request;

      GXATranCompletionListener(KernelRequest request) {
         this.request = request;
      }

      public void onCompletion(CompletionRequest cr, Object result) {
         try {
            this.request.setResult((Object)null, false);
         } catch (RuntimeException var4) {
            this.request.setResult(var4, false);
         }

      }

      public void onException(CompletionRequest cr, Throwable reason) {
         if (reason instanceof PersistentStoreException) {
            this.request.setResult(new KernelException("I/O error in acknowledge", reason), false);
         } else {
            this.request.setResult(reason, false);
         }

      }
   }
}
