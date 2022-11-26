package org.python.google.common.collect;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
class ConsumingQueueIterator extends AbstractIterator {
   private final Queue queue;

   ConsumingQueueIterator(Object... elements) {
      this.queue = new ArrayDeque(elements.length);
      Collections.addAll(this.queue, elements);
   }

   ConsumingQueueIterator(Queue queue) {
      this.queue = (Queue)Preconditions.checkNotNull(queue);
   }

   public Object computeNext() {
      return this.queue.isEmpty() ? this.endOfData() : this.queue.remove();
   }
}
