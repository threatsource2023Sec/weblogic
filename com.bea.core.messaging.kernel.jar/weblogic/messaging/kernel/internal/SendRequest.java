package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Collection;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelListener;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.SendOptions;
import weblogic.security.subject.AbstractSubject;
import weblogic.store.PersistentStoreException;
import weblogic.store.gxa.GXATransaction;

final class SendRequest extends KernelRequest implements KernelListener, CompletionListener {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   public static final int QUOTA = 1;
   public static final int COMMIT = 3;
   private DestinationImpl destination;
   private MessageHandle handle;
   private SendOptions options;
   private MessageReference ref;
   private GXATransaction transaction;
   private AbstractSubject subject;
   private Collection matchedQueues;
   private Collection messageRefs;

   SendRequest(DestinationImpl dest, MessageHandle handle, SendOptions options) {
      this.destination = dest;
      this.handle = handle;
      this.options = options;
      this.subject = SecurityHelper.getCurrentSubject();
   }

   MessageHandle getMessageHandle() {
      return this.handle;
   }

   SendOptions getOptions() {
      return this.options;
   }

   MessageReference getMessageReference() {
      return this.ref;
   }

   void setMessageReference(MessageReference ref) {
      this.ref = ref;
   }

   String getSubjectName() {
      return SecurityHelper.getSubjectName(this.subject);
   }

   GXATransaction getTransaction() {
      return this.transaction;
   }

   void setTransaction(GXATransaction transaction) {
      this.transaction = transaction;
   }

   Collection getMatchedQueues() {
      return this.matchedQueues;
   }

   void setMatchedQueues(Collection matchedQueues) {
      this.matchedQueues = matchedQueues;
   }

   Collection getMessageReferences() {
      return this.messageRefs;
   }

   void addMessageReference(MessageReference ref) {
      if (this.messageRefs == null) {
         this.messageRefs = new ArrayList();
      }

      this.messageRefs.add(ref);
   }

   private void continueRequest() {
      assert this.handle.getPinCount() > 0;

      if (logger.isDebugEnabled()) {
         logger.debug("Resuming send of message " + this);
      }

      try {
         switch (this.getState()) {
            case 1:
               this.destination.sendAddAndPersist(this, this.options, this.handle, this.transaction, false);
               break;
            case 3:
               this.setResult((Object)null, false);
               break;
            default:
               this.failRequest(new AssertionError("Invalid request type"));
         }
      } finally {
         this.handle.unPin(this.destination.getKernelImpl());
      }

   }

   private void failRequest(Throwable throwable) {
      assert this.handle.getPinCount() > 0;

      if (logger.isDebugEnabled()) {
         logger.debug("Resuming send of message " + this + " after exception " + throwable);
      }

      switch (this.getState()) {
         case 1:
            this.setResult(throwable, false);
            break;
         case 3:
            if (throwable instanceof PersistentStoreException) {
               this.setResult(new KernelException("Error persisting message", throwable), false);
            } else {
               this.setResult(throwable, false);
            }
            break;
         default:
            this.setResult(throwable, false);
      }

   }

   public void onCompletion(KernelRequest request, Object result) {
      this.continueRequest();
   }

   public void onException(KernelRequest request, Throwable throwable) {
      this.failRequest(throwable);
   }

   public void onCompletion(CompletionRequest request, Object result) {
      this.continueRequest();
   }

   public void onException(CompletionRequest request, Throwable throwable) {
      this.failRequest(throwable);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ state = ");
      switch (this.getState()) {
         case 1:
            buf.append("QUOTA ");
            break;
         case 3:
            buf.append("COMMIT ");
            break;
         default:
            buf.append("UNKNOWN ");
      }

      buf.append("handle = ");
      buf.append(this.handle.toString());
      buf.append(" ]");
      return buf.toString();
   }
}
