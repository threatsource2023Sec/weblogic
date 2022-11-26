package org.python.google.common.graph;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.python.google.common.base.Preconditions;

abstract class AbstractUndirectedNetworkConnections implements NetworkConnections {
   protected final Map incidentEdgeMap;

   protected AbstractUndirectedNetworkConnections(Map incidentEdgeMap) {
      this.incidentEdgeMap = (Map)Preconditions.checkNotNull(incidentEdgeMap);
   }

   public Set predecessors() {
      return this.adjacentNodes();
   }

   public Set successors() {
      return this.adjacentNodes();
   }

   public Set incidentEdges() {
      return Collections.unmodifiableSet(this.incidentEdgeMap.keySet());
   }

   public Set inEdges() {
      return this.incidentEdges();
   }

   public Set outEdges() {
      return this.incidentEdges();
   }

   public Object oppositeNode(Object edge) {
      return Preconditions.checkNotNull(this.incidentEdgeMap.get(edge));
   }

   public Object removeInEdge(Object edge, boolean isSelfLoop) {
      return !isSelfLoop ? this.removeOutEdge(edge) : null;
   }

   public Object removeOutEdge(Object edge) {
      Object previousNode = this.incidentEdgeMap.remove(edge);
      return Preconditions.checkNotNull(previousNode);
   }

   public void addInEdge(Object edge, Object node, boolean isSelfLoop) {
      if (!isSelfLoop) {
         this.addOutEdge(edge, node);
      }

   }

   public void addOutEdge(Object edge, Object node) {
      Object previousNode = this.incidentEdgeMap.put(edge, node);
      Preconditions.checkState(previousNode == null);
   }
}
