package org.apache.xmlbeans.impl.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LoadSaveUtils {
   public static Document xmlText2GenericDom(InputStream is, Document emptyDoc) throws SAXException, ParserConfigurationException, IOException {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true);
      SAXParser parser = factory.newSAXParser();
      Sax2Dom handler = new Sax2Dom(emptyDoc);
      parser.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
      parser.parse(is, handler);
      return (Document)handler.getDOM();
   }

   public static void xmlStreamReader2XmlText(XMLStreamReader xsr, OutputStream os) throws XMLStreamException {
      XMLStreamWriter xsw;
      for(xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(os); xsr.hasNext(); xsr.next()) {
         switch (xsr.getEventType()) {
            case 1:
               xsw.writeStartElement(xsr.getPrefix() == null ? "" : xsr.getPrefix(), xsr.getLocalName(), xsr.getNamespaceURI());
               int attrs = xsr.getAttributeCount();

               int nses;
               for(nses = attrs - 1; nses >= 0; --nses) {
                  xsw.writeAttribute(xsr.getAttributePrefix(nses) == null ? "" : xsr.getAttributePrefix(nses), xsr.getAttributeNamespace(nses), xsr.getAttributeLocalName(nses), xsr.getAttributeValue(nses));
               }

               nses = xsr.getNamespaceCount();

               for(int i = 0; i < nses; ++i) {
                  xsw.writeNamespace(xsr.getNamespacePrefix(i), xsr.getNamespaceURI(i));
               }
               break;
            case 2:
               xsw.writeEndElement();
               break;
            case 3:
               xsw.writeProcessingInstruction(xsr.getPITarget(), xsr.getPIData());
               break;
            case 4:
               xsw.writeCharacters(xsr.getText());
               break;
            case 5:
               xsw.writeComment(xsr.getText());
               break;
            case 6:
               xsw.writeCharacters(xsr.getText());
               break;
            case 7:
               xsw.writeStartDocument();
               break;
            case 8:
               xsw.writeEndDocument();
               break;
            case 9:
               xsw.writeEntityRef(xsr.getText());
               break;
            case 10:
               xsw.writeAttribute(xsr.getPrefix(), xsr.getNamespaceURI(), xsr.getLocalName(), xsr.getText());
               break;
            case 11:
               xsw.writeDTD(xsr.getText());
               break;
            case 12:
               xsw.writeCData(xsr.getText());
               break;
            case 13:
               xsw.writeNamespace(xsr.getPrefix(), xsr.getNamespaceURI());
            case 14:
            case 15:
         }
      }

      xsw.flush();
   }
}
