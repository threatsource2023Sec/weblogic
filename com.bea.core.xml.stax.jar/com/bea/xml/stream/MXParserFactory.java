package com.bea.xml.stream;

import com.bea.xml.stream.reader.XmlReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.EventFilter;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;

public class MXParserFactory extends XMLInputFactory {
   ConfigurationContextBase config = new ConfigurationContextBase();

   public static XMLInputFactory newInstance() {
      return XMLInputFactory.newInstance();
   }

   public XMLStreamReader createXMLStreamReader(Source source) throws XMLStreamException {
      return null;
   }

   public XMLStreamReader createXMLStreamReader(InputStream stream) throws XMLStreamException {
      try {
         return this.createXMLStreamReader(XmlReader.createReader(stream));
      } catch (IOException var3) {
         throw new XMLStreamException("Unable to instantiate a reader", var3);
      }
   }

   public XMLStreamReader createXMLStreamReader(InputStream stream, String encoding) throws XMLStreamException {
      try {
         return this.createXMLStreamReader((Reader)(new InputStreamReader(stream, encoding)));
      } catch (UnsupportedEncodingException var4) {
         throw new XMLStreamException("The encoding " + encoding + " is not supported.", var4);
      }
   }

   public XMLStreamReader createXMLStreamReader(String systemId, InputStream stream) throws XMLStreamException {
      return this.createXMLStreamReader(stream);
   }

   public XMLStreamReader createXMLStreamReader(String systemId, Reader reader) throws XMLStreamException {
      return this.createXMLStreamReader(reader);
   }

   public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
      return this.createXMLEventReader(reader);
   }

   public XMLEventReader createXMLEventReader(String systemId, InputStream stream) throws XMLStreamException {
      return this.createXMLEventReader(stream);
   }

   public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(reader));
   }

   public XMLEventReader createXMLEventReader(XMLStreamReader reader) throws XMLStreamException {
      XMLEventReaderBase base;
      if (this.config.getEventAllocator() == null) {
         base = new XMLEventReaderBase(reader);
      } else {
         base = new XMLEventReaderBase(reader, this.config.getEventAllocator().newInstance());
      }

      return base;
   }

   public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(source));
   }

   public XMLEventReader createXMLEventReader(InputStream stream) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(stream));
   }

   public XMLEventReader createXMLEventReader(InputStream stream, String encoding) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(stream, encoding));
   }

   public XMLResolver getXMLResolver() {
      return this.config.getXMLResolver();
   }

   public void setXMLResolver(XMLResolver resolver) {
      this.config.setXMLResolver(resolver);
   }

   public XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) throws XMLStreamException {
      return new StreamReaderFilter(reader, filter);
   }

   public XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
      return new EventReaderFilter(reader, filter);
   }

   public XMLReporter getXMLReporter() {
      return this.config.getXMLReporter();
   }

   public void setXMLReporter(XMLReporter reporter) {
      this.config.setXMLReporter(reporter);
   }

   public void setEventAllocator(XMLEventAllocator allocator) {
      this.config.setEventAllocator(allocator);
   }

   public XMLEventAllocator getEventAllocator() {
      return this.config.getEventAllocator();
   }

   public void setCoalescing(boolean coalescing) {
      this.config.setCoalescing(coalescing);
   }

   public boolean isCoalescing() {
      return this.config.isCoalescing();
   }

   public void setProperty(String name, Object value) throws IllegalArgumentException {
      this.config.setProperty(name, value);
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.config.getProperty(name);
   }

   public XMLStreamReader createXMLStreamReader(Reader in) throws XMLStreamException {
      MXParser pp = new MXParser();
      pp.setInput(in);
      pp.setConfigurationContext(this.config);
      return pp;
   }

   public boolean isPropertySupported(String name) {
      return this.config.isPropertySupported(name);
   }
}
