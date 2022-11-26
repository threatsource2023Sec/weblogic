package org.python.netty.util.internal;

import java.util.Queue;

public interface PriorityQueue extends Queue {
   boolean removeTyped(Object var1);

   boolean containsTyped(Object var1);

   void priorityChanged(Object var1);
}
