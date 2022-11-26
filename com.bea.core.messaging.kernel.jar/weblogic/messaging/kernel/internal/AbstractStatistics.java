package weblogic.messaging.kernel.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.kernel.Statistics;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

abstract class AbstractStatistics implements Statistics {
   protected AbstractStatistics parent;
   protected KernelImpl kernel;
   protected String name;
   protected boolean isKernelStats = false;

   protected AbstractStatistics(String name, KernelImpl kernel, AbstractStatistics parent) {
      this.name = name;
      this.parent = parent;
      this.kernel = kernel;
   }

   void setParent(AbstractStatistics parent) {
      this.parent = parent;
   }

   AbstractStatistics getParent() {
      return this.parent;
   }

   public String getName() {
      return this.name;
   }

   protected abstract void incrementCurrent(long var1);

   protected abstract void decrementCurrent(long var1);

   protected abstract void incrementPending(long var1);

   protected abstract void decrementPending(long var1);

   protected abstract void incrementReceivedInternal(long var1);

   protected abstract void incrementSubscriptionLimitDeleted();

   public abstract void dump(MessagingKernelDiagnosticImageSource var1, XMLStreamWriter var2) throws XMLStreamException, DiagnosticImageTimeoutException;

   final void decrementCurrent(MessageReference element) {
      MessageHandle messageHandle = element.getMessageHandle();
      long bytes = messageHandle.size();
      this.decrementCurrent(bytes);
      AbstractStatistics parent = this.parent;
      if (parent != null && !parent.isKernelStats) {
         if (messageHandle.decrementLowCurrent()) {
            parent.decrementCurrent(bytes);
         }

         parent = parent.getParent();
      }

      if (parent != null && messageHandle.decrementHighCurrent()) {
         parent.decrementCurrent(bytes);
      }

   }

   final void incrementCurrent(MessageReference element) {
      MessageHandle messageHandle = element.getMessageHandle();
      long bytes = messageHandle.size();
      this.incrementCurrent(bytes);
      AbstractStatistics parent = this.parent;
      if (parent != null && !parent.isKernelStats) {
         if (messageHandle.incrementLowCurrent()) {
            parent.incrementCurrent(bytes);
         }

         parent = parent.getParent();
      }

      if (parent != null && messageHandle.incrementHighCurrent()) {
         parent.incrementCurrent(bytes);
      }

   }

   final void incrementReceived(MessageReference element) {
      MessageHandle messageHandle = element.getMessageHandle();
      long bytes = messageHandle.size();
      this.incrementReceivedInternal(bytes);
      AbstractStatistics parent = this.parent;
      if (parent != null) {
         if (messageHandle.incrementLowReceived()) {
            parent.incrementReceivedInternal(bytes);
         }

         parent = parent.getParent();
      }

      if (parent != null && messageHandle.incrementHighReceived()) {
         parent.incrementReceivedInternal(bytes);
      }

   }

   final void incrementReceived(long bytes) {
      this.incrementReceivedInternal(bytes);
      AbstractStatistics parent = this.parent;
      if (parent != null) {
         parent.incrementReceivedInternal(bytes);
         parent = parent.getParent();
      }

      if (parent != null) {
         parent.incrementReceivedInternal(bytes);
      }

   }

   final void decrementPending(MessageReference element) {
      MessageHandle messageHandle = element.getMessageHandle();
      long bytes = messageHandle.size();
      this.decrementPending(bytes);
      AbstractStatistics parent = this.parent;
      if (parent != null && !parent.isKernelStats) {
         if (messageHandle.decrementLowPending()) {
            parent.decrementPending(bytes);
         }

         parent = parent.getParent();
      }

      if (parent != null && messageHandle.decrementHighPending()) {
         parent.decrementPending(bytes);
      }

   }

   final void incrementPending(MessageReference element) {
      MessageHandle messageHandle = element.getMessageHandle();
      long bytes = messageHandle.size();
      this.incrementPending(bytes);
      AbstractStatistics parent = this.parent;
      if (parent != null && !parent.isKernelStats) {
         if (messageHandle.incrementLowPending()) {
            parent.incrementPending(bytes);
         }

         parent = parent.getParent();
      }

      if (parent != null && messageHandle.incrementHighPending()) {
         parent.incrementPending(bytes);
      }

   }
}
