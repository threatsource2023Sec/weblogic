package weblogic.messaging.kernel.internal;

import weblogic.utils.collections.AbstractEmbeddedListElement;

final class SequenceReference extends AbstractEmbeddedListElement {
   private MessageReference msgRef;
   private SequenceImpl sequence;
   private long sequenceNum;

   SequenceReference(MessageReference msgRef, SequenceImpl sequence) {
      this.msgRef = msgRef;
      this.sequence = sequence;
   }

   SequenceReference(MessageReference msgRef, SequenceReference seqRef) {
      this.msgRef = msgRef;
      this.sequence = seqRef.sequence;
      this.sequenceNum = seqRef.sequenceNum;
   }

   MessageReference getMessageReference() {
      return this.msgRef;
   }

   SequenceImpl getSequence() {
      return this.sequence;
   }

   void setSequence(SequenceImpl sequence) {
      this.sequence = sequence;
   }

   long getSequenceNum() {
      return this.sequenceNum;
   }

   void setSequenceNum(long sequenceNum) {
      this.sequenceNum = sequenceNum;
   }
}
