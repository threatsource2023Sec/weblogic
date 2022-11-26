package weblogic.iiop.messages;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.RequestTimeoutException;

public abstract class SequencedRequestMessage extends SequencedMessage {
   private static final DebugLogger debugIIOPTransport = DebugLogger.getDebugLogger("DebugIIOPTransport");
   private Throwable t;
   protected int flags = 0;
   private static final int DEFAULT_IIOP_REQUEST_TIMEOUT = 180000;
   private static int DEFAULT_TIMEOUT = Integer.getInteger("weblogic.iiop.requestTimeout", 180000);
   private long timeout;
   private ReplyNotification notification;

   public SequencedRequestMessage(MessageHeader msgHdr) {
      super(msgHdr);
      this.timeout = (long)DEFAULT_TIMEOUT;
      this.notification = new SynchronousNotification(this);
   }

   protected SequencedRequestMessage(MessageHeader msgHdr, CorbaInputStream inputStream) {
      super(msgHdr, inputStream);
      this.timeout = (long)DEFAULT_TIMEOUT;
      this.notification = new SynchronousNotification(this);
   }

   public SequencedRequestMessage(int messageType, int minorVersion, int request_id) {
      super(messageType, minorVersion, request_id);
      this.timeout = (long)DEFAULT_TIMEOUT;
      this.notification = new SynchronousNotification(this);
   }

   public final void setTimeout(long timeout) {
      if (timeout > 0L) {
         this.timeout = timeout;
      }

   }

   public final SequencedMessage getReply() {
      return this.notification.getReply();
   }

   public final int getFlags() {
      return this.flags;
   }

   public void setNotification(ReplyNotification notification) {
      this.notification = notification;
   }

   public synchronized void notify(SequencedMessage reply) {
      this.notification.notify(reply);
   }

   public final synchronized void notify(Throwable t) {
      this.t = t;
      this.notify();
   }

   public final synchronized void waitForData() throws Throwable {
      String originalThreadName = null;

      try {
         if (KernelStatus.DEBUG && debugIIOPTransport.isDebugEnabled()) {
            originalThreadName = Thread.currentThread().getName();
            Thread.currentThread().setName(originalThreadName + " [waiting for request " + this.request_id + "] for " + this.timeout + " millis");
         }

         this.waitForData0();
      } finally {
         if (originalThreadName != null) {
            Thread.currentThread().setName(originalThreadName);
         }

      }

   }

   private void renameCurrentThread(String threadName) {
      Thread.currentThread().setName(threadName);
   }

   protected void waitForData0() throws Throwable {
      while(this.noReply()) {
         try {
            this.wait(this.timeout);
            if (this.timeout > 0L && this.noReply()) {
               this.t = new RequestTimeoutException("Response timed out after: '" + this.timeout + "' milliseconds.");
            }
         } catch (InterruptedException var2) {
         }
      }

      if (this.t != null) {
         throw this.t;
      }
   }

   public final synchronized boolean pollResponse() {
      return !this.noReply();
   }

   protected final long getTimeout() {
      return this.timeout;
   }

   private boolean noReply() {
      return this.getReply() == null && this.t == null;
   }
}
