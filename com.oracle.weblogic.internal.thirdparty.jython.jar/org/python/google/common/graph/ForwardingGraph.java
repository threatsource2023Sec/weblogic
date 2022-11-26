package org.python.google.common.graph;

import java.util.Set;

abstract class ForwardingGraph extends AbstractGraph {
   protected abstract BaseGraph delegate();

   public Set nodes() {
      return this.delegate().nodes();
   }

   public Set edges() {
      return this.delegate().edges();
   }

   public boolean isDirected() {
      return this.delegate().isDirected();
   }

   public boolean allowsSelfLoops() {
      return this.delegate().allowsSelfLoops();
   }

   public ElementOrder nodeOrder() {
      return this.delegate().nodeOrder();
   }

   public Set adjacentNodes(Object node) {
      return this.delegate().adjacentNodes(node);
   }

   public Set predecessors(Object node) {
      return this.delegate().predecessors(node);
   }

   public Set successors(Object node) {
      return this.delegate().successors(node);
   }

   public int degree(Object node) {
      return this.delegate().degree(node);
   }

   public int inDegree(Object node) {
      return this.delegate().inDegree(node);
   }

   public int outDegree(Object node) {
      return this.delegate().outDegree(node);
   }
}
