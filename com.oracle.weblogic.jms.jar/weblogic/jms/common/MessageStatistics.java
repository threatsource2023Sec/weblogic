package weblogic.jms.common;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public final class MessageStatistics {
   private long messagesPendingCount;
   private long messagesSentCount;
   private long messagesReceivedCount;
   private long bytesPendingCount;
   private long bytesSentCount;
   private long bytesReceivedCount;

   public final synchronized long getMessagesPendingCount() {
      return this.messagesPendingCount;
   }

   public final synchronized long getMessagesReceivedCount() {
      return this.messagesReceivedCount;
   }

   public final synchronized long getMessagesSentCount() {
      return this.messagesSentCount;
   }

   public final synchronized long getBytesPendingCount() {
      return this.bytesPendingCount;
   }

   public final synchronized long getBytesReceivedCount() {
      return this.bytesReceivedCount;
   }

   public final synchronized long getBytesSentCount() {
      return this.bytesSentCount;
   }

   public final synchronized void incrementPendingCount(long messageSize) {
      ++this.messagesPendingCount;
      this.bytesPendingCount += messageSize;
   }

   public final void incrementPendingCount(MessageImpl message) {
      long bytesCount = message.getPayloadSize() + (long)message.getUserPropertySize();
      synchronized(this) {
         ++this.messagesPendingCount;
         this.bytesPendingCount += bytesCount;
      }
   }

   public final void decrementPendingCount(long bytesCount) {
      synchronized(this) {
         --this.messagesPendingCount;
         this.bytesPendingCount -= bytesCount;
      }
   }

   public final synchronized void incrementReceivedCount(long messageSize) {
      ++this.messagesReceivedCount;
      this.bytesReceivedCount += messageSize;
   }

   public final synchronized void incrementReceivedCount(MessageImpl message) {
      ++this.messagesReceivedCount;
      this.bytesReceivedCount += message.getPayloadSize();
      this.bytesReceivedCount += (long)message.getUserPropertySize();
   }

   public final synchronized void incrementSentCount(long messageSize) {
      ++this.messagesSentCount;
      this.bytesSentCount += messageSize;
   }

   public final synchronized void incrementSentCount(MessageImpl message) {
      ++this.messagesSentCount;
      this.bytesSentCount += message.getPayloadSize();
      this.bytesSentCount += (long)message.getUserPropertySize();
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Statistics");
      xsw.writeAttribute("messagesPendingCount", String.valueOf(this.messagesPendingCount));
      xsw.writeAttribute("messagesSentCount", String.valueOf(this.messagesSentCount));
      xsw.writeAttribute("messagesReceivedCount", String.valueOf(this.messagesReceivedCount));
      xsw.writeAttribute("bytesPendingCount", String.valueOf(this.bytesPendingCount));
      xsw.writeAttribute("bytesSentCount", String.valueOf(this.bytesSentCount));
      xsw.writeAttribute("bytesReceivedCount", String.valueOf(this.bytesReceivedCount));
      xsw.writeEndElement();
   }
}
