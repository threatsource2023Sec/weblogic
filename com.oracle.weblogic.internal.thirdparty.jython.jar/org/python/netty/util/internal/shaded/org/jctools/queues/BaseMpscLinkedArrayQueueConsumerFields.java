package org.python.netty.util.internal.shaded.org.jctools.queues;

abstract class BaseMpscLinkedArrayQueueConsumerFields extends BaseMpscLinkedArrayQueuePad2 {
   protected long consumerMask;
   protected Object[] consumerBuffer;
   protected long consumerIndex;
}
