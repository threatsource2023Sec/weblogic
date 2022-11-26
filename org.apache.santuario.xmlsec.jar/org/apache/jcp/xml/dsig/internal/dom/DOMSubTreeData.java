package org.apache.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.xml.crypto.NodeSetData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DOMSubTreeData implements NodeSetData {
   private boolean excludeComments;
   private Node root;

   public DOMSubTreeData(Node root, boolean excludeComments) {
      this.root = root;
      this.excludeComments = excludeComments;
   }

   public Iterator iterator() {
      return new DelayedNodeIterator(this.root, this.excludeComments);
   }

   public Node getRoot() {
      return this.root;
   }

   public boolean excludeComments() {
      return this.excludeComments;
   }

   static class DelayedNodeIterator implements Iterator {
      private Node root;
      private List nodeSet;
      private ListIterator li;
      private boolean withComments;

      DelayedNodeIterator(Node root, boolean excludeComments) {
         this.root = root;
         this.withComments = !excludeComments;
      }

      public boolean hasNext() {
         if (this.nodeSet == null) {
            this.nodeSet = this.dereferenceSameDocumentURI(this.root);
            this.li = this.nodeSet.listIterator();
         }

         return this.li.hasNext();
      }

      public Node next() {
         if (this.nodeSet == null) {
            this.nodeSet = this.dereferenceSameDocumentURI(this.root);
            this.li = this.nodeSet.listIterator();
         }

         if (this.li.hasNext()) {
            return (Node)this.li.next();
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private List dereferenceSameDocumentURI(Node node) {
         List nodes = new ArrayList();
         if (node != null) {
            this.nodeSetMinusCommentNodes(node, nodes, (Node)null);
         }

         return nodes;
      }

      private void nodeSetMinusCommentNodes(Node node, List nodeSet, Node prevSibling) {
         Node pSibling;
         Node child;
         switch (node.getNodeType()) {
            case 1:
               NamedNodeMap attrs = node.getAttributes();
               if (attrs != null) {
                  int i = 0;

                  for(int len = attrs.getLength(); i < len; ++i) {
                     nodeSet.add(attrs.item(i));
                  }
               }

               nodeSet.add(node);
               pSibling = null;

               for(child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                  this.nodeSetMinusCommentNodes(child, nodeSet, pSibling);
                  pSibling = child;
               }
            case 2:
            case 5:
            case 6:
            default:
               break;
            case 3:
            case 4:
               if (prevSibling != null && (prevSibling.getNodeType() == 3 || prevSibling.getNodeType() == 4)) {
                  return;
               }

               nodeSet.add(node);
               break;
            case 7:
               nodeSet.add(node);
               break;
            case 8:
               if (this.withComments) {
                  nodeSet.add(node);
               }
               break;
            case 9:
               pSibling = null;

               for(child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                  this.nodeSetMinusCommentNodes(child, nodeSet, pSibling);
                  pSibling = child;
               }
         }

      }
   }
}
