package weblogic.ejb.container.utils.graph;

import java.util.List;
import java.util.Set;

public interface DirectedGraph {
   int getNumVerts();

   Set getCurrentVertices();

   int addVertex(Object var1);

   void addEdge(Object var1, Object var2);

   int deleteVertex(Object var1);

   void verify() throws CyclicDependencyException;

   List topologicalSort() throws CyclicDependencyException;

   List getVerticesInPathTo(Object var1) throws CyclicDependencyException;

   List getVerticesReachableFrom(Object var1) throws CyclicDependencyException;
}
