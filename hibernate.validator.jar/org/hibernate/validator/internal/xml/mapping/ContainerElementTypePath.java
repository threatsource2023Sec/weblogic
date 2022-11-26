package org.hibernate.validator.internal.xml.mapping;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.internal.util.StringHelper;

public class ContainerElementTypePath {
   private final List nodes;

   private ContainerElementTypePath(List nodes) {
      this.nodes = nodes;
   }

   public static ContainerElementTypePath root() {
      return new ContainerElementTypePath(new ArrayList());
   }

   public static ContainerElementTypePath of(ContainerElementTypePath parentPath, Integer typeArgumentIndex) {
      List nodes = new ArrayList(parentPath.nodes);
      nodes.add(typeArgumentIndex);
      return new ContainerElementTypePath(nodes);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ContainerElementTypePath other = (ContainerElementTypePath)obj;
         return this.nodes.equals(other.nodes);
      }
   }

   public int hashCode() {
      return this.nodes.hashCode();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[").append(StringHelper.join((Iterable)this.nodes, ", ")).append("]");
      return sb.toString();
   }
}
