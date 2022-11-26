package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class BaseLinkedQueueConsumerNodeRef extends BaseLinkedQueuePad1 {
   protected static final long C_NODE_OFFSET;
   protected LinkedQueueNode consumerNode;

   protected final void spConsumerNode(LinkedQueueNode node) {
      this.consumerNode = node;
   }

   protected final LinkedQueueNode lvConsumerNode() {
      return (LinkedQueueNode)UnsafeAccess.UNSAFE.getObjectVolatile(this, C_NODE_OFFSET);
   }

   protected final LinkedQueueNode lpConsumerNode() {
      return this.consumerNode;
   }

   static {
      try {
         C_NODE_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseLinkedQueueConsumerNodeRef.class.getDeclaredField("consumerNode"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
