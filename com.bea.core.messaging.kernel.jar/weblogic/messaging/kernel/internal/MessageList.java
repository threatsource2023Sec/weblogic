package weblogic.messaging.kernel.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;

final class MessageList {
   static final int NEXT = 1;
   static final int PREVIOUS = 2;
   private final SortList list = new SortList();
   private Comparator comparator;
   private KernelImpl kernel;

   MessageList(Comparator comparator, KernelImpl kernel) {
      this.comparator = comparator;
      this.kernel = kernel;
      if (comparator != null) {
         this.list.sortAndIndex(new SortingComparator(comparator, kernel, false));
      }

   }

   Iterator iterator() {
      return this.list.iterator();
   }

   SortListIterator sortListIterator() {
      return this.list.sortListIterator();
   }

   int size() {
      return this.list.size();
   }

   boolean contains(MessageReference ref) {
      return this.list.contains(ref);
   }

   void remove(MessageReference ref) {
      this.list.remove(ref);
   }

   void add(MessageReference newRef, MessageReference subsequentRef) throws KernelException {
      if (this.comparator == null) {
         if (subsequentRef != null) {
            SortListIterator i = this.list.sortListIterator();
            i.reset(subsequentRef);
            i.add(newRef);
         } else {
            this.list.add(newRef);
         }
      } else {
         this.addUsingComparator(newRef);
      }

   }

   void addUsingSequenceNumbers(MessageReference newRef) throws KernelException {
      if (this.comparator == null) {
         ListIterator iterator = this.list.listIterator();

         while(iterator.hasNext()) {
            MessageReference ref = (MessageReference)iterator.next();
            if (QueueImpl.SEQUENCE_NUM_COMPARATOR.compare(newRef, ref) < 0) {
               iterator.previous();
               break;
            }
         }

         iterator.add(newRef);
      } else {
         this.addUsingComparator(newRef);
      }

   }

   private void addUsingComparator(MessageReference newRef) throws KernelException {
      assert this.comparator != null;

      MessageHandle newHandle = newRef.getMessageHandle();
      newHandle.pin(this.kernel);

      try {
         this.list.addUsingIndex(newRef);
      } finally {
         newHandle.unPin(this.kernel);
      }

   }

   private MessageReference getNext(int direction, ListIterator iterator) {
      if (direction == 1) {
         return iterator.hasNext() ? (MessageReference)iterator.next() : null;
      } else {
         return iterator.hasPrevious() ? (MessageReference)iterator.previous() : null;
      }
   }

   void moveAfter(MessageReference ref, MessageReference previousRef) {
      assert ref.getSequenceRef() != null;

      assert previousRef.getSequenceRef() != null;

      assert ref.getSequenceRef().getSequence() == previousRef.getSequenceRef().getSequence();

      if (this.comparator == null && ref != previousRef) {
         SortListIterator iterator = this.list.sortListIterator();
         this.list.remove(ref);
         if (this.list.contains(previousRef)) {
            iterator.reset(previousRef);
         } else {
            while(iterator.hasNext()) {
               MessageReference msgRef = (MessageReference)iterator.next();
               SequenceReference prevSeqRef = previousRef.getSequenceRef();
               SequenceReference seqRef = msgRef.getSequenceRef();
               if (seqRef != null && seqRef.getSequence() == prevSeqRef.getSequence() && seqRef.getSequenceNum() > prevSeqRef.getSequenceNum()) {
                  if (iterator.hasPrevious()) {
                     iterator.previous();
                  }
                  break;
               }
            }
         }

         if (iterator.hasNext()) {
            iterator.next();
            iterator.add(ref);
         } else {
            this.list.add(ref);
         }

      }
   }

   MessageReference find(int direction, ListIterator iterator, Expression expression, boolean includeVisible, int state) throws KernelException {
      while(true) {
         MessageReference msgRef;
         if ((msgRef = this.getNext(direction, iterator)) != null) {
            int eltState = msgRef.getState();
            if (!includeVisible && eltState == 0 || (eltState & ~state) != 0) {
               continue;
            }

            if (expression == null) {
               return msgRef;
            }

            MessageElementImpl elt = new MessageElementImpl(msgRef, this.kernel);
            if (!expression.getFilter().match(elt, expression)) {
               continue;
            }

            return msgRef;
         }

         return null;
      }
   }

   MessageReference findNextVisible(Iterator iterator, Expression expression) throws KernelException {
      while(true) {
         if (iterator.hasNext()) {
            MessageReference msgRef = (MessageReference)iterator.next();
            if (!msgRef.isVisible()) {
               continue;
            }

            if (expression == null) {
               return msgRef;
            }

            MessageElementImpl elt = new MessageElementImpl(msgRef, this.kernel);
            if (!expression.getFilter().match(elt, expression)) {
               continue;
            }

            return msgRef;
         }

         return null;
      }
   }

   void setComparator(Comparator newComparator) {
      this.comparator = newComparator;
      if (this.comparator == null) {
         this.list.destroyIndex();
      } else {
         this.list.sortAndIndex(new SortingComparator(this.comparator, this.kernel, false));
      }
   }
}
