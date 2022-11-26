package org.python.google.common.graph;

import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;

@Beta
public interface Network {
   Set nodes();

   Set edges();

   Graph asGraph();

   boolean isDirected();

   boolean allowsParallelEdges();

   boolean allowsSelfLoops();

   ElementOrder nodeOrder();

   ElementOrder edgeOrder();

   Set adjacentNodes(Object var1);

   Set predecessors(Object var1);

   Set successors(Object var1);

   Set incidentEdges(Object var1);

   Set inEdges(Object var1);

   Set outEdges(Object var1);

   int degree(Object var1);

   int inDegree(Object var1);

   int outDegree(Object var1);

   EndpointPair incidentNodes(Object var1);

   Set adjacentEdges(Object var1);

   Set edgesConnecting(Object var1, Object var2);

   boolean equals(@Nullable Object var1);

   int hashCode();
}
