package org.python.google.common.graph;

import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;

@Beta
public final class NetworkBuilder extends AbstractGraphBuilder {
   boolean allowsParallelEdges = false;
   ElementOrder edgeOrder = ElementOrder.insertion();
   Optional expectedEdgeCount = Optional.absent();

   private NetworkBuilder(boolean directed) {
      super(directed);
   }

   public static NetworkBuilder directed() {
      return new NetworkBuilder(true);
   }

   public static NetworkBuilder undirected() {
      return new NetworkBuilder(false);
   }

   public static NetworkBuilder from(Network network) {
      return (new NetworkBuilder(network.isDirected())).allowsParallelEdges(network.allowsParallelEdges()).allowsSelfLoops(network.allowsSelfLoops()).nodeOrder(network.nodeOrder()).edgeOrder(network.edgeOrder());
   }

   public NetworkBuilder allowsParallelEdges(boolean allowsParallelEdges) {
      this.allowsParallelEdges = allowsParallelEdges;
      return this;
   }

   public NetworkBuilder allowsSelfLoops(boolean allowsSelfLoops) {
      this.allowsSelfLoops = allowsSelfLoops;
      return this;
   }

   public NetworkBuilder expectedNodeCount(int expectedNodeCount) {
      this.expectedNodeCount = Optional.of(Graphs.checkNonNegative(expectedNodeCount));
      return this;
   }

   public NetworkBuilder expectedEdgeCount(int expectedEdgeCount) {
      this.expectedEdgeCount = Optional.of(Graphs.checkNonNegative(expectedEdgeCount));
      return this;
   }

   public NetworkBuilder nodeOrder(ElementOrder nodeOrder) {
      NetworkBuilder newBuilder = this.cast();
      newBuilder.nodeOrder = (ElementOrder)Preconditions.checkNotNull(nodeOrder);
      return newBuilder;
   }

   public NetworkBuilder edgeOrder(ElementOrder edgeOrder) {
      NetworkBuilder newBuilder = this.cast();
      newBuilder.edgeOrder = (ElementOrder)Preconditions.checkNotNull(edgeOrder);
      return newBuilder;
   }

   public MutableNetwork build() {
      return new ConfigurableMutableNetwork(this);
   }

   private NetworkBuilder cast() {
      return this;
   }
}
