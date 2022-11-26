package weblogic.xml.jaxp;

import java.io.InputStream;
import java.io.Reader;
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
import weblogic.utils.Debug;

public class WebLogicXMLInputFactory extends XMLInputFactory {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private static final String SYSTEM_PROPERTY_MAX_ATTRIBUTES_PER_ELEMENT = "xml.ws.maximum.AttributesPerElement";
   private static final String SYSTEM_PROPERTY_MAX_ELEMENT_DEPTH = "xml.ws.maximum.ElementDepth";
   private static final String P_MAX_ATTRIBUTES_PER_ELEMENT = "com.ctc.wstx.maxAttributesPerElement";
   private static final String P_MAX_ELEMENT_DEPTH = "com.ctc.wstx.maxElementDepth";
   private static final int DEFAULT_MAX_ATTRIBUTES_PER_ELEMENT = 500;
   private static final int DEFAULT_MAX_ELEMENT_DEPTH = 500;
   private XMLInputFactory delegate;

   public WebLogicXMLInputFactory() {
      String[] inputFactories = new String[]{"com.ctc.wstx.stax.WstxInputFactory", "org.codehaus.stax2.XMLInputFactory2", "weblogic.xml.stax.XMLStreamInputFactory", "javax.xml.stream.XMLInputFactory"};
      this.delegate = (XMLInputFactory)Utils.getDelegate(inputFactories);
      this.configProperties(this.delegate);
      if (debug) {
         Debug.say("WebLogicXMLInputFactory is delegating to " + this.delegate.getClass());
      }

   }

   private void configProperties(XMLInputFactory xif) {
      int maxElementDepth;
      if (xif.isPropertySupported("com.ctc.wstx.maxAttributesPerElement")) {
         maxElementDepth = Integer.valueOf(this.buildIntegerValue("xml.ws.maximum.AttributesPerElement", 500));
         xif.setProperty("com.ctc.wstx.maxAttributesPerElement", maxElementDepth);
      }

      if (xif.isPropertySupported("com.ctc.wstx.maxElementDepth")) {
         maxElementDepth = Integer.valueOf(this.buildIntegerValue("xml.ws.maximum.ElementDepth", 500));
         xif.setProperty("com.ctc.wstx.maxElementDepth", maxElementDepth);
      }

   }

   private int buildIntegerValue(String propertyName, int defaultValue) {
      String propVal = System.getProperty(propertyName);
      if (propVal != null && propVal.length() > 0) {
         try {
            Integer value = Integer.parseInt(propVal);
            if (value > 0) {
               return value;
            }
         } catch (NumberFormatException var5) {
            return defaultValue;
         }
      }

      return defaultValue;
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
      this.delegate.setXMLResolver(resolver);
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
