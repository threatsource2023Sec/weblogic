package weblogic.xml.babel.stream;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ChangePrefixMapping;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.Comment;
import weblogic.xml.stream.EndDocument;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.EndPrefixMapping;
import weblogic.xml.stream.EntityReference;
import weblogic.xml.stream.ProcessingInstruction;
import weblogic.xml.stream.Space;
import weblogic.xml.stream.StartDocument;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.StartPrefixMapping;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;

public class SAXEventWriter extends XMLWriter {
   ContentHandler contentHandler;

   public SAXEventWriter() {
   }

   public SAXEventWriter(ContentHandler handler) {
      this.contentHandler = handler;
   }

   public void setContentHandler(ContentHandler handler) {
      this.contentHandler = handler;
   }

   public ContentHandler getContentHandler() {
      return this.contentHandler;
   }

   protected void check() throws XMLStreamException {
      if (this.contentHandler == null) {
         throw new XMLStreamException("Null handler in SAXEventWriter");
      }
   }

   public static String nullToEmptyString(String input) {
      return input == null ? "" : input;
   }

   public static Attributes createAttributes(AttributeIterator iterator) {
      AttributesImpl attributes = new AttributesImpl();

      while(iterator.hasNext()) {
         Attribute attribute = iterator.next();
         attributes.addAttribute(nullToEmptyString(attribute.getName().getNamespaceUri()), attribute.getName().getLocalName(), attribute.getName().getQualifiedName(), attribute.getType(), attribute.getValue());
      }

      return attributes;
   }

   public void write(StartElement element) throws XMLStreamException {
      this.check();
      XMLName name = element.getName();
      Attributes attributes;
      if (this.writeElementNameSpaces) {
         attributes = createAttributes(element.getAttributesAndNamespaces());
      } else {
         attributes = createAttributes(element.getAttributes());
      }

      try {
         this.contentHandler.startElement(nullToEmptyString(name.getNamespaceUri()), name.getLocalName(), name.getQualifiedName(), attributes);
      } catch (SAXException var5) {
         throw new XMLStreamException(var5);
      }
   }

   public void write(EndElement element) throws XMLStreamException {
      try {
         this.contentHandler.endElement(nullToEmptyString(element.getName().getNamespaceUri()), element.getName().getLocalName(), element.getName().getQualifiedName());
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(ProcessingInstruction event) throws XMLStreamException {
      try {
         this.contentHandler.processingInstruction(nullToEmptyString(event.getTarget()), nullToEmptyString(event.getData()));
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(CharacterData event) throws XMLStreamException {
      try {
         if (event.hasContent()) {
            this.contentHandler.characters(event.getContent().toCharArray(), 0, event.getContent().length());
         }

      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(StartDocument event) throws XMLStreamException {
      try {
         this.contentHandler.startDocument();
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(EndDocument event) throws XMLStreamException {
      try {
         this.contentHandler.endDocument();
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(Comment event) throws XMLStreamException {
   }

   public void write(StartPrefixMapping mapping) throws XMLStreamException {
      try {
         this.contentHandler.startPrefixMapping(mapping.getPrefix(), mapping.getNamespaceUri());
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(ChangePrefixMapping mapping) throws XMLStreamException {
      try {
         this.contentHandler.endPrefixMapping(mapping.getPrefix());
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(EndPrefixMapping mapping) throws XMLStreamException {
      try {
         this.contentHandler.endPrefixMapping(mapping.getPrefix());
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(EntityReference reference) throws XMLStreamException {
      try {
         this.contentHandler.skippedEntity(reference.getName().getQualifiedName());
      } catch (SAXException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public void write(Space space) throws XMLStreamException {
      this.write((CharacterData)space);
   }

   public void flush() throws XMLStreamException {
   }

   public boolean write(XMLEvent e) throws XMLStreamException {
      switch (e.getType()) {
         case 2:
            this.write((StartElement)e);
            break;
         case 4:
            this.write((EndElement)e);
            break;
         case 8:
            this.write((ProcessingInstruction)e);
            break;
         case 16:
            this.write((CharacterData)e);
            break;
         case 32:
            this.write((Comment)e);
            break;
         case 64:
            this.write((Space)e);
            break;
         case 128:
            throw new XMLStreamException("Attempt to write a null element.");
         case 256:
            this.write((StartDocument)e);
            break;
         case 512:
            this.write((EndDocument)e);
            break;
         case 1024:
            this.write((StartPrefixMapping)e);
            break;
         case 2048:
            this.write((EndPrefixMapping)e);
            break;
         case 4096:
            this.write((ChangePrefixMapping)e);
            break;
         case 8192:
            this.write((EntityReference)e);
            break;
         default:
            throw new XMLStreamException("Attempt to write unknown element [" + e.getType() + "]");
      }

      return true;
   }

   public static SAXEventWriter getWriter(ContentHandler contentHandler) throws XMLStreamException {
      SAXEventWriter xmlWriter = new SAXEventWriter();
      xmlWriter.setContentHandler(contentHandler);
      xmlWriter.setWriteHeader(false);
      xmlWriter.setWriteElementNameSpaces(true);
      xmlWriter.setWriteAll(true);
      xmlWriter.setShowNamespaceBindings(true);
      xmlWriter.setNormalizeWhiteSpace(false);
      return xmlWriter;
   }
}
