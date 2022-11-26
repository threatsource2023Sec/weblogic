package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.internal.GXAResourceImpl;

public final class CursorImpl implements Cursor {
   private MessageReference[] messages;
   private int position;
   private KernelImpl kernel;

   CursorImpl(KernelImpl kernel, QueueImpl queue, boolean snapshot, Expression expression, int userState) throws KernelException {
      if (!snapshot) {
         throw new KernelException("Live cursors are not supported");
      } else {
         this.kernel = kernel;
         boolean includeVisible = (userState & 1) != 0;
         int state = userState >> 1;
         ArrayList list = new ArrayList();
         synchronized(queue) {
            SortListIterator qIterator = queue.getMessageList().sortListIterator();

            while(true) {
               MessageReference ref;
               if ((ref = queue.getMessageList().find(1, qIterator, expression, includeVisible, state)) == null) {
                  qIterator = queue.getPendingMessageList().sortListIterator();

                  while((ref = queue.getMessageList().find(1, qIterator, expression, includeVisible, state)) != null) {
                     this.addMessage(ref, list);
                  }
                  break;
               }

               this.addMessage(ref, list);
            }
         }

         this.buildMessageList(list, queue.getComparator());
      }
   }

   CursorImpl(KernelImpl kernel, Collection queues, Expression expression, int userState) throws KernelException {
      this.kernel = kernel;
      boolean includeVisible = (userState & 1) != 0;
      int state = userState >> 1;
      ArrayList list = new ArrayList();
      IdentityHashMap handleMap = new IdentityHashMap();
      Iterator i = queues.iterator();

      while(i.hasNext()) {
         QueueImpl queue = (QueueImpl)i.next();
         synchronized(queue) {
            SortListIterator qIterator = queue.getMessageList().sortListIterator();

            MessageReference ref;
            MessageHandle handle;
            while((ref = queue.getMessageList().find(1, qIterator, expression, includeVisible, state)) != null) {
               handle = ref.getMessageHandle();
               if (handleMap.put(handle, handle) == null) {
                  this.addMessage(ref, list);
               }
            }

            qIterator = queue.getPendingMessageList().sortListIterator();

            while((ref = queue.getMessageList().find(1, qIterator, expression, includeVisible, state)) != null) {
               handle = ref.getMessageHandle();
               if (handleMap.put(handle, handle) == null) {
                  this.addMessage(ref, list);
               }
            }
         }
      }

      this.buildMessageList(list, (Comparator)null);
   }

   CursorImpl(KernelImpl kernel, Xid xid) throws KernelException {
      this.kernel = kernel;
      ArrayList list = new ArrayList();
      GXATransaction transaction = null;
      GXAResourceImpl resourceManager = (GXAResourceImpl)kernel.getGXAResource();
      if ((transaction = resourceManager.getGXATransaction(xid)) != null) {
         synchronized(transaction) {
            GXAOperation[] operations = transaction.getGXAOperations();
            if (operations == null) {
               return;
            }

            for(int i = 0; i < operations.length; ++i) {
               if (operations[i] instanceof AbstractOperation) {
                  MessageReference ref = ((AbstractOperation)operations[i]).getMessageReference();
                  this.addMessage(ref, list);
               }
            }
         }

         if (!list.isEmpty()) {
            this.messages = new MessageReference[list.size()];
            list.toArray(this.messages);
         }

      }
   }

   private void buildMessageList(ArrayList unsortedList, Comparator messageComparator) {
      if (!unsortedList.isEmpty()) {
         this.messages = new MessageReference[unsortedList.size()];
         unsortedList.toArray(this.messages);
         if (messageComparator == null) {
            Arrays.sort(this.messages, QueueImpl.SEQUENCE_NUM_COMPARATOR);
         } else {
            Arrays.sort(this.messages, new SortingComparator(messageComparator, this.kernel, false));
         }
      }

   }

   private void addMessage(MessageReference ref, ArrayList list) {
      MessageHandle handle = ref.getMessageHandle();
      handle.adjustQueueReferenceCount(1);
      handle.adjustQuotaReferenceCount(1);
      list.add(ref.duplicate());
   }

   public synchronized void close() {
      if (this.messages != null) {
         for(int inc = 0; inc < this.messages.length; ++inc) {
            this.free(this.messages[inc]);
         }

         this.messages = null;
      }
   }

   public MessageElement next() throws KernelException {
      return this.next(false);
   }

   public synchronized MessageElement next(boolean delete) throws KernelException {
      if (this.messages == null) {
         return null;
      } else if (this.position >= this.messages.length) {
         return null;
      } else {
         MessageElement ret = new MessageElementImpl(this.messages[this.position], this.kernel, delete);
         ++this.position;

         assert this.position <= this.messages.length;

         return ret;
      }
   }

   public synchronized MessageElement previous() throws KernelException {
      if (this.messages == null) {
         return null;
      } else if (this.position <= 0) {
         return null;
      } else {
         --this.position;

         assert this.position >= 0;

         return new MessageElementImpl(this.messages[this.position], this.kernel);
      }
   }

   public synchronized void setComparator(Comparator comparator) {
      if (this.messages != null) {
         Arrays.sort(this.messages, new SortingComparator(comparator, this.kernel, false));
         this.position = 0;
      }
   }

   public synchronized void setElementComparator(Comparator comparator) {
      if (this.messages != null) {
         Arrays.sort(this.messages, new SortingComparator(comparator, this.kernel, true));
         this.position = 0;
      }
   }

   public synchronized int size() {
      return this.messages == null ? 0 : this.messages.length;
   }

   private void free(MessageReference element) {
      QueueImpl queue = element.getQueue();
      element.getMessageHandle().adjustQueueReferenceCount(-1);
      queue.free(element.getMessageHandle());
      element.getMessageHandle().removePagedState(this.kernel);
   }
}
