package org.python.google.common.graph;

import java.util.Collection;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

final class ConfigurableMutableNetwork extends ConfigurableNetwork implements MutableNetwork {
   ConfigurableMutableNetwork(NetworkBuilder builder) {
      super(builder);
   }

   @CanIgnoreReturnValue
   public boolean addNode(Object node) {
      Preconditions.checkNotNull(node, "node");
      if (this.containsNode(node)) {
         return false;
      } else {
         this.addNodeInternal(node);
         return true;
      }
   }

   @CanIgnoreReturnValue
   private NetworkConnections addNodeInternal(Object node) {
      NetworkConnections connections = this.newConnections();
      Preconditions.checkState(this.nodeConnections.put(node, connections) == null);
      return connections;
   }

   @CanIgnoreReturnValue
   public boolean addEdge(Object nodeU, Object nodeV, Object edge) {
      Preconditions.checkNotNull(nodeU, "nodeU");
      Preconditions.checkNotNull(nodeV, "nodeV");
      Preconditions.checkNotNull(edge, "edge");
      if (this.containsEdge(edge)) {
         EndpointPair existingIncidentNodes = this.incidentNodes(edge);
         EndpointPair newIncidentNodes = EndpointPair.of((Network)this, nodeU, nodeV);
         Preconditions.checkArgument(existingIncidentNodes.equals(newIncidentNodes), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", edge, existingIncidentNodes, newIncidentNodes);
         return false;
      } else {
         NetworkConnections connectionsU = (NetworkConnections)this.nodeConnections.get(nodeU);
         if (!this.allowsParallelEdges()) {
            Preconditions.checkArgument(connectionsU == null || !connectionsU.successors().contains(nodeV), "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", nodeU, nodeV);
         }

         boolean isSelfLoop = nodeU.equals(nodeV);
         if (!this.allowsSelfLoops()) {
            Preconditions.checkArgument(!isSelfLoop, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", nodeU);
         }

         if (connectionsU == null) {
            connectionsU = this.addNodeInternal(nodeU);
         }

         connectionsU.addOutEdge(edge, nodeV);
         NetworkConnections connectionsV = (NetworkConnections)this.nodeConnections.get(nodeV);
         if (connectionsV == null) {
            connectionsV = this.addNodeInternal(nodeV);
         }

         connectionsV.addInEdge(edge, nodeU, isSelfLoop);
         this.edgeToReferenceNode.put(edge, nodeU);
         return true;
      }
   }

   @CanIgnoreReturnValue
   public boolean removeNode(Object node) {
      Preconditions.checkNotNull(node, "node");
      NetworkConnections connections = (NetworkConnections)this.nodeConnections.get(node);
      if (connections == null) {
         return false;
      } else {
         UnmodifiableIterator var3 = ImmutableList.copyOf((Collection)connections.incidentEdges()).iterator();

         while(var3.hasNext()) {
            Object edge = var3.next();
            this.removeEdge(edge);
         }

         this.nodeConnections.remove(node);
         return true;
      }
   }

   @CanIgnoreReturnValue
   public boolean removeEdge(Object edge) {
      Preconditions.checkNotNull(edge, "edge");
      Object nodeU = this.edgeToReferenceNode.get(edge);
      if (nodeU == null) {
         return false;
      } else {
         NetworkConnections connectionsU = (NetworkConnections)this.nodeConnections.get(nodeU);
         Object nodeV = connectionsU.oppositeNode(edge);
         NetworkConnections connectionsV = (NetworkConnections)this.nodeConnections.get(nodeV);
         connectionsU.removeOutEdge(edge);
         connectionsV.removeInEdge(edge, this.allowsSelfLoops() && nodeU.equals(nodeV));
         this.edgeToReferenceNode.remove(edge);
         return true;
      }
   }

   private NetworkConnections newConnections() {
      return (NetworkConnections)(this.isDirected() ? (this.allowsParallelEdges() ? DirectedMultiNetworkConnections.of() : DirectedNetworkConnections.of()) : (this.allowsParallelEdges() ? UndirectedMultiNetworkConnections.of() : UndirectedNetworkConnections.of()));
   }
}
