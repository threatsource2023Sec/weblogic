package weblogic.servlet.internal.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TopologicalSortingGraph {
   private Map nodes = new HashMap();

   public static Set createGraphs(Set allNodes, List edges) {
      Set nodeSet = new HashSet();
      nodeSet.addAll(allNodes);
      Set graphSet = new HashSet();
      Iterator var4 = edges.iterator();

      while(true) {
         while(true) {
            Edge edge;
            do {
               do {
                  if (!var4.hasNext()) {
                     var4 = nodeSet.iterator();

                     while(var4.hasNext()) {
                        String orphan = (String)var4.next();
                        graphSet.add(new TopologicalSortingGraph(new Node(orphan)));
                     }

                     return graphSet;
                  }

                  edge = (Edge)var4.next();
               } while(!allNodes.contains(edge.getStart()));
            } while(!allNodes.contains(edge.getEnd()));

            TopologicalSortingGraph startGraph = null;
            TopologicalSortingGraph endGraph = null;
            Iterator var8 = graphSet.iterator();

            while(var8.hasNext()) {
               TopologicalSortingGraph g = (TopologicalSortingGraph)var8.next();
               if (g.nodes.containsKey(edge.getStart())) {
                  startGraph = g;
               }

               if (g.nodes.containsKey(edge.getEnd())) {
                  endGraph = g;
               }

               if (startGraph != null && endGraph != null) {
                  break;
               }
            }

            Node startNode = getOrCreateNode(edge.getStart(), startGraph);
            Node endNode = getOrCreateNode(edge.getEnd(), endGraph);
            nodeSet.remove(edge.getStart());
            nodeSet.remove(edge.getEnd());
            startNode.addChild(endNode);
            if (startGraph == null && endGraph == null) {
               TopologicalSortingGraph g = new TopologicalSortingGraph(startNode);
               g.addNode(endNode);
               graphSet.add(g);
            } else if (startGraph == null) {
               endGraph.addNode(startNode);
            } else if (endGraph == null) {
               startGraph.addNode(endNode);
            } else if (startGraph != endGraph) {
               startGraph.nodes.putAll(endGraph.nodes);
               graphSet.remove(endGraph);
            }
         }
      }
   }

   private static Node getOrCreateNode(String identity, TopologicalSortingGraph g) {
      return g == null ? new Node(identity) : (Node)g.nodes.get(identity);
   }

   public TopologicalSortingGraph(Node node) {
      this.addNode(node);
   }

   private void calculateIndegree() {
      Iterator var1 = this.nodes.values().iterator();

      Node node;
      while(var1.hasNext()) {
         node = (Node)var1.next();
         node.setIndegree(0);
      }

      var1 = this.nodes.values().iterator();

      while(var1.hasNext()) {
         node = (Node)var1.next();
         Iterator var3 = node.getChildren().iterator();

         while(var3.hasNext()) {
            Node child = (Node)var3.next();
            child.increaseIndegree();
         }
      }

   }

   private Set getZeroIndegreeNodes() {
      Set zeroIndegreeNodes = new HashSet();
      Iterator var2 = this.nodes.values().iterator();

      while(var2.hasNext()) {
         Node node = (Node)var2.next();
         if (node.getIndegree() == 0) {
            zeroIndegreeNodes.add(node);
         }
      }

      return zeroIndegreeNodes;
   }

   public List sort() throws CycleFoundInGraphException {
      this.calculateIndegree();
      List result = new ArrayList();
      Queue zeroIndegrees = new LinkedList(this.getZeroIndegreeNodes());

      while(!zeroIndegrees.isEmpty()) {
         Node node = (Node)zeroIndegrees.poll();
         result.add(node.getIdentity());
         Iterator var4 = node.getChildren().iterator();

         while(var4.hasNext()) {
            Node child = (Node)var4.next();
            child.decreaseIndegree();
            if (child.getIndegree() == 0) {
               zeroIndegrees.add(child);
            }
         }
      }

      if (result.size() < this.nodes.size()) {
         throw new CycleFoundInGraphException();
      } else {
         return result;
      }
   }

   public boolean contains(String identity) {
      return this.nodes.containsKey(identity);
   }

   private void addNode(Node node) {
      this.nodes.put(node.getIdentity(), node);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("{");

      String name;
      for(Iterator var2 = this.nodes.keySet().iterator(); var2.hasNext(); sb.append(name)) {
         name = (String)var2.next();
         if (sb.length() > 1) {
            sb.append(" ,");
         }
      }

      return sb.append("}").toString();
   }

   public static class Edge {
      private String start;
      private String end;

      public Edge(String start, String end) {
         this.start = start;
         this.end = end;
      }

      public String getStart() {
         return this.start;
      }

      public String getEnd() {
         return this.end;
      }

      public String toString() {
         return "[" + this.start + "->" + this.end + "]";
      }
   }

   private static class Node {
      private String identity;
      private int indegree = 0;
      private List children = new ArrayList();

      Node(String identity) {
         this.identity = identity;
      }

      public String getIdentity() {
         return this.identity;
      }

      public int getIndegree() {
         return this.indegree;
      }

      public void setIndegree(int indegree) {
         this.indegree = indegree;
      }

      public void increaseIndegree() {
         ++this.indegree;
      }

      public void decreaseIndegree() {
         --this.indegree;
      }

      public void addChild(Node child) {
         this.children.add(child);
      }

      public List getChildren() {
         return this.children;
      }

      public String toString() {
         return "[identity = " + this.identity + "]";
      }
   }
}
