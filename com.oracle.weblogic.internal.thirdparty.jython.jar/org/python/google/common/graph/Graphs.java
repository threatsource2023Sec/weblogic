package org.python.google.common.graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Iterables;
import org.python.google.common.collect.Maps;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public final class Graphs {
   private Graphs() {
   }

   public static boolean hasCycle(Graph graph) {
      int numEdges = graph.edges().size();
      if (numEdges == 0) {
         return false;
      } else if (!graph.isDirected() && numEdges >= graph.nodes().size()) {
         return true;
      } else {
         Map visitedNodes = Maps.newHashMapWithExpectedSize(graph.nodes().size());
         Iterator var3 = graph.nodes().iterator();

         Object node;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            node = var3.next();
         } while(!subgraphHasCycle(graph, visitedNodes, node, (Object)null));

         return true;
      }
   }

   public static boolean hasCycle(Network network) {
      return !network.isDirected() && network.allowsParallelEdges() && network.edges().size() > network.asGraph().edges().size() ? true : hasCycle(network.asGraph());
   }

   private static boolean subgraphHasCycle(Graph graph, Map visitedNodes, Object node, @Nullable Object previousNode) {
      NodeVisitState state = (NodeVisitState)visitedNodes.get(node);
      if (state == Graphs.NodeVisitState.COMPLETE) {
         return false;
      } else if (state == Graphs.NodeVisitState.PENDING) {
         return true;
      } else {
         visitedNodes.put(node, Graphs.NodeVisitState.PENDING);
         Iterator var5 = graph.successors(node).iterator();

         Object nextNode;
         do {
            if (!var5.hasNext()) {
               visitedNodes.put(node, Graphs.NodeVisitState.COMPLETE);
               return false;
            }

            nextNode = var5.next();
         } while(!canTraverseWithoutReusingEdge(graph, nextNode, previousNode) || !subgraphHasCycle(graph, visitedNodes, nextNode, node));

         return true;
      }
   }

   private static boolean canTraverseWithoutReusingEdge(Graph graph, Object nextNode, @Nullable Object previousNode) {
      return graph.isDirected() || !Objects.equal(previousNode, nextNode);
   }

   public static Graph transitiveClosure(Graph graph) {
      MutableGraph transitiveClosure = GraphBuilder.from(graph).allowsSelfLoops(true).build();
      if (graph.isDirected()) {
         Iterator var2 = graph.nodes().iterator();

         while(var2.hasNext()) {
            Object node = var2.next();
            Iterator var4 = reachableNodes(graph, node).iterator();

            while(var4.hasNext()) {
               Object reachableNode = var4.next();
               transitiveClosure.putEdge(node, reachableNode);
            }
         }

         return transitiveClosure;
      } else {
         Set visitedNodes = new HashSet();
         Iterator var12 = graph.nodes().iterator();

         while(true) {
            Object node;
            do {
               if (!var12.hasNext()) {
                  return transitiveClosure;
               }

               node = var12.next();
            } while(visitedNodes.contains(node));

            Set reachableNodes = reachableNodes(graph, node);
            visitedNodes.addAll(reachableNodes);
            int pairwiseMatch = 1;
            Iterator var7 = reachableNodes.iterator();

            while(var7.hasNext()) {
               Object nodeU = var7.next();
               Iterator var9 = Iterables.limit(reachableNodes, pairwiseMatch++).iterator();

               while(var9.hasNext()) {
                  Object nodeV = var9.next();
                  transitiveClosure.putEdge(nodeU, nodeV);
               }
            }
         }
      }
   }

   public static Set reachableNodes(Graph graph, Object node) {
      Preconditions.checkArgument(graph.nodes().contains(node), "Node %s is not an element of this graph.", node);
      Set visitedNodes = new LinkedHashSet();
      Queue queuedNodes = new ArrayDeque();
      visitedNodes.add(node);
      queuedNodes.add(node);

      while(!queuedNodes.isEmpty()) {
         Object currentNode = queuedNodes.remove();
         Iterator var5 = graph.successors(currentNode).iterator();

         while(var5.hasNext()) {
            Object successor = var5.next();
            if (visitedNodes.add(successor)) {
               queuedNodes.add(successor);
            }
         }
      }

      return Collections.unmodifiableSet(visitedNodes);
   }

   /** @deprecated */
   @Deprecated
   public static boolean equivalent(@Nullable Graph graphA, @Nullable Graph graphB) {
      return Objects.equal(graphA, graphB);
   }

   /** @deprecated */
   @Deprecated
   public static boolean equivalent(@Nullable ValueGraph graphA, @Nullable ValueGraph graphB) {
      return Objects.equal(graphA, graphB);
   }

   /** @deprecated */
   @Deprecated
   public static boolean equivalent(@Nullable Network networkA, @Nullable Network networkB) {
      return Objects.equal(networkA, networkB);
   }

   public static Graph transpose(Graph graph) {
      if (!graph.isDirected()) {
         return graph;
      } else {
         return (Graph)(graph instanceof TransposedGraph ? ((TransposedGraph)graph).graph : new TransposedGraph(graph));
      }
   }

   public static ValueGraph transpose(ValueGraph graph) {
      if (!graph.isDirected()) {
         return graph;
      } else {
         return (ValueGraph)(graph instanceof TransposedValueGraph ? ((TransposedValueGraph)graph).graph : new TransposedValueGraph(graph));
      }
   }

   public static Network transpose(Network network) {
      if (!network.isDirected()) {
         return network;
      } else {
         return (Network)(network instanceof TransposedNetwork ? ((TransposedNetwork)network).network : new TransposedNetwork(network));
      }
   }

   public static MutableGraph inducedSubgraph(Graph graph, Iterable nodes) {
      MutableGraph subgraph = GraphBuilder.from(graph).build();
      Iterator var3 = nodes.iterator();

      Object node;
      while(var3.hasNext()) {
         node = var3.next();
         subgraph.addNode(node);
      }

      var3 = subgraph.nodes().iterator();

      while(var3.hasNext()) {
         node = var3.next();
         Iterator var5 = graph.successors(node).iterator();

         while(var5.hasNext()) {
            Object successorNode = var5.next();
            if (subgraph.nodes().contains(successorNode)) {
               subgraph.putEdge(node, successorNode);
            }
         }
      }

      return subgraph;
   }

   public static MutableValueGraph inducedSubgraph(ValueGraph graph, Iterable nodes) {
      MutableValueGraph subgraph = ValueGraphBuilder.from(graph).build();
      Iterator var3 = nodes.iterator();

      Object node;
      while(var3.hasNext()) {
         node = var3.next();
         subgraph.addNode(node);
      }

      var3 = subgraph.nodes().iterator();

      while(var3.hasNext()) {
         node = var3.next();
         Iterator var5 = graph.successors(node).iterator();

         while(var5.hasNext()) {
            Object successorNode = var5.next();
            if (subgraph.nodes().contains(successorNode)) {
               subgraph.putEdgeValue(node, successorNode, graph.edgeValue(node, successorNode));
            }
         }
      }

      return subgraph;
   }

   public static MutableNetwork inducedSubgraph(Network network, Iterable nodes) {
      MutableNetwork subgraph = NetworkBuilder.from(network).build();
      Iterator var3 = nodes.iterator();

      Object node;
      while(var3.hasNext()) {
         node = var3.next();
         subgraph.addNode(node);
      }

      var3 = subgraph.nodes().iterator();

      while(var3.hasNext()) {
         node = var3.next();
         Iterator var5 = network.outEdges(node).iterator();

         while(var5.hasNext()) {
            Object edge = var5.next();
            Object successorNode = network.incidentNodes(edge).adjacentNode(node);
            if (subgraph.nodes().contains(successorNode)) {
               subgraph.addEdge(node, successorNode, edge);
            }
         }
      }

      return subgraph;
   }

   public static MutableGraph copyOf(Graph graph) {
      MutableGraph copy = GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
      Iterator var2 = graph.nodes().iterator();

      while(var2.hasNext()) {
         Object node = var2.next();
         copy.addNode(node);
      }

      var2 = graph.edges().iterator();

      while(var2.hasNext()) {
         EndpointPair edge = (EndpointPair)var2.next();
         copy.putEdge(edge.nodeU(), edge.nodeV());
      }

      return copy;
   }

   public static MutableValueGraph copyOf(ValueGraph graph) {
      MutableValueGraph copy = ValueGraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
      Iterator var2 = graph.nodes().iterator();

      while(var2.hasNext()) {
         Object node = var2.next();
         copy.addNode(node);
      }

      var2 = graph.edges().iterator();

      while(var2.hasNext()) {
         EndpointPair edge = (EndpointPair)var2.next();
         copy.putEdgeValue(edge.nodeU(), edge.nodeV(), graph.edgeValue(edge.nodeU(), edge.nodeV()));
      }

      return copy;
   }

   public static MutableNetwork copyOf(Network network) {
      MutableNetwork copy = NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
      Iterator var2 = network.nodes().iterator();

      Object edge;
      while(var2.hasNext()) {
         edge = var2.next();
         copy.addNode(edge);
      }

      var2 = network.edges().iterator();

      while(var2.hasNext()) {
         edge = var2.next();
         EndpointPair endpointPair = network.incidentNodes(edge);
         copy.addEdge(endpointPair.nodeU(), endpointPair.nodeV(), edge);
      }

      return copy;
   }

   @CanIgnoreReturnValue
   static int checkNonNegative(int value) {
      Preconditions.checkArgument(value >= 0, "Not true that %s is non-negative.", value);
      return value;
   }

   @CanIgnoreReturnValue
   static int checkPositive(int value) {
      Preconditions.checkArgument(value > 0, "Not true that %s is positive.", value);
      return value;
   }

   @CanIgnoreReturnValue
   static long checkNonNegative(long value) {
      Preconditions.checkArgument(value >= 0L, "Not true that %s is non-negative.", value);
      return value;
   }

   @CanIgnoreReturnValue
   static long checkPositive(long value) {
      Preconditions.checkArgument(value > 0L, "Not true that %s is positive.", value);
      return value;
   }

   private static enum NodeVisitState {
      PENDING,
      COMPLETE;
   }

   private static class TransposedNetwork extends AbstractNetwork {
      private final Network network;

      TransposedNetwork(Network network) {
         this.network = network;
      }

      public Set nodes() {
         return this.network.nodes();
      }

      public Set edges() {
         return this.network.edges();
      }

      public boolean isDirected() {
         return this.network.isDirected();
      }

      public boolean allowsParallelEdges() {
         return this.network.allowsParallelEdges();
      }

      public boolean allowsSelfLoops() {
         return this.network.allowsSelfLoops();
      }

      public ElementOrder nodeOrder() {
         return this.network.nodeOrder();
      }

      public ElementOrder edgeOrder() {
         return this.network.edgeOrder();
      }

      public Set adjacentNodes(Object node) {
         return this.network.adjacentNodes(node);
      }

      public Set predecessors(Object node) {
         return this.network.successors(node);
      }

      public Set successors(Object node) {
         return this.network.predecessors(node);
      }

      public Set incidentEdges(Object node) {
         return this.network.incidentEdges(node);
      }

      public Set inEdges(Object node) {
         return this.network.outEdges(node);
      }

      public Set outEdges(Object node) {
         return this.network.inEdges(node);
      }

      public EndpointPair incidentNodes(Object edge) {
         EndpointPair endpointPair = this.network.incidentNodes(edge);
         return EndpointPair.of(this.network, endpointPair.nodeV(), endpointPair.nodeU());
      }

      public Set adjacentEdges(Object edge) {
         return this.network.adjacentEdges(edge);
      }

      public Set edgesConnecting(Object nodeU, Object nodeV) {
         return this.network.edgesConnecting(nodeV, nodeU);
      }
   }

   private static class TransposedValueGraph extends AbstractValueGraph {
      private final ValueGraph graph;

      TransposedValueGraph(ValueGraph graph) {
         this.graph = graph;
      }

      public Set nodes() {
         return this.graph.nodes();
      }

      protected long edgeCount() {
         return (long)this.graph.edges().size();
      }

      public boolean isDirected() {
         return this.graph.isDirected();
      }

      public boolean allowsSelfLoops() {
         return this.graph.allowsSelfLoops();
      }

      public ElementOrder nodeOrder() {
         return this.graph.nodeOrder();
      }

      public Set adjacentNodes(Object node) {
         return this.graph.adjacentNodes(node);
      }

      public Set predecessors(Object node) {
         return this.graph.successors(node);
      }

      public Set successors(Object node) {
         return this.graph.predecessors(node);
      }

      public Object edgeValue(Object nodeU, Object nodeV) {
         return this.graph.edgeValue(nodeV, nodeU);
      }

      public Object edgeValueOrDefault(Object nodeU, Object nodeV, @Nullable Object defaultValue) {
         return this.graph.edgeValueOrDefault(nodeV, nodeU, defaultValue);
      }
   }

   private static class TransposedGraph extends AbstractGraph {
      private final Graph graph;

      TransposedGraph(Graph graph) {
         this.graph = graph;
      }

      public Set nodes() {
         return this.graph.nodes();
      }

      protected long edgeCount() {
         return (long)this.graph.edges().size();
      }

      public boolean isDirected() {
         return this.graph.isDirected();
      }

      public boolean allowsSelfLoops() {
         return this.graph.allowsSelfLoops();
      }

      public ElementOrder nodeOrder() {
         return this.graph.nodeOrder();
      }

      public Set adjacentNodes(Object node) {
         return this.graph.adjacentNodes(node);
      }

      public Set predecessors(Object node) {
         return this.graph.successors(node);
      }

      public Set successors(Object node) {
         return this.graph.predecessors(node);
      }
   }
}
