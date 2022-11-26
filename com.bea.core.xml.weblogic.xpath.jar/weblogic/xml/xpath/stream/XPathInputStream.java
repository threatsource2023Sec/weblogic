package weblogic.xml.xpath.stream;

import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;

public final class XPathInputStream implements XMLInputStream {
   private XPathStreamDelegate mDelegate;
   private XMLInputStream mSource;

   public XPathInputStream(XMLInputStream source, FactoryCriteria criteria) {
      if (source == null) {
         throw new IllegalArgumentException("null source.");
      } else if (criteria == null) {
         throw new IllegalArgumentException("null criteria.");
      } else {
         this.mDelegate = new XPathStreamDelegate(criteria);
         this.mSource = source;
      }
   }

   public XMLEvent next() throws XMLStreamException {
      XMLEvent out = this.mSource.next();
      this.mDelegate.observe(out);
      return out;
   }

   public boolean hasNext() throws XMLStreamException {
      return this.mSource.hasNext();
   }

   public void skip() throws XMLStreamException {
      this.mSource.skip();
   }

   public boolean skip(int evType) throws XMLStreamException {
      return this.mSource.skip(evType);
   }

   public boolean skip(XMLName name) throws XMLStreamException {
      return this.mSource.skip(name);
   }

   public boolean skip(XMLName name, int evType) throws XMLStreamException {
      return this.mSource.skip(name, evType);
   }

   public void skipElement() throws XMLStreamException {
      this.mSource.skipElement();
   }

   public XMLEvent peek() throws XMLStreamException {
      return this.mSource.peek();
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return this.mSource.getSubStream();
   }

   public void close() throws XMLStreamException {
      this.mSource.close();
   }

   public ReferenceResolver getReferenceResolver() {
      return this.mSource.getReferenceResolver();
   }

   public void setReferenceResolver(ReferenceResolver rr) {
      this.mSource.setReferenceResolver(rr);
   }
}
