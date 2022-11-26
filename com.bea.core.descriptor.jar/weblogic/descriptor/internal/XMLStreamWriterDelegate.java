package weblogic.descriptor.internal;

import com.bea.staxb.runtime.internal.util.PrettyXMLStreamWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLStreamWriterDelegate extends PrettyXMLStreamWriter implements XMLStreamWriter {
   public XMLStreamWriterDelegate(XMLStreamWriter underlying) {
      super(underlying);
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      super.writeComment("something");
      super.writeStartElement(localName);
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      super.writeComment("something");
      super.writeStartElement(namespaceURI, localName);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      super.writeComment("something");
      super.writeStartElement(prefix, localName, namespaceURI);
   }
}
