package org.python.netty.util.internal.shaded.org.jctools.queues;

import java.util.Iterator;

abstract class BaseLinkedQueue extends BaseLinkedQueueConsumerNodeRef {
   long p01;
   long p02;
   long p03;
   long p04;
   long p05;
   long p06;
   long p07;
   long p10;
   long p11;
   long p12;
   long p13;
   long p14;
   long p15;
   long p16;
   long p17;

   public final Iterator iterator() {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return this.getClass().getName();
   }

   public final int size() {
      LinkedQueueNode chaserNode = this.lvConsumerNode();
      LinkedQueueNode producerNode = this.lvProducerNode();

      int size;
      for(size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; ++size) {
         LinkedQueueNode next = chaserNode.lvNext();
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

   public int capacity() {
      return -1;
   }

   protected Object getSingleConsumerNodeValue(LinkedQueueNode currConsumerNode, LinkedQueueNode nextNode) {
      Object nextValue = nextNode.getAndNullValue();
      currConsumerNode.soNext(currConsumerNode);
      this.spConsumerNode(nextNode);
      return nextValue;
   }

   public Object relaxedPoll() {
      LinkedQueueNode currConsumerNode = this.lpConsumerNode();
      LinkedQueueNode nextNode = currConsumerNode.lvNext();
      return nextNode != null ? this.getSingleConsumerNodeValue(currConsumerNode, nextNode) : null;
   }

   public int drain(MessagePassingQueue.Consumer c) {
      long result = 0L;

      int drained;
      do {
         drained = this.drain(c, 4096);
         result += (long)drained;
      } while(drained == 4096 && result <= 2147479551L);

      return (int)result;
   }

   public int drain(MessagePassingQueue.Consumer c, int limit) {
      LinkedQueueNode chaserNode = this.consumerNode;

      for(int i = 0; i < limit; ++i) {
         LinkedQueueNode nextNode = chaserNode.lvNext();
         if (nextNode == null) {
            return i;
         }

         Object nextValue = this.getSingleConsumerNodeValue(chaserNode, nextNode);
         chaserNode = nextNode;
         c.accept(nextValue);
      }

      return limit;
   }

   public void drain(MessagePassingQueue.Consumer c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
      LinkedQueueNode chaserNode = this.consumerNode;
      int idleCounter = 0;

      while(exit.keepRunning()) {
         for(int i = 0; i < 4096; ++i) {
            LinkedQueueNode nextNode = chaserNode.lvNext();
            if (nextNode == null) {
               idleCounter = wait.idle(idleCounter);
            } else {
               idleCounter = 0;
               Object nextValue = this.getSingleConsumerNodeValue(chaserNode, nextNode);
               chaserNode = nextNode;
               c.accept(nextValue);
            }
         }
      }

   }

   public Object relaxedPeek() {
      LinkedQueueNode currConsumerNode = this.consumerNode;
      LinkedQueueNode nextNode = currConsumerNode.lvNext();
      return nextNode != null ? nextNode.lpValue() : null;
   }

   public boolean relaxedOffer(Object e) {
      return this.offer(e);
   }
}
