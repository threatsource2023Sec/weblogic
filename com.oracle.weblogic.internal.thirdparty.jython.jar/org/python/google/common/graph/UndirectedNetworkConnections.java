package org.python.google.common.graph;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.python.google.common.collect.BiMap;
import org.python.google.common.collect.HashBiMap;
import org.python.google.common.collect.ImmutableBiMap;

final class UndirectedNetworkConnections extends AbstractUndirectedNetworkConnections {
   protected UndirectedNetworkConnections(Map incidentEdgeMap) {
      super(incidentEdgeMap);
   }

   static UndirectedNetworkConnections of() {
      return new UndirectedNetworkConnections(HashBiMap.create(2));
   }

   static UndirectedNetworkConnections ofImmutable(Map incidentEdges) {
      return new UndirectedNetworkConnections(ImmutableBiMap.copyOf(incidentEdges));
   }

   public Set adjacentNodes() {
      return Collections.unmodifiableSet(((BiMap)this.incidentEdgeMap).values());
   }

   public Set edgesConnecting(Object node) {
      return new EdgesConnecting(((BiMap)this.incidentEdgeMap).inverse(), node);
   }
}
