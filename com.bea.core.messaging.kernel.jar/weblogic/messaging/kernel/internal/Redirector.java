package weblogic.messaging.kernel.internal;

import javax.transaction.xa.Xid;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.Message;
import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.internal.events.EventImpl;
import weblogic.messaging.kernel.internal.events.MessageExpirationEventImpl;
import weblogic.messaging.kernel.internal.events.MessageRedeliveryLimitEventImpl;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.utils.collections.CircularQueue;

class Redirector implements Runnable, NakedTimerListener {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private static final long FAILURE_DELAY = 10000L;
   private static final long REDIRECT_SUCCESS = -1L;
   private QueueImpl sourceQueue;
   private final CircularQueue redirectList = new CircularQueue(16);
   private boolean running;
   private boolean stopped = true;

   Redirector(QueueImpl sourceQueue) {
      this.sourceQueue = sourceQueue;
   }

   synchronized void scheduleRedirection(MessageReference ref) {
      if (logger.isDebugEnabled()) {
         logger.debug("Scheduling for redirection message " + ref + " msg=" + ref.getMessageHandle().getMessage());
      }

      if ((ref.getState() & 1024) == 0) {
         ref.setState(1024);
         this.redirectList.add(ref);
         this.sourceQueue.removeFromGroup(ref);
         this.startRedirecting();
      }
   }

   private void startRedirecting() {
      assert Thread.holdsLock(this);

      if (!this.running && !this.stopped && !this.redirectList.isEmpty()) {
         this.running = true;
         this.sourceQueue.getKernelImpl().getWorkManager().schedule(this);
      }

   }

   synchronized void start() {
      this.stopped = false;
      this.startRedirecting();
   }

   synchronized void stop() {
      this.stopped = true;
   }

   public void run() {
      while(true) {
         MessageReference element;
         synchronized(this) {
            element = (MessageReference)this.redirectList.peek();
            if (element == null) {
               this.running = false;
               return;
            }
         }

         long failureDelay;
         try {
            failureDelay = this.redirectMessage(element);
         } catch (KernelException var13) {
            MessagingLogger.logFatalRedirectionError(this.getName());
            synchronized(this) {
               this.running = false;
               this.stopped = true;
               return;
            }
         }

         if (failureDelay < 0L) {
            synchronized(this) {
               assert this.redirectList.peek() == element;

               this.redirectList.remove();
            }

            synchronized(this) {
               if (!this.stopped && !this.redirectList.isEmpty()) {
                  if (this.sourceQueue.getKernelImpl().getWorkManager().scheduleIfBusy(this)) {
                     return;
                  }
                  continue;
               }

               this.running = false;
               return;
            }
         }

         synchronized(this) {
            this.running = false;
            this.sourceQueue.getKernelImpl().getLimitedTimerManager().schedule(this, failureDelay);
            if (logger.isDebugEnabled()) {
               logger.debug("Message redirection has suspended for " + failureDelay + " milliseconds");
            }

            return;
         }
      }
   }

   private long redirectMessage(MessageReference ref) throws KernelException {
      MessageHandle handle = ref.getMessageHandle();
      Message redirectedMessage = null;
      DestinationImpl redirectDestination = null;
      SendOptions redirectOptions = null;
      RedirectionListener listener = this.sourceQueue.getRedirectionListener();
      boolean expiration = false;
      boolean alreadyReported = false;
      boolean countExceeded = false;
      if (ref.isExpired()) {
         expiration = true;
         synchronized(handle) {
            alreadyReported = handle.isExpirationReported();
            handle.setExpirationReported(true);
         }
      } else if (ref.isRedeliveryCountExceeded()) {
         countExceeded = true;
      }

      handle.pin(this.sourceQueue.getKernelImpl());

      long var18;
      try {
         if (listener != null) {
            RedirectionInfoImpl info = new RedirectionInfoImpl(ref);
            if (!expiration && countExceeded) {
               listener.deliveryLimitReached(info);
               redirectedMessage = this.sourceQueue.replaceMessage(ref);
               if (redirectedMessage != null) {
                  redirectedMessage = redirectedMessage.duplicate();
               } else {
                  redirectedMessage = info.getMessageCopy();
               }
            } else {
               listener.expirationTimeReached(info, alreadyReported);
               redirectedMessage = info.getMessageCopy();
            }

            long var11;
            if (info.getRedeliveryDelay() >= 0L) {
               var11 = info.getRedeliveryDelay();
               return var11;
            }

            redirectDestination = (DestinationImpl)info.getRedirectDestination();
            if (redirectDestination != null && !redirectDestination.isActive() || redirectDestination == null && info.getRedirectDestinationName() != null) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Redirect destination " + redirectDestination + ", " + info.getRedirectDestinationName() + " is NOT active yet, DELAY redirect for " + handle.getMessage());
               }

               this.clearSetExpirationReported(handle, expiration, alreadyReported);
               var11 = 10000L;
               return var11;
            }

            redirectOptions = info.getSendOptions();
         }

         if (!expiration && !countExceeded) {
            var18 = -1L;
            return var18;
         }

         if (redirectDestination == null) {
            if (this.destroyMessage(ref, expiration)) {
               var18 = -1L;
               return var18;
            }

            this.clearSetExpirationReported(handle, expiration, alreadyReported);
            var18 = 10000L;
            return var18;
         }

         if (redirectOptions == null) {
            redirectOptions = ref.getMessageHandle().createSendOptions();
         }

         if (this.redirectMessage(ref, redirectDestination, redirectedMessage, redirectOptions, expiration)) {
            var18 = -1L;
            return var18;
         }

         this.clearSetExpirationReported(handle, expiration, alreadyReported);
         var18 = 10000L;
      } finally {
         handle.unPin(this.sourceQueue.getKernelImpl());
      }

      return var18;
   }

   private void clearSetExpirationReported(MessageHandle handle, boolean expiration, boolean alreadyReported) {
      if (expiration && !alreadyReported) {
         synchronized(handle) {
            handle.setExpirationReported(false);
         }
      }

   }

   private boolean destroyMessage(MessageReference element, boolean expiration) {
      if (!element.isOnMessageList()) {
         return true;
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Removing dead message " + element + ", " + element.getMessageHandle().getMessage());
         }

         EventImpl event = null;
         if ((this.sourceQueue.getLogMask() & 2) != 0) {
            MessageHandle handle = element.getMessageHandle();

            assert handle.getPinCount() > 0;

            if (expiration) {
               event = new MessageExpirationEventImpl(SecurityHelper.getCurrentSubjectName(), this.sourceQueue, handle.getMessage(), (Xid)null, element.getDeliveryCount(), handle.getExpirationTime());
            } else {
               event = new MessageRedeliveryLimitEventImpl(SecurityHelper.getCurrentSubjectName(), this.sourceQueue, handle.getMessage(), (Xid)null, handle.getRedeliveryLimit(), element.getDeliveryCount());
            }
         }

         if (this.sourceQueue.isDurable() && element.getMessageHandle().isPersistent()) {
            label143: {
               MultiPersistenceHandle mph = null;
               PersistentStoreTransaction storeTran = null;

               boolean var7;
               try {
                  PersistenceImpl persistence = this.sourceQueue.getKernelImpl().getPersistence();
                  storeTran = persistence.startStoreTransaction();
                  if (element instanceof MultiMessageReference) {
                     mph = ((MultiMessageReference)element).getPersistenceHandle();
                     if (mph != null) {
                        mph.lock(storeTran);
                     }
                  }

                  persistence.deleteMessage(storeTran, element);
                  storeTran.commit();
                  break label143;
               } catch (PersistentStoreException var11) {
                  MessagingLogger.logDeleteError(this.getName(), var11.toString(), var11);
                  var7 = false;
               } finally {
                  if (mph != null) {
                     mph.unlock(storeTran);
                  }

               }

               return var7;
            }
         }

         this.sourceQueue.remove(element);
         if (event != null) {
            this.sourceQueue.addEvent((EventImpl)event);
         }

         return true;
      }
   }

   private boolean redirectMessage(MessageReference element, DestinationImpl targetDestination, Message newMessage, SendOptions sendOptions, boolean expiration) throws KernelException {
      if (logger.isDebugEnabled()) {
         logger.debug("Redirecting message " + element + ", handle " + element.getMessageHandle() + ", " + element.getMessageHandle().getMessage() + " from queue " + this.sourceQueue.getName() + " to destination " + targetDestination.getName());
      }

      GXALocalTransaction tran = this.sourceQueue.getKernelImpl().startLocalGXATransaction();
      MessageHandle handle = element.getMessageHandle();
      Message redirectMessage = newMessage;
      if (newMessage == null) {
         redirectMessage = handle.getMessage();
      }

      try {
         try {
            if (element.isOnMessageList()) {
               ReceiveOperation deleteOp = new ReceiveOperation(expiration ? 3 : 4, this.sourceQueue, element, (String)null, (RedeliveryParameters)null, this.sourceQueue.getKernelImpl(), true, false);
               tran.getGXAResource().addNewOperation(tran, deleteOp);
            }

            targetDestination.sendRedirected(redirectMessage, sendOptions, tran);
         } catch (KernelException var10) {
            this.handleRedirectFailure(tran, var10);
            return false;
         } catch (GXAException var11) {
            this.handleRedirectFailure(tran, var11);
            return false;
         }

         tran.commit();
      } catch (GXAException var12) {
         MessagingLogger.logRedirectionError(this.getName(), var12.toString(), var12);
         throw new KernelException("Error redirecting messages", var12);
      }

      logger.debug("Message was successfully redirected");
      return true;
   }

   private void handleRedirectFailure(GXALocalTransaction tran, Exception ex) {
      MessagingLogger.logRedirectionError(this.getName(), ex.toString(), ex);
      tran.rollback();
   }

   private String getName() {
      return this.sourceQueue.getKernelImpl().getName() + '/' + this.sourceQueue.getName();
   }

   public synchronized void timerExpired(Timer t) {
      this.startRedirecting();
   }

   private static final class RedirectionInfoImpl implements RedirectionListener.Info {
      private MessageElement element;
      private Message messageCopy;
      private Destination redirectDestination;
      private long redeliveryDelay = -1L;
      private SendOptions sendOptions;
      private String redirectDestinationName;

      RedirectionInfoImpl(MessageReference ref) {
         assert ref.getMessageHandle().getPinCount() > 0;

         this.element = new MessageElementImpl(ref, ref.getMessageHandle().getMessage());
      }

      public MessageElement getMessageElement() {
         return this.element;
      }

      public Message getMessage() {
         if (this.messageCopy == null) {
            this.messageCopy = this.element.getMessage().duplicate();
         }

         return this.messageCopy;
      }

      Message getMessageCopy() {
         return this.messageCopy;
      }

      public void setRedirectDestination(Destination dest) {
         this.redirectDestination = dest;
      }

      Destination getRedirectDestination() {
         return this.redirectDestination;
      }

      public void setSendOptions(SendOptions options) {
         this.sendOptions = options;
      }

      SendOptions getSendOptions() {
         return this.sendOptions;
      }

      public void setRedeliveryDelay(long delay) {
         this.redeliveryDelay = delay;
      }

      long getRedeliveryDelay() {
         return this.redeliveryDelay;
      }

      public void setRedirectDestinationName(String destName) {
         this.redirectDestinationName = destName;
      }

      String getRedirectDestinationName() {
         return this.redirectDestinationName;
      }
   }
}
