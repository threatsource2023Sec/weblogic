package org.python.google.common.graph;

import org.python.google.common.annotations.Beta;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public interface MutableGraph extends Graph {
   @CanIgnoreReturnValue
   boolean addNode(Object var1);

   @CanIgnoreReturnValue
   boolean putEdge(Object var1, Object var2);

   @CanIgnoreReturnValue
   boolean removeNode(Object var1);

   @CanIgnoreReturnValue
   boolean removeEdge(Object var1, Object var2);
}
