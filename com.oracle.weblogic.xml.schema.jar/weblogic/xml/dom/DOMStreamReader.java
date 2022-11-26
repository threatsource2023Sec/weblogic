package weblogic.xml.dom;

import java.io.File;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import weblogic.xml.babel.reader.XmlChars;
import weblogic.xml.stax.XMLStreamReaderBase;

public class DOMStreamReader extends XMLStreamReaderBase implements XMLStreamReader {
   private int[] attributes = new int[1];
   private int[] namespaces = new int[1];
   private int numAttr;
   private int numNS;
   private NamedNodeMap attributeNodes;
   private final XMLStreamIterator nodeIterator;
   private Node current = null;

   public DOMStreamReader(Node n) throws XMLStreamException {
      this.nodeIterator = new XMLStreamIterator(n);
      this.advance();
   }

   public Node current() {
      return this.current;
   }

   protected boolean atEnd() {
      return this.current() == null;
   }

   protected void advance() throws XMLStreamException {
      if (!this.nodeIterator.hasNext()) {
         this.eventType = 8;
         this.current = null;
      } else {
         this.current = (Node)this.nodeIterator.next();
         this.setTextCache((String)null);
         this.setArrayCache((char[])null);
         this.eventType = this.convert(this.current().getNodeType());
         if (this.eventType == -1) {
            throw new XMLStreamException("Unable to advance the cursor unknown node type" + this.current());
         }

         if (this.isStartElement()) {
            this.initializeAttributesAndNamespaces();
         }

         if (this.isEndElement()) {
            this.initializeAttributesAndNamespaces();
         }
      }

   }

   protected int convert(int type) {
      switch (type) {
         case 1:
            if (this.nodeIterator.isOpen()) {
               return 1;
            }

            return 2;
         case 2:
         case 5:
         case 6:
         default:
            return -1;
         case 3:
            return 4;
         case 4:
            return 12;
         case 7:
            return 3;
         case 8:
            return 5;
         case 9:
            return this.nodeIterator.isOpen() ? 7 : 8;
      }
   }

   public String getPrefix() {
      return this.current().getPrefix();
   }

   public String getNamespaceURI() {
      return checkNull(this.current().getNamespaceURI());
   }

   public String getLocalName() {
      return this.current().getLocalName();
   }

   private void addNamespace(int index) {
      this.namespaces[this.numNS] = index;
      ++this.numNS;
   }

   private void addAttribute(int index) {
      this.attributes[this.numAttr] = index;
      ++this.numAttr;
   }

   private void setAttributeCapacity(int len) {
      this.numAttr = 0;
      if (len >= this.attributes.length) {
         this.attributes = new int[len];
      }
   }

   private void setNamespaceCapacity(int len) {
      this.numNS = 0;
      if (len >= this.namespaces.length) {
         this.namespaces = new int[len];
      }
   }

   protected void initializeAttributesAndNamespaces() {
      int attribute_count = this.updateAttributeNodes();
      this.setAttributeCapacity(attribute_count);
      this.setNamespaceCapacity(attribute_count);

      for(int i = 0; i < attribute_count; ++i) {
         Attr a = (Attr)this.attributeNodes.item(i);
         if ("xmlns".equals(a.getPrefix())) {
            this.addNamespace(i);
         } else if ("xmlns".equals(a.getLocalName())) {
            this.addNamespace(i);
         } else {
            this.addAttribute(i);
         }
      }

   }

   private int updateAttributeNodes() {
      int attribute_count;
      if (this.current().hasAttributes()) {
         this.attributeNodes = this.current().getAttributes();
         attribute_count = this.attributeNodes.getLength();
      } else {
         this.attributeNodes = null;
         attribute_count = 0;
      }

      return attribute_count;
   }

   protected void initializeOutOfScopeNamespaces() {
      int attribute_count = this.updateAttributeNodes();
      this.setNamespaceCapacity(attribute_count);

      for(int i = 0; i < attribute_count; ++i) {
         Attr a = (Attr)this.attributeNodes.item(i);
         if ("xmlns".equals(a.getPrefix())) {
            this.addNamespace(i);
         } else if ("xmlns".equals(a.getLocalName())) {
            this.addNamespace(i);
         }
      }

   }

   public int getAttributeCount() {
      if (this.isStartElement()) {
         return this.numAttr;
      } else {
         throw new IllegalStateException("Unable to access attributes on a non START_ELEMENT");
      }
   }

   public String getAttributeValue(String namespaceUri, String localName) {
      for(int i = 0; i < this.getAttributeCount(); ++i) {
         Attr att = this.getAttrInternal(i);
         if (localName.equals(att.getLocalName())) {
            if (namespaceUri == null) {
               return att.getValue();
            }

            if (namespaceUri.equals(att.getNamespaceURI())) {
               return att.getValue();
            }
         }
      }

      return null;
   }

   private Attr getAttrInternal(int index) {
      return this.attributeNodes == null ? null : (Attr)this.attributeNodes.item(this.attributes[index]);
   }

   private Attr getNSInternal(int index) {
      return this.attributeNodes == null ? null : (Attr)this.attributeNodes.item(this.namespaces[index]);
   }

   public String getAttributeNamespace(int index) {
      this.checkStartElement();
      Attr a = this.getAttrInternal(index);
      return a == null ? null : a.getNamespaceURI();
   }

   public String getAttributeLocalName(int index) {
      this.checkStartElement();
      Attr a = this.getAttrInternal(index);
      if (a == null) {
         return null;
      } else {
         String localName = a.getLocalName();
         if (localName == null) {
            localName = a.getNodeName();
         }

         return localName;
      }
   }

   public String getAttributePrefix(int index) {
      this.checkStartElement();
      Attr a = this.getAttrInternal(index);
      return a == null ? null : a.getPrefix();
   }

   public String getAttributeValue(int index) {
      this.checkStartElement();
      Attr a = this.getAttrInternal(index);
      return a == null ? null : a.getValue();
   }

   private Element findElement() {
      if (this.current().getNodeType() == 1) {
         return (Element)this.current();
      } else {
         for(Node n = this.current().getParentNode(); n != null; n = n.getParentNode()) {
            if (n.getNodeType() == 1) {
               return (Element)n;
            }
         }

         return null;
      }
   }

   public NamespaceContext getNamespaceContext() {
      return new NamespaceContextNode(this.findElement());
   }

   public int getNamespaceCount() {
      this.checkStartOrEnd();
      return this.numNS;
   }

   public String getNamespaceURI(String prefix) {
      this.checkStartOrEnd();
      Element e = (Element)this.current();

      String result;
      Node n;
      for(result = null; result == null && e != null && e.getNodeType() == 1; e = (Element)n) {
         result = e.getAttributeNS("http://www.w3.org/2000/xmlns/", prefix);
         if (result != null) {
            return result;
         }

         n = e.getParentNode();
         if (n.getNodeType() != 1) {
            return null;
         }
      }

      return result;
   }

   public String getNamespacePrefix(int index) {
      this.checkStartOrEnd();
      Attr a = this.getNSInternal(index);
      return a == null ? null : a.getLocalName();
   }

   public String getNamespaceURI(int index) {
      this.checkStartOrEnd();
      Attr a = this.getNSInternal(index);
      return a == null ? null : checkNull(a.getValue());
   }

   public String getText() {
      if (this.getTextCache() == null) {
         if (this.eventType != 12 && this.eventType != 5 && this.eventType != 4) {
            throw new IllegalStateException("Attempt to access text from an illegal state");
         }

         this.setTextCache(this.current().getNodeValue());
      }

      return this.getTextCache();
   }

   public boolean isWhiteSpace() {
      char[] temp = this.getTextCharacters();

      for(int i = 0; i < temp.length; ++i) {
         if (!XmlChars.isSpace(temp[i])) {
            return false;
         }
      }

      return true;
   }

   public char[] getTextCharacters() {
      if (this.getArrayCache() == null) {
         if (this.eventType != 12 && this.eventType != 5 && this.eventType != 4) {
            throw new IllegalStateException("Attempt to access text from an illegal state");
         }

         this.setArrayCache(this.current().getNodeValue().toCharArray());
      }

      return this.getArrayCache();
   }

   public int getLineNumber() {
      return -1;
   }

   public int getColumnNumber() {
      return -1;
   }

   public static void main(String[] args) throws Exception {
      Builder b = new Builder();
      Node n = b.create(new File(args[0]));
      XMLStreamReader r = new DOMStreamReader(n);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

   }
}
