package com.solarmetric.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Graph {
   private final Map _nodes = new HashMap();

   public void clear() {
      this._nodes.clear();
   }

   public boolean containsNode(Object node) {
      return this._nodes.containsKey(node);
   }

   public Collection getNodes() {
      return this._nodes.keySet();
   }

   public void addNode(Object node) {
      if (node == null) {
         throw new NullPointerException("node = null");
      } else {
         if (!this.containsNode(node)) {
            this._nodes.put(node, (Object)null);
         }

      }
   }

   public boolean removeNode(Object node) {
      boolean rem = this.containsNode(node);
      if (rem) {
         Collection edges = this.getEdgesTo(node);
         Iterator itr = edges.iterator();

         while(itr.hasNext()) {
            this.removeEdge((Edge)itr.next());
         }

         this._nodes.remove(node);
      }

      return rem;
   }

   public Collection getEdges() {
      Collection all = new HashSet();
      Iterator itr = this._nodes.values().iterator();

      while(itr.hasNext()) {
         Collection edges = (Collection)itr.next();
         if (edges != null) {
            all.addAll(edges);
         }
      }

      return all;
   }

   public Collection getEdgesFrom(Object node) {
      Collection edges = (Collection)this._nodes.get(node);
      return (Collection)(edges == null ? Collections.EMPTY_LIST : edges);
   }

   public Collection getEdgesTo(Object node) {
      Collection edges = this.getEdges();
      Collection to = new ArrayList();
      Iterator itr = edges.iterator();

      while(itr.hasNext()) {
         Edge edge = (Edge)itr.next();
         if (edge.isTo(node)) {
            to.add(edge);
         }
      }

      return to;
   }

   public Collection getEdges(Object from, Object to) {
      Collection edges = this.getEdgesFrom(from);
      Collection matches = new ArrayList(edges.size());
      Iterator itr = edges.iterator();

      while(itr.hasNext()) {
         Edge edge = (Edge)itr.next();
         if (edge.isTo(to)) {
            matches.add(edge);
         }
      }

      return matches;
   }

   public void addEdge(Edge edge) {
      if (!this.containsNode(edge.getTo())) {
         throw new IllegalArgumentException(edge.getTo().toString());
      } else if (!this.containsNode(edge.getFrom())) {
         throw new IllegalArgumentException(edge.getFrom().toString());
      } else {
         Collection from = (Collection)this._nodes.get(edge.getFrom());
         if (from == null) {
            from = new ArrayList(3);
            this._nodes.put(edge.getFrom(), from);
         }

         ((Collection)from).add(edge);
         if (!edge.isDirected() && !edge.getFrom().equals(edge.getTo())) {
            Collection to = (Collection)this._nodes.get(edge.getTo());
            if (to == null) {
               to = new ArrayList(3);
               this._nodes.put(edge.getTo(), to);
            }

            ((Collection)to).add(edge);
         }

      }
   }

   public boolean removeEdge(Edge edge) {
      Collection edges = (Collection)this._nodes.get(edge.getFrom());
      if (edges == null) {
         return false;
      } else {
         boolean rem = edges.remove(edge);
         if (rem && !edge.isDirected()) {
            edges = (Collection)this._nodes.get(edge.getTo());
            if (edges != null) {
               edges.remove(edge);
            }
         }

         return rem;
      }
   }

   public void clearTraversal() {
      Iterator vals = this._nodes.values().iterator();

      while(true) {
         Collection edges;
         do {
            if (!vals.hasNext()) {
               return;
            }

            edges = (Collection)vals.next();
         } while(edges == null);

         Iterator ed = edges.iterator();

         while(ed.hasNext()) {
            ((Edge)ed.next()).clearTraversal();
         }
      }
   }
}
