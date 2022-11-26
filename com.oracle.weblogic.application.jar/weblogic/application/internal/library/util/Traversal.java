package weblogic.application.internal.library.util;

import java.util.List;

public interface Traversal {
   void visit(RONode var1, List var2);

   void visitLeaf(RONode var1, List var2);

   Object getNextEdge(RONode var1);

   Object[] getNextEdges(RONode var1);
}
