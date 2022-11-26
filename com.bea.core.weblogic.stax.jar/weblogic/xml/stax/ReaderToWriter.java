package weblogic.xml.stax;

import java.io.FileReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import weblogic.utils.XXEUtils;

public class ReaderToWriter {
   private XMLStreamWriter writer;
   private boolean writeDTD = false;

   public ReaderToWriter() {
   }

   public ReaderToWriter(XMLStreamWriter xmlw) {
      this.writer = xmlw;
   }

   public void setStreamWriter(XMLStreamWriter xmlw) {
      this.writer = xmlw;
   }

   public void writeAll(XMLStreamReader xmlr) throws XMLStreamException {
      while(xmlr.hasNext()) {
         this.write(xmlr);
         xmlr.next();
      }

      this.write(xmlr);
      this.writer.flush();
   }

   public void writeSubTree(XMLStreamReader xmlr) throws XMLStreamException {
      while(xmlr.hasNext()) {
         this.write(xmlr);
         xmlr.next();
      }

   }

   public void write(XMLStreamReader xmlr) throws XMLStreamException {
      String prefix;
      String attUri;
      switch (xmlr.getEventType()) {
         case 1:
            String localName = xmlr.getLocalName();
            String namespaceURI = xmlr.getNamespaceURI();
            if (namespaceURI != null && namespaceURI.length() > 0) {
               prefix = xmlr.getPrefix();
               if (prefix == null) {
                  prefix = "";
               }

               this.writer.writeStartElement(prefix, localName, namespaceURI);
            } else {
               this.writer.writeStartElement(localName);
            }

            int i;
            for(i = 0; i < xmlr.getNamespaceCount(); ++i) {
               this.writer.writeNamespace(xmlr.getNamespacePrefix(i), xmlr.getNamespaceURI(i));
            }

            for(i = 0; i < xmlr.getAttributeCount(); ++i) {
               attUri = xmlr.getAttributeNamespace(i);
               if (attUri != null && attUri.length() > 0) {
                  this.writer.writeAttribute(xmlr.getAttributePrefix(i), attUri, xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
               } else {
                  this.writer.writeAttribute(xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
               }
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
            prefix = xmlr.getCharacterEncodingScheme();
            attUri = xmlr.getVersion();
            if (prefix != null && attUri != null) {
               this.writer.writeStartDocument(prefix, attUri);
            } else if (attUri != null) {
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

   public void setWriteDTD(boolean val) {
      this.writeDTD = val;
   }

   public static void main(String[] args) throws Exception {
      XMLInputFactory xmlif = XXEUtils.createXMLInputFactoryInstance();
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader(args[0]));
      XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(System.out);
      ReaderToWriter rtow = new ReaderToWriter(xmlw);
      rtow.writeAll(xmlr);
   }
}
