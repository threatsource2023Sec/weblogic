package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

abstract class AbstractXMLStreamReader implements XMLStreamReader {
   public String getElementText() throws XMLStreamException {
      if (this.getEventType() != 1) {
         throw new XMLStreamException("Parser must be on START_ELEMENT to read next text", this.getLocation());
      } else {
         int eventType = this.next();

         StringBuilder builder;
         for(builder = new StringBuilder(); eventType != 2; eventType = this.next()) {
            if (eventType != 4 && eventType != 12 && eventType != 6 && eventType != 9) {
               if (eventType != 3 && eventType != 5) {
                  if (eventType == 8) {
                     throw new XMLStreamException("Unexpected end of document when reading element text content", this.getLocation());
                  }

                  if (eventType == 1) {
                     throw new XMLStreamException("Element text content may not contain START_ELEMENT", this.getLocation());
                  }

                  throw new XMLStreamException("Unexpected event type " + eventType, this.getLocation());
               }
            } else {
               builder.append(this.getText());
            }
         }

         return builder.toString();
      }
   }

   public String getAttributeLocalName(int index) {
      return this.getAttributeName(index).getLocalPart();
   }

   public String getAttributeNamespace(int index) {
      return this.getAttributeName(index).getNamespaceURI();
   }

   public String getAttributePrefix(int index) {
      return this.getAttributeName(index).getPrefix();
   }

   public String getNamespaceURI() {
      int eventType = this.getEventType();
      if (eventType != 1 && eventType != 2) {
         throw new IllegalStateException("Parser must be on START_ELEMENT or END_ELEMENT state");
      } else {
         return this.getName().getNamespaceURI();
      }
   }

   public String getNamespaceURI(String prefix) {
      return this.getNamespaceContext().getNamespaceURI(prefix);
   }

   public boolean hasText() {
      int eventType = this.getEventType();
      return eventType == 6 || eventType == 4 || eventType == 5 || eventType == 12 || eventType == 9;
   }

   public String getPrefix() {
      int eventType = this.getEventType();
      if (eventType != 1 && eventType != 2) {
         throw new IllegalStateException("Parser must be on START_ELEMENT or END_ELEMENT state");
      } else {
         return this.getName().getPrefix();
      }
   }

   public boolean hasName() {
      int eventType = this.getEventType();
      return eventType == 1 || eventType == 2;
   }

   public boolean isWhiteSpace() {
      return this.getEventType() == 6;
   }

   public boolean isStartElement() {
      return this.getEventType() == 1;
   }

   public boolean isEndElement() {
      return this.getEventType() == 2;
   }

   public boolean isCharacters() {
      return this.getEventType() == 4;
   }

   public int nextTag() throws XMLStreamException {
      int eventType;
      for(eventType = this.next(); eventType == 4 && this.isWhiteSpace() || eventType == 12 && this.isWhiteSpace() || eventType == 6 || eventType == 3 || eventType == 5; eventType = this.next()) {
      }

      if (eventType != 1 && eventType != 2) {
         throw new XMLStreamException("expected start or end tag", this.getLocation());
      } else {
         return eventType;
      }
   }

   public void require(int expectedType, String namespaceURI, String localName) throws XMLStreamException {
      int eventType = this.getEventType();
      if (eventType != expectedType) {
         throw new XMLStreamException("Expected [" + expectedType + "] but read [" + eventType + "]");
      }
   }

   @Nullable
   public String getAttributeValue(@Nullable String namespaceURI, String localName) {
      for(int i = 0; i < this.getAttributeCount(); ++i) {
         QName name = this.getAttributeName(i);
         if (name.getLocalPart().equals(localName) && (namespaceURI == null || name.getNamespaceURI().equals(namespaceURI))) {
            return this.getAttributeValue(i);
         }
      }

      return null;
   }

   public boolean hasNext() {
      return this.getEventType() != 8;
   }

   public String getLocalName() {
      return this.getName().getLocalPart();
   }

   public char[] getTextCharacters() {
      return this.getText().toCharArray();
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) {
      char[] source = this.getTextCharacters();
      length = Math.min(length, source.length);
      System.arraycopy(source, sourceStart, target, targetStart, length);
      return length;
   }

   public int getTextLength() {
      return this.getText().length();
   }
}
