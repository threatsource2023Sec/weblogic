package weblogic.xml.xmlnode;

import java.io.FileInputStream;
import java.util.Iterator;
import weblogic.utils.collections.Stack;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.util.TypeFilter;

public class XMLNodeIterator {
   private Stack iteratorStack = new Stack();
   private Stack nameStack = new Stack();
   private XMLNode nextNode;
   private XMLNode rootNode;
   private int depth;

   public XMLNodeIterator(XMLNode node) {
      if (node == null) {
         this.nextNode = null;
      } else {
         this.rootNode = node;
         this.nextNode = node;
         this.iteratorStack.push(node.getChildren());
         this.nameStack.push(node.getName());
      }
   }

   public int getDepth() {
      return this.depth;
   }

   public void reset() {
      this.nextNode = this.rootNode;
      this.iteratorStack.clear();
      this.iteratorStack.push(this.nextNode.getChildren());
      this.nameStack.clear();
      this.nameStack.push(this.rootNode.getName());
   }

   public boolean hasNext() {
      return this.nextNode != null;
   }

   public XMLNode next() {
      XMLNode currentNode = this.nextNode;
      if (this.iteratorStack.isEmpty()) {
         this.nextNode = null;
         return currentNode;
      } else if (!this.iteratorStack.isEmpty()) {
         Iterator i = (Iterator)this.iteratorStack.peek();
         if (!i.hasNext()) {
            this.iteratorStack.pop();
            this.nextNode = new XMLEndNode((XMLName)this.nameStack.pop());
            return currentNode;
         } else {
            this.nextNode = (XMLNode)i.next();
            if (!this.nextNode.isTextNode()) {
               this.iteratorStack.push(this.nextNode.getChildren());
               this.nameStack.push(this.nextNode.getName());
            }

            return currentNode;
         }
      } else {
         this.nextNode = null;
         return currentNode;
      }
   }

   public void print() {
      while(this.hasNext()) {
         XMLNode node = this.next();
         if (node.isTextNode() && node.getText() != null) {
            System.out.println(node.getText());
         } else if (node.isEndNode()) {
            System.out.println("</" + node.getName() + ">");
         } else {
            System.out.println("<" + node.getName() + ">");
         }
      }

   }

   public void print2() {
      while(this.hasNext()) {
         XMLNode node = this.next();
         if (node.isTextNode() && node.getText() != null) {
            System.out.println(node.createCharacterData());
         } else if (node.isEndNode()) {
            System.out.println(node.createEndElement());
         } else {
            System.out.println(node.createStartElement());
         }
      }

   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      TypeFilter filter = new TypeFilter(22);
      XMLInputStream stream = factory.newInputStream(new FileInputStream(args[0]), filter);
      XMLNode node = new XMLNode();
      node.read(stream);
      XMLNodeIterator i = new XMLNodeIterator(node);
      i.reset();
      i.print();
      i.reset();
      i.print2();
   }
}
