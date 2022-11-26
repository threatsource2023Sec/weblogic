package org.apache.openjpa.lib.graph;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.lib.util.Localizer;

public class DepthFirstAnalysis {
   private static final Localizer _loc = Localizer.forPackage(DepthFirstAnalysis.class);
   private final Graph _graph;
   private final Map _nodeInfo = new HashMap();
   private Comparator _comp;

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
            this.visit(graph, node, info, 0, new LinkedList());
         }
      }

   }

   private int visit(Graph graph, Object node, NodeInfo info, int time, List path) {
      info.color = 1;
      Collection edges = graph.getEdgesFrom(node);
      int maxChildTime = time - 1;

      int childTime;
      for(Iterator itr = edges.iterator(); itr.hasNext(); maxChildTime = Math.max(maxChildTime, childTime)) {
         Edge edge = (Edge)itr.next();
         Object other = edge.getOther(node);
         NodeInfo otherInfo = (NodeInfo)this._nodeInfo.get(other);
         if (otherInfo.color == 0) {
            path.add(edge);
            childTime = this.visit(graph, other, otherInfo, time, path);
            path.remove(edge);
            edge.setType(1);
         } else if (otherInfo.color == 1) {
            childTime = -1;
            edge.setType(2);
            edge.setCycle(this.cycleForBackEdge(edge, path));
         } else {
            childTime = otherInfo.finished;
            edge.setType(3);
            List cycle = new LinkedList();
            cycle.add(edge);
            if (this.cycleForForwardEdge(graph, other, node, cycle)) {
               edge.setCycle(cycle);
            }
         }
      }

      info.color = 2;
      info.finished = maxChildTime + 1;
      return info.finished;
   }

   public void setNodeComparator(Comparator comp) {
      this._comp = comp;
   }

   public List getSortedNodes() {
      Map.Entry[] entries = (Map.Entry[])((Map.Entry[])this._nodeInfo.entrySet().toArray(new Map.Entry[this._nodeInfo.size()]));
      Arrays.sort(entries, new NodeInfoComparator(this._comp));
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

   private List buildCycle(Edge backEdge, List path, int pos) {
      int length = path != null ? path.size() - pos : 0;
      List cycle = new ArrayList(length + 1);
      cycle.add(0, backEdge);

      for(int i = 0; i < length; ++i) {
         cycle.add(i + 1, path.get(pos + i));
      }

      return cycle;
   }

   private List cycleForBackEdge(Edge edge, List path) {
      if (edge.getType() != 2) {
         return null;
      } else {
         int pos = 0;
         if (path != null && !edge.getFrom().equals(edge.getTo())) {
            pos = this.findNodeInPath(edge.getTo(), path);

            assert pos >= 0 : _loc.get("node-not-on-path", edge, edge.getTo());
         } else {
            assert edge.getFrom().equals(edge.getTo()) : _loc.get("edge-no-loop", (Object)edge).getMessage();

            path = null;
         }

         List cycle = this.buildCycle(edge, path, pos);

         assert cycle != null : _loc.get("cycle-null", (Object)edge).getMessage();

         return cycle;
      }
   }

   private boolean cycleForForwardEdge(Graph graph, Object node, Object cycleTo, List path) {
      boolean found = false;
      Collection edges = graph.getEdgesFrom(node);
      Iterator itr = edges.iterator();

      while(!found && itr.hasNext()) {
         Edge edge = (Edge)itr.next();
         Object other = edge.getOther(node);
         if (!node.equals(other)) {
            if (other.equals(cycleTo)) {
               path.add(edge);
               found = true;
            } else if (!path.contains(edge)) {
               path.add(edge);
               found = this.cycleForForwardEdge(graph, other, cycleTo, path);
               if (!found) {
                  path.remove(edge);
               }
            }
         }
      }

      return found;
   }

   private int findNodeInPath(Object node, List path) {
      int pos = -1;
      if (path != null) {
         for(int i = 0; i < path.size(); ++i) {
            if (((Edge)path.get(i)).getFrom().equals(node)) {
               pos = i;
            }
         }
      }

      return pos;
   }

   public boolean hasNoCycles() {
      if (!this.getEdges(2).isEmpty()) {
         return false;
      } else {
         Collection edges = this.getEdges(3);
         if (!edges.isEmpty()) {
            Iterator itr = edges.iterator();

            while(itr.hasNext()) {
               Edge edge = (Edge)itr.next();
               if (edge.getCycle() != null) {
                  return false;
               }
            }
         }

         return true;
      }
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
         int ret = n1.finished - n2.finished;
         if (ret == 0 && this._subComp != null) {
            ret = this._subComp.compare(e1.getKey(), e2.getKey());
         }

         return ret;
      }
   }
}
