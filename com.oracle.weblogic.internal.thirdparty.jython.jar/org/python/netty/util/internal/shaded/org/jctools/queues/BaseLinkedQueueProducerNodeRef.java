package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class BaseLinkedQueueProducerNodeRef extends BaseLinkedQueuePad0 {
   protected static final long P_NODE_OFFSET;
   protected LinkedQueueNode producerNode;

   protected final void spProducerNode(LinkedQueueNode node) {
      this.producerNode = node;
   }

   protected final LinkedQueueNode lvProducerNode() {
      return (LinkedQueueNode)UnsafeAccess.UNSAFE.getObjectVolatile(this, P_NODE_OFFSET);
   }

   protected final LinkedQueueNode lpProducerNode() {
      return this.producerNode;
   }

   static {
      try {
         P_NODE_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseLinkedQueueProducerNodeRef.class.getDeclaredField("producerNode"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
