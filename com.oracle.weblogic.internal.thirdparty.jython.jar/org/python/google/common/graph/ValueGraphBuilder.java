package org.python.google.common.graph;

import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;

@Beta
public final class ValueGraphBuilder extends AbstractGraphBuilder {
   private ValueGraphBuilder(boolean directed) {
      super(directed);
   }

   public static ValueGraphBuilder directed() {
      return new ValueGraphBuilder(true);
   }

   public static ValueGraphBuilder undirected() {
      return new ValueGraphBuilder(false);
   }

   public static ValueGraphBuilder from(ValueGraph graph) {
      return (new ValueGraphBuilder(graph.isDirected())).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
   }

   public ValueGraphBuilder allowsSelfLoops(boolean allowsSelfLoops) {
      this.allowsSelfLoops = allowsSelfLoops;
      return this;
   }

   public ValueGraphBuilder expectedNodeCount(int expectedNodeCount) {
      this.expectedNodeCount = Optional.of(Graphs.checkNonNegative(expectedNodeCount));
      return this;
   }

   public ValueGraphBuilder nodeOrder(ElementOrder nodeOrder) {
      ValueGraphBuilder newBuilder = this.cast();
      newBuilder.nodeOrder = (ElementOrder)Preconditions.checkNotNull(nodeOrder);
      return newBuilder;
   }

   public MutableValueGraph build() {
      return new ConfigurableMutableValueGraph(this);
   }

   private ValueGraphBuilder cast() {
      return this;
   }
}
