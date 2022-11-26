package weblogic.xml.jaxp;

import java.io.InputStream;
import java.io.Reader;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;

public class RegistryXMLInputFactory extends XMLInputFactory {
   private XMLInputFactory delegate = null;
   private ChainingXMLResolver chainingXMLResolver;

   public RegistryXMLInputFactory() {
      try {
         RegistryEntityResolver registry = new RegistryEntityResolver();
         this.delegate = registry.getXMLInputFactory();
      } catch (XMLRegistryException var3) {
         XMLLogger.logXMLRegistryException(var3.getMessage());
      }

      if (this.delegate == null) {
         this.delegate = new WebLogicXMLInputFactory();
      }

      this.chainingXMLResolver = new ChainingXMLResolver();

      try {
         this.chainingXMLResolver.pushXMLResolver(new RegistryXMLResolver());
      } catch (XMLRegistryException var2) {
         throw new FactoryConfigurationError(var2, "Could not access XML Registry. " + var2.getMessage());
      }

      this.delegate.setXMLResolver(this.chainingXMLResolver);
   }

   public XMLStreamReader createXMLStreamReader(Reader reader) throws XMLStreamException {
      return this.delegate.createXMLStreamReader(reader);
   }

   public XMLStreamReader createXMLStreamReader(Source source) throws XMLStreamException {
      return this.delegate.createXMLStreamReader(source);
   }

   public XMLStreamReader createXMLStreamReader(InputStream stream) throws XMLStreamException {
      return this.delegate.createXMLStreamReader(stream);
   }

   public XMLStreamReader createXMLStreamReader(InputStream stream, String encoding) throws XMLStreamException {
      return this.delegate.createXMLStreamReader(stream, encoding);
   }

   public XMLStreamReader createXMLStreamReader(String systemId, InputStream stream) throws XMLStreamException {
      return this.delegate.createXMLStreamReader(systemId, stream);
   }

   public XMLStreamReader createXMLStreamReader(String systemId, Reader reader) throws XMLStreamException {
      return this.delegate.createXMLStreamReader(systemId, reader);
   }

   public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
      return this.delegate.createXMLEventReader(reader);
   }

   public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
      return this.delegate.createXMLEventReader(systemId, reader);
   }

   public XMLEventReader createXMLEventReader(XMLStreamReader reader) throws XMLStreamException {
      return this.delegate.createXMLEventReader(reader);
   }

   public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
      return this.delegate.createXMLEventReader(source);
   }

   public XMLEventReader createXMLEventReader(InputStream stream) throws XMLStreamException {
      return this.delegate.createXMLEventReader(stream);
   }

   public XMLEventReader createXMLEventReader(InputStream stream, String encoding) throws XMLStreamException {
      return this.delegate.createXMLEventReader(stream, encoding);
   }

   public XMLEventReader createXMLEventReader(String systemId, InputStream stream) throws XMLStreamException {
      return this.delegate.createXMLEventReader(systemId, stream);
   }

   public XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) throws XMLStreamException {
      return this.delegate.createFilteredReader(reader, filter);
   }

   public XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
      return this.delegate.createFilteredReader(reader, filter);
   }

   public XMLResolver getXMLResolver() {
      return this.delegate.getXMLResolver();
   }

   public void setXMLResolver(XMLResolver resolver) {
      if (this.chainingXMLResolver.getResolverCount() == 2) {
         this.chainingXMLResolver.popXMLResolver();
      }

      this.chainingXMLResolver.pushXMLResolver(resolver);
   }

   public XMLReporter getXMLReporter() {
      return this.delegate.getXMLReporter();
   }

   public void setXMLReporter(XMLReporter reporter) {
      this.delegate.setXMLReporter(reporter);
   }

   public void setProperty(String name, Object value) throws IllegalArgumentException {
      this.delegate.setProperty(name, value);
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.delegate.getProperty(name);
   }

   public boolean isPropertySupported(String name) {
      return this.delegate.isPropertySupported(name);
   }

   public void setEventAllocator(XMLEventAllocator allocator) {
      this.delegate.setEventAllocator(allocator);
   }

   public XMLEventAllocator getEventAllocator() {
      return this.delegate.getEventAllocator();
   }
}
