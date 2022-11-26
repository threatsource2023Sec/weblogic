package com.bea.xml.stream;

import java.io.FileReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class ReaderToWriter {
   private XMLStreamWriter writer;

   public ReaderToWriter() {
   }

   public ReaderToWriter(XMLStreamWriter xmlw) {
      this.writer = xmlw;
   }

   public void setStreamWriter(XMLStreamWriter xmlw) {
      this.writer = xmlw;
   }

   public void write(XMLStreamReader xmlr) throws XMLStreamException {
      System.out.println("wrote event");
      switch (xmlr.getEventType()) {
         case 1:
            String prefix = xmlr.getPrefix();
            String namespaceURI = xmlr.getNamespaceURI();
            if (namespaceURI != null) {
               if (prefix != null) {
                  this.writer.writeStartElement(xmlr.getPrefix(), xmlr.getLocalName(), xmlr.getNamespaceURI());
               } else {
                  this.writer.writeStartElement(xmlr.getNamespaceURI(), xmlr.getLocalName());
               }
            } else {
               this.writer.writeStartElement(xmlr.getLocalName());
            }

            for(int i = 0; i < xmlr.getNamespaceCount(); ++i) {
               this.writer.writeNamespace(xmlr.getNamespacePrefix(i), xmlr.getNamespaceURI(i));
            }

            return;
         case 2:
            this.writer.writeEndElement();
            break;
         case 3:
            this.writer.writeProcessingInstruction(xmlr.getPITarget(), xmlr.getPIData());
            break;
         case 4:
         case 6:
            this.writer.writeCharacters(xmlr.getTextCharacters(), xmlr.getTextStart(), xmlr.getTextLength());
            break;
         case 5:
            this.writer.writeComment(xmlr.getText());
            break;
         case 7:
            String encoding = xmlr.getCharacterEncodingScheme();
            String version = xmlr.getVersion();
            if (encoding != null && version != null) {
               this.writer.writeStartDocument(encoding, version);
            } else if (version != null) {
               this.writer.writeStartDocument(xmlr.getVersion());
            }
            break;
         case 8:
            this.writer.writeEndDocument();
            break;
         case 9:
            this.writer.writeEntityRef(xmlr.getLocalName());
         case 10:
         default:
            break;
         case 11:
            this.writer.writeDTD(xmlr.getText());
            break;
         case 12:
            this.writer.writeCData(xmlr.getText());
      }

   }

   public XMLStreamWriter writeAll(XMLStreamReader xmlr) throws XMLStreamException {
      while(xmlr.hasNext()) {
         this.write(xmlr);
         xmlr.next();
      }

      this.writer.flush();
      return this.writer;
   }

   public static void main(String[] args) throws Exception {
      XMLInputFactory xmlif = XMLInputFactory.newInstance();
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader(args[0]));
      XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(System.out);
      ReaderToWriter rtow = new ReaderToWriter(xmlw);

      while(xmlr.hasNext()) {
         rtow.write(xmlr);
         xmlr.next();
      }

      xmlw.flush();
   }
}
