package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaException;
import weblogic.messaging.kernel.QuotaPolicy;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.timers.Timer;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.collections.EmbeddedList;

public final class QuotaImpl implements Quota {
   private String name;
   private KernelImpl kernel;
   private QuotaPolicy policy;
   private int messagesMaximum;
   private int messagesCurrent;
   private long bytesMaximum;
   private long bytesCurrent;
   private boolean enableQuotaCheck;
   private HashMap destinations;
   private List requests;
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");

   public QuotaImpl(String name, KernelImpl kernel) {
      this.policy = QuotaPolicy.FIFO;
      this.messagesMaximum = Integer.MAX_VALUE;
      this.bytesMaximum = Long.MAX_VALUE;
      this.enableQuotaCheck = false;
      this.destinations = new HashMap(0);
      this.requests = null;
      this.name = name;
      this.kernel = kernel;
      this.requests = new EmbeddedList();
   }

   public String getName() {
      return this.name;
   }

   public Kernel getKernel() {
      return this.kernel;
   }

   public synchronized QuotaRequest allocate(MessageHandle handle, long timeout) throws KernelException {
      long bytes = handle.size();
      QuotaState state = null;
      if ((!this.haveRequests() || this.policy != QuotaPolicy.FIFO) && (state = this.available(bytes, handle.isPersistent())) == QuotaImpl.QuotaState.OK) {
         this.adjust(1, bytes);
         return null;
      } else if (timeout <= 0L) {
         if (state == QuotaImpl.QuotaState.OVERLOAD) {
            throw new QuotaException("Replicated store '" + this.kernel.getPersistentStore().getName() + "' is approaching its shared memory limit and cannot accept any persistent messages");
         } else {
            throw new QuotaException("Quota " + this.name + " exceeded: Request: " + bytes + " bytes. Quota: bytes = " + this.bytesCurrent + " / " + this.bytesMaximum + " messages = " + this.messagesCurrent + " / " + this.messagesMaximum + " policy = " + this.policy + " outstanding blocking request " + this.haveRequests());
         }
      } else {
         return this.add(new QuotaRequest(this, bytes, handle.isPersistent()), timeout);
      }
   }

   public void allocateNoCheck(MessageHandle handle, int refCount) {
      if (handle.adjustQuotaReferenceCount(refCount) == refCount) {
         this.adjust(1, handle.size());
      }

   }

   public void allocateNoCheck(MessageHandle handle) {
      this.allocateNoCheck(handle, 1);
   }

   synchronized void addDestination(DestinationImpl destination) {
      this.destinations.put(destination.getName(), destination);
   }

   synchronized void removeDestination(DestinationImpl destination) {
      this.destinations.remove(destination.getName());
   }

   public synchronized void free(MessageHandle handle) {
      if (handle.adjustQuotaReferenceCount(-1) == 0) {
         this.adjust(-1, -handle.size());
         if (this.haveRequests()) {
            this.check();
         }

      }
   }

   public synchronized void adjustDownWard(MessageHandle handle) {
      this.adjust(-1, -handle.size());
      if (this.haveRequests()) {
         this.check();
      }

   }

   public synchronized void adjustUpWard(MessageHandle handle) {
      this.adjust(1, handle.size());
   }

   public synchronized int getMessagesCurrent() {
      return this.messagesCurrent;
   }

   public synchronized long getBytesCurrent() {
      return this.bytesCurrent;
   }

   public synchronized boolean isEnabled() {
      return this.enableQuotaCheck;
   }

   public void setBytesMaximum(long bytesMaximum) {
      Iterator itr = null;
      if (bytesMaximum < 0L) {
         throw new IllegalArgumentException("Value is negative");
      } else {
         synchronized(this) {
            long diff = bytesMaximum - this.bytesMaximum;
            this.bytesMaximum = bytesMaximum;
            if (diff > 0L && this.haveRequests()) {
               this.check();
            }

            if (this.bytesMaximum == Long.MAX_VALUE && this.messagesMaximum == Integer.MAX_VALUE) {
               this.messagesCurrent = 0;
               this.bytesCurrent = 0L;
               this.enableQuotaCheck = false;
            } else if (!this.enableQuotaCheck) {
               this.enableQuotaCheck = true;
               itr = ((HashMap)this.destinations.clone()).values().iterator();
            }
         }

         if (itr != null) {
            while(itr.hasNext()) {
               DestinationImpl destination = (DestinationImpl)itr.next();
               if (destination instanceof QueueImpl) {
                  ((QueueImpl)destination).enableQuotaCheck();
               }
            }
         }

      }
   }

   public synchronized long getBytesMaximum() {
      return this.bytesMaximum;
   }

   public void setMessagesMaximum(int messagesMaximum) {
      Iterator itr = null;
      if (messagesMaximum < 0) {
         throw new IllegalArgumentException("Value is negative");
      } else {
         synchronized(this) {
            int diff = messagesMaximum - this.messagesMaximum;
            this.messagesMaximum = messagesMaximum;
            if (diff > 0 && this.haveRequests()) {
               this.check();
            }

            if (this.bytesMaximum == Long.MAX_VALUE && this.messagesMaximum == Integer.MAX_VALUE) {
               this.messagesCurrent = 0;
               this.bytesCurrent = 0L;
               this.enableQuotaCheck = false;
            } else if (!this.enableQuotaCheck) {
               this.enableQuotaCheck = true;
               itr = ((HashMap)this.destinations.clone()).values().iterator();
            }
         }

         if (itr != null) {
            while(itr.hasNext()) {
               DestinationImpl destination = (DestinationImpl)itr.next();
               if (destination instanceof QueueImpl) {
                  ((QueueImpl)destination).enableQuotaCheck();
               }
            }
         }

      }
   }

   public synchronized int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public synchronized void setPolicy(QuotaPolicy policy) {
      this.policy = policy;
      if (this.haveRequests()) {
         this.check();
      }

   }

   public synchronized QuotaPolicy getPolicy() {
      return this.policy;
   }

   private QuotaState available(long bytes, boolean isPersistent) {
      if (logger.isDebugEnabled()) {
         logger.debug("Messaging kernel available: isPersistent = " + isPersistent + " bytes = " + bytes + " isStoreOverloaded = " + this.kernel.isStoreOverloaded(bytes) + " maximumWriteSize = " + this.kernel.getMaximumWriteSize() + " messagesCurrent = " + this.messagesCurrent + " bytesCurrent = " + this.bytesCurrent + " messagesMaximum = " + this.messagesMaximum + " bytesMaximum = " + this.bytesMaximum);
      }

      if (this.messagesCurrent >= this.messagesMaximum) {
         return QuotaImpl.QuotaState.QUOTA;
      } else if (this.bytesCurrent + bytes > this.bytesMaximum) {
         return QuotaImpl.QuotaState.QUOTA;
      } else {
         return isPersistent && this.kernel.isStoreOverloaded(bytes) ? QuotaImpl.QuotaState.OVERLOAD : QuotaImpl.QuotaState.OK;
      }
   }

   void adjust(int messages, long bytes) {
      this.messagesCurrent += messages;
      this.bytesCurrent += bytes;
   }

   private QuotaRequest add(QuotaRequest request, long timeout) throws KernelException {
      assert Thread.holdsLock(this);

      this.requests.add(request);
      if (timeout != Long.MAX_VALUE) {
         Timer timer = this.kernel.getLimitedTimerManager().schedule(request, timeout);
         request.setTimer(timer);
      }

      Transaction tran = TransactionHelper.getTransactionHelper().getTransaction();
      if (tran != null) {
         try {
            tran.registerSynchronization(request);
         } catch (RollbackException var6) {
            this.timeout(request);
         } catch (IllegalStateException var7) {
            this.timeout(request);
         } catch (SystemException var8) {
            throw new KernelException("Unable to enlist transaction to block for quota", var8);
         }
      }

      return request;
   }

   private boolean haveRequests() {
      return !this.requests.isEmpty();
   }

   private void check() {
      Iterator iterator = this.requests.iterator();

      while(iterator.hasNext()) {
         QuotaRequest request = (QuotaRequest)iterator.next();
         if (this.available(request.getBytes(), request.isPersistent()) == QuotaImpl.QuotaState.OK) {
            Timer timer = request.getTimer();
            if (timer != null) {
               timer.cancel();
            }

            iterator.remove();
            this.adjust(1, request.getBytes());
            request.setResult((Object)null);
         } else if (this.policy == QuotaPolicy.FIFO) {
            break;
         }
      }

   }

   synchronized void timeout(QuotaRequest request) {
      if (this.requests.contains(request)) {
         boolean first = request == this.requests.get(0);
         this.requests.remove(request);
         if (this.available(request.getBytes(), request.isPersistent()) == QuotaImpl.QuotaState.OK) {
            this.adjust(1, request.getBytes());
            request.setResult((Object)null);
         } else {
            request.setResult(new QuotaException("Quota blocking time exceeded and no quota available"));
         }

         if (first && this.policy == QuotaPolicy.FIFO && this.haveRequests()) {
            this.check();
         }
      }

   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Quota");
      xsw.writeAttribute("name", this.name != null ? this.name : "");
      xsw.writeAttribute("quotaPolicy", this.policy != null ? this.policy.toString() : "");
      xsw.writeAttribute("messagesMaximum", String.valueOf(this.messagesMaximum));
      xsw.writeAttribute("messagesCurrent", String.valueOf(this.messagesCurrent));
      xsw.writeAttribute("bytesMaximum", String.valueOf(this.bytesMaximum));
      xsw.writeAttribute("bytesCurrent", String.valueOf(this.bytesCurrent));
      long bytes = 0L;
      int count = 0;
      Iterator it = this.requests.iterator();
      List tempRequests = new ArrayList();

      QuotaRequest req;
      while(it.hasNext()) {
         req = (QuotaRequest)it.next();
         bytes += req.getBytes();
         ++count;
         tempRequests.add(req);
      }

      xsw.writeAttribute("quotaRequestCount", String.valueOf(count));
      xsw.writeAttribute("quotaRequestBytesTotalCount", String.valueOf(bytes));
      it = tempRequests.iterator();

      while(it.hasNext()) {
         req = (QuotaRequest)it.next();
         req.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
   }

   private static enum QuotaState {
      OK,
      QUOTA,
      OVERLOAD;
   }
}
