package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

class StaxEventHandler extends AbstractStaxHandler {
   private final XMLEventFactory eventFactory;
   private final XMLEventWriter eventWriter;

   public StaxEventHandler(XMLEventWriter eventWriter) {
      this.eventFactory = XMLEventFactory.newInstance();
      this.eventWriter = eventWriter;
   }

   public StaxEventHandler(XMLEventWriter eventWriter, XMLEventFactory factory) {
      this.eventFactory = factory;
      this.eventWriter = eventWriter;
   }

   public void setDocumentLocator(@Nullable Locator locator) {
      if (locator != null) {
         this.eventFactory.setLocation(new LocatorLocationAdapter(locator));
      }

   }

   protected void startDocumentInternal() throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createStartDocument());
   }

   protected void endDocumentInternal() throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createEndDocument());
   }

   protected void startElementInternal(QName name, Attributes atts, Map namespaceMapping) throws XMLStreamException {
      List attributes = this.getAttributes(atts);
      List namespaces = this.getNamespaces(namespaceMapping);
      this.eventWriter.add(this.eventFactory.createStartElement(name, attributes.iterator(), namespaces.iterator()));
   }

   private List getNamespaces(Map namespaceMappings) {
      List result = new ArrayList(namespaceMappings.size());
      namespaceMappings.forEach((prefix, namespaceUri) -> {
         result.add(this.eventFactory.createNamespace(prefix, namespaceUri));
      });
      return result;
   }

   private List getAttributes(Attributes attributes) {
      int attrLength = attributes.getLength();
      List result = new ArrayList(attrLength);

      for(int i = 0; i < attrLength; ++i) {
         QName attrName = this.toQName(attributes.getURI(i), attributes.getQName(i));
         if (!this.isNamespaceDeclaration(attrName)) {
            result.add(this.eventFactory.createAttribute(attrName, attributes.getValue(i)));
         }
      }

      return result;
   }

   protected void endElementInternal(QName name, Map namespaceMapping) throws XMLStreamException {
      List namespaces = this.getNamespaces(namespaceMapping);
      this.eventWriter.add(this.eventFactory.createEndElement(name, namespaces.iterator()));
   }

   protected void charactersInternal(String data) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createCharacters(data));
   }

   protected void cDataInternal(String data) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createCData(data));
   }

   protected void ignorableWhitespaceInternal(String data) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createIgnorableSpace(data));
   }

   protected void processingInstructionInternal(String target, String data) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createProcessingInstruction(target, data));
   }

   protected void dtdInternal(String dtd) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createDTD(dtd));
   }

   protected void commentInternal(String comment) throws XMLStreamException {
      this.eventWriter.add(this.eventFactory.createComment(comment));
   }

   protected void skippedEntityInternal(String name) {
   }

   private static final class LocatorLocationAdapter implements Location {
      private final Locator locator;

      public LocatorLocationAdapter(Locator locator) {
         this.locator = locator;
      }

      public int getLineNumber() {
         return this.locator.getLineNumber();
      }

      public int getColumnNumber() {
         return this.locator.getColumnNumber();
      }

      public int getCharacterOffset() {
         return -1;
      }

      public String getPublicId() {
         return this.locator.getPublicId();
      }

      public String getSystemId() {
         return this.locator.getSystemId();
      }
   }
}
