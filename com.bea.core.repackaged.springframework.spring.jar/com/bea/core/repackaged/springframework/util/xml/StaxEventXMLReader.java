package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.NotationDeclaration;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Locator2;
import org.xml.sax.helpers.AttributesImpl;

class StaxEventXMLReader extends AbstractStaxXMLReader {
   private static final String DEFAULT_XML_VERSION = "1.0";
   private final XMLEventReader reader;
   private String xmlVersion = "1.0";
   @Nullable
   private String encoding;

   StaxEventXMLReader(XMLEventReader reader) {
      try {
         XMLEvent event = reader.peek();
         if (event != null && !event.isStartDocument() && !event.isStartElement()) {
            throw new IllegalStateException("XMLEventReader not at start of document or element");
         }
      } catch (XMLStreamException var3) {
         throw new IllegalStateException("Could not read first element: " + var3.getMessage());
      }

      this.reader = reader;
   }

   protected void parseInternal() throws SAXException, XMLStreamException {
      boolean documentStarted = false;
      boolean documentEnded = false;
      int elementDepth = 0;

      while(this.reader.hasNext() && elementDepth >= 0) {
         XMLEvent event = this.reader.nextEvent();
         if (!event.isStartDocument() && !event.isEndDocument() && !documentStarted) {
            this.handleStartDocument(event);
            documentStarted = true;
         }

         switch (event.getEventType()) {
            case 1:
               ++elementDepth;
               this.handleStartElement(event.asStartElement());
               break;
            case 2:
               --elementDepth;
               if (elementDepth >= 0) {
                  this.handleEndElement(event.asEndElement());
               }
               break;
            case 3:
               this.handleProcessingInstruction((ProcessingInstruction)event);
               break;
            case 4:
            case 6:
            case 12:
               this.handleCharacters(event.asCharacters());
               break;
            case 5:
               this.handleComment((Comment)event);
               break;
            case 7:
               this.handleStartDocument(event);
               documentStarted = true;
               break;
            case 8:
               this.handleEndDocument();
               documentEnded = true;
               break;
            case 9:
               this.handleEntityReference((EntityReference)event);
            case 10:
            case 13:
            default:
               break;
            case 11:
               this.handleDtd((DTD)event);
               break;
            case 14:
               this.handleNotationDeclaration((NotationDeclaration)event);
               break;
            case 15:
               this.handleEntityDeclaration((EntityDeclaration)event);
         }
      }

      if (documentStarted && !documentEnded) {
         this.handleEndDocument();
      }

   }

   private void handleStartDocument(XMLEvent event) throws SAXException {
      if (event.isStartDocument()) {
         StartDocument startDocument = (StartDocument)event;
         String xmlVersion = startDocument.getVersion();
         if (StringUtils.hasLength(xmlVersion)) {
            this.xmlVersion = xmlVersion;
         }

         if (startDocument.encodingSet()) {
            this.encoding = startDocument.getCharacterEncodingScheme();
         }
      }

      if (this.getContentHandler() != null) {
         final Location location = event.getLocation();
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
               return StaxEventXMLReader.this.xmlVersion;
            }

            @Nullable
            public String getEncoding() {
               return StaxEventXMLReader.this.encoding;
            }
         });
         this.getContentHandler().startDocument();
      }

   }

   private void handleStartElement(StartElement startElement) throws SAXException {
      if (this.getContentHandler() != null) {
         QName qName = startElement.getName();
         if (this.hasNamespacesFeature()) {
            Iterator i = startElement.getNamespaces();

            while(i.hasNext()) {
               Namespace namespace = (Namespace)i.next();
               this.startPrefixMapping(namespace.getPrefix(), namespace.getNamespaceURI());
            }

            i = startElement.getAttributes();

            while(i.hasNext()) {
               Attribute attribute = (Attribute)i.next();
               QName attributeName = attribute.getName();
               this.startPrefixMapping(attributeName.getPrefix(), attributeName.getNamespaceURI());
            }

            this.getContentHandler().startElement(qName.getNamespaceURI(), qName.getLocalPart(), this.toQualifiedName(qName), this.getAttributes(startElement));
         } else {
            this.getContentHandler().startElement("", "", this.toQualifiedName(qName), this.getAttributes(startElement));
         }
      }

   }

   private void handleCharacters(Characters characters) throws SAXException {
      char[] data = characters.getData().toCharArray();
      if (this.getContentHandler() != null && characters.isIgnorableWhiteSpace()) {
         this.getContentHandler().ignorableWhitespace(data, 0, data.length);
      } else {
         if (characters.isCData() && this.getLexicalHandler() != null) {
            this.getLexicalHandler().startCDATA();
         }

         if (this.getContentHandler() != null) {
            this.getContentHandler().characters(data, 0, data.length);
         }

         if (characters.isCData() && this.getLexicalHandler() != null) {
            this.getLexicalHandler().endCDATA();
         }

      }
   }

   private void handleEndElement(EndElement endElement) throws SAXException {
      if (this.getContentHandler() != null) {
         QName qName = endElement.getName();
         if (this.hasNamespacesFeature()) {
            this.getContentHandler().endElement(qName.getNamespaceURI(), qName.getLocalPart(), this.toQualifiedName(qName));
            Iterator i = endElement.getNamespaces();

            while(i.hasNext()) {
               Namespace namespace = (Namespace)i.next();
               this.endPrefixMapping(namespace.getPrefix());
            }
         } else {
            this.getContentHandler().endElement("", "", this.toQualifiedName(qName));
         }
      }

   }

   private void handleEndDocument() throws SAXException {
      if (this.getContentHandler() != null) {
         this.getContentHandler().endDocument();
      }

   }

   private void handleNotationDeclaration(NotationDeclaration declaration) throws SAXException {
      if (this.getDTDHandler() != null) {
         this.getDTDHandler().notationDecl(declaration.getName(), declaration.getPublicId(), declaration.getSystemId());
      }

   }

   private void handleEntityDeclaration(EntityDeclaration entityDeclaration) throws SAXException {
      if (this.getDTDHandler() != null) {
         this.getDTDHandler().unparsedEntityDecl(entityDeclaration.getName(), entityDeclaration.getPublicId(), entityDeclaration.getSystemId(), entityDeclaration.getNotationName());
      }

   }

   private void handleProcessingInstruction(ProcessingInstruction pi) throws SAXException {
      if (this.getContentHandler() != null) {
         this.getContentHandler().processingInstruction(pi.getTarget(), pi.getData());
      }

   }

   private void handleComment(Comment comment) throws SAXException {
      if (this.getLexicalHandler() != null) {
         char[] ch = comment.getText().toCharArray();
         this.getLexicalHandler().comment(ch, 0, ch.length);
      }

   }

   private void handleDtd(DTD dtd) throws SAXException {
      if (this.getLexicalHandler() != null) {
         Location location = dtd.getLocation();
         this.getLexicalHandler().startDTD((String)null, location.getPublicId(), location.getSystemId());
      }

      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().endDTD();
      }

   }

   private void handleEntityReference(EntityReference reference) throws SAXException {
      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().startEntity(reference.getName());
      }

      if (this.getLexicalHandler() != null) {
         this.getLexicalHandler().endEntity(reference.getName());
      }

   }

   private Attributes getAttributes(StartElement event) {
      AttributesImpl attributes = new AttributesImpl();

      Iterator i;
      Attribute attribute;
      QName qName;
      String namespaceUri;
      String qName;
      for(i = event.getAttributes(); i.hasNext(); attributes.addAttribute(namespaceUri, qName.getLocalPart(), this.toQualifiedName(qName), qName, attribute.getValue())) {
         attribute = (Attribute)i.next();
         qName = attribute.getName();
         namespaceUri = qName.getNamespaceURI();
         if (namespaceUri == null || !this.hasNamespacesFeature()) {
            namespaceUri = "";
         }

         qName = attribute.getDTDType();
         if (qName == null) {
            qName = "CDATA";
         }
      }

      if (this.hasNamespacePrefixesFeature()) {
         for(i = event.getNamespaces(); i.hasNext(); attributes.addAttribute("", "", qName, "CDATA", namespaceUri)) {
            Namespace namespace = (Namespace)i.next();
            String prefix = namespace.getPrefix();
            namespaceUri = namespace.getNamespaceURI();
            if (StringUtils.hasLength(prefix)) {
               qName = "xmlns:" + prefix;
            } else {
               qName = "xmlns";
            }
         }
      }

      return attributes;
   }
}
