package weblogic.xml.xmlnode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.stream.XMLInputOutputStream;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStreamFactory;
import weblogic.xml.stream.XMLStreamException;

public class XMLNodeSet {
   List nodeList = new ArrayList();

   public XMLNode[] getXMLNodes() {
      return this.nodeList.size() == 0 ? null : (XMLNode[])((XMLNode[])this.nodeList.toArray(new XMLNode[0]));
   }

   public XMLNode getAsXMLNode(XMLName nodeName) {
      XMLNode topNode = new XMLNode(nodeName);
      Iterator i = this.nodeList.iterator();

      while(i.hasNext()) {
         XMLNode n = (XMLNode)i.next();
         topNode.addChild(n);
      }

      return this.nodeList.size() == 0 ? null : topNode;
   }

   public void addXMLNode(XMLNode n) {
      this.nodeList.add(n);
   }

   public void removeXMLNode(XMLNode n) {
      this.nodeList.remove(n);
   }

   public XMLInputStream stream() throws XMLStreamException {
      XMLInputOutputStream output = XMLOutputStreamFactory.newInstance().newInputOutputStream();
      Iterator i = this.nodeList.iterator();

      while(i.hasNext()) {
         XMLNode n = (XMLNode)i.next();
         output.add(n.stream());
      }

      return output;
   }

   public int size() {
      return this.nodeList.size();
   }

   public String toString() {
      StringBuffer sbuf = new StringBuffer();
      Iterator i = this.nodeList.iterator();

      while(i.hasNext()) {
         XMLNode n = (XMLNode)i.next();
         sbuf.append(n.toString());
      }

      return sbuf.toString();
   }
}
