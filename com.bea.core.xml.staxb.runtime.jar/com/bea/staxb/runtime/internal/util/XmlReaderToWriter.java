package com.bea.staxb.runtime.internal.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public final class XmlReaderToWriter {
   private XmlReaderToWriter() {
   }

   public static void writeAll(XMLStreamReader xmlr, XMLStreamWriter writer) throws XMLStreamException {
      while(xmlr.hasNext()) {
         write(xmlr, writer);
         xmlr.next();
      }

      write(xmlr, writer);
      writer.flush();
   }

   public static void write(XMLStreamReader xmlr, XMLStreamWriter writer) throws XMLStreamException {
      String prefix;
      switch (xmlr.getEventType()) {
         case 1:
            String localName = xmlr.getLocalName();
            String namespaceURI = xmlr.getNamespaceURI();
            if (namespaceURI != null && namespaceURI.length() > 0) {
               prefix = xmlr.getPrefix();
               if (prefix != null) {
                  writer.writeStartElement(prefix, localName, namespaceURI);
               } else {
                  writer.writeStartElement(namespaceURI, localName);
               }
            } else {
               writer.writeStartElement(localName);
            }

            int i = 0;

            int len;
            for(len = xmlr.getNamespaceCount(); i < len; ++i) {
               writer.writeNamespace(xmlr.getNamespacePrefix(i), xmlr.getNamespaceURI(i));
            }

            i = 0;

            for(len = xmlr.getAttributeCount(); i < len; ++i) {
               String attUri = xmlr.getAttributeNamespace(i);
               String lname = xmlr.getAttributeLocalName(i);
               if (attUri != null && attUri.length() > 0) {
                  writer.writeAttribute(xmlr.getAttributePrefix(i), attUri, lname, xmlr.getAttributeValue(i));
               } else {
                  writer.writeAttribute(lname, xmlr.getAttributeValue(i));
               }
            }

            return;
         case 2:
            writer.writeEndElement();
            break;
         case 3:
            writer.writeProcessingInstruction(xmlr.getPITarget(), xmlr.getPIData());
            break;
         case 4:
         case 6:
            writer.writeCharacters(xmlr.getTextCharacters(), xmlr.getTextStart(), xmlr.getTextLength());
            break;
         case 5:
            writer.writeComment(xmlr.getText());
            break;
         case 7:
            prefix = xmlr.getCharacterEncodingScheme();
            String version = xmlr.getVersion();
            if (prefix != null && version != null) {
               writer.writeStartDocument(prefix, version);
            } else if (version != null) {
               writer.writeStartDocument(xmlr.getVersion());
            }
            break;
         case 8:
            writer.writeEndDocument();
            break;
         case 9:
            writer.writeEntityRef(xmlr.getLocalName());
         case 10:
         default:
            break;
         case 11:
            writer.writeDTD(xmlr.getText());
            break;
         case 12:
            writer.writeCData(xmlr.getText());
      }

   }
}
