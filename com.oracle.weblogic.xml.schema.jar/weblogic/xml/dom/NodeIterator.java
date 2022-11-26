package weblogic.xml.dom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;

public class NodeIterator implements Iterator {
   private final Node startNode;
   private Node currentNode;
   private Node nextNode;

   public NodeIterator(Node n) {
      this.startNode = this.nextNode = n;
   }

   private void advance() {
      this.currentNode = this.nextNode;
      this.nextNode = this.findNext();
   }

   public Object next() {
      return this.nextNode();
   }

   public void remove() {
      this.current().getParentNode().removeChild(this.current());
      this.advance();
   }

   public Node current() {
      if (this.currentNode == null) {
         throw new NoSuchElementException("The current node is null please call next() before using accessor methods");
      } else {
         return this.currentNode;
      }
   }

   public Node nextNode() {
      if (this.nextNode == null) {
         throw new NoSuchElementException("Unable to advance the node iterator");
      } else {
         this.advance();
         return this.current();
      }
   }

   private Node findNext() {
      Node next = this.currentNode.getFirstChild();
      if (next != null) {
         return next;
      } else {
         next = this.currentNode.getNextSibling();
         if (next != null) {
            return next;
         } else if (this.currentNode == this.startNode) {
            return null;
         } else {
            for(next = this.currentNode.getParentNode(); next != null && next != this.startNode; next = next.getParentNode()) {
               if (next.getNextSibling() != null) {
                  return next.getNextSibling();
               }
            }

            return null;
         }
      }
   }

   public boolean hasNext() {
      return this.nextNode != null;
   }
}
