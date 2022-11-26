package org.python.netty.util.internal.shaded.org.jctools.queues.atomic;

public final class MpscLinkedAtomicQueue extends BaseLinkedAtomicQueue {
   public MpscLinkedAtomicQueue() {
      LinkedQueueAtomicNode node = new LinkedQueueAtomicNode();
      this.spConsumerNode(node);
      this.xchgProducerNode(node);
   }

   public final boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         LinkedQueueAtomicNode nextNode = new LinkedQueueAtomicNode(e);
         LinkedQueueAtomicNode prevProducerNode = this.xchgProducerNode(nextNode);
         prevProducerNode.soNext(nextNode);
         return true;
      }
   }

   public final Object poll() {
      LinkedQueueAtomicNode currConsumerNode = this.lpConsumerNode();
      LinkedQueueAtomicNode nextNode = currConsumerNode.lvNext();
      if (nextNode != null) {
         return this.getSingleConsumerNodeValue(currConsumerNode, nextNode);
      } else if (currConsumerNode == this.lvProducerNode()) {
         return null;
      } else {
         while((nextNode = currConsumerNode.lvNext()) == null) {
         }

         return this.getSingleConsumerNodeValue(currConsumerNode, nextNode);
      }
   }

   public final Object peek() {
      LinkedQueueAtomicNode currConsumerNode = this.lpConsumerNode();
      LinkedQueueAtomicNode nextNode = currConsumerNode.lvNext();
      if (nextNode != null) {
         return nextNode.lpValue();
      } else if (currConsumerNode == this.lvProducerNode()) {
         return null;
      } else {
         while((nextNode = currConsumerNode.lvNext()) == null) {
         }

         return nextNode.lpValue();
      }
   }
}
