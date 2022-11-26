package weblogic.xml.xpath.stream;

import java.io.StringReader;
import weblogic.xml.babel.stream.XMLInputStreamFactoryImpl;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.util.TypeFilter;

public final class XPathOutputStream implements XMLOutputStream {
   private static final boolean DEBUG = false;
   private XMLOutputStream mDestination;
   private XPathStreamDelegate mDelegate;

   public XPathOutputStream(XMLOutputStream destination, FactoryCriteria criteria) {
      if (destination == null) {
         throw new IllegalArgumentException("null destination.");
      } else if (criteria == null) {
         throw new IllegalArgumentException("null criteria.");
      } else {
         this.mDestination = destination;
         this.mDelegate = new XPathStreamDelegate(criteria);
      }
   }

   public void add(XMLEvent event) throws XMLStreamException {
      this.mDelegate.observe(event);
      this.mDestination.add(event);
   }

   public void add(XMLInputStream inputStream) throws XMLStreamException {
      while(inputStream.hasNext()) {
         this.add(inputStream.next());
      }

   }

   public void add(String markup) throws XMLStreamException {
      new XMLInputStreamFactoryImpl();
      XMLInputStreamFactory factory = XMLInputStreamFactoryImpl.newInstance();
      this.add(factory.newInputStream(new StringReader(markup), new TypeFilter(8318)));
   }

   public void add(Attribute attribute) throws XMLStreamException {
      this.mDestination.add(attribute);
   }

   public void close() throws XMLStreamException {
      this.mDestination.close();
   }

   public void close(boolean flush) throws XMLStreamException {
      this.mDestination.close(flush);
   }

   public void flush() throws XMLStreamException {
      this.mDestination.flush();
   }
}
