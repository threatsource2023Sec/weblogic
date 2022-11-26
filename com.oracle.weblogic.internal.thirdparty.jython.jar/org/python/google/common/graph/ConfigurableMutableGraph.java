package org.python.google.common.graph;

final class ConfigurableMutableGraph extends ForwardingGraph implements MutableGraph {
   private final MutableValueGraph backingValueGraph;

   ConfigurableMutableGraph(AbstractGraphBuilder builder) {
      this.backingValueGraph = new ConfigurableMutableValueGraph(builder);
   }

   protected BaseGraph delegate() {
      return this.backingValueGraph;
   }

   public boolean addNode(Object node) {
      return this.backingValueGraph.addNode(node);
   }

   public boolean putEdge(Object nodeU, Object nodeV) {
      return this.backingValueGraph.putEdgeValue(nodeU, nodeV, GraphConstants.Presence.EDGE_EXISTS) == null;
   }

   public boolean removeNode(Object node) {
      return this.backingValueGraph.removeNode(node);
   }

   public boolean removeEdge(Object nodeU, Object nodeV) {
      return this.backingValueGraph.removeEdge(nodeU, nodeV) != null;
   }
}
