package weblogic.messaging.kernel.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.kernel.Threshold;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

final class BypassStatisticsImpl extends AbstractStatistics {
   BypassStatisticsImpl(String name, KernelImpl kernel, AbstractStatistics parent) {
      super(name, kernel, parent);
   }

   protected void incrementCurrent(long size) {
   }

   protected void decrementCurrent(long size) {
   }

   protected void incrementPending(long size) {
   }

   protected void decrementPending(long size) {
   }

   protected void incrementReceivedInternal(long size) {
   }

   public long getBytesCurrent() {
      return 0L;
   }

   public long getBytesHigh() {
      return 0L;
   }

   public long getBytesLow() {
      return 0L;
   }

   public long getBytesPending() {
      return 0L;
   }

   public long getBytesReceived() {
      return 0L;
   }

   public int getMessagesCurrent() {
      return 0;
   }

   public int getMessagesHigh() {
      return 0;
   }

   public int getMessagesLow() {
      return 0;
   }

   public int getMessagesPending() {
      return 0;
   }

   public long getMessagesReceived() {
      return 0L;
   }

   public Threshold addByteThreshold(long low, long high) {
      throw new AssertionError("Not implemented");
   }

   public Threshold addMessageThreshold(long low, long high) {
      throw new AssertionError("Not implemented");
   }

   public void removeThreshold(Threshold threshold) {
      throw new AssertionError("Not implemented");
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("BypassStatistics");
      xsw.writeEndElement();
   }

   public long getSubscriptionLimitMessagesDeleted() {
      return 0L;
   }

   protected void incrementSubscriptionLimitDeleted() {
   }
}
