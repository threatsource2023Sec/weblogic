package org.python.google.common.graph;

import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

interface GraphConnections {
   Set adjacentNodes();

   Set predecessors();

   Set successors();

   @Nullable
   Object value(Object var1);

   void removePredecessor(Object var1);

   @CanIgnoreReturnValue
   Object removeSuccessor(Object var1);

   void addPredecessor(Object var1, Object var2);

   @CanIgnoreReturnValue
   Object addSuccessor(Object var1, Object var2);
}
