package org.python.google.common.graph;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;
import org.python.google.common.base.Preconditions;

class ConfigurableValueGraph extends AbstractValueGraph {
   private final boolean isDirected;
   private final boolean allowsSelfLoops;
   private final ElementOrder nodeOrder;
   protected final MapIteratorCache nodeConnections;
   protected long edgeCount;

   ConfigurableValueGraph(AbstractGraphBuilder builder) {
      this(builder, builder.nodeOrder.createMap((Integer)builder.expectedNodeCount.or((int)10)), 0L);
   }

   ConfigurableValueGraph(AbstractGraphBuilder builder, Map nodeConnections, long edgeCount) {
      this.isDirected = builder.directed;
      this.allowsSelfLoops = builder.allowsSelfLoops;
      this.nodeOrder = builder.nodeOrder.cast();
      this.nodeConnections = (MapIteratorCache)(nodeConnections instanceof TreeMap ? new MapRetrievalCache(nodeConnections) : new MapIteratorCache(nodeConnections));
      this.edgeCount = Graphs.checkNonNegative(edgeCount);
   }

   public Set nodes() {
      return this.nodeConnections.unmodifiableKeySet();
   }

   public boolean isDirected() {
      return this.isDirected;
   }

   public boolean allowsSelfLoops() {
      return this.allowsSelfLoops;
   }

   public ElementOrder nodeOrder() {
      return this.nodeOrder;
   }

   public Set adjacentNodes(Object node) {
      return this.checkedConnections(node).adjacentNodes();
   }

   public Set predecessors(Object node) {
      return this.checkedConnections(node).predecessors();
   }

   public Set successors(Object node) {
      return this.checkedConnections(node).successors();
   }

   public Object edgeValueOrDefault(Object nodeU, Object nodeV, @Nullable Object defaultValue) {
      GraphConnections connectionsU = (GraphConnections)this.nodeConnections.get(nodeU);
      if (connectionsU == null) {
         return defaultValue;
      } else {
         Object value = connectionsU.value(nodeV);
         return value == null ? defaultValue : value;
      }
   }

   protected long edgeCount() {
      return this.edgeCount;
   }

   protected final GraphConnections checkedConnections(Object node) {
      GraphConnections connections = (GraphConnections)this.nodeConnections.get(node);
      if (connections == null) {
         Preconditions.checkNotNull(node);
         throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", node));
      } else {
         return connections;
      }
   }

   protected final boolean containsNode(@Nullable Object node) {
      return this.nodeConnections.containsKey(node);
   }
}
