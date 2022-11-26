package org.python.google.common.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableMap;

final class UndirectedGraphConnections implements GraphConnections {
   private final Map adjacentNodeValues;

   private UndirectedGraphConnections(Map adjacentNodeValues) {
      this.adjacentNodeValues = (Map)Preconditions.checkNotNull(adjacentNodeValues);
   }

   static UndirectedGraphConnections of() {
      return new UndirectedGraphConnections(new HashMap(2, 1.0F));
   }

   static UndirectedGraphConnections ofImmutable(Map adjacentNodeValues) {
      return new UndirectedGraphConnections(ImmutableMap.copyOf(adjacentNodeValues));
   }

   public Set adjacentNodes() {
      return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
   }

   public Set predecessors() {
      return this.adjacentNodes();
   }

   public Set successors() {
      return this.adjacentNodes();
   }

   public Object value(Object node) {
      return this.adjacentNodeValues.get(node);
   }

   public void removePredecessor(Object node) {
      this.removeSuccessor(node);
   }

   public Object removeSuccessor(Object node) {
      return this.adjacentNodeValues.remove(node);
   }

   public void addPredecessor(Object node, Object value) {
      this.addSuccessor(node, value);
   }

   public Object addSuccessor(Object node, Object value) {
      return this.adjacentNodeValues.put(node, value);
   }
}
