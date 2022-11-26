package weblogic.xml.stax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
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
import weblogic.xml.babel.reader.XmlReader;

public class XMLStreamInputFactory extends XMLInputFactory {
   final ConfigurationContextBase config = new ConfigurationContextBase();

   public XMLStreamInputFactory() {
      this.checkSecurityOverrides();
   }

   public static XMLInputFactory newInstance() {
      return new XMLStreamInputFactory();
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

   public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(reader));
   }

   public XMLEventReader createXMLEventReader(XMLStreamReader reader) throws XMLStreamException {
      XMLEventReaderBase base;
      if (this.config.getEventAllocator() == null) {
         base = new XMLEventReaderBase(reader);
      } else {
         base = new XMLEventReaderBase(reader, this.config.getEventAllocator());
      }

      return base;
   }

   public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(source));
   }

   public XMLEventReader createXMLEventReader(InputStream stream) throws XMLStreamException {
      return this.createXMLEventReader(this.createXMLStreamReader(stream));
   }

   public XMLStreamReader createXMLStreamReader(String systemId, InputStream stream) throws XMLStreamException {
      XMLStreamReaderBase b = (XMLStreamReaderBase)this.createXMLStreamReader(stream);
      b.setSystemId(systemId);
      return b;
   }

   public XMLStreamReader createXMLStreamReader(String systemId, Reader reader) throws XMLStreamException {
      XMLStreamReaderBase b = (XMLStreamReaderBase)this.createXMLStreamReader(reader);
      b.setSystemId(systemId);
      return b;
   }

   public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
      return this.createXMLEventReader(reader);
   }

   public XMLEventReader createXMLEventReader(String systemId, InputStream stream) throws XMLStreamException {
      return this.createXMLEventReader(stream);
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

   public boolean isPropertySupported(String name) {
      return this.config.isSupported(name);
   }

   public XMLStreamReader createXMLStreamFragmentReader(Reader in, Map namespaces) throws XMLStreamException {
      XMLStreamReaderBase base = new XMLStreamReaderBase();
      base.setInitialNamespaces(namespaces);
      base.setConfigurationContext(this.config);
      base.setInput(in);
      return base;
   }

   public XMLStreamReader createXMLStreamFragmentReader(InputStream stream, String encoding, Map namespaces) throws XMLStreamException {
      try {
         return this.createXMLStreamFragmentReader(new InputStreamReader(stream, encoding), namespaces);
      } catch (UnsupportedEncodingException var5) {
         throw new XMLStreamException("The encoding " + encoding + " is not supported.", var5);
      }
   }

   public XMLStreamReader createXMLStreamReader(Reader in) throws XMLStreamException {
      XMLStreamReaderBase base = new XMLStreamReaderBase();
      base.setConfigurationContext(this.config);
      base.setInput(in);
      return base;
   }

   private void checkSecurityOverrides() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            XMLStreamInputFactory.this.checkPropertyOverride("weblogic.xml.stax.MaxAttrsPerElement", Integer.class);
            XMLStreamInputFactory.this.checkPropertyOverride("weblogic.xml.stax.MaxChildElements", Integer.class);
            XMLStreamInputFactory.this.checkPropertyOverride("weblogic.xml.stax.MaxElementDepth", Integer.class);
            XMLStreamInputFactory.this.checkPropertyOverride("weblogic.xml.stax.MaxInputSize", Long.class);
            XMLStreamInputFactory.this.checkPropertyOverride("weblogic.xml.stax.MaxTotalElements", Long.class);
            XMLStreamInputFactory.this.checkPropertyOverride("weblogic.xml.stax.EnableAllLimitChecks", Boolean.class);
            return null;
         }
      });
   }

   private void checkPropertyOverride(String propName, Class clz) {
      String value = System.getProperty(propName);
      if (value != null) {
         if (Integer.class.equals(clz)) {
            this.config.setProperty(propName, Integer.valueOf(value));
         } else if (Long.class.equals(clz)) {
            this.config.setProperty(propName, Long.valueOf(value));
         } else if (Boolean.class.equals(clz)) {
            this.config.setProperty(propName, Boolean.valueOf(value));
         } else {
            this.config.setProperty(propName, value);
         }

      }
   }

   public String toString() {
      return "weblogic.xml.stax.XMLStreamInputFactory[\n" + this.config + "]\n";
   }
}
