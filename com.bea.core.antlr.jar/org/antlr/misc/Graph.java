package org.antlr.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
   protected Map nodes = new HashMap();

   public void addEdge(Object a, Object b) {
      Node a_node = this.getNode(a);
      Node b_node = this.getNode(b);
      a_node.addEdge(b_node);
   }

   protected Node getNode(Object a) {
      Node existing = (Node)this.nodes.get(a);
      if (existing != null) {
         return existing;
      } else {
         Node n = new Node(a);
         this.nodes.put(a, n);
         return n;
      }
   }

   public List sort() {
      Set visited = new OrderedHashSet();

      ArrayList sorted;
      Node n;
      for(sorted = new ArrayList(); visited.size() < this.nodes.size(); this.DFS(n, visited, sorted)) {
         n = null;
         Iterator i$ = this.nodes.values().iterator();

         while(i$.hasNext()) {
            Node tNode = (Node)i$.next();
            n = tNode;
            if (!visited.contains(tNode)) {
               break;
            }
         }
      }

      return sorted;
   }

   public void DFS(Node n, Set visited, ArrayList sorted) {
      if (!visited.contains(n)) {
         visited.add(n);
         if (n.edges != null) {
            Iterator i$ = n.edges.iterator();

            while(i$.hasNext()) {
               Node target = (Node)i$.next();
               this.DFS(target, visited, sorted);
            }
         }

         sorted.add(n.payload);
      }
   }

   public static class Node {
      Object payload;
      List edges;

      public Node(Object payload) {
         this.payload = payload;
      }

      public void addEdge(Node n) {
         if (this.edges == null) {
            this.edges = new ArrayList();
         }

         if (!this.edges.contains(n)) {
            this.edges.add(n);
         }

      }

      public String toString() {
         return this.payload.toString();
      }
   }
}
