package com.bea.core.repackaged.springframework.util.xml;

import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

class StaxStreamHandler extends AbstractStaxHandler {
   private final XMLStreamWriter streamWriter;

   public StaxStreamHandler(XMLStreamWriter streamWriter) {
      this.streamWriter = streamWriter;
   }

   protected void startDocumentInternal() throws XMLStreamException {
      this.streamWriter.writeStartDocument();
   }

   protected void endDocumentInternal() throws XMLStreamException {
      this.streamWriter.writeEndDocument();
   }

   protected void startElementInternal(QName name, Attributes attributes, Map namespaceMapping) throws XMLStreamException {
      this.streamWriter.writeStartElement(name.getPrefix(), name.getLocalPart(), name.getNamespaceURI());
      Iterator var4 = namespaceMapping.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         String prefix = (String)entry.getKey();
         String namespaceUri = (String)entry.getValue();
         this.streamWriter.writeNamespace(prefix, namespaceUri);
         if ("".equals(prefix)) {
            this.streamWriter.setDefaultNamespace(namespaceUri);
         } else {
            this.streamWriter.setPrefix(prefix, namespaceUri);
         }
      }

      for(int i = 0; i < attributes.getLength(); ++i) {
         QName attrName = this.toQName(attributes.getURI(i), attributes.getQName(i));
         if (!this.isNamespaceDeclaration(attrName)) {
            this.streamWriter.writeAttribute(attrName.getPrefix(), attrName.getNamespaceURI(), attrName.getLocalPart(), attributes.getValue(i));
         }
      }

   }

   protected void endElementInternal(QName name, Map namespaceMapping) throws XMLStreamException {
      this.streamWriter.writeEndElement();
   }

   protected void charactersInternal(String data) throws XMLStreamException {
      this.streamWriter.writeCharacters(data);
   }

   protected void cDataInternal(String data) throws XMLStreamException {
      this.streamWriter.writeCData(data);
   }

   protected void ignorableWhitespaceInternal(String data) throws XMLStreamException {
      this.streamWriter.writeCharacters(data);
   }

   protected void processingInstructionInternal(String target, String data) throws XMLStreamException {
      this.streamWriter.writeProcessingInstruction(target, data);
   }

   protected void dtdInternal(String dtd) throws XMLStreamException {
      this.streamWriter.writeDTD(dtd);
   }

   protected void commentInternal(String comment) throws XMLStreamException {
      this.streamWriter.writeComment(comment);
   }

   public void setDocumentLocator(Locator locator) {
   }

   public void startEntity(String name) throws SAXException {
   }

   public void endEntity(String name) throws SAXException {
   }

   protected void skippedEntityInternal(String name) throws XMLStreamException {
   }
}
