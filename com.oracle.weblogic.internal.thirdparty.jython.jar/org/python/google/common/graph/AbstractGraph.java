package org.python.google.common.graph;

import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;

@Beta
public abstract class AbstractGraph extends AbstractBaseGraph implements Graph {
   public final boolean equals(@Nullable Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof Graph)) {
         return false;
      } else {
         Graph other = (Graph)obj;
         return this.isDirected() == other.isDirected() && this.nodes().equals(other.nodes()) && this.edges().equals(other.edges());
      }
   }

   public final int hashCode() {
      return this.edges().hashCode();
   }

   public String toString() {
      String propertiesString = String.format("isDirected: %s, allowsSelfLoops: %s", this.isDirected(), this.allowsSelfLoops());
      return String.format("%s, nodes: %s, edges: %s", propertiesString, this.nodes(), this.edges());
   }
}
