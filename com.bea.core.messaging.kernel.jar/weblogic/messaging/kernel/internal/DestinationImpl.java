package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.Event;
import weblogic.messaging.kernel.EventListener;
import weblogic.messaging.kernel.Filter;
import weblogic.messaging.kernel.IllegalStateException;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaException;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Statistics;
import weblogic.messaging.kernel.internal.events.EventImpl;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATransaction;
import weblogic.timers.TimerManager;

public abstract class DestinationImpl extends AbstractConfigurable implements Destination, Persistable {
   protected static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private static final SendOptions DEFAULT_SEND_OPTIONS = new SendOptions();
   protected KernelImpl kernel;
   protected QuotaImpl quota;
   protected AbstractStatistics statistics;
   private TimerManager limitedTimerManager;
   private TimerManager directTimerManager;
   protected EventManager eventManager;
   private LinkedList listenerList;
   private volatile int mask = 16384;
   protected Filter filter;
   protected Comparator comparator;
   protected int maximumMessageSize;
   protected RedirectionListener redirectionListener;
   protected boolean ignoreExpiration;
   protected long serialNumber;
   private PersistentHandle storeHandle;
   private String defaultAssignSequenceName;
   private SequenceImpl defaultAssignSequence;
   protected SendOptions defaultSendOptions;
   protected boolean durable = true;
   protected boolean created;
   private volatile boolean deleted;
   private volatile boolean activated;
   protected int logMask;
   private boolean isSAFImportedDestination = false;
   protected volatile long subscriptionMsgsLimit = -1L;
   private String alternativeName;
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DELETED_FLAG = 256;

   protected DestinationImpl(String name) {
      super(name);
   }

   protected DestinationImpl() {
   }

   protected void close() {
   }

   protected void initialize(KernelImpl kernel) {
      this.kernel = kernel;
      this.limitedTimerManager = kernel.getLimitedTimerManager();
      this.directTimerManager = kernel.getDirectTimerManager();
      this.eventManager = kernel.getEventManager();
      this.listenerList = new LinkedList();
      this.statistics = new StatisticsImpl(this.name, kernel, (AbstractStatistics)kernel.getStatistics());
   }

   public long getSerialNumber() {
      return this.serialNumber;
   }

   public void setSerialNumber(long serialNumber) {
      this.serialNumber = serialNumber;
   }

   TimerManager getLimitedTimerManager() {
      return this.limitedTimerManager;
   }

   TimerManager getDirectTimerManager() {
      return this.directTimerManager;
   }

   PersistentHandle getPersistentHandle() {
      return this.storeHandle;
   }

   void setPersistentHandle(PersistentHandle pHandle) {
      this.storeHandle = pHandle;
   }

   int getLogMask() {
      return this.logMask;
   }

   public String getName() {
      return this.name;
   }

   String getAlternativeName() {
      return this.alternativeName;
   }

   public Kernel getKernel() {
      return this.kernel;
   }

   public KernelImpl getKernelImpl() {
      return this.kernel;
   }

   public Statistics getStatistics() {
      return this.statistics;
   }

   public Quota getQuota() {
      return this.quota;
   }

   QuotaImpl getQuotaImpl() {
      return this.quota;
   }

   boolean isDeleted() {
      return this.deleted;
   }

   void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }

   boolean isActive() {
      return this.activated && !this.deleted;
   }

   RedirectionListener getRedirectionListener() {
      return this.redirectionListener;
   }

   protected void checkActivation() throws KernelException {
      this.kernel.checkOpened();
      if (this.isSuspended(16384)) {
         throw new KernelException("Destination has not been activated");
      }
   }

   protected void checkDeleted() throws KernelException {
      if (this.isDeleted()) {
         throw new IllegalStateException("The destination " + this.name + " has been deleted");
      }
   }

   private void checkMask(int mask) throws KernelException {
      if ((mask & -16392) != 0) {
         throw new KernelException("Invalid mask for suspend/resume");
      }
   }

   public void suspend(int newMask) throws KernelException {
      this.checkMask(newMask);
      boolean wasActivated;
      synchronized(this) {
         wasActivated = this.activated;
         this.mask |= newMask;
         if ((newMask & 16384) != 0) {
            this.activated = false;
         }
      }

      if (wasActivated && !this.activated) {
         this.deactivate();
      }

   }

   public void resume(int resumeMask) throws KernelException {
      this.checkMask(resumeMask);
      boolean wasActivated;
      synchronized(this) {
         wasActivated = this.activated;
         this.mask &= ~resumeMask;
         if ((resumeMask & 16384) != 0) {
            this.activated = true;
         }
      }

      if (!wasActivated && this.activated) {
         this.activate();
      }

   }

   public int getMask() {
      return this.mask;
   }

   protected void activate() throws KernelException {
      this.kernel.checkOpened();
      PersistenceImpl persistence = this.kernel.getPersistence();
      if (this.isDurable() && this.storeHandle == null) {
         if (persistence == null) {
            throw new KernelException("Durable destinations not supported without a store");
         }

         persistence.createDestination(this);
      }

      if (this.defaultAssignSequenceName != null) {
         this.defaultAssignSequence = (SequenceImpl)this.findOrCreateSequence(this.defaultAssignSequenceName, 1);
      }

      if (this.defaultAssignSequence == null) {
         this.defaultSendOptions = DEFAULT_SEND_OPTIONS;
      } else {
         this.defaultSendOptions = new SendOptions();
         this.defaultSendOptions.setSequence(this.defaultAssignSequence);
      }

   }

   protected abstract void deactivate() throws KernelException;

   public boolean isSuspended(int mask) {
      return (this.mask & mask) != 0;
   }

   public boolean isCreated() {
      return this.created;
   }

   public synchronized void setFilter(Filter filter) {
      if (filter == null) {
         this.filter = new NullFilterImpl(this.kernel);
      } else {
         this.filter = filter;
      }

   }

   public synchronized Filter getFilter() {
      return this.filter;
   }

   public synchronized void setComparator(Comparator comparator) {
      this.comparator = comparator;
   }

   public synchronized Comparator getComparator() {
      return this.comparator;
   }

   public boolean isDurable() {
      return this.durable;
   }

   protected SendOptions initializeSendOptions(SendOptions options) {
      if (options == null) {
         return this.defaultSendOptions;
      } else {
         if (this.defaultAssignSequence != null && options.getSequence() == null) {
            options.setSequence(this.defaultAssignSequence);
         }

         return options;
      }
   }

   protected void checkDestinationState(Message message, SendOptions options) throws KernelException {
      if (message == null) {
         throw new IllegalArgumentException("Message is null");
      } else if (options.getTimeout() < 0L) {
         throw new IllegalArgumentException("Invalid timeout, " + options.getTimeout());
      } else {
         this.checkDeleted();
         if (this.isSuspended(16389)) {
            if (this.isSuspended(16384)) {
               throw new KernelException("Destination has not been activated");
            } else {
               throw new IllegalStateException("Destination is suspended");
            }
         } else if (message.size() <= (long)this.maximumMessageSize && message.size() <= (long)this.kernel.getMaximumMessageSize()) {
            if (options.isPersistent() && this.kernel.getPersistentStore() != null) {
               if (message.size() > (long)this.kernel.getMaximumWriteSize()) {
                  throw new QuotaException("Maximum message size exceeded - allowable size for store '" + this.kernel.getPersistentStore().getName() + "' is " + this.kernel.getMaximumWriteSize() + ", which is 1% (by default) of the region size that is configured for replicated store '" + this.kernel.getPersistentStore().getName() + "', and your message's size is " + message.size());
               }

               if (!this.isQuotaCheckEnabled() && this.kernel.isStoreOverloaded(message.size())) {
                  throw new QuotaException("Replicated store '" + this.kernel.getPersistentStore().getName() + "' is approaching its shared memory limit and cannot accept any persistent messages");
               }
            }

         } else {
            throw new QuotaException("Maximum message size exceeded - allowable size for kernel is " + this.kernel.getMaximumMessageSize() + ", and allowable size for destination is " + this.maximumMessageSize + " and your message's size is " + message.size());
         }
      }
   }

   protected SendRequest sendAllocateQuota(SendRequest sendRequest, MessageHandle handle, GXATransaction transaction, SendOptions options) throws KernelException {
      if (!this.isQuotaCheckEnabled()) {
         return null;
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Allocating quota for " + handle);
         }

         QuotaRequest quotaRequest = this.quota.allocate(handle, options.getTimeout());
         if (quotaRequest == null) {
            return null;
         } else {
            synchronized(quotaRequest) {
               if (quotaRequest.hasResult()) {
                  quotaRequest.getResult();
                  return null;
               } else {
                  handle.pin(this.kernel);
                  if (sendRequest == null) {
                     sendRequest = new SendRequest(this, handle, options);
                  }

                  sendRequest.setTransaction(transaction);
                  sendRequest.setState(1);
                  quotaRequest.addListener(sendRequest, this.kernel.getWorkManager());
                  return sendRequest;
               }
            }
         }
      }
   }

   protected void sendUndoQuota(MessageHandle handle) {
      if (this.isQuotaCheckEnabled()) {
         this.quota.free(handle);
      }

   }

   protected abstract SendRequest sendAddAndPersist(SendRequest var1, SendOptions var2, MessageHandle var3, GXATransaction var4, boolean var5);

   protected abstract void sendRedirected(Message var1, SendOptions var2, GXATransaction var3) throws KernelException;

   synchronized boolean isQuotaCheckEnabled() {
      return this.quota != null && this.quota.isEnabled();
   }

   protected synchronized void setQuota(QuotaImpl quota) throws KernelException {
      if (this.quota != null) {
         this.quota.removeDestination(this);
      }

      quota.addDestination(this);
      this.quota = quota;
   }

   protected synchronized void updateIgnoreExpiration(boolean ignore) {
      this.ignoreExpiration = ignore;
   }

   private void setStatisticsMode(String mode) throws KernelException {
      if (!this.isSuspended(16384)) {
         throw new KernelException("Statistics mode may only be changed before activation");
      } else {
         if (mode.equals("Full") && !(this.statistics instanceof StatisticsImpl)) {
            this.statistics = new StatisticsImpl(this.name, this.kernel, (AbstractStatistics)this.kernel.getStatistics());
         } else {
            if (!mode.equals("Bypass") || this.statistics instanceof BypassStatisticsImpl) {
               throw new KernelException("Invalid statistics mode \"" + mode + "\"");
            }

            this.statistics = new BypassStatisticsImpl(this.name, this.kernel, (AbstractStatistics)this.kernel.getStatistics());
         }

      }
   }

   public void setProperty(String name, Object object) throws KernelException {
      try {
         if (name.equals("Quota")) {
            this.setQuota((QuotaImpl)object);
         } else if (name.equals("MaximumMessageSize")) {
            int value = (Integer)object;
            if (value < 0) {
               throw new KernelException("Invalid value for MaximumMessageSize");
            }

            this.maximumMessageSize = value;
         } else if (name.equals("Durable")) {
            boolean newDurable = (Boolean)object;
            if (newDurable == this.durable) {
               return;
            }

            if (!this.isSuspended(16384)) {
               throw new KernelException("The Durable flag may only be set before activation");
            }

            this.durable = (Boolean)object;
         } else if (name.equals("RedirectionListener")) {
            this.redirectionListener = (RedirectionListener)object;
         } else if (name.equals("IgnoreExpiration")) {
            this.updateIgnoreExpiration((Boolean)object);
         } else if (name.equals("Logging")) {
            this.logMask = (Integer)object;
         } else if (name.equals("DefaultAssignSequence")) {
            this.defaultAssignSequenceName = (String)object;
         } else if (name.equals("StatisticsMode")) {
            this.setStatisticsMode((String)object);
         } else if (name.equals("SubscriptionMessagesLimit")) {
            this.subscriptionMsgsLimit = (Long)object;
         } else {
            if (!name.equals("AlternativeSAFQueueName")) {
               throw new KernelException("Unkown property name, " + name);
            }

            this.alternativeName = (String)object;
         }
      } catch (ClassCastException var4) {
         throw new KernelException("Invalid property value, " + name);
      }

      super.setProperty(name, object);
   }

   public Object getProperty(String name) {
      return name.equals("SubscriptionMessagesLimit") ? this.subscriptionMsgsLimit : super.getProperty(name);
   }

   public void delete(KernelRequest request) throws KernelException {
      if (this.defaultAssignSequence != null) {
         this.defaultAssignSequence.delete(true);
         this.kernel.getPersistence().deleteSequence(this.defaultAssignSequence);
      }

   }

   public void addListener(EventListener listener) {
      synchronized(this.listenerList) {
         this.listenerList.add(listener);
      }
   }

   public boolean removeListener(EventListener listener) {
      synchronized(this.listenerList) {
         return this.listenerList.remove(listener);
      }
   }

   void addEvent(List eventList) {
      this.eventManager.addEvent(eventList);
   }

   void addEvent(EventImpl event) {
      this.eventManager.addEvent(event);
   }

   void onEvent(Event event) {
      EventManager.onEvent(event, this.listenerList);
   }

   protected final void enlistOperation(GXATransaction transaction, GXAOperation operation) throws KernelException {
      GXAResource resource = this.kernel.getGXAResource();

      try {
         resource.addNewOperation(transaction, operation);
      } catch (PersistentStoreException var5) {
         throw new KernelException("Error enlisting operation", var5);
      }
   }

   public void writeToStore(ObjectOutput out, ObjectHandler handler) throws IOException {
      int flags = 1;
      if (this.deleted) {
         flags |= 256;
      }

      out.writeInt(flags);
      out.writeLong(this.serialNumber);
      out.writeUTF(this.name);
   }

   public void readFromStore(ObjectInput in, ObjectHandler handler, KernelImpl ignored) throws IOException {
      int flags = in.readInt();
      if ((flags & 255) != 1) {
         throw new IOException("External version mismatch");
      } else {
         this.deleted = (flags & 256) != 0;
         this.serialNumber = in.readLong();
         this.name = in.readUTF();
      }
   }

   public void setSAFImportedDestination(boolean isSAF) {
      this.isSAFImportedDestination = isSAF;
   }

   public boolean isSAFImportedDestination() {
      return this.isSAFImportedDestination;
   }

   public String getHashedBasedName(String groupName, String sequenceName) {
      if (this.isSAFImportedDestination() && !groupName.equals(sequenceName)) {
         int moduloHashcode;
         if (sequenceName.contains("%uV1@")) {
            moduloHashcode = sequenceName.indexOf("%uV1@");
            sequenceName = sequenceName.substring(0, moduloHashcode);
         }

         moduloHashcode = Math.abs(groupName.hashCode() % 257);
         String prefix;
         if (moduloHashcode < 10) {
            prefix = "%uV1@00";
         } else if (moduloHashcode < 100) {
            prefix = "%uV1@0";
         } else {
            prefix = "%uV1@";
         }

         return sequenceName + prefix + moduloHashcode;
      } else {
         return sequenceName;
      }
   }

   public boolean equals(Object o) {
      try {
         return this.name.equals(((DestinationImpl)o).name);
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeAttribute("name", this.name != null ? this.name : "");
      xsw.writeAttribute("maximumMessageSize", String.valueOf(this.maximumMessageSize));
      xsw.writeAttribute("serialNumber", String.valueOf(this.serialNumber));
      xsw.writeAttribute("persistentStoreHandle", this.storeHandle != null ? this.storeHandle.toString() : "");
      xsw.writeAttribute("isDeleted", String.valueOf(this.deleted));
      xsw.writeAttribute("isDurable", String.valueOf(this.durable));
      xsw.writeAttribute("filter", this.filter != null ? this.filter.toString() : "");
      xsw.writeAttribute("comparator", this.comparator != null ? this.comparator.toString() : "");
      xsw.writeAttribute("redirectionListener", this.redirectionListener != null ? this.redirectionListener.toString() : "");
      this.statistics.dump(imageSource, xsw);
      if (this.quota != null) {
         this.quota.dump(imageSource, xsw);
      }

   }
}
