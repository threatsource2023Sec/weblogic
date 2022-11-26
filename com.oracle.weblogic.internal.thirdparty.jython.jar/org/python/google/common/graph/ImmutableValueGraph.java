package org.python.google.common.graph;

import java.util.Iterator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Maps;
import org.python.google.errorprone.annotations.Immutable;

@Immutable(
   containerOf = {"N", "V"}
)
@Beta
public final class ImmutableValueGraph extends ConfigurableValueGraph {
   private ImmutableValueGraph(ValueGraph graph) {
      super(ValueGraphBuilder.from(graph), getNodeConnections(graph), (long)graph.edges().size());
   }

   public static ImmutableValueGraph copyOf(ValueGraph graph) {
      return graph instanceof ImmutableValueGraph ? (ImmutableValueGraph)graph : new ImmutableValueGraph(graph);
   }

   /** @deprecated */
   @Deprecated
   public static ImmutableValueGraph copyOf(ImmutableValueGraph graph) {
      return (ImmutableValueGraph)Preconditions.checkNotNull(graph);
   }

   public ImmutableGraph asGraph() {
      return new ImmutableGraph(this);
   }

   private static ImmutableMap getNodeConnections(ValueGraph graph) {
      ImmutableMap.Builder nodeConnections = ImmutableMap.builder();
      Iterator var2 = graph.nodes().iterator();

      while(var2.hasNext()) {
         Object node = var2.next();
         nodeConnections.put(node, connectionsOf(graph, node));
      }

      return nodeConnections.build();
   }

   private static GraphConnections connectionsOf(final ValueGraph graph, final Object node) {
      Function successorNodeToValueFn = new Function() {
         public Object apply(Object successorNode) {
            return graph.edgeValue(node, successorNode);
         }
      };
      return (GraphConnections)(graph.isDirected() ? DirectedGraphConnections.ofImmutable(graph.predecessors(node), Maps.asMap(graph.successors(node), successorNodeToValueFn)) : UndirectedGraphConnections.ofImmutable(Maps.asMap(graph.adjacentNodes(node), successorNodeToValueFn)));
   }
}
