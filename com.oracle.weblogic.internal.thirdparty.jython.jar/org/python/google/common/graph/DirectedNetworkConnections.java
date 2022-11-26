package org.python.google.common.graph;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.python.google.common.collect.BiMap;
import org.python.google.common.collect.HashBiMap;
import org.python.google.common.collect.ImmutableBiMap;

final class DirectedNetworkConnections extends AbstractDirectedNetworkConnections {
   protected DirectedNetworkConnections(Map inEdgeMap, Map outEdgeMap, int selfLoopCount) {
      super(inEdgeMap, outEdgeMap, selfLoopCount);
   }

   static DirectedNetworkConnections of() {
      return new DirectedNetworkConnections(HashBiMap.create(2), HashBiMap.create(2), 0);
   }

   static DirectedNetworkConnections ofImmutable(Map inEdges, Map outEdges, int selfLoopCount) {
      return new DirectedNetworkConnections(ImmutableBiMap.copyOf(inEdges), ImmutableBiMap.copyOf(outEdges), selfLoopCount);
   }

   public Set predecessors() {
      return Collections.unmodifiableSet(((BiMap)this.inEdgeMap).values());
   }

   public Set successors() {
      return Collections.unmodifiableSet(((BiMap)this.outEdgeMap).values());
   }

   public Set edgesConnecting(Object node) {
      return new EdgesConnecting(((BiMap)this.outEdgeMap).inverse(), node);
   }
}
