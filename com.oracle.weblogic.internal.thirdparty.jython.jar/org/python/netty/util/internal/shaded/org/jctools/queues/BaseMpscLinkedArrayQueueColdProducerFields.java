package org.python.netty.util.internal.shaded.org.jctools.queues;

abstract class BaseMpscLinkedArrayQueueColdProducerFields extends BaseMpscLinkedArrayQueuePad3 {
   protected volatile long producerLimit;
   protected long producerMask;
   protected Object[] producerBuffer;
}
