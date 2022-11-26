package org.python.google.common.graph;

import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;

@Beta
public interface Graph extends BaseGraph {
   Set nodes();

   Set edges();

   boolean isDirected();

   boolean allowsSelfLoops();

   ElementOrder nodeOrder();

   Set adjacentNodes(Object var1);

   Set predecessors(Object var1);

   Set successors(Object var1);

   int degree(Object var1);

   int inDegree(Object var1);

   int outDegree(Object var1);

   boolean equals(@Nullable Object var1);

   int hashCode();
}
