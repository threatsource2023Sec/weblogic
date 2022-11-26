package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelListener;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.UOWCallback;
import weblogic.messaging.kernel.UOWCallbackCaller;
import weblogic.messaging.kernel.UOWCallbackFactory;
import weblogic.messaging.kernel.internal.events.UnitOfWorkEventImpl;
import weblogic.store.PersistentStoreRuntimeException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXATransaction;

public final class UOWSequenceImpl extends SequenceImpl implements Runnable, UOWCallbackCaller, KernelListener {
   private static UOWCallbackFactory callbackFactory = null;
   private UOWCallback callback = null;
   private List refs = new ArrayList();
   private List deleteMe;
   private Message messageToBeReplaced = null;
   private LinkedList assigned = new LinkedList();
   private boolean expiration = false;
   private Message oneBigMessage = null;

   public UOWSequenceImpl() {
   }

   protected UOWSequenceImpl(String name, int sequenceMode, long id, QueueImpl queue) {
      super(name, sequenceMode, id, queue);

      assert (sequenceMode & 8) != 0;

      String qname;
      if (queue.getName().startsWith("_weblogic.messaging.SequencingQueue.")) {
         qname = queue.getName().substring("_weblogic.messaging.SequencingQueue.".length());
      } else {
         qname = queue.getName();
      }

      this.callback = callbackFactory.create(this, qname);
   }

   public static void setCallbackFactory(UOWCallbackFactory paramCallbackFactory) {
      callbackFactory = paramCallbackFactory;
   }

   boolean requiresUpdate() {
      return true;
   }

   private void initCallback() {
      assert callbackFactory != null;

      assert this.queue != null;

      if (this.callback == null) {
         this.callback = callbackFactory.create(this, this.queue.getName());
      }

   }

   List getAssignedMessages(int clearedState) {
      ListIterator iter;
      synchronized(this) {
         if (this.assigned.isEmpty()) {
            return null;
         }

         iter = ((LinkedList)((LinkedList)this.assigned.clone())).listIterator();
      }

      List ret = null;

      while(iter.hasNext()) {
         MessageReference ref = (MessageReference)iter.next();
         if ((ref.getState() & clearedState) == 0) {
            if (ref.getMessageHandle().getMessage() == this.getMessageToBeReplaced()) {
               ref.clearState(768);
            }

            if (ret == null) {
               ret = new LinkedList();
            }

            ret.add(ref);
            synchronized(this) {
               this.assigned.remove(ref);
            }
         }
      }

      return ret;
   }

   public synchronized Message replaceMessage(Message message) {
      this.initCallback();
      if (message == this.messageToBeReplaced) {
         this.callback.checkReplacement(this.oneBigMessage, this.messageToBeReplaced);
         return this.oneBigMessage;
      } else {
         return message;
      }
   }

   MessageReference updateVisibleMessage(MessageReference ref, KernelImpl kernel, PersistentStoreTransaction storeTran) {
      this.initCallback();
      this.queue = ref.getQueue();
      Message message = ref.getMessageHandle().getMessage();
      if (message == this.messageToBeReplaced) {
         return null;
      } else {
         this.assigned.add(ref);
         ref.setState(512);
         Message newMessage = this.callback.newVisibleMessage(message);
         if (newMessage == null) {
            return null;
         } else {
            this.oneBigMessage = newMessage;
            Iterator iter = this.refs.listIterator();
            this.messageToBeReplaced = this.callback.getOneBigMessageReplacee();
            MessageReference replacementRef = null;
            long minExp = 0L;

            while(iter.hasNext()) {
               MessageReference walkRef = (MessageReference)iter.next();
               long walkExp = walkRef.getMessageHandle().getExpirationTime();
               if (walkExp < minExp || minExp == 0L) {
                  minExp = walkExp;
               }

               if (walkRef.getMessageHandle().getMessage() == this.messageToBeReplaced) {
                  replacementRef = walkRef;
                  this.assigned.add(walkRef);
               }
            }

            if (replacementRef != null && minExp != 0L) {
               replacementRef.getMessageHandle().setExpirationTime(minExp);
            }

            return null;
         }
      }
   }

   public void run() {
      if (this.getExpiration()) {
         this.expireUsingList();
      } else {
         this.deleteUsingList();
      }

   }

   public void deleteAll() {
      synchronized(this) {
         this.deleteMe = this.refs;
         this.refs = new ArrayList();
         this.expiration = false;
      }

      this.queue.getKernelImpl().getDefaultWorkManager().schedule(this);
   }

   private void expireUsingList() {
      Iterator iter = null;
      synchronized(this) {
         this.expiration = false;
         if (this.deleteMe != null) {
            iter = ((ArrayList)((ArrayList)((ArrayList)this.deleteMe).clone())).iterator();
            this.deleteMe = null;
         }
      }

      while(iter != null && iter.hasNext()) {
         MessageReference toRemove = (MessageReference)iter.next();
         if ((toRemove.getState() & 32) == 0) {
            this.expire(toRemove);
         }
      }

      this.deleteSequence();
   }

   public void onCompletion(KernelRequest request, Object o) {
      this.deleteSequence();
   }

   public void onException(KernelRequest request, Throwable t) {
      this.deleteSequence();
   }

   private void deleteUsingList() {
      List localDeleteMe;
      synchronized(this) {
         if (this.deleteMe == null) {
            return;
         }

         localDeleteMe = this.deleteMe;
         this.deleteMe = null;
      }

      try {
         KernelRequest request = this.queue.delete(localDeleteMe);
         if (request != null) {
            request.addListener(this);
         }
      } catch (KernelException var4) {
         var4.printStackTrace();
      }

   }

   private void deleteSequence() {
      if (this.queue.isDurable()) {
         try {
            PersistenceImpl pers = this.queue.getKernelImpl().getPersistence();
            if (this.numberPersHandle != null) {
               pers.deleteSequenceNumber(this.numberPersHandle);
               this.numberPersHandle = null;
            }

            pers.deleteSequence(this);
         } catch (KernelException var2) {
            var2.printStackTrace();
         } catch (PersistentStoreRuntimeException var3) {
            var3.printStackTrace();
         }
      }

      this.queue.sequenceDeleted(this);
   }

   public void adminDeletedMessage(SequenceReference seqRef) {
      MessageReference ref = seqRef.getMessageReference();
      Message message = ref.getMessageHandle().getMessage();
      this.initCallback();
      this.callback.adminDeletedMessage(message);
   }

   synchronized void removeMessage(SequenceReference seqRef) {
      if (this.queue != null && this.queue.isActive()) {
         this.initCallback();
         MessageReference ref = seqRef.getMessageReference();
         Message message = ref.getMessageHandle().getMessage();
         ref.clearState(512);
         if (this.callback.removeMessage(message)) {
            this.expiration = false;
            if (this.messageToBeReplaced != message) {
               this.expiration = true;
            }

            this.messageToBeReplaced = null;
            if (this.refs.size() != 0) {
               this.deleteMe = this.refs;
               this.deleteMe.remove(ref);
               this.refs = new ArrayList();
               this.queue.getKernelImpl().getDefaultWorkManager().schedule(this);
            }
         }

         ref.getQueue().onEvent(new UnitOfWorkEventImpl(ref.getQueue(), this.name, false));
      } else {
         this.refs = new ArrayList();
         this.callback = null;
         this.deleteMe = null;
         this.messageToBeReplaced = null;
         this.assigned = new LinkedList();
         this.expiration = false;
         this.oneBigMessage = null;
      }
   }

   synchronized boolean sendMessage(GXATransaction transaction, MessageReference ref, long sequenceNum) throws KernelException {
      this.initCallback();
      SequenceReference seqRef = new SequenceReference(ref, this);
      ref.setSequenceRef(seqRef);
      if (this.messageToBeReplaced == ref.getMessageHandle().getMessage()) {
         return false;
      } else {
         try {
            ref.getMessageHandle().pin((KernelImpl)this.queue.getKernel());
         } catch (KernelException var12) {
         }

         boolean removeRef = false;
         this.refs.add(ref);

         try {
            if (this.callback.sendMessage(ref.getMessageHandle().getMessage())) {
               removeRef = true;
               boolean var7 = true;
               return var7;
            }
         } catch (KernelException var13) {
            removeRef = true;
            throw var13;
         } finally {
            if (removeRef) {
               this.refs.remove(ref);
            }

         }

         ref.getQueue().onEvent(new UnitOfWorkEventImpl(ref.getQueue(), this.name, true));
         ref.setState(512);
         return false;
      }
   }

   synchronized void recoverMessage(MessageReference ref) {
      this.initCallback();

      try {
         ref.getMessageHandle().pin((KernelImpl)this.queue.getKernel());
      } catch (KernelException var4) {
      }

      this.refs.add(ref);

      try {
         this.callback.recoverMessage(ref.getMessageHandle().getMessage());
      } catch (KernelException var3) {
         throw new AssertionError("We shouldn't have trouble with a message we already saw: " + var3);
      }

      ref.getQueue().onEvent(new UnitOfWorkEventImpl(ref.getQueue(), this.name, true));
      if (ref.getState() == 0) {
         this.updateVisibleMessage(ref, (KernelImpl)null, (PersistentStoreTransaction)null);
      }

   }

   synchronized void recoveryComplete(Queue queue) {
      this.initCallback();
      this.callback.recoveryComplete();
   }

   private void expire(MessageReference ref) {
      this.expire(ref, false);
   }

   private void expire(MessageReference ref, boolean markOnly) {
      if (logger.isDebugEnabled()) {
         logger.debug("Expiring " + ref.getMessageHandle().getMessage());
      }

      ref.setState(32);
      ref.clearState(512);
      if (!markOnly) {
         ref.getQueue().triggerExpirationTimerNow(ref);
      }

   }

   public void expire(Message message, boolean markOnly) {
      Iterator iter = null;
      synchronized(this) {
         if (this.refs.isEmpty()) {
            return;
         }

         iter = ((ArrayList)((ArrayList)((ArrayList)this.refs).clone())).iterator();
      }

      MessageReference ref;
      do {
         if (!iter.hasNext()) {
            return;
         }

         ref = (MessageReference)iter.next();
      } while(ref.getMessageHandle().getMessage() != message);

      this.expire(ref, markOnly);
      synchronized(this) {
         this.refs.remove(ref);
      }
   }

   public void expireAll() {
      Iterator iter = null;
      synchronized(this) {
         if (this.refs.isEmpty()) {
            return;
         }

         iter = ((ArrayList)((ArrayList)((ArrayList)this.refs).clone())).iterator();
      }

      if (iter.hasNext()) {
         MessageReference ref = (MessageReference)iter.next();
         this.expire(ref);
      }
   }

   public void setUserData(Object object) {
      try {
         super.setUserData(object);
      } catch (KernelException var3) {
         var3.printStackTrace();
      }

   }

   public Object getUserData() {
      return super.getUserData();
   }

   public String toString() {
      return this.name;
   }

   private synchronized Message getMessageToBeReplaced() {
      return this.messageToBeReplaced;
   }

   private synchronized boolean getExpiration() {
      return this.expiration;
   }
}
