package com.bea.xml.stream;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

public class XMLOutputFactoryBase extends XMLOutputFactory {
   ConfigurationContextBase config = new ConfigurationContextBase();

   public static XMLOutputFactory newInstance() {
      return XMLOutputFactory.newInstance();
   }

   public XMLStreamWriter createXMLStreamWriter(Writer stream) throws XMLStreamException {
      XMLWriterBase b = new XMLWriterBase(stream);
      b.setConfigurationContext(this.config);
      return b;
   }

   public XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException {
      return this.createXMLStreamWriter((Writer)(new OutputStreamWriter(stream)));
   }

   public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException {
      try {
         return this.createXMLStreamWriter((Writer)(new OutputStreamWriter(stream, encoding)));
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

   public boolean isPrefixDefaulting() {
      return this.config.isPrefixDefaulting();
   }

   public void setPrefixDefaulting(boolean value) {
      this.config.setPrefixDefaulting(value);
   }

   public boolean isPropertySupported(String name) {
      return this.config.isPropertySupported(name);
   }

   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }
}
