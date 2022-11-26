package org.python.google.common.graph;

import java.util.Iterator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Function;
import org.python.google.common.base.Functions;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Maps;
import org.python.google.errorprone.annotations.Immutable;

@Immutable(
   containerOf = {"N"}
)
@Beta
public class ImmutableGraph extends ForwardingGraph {
   private final BaseGraph backingGraph;

   ImmutableGraph(BaseGraph backingGraph) {
      this.backingGraph = backingGraph;
   }

   public static ImmutableGraph copyOf(Graph graph) {
      return graph instanceof ImmutableGraph ? (ImmutableGraph)graph : new ImmutableGraph(new ConfigurableValueGraph(GraphBuilder.from(graph), getNodeConnections(graph), (long)graph.edges().size()));
   }

   /** @deprecated */
   @Deprecated
   public static ImmutableGraph copyOf(ImmutableGraph graph) {
      return (ImmutableGraph)Preconditions.checkNotNull(graph);
   }

   private static ImmutableMap getNodeConnections(Graph graph) {
      ImmutableMap.Builder nodeConnections = ImmutableMap.builder();
      Iterator var2 = graph.nodes().iterator();

      while(var2.hasNext()) {
         Object node = var2.next();
         nodeConnections.put(node, connectionsOf(graph, node));
      }

      return nodeConnections.build();
   }

   private static GraphConnections connectionsOf(Graph graph, Object node) {
      Function edgeValueFn = Functions.constant(GraphConstants.Presence.EDGE_EXISTS);
      return (GraphConnections)(graph.isDirected() ? DirectedGraphConnections.ofImmutable(graph.predecessors(node), Maps.asMap(graph.successors(node), edgeValueFn)) : UndirectedGraphConnections.ofImmutable(Maps.asMap(graph.adjacentNodes(node), edgeValueFn)));
   }

   protected BaseGraph delegate() {
      return this.backingGraph;
   }
}
