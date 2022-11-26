package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.common.CompletionRequest;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.messaging.kernel.internal.persistence.KernelObjectHandler;
import weblogic.messaging.kernel.internal.persistence.LastFailureRecord;
import weblogic.messaging.kernel.internal.persistence.PersistedBody;
import weblogic.messaging.kernel.internal.persistence.PersistedSequenceRecord;
import weblogic.messaging.kernel.internal.persistence.PersistedShutdownRecord;
import weblogic.messaging.kernel.internal.persistence.PersistedXARecord;
import weblogic.messaging.kernel.util.Util;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.TestStoreException;
import weblogic.store.gxa.GXid;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public final class PersistenceImpl {
   protected static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   protected static final DebugLogger bootLogger = DebugLogger.getDebugLogger("DebugMessagingKernelBoot");
   private static final boolean BOOT_ON_ERROR;
   private static final boolean upgradeOldConnectionsOnly;
   private static final boolean upgradeIgnoreOldConnections;
   private static final boolean forceOldConnections;
   private static final boolean DELETE_BAD_MSGHEADERS;
   private static final boolean DELETE_BAD_2PCRECORDS;
   private static final boolean DELETE_BAD_INSTRTEST;
   private static final SequenceNumberComparator SEQUENCE_NUMBER_COMPARATOR;
   private static final String CONN_PREFIX = "weblogic.messaging.";
   private static final String CONFIG_CONN_SUFFIX = ".config";
   private static final String BODY_CONN_SUFFIX = ".body";
   private static final String HEADER_CONN_SUFFIX = ".header";
   private static final String XA_CONN_SUFFIX = ".xa";
   private static final long DEFAULT_FILE_IN_LINE_THRESHOLD = 8000L;
   private static final long DEFAULT_JDBC_IN_LINE_THRESHOLD = Long.MAX_VALUE;
   private static final long DELETE_MESSAGES_DELAY = 5000L;
   private final String kernelName;
   protected KernelImpl kernel;
   private final PersistentStoreXA store;
   private final ObjectHandler objectHandler;
   private final long inLineBodyThreshold;
   private final boolean pageInOnBoot;
   private PersistentStoreConnection configConnection;
   private PersistentStoreConnection headerConnection;
   private PersistentStoreConnection bodyConnection;
   private PersistentStoreConnection xaConnection;
   private boolean cleanShutdown;
   private PersistentHandle shutdownRecordHandle;
   private long nextHandleIDAfterCrash = 0L;
   private PersistentHandle lastFailureHandle;

   PersistenceImpl(KernelImpl kernel, PersistentStoreXA store, ObjectHandler userObjectHandler) {
      this.kernelName = kernel.getName();
      this.kernel = kernel;
      this.store = store;
      this.objectHandler = this.setUpObjectHandler(userObjectHandler, kernel);
      if (store.supportsFastReads()) {
         this.inLineBodyThreshold = kernel.getLongProperty("weblogic.messaging.kernel.persistence.InLineBodyThreshold", 8000L);
         this.pageInOnBoot = kernel.getBooleanProperty("weblogic.messaging.kernel.persistence.PageInOnBoot", false);
      } else {
         this.inLineBodyThreshold = kernel.getLongProperty("weblogic.messaging.kernel.persistence.InLineBodyThreshold", Long.MAX_VALUE);
         this.pageInOnBoot = kernel.getBooleanProperty("weblogic.messaging.kernel.persistence.PageInOnBoot", true);
      }

   }

   public PersistenceImpl(String kernelName, PersistentStoreXA store, ObjectHandler userObjectHandler) {
      this.kernelName = kernelName;
      this.store = store;
      this.objectHandler = new KernelObjectHandler(userObjectHandler, (KernelImpl)null);
      this.inLineBodyThreshold = Long.MAX_VALUE;
      this.pageInOnBoot = false;
   }

   private ObjectHandler setUpObjectHandler(ObjectHandler userObjectHandler, KernelImpl kernel) {
      KernelObjectHandler kernelObjectHandler = new KernelObjectHandler(userObjectHandler, kernel);
      return (ObjectHandler)(userObjectHandler instanceof TestStoreException ? new TestStoreExceptionObjectHandler(kernelObjectHandler) : kernelObjectHandler);
   }

   boolean alwaysUsePagingStore() {
      return !this.store.supportsFastReads();
   }

   public PersistentStoreTransaction startStoreTransaction() {
      return this.store.begin();
   }

   public void open() throws KernelException {
      if (this.kernel.isStoreSeverelyOverloaded()) {
         throw new KernelException("Cannot open persistent store  --  store '" + this.store.getName() + "' is overloaded already");
      } else {
         try {
            String effectiveName = this.kernelName;
            String alternativeKernelName = this.kernel.getAlternativeKernelName();
            logger.debug("Opening persistent store connections");
            if (logger.isDebugEnabled() && alternativeKernelName != null) {
               logger.debug("Opening persistent store connections: kernelName = " + this.kernelName + " alternativeKernelName = " + alternativeKernelName);
            }

            if (alternativeKernelName != null && this.store.getConnection("weblogic.messaging." + alternativeKernelName + ".config") != null) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Opening persistent store connections: found pre-12.2.1.0.0 store connections");
               }

               if (this.store.getConnection("weblogic.messaging." + this.kernelName + ".config") == null) {
                  if (logger.isDebugEnabled()) {
                     logger.debug("Opening persistent store connections: did not find post-12.2.1.0.0 store connections");
                  }

                  effectiveName = alternativeKernelName;
                  this.kernel.setUseAlternativeName(true);
               } else {
                  if (logger.isDebugEnabled()) {
                     logger.debug("Opening persistent store connections: found post-1221 store connections");
                  }

                  if (!upgradeIgnoreOldConnections && this.hasMessages(alternativeKernelName)) {
                     if (upgradeOldConnectionsOnly) {
                        effectiveName = alternativeKernelName;
                        this.kernel.setUseAlternativeName(true);
                        if (logger.isDebugEnabled()) {
                           logger.debug("Opening persistent store connections: forced to use the old store connections");
                        }
                     } else {
                        Util.logSAFUpgradeWarning(this.kernelName);
                     }
                  }
               }

               if (logger.isDebugEnabled()) {
                  logger.debug("Opening persistent store connections:  effectiveName = " + effectiveName);
               }
            }

            if (alternativeKernelName != null && forceOldConnections) {
               effectiveName = alternativeKernelName;
               this.kernel.setUseAlternativeName(true);
               if (logger.isDebugEnabled()) {
                  logger.debug("Opening persistent store connections:  forced to use the alternativeName  = " + alternativeKernelName);
               }
            }

            this.configConnection = this.store.createConnection("weblogic.messaging." + effectiveName + ".config", this.objectHandler);
            this.headerConnection = this.store.createConnection("weblogic.messaging." + effectiveName + ".header", this.objectHandler);
            this.bodyConnection = this.store.createConnection("weblogic.messaging." + effectiveName + ".body", this.objectHandler);
            this.xaConnection = this.store.createConnection("weblogic.messaging." + effectiveName + ".xa", this.objectHandler);
         } catch (PersistentStoreException var3) {
            throw new KernelException("Cannot open persistent store", var3);
         }
      }
   }

   private boolean hasMessages(String alternativeKernelName) throws KernelException {
      PersistentStoreConnection configCon = null;
      PersistentStoreConnection headerCon = null;
      PersistentStoreConnection bodyCon = null;

      try {
         configCon = this.store.createConnection("weblogic.messaging." + alternativeKernelName + ".config", this.objectHandler);
         headerCon = this.store.createConnection("weblogic.messaging." + alternativeKernelName + ".header", this.objectHandler);
         bodyCon = this.store.createConnection("weblogic.messaging." + alternativeKernelName + ".body", this.objectHandler);
         boolean foundMessage;
         if (configCon.getStatistics().getObjectCount() == 0L || headerCon.getStatistics().getObjectCount() == 0L) {
            foundMessage = false;
            return foundMessage;
         } else if (bodyCon.getStatistics().getObjectCount() != 0L) {
            foundMessage = true;
            return foundMessage;
         } else {
            foundMessage = false;
            PersistentStoreConnection.Cursor cursor = headerCon.createCursor(0);
            int sequenceCount = false;

            PersistentStoreRecord rec;
            while((rec = cursor.next()) != null) {
               this.kernel.getPaging().waitForSpace();
               Object obj = rec.getData();
               if (obj instanceof QueueMessageReference) {
                  foundMessage = true;
               }
            }

            if (logger.isDebugEnabled()) {
               logger.debug("foundMessage = " + foundMessage);
            }

            boolean var15 = foundMessage;
            return var15;
         }
      } catch (PersistentStoreException var13) {
         throw new KernelException("Unexpected store exception in messaging kernel recovery", var13);
      } finally {
         if (configCon != null) {
            configCon.close();
         }

         if (headerCon != null) {
            headerCon.close();
         }

         if (bodyCon != null) {
            bodyCon.close();
         }

      }
   }

   public void close() {
      this.createShutdownRecord();
      logger.debug("Closing persistent store connections");
      this.configConnection.close();
      this.headerConnection.close();
      this.bodyConnection.close();
      this.xaConnection.close();
   }

   public void createDestination(DestinationImpl dest) throws KernelException {
      PersistentStoreTransaction tran = this.store.begin();
      PersistentHandle handle = this.configConnection.create(tran, dest, 0);

      try {
         tran.commit();
      } catch (PersistentStoreException var5) {
         throw new KernelException("Error creating queue record", var5);
      }

      dest.setPersistentHandle(handle);
      if (logger.isDebugEnabled()) {
         logger.debug("Persisted destination " + dest.getName() + " using serial number " + dest.getSerialNumber());
      }

   }

   public void updateDestination(DestinationImpl dest) throws KernelException {
      if (dest.getPersistentHandle() != null) {
         PersistentStoreTransaction tran = this.store.begin();
         this.configConnection.update(tran, dest.getPersistentHandle(), dest, 0);

         try {
            tran.commit();
         } catch (PersistentStoreException var4) {
            throw new KernelException("Error updating queue record", var4);
         }

         if (logger.isDebugEnabled()) {
            logger.debug("Updated state of destination " + dest);
         }

      }
   }

   void deleteDestination(DestinationImpl dest) throws KernelException {
      if (dest.getPersistentHandle() != null) {
         PersistentStoreTransaction tran = this.store.begin();
         this.configConnection.delete(tran, dest.getPersistentHandle(), 0);

         try {
            tran.commit();
         } catch (PersistentStoreException var4) {
            throw new KernelException("Error creating queue record", var4);
         }

         dest.setPersistentHandle((PersistentHandle)null);
         if (logger.isDebugEnabled()) {
            logger.debug("Deleted persistent record for destination " + dest.getName());
         }

      }
   }

   public void createSequence(SequenceImpl seq) throws KernelException {
      PersistentStoreTransaction tran = this.store.begin();
      PersistentHandle handle = this.configConnection.create(tran, seq, 0);

      try {
         tran.commit();
      } catch (PersistentStoreException var5) {
         throw new KernelException("Error creating sequence record", var5);
      }

      seq.setPersistentHandle(handle);
      if (logger.isDebugEnabled()) {
         logger.debug("Persisted new sequence " + seq.getName() + " using serial number " + seq.getSerialNumber());
      }

   }

   public void updateSequence(SequenceImpl seq) throws KernelException {
      if (seq.getPersistentHandle() != null) {
         PersistentStoreTransaction tran = this.store.begin();
         this.configConnection.update(tran, seq.getPersistentHandle(), seq, 0);

         try {
            tran.commit();
         } catch (PersistentStoreException var4) {
            throw new KernelException("Error updating sequence record", var4);
         }

         if (logger.isDebugEnabled()) {
            logger.debug("Updated state of sequence " + seq);
         }

      }
   }

   void deleteSequence(SequenceImpl seq) throws KernelException {
      if (seq.getPersistentHandle() != null) {
         PersistentStoreTransaction tran = this.store.begin();
         this.configConnection.delete(tran, seq.getPersistentHandle(), 0);

         try {
            tran.commit();
         } catch (PersistentStoreException var4) {
            throw new KernelException("Error creating sequence record", var4);
         }

         seq.setPersistentHandle((PersistentHandle)null);
         if (logger.isDebugEnabled()) {
            logger.debug("Deleted persistent record for sequence " + seq.getName());
         }

      }
   }

   public PersistentHandle createSequenceNumber(PersistedSequenceRecord seqRec) throws KernelException {
      PersistentStoreTransaction tran = this.store.begin();
      PersistentHandle handle = this.headerConnection.create(tran, seqRec, 0);

      try {
         tran.commit();
      } catch (PersistentStoreException var5) {
         throw new KernelException("Error creating sequence record", var5);
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Created sequence number record for " + seqRec.getSequence());
      }

      return handle;
   }

   public void updateSequenceNumber(PersistentStoreTransaction tran, PersistentHandle handle, PersistedSequenceRecord seqRec) {
      this.headerConnection.update(tran, handle, seqRec, 0);
      if (logger.isDebugEnabled()) {
         logger.debug("Persisted new value of " + seqRec.getNewValue() + " for sequence " + seqRec.getSequence());
      }

   }

   void deleteSequenceNumber(PersistentHandle handle) throws KernelException {
      PersistentStoreTransaction tran = this.store.begin();
      this.headerConnection.delete(tran, handle, 0);

      try {
         tran.commit();
      } catch (PersistentStoreException var4) {
         throw new KernelException("Error deleting sequence record", var4);
      }
   }

   public void createMessageBody(PersistentStoreTransaction tran, MessageHandle handle) throws KernelException {
      this.createMessageBody(tran, handle, -1L, -1L);
   }

   private void createMessageBody(PersistentStoreTransaction tran, MessageHandle handle, long flushGroup, long liveSequence) throws KernelException {
      PersistedBody pBody = new PersistedBody(handle);
      if (pBody.getMessage() == null) {
         String info = "[pBodyId=" + pBody.getHandleID() + ", tran=" + tran + ", flushGroup=" + flushGroup + ", liveSequence=" + liveSequence + ", MessageHandle=" + handle + "]";
         MessagingLogger.logErrorWritingToStore(this.store.getName(), info);
         KernelException ke = new KernelException("Attempted to create a null message body " + handle);
         throw ke;
      } else {
         PersistentHandle bodyHandle = this.bodyConnection.create(tran, pBody, 0, flushGroup, liveSequence);
         handle.setPersistentHandle(bodyHandle);
         if (logger.isDebugEnabled()) {
            logger.debug("Persisted message body " + handle);
         }

      }
   }

   void createQueueMessage(PersistentStoreTransaction tran, QueueMessageReference ref) throws KernelException {
      MessageHandle handle = ref.getMessageHandle();

      assert handle.getPersistentRefCount() == 0;

      handle.adjustPersistentRefCount(1);
      handle.pin(this.kernel);

      try {
         if (handle.size() <= this.inLineBodyThreshold) {
            handle.setPersistBody(true);
            if (handle.getMessage() == null) {
               String info = "[MessageReference=" + ref + ", queue=" + ref.getQueue().getName() + ", tran=" + tran + ", MessageHandle=" + handle + "]";
               MessagingLogger.logErrorWritingToStore(this.store.getName(), info);
               KernelException ke = new KernelException("Attempted to create a message with a null message body " + handle + " to destination " + ref.getQueue().getName());
               throw ke;
            }
         } else {
            this.createMessageBody(tran, handle);
         }

         this.createQueueMessageReference(tran, ref);
      } finally {
         handle.unPin(this.kernel);
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Persisted message element " + ref.getSequenceNumber() + " to queue " + ref.getQueue().getName());
      }

   }

   public void createQueueMessageReference(PersistentStoreTransaction tran, QueueMessageReference ref) {
      PersistentHandle pHandle = this.headerConnection.create(tran, ref, 0);
      ref.setPersistentHandle(pHandle);
   }

   void createMultiMessage(PersistentStoreTransaction tran, MessageHandle handle, MultiPersistenceHandle persHandle) throws KernelException {
      handle.pin(this.kernel);

      try {
         synchronized(handle) {
            if (handle.getPersistentRefCount() == 0) {
               this.createMessageBody(tran, handle, persHandle.getFlushGroup(), persHandle.getLiveSequence());
            }

            handle.adjustPersistentRefCount(persHandle.size());
            this.createMultiMessageReference(tran, persHandle);
            if (logger.isDebugEnabled()) {
               logger.debug("Persisted a multi-persistence record for " + handle);
            }
         }
      } finally {
         handle.unPin(this.kernel);
      }

   }

   public void createMultiMessageReference(PersistentStoreTransaction tran, MultiPersistenceHandle persHandle) {
      PersistentHandle pHandle = this.headerConnection.create(tran, persHandle, 0, persHandle.getFlushGroup(), persHandle.getLiveSequence());
      persHandle.setPersistentHandle(pHandle);
   }

   void deleteMessageBody(PersistentStoreTransaction tran, MessageHandle handle) {
      this.deleteMessageBody(tran, handle, -1L, -1L);
   }

   private void deleteMessageBody(PersistentStoreTransaction tran, MessageHandle handle, long flushGroup, long liveSequence) {
      this.bodyConnection.delete(tran, handle.getPersistentHandle(), 0, flushGroup, liveSequence);
      handle.setPersistentHandle((PersistentHandle)null);
      if (logger.isDebugEnabled()) {
         logger.debug("Deleted persistent message body " + handle);
      }

   }

   void deleteMessage(PersistentStoreTransaction tran, MessageReference ref) {
      this.deleteMessageInternal(tran, ref, false);
   }

   void deleteMessageForRollback(PersistentStoreTransaction tran, MessageReference ref) {
      this.deleteMessageInternal(tran, ref, true);
   }

   private void deleteMessageInternal(PersistentStoreTransaction tran, MessageReference ref, boolean forRollback) {
      MessageHandle handle = ref.getMessageHandle();
      if (ref instanceof QueueMessageReference) {
         if (handle.getPersistentRefCount() == 0) {
            return;
         }

         QueueMessageReference qRef = (QueueMessageReference)ref;

         assert handle.getPersistentRefCount() == 1;

         handle.adjustPersistentRefCount(-1);
         if (!handle.isPersistBody()) {
            this.deleteMessageBody(tran, handle);
         }

         this.headerConnection.delete(tran, qRef.getPersistentHandle(), 0);
         qRef.setPersistentHandle((PersistentHandle)null);
      } else {
         if (!(ref instanceof MultiMessageReference)) {
            throw new AssertionError("Invalid MessageReference subclass");
         }

         MultiMessageReference mRef = (MultiMessageReference)ref;
         MultiPersistenceHandle persHandle = mRef.getPersistenceHandle();
         boolean removedRef = false;
         synchronized(handle) {
            synchronized(persHandle) {
               removedRef = persHandle.removeMessageReference(mRef);
               if (persHandle.size() > 0) {
                  if (!forRollback) {
                     this.headerConnection.update(tran, persHandle.getPersistentHandle(), persHandle, 0, persHandle.getFlushGroup(), persHandle.getLiveSequence());
                     if (logger.isDebugEnabled()) {
                        logger.debug("Updated existing multi handle for " + handle + " New size = " + persHandle.size());
                     }
                  }
               } else if (removedRef) {
                  if (persHandle.getPersistentHandle() != null) {
                     this.headerConnection.delete(tran, persHandle.getPersistentHandle(), 0, persHandle.getFlushGroup(), persHandle.getLiveSequence());
                     persHandle.setPersistentHandle((PersistentHandle)null);
                     if (logger.isDebugEnabled()) {
                        logger.debug("Deleted a multi persistence handle for " + handle);
                     }
                  }
               } else {
                  assert false : "Deleted a multi reference twice";
               }
            }

            if (removedRef && handle.adjustPersistentRefCount(-1) == 0) {
               this.deleteMessageBody(tran, handle, persHandle.getFlushGroup(), persHandle.getLiveSequence());
            }
         }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Deleted persistent message reference " + ref);
      }

   }

   void deleteMessages(List list, KernelRequest request, Runnable cleanUpWork) {
      MessageDeleteJob job = new MessageDeleteJob(list, request, cleanUpWork);
      this.kernel.getLimitedTimerManager().schedule(job, 5000L);
   }

   void updateMessage(PersistentStoreTransaction tran, MessageReference ref) {
      if (ref instanceof QueueMessageReference) {
         QueueMessageReference qRef = (QueueMessageReference)ref;
         this.headerConnection.update(tran, qRef.getPersistentHandle(), qRef, 0);
      } else {
         if (!(ref instanceof MultiMessageReference)) {
            throw new AssertionError("Invalid MessageReference subclass");
         }

         MultiMessageReference mRef = (MultiMessageReference)ref;
         MultiPersistenceHandle persHandle = mRef.getPersistenceHandle();
         MessageHandle msgHandle = ref.getMessageHandle();
         synchronized(msgHandle) {
            synchronized(persHandle) {
               this.headerConnection.update(tran, persHandle.getPersistentHandle(), persHandle, 0);
            }
         }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Updated persistent message reference " + ref);
      }

   }

   void create2PCRecord(PersistentStoreTransaction tran, AbstractOperation operation) {
      PersistedXARecord pRecord = new PersistedXARecord(operation);
      PersistentHandle handle = this.xaConnection.create(tran, pRecord, 0);
      operation.setPersistentHandle(handle);
      if (logger.isDebugEnabled()) {
         logger.debug("Persisted a 2PC record of type " + this.typeToString(operation.getType()) + " for XID " + operation.getGXid());
      }

   }

   public void create2PCRecord(PersistentStoreTransaction tran, int type, GXid xid, QueueImpl queue, MessageReference element, String subjectName) {
      PersistedXARecord xaRec = new PersistedXARecord(type, xid, queue, element, subjectName);
      this.xaConnection.create(tran, xaRec, 0);
      if (logger.isDebugEnabled()) {
         logger.debug("Persisted a 2PC record of type " + this.typeToString(type) + " for XID " + xid);
      }

   }

   void delete2PCRecord(PersistentStoreTransaction tran, AbstractOperation operation) {
      this.xaConnection.delete(tran, operation.getPersistentHandle(), 0);
      if (logger.isDebugEnabled()) {
         logger.debug("Deleted the 2PC record for XID " + operation.getGXid());
      }

   }

   void readMessageBody(MessageHandle handle) throws KernelException {
      CompletionRequest completion = new CompletionRequest();
      PersistentStoreTransaction tran = this.store.begin();
      this.bodyConnection.read(tran, handle.getPersistentHandle(), completion);

      try {
         PersistentStoreRecord rec = (PersistentStoreRecord)completion.getResult();
         handle.setMessage(((PersistedBody)rec.getData()).getMessage());
         completion.reset();
         tran.commit(completion);
         completion.getResult();
      } catch (PersistentStoreException var5) {
         throw new KernelException("Error reading message body", var5);
      } catch (Throwable var6) {
         throw new RuntimeException(var6);
      }
   }

   void recover() throws KernelException {
      MessagingLogger.logStartingKernelRecovery(this.kernelName);
      Map handleMap = new HashMap();
      Map elementMap = new HashMap();
      Set deletedQueues = new HashSet();
      if (logger.isDebugEnabled()) {
         logger.debug("Starting recovery for messaging kernel " + this.kernelName);
      }

      try {
         this.recoverDestinations(deletedQueues);
         this.recoverHeaders(handleMap, elementMap);
         if (this.pageInOnBoot) {
            this.recoverBodies(handleMap);
         }

         this.recover2PCRecords(elementMap);
         this.cleanupDeletedQueues(deletedQueues);
         this.updateShutdownRecord();
         MessagingLogger.logCompletedKernelRecovery(this.kernelName, elementMap.size());
      } catch (PersistentStoreException var5) {
         throw new KernelException("Unexpected store exception in messaging kernel recovery", var5);
      }
   }

   private void recoverDestinations(Set deletedQueues) throws KernelException, PersistentStoreException {
      PersistentStoreConnection.Cursor cursor = this.configConnection.createCursor(0);
      Map defaultProps = new HashMap(1);
      defaultProps.put("Durable", Boolean.TRUE);
      Set existingQueues = new HashSet(this.kernel.getQueues());
      Set existingTopics = new HashSet(this.kernel.getTopics());
      Set sequences = new HashSet();
      Set alternativeNames = new HashSet();
      if (this.kernel.useAlternativeName()) {
         Iterator itr = existingQueues.iterator();

         while(itr.hasNext()) {
            DestinationImpl dest = (DestinationImpl)itr.next();
            if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("Have destination: name = " + dest.getName() + " alternativeName = " + dest.getAlternativeName() + " serialNumber = " + dest.getSerialNumber());
            }

            if (dest.getAlternativeName() != null) {
               alternativeNames.add(dest.getAlternativeName());
            }
         }
      }

      long hiSerialNum = 0L;
      LastFailureRecord lastFailureRecord = null;

      while(true) {
         PersistentStoreRecord rec;
         Object dest;
         while(true) {
            TopicImpl topic;
            QueueImpl queue;
            if ((rec = cursor.next()) == null) {
               Iterator i = existingQueues.iterator();

               while(i.hasNext()) {
                  queue = (QueueImpl)i.next();
                  this.kernel.setQueueSerialNumber(queue.getName(), ++hiSerialNum);
                  this.createDestination(queue);
               }

               i = existingTopics.iterator();

               while(i.hasNext()) {
                  topic = (TopicImpl)i.next();
                  this.kernel.setTopicSerialNumber(topic.getName(), ++hiSerialNum);
                  this.createDestination(topic);
               }

               this.kernel.setLastSerialNum(hiSerialNum);
               long hiSeqSeqNum = 0L;
               i = sequences.iterator();

               while(i.hasNext()) {
                  SequenceImpl seq = (SequenceImpl)i.next();
                  if (seq.getSerialNumber() > hiSeqSeqNum) {
                     hiSeqSeqNum = seq.getSerialNumber();
                  }

                  seq.restoreDestination(this.kernel);
                  if (seq.getQueue() != null) {
                     seq.getQueue().addSequence(seq);
                  } else if (bootLogger.isDebugEnabled()) {
                     bootLogger.debug("Sequence " + seq.getName() + " does not match an existing destination");
                  }
               }

               this.kernel.setLastSequenceID(hiSeqSeqNum);
               if (!this.cleanShutdown) {
                  this.nextHandleIDAfterCrash = Long.MAX_VALUE;
               } else if (lastFailureRecord != null) {
                  this.nextHandleIDAfterCrash = lastFailureRecord.getID();
               }

               return;
            }

            if (rec.getData() instanceof QueueImpl) {
               queue = (QueueImpl)rec.getData();
               QueueImpl oldQueue;
               if (!queue.isDeleted() && existingQueues.contains(queue)) {
                  if (bootLogger.isDebugEnabled()) {
                     bootLogger.debug("Found persistent record for existing queue " + queue.getName() + " serial number = " + queue.getSerialNumber());
                  }

                  oldQueue = (QueueImpl)this.kernel.findQueue(queue.getName());
                  this.kernel.setQueueSerialNumber(queue.getName(), queue.getSerialNumber());
                  existingQueues.remove(queue);
                  dest = oldQueue;
                  break;
               }

               if (!queue.isDeleted() && alternativeNames.contains(queue.getName())) {
                  if (bootLogger.isDebugEnabled()) {
                     bootLogger.debug("Found persistent record for existing queue  using alternative name " + queue.getName() + " serial number = " + queue.getSerialNumber());
                  }

                  oldQueue = (QueueImpl)this.kernel.findQueueByAlternativeName(queue.getName());
                  this.kernel.setQueueSerialNumber(oldQueue.getName(), queue.getSerialNumber());
                  existingQueues.remove(queue);
                  dest = oldQueue;
                  break;
               }

               if (queue.isDeleted()) {
                  deletedQueues.add(queue);
               }

               this.kernel.addQueue(queue);
               queue.initialize(defaultProps, this.kernel);
               dest = queue;
               break;
            }

            if (rec.getData() instanceof TopicImpl) {
               topic = (TopicImpl)rec.getData();
               if (!topic.isDeleted() && existingTopics.contains(topic)) {
                  if (bootLogger.isDebugEnabled()) {
                     bootLogger.debug("Found persistent record for existing topic " + topic.getName());
                  }

                  TopicImpl oldTopic = (TopicImpl)this.kernel.findTopic(topic.getName());
                  this.kernel.setTopicSerialNumber(topic.getName(), topic.getSerialNumber());
                  existingTopics.remove(topic);
                  dest = oldTopic;
                  break;
               }

               topic.initialize(defaultProps, this.kernel);
               this.kernel.addTopic(topic);
               dest = topic;
               break;
            }

            if (rec.getData() instanceof SequenceImpl) {
               SequenceImpl seq = (SequenceImpl)rec.getData();
               if (bootLogger.isDebugEnabled()) {
                  bootLogger.debug("Found persisted sequence record " + seq.getName());
               }

               seq.setPersistentHandle(rec.getHandle());
               sequences.add(seq);
            } else if (rec.getData() instanceof PersistedShutdownRecord) {
               assert !this.cleanShutdown : "Duplicate clean shutdown records found";

               this.cleanShutdown = true;
               this.shutdownRecordHandle = rec.getHandle();
               if (bootLogger.isDebugEnabled()) {
                  bootLogger.debug("Found clean shutdown record");
               }
            } else if (rec.getData() instanceof LastFailureRecord) {
               assert lastFailureRecord == null : "Duplicate last failure records found";

               this.lastFailureHandle = rec.getHandle();
               lastFailureRecord = (LastFailureRecord)rec.getData();
               if (bootLogger.isDebugEnabled()) {
                  bootLogger.debug("Found last failure record");
               }
            } else if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("Recovered a bad configuration record: " + rec.getData());
            }
         }

         ((DestinationImpl)dest).setPersistentHandle(rec.getHandle());
         if (((DestinationImpl)dest).getSerialNumber() > hiSerialNum) {
            hiSerialNum = ((DestinationImpl)dest).getSerialNumber();
         }

         if (bootLogger.isDebugEnabled()) {
            bootLogger.debug("Recovered record for destination " + ((DestinationImpl)dest).getName() + " serial num = " + ((DestinationImpl)dest).getSerialNumber());
         }
      }
   }

   private void updateShutdownRecord() throws PersistentStoreException {
      if (!this.cleanShutdown) {
         if (this.lastFailureHandle != null) {
            this.deleteLastFailureRecord();
         }

         this.createLastFailureRecord();
      } else if (this.shutdownRecordHandle != null) {
         this.deleteShutdownRecord();
      }

   }

   private void deleteShutdownRecord() throws PersistentStoreException {
      PersistentStoreTransaction tran = this.store.begin();
      this.configConnection.delete(tran, this.shutdownRecordHandle, 0);
      tran.commit();
      this.cleanShutdown = false;
      this.shutdownRecordHandle = null;
   }

   private void createShutdownRecord() {
      logger.debug("Writing clean shutdown record");

      try {
         PersistentStoreTransaction tran = this.store.begin();
         this.configConnection.create(tran, new PersistedShutdownRecord(), 0);
         tran.commit();
      } catch (PersistentStoreException var2) {
         if (logger.isDebugEnabled()) {
            logger.debug("Error writing clean shutdown record: " + var2, var2);
         }
      }

   }

   private void deleteLastFailureRecord() throws PersistentStoreException {
      PersistentStoreTransaction tran = this.store.begin();
      this.configConnection.delete(tran, this.lastFailureHandle, 0);
      tran.commit();
      this.lastFailureHandle = null;
   }

   private void createLastFailureRecord() {
      if (logger.isDebugEnabled()) {
         logger.debug("Writing last failure record");
      }

      try {
         PersistentStoreTransaction tran = this.store.begin();
         LastFailureRecord lastFailureRecord = new LastFailureRecord();
         lastFailureRecord.setID(this.kernel.getNextHandleID());
         this.configConnection.create(tran, lastFailureRecord, 0);
         tran.commit();
      } catch (PersistentStoreException var3) {
         if (logger.isDebugEnabled()) {
            logger.debug("Error writing last failure record: " + var3, var3);
         }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Finished writing last failure record");
      }

   }

   private void recoverHeaders(Map handleMap, Map elementMap) throws PersistentStoreException {
      if (DELETE_BAD_INSTRTEST) {
         this.deleteAlternatingHeaders(true);
         this.deleteAlternatingHeaders(false);
      }

      PersistentStoreConnection.Cursor cursor = this.headerConnection.createCursor(0);
      ArrayList badPHandles = new ArrayList();

      while(true) {
         while(true) {
            PersistentStoreRecord rec;
            while((rec = cursor.next()) != null) {
               this.kernel.getPaging().waitForSpace();
               Object obj = rec.getData();
               MessageHandle handle;
               PersistentHandle bodyPH;
               if (obj instanceof QueueMessageReference) {
                  QueueMessageReference qRef = (QueueMessageReference)obj;
                  qRef.setPersistentHandle(rec.getHandle());
                  handle = qRef.getMessageHandle();
                  if (DELETE_BAD_MSGHEADERS) {
                     bodyPH = handle.getPersistentHandle();
                     if (bodyPH != null && !this.bodyConnection.isHandleReadable(bodyPH)) {
                        badPHandles.add(rec.getHandle());
                        if (bootLogger.isDebugEnabled()) {
                           bootLogger.debug("weblogic.messaging.kernel.DeleteBadMessageHeaders: Deleting queue header record with missing body record.  body-store-handle=" + bodyPH + " hdr-kernel-qref=" + obj + " hdr-store-handle=" + rec.getHandle() + " hdr-kernel-mhandle=" + handle);
                        }
                        continue;
                     }
                  }

                  this.recoverMessageHandle(handle);

                  assert !handleMap.containsKey(handle.getID());

                  handleMap.put(handle.getID(), handle);
                  this.recoverMessageReference(qRef, handle, elementMap);
                  if (handle.isPersistBody() && handle.getMessage() != null) {
                     handle.makePageable(this.kernel);
                  }
               } else if (obj instanceof MultiPersistenceHandle) {
                  MultiPersistenceHandle persHandle = (MultiPersistenceHandle)obj;
                  persHandle.setPersistentHandle(rec.getHandle());
                  handle = persHandle.getMessageHandle();
                  if (DELETE_BAD_MSGHEADERS) {
                     bodyPH = handle.getPersistentHandle();
                     if (bodyPH != null && !this.bodyConnection.isHandleReadable(bodyPH)) {
                        badPHandles.add(rec.getHandle());
                        bootLogger.debug("weblogic.messaging.kernel.DeleteBadMessageHeaders: Deleting topic multi-handle header record with missing body record.  body-store-handle=" + bodyPH + " hdr-kernel-mph=" + obj + " hdr-store-handle=" + rec.getHandle() + " hdr-kernel-mhandle=" + handle);
                        continue;
                     }
                  }

                  this.recoverMessageHandle(handle);
                  long handleID = handle.getID();
                  MessageHandle existingHandle = (MessageHandle)handleMap.get(handleID);
                  if (existingHandle == null) {
                     handleMap.put(handleID, handle);
                  } else {
                     handle = existingHandle;
                     persHandle.setMessageHandle(existingHandle);
                  }

                  Iterator i = persHandle.getMessageReferences().iterator();

                  while(i.hasNext()) {
                     MessageReference ref = (MessageReference)i.next();
                     ref.setMessageHandle(handle);
                     this.recoverMessageReference(ref, handle, elementMap);
                  }
               } else {
                  if (!(obj instanceof PersistedSequenceRecord)) {
                     throw new AssertionError("Invalid store object type " + obj.getClass().getName());
                  }

                  PersistedSequenceRecord seqRec = (PersistedSequenceRecord)obj;
                  this.recoverSequenceNumber(seqRec, rec.getHandle());
               }
            }

            Iterator var14 = badPHandles.iterator();

            while(var14.hasNext()) {
               PersistentHandle ph = (PersistentHandle)var14.next();
               PersistentStoreTransaction tran = this.store.begin();
               this.headerConnection.delete(tran, ph, 0);
               tran.commit();
            }

            return;
         }
      }
   }

   private void recoverMessageHandle(MessageHandle handle) {
      if (handle.getID() > this.kernel.getLastHandleID()) {
         this.kernel.setLastHandleID(handle.getID());
      }

   }

   private void recoverMessageReference(MessageReference ref, MessageHandle handle, Map elementMap) {
      if (ref.getQueue() == null) {
         assert false : "Found a record for a non-existent queue";

         if (bootLogger.isDebugEnabled()) {
            bootLogger.debug("Message reference " + ref + " cannot find matching queue");
         }

      } else {
         if (this.nextHandleIDAfterCrash > 0L) {
            if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("Found message " + ref + " from previous unsuccessfullly closed store");
            }

            if (ref.getDeliveryCount() == 0 && ref.getMessageHandle().getID() < this.nextHandleIDAfterCrash) {
               ref.incrementDeliveryCount();
            }
         }

         handle.adjustPersistentRefCount(1);
         handle.adjustQueueReferenceCount(1);
         ref.getQueue().addRecoveredMessage(ref);
         elementMap.put(new ElementKey(ref.getQueue().getSerialNumber(), ref.getSequenceNumber()), ref);
         if (bootLogger.isDebugEnabled()) {
            bootLogger.debug("Recovered message reference " + ref);
         }

      }
   }

   private void recoverSequenceNumber(PersistedSequenceRecord seqRec, PersistentHandle persHandle) throws PersistentStoreException {
      SequenceImpl sequence = seqRec.getSequence();
      if (sequence == null) {
         if (bootLogger.isDebugEnabled()) {
            bootLogger.debug("Encountered a persisted sequence number with no matching sequence");
         }

      } else {
         if (bootLogger.isDebugEnabled()) {
            bootLogger.debug("Recovered new value " + seqRec.getNewValue() + " for the sequence " + sequence);
         }

         sequence.setNumberRecord(seqRec);
         sequence.setLastValueInternal(seqRec.getNewValue());
         sequence.setLastAssignedValueInternal(seqRec.getNewAssignedValue());
         sequence.setUserDataInternal(seqRec.getUserData());
         sequence.setNumberPersistentHandle(persHandle);
         if (seqRec.getXid() != null) {
            SequenceUpdateOperation op = new SequenceUpdateOperation(this.kernel, sequence);
            op.setGXid(seqRec.getXid());
            this.store.getGXAResource().addRecoveredOperation(op);
         }

      }
   }

   private void recoverBodies(Map handleMap) throws PersistentStoreException {
      PersistentStoreConnection.Cursor cursor = this.bodyConnection.createCursor(0);

      PersistentStoreRecord rec;
      while((rec = cursor.next()) != null) {
         this.kernel.getPaging().waitForSpace();
         PersistedBody pBody = (PersistedBody)rec.getData();
         MessageHandle handle = (MessageHandle)handleMap.get(pBody.getHandleID());
         if (handle == null) {
            if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("Cannot find matching message handle for body " + pBody.getHandleID() + ": Ignoring orphaned message body");
            }
         } else {
            assert handle.getMessage() == null;

            assert !handle.isPersistBody();

            handle.setMessage(pBody.getMessage());
            handle.makePageable(this.kernel);
            if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("Recovered message body for handle " + pBody.getHandleID());
            }
         }
      }

   }

   private void recover2PCRecords(Map elementMap) throws KernelException, PersistentStoreException {
      long hiOperationID = 0L;
      ArrayList deadHandles = null;
      PersistentStoreConnection.Cursor cursor = this.xaConnection.createCursor(0);

      while(true) {
         while(true) {
            PersistentStoreRecord rec;
            while((rec = cursor.next()) != null) {
               PersistedXARecord xaRec = (PersistedXARecord)rec.getData();
               QueueImpl queue = this.kernel.findQueueUnsync(xaRec.getQueue());
               if (queue != null && !queue.isDeleted()) {
                  ElementKey key = new ElementKey(xaRec.getQueue(), xaRec.getSequenceNumber());
                  MessageReference element = (MessageReference)elementMap.get(key);
                  if (element == null) {
                     if (!BOOT_ON_ERROR && !DELETE_BAD_2PCRECORDS) {
                        throw new KernelException("Ignoring 2PC record for sequence=" + xaRec.getSequenceNumber() + ", queue=" + xaRec.getQueue() + ", queueName=" + queue.getName() + ", type=" + this.typeToString(xaRec.getType()) + ", id=" + xaRec.getID() + ", XID=" + xaRec.getXID().toString() + " because the element cannot be found");
                     }

                     MessagingLogger.logIgnore2PCRecord(this.store.getName(), xaRec.getSequenceNumber(), queue.getName(), this.typeToString(xaRec.getType()), xaRec.getID(), xaRec.getXID().toString());
                     if (DELETE_BAD_2PCRECORDS) {
                        deadHandles.add(rec.getHandle());
                     }
                  } else {
                     assert element.getQueue() == queue;

                     Object operation;
                     switch (xaRec.getType()) {
                        case 1:
                        case 6:
                           operation = new SendOperation(xaRec.getType(), queue, element, this.kernel, xaRec.getSubjectName(), false);
                           break;
                        case 2:
                        case 5:
                           operation = new ReceiveOperation(xaRec.getType(), queue, element, xaRec.getUserID(), (RedeliveryParameters)null, this.kernel, xaRec.getSubjectName(), false);
                           break;
                        case 3:
                        case 4:
                        default:
                           if (bootLogger.isDebugEnabled()) {
                              bootLogger.debug("Unknown XA operation type " + this.typeToString(xaRec.getType()));
                           }
                           continue;
                     }

                     ((AbstractOperation)operation).setID(xaRec.getID());
                     ((AbstractOperation)operation).setPersistentHandle(rec.getHandle());
                     ((AbstractOperation)operation).setGXid(xaRec.getXID());
                     queue.addRecoveredOperation((AbstractOperation)operation);
                     if (bootLogger.isDebugEnabled()) {
                        bootLogger.debug("Recovered a 2PC operation of type " + this.typeToString(xaRec.getType()) + " for " + element);
                     }

                     if (xaRec.getID() > hiOperationID) {
                        hiOperationID = xaRec.getID();
                     }
                  }
               } else {
                  if (bootLogger.isDebugEnabled()) {
                     bootLogger.debug("Ignoring 2PC record for sequence=" + xaRec.getSequenceNumber() + " queue=" + xaRec.getQueue() + " because the queue was deleted");
                  }

                  if (deadHandles == null) {
                     deadHandles = new ArrayList();
                  }

                  deadHandles.add(rec.getHandle());
               }
            }

            this.kernel.setLastOperationID(hiOperationID);
            if (deadHandles != null) {
               Iterator i = deadHandles.iterator();

               while(i.hasNext()) {
                  PersistentHandle handle = (PersistentHandle)i.next();
                  PersistentStoreTransaction tran = this.store.begin();
                  this.xaConnection.delete(tran, handle, 0);
                  tran.commit();
               }
            }

            return;
         }
      }
   }

   private void deleteAlternatingHeaders(boolean isQueue) throws PersistentStoreException {
      if (bootLogger.isDebugEnabled()) {
         bootLogger.debug("weblogic.messaging.kernel.DeleteBadInstrTest:  Deleting alternate msg headers isQueue = " + isQueue);
      }

      PersistentHandle bodyToDelete = null;
      PersistentStoreConnection.Cursor cursor = this.headerConnection.createCursor(0);
      boolean skipNext = false;

      PersistentStoreRecord rec;
      while((rec = cursor.next()) != null) {
         Object obj = rec.getData();
         PersistentHandle deleteMe = null;
         if (isQueue) {
            if (obj instanceof QueueMessageReference) {
               deleteMe = ((QueueMessageReference)obj).getMessageHandle().getPersistentHandle();
            }
         } else if (obj instanceof MultiPersistenceHandle) {
            deleteMe = ((MultiPersistenceHandle)obj).getMessageHandle().getPersistentHandle();
         }

         if (deleteMe != null) {
            if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("weblogic.messaging.kernel.DeleteBadInstrTest: Found " + deleteMe + " deleting= " + !skipNext);
            }

            if (skipNext) {
               skipNext = false;
            } else {
               skipNext = true;
               PersistentStoreTransaction tran = this.store.begin();
               this.bodyConnection.delete(tran, deleteMe, 0);
               tran.commit();
            }
         }
      }

   }

   private void cleanupDeletedQueues(Set deletedQueues) {
      Iterator i = deletedQueues.iterator();

      while(i.hasNext()) {
         QueueImpl queue = (QueueImpl)i.next();

         try {
            queue.emptyAll();
         } catch (KernelException var5) {
            if (bootLogger.isDebugEnabled()) {
               bootLogger.debug("Error cleaning up a deleted queue: " + var5, var5);
            }
         }
      }

   }

   private String typeToString(int type) {
      switch (type) {
         case 1:
            return "SEND";
         case 2:
            return "RECEIVE";
         case 3:
            return "EXPIRATION";
         case 4:
            return "REDELIVERY_LIMIT";
         case 5:
            return "DELETE";
         case 6:
            return "MULTI_SEND";
         default:
            return Integer.toString(type);
      }
   }

   static {
      String storeBootOnError = System.getProperty("weblogic.store.StoreBootOnError");
      BOOT_ON_ERROR = storeBootOnError != null && storeBootOnError.equalsIgnoreCase("true");
      String upgradeMode = System.getProperty("weblogic.saf.StoreUpgradeMode");
      upgradeOldConnectionsOnly = upgradeMode != null && "OldOnly".equalsIgnoreCase(upgradeMode);
      upgradeIgnoreOldConnections = upgradeMode != null && "IgnoreOld".equalsIgnoreCase(upgradeMode);
      forceOldConnections = upgradeMode != null && "ForceOld".equalsIgnoreCase(upgradeMode);
      String deleteBadInstrTest = System.getProperty("weblogic.messaging.kernel.DeleteBadInstrTest");
      DELETE_BAD_INSTRTEST = deleteBadInstrTest != null && deleteBadInstrTest.equalsIgnoreCase("true");
      String deleteBadMsgHeaders = System.getProperty("weblogic.messaging.kernel.DeleteBadMessageHeaders");
      DELETE_BAD_MSGHEADERS = deleteBadMsgHeaders != null && deleteBadMsgHeaders.equalsIgnoreCase("true");
      String deleteBad2PCRecords = System.getProperty("weblogic.messaging.kernel.DeleteBad2PCRecords");
      DELETE_BAD_2PCRECORDS = deleteBad2PCRecords != null && deleteBad2PCRecords.equalsIgnoreCase("true");
      SEQUENCE_NUMBER_COMPARATOR = new SequenceNumberComparator();
   }

   private static final class SequenceNumberComparator implements Comparator {
      private SequenceNumberComparator() {
      }

      public int compare(MessageReference m1, MessageReference m2) {
         if (m1.getMessageHandle().getID() == m2.getMessageHandle().getID()) {
            return 0;
         } else {
            return m1.getMessageHandle().getID() < m2.getMessageHandle().getID() ? -1 : 1;
         }
      }

      public boolean equals(Object o) {
         return o instanceof SequenceNumberComparator;
      }

      // $FF: synthetic method
      SequenceNumberComparator(Object x0) {
         this();
      }
   }

   private static final class ElementKey {
      long queueID;
      long sequenceNum;

      ElementKey(long queueID, long sequenceNum) {
         this.queueID = queueID;
         this.sequenceNum = sequenceNum;
      }

      public int hashCode() {
         return (int)this.sequenceNum;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            try {
               ElementKey key = (ElementKey)o;
               return this.sequenceNum == key.sequenceNum && this.queueID == key.queueID;
            } catch (ClassCastException var3) {
               return false;
            }
         }
      }
   }

   final class MessageDeleteJob implements TimerListener, Runnable {
      private static final int MAX_BATCH_SIZE = 128;
      private List list;
      private int position;
      private KernelRequest request;
      private Runnable cleanUpWork;

      MessageDeleteJob(List list, KernelRequest request, Runnable cleanUpWork) {
         this.list = list;
         this.request = request;
         this.cleanUpWork = cleanUpWork;
      }

      private boolean commit(PersistentStoreTransaction tran) {
         try {
            tran.commit();
            return true;
         } catch (PersistentStoreException var3) {
            this.request.setResult(new KernelException("I/O error emptying queue", var3));
            return false;
         }
      }

      public void timerExpired(Timer timer) {
         this.deleteWork();
      }

      public synchronized void run() {
         this.deleteWork();
      }

      public synchronized void deleteWork() {
         if (PersistenceImpl.logger.isDebugEnabled()) {
            PersistenceImpl.logger.debug("Starting to delete " + (this.list.size() - this.position) + " messages");
         }

         if (this.list == null) {
            this.request.setResult((Object)null);
         } else {
            Collections.sort(this.list, PersistenceImpl.SEQUENCE_NUMBER_COMPARATOR);

            do {
               try {
                  PersistenceImpl.this.kernel.checkOpened();
               } catch (KernelException var7) {
                  this.request.setResult(var7);
                  return;
               }

               PersistentStoreTransaction tran = PersistenceImpl.this.store.begin();
               int count = 0;

               ArrayList unlockHandles;
               for(unlockHandles = new ArrayList(); count < 128 && this.position < this.list.size(); ++this.position) {
                  MessageReference element = (MessageReference)this.list.get(this.position);
                  if (element instanceof MultiMessageReference) {
                     MultiPersistenceHandle persHandlex = ((MultiMessageReference)element).getPersistenceHandle();
                     persHandlex.lock(tran);
                     unlockHandles.add(persHandlex);
                  }

                  PersistenceImpl.this.deleteMessage(tran, element);
                  element.getMessageHandle().removePagedState(PersistenceImpl.this.kernel);
                  ++count;
               }

               CompletionRequest cr = new CompletionRequest();
               tran.commit(cr);
               Iterator i = unlockHandles.iterator();

               while(i.hasNext()) {
                  MultiPersistenceHandle persHandle = (MultiPersistenceHandle)i.next();
                  persHandle.unlock(tran);
               }

               try {
                  cr.getResult();
               } catch (Throwable var8) {
                  this.request.setResult(new KernelException("I/O error emptying queue", var8));
                  return;
               }

               if (this.position >= this.list.size()) {
                  PersistenceImpl.logger.debug("Done with message delete request");
                  if (this.cleanUpWork != null) {
                     this.cleanUpWork.run();
                  }

                  this.request.setResult((Object)null);
                  return;
               }
            } while(!PersistenceImpl.this.kernel.getLimitedWorkManager().scheduleIfBusy(this));

         }
      }
   }

   private class TestStoreExceptionObjectHandler implements ObjectHandler, TestStoreException {
      ObjectHandler delegate;

      private TestStoreExceptionObjectHandler(ObjectHandler delegate) {
         this.delegate = delegate;
      }

      public void writeObject(ObjectOutput out, Object obj) throws IOException {
         this.delegate.writeObject(out, obj);
      }

      public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
         return this.delegate.readObject(in);
      }

      public PersistentStoreException getTestException() {
         return ((TestStoreException)this.delegate).getTestException();
      }

      // $FF: synthetic method
      TestStoreExceptionObjectHandler(ObjectHandler x1, Object x2) {
         this(x1);
      }
   }
}
