package weblogic.ejb.container.utils.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedGraphImpl implements DirectedGraph {
   private final int maxVerts;
   private final List vertexList;
   private final boolean[][] matrix;
   private int numVerts;
   private final Map idMap = new HashMap();
   private TransitiveClosure tc;
   private TopologicalSorter tSorter;

   public DirectedGraphImpl(DirectedGraphImpl source) {
      this.maxVerts = source.maxVerts;
      this.vertexList = new ArrayList(source.vertexList);
      this.matrix = new boolean[this.maxVerts][this.maxVerts];

      for(int i = 0; i < this.maxVerts; ++i) {
         for(int k = 0; k < this.maxVerts; ++k) {
            this.matrix[i][k] = source.matrix[i][k];
         }
      }

      this.numVerts = source.numVerts;
      this.idMap.putAll(source.idMap);
      this.tc = source.tc;
      this.tSorter = source.tSorter;
   }

   public DirectedGraphImpl(int maxVerts) {
      this.maxVerts = maxVerts;
      this.vertexList = new ArrayList(maxVerts);
      this.matrix = new boolean[maxVerts][maxVerts];

      for(int i = 0; i < maxVerts; ++i) {
         for(int k = 0; k < maxVerts; ++k) {
            this.matrix[i][k] = false;
         }
      }

      this.numVerts = 0;
   }

   public int getNumVerts() {
      return this.numVerts;
   }

   public Set getCurrentVertices() {
      Set vs = new HashSet(this.vertexList);
      vs.remove((Object)null);
      return vs;
   }

   public int addVertex(Object v) {
      if (null == v) {
         throw new IllegalArgumentException("Input vertex can't be null");
      } else {
         Integer id = (Integer)this.idMap.get(v);
         if (null == id) {
            id = this.getAvailableID();
            if (-1 == id) {
               id = this.numVerts;
               this.vertexList.add(id, v);
            } else {
               this.vertexList.set(id, v);
            }

            this.idMap.put(v, id);
            ++this.numVerts;
            this.clearStale();
            return id;
         } else {
            return -1;
         }
      }
   }

   private int getAvailableID() {
      for(int i = 0; i < this.vertexList.size(); ++i) {
         Object t = this.vertexList.get(i);
         if (null == t) {
            return i;
         }
      }

      return -1;
   }

   public void addEdge(Object sv, Object ev) {
      if (null != sv && null != ev) {
         Integer sid = (Integer)this.idMap.get(sv);
         if (null == sid) {
            sid = this.addVertex(sv);
         }

         Integer eid = (Integer)this.idMap.get(ev);
         if (null == eid) {
            eid = this.addVertex(ev);
         }

         this.matrix[sid][eid] = true;
         this.clearStale();
      } else {
         throw new IllegalArgumentException("Input vertices can't be null");
      }
   }

   public int deleteVertex(Object v) {
      if (null == v) {
         throw new IllegalArgumentException("Input vertex can't be null");
      } else {
         Integer id = (Integer)this.idMap.get(v);
         if (null == id) {
            return -1;
         } else {
            this.idMap.remove(v);
            this.vertexList.set(id, (Object)null);
            int size = this.vertexList.size();

            for(int i = 0; i < size; ++i) {
               this.matrix[i][id] = false;
               this.matrix[id][i] = false;
            }

            --this.numVerts;
            this.clearStale();
            return id;
         }
      }
   }

   private void clearStale() {
      if (null != this.tc) {
         this.tc = null;
      }

      if (null != this.tSorter) {
         this.tSorter = null;
      }

   }

   public void verify() throws CyclicDependencyException {
      if (null == this.tc) {
         this.tc = new TransitiveClosure();
      }

      this.tc.checkCyclicDependency();
   }

   public List getVerticesInPathTo(Object t) throws CyclicDependencyException {
      if (null == this.tc) {
         this.tc = new TransitiveClosure();
         this.tc.checkCyclicDependency();
      }

      if (null == this.tSorter) {
         this.tSorter = new TopologicalSorter(this);
      }

      return this.getLinearOrder(this.tSorter.sort(), this.tc.getVerticesInPathTo(t));
   }

   public List getVerticesReachableFrom(Object t) throws CyclicDependencyException {
      if (null == this.tc) {
         this.tc = new TransitiveClosure();
         this.tc.checkCyclicDependency();
      }

      if (null == this.tSorter) {
         this.tSorter = new TopologicalSorter(this);
      }

      return this.getLinearOrder(this.tSorter.sort(), this.tc.getVerticesReachableFrom(t));
   }

   private List getLinearOrder(List sortedList, Set dependencies) {
      List sil = new ArrayList();
      Iterator var4 = sortedList.iterator();

      while(true) {
         while(var4.hasNext()) {
            Object vs = var4.next();
            Iterator var6 = dependencies.iterator();

            while(var6.hasNext()) {
               Object vd = var6.next();
               if (vs.equals(vd)) {
                  sil.add(vs);
                  dependencies.remove(vd);
                  break;
               }
            }
         }

         return sil;
      }
   }

   public List topologicalSort() throws CyclicDependencyException {
      if (null == this.tSorter) {
         this.tSorter = new TopologicalSorter(this);
      }

      return this.tSorter.sort();
   }

   private Object noPredecessor() {
      int size = this.vertexList.size();

      for(int col = 0; col < size; ++col) {
         if (null != this.vertexList.get(col)) {
            boolean isEdge = false;

            for(int row = 0; row < size; ++row) {
               if (this.matrix[row][col]) {
                  isEdge = true;
                  break;
               }
            }

            if (!isEdge) {
               return this.vertexList.get(col);
            }
         }
      }

      return null;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < this.maxVerts; ++i) {
         for(int k = 0; k < this.maxVerts; ++k) {
            if (this.matrix[i][k]) {
               sb.append("1 ");
            } else {
               sb.append("0 ");
            }
         }

         sb.append("\n");
      }

      return sb.toString();
   }

   private final class TopologicalSorter {
      private final DirectedGraphImpl g;
      private List verticesInLinearOrder;

      TopologicalSorter(DirectedGraphImpl graph) {
         this.g = new DirectedGraphImpl(graph);
      }

      public List sort() throws CyclicDependencyException {
         if (null != this.verticesInLinearOrder) {
            return this.verticesInLinearOrder;
         } else {
            this.verticesInLinearOrder = new ArrayList();

            while(this.g.getNumVerts() > 0) {
               Object currentVertex = this.g.noPredecessor();
               if (null == currentVertex) {
                  Set vs = this.g.getCurrentVertices();
                  Set vss = new HashSet();
                  Iterator var4 = vs.iterator();

                  while(var4.hasNext()) {
                     Object v = var4.next();
                     vss.add(v.toString());
                  }

                  throw new CyclicDependencyException(vss);
               }

               this.verticesInLinearOrder.add(currentVertex);
               this.g.deleteVertex(currentVertex);
            }

            return this.verticesInLinearOrder;
         }
      }
   }

   private final class TransitiveClosure {
      private final int tcMaxVerts;
      private final boolean[][] tcMatrix;
      private final List tcVertexList;
      private final Map tcIdMap;

      TransitiveClosure() {
         this.tcMaxVerts = DirectedGraphImpl.this.vertexList.size();
         this.tcMatrix = new boolean[this.tcMaxVerts][this.tcMaxVerts];

         for(int i = 0; i < this.tcMaxVerts; ++i) {
            for(int k = 0; k < this.tcMaxVerts; ++k) {
               this.tcMatrix[i][k] = DirectedGraphImpl.this.matrix[i][k];
            }
         }

         this.tcVertexList = new ArrayList(DirectedGraphImpl.this.vertexList);
         this.tcIdMap = new HashMap(DirectedGraphImpl.this.idMap);
         this.computeTranstiveClosure();
      }

      public void checkCyclicDependency() throws CyclicDependencyException {
         Set vs = null;

         for(int i = 0; i < this.tcMaxVerts; ++i) {
            if (this.tcMatrix[i][i]) {
               if (null == vs) {
                  vs = new HashSet();
               }

               vs.add(this.tcVertexList.get(i).toString());
            }
         }

         if (null != vs) {
            throw new CyclicDependencyException(vs);
         }
      }

      private void computeTranstiveClosure() {
         for(int k = 0; k < this.tcMaxVerts; ++k) {
            for(int i = 0; i < this.tcMaxVerts; ++i) {
               if (this.tcMatrix[i][k]) {
                  for(int j = 0; j < this.tcMaxVerts; ++j) {
                     this.tcMatrix[i][j] = this.tcMatrix[i][j] || this.tcMatrix[k][j];
                  }
               }
            }
         }

      }

      public Set getVerticesInPathTo(Object v) {
         Set vs = new HashSet();
         Integer col = (Integer)this.tcIdMap.get(v);
         if (null == col) {
            return vs;
         } else {
            for(int i = 0; i < this.tcMaxVerts; ++i) {
               if (this.tcMatrix[i][col]) {
                  vs.add(this.tcVertexList.get(i));
               }
            }

            return vs;
         }
      }

      public Set getVerticesReachableFrom(Object v) {
         Set vs = new HashSet();
         Integer row = (Integer)this.tcIdMap.get(v);
         if (null == row) {
            return vs;
         } else {
            for(int k = 0; k < this.tcMaxVerts; ++k) {
               if (this.tcMatrix[row][k]) {
                  vs.add(this.tcVertexList.get(k));
               }
            }

            return vs;
         }
      }
   }
}
