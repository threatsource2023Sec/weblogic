package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;

class XMLEventStreamReader extends AbstractXMLStreamReader {
   private XMLEvent event;
   private final XMLEventReader eventReader;

   public XMLEventStreamReader(XMLEventReader eventReader) throws XMLStreamException {
      this.eventReader = eventReader;
      this.event = eventReader.nextEvent();
   }

   public QName getName() {
      if (this.event.isStartElement()) {
         return this.event.asStartElement().getName();
      } else if (this.event.isEndElement()) {
         return this.event.asEndElement().getName();
      } else {
         throw new IllegalStateException();
      }
   }

   public Location getLocation() {
      return this.event.getLocation();
   }

   public int getEventType() {
      return this.event.getEventType();
   }

   @Nullable
   public String getVersion() {
      return this.event.isStartDocument() ? ((StartDocument)this.event).getVersion() : null;
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.eventReader.getProperty(name);
   }

   public boolean isStandalone() {
      if (this.event.isStartDocument()) {
         return ((StartDocument)this.event).isStandalone();
      } else {
         throw new IllegalStateException();
      }
   }

   public boolean standaloneSet() {
      if (this.event.isStartDocument()) {
         return ((StartDocument)this.event).standaloneSet();
      } else {
         throw new IllegalStateException();
      }
   }

   @Nullable
   public String getEncoding() {
      return null;
   }

   @Nullable
   public String getCharacterEncodingScheme() {
      return null;
   }

   public String getPITarget() {
      if (this.event.isProcessingInstruction()) {
         return ((ProcessingInstruction)this.event).getTarget();
      } else {
         throw new IllegalStateException();
      }
   }

   public String getPIData() {
      if (this.event.isProcessingInstruction()) {
         return ((ProcessingInstruction)this.event).getData();
      } else {
         throw new IllegalStateException();
      }
   }

   public int getTextStart() {
      return 0;
   }

   public String getText() {
      if (this.event.isCharacters()) {
         return this.event.asCharacters().getData();
      } else if (this.event.getEventType() == 5) {
         return ((Comment)this.event).getText();
      } else {
         throw new IllegalStateException();
      }
   }

   public int getAttributeCount() {
      if (!this.event.isStartElement()) {
         throw new IllegalStateException();
      } else {
         Iterator attributes = this.event.asStartElement().getAttributes();
         return countIterator(attributes);
      }
   }

   public boolean isAttributeSpecified(int index) {
      return this.getAttribute(index).isSpecified();
   }

   public QName getAttributeName(int index) {
      return this.getAttribute(index).getName();
   }

   public String getAttributeType(int index) {
      return this.getAttribute(index).getDTDType();
   }

   public String getAttributeValue(int index) {
      return this.getAttribute(index).getValue();
   }

   private Attribute getAttribute(int index) {
      if (!this.event.isStartElement()) {
         throw new IllegalStateException();
      } else {
         int count = 0;

         for(Iterator attributes = this.event.asStartElement().getAttributes(); attributes.hasNext(); ++count) {
            Attribute attribute = (Attribute)attributes.next();
            if (count == index) {
               return attribute;
            }
         }

         throw new IllegalArgumentException();
      }
   }

   public NamespaceContext getNamespaceContext() {
      if (this.event.isStartElement()) {
         return this.event.asStartElement().getNamespaceContext();
      } else {
         throw new IllegalStateException();
      }
   }

   public int getNamespaceCount() {
      Iterator namespaces;
      if (this.event.isStartElement()) {
         namespaces = this.event.asStartElement().getNamespaces();
      } else {
         if (!this.event.isEndElement()) {
            throw new IllegalStateException();
         }

         namespaces = this.event.asEndElement().getNamespaces();
      }

      return countIterator(namespaces);
   }

   public String getNamespacePrefix(int index) {
      return this.getNamespace(index).getPrefix();
   }

   public String getNamespaceURI(int index) {
      return this.getNamespace(index).getNamespaceURI();
   }

   private Namespace getNamespace(int index) {
      Iterator namespaces;
      if (this.event.isStartElement()) {
         namespaces = this.event.asStartElement().getNamespaces();
      } else {
         if (!this.event.isEndElement()) {
            throw new IllegalStateException();
         }

         namespaces = this.event.asEndElement().getNamespaces();
      }

      for(int count = 0; namespaces.hasNext(); ++count) {
         Namespace namespace = (Namespace)namespaces.next();
         if (count == index) {
            return namespace;
         }
      }

      throw new IllegalArgumentException();
   }

   public int next() throws XMLStreamException {
      this.event = this.eventReader.nextEvent();
      return this.event.getEventType();
   }

   public void close() throws XMLStreamException {
      this.eventReader.close();
   }

   private static int countIterator(Iterator iterator) {
      int count;
      for(count = 0; iterator.hasNext(); ++count) {
         iterator.next();
      }

      return count;
   }
}
