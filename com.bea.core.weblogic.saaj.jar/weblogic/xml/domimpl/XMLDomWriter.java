package weblogic.xml.domimpl;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import weblogic.xml.util.WriteNamespaceHandler;

public final class XMLDomWriter implements XMLStreamWriter {
   private final ElementBase topElem;
   private final DocumentImpl ownerDocument;
   private final WriteNamespaceHandler writeNamespaceHandler;
   private ElementBase curr;

   public XMLDomWriter(DocumentImpl doc) {
      this(doc.docElement);
   }

   public XMLDomWriter(ElementBase parent_elem) {
      this(parent_elem, XMLDomWriter.NoopWriteNamespaceHandler.INSTANCE);
   }

   public XMLDomWriter(ElementBase parent_elem, WriteNamespaceHandler writeNamespaceHandler) {
      if (parent_elem == null) {
         throw new IllegalArgumentException("null parent element");
      } else {
         this.topElem = parent_elem;
         this.ownerDocument = parent_elem.ownerDocument();
         this.curr = parent_elem;
         this.writeNamespaceHandler = writeNamespaceHandler;
      }
   }

   public Element getCurrentNode() {
      return this.curr;
   }

   public void close() throws XMLStreamException {
   }

   public void flush() throws XMLStreamException {
   }

   public void writeEndDocument() throws XMLStreamException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void writeEndElement() throws XMLStreamException {
      if (this.curr == this.topElem) {
         throw new IllegalStateException("mismatched start/end elements");
      } else {
         this.curr = (ElementBase)this.curr.parentNode();
         if (this.curr == null) {
            throw new IllegalStateException("mismatched start/end elements: null parent");
         }
      }
   }

   public void writeStartDocument() throws XMLStreamException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.writeCharacters(new String(text, start, len));
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.writeNamespace((String)null, uri);
   }

   public void writeCData(String data) throws XMLStreamException {
      this.curr.appendChild(this.ownerDocument.createCDATASection(data));
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.curr.appendChild(this.ownerDocument.createTextNode(text));
   }

   public void writeComment(String data) throws XMLStreamException {
      this.curr.appendChild(this.ownerDocument.createComment(data));
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      throw new UnsupportedOperationException("dtd not supported");
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      Attr nsAttribute = this.ownerDocument.createDefaultNSAttribute();
      nsAttribute.setValue(namespaceURI);
      this.curr.setAttributeNodeNS(nsAttribute);
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.writeEmptyElement((String)null, localName, (String)null);
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.curr.appendChild(this.ownerDocument.createEntityReference(name));
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.writeProcessingInstruction(target, (String)null);
   }

   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
      this.curr.appendChild(this.ownerDocument.createProcessingInstruction(target, data));
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.writeStartElement((String)null, localName);
   }

   public NamespaceContext getNamespaceContext() {
      return this.curr;
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      throw new UnsupportedOperationException();
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.getNamespaceContext().getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeEmptyElement((String)null, localName, namespaceURI);
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      assert namespaceURI != null;

      this.writeNamespaceOnNode(this.curr, prefix, namespaceURI);
   }

   private void writeNamespaceOnNode(Element node, String prefix, String namespaceURI) throws XMLStreamException {
      if (prefix == null) {
         this.writeDefaultNamespace(namespaceURI);
      } else {
         assert prefix.length() > 0;

         if (this.writeNamespaceHandler.writeNamespaceOnElement(node, prefix, namespaceURI)) {
            return;
         }

         Attr nsAttribute = this.ownerDocument.createNSAttribute(prefix);
         nsAttribute.setValue(namespaceURI);
         node.setAttributeNodeNS(nsAttribute);
      }

   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeStartElement((String)null, localName, namespaceURI);
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.curr.appendChild(this.ownerDocument.createElementNS(namespaceURI, localName, prefix));
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      ElementNSImpl elementNS = this.ownerDocument.createElementNS(namespaceURI, localName, prefix);
      this.curr.appendChild(elementNS);
      this.curr = elementNS;
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      this.writeAttribute((String)null, (String)null, localName, value);
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      this.writeAttribute((String)null, namespaceURI, localName, value);
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      Attr att = this.ownerDocument.createNonNSAttributeNS(namespaceURI, localName, prefix);
      att.setValue(value);
      this.curr.setAttributeNodeNS(att);
   }

   private static final class NoopWriteNamespaceHandler implements WriteNamespaceHandler {
      static final WriteNamespaceHandler INSTANCE = new NoopWriteNamespaceHandler();

      public boolean writeNamespaceOnElement(Element node, String prefix, String namespaceURI) {
         return false;
      }
   }
}
