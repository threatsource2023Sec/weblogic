package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;

public class DiGraph {
   private final Map vertices = new HashMap();

   public void addVertex(Object data) {
      if (this.vertices.containsKey(data)) {
         throw new IllegalArgumentException("Vertex already exists");
      } else {
         this.addVertexPrechecked(data);
      }
   }

   public void ensureVertex(Object data) {
      if (this.vertices.get(data) == null) {
         this.addVertexPrechecked(data);
      }

   }

   public void addEdge(Object fromData, Object toData) {
      Vertex from = (Vertex)this.vertices.get(fromData);
      Vertex to = (Vertex)this.vertices.get(toData);
      this.checkEdgeVertices(from, to);
      this.addEdgePrechecked(from, to);
   }

   public boolean ensureEdge(Object fromData, Object toData) {
      Vertex from = (Vertex)this.vertices.get(fromData);
      Vertex to = (Vertex)this.vertices.get(toData);
      this.checkEdgeVertices(from, to);
      if (from != null) {
         Iterator var5 = from.outboundEdges.iterator();

         while(var5.hasNext()) {
            Edge out = (Edge)var5.next();
            if (to.equals(out.to)) {
               return false;
            }
         }
      }

      this.addEdgePrechecked(from, to);
      return true;
   }

   public boolean contains(Object data) {
      return this.getVertex(data) != null;
   }

   public void breadthFirst(Object startingData, Object endingData, Visitor visitor) {
      Vertex startingVertex = this.getVertex(startingData);
      if (startingVertex == null) {
         throw new IllegalArgumentException("Starting data value for traversal not found in graph");
      } else {
         Vertex endingVertex = null;
         if (endingData != null) {
            endingVertex = this.getVertex(endingData);
            if (endingVertex == null) {
               throw new IllegalArgumentException("Ending data value for traversal not found in graph");
            }
         }

         this.breadthFirst(startingVertex, endingVertex, visitor);
      }
   }

   private Vertex getVertex(Object data) {
      return (Vertex)this.vertices.get(data);
   }

   private Vertex addVertexPrechecked(Object data) {
      Vertex v = new Vertex(data);
      this.vertices.put(data, v);
      return v;
   }

   private Edge addEdgePrechecked(Vertex from, Vertex to) {
      Edge edge = new Edge(from, to);
      from.addOutboundEdge(edge);
      to.addInboundEdge(edge);
      return edge;
   }

   private void checkEdgeVertices(Vertex from, Vertex to) {
      if (from == null) {
         throw new IllegalArgumentException("Attempt to add edge from unknown vertex");
      } else if (to == null) {
         throw new IllegalArgumentException("Attempt to add edge to unknown vertex");
      }
   }

   private void breadthFirst(Vertex startingVertex, Vertex endingVertex, Visitor visitor) {
      LinkedList visitationQueue = new LinkedList();
      visitationQueue.add(startingVertex);
      EdgeTracker edgeTracker = new EdgeTracker(this.vertices.values(), startingVertex);

      try {
         Iterator var7;
         label52:
         while(!visitationQueue.isEmpty()) {
            Vertex v = (Vertex)visitationQueue.removeFirst();
            visitor.visit(v.data);
            var7 = v.outboundEdges.iterator();

            while(true) {
               Edge out;
               boolean isReady;
               do {
                  if (!var7.hasNext()) {
                     continue label52;
                  }

                  out = (Edge)var7.next();
                  isReady = edgeTracker.traverse(out);
               } while(endingVertex != null && endingVertex.equals(out.to));

               if (isReady) {
                  visitationQueue.add(out.to);
               }
            }
         }

         Set entrySet = edgeTracker.allInboundEdges.entrySet();
         if (!entrySet.isEmpty()) {
            PartitionLifecycleDebugger.debug("After traversal some edges remain:");
            var7 = entrySet.iterator();

            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               StringBuilder sb = new StringBuilder("  To " + ((Vertex)entry.getKey()).data + " from ");
               Iterator var10 = ((List)entry.getValue()).iterator();

               while(var10.hasNext()) {
                  Edge edge = (Edge)var10.next();
                  sb.append(edge.from.data).append(" ,");
               }

               PartitionLifecycleDebugger.debug(sb.toString());
            }
         }
      } catch (Exception var12) {
         PartitionLifecycleDebugger.debug("BreadthFirst error: " + var12.getMessage());
      }

   }

   private class EdgeTracker {
      private Map allInboundEdges = new HashMap();

      EdgeTracker(Collection vertices, Vertex startingVertex) {
         Iterator var4 = vertices.iterator();

         while(true) {
            Vertex v;
            do {
               if (!var4.hasNext()) {
                  return;
               }

               v = (Vertex)var4.next();
            } while(v == startingVertex);

            List inbounds = (List)this.allInboundEdges.get(v);
            if (inbounds == null) {
               inbounds = new ArrayList();
               this.allInboundEdges.put(v, inbounds);
            }

            Iterator var7 = v.inboundEdges.iterator();

            while(var7.hasNext()) {
               Edge inbound = (Edge)var7.next();
               ((List)inbounds).add(inbound);
            }
         }
      }

      boolean traverse(Edge edge) {
         List inbounds = (List)this.allInboundEdges.get(edge.to);
         inbounds.remove(edge);
         if (inbounds.isEmpty()) {
            this.allInboundEdges.remove(edge.to);
         }

         return inbounds.isEmpty();
      }
   }

   private static class Edge {
      private final Vertex from;
      private final Vertex to;

      Edge(Vertex from, Vertex to) {
         this.from = from;
         this.to = to;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            Edge edge = (Edge)o;
            if (this.from != null) {
               if (!this.from.equals(edge.from)) {
                  return false;
               }
            } else if (edge.from != null) {
               return false;
            }

            if (this.to != null) {
               if (this.to.equals(edge.to)) {
                  return true;
               }
            } else if (edge.to == null) {
               return true;
            }

            return false;
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.from != null ? this.from.hashCode() : 0;
         result = 31 * result + (this.to != null ? this.to.hashCode() : 0);
         return result;
      }
   }

   private static class Vertex {
      private final Object data;
      private final List inboundEdges = new ArrayList();
      private final List outboundEdges = new ArrayList();

      Vertex(Object data) {
         this.data = data;
      }

      void addInboundEdge(Edge inbound) {
         this.inboundEdges.add(inbound);
      }

      void addOutboundEdge(Edge outbound) {
         this.outboundEdges.add(outbound);
      }

      public boolean equals(Object obj) {
         if (obj instanceof Vertex && obj != null) {
            Vertex other = (Vertex)obj;
            return this.data.equals(other.data);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.data.hashCode();
      }
   }

   public interface Visitor {
      void visit(Object var1);
   }
}
