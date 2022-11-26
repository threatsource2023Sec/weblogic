package weblogic.xml.stax;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.utils.XXEUtils;
import weblogic.xml.stax.util.TypeNames;

public class XMLStreamRecorder extends XMLWriterBase {
   public XMLStreamRecorder(Writer writer) {
      super(writer);
   }

   protected String writeName(String prefix, String namespaceURI, String localName) throws XMLStreamException {
      if (!"".equals(namespaceURI)) {
         this.write("['" + namespaceURI + "':");
      } else {
         this.write("[");
      }

      prefix = super.writeName(prefix, namespaceURI, localName);
      this.write(']');
      return prefix;
   }

   protected void writeType(int type) throws XMLStreamException {
      this.closeStartElement();
      this.write('[');
      this.write(TypeNames.getName(type));
      this.write(']');
   }

   protected void openStartTag() throws XMLStreamException {
      this.write('[');
   }

   protected void closeStartTag() throws XMLStreamException {
      this.write("];\n");
   }

   protected void openEndTag() throws XMLStreamException {
      this.write('[');
   }

   protected void closeEndTag() throws XMLStreamException {
      this.write(']');
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      this.write("[[ATTRIBUTE]");
      this.writeName("", namespaceURI, localName);
      this.write("=");
      this.writeCharactersInternal(value.toCharArray(), 0, value.length(), true);
      this.write("]");
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before a namespace");
      } else if (prefix != null && !"".equals(prefix) && !"xmlns".equals(prefix)) {
         this.write("[[NAMESPACE][");
         this.write("xmlns:");
         this.write(prefix);
         this.write("]=[");
         this.write(namespaceURI);
         this.write("]");
         this.setPrefix(prefix, namespaceURI);
         this.write(']');
      } else {
         this.writeDefaultNamespace(namespaceURI);
      }
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      this.write("[[DEFAULT][");
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before the default namespace");
      } else {
         this.write("xmlns]");
         this.write("=[");
         this.write(namespaceURI);
         this.write("]");
         this.setPrefix("", namespaceURI);
         this.write(']');
      }
   }

   public void writeComment(String data) throws XMLStreamException {
      this.closeStartElement();
      this.write("[");
      if (data != null) {
         this.write(data);
      }

      this.write("]");
   }

   public void writeProcessingInstruction(String target, String text) throws XMLStreamException {
      this.closeStartElement();
      this.write("[");
      if (target != null) {
         this.write("[" + target + "]");
      }

      if (text != null) {
         this.write(",[" + text + "]");
      }

      this.write("]");
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      this.write("[");
      super.write(dtd);
      this.write("]");
   }

   public void writeCData(String data) throws XMLStreamException {
      this.write("[");
      if (data != null) {
         this.write(data);
      }

      this.write("]");
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.write("[");
      super.writeEntityRef(name);
      this.write("]");
   }

   public void writeStartDocument() throws XMLStreamException {
      this.write("[[1.0],[utf-8]]");
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.write("[[");
      this.write(version);
      this.write("],[utf-8]]");
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.write("[[");
      this.write(version);
      this.write("],[");
      this.write(encoding);
      this.write("]]");
   }

   protected void writeCharactersInternal(char[] characters, int start, int length, boolean isAttributeValue) throws XMLStreamException {
      if (length == 0) {
         this.write("[]");
      } else {
         this.write("[");
         this.write(characters, start, length);
         this.write("]");
      }

   }

   public void write(XMLStreamReader xmlr) throws XMLStreamException {
      this.writeType(xmlr.getEventType());
      super.write((XMLStreamReader)xmlr);
      if (!this.isOpen()) {
         this.write(";\n");
      }

   }

   public static void main(String[] args) throws Exception {
      XMLInputFactory xmlif = XXEUtils.createXMLInputFactoryInstance();
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader(args[0]));
      XMLStreamRecorder r = new XMLStreamRecorder(new OutputStreamWriter(new FileOutputStream("out.stream")));

      while(xmlr.hasNext()) {
         r.write(xmlr);
         xmlr.next();
      }

      r.write(xmlr);
      r.flush();
   }
}
