package weblogic.jms.common;

public class MessageList {
   protected MessageReference first;
   protected MessageReference last;
   private int count;

   public void addLast(MessageReference messageReference) {
      messageReference.setPrev(this.last);
      messageReference.setNext((MessageReference)null);
      if (this.last == null) {
         this.first = messageReference;
      } else {
         this.last.setNext(messageReference);
      }

      this.last = messageReference;
      this.incSize();
   }

   public void addFirst(MessageReference messageReference) {
      messageReference.setPrev((MessageReference)null);
      messageReference.setNext(this.first);
      if (this.first == null) {
         this.last = messageReference;
      } else {
         this.first.setPrev(messageReference);
      }

      this.first = messageReference;
      this.incSize();
   }

   public final void remove(MessageReference messageReference) {
      if (messageReference.getPrev() == null) {
         this.first = messageReference.getNext();
      } else {
         messageReference.getPrev().setNext(messageReference.getNext());
      }

      if (messageReference.getNext() == null) {
         this.last = messageReference.getPrev();
      } else {
         messageReference.getNext().setPrev(messageReference.getPrev());
      }

      messageReference.setNext((MessageReference)null);
      messageReference.setPrev((MessageReference)null);
      --this.count;
   }

   public final MessageReference removeFirst() {
      MessageReference messageReference = this.first;
      if (messageReference != null) {
         --this.count;
         this.first = messageReference.getNext();
         messageReference.setNext((MessageReference)null);
         if (this.first == null) {
            this.last = null;
         } else {
            this.first.setPrev((MessageReference)null);
         }
      }

      return messageReference;
   }

   public final MessageReference removeBeforeSequenceNumber(long sequenceNumber) {
      MessageReference messageReference = this.first;

      for(int removedCount = 0; messageReference != null; messageReference = messageReference.getNext()) {
         if (messageReference.getSequenceNumber() == sequenceNumber) {
            if ((this.first = messageReference.getNext()) == null) {
               this.last = null;
            } else {
               this.first.setPrev((MessageReference)null);
            }

            messageReference.setNext((MessageReference)null);
            ++removedCount;
            this.count -= removedCount;
            break;
         }

         ++removedCount;
      }

      return messageReference;
   }

   public final MessageReference removeAll() {
      MessageReference messageReference = this.first;
      this.first = this.last = null;
      this.count = 0;
      return messageReference;
   }

   public final MessageReference getFirst() {
      return this.first;
   }

   public final MessageReference getLast() {
      return this.last;
   }

   public final boolean isEmpty() {
      return this.first == null;
   }

   public final int getSize() {
      return this.count;
   }

   protected void incSize() {
      ++this.count;
   }
}
