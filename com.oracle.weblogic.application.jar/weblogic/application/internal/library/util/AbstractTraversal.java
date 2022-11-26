package weblogic.application.internal.library.util;

import java.util.List;

public abstract class AbstractTraversal implements Traversal {
   public Object getNextEdge(RONode node) {
      return null;
   }

   public Object[] getNextEdges(RONode node) {
      return null;
   }

   public void visit(RONode node, List path) {
   }

   public void visitLeaf(RONode node, List path) {
   }
}
