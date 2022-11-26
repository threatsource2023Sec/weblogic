package weblogic.ejb.container.utils.graph;

import java.util.Iterator;
import java.util.Set;

public class CyclicDependencyException extends Exception {
   private static final long serialVersionUID = -6904504077828406051L;
   private final Set vs;

   public CyclicDependencyException(Set vs) {
      this.vs = vs;
   }

   public String getMessage() {
      StringBuilder sb = new StringBuilder();
      sb.append("Cyclic dependencies detected among following elements: ");
      Iterator var2 = this.vs.iterator();

      while(var2.hasNext()) {
         String v = (String)var2.next();
         sb.append(v).append("|");
      }

      return sb.substring(0, sb.length() - 1);
   }
}
