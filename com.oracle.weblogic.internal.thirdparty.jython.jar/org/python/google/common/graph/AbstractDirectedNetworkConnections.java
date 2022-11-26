package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Iterables;
import org.python.google.common.collect.Iterators;
import org.python.google.common.collect.Sets;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.common.math.IntMath;

abstract class AbstractDirectedNetworkConnections implements NetworkConnections {
   protected final Map inEdgeMap;
   protected final Map outEdgeMap;
   private int selfLoopCount;

   protected AbstractDirectedNetworkConnections(Map inEdgeMap, Map outEdgeMap, int selfLoopCount) {
      this.inEdgeMap = (Map)Preconditions.checkNotNull(inEdgeMap);
      this.outEdgeMap = (Map)Preconditions.checkNotNull(outEdgeMap);
      this.selfLoopCount = Graphs.checkNonNegative(selfLoopCount);
      Preconditions.checkState(selfLoopCount <= inEdgeMap.size() && selfLoopCount <= outEdgeMap.size());
   }

   public Set adjacentNodes() {
      return Sets.union(this.predecessors(), this.successors());
   }

   public Set incidentEdges() {
      return new AbstractSet() {
         public UnmodifiableIterator iterator() {
            Iterable incidentEdges = AbstractDirectedNetworkConnections.this.selfLoopCount == 0 ? Iterables.concat(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet()) : Sets.union(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet());
            return Iterators.unmodifiableIterator(((Iterable)incidentEdges).iterator());
         }

         public int size() {
            return IntMath.saturatedAdd(AbstractDirectedNetworkConnections.this.inEdgeMap.size(), AbstractDirectedNetworkConnections.this.outEdgeMap.size() - AbstractDirectedNetworkConnections.this.selfLoopCount);
         }

         public boolean contains(@Nullable Object obj) {
            return AbstractDirectedNetworkConnections.this.inEdgeMap.containsKey(obj) || AbstractDirectedNetworkConnections.this.outEdgeMap.containsKey(obj);
         }
      };
   }

   public Set inEdges() {
      return Collections.unmodifiableSet(this.inEdgeMap.keySet());
   }

   public Set outEdges() {
      return Collections.unmodifiableSet(this.outEdgeMap.keySet());
   }

   public Object oppositeNode(Object edge) {
      return Preconditions.checkNotNull(this.outEdgeMap.get(edge));
   }

   public Object removeInEdge(Object edge, boolean isSelfLoop) {
      if (isSelfLoop) {
         Graphs.checkNonNegative(--this.selfLoopCount);
      }

      Object previousNode = this.inEdgeMap.remove(edge);
      return Preconditions.checkNotNull(previousNode);
   }

   public Object removeOutEdge(Object edge) {
      Object previousNode = this.outEdgeMap.remove(edge);
      return Preconditions.checkNotNull(previousNode);
   }

   public void addInEdge(Object edge, Object node, boolean isSelfLoop) {
      if (isSelfLoop) {
         Graphs.checkPositive(++this.selfLoopCount);
      }

      Object previousNode = this.inEdgeMap.put(edge, node);
      Preconditions.checkState(previousNode == null);
   }

   public void addOutEdge(Object edge, Object node) {
      Object previousNode = this.outEdgeMap.put(edge, node);
      Preconditions.checkState(previousNode == null);
   }
}
