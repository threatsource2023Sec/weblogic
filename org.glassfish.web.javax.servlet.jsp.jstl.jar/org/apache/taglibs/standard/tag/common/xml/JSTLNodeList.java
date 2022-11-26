package org.apache.taglibs.standard.tag.common.xml;

import java.util.Vector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class JSTLNodeList extends Vector implements NodeList {
   Vector nodeVector;

   public JSTLNodeList(Vector nodeVector) {
      this.nodeVector = nodeVector;
   }

   public JSTLNodeList(NodeList nl) {
      this.nodeVector = new Vector();

      for(int i = 0; i < nl.getLength(); ++i) {
         nl.item(i);
         this.nodeVector.add(i, nl.item(i));
      }

   }

   public JSTLNodeList(Node n) {
      this.nodeVector = new Vector();
      this.nodeVector.addElement(n);
   }

   public JSTLNodeList(Object o) {
      this.nodeVector = new Vector();
      if (o instanceof NodeList) {
         NodeList nl = (NodeList)o;

         for(int i = 0; i < nl.getLength(); ++i) {
            nl.item(i);
            this.nodeVector.add(i, nl.item(i));
         }
      } else {
         this.nodeVector.addElement(o);
      }

   }

   public Node item(int index) {
      return (Node)this.nodeVector.elementAt(index);
   }

   public Object elementAt(int index) {
      return this.nodeVector.elementAt(index);
   }

   public Object get(int index) {
      return this.nodeVector.get(index);
   }

   public int getLength() {
      return this.nodeVector.size();
   }

   public int size() {
      return this.nodeVector.size();
   }
}
