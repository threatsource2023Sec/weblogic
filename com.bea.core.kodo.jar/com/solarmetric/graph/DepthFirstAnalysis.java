package com.solarmetric.graph;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DepthFirstAnalysis {
   private final Graph _graph;
   private final Map _nodeInfo = new HashMap();

   public DepthFirstAnalysis(Graph graph) {
      this._graph = graph;
      Collection nodes = graph.getNodes();
      Iterator itr = nodes.iterator();

      while(itr.hasNext()) {
         this._nodeInfo.put(itr.next(), new NodeInfo());
      }

      Iterator itr = nodes.iterator();

      while(itr.hasNext()) {
         Object node = itr.next();
         NodeInfo info = (NodeInfo)this._nodeInfo.get(node);
         if (info.color == 0) {
            this.visit(graph, node, info, 0);
         }
      }

   }

   private int visit(Graph graph, Object node, NodeInfo info, int time) {
      info.color = 1;
      Collection edges = graph.getEdgesFrom(node);
      int maxChildTime = time - 1;

      int childTime;
      for(Iterator itr = edges.iterator(); itr.hasNext(); maxChildTime = Math.max(maxChildTime, childTime)) {
         Edge edge = (Edge)itr.next();
         Object other = edge.getOther(node);
         NodeInfo otherInfo = (NodeInfo)this._nodeInfo.get(other);
         if (otherInfo.color == 0) {
            childTime = this.visit(graph, other, otherInfo, time);
            edge.setType(1);
         } else if (otherInfo.color == 1) {
            childTime = -1;
            edge.setType(2);
         } else {
            childTime = otherInfo.finished;
            edge.setType(3);
         }
      }

      info.color = 2;
      info.finished = maxChildTime + 1;
      return info.finished;
   }

   public List getSortedNodes(Comparator comp) {
      Map.Entry[] entries = (Map.Entry[])((Map.Entry[])this._nodeInfo.entrySet().toArray(new Map.Entry[this._nodeInfo.size()]));
      Arrays.sort(entries, new NodeInfoComparator(comp));
      return new NodeList(entries);
   }

   public Collection getEdges(int type) {
      Collection typed = null;
      Iterator nodes = this._graph.getNodes().iterator();

      while(nodes.hasNext()) {
         Object node = nodes.next();
         Iterator itr = this._graph.getEdgesFrom(node).iterator();

         while(itr.hasNext()) {
            Edge edge = (Edge)itr.next();
            if (edge.getType() == type) {
               if (typed == null) {
                  typed = new ArrayList();
               }

               typed.add(edge);
            }
         }
      }

      return (Collection)(typed == null ? Collections.EMPTY_LIST : typed);
   }

   public int getFinishedTime(Object node) {
      NodeInfo info = (NodeInfo)this._nodeInfo.get(node);
      return info == null ? -1 : info.finished;
   }

   private static class NodeList extends AbstractList {
      private final Map.Entry[] _entries;

      public NodeList(Map.Entry[] entries) {
         this._entries = entries;
      }

      public Object get(int idx) {
         return this._entries[idx].getKey();
      }

      public int size() {
         return this._entries.length;
      }
   }

   private static class NodeInfoComparator implements Comparator {
      private final Comparator _subComp;

      public NodeInfoComparator(Comparator subComp) {
         this._subComp = subComp;
      }

      public int compare(Object o1, Object o2) {
         Map.Entry e1 = (Map.Entry)o1;
         Map.Entry e2 = (Map.Entry)o2;
         NodeInfo n1 = (NodeInfo)e1.getValue();
         NodeInfo n2 = (NodeInfo)e2.getValue();
         int ret = n2.finished - n1.finished;
         if (ret == 0 && this._subComp != null) {
            ret = this._subComp.compare(e1.getKey(), e2.getKey());
         }

         return ret;
      }
   }
}
