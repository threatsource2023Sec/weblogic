package org.python.google.common.graph;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableSet;

class ConfigurableNetwork extends AbstractNetwork {
   private final boolean isDirected;
   private final boolean allowsParallelEdges;
   private final boolean allowsSelfLoops;
   private final ElementOrder nodeOrder;
   private final ElementOrder edgeOrder;
   protected final MapIteratorCache nodeConnections;
   protected final MapIteratorCache edgeToReferenceNode;

   ConfigurableNetwork(NetworkBuilder builder) {
      this(builder, builder.nodeOrder.createMap((Integer)builder.expectedNodeCount.or((int)10)), builder.edgeOrder.createMap((Integer)builder.expectedEdgeCount.or((int)20)));
   }

   ConfigurableNetwork(NetworkBuilder builder, Map nodeConnections, Map edgeToReferenceNode) {
      this.isDirected = builder.directed;
      this.allowsParallelEdges = builder.allowsParallelEdges;
      this.allowsSelfLoops = builder.allowsSelfLoops;
      this.nodeOrder = builder.nodeOrder.cast();
      this.edgeOrder = builder.edgeOrder.cast();
      this.nodeConnections = (MapIteratorCache)(nodeConnections instanceof TreeMap ? new MapRetrievalCache(nodeConnections) : new MapIteratorCache(nodeConnections));
      this.edgeToReferenceNode = new MapIteratorCache(edgeToReferenceNode);
   }

   public Set nodes() {
      return this.nodeConnections.unmodifiableKeySet();
   }

   public Set edges() {
      return this.edgeToReferenceNode.unmodifiableKeySet();
   }

   public boolean isDirected() {
      return this.isDirected;
   }

   public boolean allowsParallelEdges() {
      return this.allowsParallelEdges;
   }

   public boolean allowsSelfLoops() {
      return this.allowsSelfLoops;
   }

   public ElementOrder nodeOrder() {
      return this.nodeOrder;
   }

   public ElementOrder edgeOrder() {
      return this.edgeOrder;
   }

   public Set incidentEdges(Object node) {
      return this.checkedConnections(node).incidentEdges();
   }

   public EndpointPair incidentNodes(Object edge) {
      Object nodeU = this.checkedReferenceNode(edge);
      Object nodeV = ((NetworkConnections)this.nodeConnections.get(nodeU)).oppositeNode(edge);
      return EndpointPair.of((Network)this, nodeU, nodeV);
   }

   public Set adjacentNodes(Object node) {
      return this.checkedConnections(node).adjacentNodes();
   }

   public Set edgesConnecting(Object nodeU, Object nodeV) {
      NetworkConnections connectionsU = this.checkedConnections(nodeU);
      if (!this.allowsSelfLoops && nodeU == nodeV) {
         return ImmutableSet.of();
      } else {
         Preconditions.checkArgument(this.containsNode(nodeV), "Node %s is not an element of this graph.", nodeV);
         return connectionsU.edgesConnecting(nodeV);
      }
   }

   public Set inEdges(Object node) {
      return this.checkedConnections(node).inEdges();
   }

   public Set outEdges(Object node) {
      return this.checkedConnections(node).outEdges();
   }

   public Set predecessors(Object node) {
      return this.checkedConnections(node).predecessors();
   }

   public Set successors(Object node) {
      return this.checkedConnections(node).successors();
   }

   protected final NetworkConnections checkedConnections(Object node) {
      NetworkConnections connections = (NetworkConnections)this.nodeConnections.get(node);
      if (connections == null) {
         Preconditions.checkNotNull(node);
         throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", node));
      } else {
         return connections;
      }
   }

   protected final Object checkedReferenceNode(Object edge) {
      Object referenceNode = this.edgeToReferenceNode.get(edge);
      if (referenceNode == null) {
         Preconditions.checkNotNull(edge);
         throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", edge));
      } else {
         return referenceNode;
      }
   }

   protected final boolean containsNode(@Nullable Object node) {
      return this.nodeConnections.containsKey(node);
   }

   protected final boolean containsEdge(@Nullable Object edge) {
      return this.edgeToReferenceNode.containsKey(edge);
   }
}
