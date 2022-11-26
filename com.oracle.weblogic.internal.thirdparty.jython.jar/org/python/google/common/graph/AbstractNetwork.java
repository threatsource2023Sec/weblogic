package org.python.google.common.graph;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Function;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Iterators;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.Sets;
import org.python.google.common.math.IntMath;

@Beta
public abstract class AbstractNetwork implements Network {
   public Graph asGraph() {
      return new AbstractGraph() {
         public Set nodes() {
            return AbstractNetwork.this.nodes();
         }

         public Set edges() {
            return (Set)(AbstractNetwork.this.allowsParallelEdges() ? super.edges() : new AbstractSet() {
               public Iterator iterator() {
                  return Iterators.transform(AbstractNetwork.this.edges().iterator(), new Function() {
                     public EndpointPair apply(Object edge) {
                        return AbstractNetwork.this.incidentNodes(edge);
                     }
                  });
               }

               public int size() {
                  return AbstractNetwork.this.edges().size();
               }

               public boolean contains(@Nullable Object obj) {
                  if (!(obj instanceof EndpointPair)) {
                     return false;
                  } else {
                     EndpointPair endpointPair = (EndpointPair)obj;
                     return isDirected() == endpointPair.isOrdered() && nodes().contains(endpointPair.nodeU()) && successors(endpointPair.nodeU()).contains(endpointPair.nodeV());
                  }
               }
            });
         }

         public ElementOrder nodeOrder() {
            return AbstractNetwork.this.nodeOrder();
         }

         public boolean isDirected() {
            return AbstractNetwork.this.isDirected();
         }

         public boolean allowsSelfLoops() {
            return AbstractNetwork.this.allowsSelfLoops();
         }

         public Set adjacentNodes(Object node) {
            return AbstractNetwork.this.adjacentNodes(node);
         }

         public Set predecessors(Object node) {
            return AbstractNetwork.this.predecessors(node);
         }

         public Set successors(Object node) {
            return AbstractNetwork.this.successors(node);
         }
      };
   }

   public int degree(Object node) {
      return this.isDirected() ? IntMath.saturatedAdd(this.inEdges(node).size(), this.outEdges(node).size()) : IntMath.saturatedAdd(this.incidentEdges(node).size(), this.edgesConnecting(node, node).size());
   }

   public int inDegree(Object node) {
      return this.isDirected() ? this.inEdges(node).size() : this.degree(node);
   }

   public int outDegree(Object node) {
      return this.isDirected() ? this.outEdges(node).size() : this.degree(node);
   }

   public Set adjacentEdges(Object edge) {
      EndpointPair endpointPair = this.incidentNodes(edge);
      Set endpointPairIncidentEdges = Sets.union(this.incidentEdges(endpointPair.nodeU()), this.incidentEdges(endpointPair.nodeV()));
      return Sets.difference(endpointPairIncidentEdges, ImmutableSet.of(edge));
   }

   public final boolean equals(@Nullable Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof Network)) {
         return false;
      } else {
         Network other = (Network)obj;
         return this.isDirected() == other.isDirected() && this.nodes().equals(other.nodes()) && edgeIncidentNodesMap(this).equals(edgeIncidentNodesMap(other));
      }
   }

   public final int hashCode() {
      return edgeIncidentNodesMap(this).hashCode();
   }

   public String toString() {
      String propertiesString = String.format("isDirected: %s, allowsParallelEdges: %s, allowsSelfLoops: %s", this.isDirected(), this.allowsParallelEdges(), this.allowsSelfLoops());
      return String.format("%s, nodes: %s, edges: %s", propertiesString, this.nodes(), edgeIncidentNodesMap(this));
   }

   private static Map edgeIncidentNodesMap(final Network network) {
      Function edgeToIncidentNodesFn = new Function() {
         public EndpointPair apply(Object edge) {
            return network.incidentNodes(edge);
         }
      };
      return Maps.asMap(network.edges(), edgeToIncidentNodesFn);
   }
}
