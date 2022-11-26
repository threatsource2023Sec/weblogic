package weblogic.application.internal.library.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SortedNodeTree {
   public static final boolean VALUES_ONLY_IN_LEAF_NODES = true;
   private final Node root = new Node((Node)null);

   public Object remove(Object[] k) {
      Node n = this.getNode(k);
      if (n == null) {
         return null;
      } else {
         Object rtn = n.getValue();
         this.killOnlyChild(k, k.length, n);
         return rtn;
      }
   }

   public Object put(Object[] k, Object v) throws NodeModificationException {
      Node n = this.createPathTraversal(k, 0, this.root);
      if (!n.isLeafNode()) {
         throw new NodeModificationException(NodeModificationException.Type.ADDING_VALUE_TO_NON_LEAF_NODE, n);
      } else {
         return n.setVal(v);
      }
   }

   public boolean hasElement(Object[] k) {
      return this.get(k) != null;
   }

   public int size() {
      SizeTraversal st = new SizeTraversal();
      this.traverse(st);
      return st.size();
   }

   public Collection getAll() {
      Collection rtn = new ArrayList();
      this.getAll(rtn);
      return rtn;
   }

   public void getAll(Collection rtn) {
      this.traverse(new GathererTraversal(rtn));
   }

   public String toString() {
      if (this.root.getNumChildren() == 0) {
         return "[]";
      } else {
         Traversal st = new StringTraversal();
         this.traverse(st);
         return st.toString();
      }
   }

   public void traverse(Traversal t) {
      this.traverse(t, this.root, new ArrayList());
   }

   private void killOnlyChild(Object[] k, int i, Node child) {
      Node parent = child.getParent();
      Object childKey = k[i - 1];
      parent.remove(childKey);
      if (!parent.isRoot() && parent.getNumChildren() == 0) {
         if (!parent.hasValue()) {
            this.killOnlyChild(k, i - 1, parent);
         } else {
            assert false;
         }
      }

   }

   private Object get(Object[] k) {
      Node n = this.getNode(k);
      return n == null ? null : n.getValue();
   }

   private Node getNode(Object[] k) {
      ExactMatchTraversal emt = new ExactMatchTraversal(k);
      this.traverse(emt);
      return emt.getMatchNode();
   }

   private Node createPathTraversal(Object[] k, int i, Node n) throws NodeModificationException {
      Node nextNode = n.getOrCreateChild(k[i]);
      if (i == k.length - 1) {
         return nextNode;
      } else if (nextNode.hasValue() && nextNode.isLeafNode()) {
         throw new NodeModificationException(NodeModificationException.Type.ADDING_EDGE_TO_LEAF_NODE, nextNode);
      } else {
         return this.createPathTraversal(k, i + 1, nextNode);
      }
   }

   private void traverse(Traversal t, Node n, List path) {
      if (n.isLeafNode()) {
         t.visitLeaf(n, path);
      } else {
         t.visit(n, path);
         Object nextEdge = t.getNextEdge(n);
         if (nextEdge != null) {
            this.traverse(t, n, nextEdge, path);
         } else {
            Object[] nextEdges = t.getNextEdges(n);
            if (nextEdges != null) {
               for(int i = 0; i < nextEdges.length; ++i) {
                  this.traverse(t, n, nextEdges[i], path);
                  path.remove(path.size() - 1);
               }
            }
         }
      }

   }

   private void traverse(Traversal t, Node n, Object e, List path) {
      try {
         if (!n.hasEdge(e)) {
            this.throwUnknownEdgeException(e, n, (ClassCastException)null);
         }
      } catch (ClassCastException var6) {
         this.throwUnknownEdgeException(e, n, var6);
      }

      path.add(e);
      this.traverse(t, n.getChild(e), path);
   }

   private void throwUnknownEdgeException(Object edge, Node n, ClassCastException ex) {
      throw new UnknownEdgeRuntimeException("Unknown edge: " + String.valueOf(edge) + ". Current Node has edges: " + n.getEdges() + ".", ex);
   }

   private static class UnknownEdgeRuntimeException extends RuntimeException {
      private static final long serialVersionUID = 3240595793558570615L;

      public UnknownEdgeRuntimeException(String message) {
         super(message);
      }

      public UnknownEdgeRuntimeException(String message, Throwable throwable) {
         super(message, throwable);
      }
   }

   private class SizeTraversal extends AbstractTraversal implements Traversal {
      private int numLeafNodes;

      private SizeTraversal() {
         this.numLeafNodes = 0;
      }

      public Object[] getNextEdges(RONode node) {
         return (Object[])node.getEdges().toArray();
      }

      public void visitLeaf(RONode node, List path) {
         if (node.hasValue()) {
            ++this.numLeafNodes;
         }

      }

      public int size() {
         return this.numLeafNodes;
      }

      // $FF: synthetic method
      SizeTraversal(Object x1) {
         this();
      }
   }

   private class StringTraversal extends AbstractTraversal implements Traversal {
      private final StringBuffer sb;

      private StringTraversal() {
         this.sb = new StringBuffer();
      }

      public Object[] getNextEdges(RONode node) {
         return (Object[])node.getEdges().toArray();
      }

      public void visitLeaf(RONode node, List path) {
         this.sb.append(path + " -> " + node.getValue()).append("\n");
      }

      public String toString() {
         return this.sb.toString();
      }

      // $FF: synthetic method
      StringTraversal(Object x1) {
         this();
      }
   }

   private class GathererTraversal extends AbstractTraversal implements Traversal {
      private final Collection rtn;

      public GathererTraversal(Collection rtn) {
         this.rtn = rtn;
      }

      public Object[] getNextEdges(RONode node) {
         return (Object[])node.getEdges().toArray();
      }

      public void visit(RONode node, List path) {
         if (node.hasValue()) {
            this.rtn.add(node.getValue());
         }

      }

      public void visitLeaf(RONode node, List path) {
         if (node.hasValue()) {
            this.rtn.add(node.getValue());
         }

      }
   }

   private class ExactMatchTraversal extends AbstractTraversal implements Traversal {
      private Object[] edges = null;
      private RONode matchNode = null;
      private int edgeIndex = -1;

      public ExactMatchTraversal(Object[] inEdges) {
         this.edges = inEdges;
      }

      public Object getNextEdge(RONode node) {
         ++this.edgeIndex;
         if (this.edgeIndex == this.edges.length) {
            return null;
         } else {
            return this.edges[this.edgeIndex] != null && node.hasEdge(this.edges[this.edgeIndex]) ? this.edges[this.edgeIndex] : null;
         }
      }

      private Node getMatchNode() {
         return (Node)this.matchNode;
      }

      public void visit(RONode node, List path) {
      }

      public void visitLeaf(RONode node, List path) {
         if (this.edgeIndex == this.edges.length - 1) {
            this.matchNode = node;
         }

      }
   }
}
