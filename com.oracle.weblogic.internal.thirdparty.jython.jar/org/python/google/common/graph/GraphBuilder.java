package org.python.google.common.graph;

import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;

@Beta
public final class GraphBuilder extends AbstractGraphBuilder {
   private GraphBuilder(boolean directed) {
      super(directed);
   }

   public static GraphBuilder directed() {
      return new GraphBuilder(true);
   }

   public static GraphBuilder undirected() {
      return new GraphBuilder(false);
   }

   public static GraphBuilder from(Graph graph) {
      return (new GraphBuilder(graph.isDirected())).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
   }

   public GraphBuilder allowsSelfLoops(boolean allowsSelfLoops) {
      this.allowsSelfLoops = allowsSelfLoops;
      return this;
   }

   public GraphBuilder expectedNodeCount(int expectedNodeCount) {
      this.expectedNodeCount = Optional.of(Graphs.checkNonNegative(expectedNodeCount));
      return this;
   }

   public GraphBuilder nodeOrder(ElementOrder nodeOrder) {
      GraphBuilder newBuilder = this.cast();
      newBuilder.nodeOrder = (ElementOrder)Preconditions.checkNotNull(nodeOrder);
      return newBuilder;
   }

   public MutableGraph build() {
      return new ConfigurableMutableGraph(this);
   }

   private GraphBuilder cast() {
      return this;
   }
}
