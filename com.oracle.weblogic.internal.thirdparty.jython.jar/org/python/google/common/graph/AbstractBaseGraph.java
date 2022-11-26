package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.common.math.IntMath;
import org.python.google.common.primitives.Ints;

abstract class AbstractBaseGraph implements BaseGraph {
   protected long edgeCount() {
      long degreeSum = 0L;

      Object node;
      for(Iterator var3 = this.nodes().iterator(); var3.hasNext(); degreeSum += (long)this.degree(node)) {
         node = var3.next();
      }

      Preconditions.checkState((degreeSum & 1L) == 0L);
      return degreeSum >>> 1;
   }

   public Set edges() {
      return new AbstractSet() {
         public UnmodifiableIterator iterator() {
            return EndpointPairIterator.of(AbstractBaseGraph.this);
         }

         public int size() {
            return Ints.saturatedCast(AbstractBaseGraph.this.edgeCount());
         }

         public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof EndpointPair)) {
               return false;
            } else {
               EndpointPair endpointPair = (EndpointPair)obj;
               return AbstractBaseGraph.this.isDirected() == endpointPair.isOrdered() && AbstractBaseGraph.this.nodes().contains(endpointPair.nodeU()) && AbstractBaseGraph.this.successors(endpointPair.nodeU()).contains(endpointPair.nodeV());
            }
         }
      };
   }

   public int degree(Object node) {
      if (this.isDirected()) {
         return IntMath.saturatedAdd(this.predecessors(node).size(), this.successors(node).size());
      } else {
         Set neighbors = this.adjacentNodes(node);
         int selfLoopCount = this.allowsSelfLoops() && neighbors.contains(node) ? 1 : 0;
         return IntMath.saturatedAdd(neighbors.size(), selfLoopCount);
      }
   }

   public int inDegree(Object node) {
      return this.isDirected() ? this.predecessors(node).size() : this.degree(node);
   }

   public int outDegree(Object node) {
      return this.isDirected() ? this.successors(node).size() : this.degree(node);
   }
}
