package com.solarmetric.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BreadthFirstWalk {
   private final Graph _graph;
   private final Set _visitors = new HashSet();
   private final List _queue = new LinkedList();
   private final Map _nodeInfo = new HashMap();

   public BreadthFirstWalk(Graph graph) {
      this._graph = graph;
   }

   public void walk() {
      this._queue.clear();
      this._nodeInfo.clear();
      Collection nodes = this._graph.getNodes();
      Iterator itr = nodes.iterator();

      while(itr.hasNext()) {
         this._nodeInfo.put(itr.next(), new NodeInfo());
      }

      for(Iterator itr = nodes.iterator(); itr.hasNext(); this.processQueue()) {
         Object node = itr.next();
         NodeInfo info = (NodeInfo)this._nodeInfo.get(node);
         if (info.color == 0) {
            this.enqueue(node, info);
         }
      }

   }

   private void processQueue() {
      label18:
      while(true) {
         if (this._queue.size() > 0) {
            Object node = this._queue.remove(0);
            NodeInfo info = (NodeInfo)this._nodeInfo.get(node);
            this.visit(node, info);
            Collection edges = this._graph.getEdgesFrom(node);
            Iterator itr = edges.iterator();

            while(true) {
               if (!itr.hasNext()) {
                  continue label18;
               }

               Edge edge = (Edge)itr.next();
               this.edgeVisited(edge);
               Object other = edge.getOther(node);
               NodeInfo otherInfo = (NodeInfo)this._nodeInfo.get(other);
               if (otherInfo.color == 0) {
                  this.enqueue(other, otherInfo);
               }
            }
         }

         return;
      }
   }

   protected void enqueue(Object node, NodeInfo info) {
      this._queue.add(node);
      info.color = 1;
      Iterator i = this._visitors.iterator();

      while(i.hasNext()) {
         ((GraphVisitor)i.next()).nodeSeen(node);
      }

   }

   protected void visit(Object node, NodeInfo info) {
      info.color = 2;
      Iterator i = this._visitors.iterator();

      while(i.hasNext()) {
         ((GraphVisitor)i.next()).nodeVisited(node);
      }

   }

   protected void edgeVisited(Edge edge) {
      Iterator i = this._visitors.iterator();

      while(i.hasNext()) {
         ((GraphVisitor)i.next()).edgeVisited(edge);
      }

   }

   public void addGraphVisitor(GraphVisitor visitor) {
      this._visitors.add(visitor);
   }

   public void removeGraphVisitor(GraphVisitor visitor) {
      this._visitors.remove(visitor);
   }
}
