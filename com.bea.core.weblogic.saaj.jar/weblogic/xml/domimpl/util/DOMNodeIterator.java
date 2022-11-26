package weblogic.xml.domimpl.util;

import java.util.NoSuchElementException;
import org.w3c.dom.Node;

public class DOMNodeIterator {
   private final Node root;
   private Node currentNode;
   private Node nextNode;
   private int currenState;
   private int nextState;
   private static final int OPEN = 1;
   private static final int CLOSE = 2;
   private static final int VISIT = 3;

   public DOMNodeIterator(Node n) {
      if (n.getNodeType() == 1) {
         this.nextNode = n;
         this.root = n;
         this.nextState = 1;
      } else {
         this.root = n;
         this.nextNode = n;
         this.nextState = 3;
      }

   }

   private void advance() {
      this.currentNode = this.nextNode;
      this.currenState = this.nextState;
      this.nextNode = this.findNext();
   }

   public Node current() {
      if (this.currentNode == null) {
         String s = "The current node is null please call nextNode() before using accessor methods";
         throw new NoSuchElementException("The current node is null please call nextNode() before using accessor methods");
      } else {
         return this.currentNode;
      }
   }

   public Node nextNode() {
      if (this.nextNode == null) {
         throw new NoSuchElementException("Unable to advance the node iterator");
      } else {
         this.advance();

         assert this.currentNode != null;

         return this.currentNode;
      }
   }

   public boolean isOpen() {
      return this.currenState == 1;
   }

   public boolean isClosed() {
      return this.currenState == 2;
   }

   private Node findNext() {
      Node next;
      switch (this.currenState) {
         case 1:
            next = this.currentNode.getFirstChild();
            if (next != null) {
               if (next.getNodeType() == 1) {
                  this.nextState = 1;
               } else {
                  this.nextState = 3;
               }

               return next;
            }

            next = this.currentNode;
            this.nextState = 2;
            return next;
         case 2:
            if (this.currentNode != this.root) {
               next = this.currentNode.getNextSibling();
            } else {
               next = null;
            }

            if (next != null) {
               if (next.getNodeType() == 1) {
                  this.nextState = 1;
               } else {
                  this.nextState = 3;
               }

               return next;
            }

            if (this.currentNode != this.root) {
               next = this.currentNode.getParentNode();
            } else {
               next = null;
            }

            this.nextState = 2;
            return next;
         case 3:
            if (this.currentNode != this.root) {
               next = this.currentNode.getNextSibling();
            } else {
               next = null;
            }

            if (next != null) {
               if (next.getNodeType() == 1) {
                  this.nextState = 1;
               } else {
                  this.nextState = 3;
               }

               return next;
            }

            if (this.currentNode != this.root) {
               next = this.currentNode.getParentNode();
            } else {
               next = null;
            }

            this.nextState = 2;
            return next;
         default:
            return null;
      }
   }

   public boolean hasNext() {
      return this.nextNode != null;
   }
}
