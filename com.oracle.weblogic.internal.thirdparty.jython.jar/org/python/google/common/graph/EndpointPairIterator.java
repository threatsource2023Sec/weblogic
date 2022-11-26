package org.python.google.common.graph;

import java.util.Iterator;
import java.util.Set;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.AbstractIterator;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Sets;

abstract class EndpointPairIterator extends AbstractIterator {
   private final BaseGraph graph;
   private final Iterator nodeIterator;
   protected Object node;
   protected Iterator successorIterator;

   static EndpointPairIterator of(BaseGraph graph) {
      return (EndpointPairIterator)(graph.isDirected() ? new Directed(graph) : new Undirected(graph));
   }

   private EndpointPairIterator(BaseGraph graph) {
      this.node = null;
      this.successorIterator = ImmutableSet.of().iterator();
      this.graph = graph;
      this.nodeIterator = graph.nodes().iterator();
   }

   protected final boolean advance() {
      Preconditions.checkState(!this.successorIterator.hasNext());
      if (!this.nodeIterator.hasNext()) {
         return false;
      } else {
         this.node = this.nodeIterator.next();
         this.successorIterator = this.graph.successors(this.node).iterator();
         return true;
      }
   }

   // $FF: synthetic method
   EndpointPairIterator(BaseGraph x0, Object x1) {
      this(x0);
   }

   private static final class Undirected extends EndpointPairIterator {
      private Set visitedNodes;

      private Undirected(BaseGraph graph) {
         super(graph, null);
         this.visitedNodes = Sets.newHashSetWithExpectedSize(graph.nodes().size());
      }

      protected EndpointPair computeNext() {
         while(true) {
            if (this.successorIterator.hasNext()) {
               Object otherNode = this.successorIterator.next();
               if (!this.visitedNodes.contains(otherNode)) {
                  return EndpointPair.unordered(this.node, otherNode);
               }
            } else {
               this.visitedNodes.add(this.node);
               if (!this.advance()) {
                  this.visitedNodes = null;
                  return (EndpointPair)this.endOfData();
               }
            }
         }
      }

      // $FF: synthetic method
      Undirected(BaseGraph x0, Object x1) {
         this(x0);
      }
   }

   private static final class Directed extends EndpointPairIterator {
      private Directed(BaseGraph graph) {
         super(graph, null);
      }

      protected EndpointPair computeNext() {
         do {
            if (this.successorIterator.hasNext()) {
               return EndpointPair.ordered(this.node, this.successorIterator.next());
            }
         } while(this.advance());

         return (EndpointPair)this.endOfData();
      }

      // $FF: synthetic method
      Directed(BaseGraph x0, Object x1) {
         this(x0);
      }
   }
}
