package weblogic.messaging.kernel.internal;

import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.kernel.Threshold;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicInteger;
import weblogic.utils.concurrent.atomic.AtomicLong;

class StatisticsImpl extends AbstractStatistics {
   private final AtomicInteger messagesCurrent = AtomicFactory.createAtomicInteger();
   private final AtomicInteger messagesHigh = AtomicFactory.createAtomicInteger();
   private final AtomicInteger messagesLow = AtomicFactory.createAtomicInteger();
   private final AtomicInteger messagesPending = AtomicFactory.createAtomicInteger();
   private final AtomicLong messagesReceived = AtomicFactory.createAtomicLong();
   private final AtomicLong bytesCurrent = AtomicFactory.createAtomicLong();
   private final AtomicLong bytesHigh = AtomicFactory.createAtomicLong();
   private final AtomicLong bytesLow = AtomicFactory.createAtomicLong();
   private final AtomicLong bytesPending = AtomicFactory.createAtomicLong();
   private final AtomicLong bytesReceived = AtomicFactory.createAtomicLong();
   private final AtomicLong subscriptionLimitMessagesDeleted = AtomicFactory.createAtomicLong();
   private final EmbeddedList thresholds = new EmbeddedList();
   private volatile boolean hasThresholds;

   StatisticsImpl(String name, KernelImpl kernel, AbstractStatistics parent) {
      super(name, kernel, parent);
   }

   public long getBytesCurrent() {
      return this.bytesCurrent.get();
   }

   public long getBytesHigh() {
      return this.bytesHigh.get();
   }

   public long getBytesLow() {
      return this.bytesLow.get();
   }

   public long getBytesPending() {
      return this.bytesPending.get();
   }

   public long getBytesReceived() {
      return this.bytesReceived.get();
   }

   public int getMessagesCurrent() {
      return this.messagesCurrent.get();
   }

   public int getMessagesHigh() {
      return this.messagesHigh.get();
   }

   public int getMessagesLow() {
      return this.messagesLow.get();
   }

   public int getMessagesPending() {
      return this.messagesPending.get();
   }

   public long getMessagesReceived() {
      return this.messagesReceived.get();
   }

   public long getSubscriptionLimitMessagesDeleted() {
      return this.subscriptionLimitMessagesDeleted.get();
   }

   protected void incrementSubscriptionLimitDeleted() {
      this.subscriptionLimitMessagesDeleted.incrementAndGet();
   }

   protected final void incrementCurrent(long bytes) {
      int newMessages = this.messagesCurrent.incrementAndGet();

      while(newMessages > this.messagesHigh.get()) {
         this.messagesHigh.set(newMessages);
      }

      long newBytes = this.bytesCurrent.addAndGet(bytes);

      while(newBytes > this.bytesHigh.get()) {
         this.bytesHigh.set(newBytes);
      }

      if (this.hasThresholds) {
         this.checkThresholdsHigh();
      }

   }

   protected final void decrementCurrent(long bytes) {
      int newMessages = this.messagesCurrent.decrementAndGet();

      while(newMessages < this.messagesLow.get()) {
         this.messagesLow.set(newMessages);
      }

      long newBytes = this.bytesCurrent.addAndGet(-bytes);

      while(newBytes < this.bytesLow.get()) {
         this.bytesLow.set(newBytes);
      }

      if (this.hasThresholds) {
         this.checkThresholdsLow();
      }

   }

   protected final void incrementPending(long bytes) {
      this.messagesPending.incrementAndGet();
      this.bytesPending.addAndGet(bytes);
   }

   protected final void decrementPending(long bytes) {
      this.messagesPending.decrementAndGet();
      this.bytesPending.addAndGet(-bytes);
   }

   protected final void incrementReceivedInternal(long bytes) {
      this.messagesReceived.incrementAndGet();
      this.bytesReceived.addAndGet(bytes);
   }

   public Threshold addMessageThreshold(long high, long low) {
      ThresholdImpl threshold = new ThresholdMessages(this, high, low, this.kernel.getLimitedWorkManager());
      this.addThreshold(threshold);
      return threshold;
   }

   public Threshold addByteThreshold(long high, long low) {
      ThresholdImpl threshold = new ThresholdBytes(this, high, low, this.kernel.getLimitedWorkManager());
      this.addThreshold(threshold);
      return threshold;
   }

   private void addThreshold(ThresholdImpl threshold) {
      if (threshold.getLowThreshold() >= 0L && threshold.getHighThreshold() >= 0L) {
         if (threshold.getLowThreshold() > threshold.getHighThreshold()) {
            throw new IllegalArgumentException("Inverted threshold");
         } else {
            synchronized(this) {
               this.thresholds.add(threshold);
               threshold.checkThresholdHigh();
               this.hasThresholds = true;
            }
         }
      } else {
         throw new IllegalArgumentException("Negative threshold");
      }
   }

   public synchronized void removeThreshold(Threshold threshold) {
      if (this.thresholds.contains(threshold)) {
         this.thresholds.remove(threshold);
         if (this.thresholds.isEmpty()) {
            this.hasThresholds = false;
         }

      }
   }

   private synchronized void checkThresholdsLow() {
      Iterator i = this.thresholds.iterator();

      while(i.hasNext()) {
         ((ThresholdImpl)i.next()).checkThresholdLow();
      }

   }

   private synchronized void checkThresholdsHigh() {
      Iterator i = this.thresholds.iterator();

      while(i.hasNext()) {
         ((ThresholdImpl)i.next()).checkThresholdHigh();
      }

   }

   public static void printStackTrace(int depth, String string) {
      Exception exception = new Exception(string);
      StackTraceElement[] ste1 = exception.getStackTrace();
      if (depth > ste1.length - 1) {
         depth = ste1.length - 1;
      }

      StackTraceElement[] ste2 = new StackTraceElement[depth];

      for(int i = 0; i < depth; ++i) {
         ste2[i] = ste1[i + 1];
      }

      exception.setStackTrace(ste2);
      exception.printStackTrace();
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Statistics");
      xsw.writeAttribute("messageCurrent", String.valueOf(this.messagesCurrent));
      xsw.writeAttribute("messagesHigh", String.valueOf(this.messagesHigh));
      xsw.writeAttribute("messagesLow", String.valueOf(this.messagesLow));
      xsw.writeAttribute("messagesPending", String.valueOf(this.messagesPending));
      xsw.writeAttribute("messagesReceived", String.valueOf(this.messagesReceived));
      xsw.writeAttribute("bytesCurrent", String.valueOf(this.bytesCurrent));
      xsw.writeAttribute("bytesHigh", String.valueOf(this.bytesHigh));
      xsw.writeAttribute("bytesLow", String.valueOf(this.bytesLow));
      xsw.writeAttribute("bytesPending", String.valueOf(this.bytesPending));
      xsw.writeAttribute("bytesReceived", String.valueOf(this.bytesReceived));
      xsw.writeEndElement();
   }
}
