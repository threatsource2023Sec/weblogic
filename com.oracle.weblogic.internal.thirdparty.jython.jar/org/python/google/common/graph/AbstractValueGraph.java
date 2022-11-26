package org.python.google.common.graph;

import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Maps;

@Beta
public abstract class AbstractValueGraph extends AbstractBaseGraph implements ValueGraph {
   public Graph asGraph() {
      return new AbstractGraph() {
         public Set nodes() {
            return AbstractValueGraph.this.nodes();
         }

         public Set edges() {
            return AbstractValueGraph.this.edges();
         }

         public boolean isDirected() {
            return AbstractValueGraph.this.isDirected();
         }

         public boolean allowsSelfLoops() {
            return AbstractValueGraph.this.allowsSelfLoops();
         }

         public ElementOrder nodeOrder() {
            return AbstractValueGraph.this.nodeOrder();
         }

         public Set adjacentNodes(Object node) {
            return AbstractValueGraph.this.adjacentNodes(node);
         }

         public Set predecessors(Object node) {
            return AbstractValueGraph.this.predecessors(node);
         }

         public Set successors(Object node) {
            return AbstractValueGraph.this.successors(node);
         }

         public int degree(Object node) {
            return AbstractValueGraph.this.degree(node);
         }

         public int inDegree(Object node) {
            return AbstractValueGraph.this.inDegree(node);
         }

         public int outDegree(Object node) {
            return AbstractValueGraph.this.outDegree(node);
         }
      };
   }

   public Object edgeValue(Object nodeU, Object nodeV) {
      Object value = this.edgeValueOrDefault(nodeU, nodeV, (Object)null);
      if (value == null) {
         Preconditions.checkArgument(this.nodes().contains(nodeU), "Node %s is not an element of this graph.", nodeU);
         Preconditions.checkArgument(this.nodes().contains(nodeV), "Node %s is not an element of this graph.", nodeV);
         throw new IllegalArgumentException(String.format("Edge connecting %s to %s is not present in this graph.", nodeU, nodeV));
      } else {
         return value;
      }
   }

   public final boolean equals(@Nullable Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof ValueGraph)) {
         return false;
      } else {
         ValueGraph other = (ValueGraph)obj;
         return this.isDirected() == other.isDirected() && this.nodes().equals(other.nodes()) && edgeValueMap(this).equals(edgeValueMap(other));
      }
   }

   public final int hashCode() {
      return edgeValueMap(this).hashCode();
   }

   public String toString() {
      String propertiesString = String.format("isDirected: %s, allowsSelfLoops: %s", this.isDirected(), this.allowsSelfLoops());
      return String.format("%s, nodes: %s, edges: %s", propertiesString, this.nodes(), edgeValueMap(this));
   }

   private static Map edgeValueMap(final ValueGraph graph) {
      Function edgeToValueFn = new Function() {
         public Object apply(EndpointPair edge) {
            return graph.edgeValue(edge.nodeU(), edge.nodeV());
         }
      };
      return Maps.asMap(graph.edges(), edgeToValueFn);
   }
}
