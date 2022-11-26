package org.python.google.common.graph;

import java.util.Set;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

interface NetworkConnections {
   Set adjacentNodes();

   Set predecessors();

   Set successors();

   Set incidentEdges();

   Set inEdges();

   Set outEdges();

   Set edgesConnecting(Object var1);

   Object oppositeNode(Object var1);

   @CanIgnoreReturnValue
   Object removeInEdge(Object var1, boolean var2);

   @CanIgnoreReturnValue
   Object removeOutEdge(Object var1);

   void addInEdge(Object var1, Object var2, boolean var3);

   void addOutEdge(Object var1, Object var2);
}
