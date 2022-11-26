package weblogic.xml.dom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLStreamIterator implements Iterator {
   private static final int OPEN = 1;
   private static final int CLOSE = 2;
   private static final int VISIT = 3;
   private Node currentNode;
   private Node nextNode;
   private Node root;
   private int currenState;
   private int nextState;

   public XMLStreamIterator(Node n) {
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

   public static void main(String[] args) throws Exception {
      Document doc = new DocumentImpl();
      Element n = doc.createElement("parent");
      doc.appendChild(n);
      System.out.println("----------------[ empty root node]");
      XMLStreamIterator i = new XMLStreamIterator(n);

      while(i.hasNext()) {
         System.out.println(i.next());
      }

      DOMStreamReader r = new DOMStreamReader(n);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

      System.out.println("----------------[ dump]");
      n.appendChild(doc.createComment(" this is a comment "));
      Element emptyc = doc.createElement("child1");
      n.appendChild(emptyc);
      Node t = doc.createElement("text");
      t.appendChild(doc.createTextNode("sometext"));
      n.appendChild(t);
      Element n2 = doc.createElement("child2");
      n.appendChild(n2);
      n2.appendChild(doc.createElement("a"));
      n2.appendChild(doc.createElement("b"));
      n2.appendChild(doc.createElement("c"));
      System.out.println(Util.printNode(doc));
      System.out.println("----------------[ root node]");
      i = new XMLStreamIterator(n);

      while(i.hasNext()) {
         System.out.println(i.next());
      }

      System.out.println("----------------[ text node]");
      i = new XMLStreamIterator(t);

      while(i.hasNext()) {
         System.out.println(i.next());
      }

      System.out.println("----------------[ child node]");
      i = new XMLStreamIterator(n2);

      while(i.hasNext()) {
         System.out.println(i.next());
      }

      System.out.println("----------------[ empty child node]");
      i = new XMLStreamIterator(emptyc);

      while(i.hasNext()) {
         System.out.println(i.next());
      }

      System.out.println("----------------[ root node]");
      r = new DOMStreamReader(n);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

      System.out.println("----------------[ text node]");
      r = new DOMStreamReader(t);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

      System.out.println("----------------[ child node]");
      r = new DOMStreamReader(n2);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

      System.out.println("----------------[ empty child node]");
      r = new DOMStreamReader(emptyc);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

   }
}
