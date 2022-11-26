package org.apache.xml.security.stax.impl;

import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecCharacters;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecurityEventWriter implements XMLEventWriter {
   private final XMLStreamWriter xmlStreamWriter;

   public XMLSecurityEventWriter(XMLStreamWriter xmlStreamWriter) {
      this.xmlStreamWriter = xmlStreamWriter;
   }

   public void add(XMLEvent event) throws XMLStreamException {
      if (!(event instanceof XMLSecEvent)) {
         throw new IllegalArgumentException("XMLEvent must be an instance of XMLSecEvent");
      } else {
         XMLSecEvent xmlSecEvent = (XMLSecEvent)event;
         switch (xmlSecEvent.getEventType()) {
            case 1:
               XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
               QName n = xmlSecStartElement.getName();
               this.xmlStreamWriter.writeStartElement(n.getPrefix(), n.getLocalPart(), n.getNamespaceURI());
               List xmlSecNamespaces = xmlSecStartElement.getOnElementDeclaredNamespaces();

               for(int i = 0; i < xmlSecNamespaces.size(); ++i) {
                  Namespace namespace = (Namespace)xmlSecNamespaces.get(i);
                  this.add((XMLEvent)namespace);
               }

               List xmlSecAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();

               for(int i = 0; i < xmlSecAttributes.size(); ++i) {
                  XMLSecAttribute xmlSecAttribute = (XMLSecAttribute)xmlSecAttributes.get(i);
                  this.add((XMLEvent)xmlSecAttribute);
               }

               return;
            case 2:
               this.xmlStreamWriter.writeEndElement();
               break;
            case 3:
               ProcessingInstruction pi = (ProcessingInstruction)xmlSecEvent;
               this.xmlStreamWriter.writeProcessingInstruction(pi.getTarget(), pi.getData());
               break;
            case 4:
               XMLSecCharacters characters = xmlSecEvent.asCharacters();
               if (!characters.isCData()) {
                  char[] text = characters.getText();
                  this.xmlStreamWriter.writeCharacters(text, 0, text.length);
               } else {
                  this.xmlStreamWriter.writeCData(characters.getData());
               }
               break;
            case 5:
               this.xmlStreamWriter.writeComment(((Comment)xmlSecEvent).getText());
               break;
            case 6:
            case 14:
            case 15:
            default:
               throw new XMLStreamException("Illegal event");
            case 7:
               StartDocument startDocument = (StartDocument)xmlSecEvent;
               if (!startDocument.encodingSet()) {
                  this.xmlStreamWriter.writeStartDocument(startDocument.getVersion());
               } else {
                  this.xmlStreamWriter.writeStartDocument(startDocument.getCharacterEncodingScheme(), startDocument.getVersion());
               }
               break;
            case 8:
               this.xmlStreamWriter.writeEndDocument();
               break;
            case 9:
               this.xmlStreamWriter.writeEntityRef(((EntityReference)xmlSecEvent).getName());
               break;
            case 10:
               Attribute attribute = (Attribute)xmlSecEvent;
               QName name = attribute.getName();
               this.xmlStreamWriter.writeAttribute(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attribute.getValue());
               break;
            case 11:
               this.xmlStreamWriter.writeDTD(((DTD)xmlSecEvent).getDocumentTypeDeclaration());
               break;
            case 12:
               this.xmlStreamWriter.writeCData(xmlSecEvent.asCharacters().getData());
               break;
            case 13:
               Namespace ns = (Namespace)xmlSecEvent;
               this.xmlStreamWriter.writeNamespace(ns.getPrefix(), ns.getNamespaceURI());
         }

      }
   }

   public void add(XMLEventReader reader) throws XMLStreamException {
      while(reader.hasNext()) {
         this.add(reader.nextEvent());
      }

   }

   public void close() throws XMLStreamException {
      this.xmlStreamWriter.close();
   }

   public void flush() throws XMLStreamException {
      this.xmlStreamWriter.flush();
   }

   public NamespaceContext getNamespaceContext() {
      return this.xmlStreamWriter.getNamespaceContext();
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.xmlStreamWriter.getPrefix(uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.xmlStreamWriter.setDefaultNamespace(uri);
   }

   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
      this.xmlStreamWriter.setNamespaceContext(namespaceContext);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.xmlStreamWriter.setPrefix(prefix, uri);
   }
}
