package weblogic.messaging.kernel.internal;

import javax.transaction.Synchronization;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;

public final class QuotaRequest extends KernelRequest implements NakedTimerListener, Synchronization {
   private QuotaImpl quota;
   private long bytes;
   private Timer timer;
   private boolean persistent;

   QuotaRequest(QuotaImpl quota, long bytes, boolean persistent) {
      this.quota = quota;
      this.bytes = bytes;
      this.persistent = persistent;
   }

   QuotaImpl getQuota() {
      return this.quota;
   }

   long getBytes() {
      return this.bytes;
   }

   Timer getTimer() {
      return this.timer;
   }

   void setTimer(Timer timer) {
      this.timer = timer;
   }

   boolean isPersistent() {
      return this.persistent;
   }

   public void timerExpired(Timer timer) {
      this.quota.timeout(this);
   }

   public void afterCompletion(int state) {
      if (state == 4) {
         this.quota.timeout(this);
      }

   }

   public void beforeCompletion() {
   }

   public void dump(MessagingKernelDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("QuotaRequest");
      xsw.writeAttribute("bytes", String.valueOf(this.bytes));
      xsw.writeEndElement();
   }
}
