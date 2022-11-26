package weblogic.xml.dom;

import java.io.FileReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import weblogic.utils.XXEUtils;
import weblogic.xml.stax.XMLWriterBase;

public class DOMStreamWriter extends XMLWriterBase {
   private Document document;
   private Node current;
   private Node sibling;
   private Node beginCurrent;

   public DOMStreamWriter(Document document) throws XMLStreamException {
      this.setStreamWriter(this);
      this.document = document;
      this.current = document;
   }

   public DOMStreamWriter(Document document, Node current) {
      this.setStreamWriter(this);
      this.document = document;
      this.current = current;
   }

   public DOMStreamWriter(Document document, Node parent, Node reference) {
      this.setStreamWriter(this);
      this.document = document;
      this.current = parent;
      this.sibling = reference;
      this.beginCurrent = this.current;
   }

   public Node getCurrentNode() {
      return this.current;
   }

   public Document getDocument() {
      return this.document;
   }

   protected void openStartTag() throws XMLStreamException {
   }

   protected void closeStartTag() throws XMLStreamException {
      this.flushNamespace();
   }

   protected void openEndTag() throws XMLStreamException {
   }

   protected void closeEndTag() throws XMLStreamException {
   }

   protected String writeName(String prefix, String namespaceURI, String localName) throws XMLStreamException {
      return prefix;
   }

   private String getQualifiedName(String prefix, String namespaceURI, String localName) {
      if (!"".equals(namespaceURI)) {
         prefix = this.getPrefixInternal(namespaceURI);
      }

      String qualifiedName;
      if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         qualifiedName = "xmlns:" + localName;
      } else if (prefix != null && !"".equals(prefix)) {
         qualifiedName = prefix + ":" + localName;
      } else {
         qualifiedName = localName;
      }

      return qualifiedName;
   }

   private void report() {
      System.out.println("appended current:" + this.current);
      System.out.println(Util.printNode(this.document));
   }

   protected void writeStartElementInternal(String namespaceURI, String localName) throws XMLStreamException {
      super.writeStartElementInternal(namespaceURI, localName);
      String qualifiedName = this.getQualifiedName("", namespaceURI, localName);
      this.current = this.appendChild(this.document.createElementNS(namespaceURI, qualifiedName));
   }

   protected void writeStartElementInternal(String prefix, String namespaceURI, String localName) throws XMLStreamException {
      super.writeStartElementInternal(namespaceURI, localName);
      String qualifiedName = this.getQualifiedName(prefix, namespaceURI, localName);
      this.current = this.appendChild(this.document.createElementNS(namespaceURI, qualifiedName));
   }

   protected void write(String s) throws XMLStreamException {
      this.appendChild(this.document.createTextNode(s));
   }

   protected void write(char c) throws XMLStreamException {
      this.write(String.valueOf(c));
   }

   protected void write(char[] c) throws XMLStreamException {
      this.write(String.valueOf(c));
   }

   protected void write(char[] c, int start, int len) throws XMLStreamException {
      this.write(String.valueOf(c, start, len));
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.closeStartElement();
      this.write(text);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.closeStartElement();
      this.write(text, start, len);
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name of an attribute may not be null");
      } else if (value == null) {
         throw new IllegalArgumentException("An attribute value may not be null");
      } else {
         this.writeAttribute("", localName, value);
      }
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI of an attribute may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name of an attribute may not be null");
      } else if (value == null) {
         throw new IllegalArgumentException("An attribute value may not be null");
      } else {
         this.prepareNamespace(namespaceURI);
         Element e = (Element)this.current;
         e.setAttributeNS(namespaceURI, this.getQualifiedName("", namespaceURI, localName), value);
      }
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI of an attribute may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name of an attribute may not be null");
      } else if (value == null) {
         throw new IllegalArgumentException("An attribute value may not be null");
      } else {
         this.prepareNamespace(namespaceURI);
         this.context.bindNamespace(prefix, namespaceURI);
         Element e = (Element)this.current;
         e.setAttributeNS(namespaceURI, this.getQualifiedName(prefix, namespaceURI, localName), value);
      }
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before the default namespace");
      } else {
         Element e = (Element)this.current;
         e.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespaceURI);
         this.setPrefix("", namespaceURI);
      }
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before a namespace");
      } else if (prefix != null && !"".equals(prefix) && !"xmlns".equals(prefix)) {
         Element e = (Element)this.current;
         e.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespaceURI);
         this.setPrefix(prefix, namespaceURI);
      } else {
         this.writeDefaultNamespace(namespaceURI);
      }
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeStartElement(namespaceURI, localName);
      this.writeEndElement();
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.writeStartElement(prefix, localName, namespaceURI);
      this.writeEndElement();
   }

   public void writeEndElement() throws XMLStreamException {
      super.writeEndElement();
      this.current = this.current.getParentNode();
   }

   public void writeComment(String data) throws XMLStreamException {
      this.closeStartElement();
      this.appendChild(this.document.createComment(data));
   }

   public void writeProcessingInstruction(String target, String text) throws XMLStreamException {
      this.closeStartElement();
      this.appendChild(this.document.createProcessingInstruction(target, text));
   }

   public void writeCData(String data) throws XMLStreamException {
      this.closeStartElement();
      this.appendChild(this.document.createCDATASection(data));
   }

   public void writeStartDocument() throws XMLStreamException {
   }

   public void writeStartDocument(String version) throws XMLStreamException {
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
   }

   public void flush() throws XMLStreamException {
   }

   public void writeEndDocument() throws XMLStreamException {
   }

   private final Node appendChild(Node child) {
      return this.current == this.beginCurrent && this.sibling != null ? this.current.insertBefore(child, this.sibling) : this.current.appendChild(child);
   }

   public static void main(String[] args) throws Exception {
      Document d = new DocumentImpl();
      XMLWriterBase writer = new DOMStreamWriter(d);
      XMLInputFactory f = XXEUtils.createXMLInputFactoryInstance();
      XMLStreamReader r = f.createXMLStreamReader(new FileReader(args[0]));

      while(r.hasNext()) {
         writer.write(r);
         r.next();
      }

      System.out.println(Util.printNode(d));
   }
}
