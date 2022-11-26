package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Locator2;
import org.xml.sax.helpers.AttributesImpl;

class StaxStreamXMLReader extends AbstractStaxXMLReader {
   private static final String DEFAULT_XML_VERSION = "1.0";
   private final XMLStreamReader reader;
   private String xmlVersion = "1.0";
   @Nullable
   private String encoding;

   StaxStreamXMLReader(XMLStreamReader reader) {
      int event = reader.getEventType();
      if (event != 7 && event != 1) {
         throw new IllegalStateException("XMLEventReader not at start of document or element");
      } else {
         this.reader = reader;
      }
   }

   protected void parseInternal() throws SAXException, XMLStreamException {
      boolean documentStarted = false;
      boolean documentEnded = false;
      int elementDepth = 0;
      int eventType = this.reader.getEventType();

      while(true) {
         if (eventType != 7 && eventType != 8 && !documentStarted) {
            this.handleStartDocument();
            documentStarted = true;
         }

         switch (eventType) {
            case 1:
               ++elementDepth;
               this.handleStartElement();
               break;
            case 2:
               --elementDepth;
               if (elementDepth >= 0) {
                  this.handleEndElement();
               }
               break;
            case 3:
               this.handleProcessingInstruction();
               break;
            case 4:
            case 6:
            case 12:
               this.handleCharacters();
               break;
            case 5:
               this.handleComment();
               break;
            case 7:
               this.handleStartDocument();
               documentStarted = true;
               break;
            case 8:
               this.handleEndDocument();
               documentEnded = true;
               break;
            case 9:
               this.handleEntityReference();
            case 10:
            default:
               break;
            case 11:
               this.handleDtd();
         }

         if (!this.reader.hasNext() || elementDepth < 0) {
            if (!documentEnded) {
               this.handleEndDocument();
            }

            return;
         }

         eventType = this.reader.next();
      }
   }

   private void handleStartDocument() throws SAXException {
      if (7 == this.reader.getEventType()) {
         String xmlVersion = this.reader.getVersion();
         if (StringUtils.hasLength(xmlVersion)) {
            this.xmlVersion = xmlVersion;
         }

         this.encoding = this.reader.getCharacterEncodingScheme();
      }

      if (this.getContentHandler() != null) {
         final Location location = this.reader.getLocation();
         this.getContentHandler().setDocumentLocator(new Locator2() {
            public int getColumnNumber() {
               return location != null ? location.getColumnNumber() : -1;
            }

            public int getLineNumber() {
               return location != null ? location.getLineNumber() : -1;
            }

            @Nullable
            public String getPublicId() {
               return location != null ? location.getPublicId() : null;
            }

            @Nullable
            public String getSystemId() {
               return location != null ? location.getSystemId() : null;
            }

            public String getXMLVersion() {
               return StaxStreamXMLReader.this.xmlVersion;
            }

            @Nullable
            public String getEncoding() {
               return StaxStreamXMLReader.this.encoding;
            }
         });
         this.getContentHandler().startDocument();
         if (this.reader.standaloneSet()) {
            this.setStandalone(this.reader.isStandalone());
         }
      }

   }

   private void handleStartElement() throws SAXException {
      if (this.getContentHandler() != null) {
         QName qName = this.reader.getName();
         if (this.hasNamespacesFeature()) {
            int i;
            for(i = 0; i < this.reader.getNamespaceCount(); ++i) {
               this.startPrefixMapping(this.reader.getNamespacePrefix(i), this.reader.getNamespaceURI(i));
            }

            for(i = 0; i < this.reader.getAttributeCount(); ++i) {
               String prefix = this.reader.getAttributePrefix(i);
               String namespace = this.reader.getAttributeNamespace(i);
               if (StringUtils.hasLength(namespace)) {
                  this.startPrefixMapping(prefix, namespace);
               }
            }

            this.getContentHandler().startElement(qName.getNamespaceURI(), qName.getLocalPart(), this.toQualifiedName(qName), this.getAttributes());
         } else {
            this.getContentHandler().startElement("", "", this.toQualifiedName(qName), this.getAttributes());
         }
      }

   }

   private void handleEndElement() throws SAXException {
      if (this.getContentHandler() != null) {
         QName qName = this.reader.getName();
         if (this.hasNamespacesFeature()) {
            this.getContentHandler().endElement(qName.getNamespaceURI(), qName.getLocalPart(), this.toQualifiedName(qName));

            for(int i = 0; i < this.reader.getNamespaceCount(); ++i) {
               String prefix = this.reader.getNamespacePrefix(i);
               if (prefix == null) {
                  prefix = "";
               }

               this.endPrefixMapping(prefix);
            }
         } else {
            this.getContentHandler().endElement("", "", this.toQualifiedName(qName));
         }
      }

   }

   private void handleCharacters() throws SAXException {
      if (12 == this.reader.getEventType() && this.getLexicalHandler() != null) {
         this.getLexicalHandler().startCDATA();
      }

      if (this.getContentHandler() != null) {
         this.getContentHandler().characters(this.reader.getTextCharacters(), this.reader.getTextStart(), this.reader.getTextLength());
      }

      if (12 == this.reader.getEventType() && this.getLexicalHandler() != null) {
         this.getLexicalHandler().endCDATA();
      }

   }

   private void handleComment() throws SAXException {
      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().comment(this.reader.getTextCharacters(), this.reader.getTextStart(), this.reader.getTextLength());
      }

   }

   private void handleDtd() throws SAXException {
      if (this.getLexicalHandler() != null) {
         Location location = this.reader.getLocation();
         this.getLexicalHandler().startDTD((String)null, location.getPublicId(), location.getSystemId());
      }

      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().endDTD();
      }

   }

   private void handleEntityReference() throws SAXException {
      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().startEntity(this.reader.getLocalName());
      }

      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().endEntity(this.reader.getLocalName());
      }

   }

   private void handleEndDocument() throws SAXException {
      if (this.getContentHandler() != null) {
         this.getContentHandler().endDocument();
      }

   }

   private void handleProcessingInstruction() throws SAXException {
      if (this.getContentHandler() != null) {
         this.getContentHandler().processingInstruction(this.reader.getPITarget(), this.reader.getPIData());
      }

   }

   private Attributes getAttributes() {
      AttributesImpl attributes = new AttributesImpl();

      int i;
      String prefix;
      String namespaceUri;
      for(i = 0; i < this.reader.getAttributeCount(); ++i) {
         prefix = this.reader.getAttributeNamespace(i);
         if (prefix == null || !this.hasNamespacesFeature()) {
            prefix = "";
         }

         namespaceUri = this.reader.getAttributeType(i);
         if (namespaceUri == null) {
            namespaceUri = "CDATA";
         }

         attributes.addAttribute(prefix, this.reader.getAttributeLocalName(i), this.toQualifiedName(this.reader.getAttributeName(i)), namespaceUri, this.reader.getAttributeValue(i));
      }

      if (this.hasNamespacePrefixesFeature()) {
         for(i = 0; i < this.reader.getNamespaceCount(); ++i) {
            prefix = this.reader.getNamespacePrefix(i);
            namespaceUri = this.reader.getNamespaceURI(i);
            String qName;
            if (StringUtils.hasLength(prefix)) {
               qName = "xmlns:" + prefix;
            } else {
               qName = "xmlns";
            }

            attributes.addAttribute("", "", qName, "CDATA", namespaceUri);
         }
      }

      return attributes;
   }
}
