package org.python.netty.util.internal.shaded.org.jctools.queues.atomic;

public final class SpscLinkedAtomicQueue extends BaseLinkedAtomicQueue {
   public SpscLinkedAtomicQueue() {
      LinkedQueueAtomicNode node = new LinkedQueueAtomicNode();
      this.spProducerNode(node);
      this.spConsumerNode(node);
      node.soNext((LinkedQueueAtomicNode)null);
   }

   public boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         LinkedQueueAtomicNode nextNode = new LinkedQueueAtomicNode(e);
         this.lpProducerNode().soNext(nextNode);
         this.spProducerNode(nextNode);
         return true;
      }
   }

   public Object poll() {
      LinkedQueueAtomicNode currConsumerNode = this.lpConsumerNode();
      LinkedQueueAtomicNode nextNode = currConsumerNode.lvNext();
      return nextNode != null ? this.getSingleConsumerNodeValue(currConsumerNode, nextNode) : null;
   }

   public Object peek() {
      LinkedQueueAtomicNode nextNode = this.lpConsumerNode().lvNext();
      return nextNode != null ? nextNode.lpValue() : null;
   }
}
