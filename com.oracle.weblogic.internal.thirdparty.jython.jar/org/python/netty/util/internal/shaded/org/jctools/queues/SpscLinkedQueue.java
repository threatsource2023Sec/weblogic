package org.python.netty.util.internal.shaded.org.jctools.queues;

public class SpscLinkedQueue extends BaseLinkedQueue {
   public SpscLinkedQueue() {
      this.spProducerNode(new LinkedQueueNode());
      this.spConsumerNode(this.producerNode);
      this.consumerNode.soNext((LinkedQueueNode)null);
   }

   public boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         LinkedQueueNode nextNode = new LinkedQueueNode(e);
         LinkedQueueNode producerNode = this.lpProducerNode();
         producerNode.soNext(nextNode);
         this.spProducerNode(nextNode);
         return true;
      }
   }

   public Object poll() {
      return this.relaxedPoll();
   }

   public Object peek() {
      return this.relaxedPeek();
   }

   public int fill(MessagePassingQueue.Supplier s) {
      long result = 0L;

      do {
         this.fill(s, 4096);
         result += 4096L;
      } while(result <= 2147479551L);

      return (int)result;
   }

   public int fill(MessagePassingQueue.Supplier s, int limit) {
      if (limit == 0) {
         return 0;
      } else {
         LinkedQueueNode tail = new LinkedQueueNode(s.get());

         for(int i = 1; i < limit; ++i) {
            LinkedQueueNode temp = new LinkedQueueNode(s.get());
            tail.soNext(temp);
            tail = temp;
         }

         LinkedQueueNode oldPNode = this.lpProducerNode();
         oldPNode.soNext(tail);
         this.spProducerNode(tail);
         return limit;
      }
   }

   public void fill(MessagePassingQueue.Supplier s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
      LinkedQueueNode chaserNode = this.producerNode;

      while(exit.keepRunning()) {
         for(int i = 0; i < 4096; ++i) {
            LinkedQueueNode nextNode = new LinkedQueueNode(s.get());
            chaserNode.soNext(nextNode);
            chaserNode = nextNode;
            this.producerNode = nextNode;
         }
      }

   }
}
