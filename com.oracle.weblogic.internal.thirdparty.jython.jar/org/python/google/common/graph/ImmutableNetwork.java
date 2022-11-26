package org.python.google.common.graph;

import java.util.Iterator;
import java.util.Map;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.Maps;
import org.python.google.errorprone.annotations.Immutable;

@Immutable(
   containerOf = {"N", "E"}
)
@Beta
public final class ImmutableNetwork extends ConfigurableNetwork {
   private ImmutableNetwork(Network network) {
      super(NetworkBuilder.from(network), getNodeConnections(network), getEdgeToReferenceNode(network));
   }

   public static ImmutableNetwork copyOf(Network network) {
      return network instanceof ImmutableNetwork ? (ImmutableNetwork)network : new ImmutableNetwork(network);
   }

   /** @deprecated */
   @Deprecated
   public static ImmutableNetwork copyOf(ImmutableNetwork network) {
      return (ImmutableNetwork)Preconditions.checkNotNull(network);
   }

   public ImmutableGraph asGraph() {
      return new ImmutableGraph(super.asGraph());
   }

   private static Map getNodeConnections(Network network) {
      ImmutableMap.Builder nodeConnections = ImmutableMap.builder();
      Iterator var2 = network.nodes().iterator();

      while(var2.hasNext()) {
         Object node = var2.next();
         nodeConnections.put(node, connectionsOf(network, node));
      }

      return nodeConnections.build();
   }

   private static Map getEdgeToReferenceNode(Network network) {
      ImmutableMap.Builder edgeToReferenceNode = ImmutableMap.builder();
      Iterator var2 = network.edges().iterator();

      while(var2.hasNext()) {
         Object edge = var2.next();
         edgeToReferenceNode.put(edge, network.incidentNodes(edge).nodeU());
      }

      return edgeToReferenceNode.build();
   }

   private static NetworkConnections connectionsOf(Network network, Object node) {
      Map incidentEdgeMap;
      if (network.isDirected()) {
         incidentEdgeMap = Maps.asMap(network.inEdges(node), sourceNodeFn(network));
         Map outEdgeMap = Maps.asMap(network.outEdges(node), targetNodeFn(network));
         int selfLoopCount = network.edgesConnecting(node, node).size();
         return (NetworkConnections)(network.allowsParallelEdges() ? DirectedMultiNetworkConnections.ofImmutable(incidentEdgeMap, outEdgeMap, selfLoopCount) : DirectedNetworkConnections.ofImmutable(incidentEdgeMap, outEdgeMap, selfLoopCount));
      } else {
         incidentEdgeMap = Maps.asMap(network.incidentEdges(node), adjacentNodeFn(network, node));
         return (NetworkConnections)(network.allowsParallelEdges() ? UndirectedMultiNetworkConnections.ofImmutable(incidentEdgeMap) : UndirectedNetworkConnections.ofImmutable(incidentEdgeMap));
      }
   }

   private static Function sourceNodeFn(final Network network) {
      return new Function() {
         public Object apply(Object edge) {
            return network.incidentNodes(edge).source();
         }
      };
   }

   private static Function targetNodeFn(final Network network) {
      return new Function() {
         public Object apply(Object edge) {
            return network.incidentNodes(edge).target();
         }
      };
   }

   private static Function adjacentNodeFn(final Network network, final Object node) {
      return new Function() {
         public Object apply(Object edge) {
            return network.incidentNodes(edge).adjacentNode(node);
         }
      };
   }
}
