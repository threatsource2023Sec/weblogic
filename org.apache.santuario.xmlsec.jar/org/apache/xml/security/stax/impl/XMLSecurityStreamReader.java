package org.apache.xml.security.stax.impl;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecurityStreamReader implements XMLStreamReader {
   private final InputProcessorChain inputProcessorChain;
   private XMLSecEvent currentXMLSecEvent;
   private boolean skipDocumentEvents = false;
   private static final String ERR_STATE_NOT_ELEM = "Current state not START_ELEMENT or END_ELEMENT";
   private static final String ERR_STATE_NOT_STELEM = "Current state not START_ELEMENT";
   private static final String ERR_STATE_NOT_PI = "Current state not PROCESSING_INSTRUCTION";
   private static final int MASK_GET_TEXT = 6768;

   public XMLSecurityStreamReader(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties) {
      this.inputProcessorChain = inputProcessorChain;
      this.skipDocumentEvents = securityProperties.isSkipDocumentEvents();
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return "javax.xml.stream.isNamespaceAware".equals(name) ? true : null;
   }

   public int next() throws XMLStreamException {
      try {
         this.inputProcessorChain.reset();
         this.currentXMLSecEvent = this.inputProcessorChain.processEvent();
         int eventType = this.currentXMLSecEvent.getEventType();
         if (eventType == 7 && this.skipDocumentEvents) {
            this.currentXMLSecEvent = this.inputProcessorChain.processEvent();
            eventType = this.currentXMLSecEvent.getEventType();
         }

         return eventType;
      } catch (XMLSecurityException var3) {
         throw new XMLStreamException(var3);
      }
   }

   private XMLSecEvent getCurrentEvent() {
      return this.currentXMLSecEvent;
   }

   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != type) {
         throw new XMLStreamException("Event type mismatch");
      } else {
         String uri;
         if (localName != null) {
            if (xmlSecEvent.getEventType() != 1 && xmlSecEvent.getEventType() != 2 && xmlSecEvent.getEventType() != 9) {
               throw new XMLStreamException("Expected non-null local name, but current token not a START_ELEMENT, END_ELEMENT or ENTITY_REFERENCE (was " + xmlSecEvent.getEventType() + ")");
            }

            uri = this.getLocalName();
            if (!uri.equals(localName)) {
               throw new XMLStreamException("Expected local name '" + localName + "'; current local name '" + uri + "'.");
            }
         }

         if (namespaceURI != null) {
            if (xmlSecEvent.getEventType() != 1 && xmlSecEvent.getEventType() != 2) {
               throw new XMLStreamException("Expected non-null NS URI, but current token not a START_ELEMENT or END_ELEMENT (was " + xmlSecEvent.getEventType() + ")");
            }

            uri = this.getNamespaceURI();
            if (namespaceURI.length() == 0) {
               if (uri != null && uri.length() > 0) {
                  throw new XMLStreamException("Expected empty namespace, instead have '" + uri + "'.");
               }
            } else if (!namespaceURI.equals(uri)) {
               throw new XMLStreamException("Expected namespace '" + namespaceURI + "'; have '" + uri + "'.");
            }
         }

      }
   }

   public String getElementText() throws XMLStreamException {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new XMLStreamException("Not positioned on a start element");
      } else {
         StringBuilder stringBuilder = new StringBuilder();

         while(true) {
            int type = this.next();
            switch (type) {
               case 2:
                  return stringBuilder.toString();
               case 3:
               case 5:
                  break;
               case 4:
               case 6:
               case 9:
               case 12:
                  stringBuilder.append(this.getText());
                  break;
               case 7:
               case 8:
               case 10:
               case 11:
               default:
                  throw new XMLStreamException("Expected a text token, got " + type + ".");
            }
         }
      }
   }

   public int nextTag() throws XMLStreamException {
      while(true) {
         int next = this.next();
         switch (next) {
            case 1:
            case 2:
               return next;
            case 3:
            case 5:
            case 6:
               break;
            case 4:
            case 12:
               if (this.isWhiteSpace()) {
                  break;
               }

               throw new XMLStreamException("Received non-all-whitespace CHARACTERS or CDATA event in nextTag().");
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               throw new XMLStreamException("Received event " + next + ", instead of START_ELEMENT or END_ELEMENT.");
         }
      }
   }

   public boolean hasNext() throws XMLStreamException {
      return this.currentXMLSecEvent == null || this.currentXMLSecEvent.getEventType() != 8;
   }

   public void close() throws XMLStreamException {
      try {
         this.inputProcessorChain.reset();
         this.inputProcessorChain.doFinal();
      } catch (XMLSecurityException var2) {
         throw new XMLStreamException(var2);
      }
   }

   public String getNamespaceURI(String prefix) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return xmlSecEvent.asStartElement().getNamespaceURI(prefix);
         case 2:
            XMLSecStartElement xmlSecStartElement = xmlSecEvent.asEndElement().getParentXMLSecStartElement();
            if (xmlSecStartElement != null) {
               return xmlSecStartElement.getNamespaceURI(prefix);
            }

            return null;
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public boolean isStartElement() {
      return this.getCurrentEvent().isStartElement();
   }

   public boolean isEndElement() {
      return this.getCurrentEvent().isEndElement();
   }

   public boolean isCharacters() {
      return this.getCurrentEvent().isCharacters();
   }

   public boolean isWhiteSpace() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      return xmlSecEvent.isCharacters() && xmlSecEvent.asCharacters().isWhiteSpace();
   }

   public String getAttributeValue(String namespaceURI, String localName) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         Attribute attribute = xmlSecEvent.asStartElement().getAttributeByName(new QName(namespaceURI, localName));
         return attribute != null ? attribute.getValue() : null;
      }
   }

   public int getAttributeCount() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().size();
      }
   }

   public QName getAttributeName(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).getName();
      }
   }

   public String getAttributeNamespace(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).getAttributeNamespace().getNamespaceURI();
      }
   }

   public String getAttributeLocalName(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).getName().getLocalPart();
      }
   }

   public String getAttributePrefix(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).getName().getPrefix();
      }
   }

   public String getAttributeType(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).getDTDType();
      }
   }

   public String getAttributeValue(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).getValue();
      }
   }

   public boolean isAttributeSpecified(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecAttribute)xmlSecEvent.asStartElement().getOnElementDeclaredAttributes().get(index)).isSpecified();
      }
   }

   public int getNamespaceCount() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return xmlSecEvent.asStartElement().getOnElementDeclaredNamespaces().size();
         case 2:
            int count = 0;

            for(Iterator namespaceIterator = xmlSecEvent.asEndElement().getNamespaces(); namespaceIterator.hasNext(); ++count) {
               namespaceIterator.next();
            }

            return count;
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public String getNamespacePrefix(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return ((XMLSecNamespace)xmlSecEvent.asStartElement().getOnElementDeclaredNamespaces().get(index)).getPrefix();
         case 2:
            int count = 0;

            for(Iterator namespaceIterator = xmlSecEvent.asEndElement().getNamespaces(); namespaceIterator.hasNext(); ++count) {
               Namespace namespace = (Namespace)namespaceIterator.next();
               if (count == index) {
                  return namespace.getPrefix();
               }
            }

            throw new ArrayIndexOutOfBoundsException(index);
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public String getNamespaceURI(int index) {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return ((XMLSecNamespace)xmlSecEvent.asStartElement().getOnElementDeclaredNamespaces().get(index)).getNamespaceURI();
      }
   }

   public NamespaceContext getNamespaceContext() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 1) {
         throw new IllegalStateException("Current state not START_ELEMENT");
      } else {
         return xmlSecEvent.asStartElement().getNamespaceContext();
      }
   }

   public int getEventType() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent == null) {
         try {
            return this.next();
         } catch (XMLStreamException var3) {
            throw new IllegalStateException(var3);
         }
      } else {
         return xmlSecEvent.isCharacters() && xmlSecEvent.asCharacters().isIgnorableWhiteSpace() ? 6 : xmlSecEvent.getEventType();
      }
   }

   public String getText() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 4:
         case 6:
         case 12:
            return xmlSecEvent.asCharacters().getData();
         case 5:
            return ((Comment)xmlSecEvent).getText();
         case 7:
         case 8:
         case 10:
         default:
            throw new IllegalStateException("Current state not TEXT");
         case 9:
            return ((EntityReference)xmlSecEvent).getDeclaration().getReplacementText();
         case 11:
            return ((DTD)xmlSecEvent).getDocumentTypeDeclaration();
      }
   }

   public char[] getTextCharacters() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 4:
         case 6:
         case 12:
            return xmlSecEvent.asCharacters().getText();
         case 5:
            return ((Comment)xmlSecEvent).getText().toCharArray();
         case 7:
         case 8:
         case 10:
         default:
            throw new IllegalStateException("Current state not TEXT");
         case 9:
            return ((EntityReference)xmlSecEvent).getDeclaration().getReplacementText().toCharArray();
         case 11:
            return ((DTD)xmlSecEvent).getDocumentTypeDeclaration().toCharArray();
      }
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 4:
         case 6:
         case 12:
            xmlSecEvent.asCharacters().getData().getChars(sourceStart, sourceStart + length, target, targetStart);
            return length;
         case 5:
            ((Comment)xmlSecEvent).getText().getChars(sourceStart, sourceStart + length, target, targetStart);
            return length;
         case 7:
         case 8:
         case 10:
         default:
            throw new IllegalStateException("Current state not TEXT");
         case 9:
            ((EntityReference)xmlSecEvent).getDeclaration().getReplacementText().getChars(sourceStart, sourceStart + length, target, targetStart);
            return length;
         case 11:
            ((DTD)xmlSecEvent).getDocumentTypeDeclaration().getChars(sourceStart, sourceStart + length, target, targetStart);
            return length;
      }
   }

   public int getTextStart() {
      return 0;
   }

   public int getTextLength() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 4:
         case 6:
         case 12:
            return xmlSecEvent.asCharacters().getData().length();
         case 5:
            return ((Comment)xmlSecEvent).getText().length();
         case 7:
         case 8:
         case 10:
         default:
            throw new IllegalStateException("Current state not TEXT");
         case 9:
            return ((EntityReference)xmlSecEvent).getDeclaration().getReplacementText().length();
         case 11:
            return ((DTD)xmlSecEvent).getDocumentTypeDeclaration().length();
      }
   }

   public String getEncoding() {
      return this.inputProcessorChain.getDocumentContext().getEncoding();
   }

   public boolean hasText() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      return (1 << xmlSecEvent.getEventType() & 6768) != 0;
   }

   public Location getLocation() {
      return new Location() {
         public int getLineNumber() {
            return -1;
         }

         public int getColumnNumber() {
            return -1;
         }

         public int getCharacterOffset() {
            return -1;
         }

         public String getPublicId() {
            return null;
         }

         public String getSystemId() {
            return null;
         }
      };
   }

   public QName getName() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return xmlSecEvent.asStartElement().getName();
         case 2:
            return xmlSecEvent.asEndElement().getName();
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public String getLocalName() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return xmlSecEvent.asStartElement().getName().getLocalPart();
         case 2:
            return xmlSecEvent.asEndElement().getName().getLocalPart();
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public boolean hasName() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      return xmlSecEvent.getEventType() == 1 || xmlSecEvent.getEventType() == 2;
   }

   public String getNamespaceURI() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return xmlSecEvent.asStartElement().getName().getNamespaceURI();
         case 2:
            return xmlSecEvent.asEndElement().getName().getNamespaceURI();
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public String getPrefix() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            return xmlSecEvent.asStartElement().getName().getPrefix();
         case 2:
            return xmlSecEvent.asEndElement().getName().getPrefix();
         default:
            throw new IllegalStateException("Current state not START_ELEMENT or END_ELEMENT");
      }
   }

   public String getVersion() {
      return null;
   }

   public boolean isStandalone() {
      return false;
   }

   public boolean standaloneSet() {
      return false;
   }

   public String getCharacterEncodingScheme() {
      return null;
   }

   public String getPITarget() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 3) {
         throw new IllegalStateException("Current state not PROCESSING_INSTRUCTION");
      } else {
         return ((ProcessingInstruction)xmlSecEvent).getTarget();
      }
   }

   public String getPIData() {
      XMLSecEvent xmlSecEvent = this.getCurrentEvent();
      if (xmlSecEvent.getEventType() != 3) {
         throw new IllegalStateException("Current state not PROCESSING_INSTRUCTION");
      } else {
         return ((ProcessingInstruction)xmlSecEvent).getData();
      }
   }
}
