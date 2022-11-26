package weblogic.application.internal.library.util;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import weblogic.utils.NestedRuntimeException;

public class Node implements RONode {
   private final SortedMap edges = new TreeMap();
   private final Node parent;
   private Object value = null;

   Node(Node inParent) {
      this.parent = inParent;
   }

   public boolean isRoot() {
      return this.parent == null;
   }

   public int getDepth() {
      int i = 1;

      for(Node n = this; !n.isRoot(); ++i) {
         n = n.getParent();
      }

      return i;
   }

   public Node remove(Object k) {
      return (Node)this.edges.remove(k);
   }

   public boolean hasEdge(Object k) {
      return this.edges.containsKey(k);
   }

   public Object getValue() {
      return this.value;
   }

   public Object setVal(Object inV) {
      Object o = null;
      o = this.value;
      this.value = inV;
      return o;
   }

   public Node getParent() {
      return this.parent;
   }

   public Node getChild(Object k) {
      return (Node)this.edges.get(k);
   }

   public int getNumChildren() {
      return this.edges.size();
   }

   public Node getOrCreateChild(Object k) {
      Node n = (Node)this.edges.get(k);
      if (n == null) {
         n = new Node(this);
         this.edges.put(k, n);
      }

      return n;
   }

   public Set getEdges() {
      return this.edges.keySet();
   }

   public boolean isLeafNode() {
      return this.edges.keySet().isEmpty();
   }

   public boolean hasValue() {
      return this.value != null;
   }

   public String toString() {
      return this.isLeafNode() ? "leaf(" + String.valueOf(this.value) + ")" : "not a leaf";
   }

   public boolean hasChildren() {
      return this.getNumChildren() != 0;
   }

   public Object getHighestEdge() {
      if (this.isLeafNode()) {
         throw new NoEdgesRuntimeException("No edges to traverse");
      } else {
         return this.edges.lastKey();
      }
   }

   private static class NoEdgesRuntimeException extends NestedRuntimeException {
      private static final long serialVersionUID = 4299692877739743461L;

      public NoEdgesRuntimeException(String message) {
         super(message);
      }
   }
}
