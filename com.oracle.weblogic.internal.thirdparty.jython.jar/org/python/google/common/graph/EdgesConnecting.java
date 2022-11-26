package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Iterators;
import org.python.google.common.collect.UnmodifiableIterator;

final class EdgesConnecting extends AbstractSet {
   private final Map nodeToOutEdge;
   private final Object targetNode;

   EdgesConnecting(Map nodeToEdgeMap, Object targetNode) {
      this.nodeToOutEdge = (Map)Preconditions.checkNotNull(nodeToEdgeMap);
      this.targetNode = Preconditions.checkNotNull(targetNode);
   }

   public UnmodifiableIterator iterator() {
      Object connectingEdge = this.getConnectingEdge();
      return connectingEdge == null ? ImmutableSet.of().iterator() : Iterators.singletonIterator(connectingEdge);
   }

   public int size() {
      return this.getConnectingEdge() == null ? 0 : 1;
   }

   public boolean contains(@Nullable Object edge) {
      Object connectingEdge = this.getConnectingEdge();
      return connectingEdge != null && connectingEdge.equals(edge);
   }

   @Nullable
   private Object getConnectingEdge() {
      return this.nodeToOutEdge.get(this.targetNode);
   }
}
