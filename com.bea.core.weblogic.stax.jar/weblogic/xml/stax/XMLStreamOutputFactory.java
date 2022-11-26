package weblogic.xml.stax;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

public class XMLStreamOutputFactory extends XMLOutputFactory {
   public static final String IS_ESCAPING_CHARS = "javax.xml.stream.isEscapingCharacters";
   public static final String IS_ESCAPING_CR = "weblogic.xml.stream.isEscapingCR";
   public static final String IS_WRITING_DTD = "weblogic.xml.stream.isWritingDTD";
   public static final String IS_ESCAPING_CRLFTAB = "weblogic.xml.stream.isEscapingCRLFTAB";
   ConfigurationContextBase config = new OutputConfigurationContext();
   private static final int WRITER_BUFSIZ = 512;

   public static XMLOutputFactory newInstance() {
      return new XMLStreamOutputFactory();
   }

   public XMLStreamWriter createXMLStreamWriter(Writer stream) throws XMLStreamException {
      XMLWriterBase b = new XMLWriterBase(stream);
      b.setConfigurationContext(this.config);
      return b;
   }

   public XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException {
      return this.createXMLStreamWriter(stream, "UTF-8");
   }

   public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException {
      try {
         OutputStreamWriter osw = new OutputStreamWriter(stream, encoding);
         return this.createXMLStreamWriter((Writer)(new BufferedWriter(osw, 512)));
      } catch (UnsupportedEncodingException var4) {
         throw new XMLStreamException("Unsupported encoding " + encoding, var4);
      }
   }

   public XMLEventWriter createXMLEventWriter(OutputStream stream) throws XMLStreamException {
      return new XMLEventWriterBase(this.createXMLStreamWriter(stream));
   }

   public XMLEventWriter createXMLEventWriter(Writer stream) throws XMLStreamException {
      return new XMLEventWriterBase(this.createXMLStreamWriter(stream));
   }

   public XMLEventWriter createXMLEventWriter(OutputStream stream, String encoding) throws XMLStreamException {
      return new XMLEventWriterBase(this.createXMLStreamWriter(stream, encoding));
   }

   public void setProperty(String name, Object value) {
      this.config.setProperty(name, value);
   }

   public Object getProperty(String name) {
      return this.config.getProperty(name);
   }

   public boolean isPropertySupported(String name) {
      return this.config.isSupported(name);
   }

   public boolean isPrefixDefaulting() {
      return this.config.isPrefixDefaulting();
   }

   public void setPrefixDefaulting(boolean val) {
      this.config.setPrefixDefaulting(val);
   }

   public String toString() {
      return "weblogic.xml.stax.XMLStreamOutputFactory[\n" + this.config + "]\n";
   }

   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }
}
