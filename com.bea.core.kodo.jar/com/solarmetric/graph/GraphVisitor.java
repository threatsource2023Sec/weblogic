package com.solarmetric.graph;

public interface GraphVisitor {
   void nodeSeen(Object var1);

   void nodeVisited(Object var1);

   void edgeVisited(Edge var1);
}
