package weblogic.xml.babel.stream;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.xml.sax.ContentHandler;
import weblogic.xml.stream.XMLInputOutputStream;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.util.XMLInputOutputStreamBase;

public class XMLOutputStreamFactoryImpl extends XMLOutputStreamFactory {
   public XMLOutputStream newOutputStream(OutputStream stream) throws XMLStreamException {
      return this.newOutputStream((Writer)(new OutputStreamWriter(stream)));
   }

   public XMLOutputStream newOutputStream(Writer writer) throws XMLStreamException {
      XMLWriter xmlwriter = XMLWriter.getSymmetricWriter(writer);
      return new XMLOutputStreamBase(xmlwriter);
   }

   public XMLOutputStream newOutputStream(Document document) throws XMLStreamException {
      return new DOMOutputStream(document);
   }

   public XMLOutputStream newOutputStream(Document document, DocumentFragment documentFragment) throws XMLStreamException {
      return new DOMOutputStream(document, documentFragment);
   }

   public XMLOutputStream newOutputStream(ContentHandler handler) throws XMLStreamException {
      return new SAXOutputStream(handler);
   }

   public XMLInputOutputStream newInputOutputStream() throws XMLStreamException {
      return new XMLInputOutputStreamBase();
   }

   public XMLOutputStream newDebugOutputStream(Writer writer) throws XMLStreamException {
      XMLWriter xmlwriter = XMLDataWriter.getDebugWriter(writer);
      return new XMLOutputStreamBase(xmlwriter);
   }

   public XMLOutputStream newDebugOutputStream(OutputStream stream) throws XMLStreamException {
      return this.newDebugOutputStream((Writer)(new OutputStreamWriter(stream)));
   }

   public XMLOutputStream newCanonicalOutputStream(Writer writer) throws XMLStreamException {
      return this.newCanonicalOutputStream((Writer)writer, (Map)null);
   }

   public XMLOutputStream newCanonicalOutputStream(OutputStream stream) throws XMLStreamException {
      return this.newCanonicalOutputStream((OutputStream)stream, (Map)null);
   }

   public XMLOutputStream newCanonicalOutputStream(Writer writer, Map namespaces) throws XMLStreamException {
      XMLWriter xmlwriter = new CanonicalWriter(writer, namespaces);
      return new XMLOutputStreamBase(xmlwriter);
   }

   public XMLOutputStream newCanonicalOutputStream(OutputStream stream, Map namespaces) throws XMLStreamException {
      try {
         return this.newCanonicalOutputStream((Writer)(new OutputStreamWriter(stream, "utf-8")), namespaces);
      } catch (UnsupportedEncodingException var4) {
         throw new XMLStreamException("Unable to instantiate the output stream: ", var4);
      }
   }

   public XMLOutputStream newOutputStream(OutputStream stream, boolean writeEmptyElements) throws XMLStreamException {
      return this.newOutputStream((Writer)(new OutputStreamWriter(stream)), writeEmptyElements);
   }

   public XMLOutputStream newOutputStream(Writer writer, boolean writeEmptyElements) throws XMLStreamException {
      XMLWriter xmlwriter = XMLWriter.getSymmetricWriter(writer);
      XMLOutputStreamBase b = new XMLOutputStreamBase(xmlwriter);
      b.setWriteEmptyTags(writeEmptyElements);
      return b;
   }

   public XMLOutputStream newDebugOutputStream(Writer writer, boolean writeEmptyElements) throws XMLStreamException {
      XMLWriter xmlwriter = XMLDataWriter.getDebugWriter(writer);
      XMLOutputStreamBase b = new XMLOutputStreamBase(xmlwriter);
      b.setWriteEmptyTags(writeEmptyElements);
      return b;
   }

   public XMLOutputStream newDebugOutputStream(OutputStream stream, boolean writeEmptyElements) throws XMLStreamException {
      return this.newDebugOutputStream((Writer)(new OutputStreamWriter(stream)), writeEmptyElements);
   }
}
