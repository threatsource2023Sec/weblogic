package org.python.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

abstract class BaseLinkedAtomicQueue extends AbstractQueue {
   private final AtomicReference producerNode = new AtomicReference();
   private final AtomicReference consumerNode = new AtomicReference();

   public BaseLinkedAtomicQueue() {
   }

   protected final LinkedQueueAtomicNode lvProducerNode() {
      return (LinkedQueueAtomicNode)this.producerNode.get();
   }

   protected final LinkedQueueAtomicNode lpProducerNode() {
      return (LinkedQueueAtomicNode)this.producerNode.get();
   }

   protected final void spProducerNode(LinkedQueueAtomicNode node) {
      this.producerNode.lazySet(node);
   }

   protected final LinkedQueueAtomicNode xchgProducerNode(LinkedQueueAtomicNode node) {
      return (LinkedQueueAtomicNode)this.producerNode.getAndSet(node);
   }

   protected final LinkedQueueAtomicNode lvConsumerNode() {
      return (LinkedQueueAtomicNode)this.consumerNode.get();
   }

   protected final LinkedQueueAtomicNode lpConsumerNode() {
      return (LinkedQueueAtomicNode)this.consumerNode.get();
   }

   protected final void spConsumerNode(LinkedQueueAtomicNode node) {
      this.consumerNode.lazySet(node);
   }

   public final Iterator iterator() {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return this.getClass().getName();
   }

   public final int size() {
      LinkedQueueAtomicNode chaserNode = this.lvConsumerNode();
      LinkedQueueAtomicNode producerNode = this.lvProducerNode();

      int size;
      for(size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; ++size) {
         LinkedQueueAtomicNode next = chaserNode.lvNext();
         if (next == chaserNode) {
            return size;
         }

         chaserNode = next;
      }

      return size;
   }

   public final boolean isEmpty() {
      return this.lvConsumerNode() == this.lvProducerNode();
   }

   protected Object getSingleConsumerNodeValue(LinkedQueueAtomicNode currConsumerNode, LinkedQueueAtomicNode nextNode) {
      Object nextValue = nextNode.getAndNullValue();
      currConsumerNode.soNext(currConsumerNode);
      this.spConsumerNode(nextNode);
      return nextValue;
   }
}
