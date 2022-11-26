package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelStatistics;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicLong;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class KernelImpl extends AbstractConfigurable implements Kernel {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private static final String TIMER_MANAGER_NAME = "weblogic.messaging.kernel.";
   private static final String DIRECT_TIMER_EXT = ".direct";
   private static final String LIMITED_TIMER_EXT = ".limited";
   private static final int TIMER_POOL_DEFAULT = 16;
   private static final int TIMER_POOL_MAX = 256;
   private static final int TIMER_POOL_MIN = 1;
   private static int directTimerPoolLimit = 16;
   private static int limitedTimerPoolLimit = 16;
   private int currentDirectTimer;
   private int currentLimitedTimer;
   private Object currentTimerLock = new Object();
   private volatile boolean opened;
   private final Map queues = new HashMap();
   private final Map queuesByID = new HashMap();
   private final Map topics = new HashMap();
   private final Map topicsByID = new HashMap();
   private final Map quotas = new HashMap();
   private KernelStatisticsImpl statistics;
   private TimerManager[] limitedTimerManager;
   private String limitedTimerManagerName;
   private TimerManager[] directTimerManager;
   private String directTimerManagerName;
   private EventManager eventManager;
   private ClientTransactionManager tranManager;
   private WorkManager workManager;
   private WorkManager limitedWorkManager;
   private WorkManager defaultWorkManager;
   private PersistentStoreXA store;
   private GXAResource gxaResource;
   private PersistenceImpl persistence;
   private PagingImpl paging;
   private ObjectHandler userObjectHandler;
   private long messageBufferSize;
   private String pagingDirectory;
   private HashMap pagingFileParams = new HashMap();
   private int maximumMessageSize;
   private int topicPackSize = 256;
   private long lastSerialNum;
   private final AtomicLong handleID = AtomicFactory.createAtomicLong();
   private final AtomicLong operationID = AtomicFactory.createAtomicLong();
   private final AtomicLong sequenceID = AtomicFactory.createAtomicLong();
   private static final long DEFAULT_MESSAGE_BUFFER_SIZE = -1L;
   private static final int DEFAULT_TOPIC_PACK_SIZE = 256;
   private String alternativeKernelName = null;
   private volatile boolean useAlternativeName = false;

   public KernelImpl(String name, Map properties) throws KernelException {
      super(name);
      if (logger.isDebugEnabled()) {
         logger.debug("Messaging kernel " + name + " starting constructor");
      }

      this.tranManager = TransactionHelper.getTransactionHelper().getTransactionManager();
      this.statistics = new KernelStatisticsImpl(name, this, (StatisticsImpl)null);
      this.setProperty("Store", (Object)null);
      this.setProperty("MessageBufferSize", -1L);
      this.setProperty("PagingDirectory", ".");
      this.setProperty("MaximumMessageSize", Integer.MAX_VALUE);
      this.getSystemProperties();
      if (properties != null) {
         this.setProperties(properties);
      }

      if (this.workManager == null) {
         this.workManager = WorkManagerFactory.getInstance().getSystem();
      }

      if (this.limitedWorkManager == null) {
         this.limitedWorkManager = WorkManagerFactory.getInstance().getSystem();
      }

      this.defaultWorkManager = WorkManagerFactory.getInstance().getDefault();
      this.eventManager = new EventManager(this.workManager);
      if (this.limitedTimerManagerName == null) {
         this.limitedTimerManagerName = "weblogic.messaging.kernel." + name + ".limited";
      }

      this.limitedTimerManager = new TimerManager[limitedTimerPoolLimit];

      int i;
      for(i = 0; i < limitedTimerPoolLimit; ++i) {
         this.limitedTimerManager[i] = TimerManagerFactory.getTimerManagerFactory().getTimerManager(this.limitedTimerManagerName + i, this.limitedWorkManager);
      }

      if (this.directTimerManagerName == null) {
         this.directTimerManagerName = "weblogic.messaging.kernel." + name + ".direct";
      }

      this.directTimerManager = new TimerManager[directTimerPoolLimit];

      for(i = 0; i < directTimerPoolLimit; ++i) {
         this.directTimerManager[i] = TimerManagerFactory.getTimerManagerFactory().getTimerManager(this.directTimerManagerName + i, WorkManagerFactory.getInstance().find("direct"));
      }

      if (logger.isDebugEnabled()) {
         logger.debug("Messaging kernel " + name + " done with constructor");
      }

   }

   TimerManager getLimitedTimerManager() {
      synchronized(this.currentTimerLock) {
         int index = this.currentLimitedTimer++ % limitedTimerPoolLimit;
         return this.limitedTimerManager[index];
      }
   }

   TimerManager getDirectTimerManager() {
      synchronized(this.currentTimerLock) {
         int index = this.currentDirectTimer++ % directTimerPoolLimit;
         return this.directTimerManager[index];
      }
   }

   EventManager getEventManager() {
      return this.eventManager;
   }

   ClientTransactionManager getTransactionManager() {
      return this.tranManager;
   }

   WorkManager getWorkManager() {
      return this.workManager;
   }

   WorkManager getLimitedWorkManager() {
      return this.limitedWorkManager;
   }

   WorkManager getDefaultWorkManager() {
      return this.defaultWorkManager;
   }

   PersistenceImpl getPersistence() {
      return this.persistence;
   }

   PagingImpl getPaging() {
      return this.paging;
   }

   int getMaximumMessageSize() {
      return this.maximumMessageSize;
   }

   int getTopicPackSize() {
      return this.topicPackSize;
   }

   void checkOpened() throws KernelException {
      if (!this.opened) {
         throw new KernelException("The Messaging Kernel " + this.getName() + " has not yet been opened");
      }
   }

   boolean isOpened() {
      return this.opened;
   }

   String getAlternativeKernelName() {
      return this.alternativeKernelName;
   }

   public boolean useAlternativeName() {
      return this.useAlternativeName;
   }

   void setUseAlternativeName(boolean value) {
      this.useAlternativeName = value;
   }

   public synchronized void open() throws KernelException {
      if (this.opened) {
         throw new KernelException("Already open");
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Messaging kernel " + this.name + " opening");
         }

         PersistentStoreXA gxaStore = this.store;
         if (gxaStore == null) {
            gxaStore = (PersistentStoreXA)PersistentStoreManager.getManager().getDefaultStore();
         }

         if (!(gxaStore instanceof PersistentStoreXA)) {
            throw new KernelException("Default store is not XA enabled");
         } else {
            try {
               this.gxaResource = gxaStore.getGXAResource();
            } catch (PersistentStoreException var3) {
               throw new KernelException("Error getting GXA resource", var3);
            }

            this.paging = new PagingImpl(this, this.pagingDirectory, this.userObjectHandler, this.messageBufferSize, this.store != null && this.store.supportsFastReads());
            this.paging.setPagingConfig(this.pagingFileParams);
            this.paging.open();
            this.statistics.setPaging(this.paging);
            if (this.store != null) {
               this.persistence = new PersistenceImpl(this, this.store, this.userObjectHandler);
               this.persistence.open();
               this.persistence.recover();
            }

            this.opened = true;
            if (logger.isDebugEnabled()) {
               logger.debug("Messaging kernel " + this.name + " fully opened");
            }

         }
      }
   }

   public synchronized void close() throws KernelException {
      this.checkOpened();
      if (logger.isDebugEnabled()) {
         logger.debug("Messaging kernel " + this.name + " closing");
      }

      int n;
      for(n = 0; n < limitedTimerPoolLimit; ++n) {
         this.limitedTimerManager[n].stop();
      }

      for(n = 0; n < directTimerPoolLimit; ++n) {
         this.directTimerManager[n].stop();
      }

      Collection destinations = this.getDestinations();
      Iterator i = destinations.iterator();

      while(i.hasNext()) {
         ((DestinationImpl)i.next()).close();
      }

      if (this.persistence != null) {
         this.persistence.close();
      }

      this.paging.close();
      this.queues.clear();
      this.queuesByID.clear();
      this.topics.clear();
      this.opened = false;
      if (logger.isDebugEnabled()) {
         logger.debug("Messaging kernel " + this.name + " fully closed");
      }

   }

   public synchronized Queue findQueue(String name) {
      return (Queue)this.queues.get(name);
   }

   public synchronized Queue findQueueByAlternativeName(String name) {
      Iterator itr = this.queues.values().iterator();

      QueueImpl queue;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         queue = (QueueImpl)itr.next();
      } while(!name.equals(queue.getAlternativeName()));

      return queue;
   }

   public synchronized QueueImpl findQueue(long id) {
      return this.findQueueUnsync(id);
   }

   public QueueImpl findQueueUnsync(long id) {
      return (QueueImpl)this.queuesByID.get(id);
   }

   public synchronized Queue createQueue(String name, Map properties) throws KernelException {
      if (this.queues.get(name) != null) {
         throw new KernelException("Queue already exists, " + name);
      } else {
         QueueImpl newQueue = new QueueImpl(name, properties, this);
         this.queues.put(name, newQueue);
         if (this.opened) {
            this.setQueueSerialNumber(name, ++this.lastSerialNum);
         }

         return newQueue;
      }
   }

   synchronized void addQueue(QueueImpl queue) {
      if (!queue.isDeleted()) {
         this.queues.put(queue.getName(), queue);
      }

      this.queuesByID.put(queue.getSerialNumber(), queue);
   }

   synchronized void queueDeleted(QueueImpl deadQueue, boolean deleteFully) {
      this.queues.remove(deadQueue.getName());
      if (deleteFully) {
         this.queuesByID.remove(deadQueue.getSerialNumber());
      }

   }

   synchronized void setQueueSerialNumber(String name, long serialNum) {
      QueueImpl queue = (QueueImpl)this.queues.get(name);
      if (queue != null) {
         queue.setSerialNumber(serialNum);
         this.queuesByID.put(serialNum, queue);
      }

   }

   public synchronized Collection getQueues() {
      return Collections.unmodifiableCollection(new ArrayList(this.queues.values()));
   }

   public synchronized Topic findTopic(String name) {
      return (Topic)this.topics.get(name);
   }

   public synchronized TopicImpl findTopic(long id) {
      return (TopicImpl)this.topicsByID.get(id);
   }

   public synchronized DestinationImpl findDestination(long id) {
      DestinationImpl ret = this.findQueue(id);
      if (ret == null) {
         ret = this.findTopic(id);
      }

      return (DestinationImpl)ret;
   }

   public synchronized Topic createTopic(String name, Map properties) throws KernelException {
      if (this.topics.containsKey(name)) {
         throw new KernelException("Topic already exists, " + name);
      } else {
         TopicImpl newTopic = new TopicImpl(name, properties, this);
         this.topics.put(name, newTopic);
         if (this.opened) {
            this.setTopicSerialNumber(name, ++this.lastSerialNum);
         }

         return newTopic;
      }
   }

   synchronized void addTopic(TopicImpl topic) {
      if (!topic.isDeleted()) {
         this.topics.put(topic.getName(), topic);
      }

      this.topicsByID.put(topic.getSerialNumber(), topic);
   }

   synchronized void setTopicSerialNumber(String name, long serialNum) {
      TopicImpl topic = (TopicImpl)this.topics.get(name);
      if (topic != null) {
         topic.setSerialNumber(serialNum);
         this.topicsByID.put(serialNum, topic);
      }

   }

   public synchronized Collection getTopics() {
      return Collections.unmodifiableCollection(new ArrayList(this.topics.values()));
   }

   synchronized void topicDeleted(TopicImpl deadTopic) {
      this.topics.remove(deadTopic.getName());
      this.topicsByID.remove(deadTopic.getSerialNumber());
   }

   public synchronized Collection getDestinations() {
      ArrayList ret = new ArrayList();
      ret.addAll(this.queues.values());
      ret.addAll(this.topics.values());
      return Collections.unmodifiableCollection(ret);
   }

   public synchronized Quota findQuota(String name) {
      return (Quota)this.quotas.get(name);
   }

   public synchronized Quota createQuota(String name) throws KernelException {
      if (this.quotas.get(name) != null) {
         throw new KernelException("Quota already exists, " + name);
      } else {
         QuotaImpl quota = new QuotaImpl(name, this);
         this.quotas.put(name, quota);
         return quota;
      }
   }

   public synchronized void deleteQuota(String name) {
      this.quotas.remove(name);
   }

   public Cursor createCursor(Xid xid) throws KernelException {
      return new CursorImpl(this, xid);
   }

   public Cursor createCursor(Collection queues, Expression expression, int state) throws KernelException {
      return new CursorImpl(this, queues, expression, state);
   }

   long getNextHandleID() {
      return this.handleID.incrementAndGet();
   }

   long getLastHandleID() {
      return this.handleID.get();
   }

   void setLastHandleID(long lastID) {
      this.handleID.set(lastID);
   }

   long getNextOperationID() {
      return this.operationID.incrementAndGet();
   }

   void setLastOperationID(long lastID) {
      this.operationID.set(lastID);
   }

   long getNextSequenceID() {
      return this.sequenceID.incrementAndGet();
   }

   void setLastSequenceID(long lastID) {
      this.sequenceID.set(lastID);
   }

   synchronized void setLastSerialNum(long lastNum) {
      this.lastSerialNum = lastNum;
   }

   GXATransaction getGXATransaction() throws KernelException {
      try {
         return this.gxaResource.enlist(true);
      } catch (PersistentStoreException var2) {
         throw new KernelException("Error enlisting GXA transaction", var2);
      }
   }

   public GXALocalTransaction startLocalGXATransaction() throws KernelException {
      try {
         return this.gxaResource.beginLocalTransaction();
      } catch (PersistentStoreException var2) {
         throw new KernelException("Error enlisting GXA transaction", var2);
      }
   }

   GXAResource getGXAResource() {
      return this.gxaResource;
   }

   public void setProperty(String name, Object value) throws KernelException {
      try {
         if (name.equals("Store")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.store = (PersistentStoreXA)value;
         } else if (name.equals("PagingDirectory")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.pagingDirectory = (String)value;
         } else if (name.equals("PagingParams")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.pagingFileParams.putAll((HashMap)value);
         } else if (name.equals("ObjectHandler")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.userObjectHandler = (ObjectHandler)value;
         } else if (name.equals("WorkManager")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.workManager = (WorkManager)value;
         } else if (name.equals("LimitedWorkManager")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.limitedWorkManager = (WorkManager)value;
         } else if (name.equals("LimitedTimerManagerName")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.limitedTimerManagerName = (String)value;
         } else if (name.equals("DirectTimerManagerName")) {
            if (this.opened) {
               throw new KernelException("Kernel is open");
            }

            this.directTimerManagerName = (String)value;
         } else if (name.equals("MessageBufferSize")) {
            this.messageBufferSize = (Long)value;
            if (this.paging != null) {
               this.paging.setBufferSize(this.messageBufferSize);
            }
         } else {
            int lpoolSize;
            if (name.equals("MaximumMessageSize")) {
               lpoolSize = (Integer)value;
               if (lpoolSize < 0) {
                  throw new KernelException("Value may not be negative");
               }

               this.maximumMessageSize = lpoolSize;
            } else if (name.equals("AlternativeKernelName")) {
               this.alternativeKernelName = (String)value;
            } else if (name.equals("weblogic.messaging.kernel.TopicPackSize")) {
               lpoolSize = (Integer)value;
               if (lpoolSize < 0) {
                  throw new KernelException("Value may not be negative");
               }

               this.topicPackSize = lpoolSize;
            } else if (name.equals("weblogic.messaging.kernel.DirectTimerManagerPoolLimit")) {
               lpoolSize = (Integer)value;
               if (lpoolSize > 256) {
                  lpoolSize = 256;
               } else if (lpoolSize < 1) {
                  lpoolSize = 1;
               }

               setDirectTimerPoolLimit(lpoolSize);
               if (logger.isDebugEnabled()) {
                  logger.debug("Messaging kernel " + this.getName() + " customized direct timer manager pool size " + directTimerPoolLimit);
               }
            } else if (name.equals("weblogic.messaging.kernel.LimitedTimerManagerPoolLimit")) {
               lpoolSize = (Integer)value;
               if (lpoolSize > 256) {
                  lpoolSize = 256;
               } else if (lpoolSize < 1) {
                  lpoolSize = 1;
               }

               limitedTimerPoolLimit = lpoolSize;
               if (logger.isDebugEnabled()) {
                  logger.debug("Messaging kernel " + this.getName() + " customized limited timer manager pool size " + limitedTimerPoolLimit);
               }
            } else if (!name.startsWith("weblogic.messaging.kernel")) {
               throw new KernelException("Unknown property name, " + name);
            }
         }
      } catch (ClassCastException var4) {
         throw new KernelException("Invalid type for property, " + name);
      }

      super.setProperty(name, value);
   }

   private static void setDirectTimerPoolLimit(int limit) {
      directTimerPoolLimit = limit;
   }

   PersistentStore getPersistentStore() {
      return this.store;
   }

   public KernelStatistics getStatistics() {
      return this.statistics;
   }

   public boolean isStoreOverloaded(long bytes) {
      if (this.store != null && this.store.getStatistics() != null) {
         return this.store.getStatistics().isWarningOverloaded() || this.store.getStatistics().isPotentiallyOverloaded(bytes);
      } else {
         return false;
      }
   }

   public boolean isStoreSeverelyOverloaded() {
      return this.store != null && this.store.getStatistics() != null ? this.store.getStatistics().isDeviceErrorOverloaded() : false;
   }

   public int getDeviceUsedPercent() {
      return this.store != null && this.store.getStatistics() != null ? this.store.getStatistics().getDeviceUsedPercent() : 0;
   }

   public int getLocalUsedPercent() {
      return this.store != null && this.store.getStatistics() != null ? this.store.getStatistics().getLocalUsedPercent() : 0;
   }

   public int getMaximumWriteSize() {
      return this.store != null && this.store.getStatistics() != null ? this.store.getStatistics().getMaximumWriteSize() : Integer.MAX_VALUE;
   }

   private void getSystemProperties() throws KernelException {
      this.setLongSystemProp("weblogic.messaging.kernel.persistence.InLineBodyThreshold");
      this.setBooleanSystemProp("weblogic.messaging.kernel.persistence.PageInOnBoot");
      this.setBooleanSystemProp("weblogic.messaging.kernel.paging.AlwaysUsePagingStore");
      this.setIntegerSystemProp("weblogic.messaging.kernel.paging.BatchSize");
      this.setLongSystemProp("weblogic.messaging.kernel.paging.PagedMessageThreshold");
      this.setIntegerSystemProp("weblogic.messaging.kernel.TopicPackSize");
      this.setIntegerSystemProp("weblogic.messaging.kernel.DirectTimerManagerPoolLimit");
      this.setIntegerSystemProp("weblogic.messaging.kernel.LimitedTimerManagerPoolLimit");
   }

   private void setLongSystemProp(String name) throws KernelException {
      String prop = System.getProperty(name);
      if (prop != null) {
         try {
            this.setProperty(name, Long.valueOf(prop));
         } catch (NumberFormatException var4) {
         }
      }

   }

   private void setIntegerSystemProp(String name) throws KernelException {
      String prop = System.getProperty(name);
      if (prop != null) {
         try {
            this.setProperty(name, Integer.valueOf(prop));
         } catch (NumberFormatException var4) {
         }
      }

   }

   private void setBooleanSystemProp(String name) throws KernelException {
      String prop = System.getProperty(name);
      if (prop != null) {
         try {
            this.setProperty(name, Boolean.valueOf(prop));
         } catch (NumberFormatException var4) {
         }
      }

   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("MessagingKernel");
      xsw.writeAttribute("name", this.name != null ? this.name : "");
      xsw.writeAttribute("messageBufferSize", String.valueOf(this.messageBufferSize));
      xsw.writeAttribute("pagingDirectory", this.pagingDirectory != null ? this.pagingDirectory : "");
      xsw.writeAttribute("pagingFileParams", this.pagingFileParams.toString());
      xsw.writeAttribute("maximumMessageSize", String.valueOf(this.maximumMessageSize));
      xsw.writeAttribute("persistentStoreName", this.store != null ? this.store.getName() : "");
      this.statistics.dump(imageSource, xsw);

      int n;
      for(n = 0; n < limitedTimerPoolLimit; ++n) {
         MessagingKernelDiagnosticImageSource.dumpTimerManager(xsw, this.limitedTimerManager[n]);
      }

      for(n = 0; n < directTimerPoolLimit; ++n) {
         MessagingKernelDiagnosticImageSource.dumpTimerManager(xsw, this.directTimerManager[n]);
      }

      assert this.quotas instanceof HashMap;

      xsw.writeStartElement("Quotas");
      HashMap tempQuotas = (HashMap)((HashMap)this.quotas).clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempQuotas.size()));
      Iterator it = tempQuotas.values().iterator();

      while(it.hasNext()) {
         QuotaImpl quota = (QuotaImpl)it.next();
         quota.dump(imageSource, xsw);
      }

      xsw.writeEndElement();

      assert this.queues instanceof HashMap;

      xsw.writeStartElement("Queues");
      HashMap tempQueues = (HashMap)((HashMap)this.queues).clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempQueues.size()));
      it = tempQueues.values().iterator();

      while(it.hasNext()) {
         QueueImpl queue = (QueueImpl)it.next();
         queue.dump(imageSource, xsw);
      }

      xsw.writeEndElement();

      assert this.topics instanceof HashMap;

      xsw.writeStartElement("Topics");
      HashMap tempTopics = (HashMap)((HashMap)this.topics).clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempTopics.size()));
      it = tempTopics.values().iterator();

      while(it.hasNext()) {
         TopicImpl topic = (TopicImpl)it.next();
         topic.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }
}
