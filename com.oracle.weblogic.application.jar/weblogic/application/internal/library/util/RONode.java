package weblogic.application.internal.library.util;

import java.util.Set;

public interface RONode {
   int getDepth();

   boolean hasEdge(Object var1);

   Object getValue();

   Set getEdges();

   Object getHighestEdge();

   boolean isLeafNode();

   boolean hasValue();

   boolean hasChildren();

   int getNumChildren();
}
