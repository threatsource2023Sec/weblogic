package weblogic.xml.stream.util;

import java.io.FileInputStream;
import java.io.InputStream;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.StartPrefixMapping;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLInputStreamFilter;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;

/** @deprecated */
@Deprecated
public class XMLInputStreamFilterBase implements XMLInputStreamFilter {
   protected XMLInputStream parent;
   protected boolean open = true;
   protected ElementFilter filter;
   private XMLEvent tok;
   private boolean hasTok = false;

   public XMLInputStreamFilterBase() {
   }

   public XMLInputStreamFilterBase(XMLInputStream parent, ElementFilter filter) throws XMLStreamException {
      this.setParent(parent);
      this.filter = filter;
   }

   public void setFilter(ElementFilter filter) {
      this.filter = filter;
   }

   protected XMLEvent modify(XMLEvent e) throws XMLStreamException {
      return e;
   }

   public void pushBack(XMLEvent e) throws XMLStreamException {
      this.tok = e;
      this.hasTok = true;
   }

   public XMLEvent next() throws XMLStreamException {
      if (this.hasTok) {
         this.hasTok = false;
         return this.tok;
      } else {
         return (XMLEvent)(this.hasNext() ? this.modify(this.parent.next()) : new NullEvent());
      }
   }

   public boolean hasNext() throws XMLStreamException {
      if (this.hasTok) {
         return true;
      } else if (!this.open) {
         return false;
      } else {
         while(this.parent.hasNext()) {
            if (this.filter.accept(this.parent.peek())) {
               return true;
            }

            this.parent.next();
         }

         return false;
      }
   }

   public void skip() throws XMLStreamException {
      if (this.open) {
         this.next();
      }

   }

   public void skipElement() throws XMLStreamException {
      if (this.open) {
         this.parent.skipElement();
      }

   }

   public XMLEvent peek() throws XMLStreamException {
      if (!this.open) {
         return new NullEvent();
      } else if (this.hasTok) {
         return this.tok;
      } else {
         return (XMLEvent)(this.hasNext() ? this.modify(this.parent.peek()) : new NullEvent());
      }
   }

   public boolean skip(int elementType) throws XMLStreamException {
      if (!this.open) {
         return false;
      } else {
         while(this.parent.skip(elementType)) {
            if (this.filter.accept(this.parent.peek())) {
               return true;
            }

            this.parent.next();
         }

         return false;
      }
   }

   public boolean skip(XMLName name) throws XMLStreamException {
      if (!this.open) {
         return false;
      } else {
         while(this.parent.skip(name)) {
            if (this.filter.accept(this.parent.peek())) {
               return true;
            }

            this.parent.next();
         }

         return false;
      }
   }

   public boolean skip(XMLName name, int elementType) throws XMLStreamException {
      if (!this.open) {
         return false;
      } else {
         while(this.parent.skip(name, elementType)) {
            if (this.filter.accept(this.parent.peek())) {
               return true;
            }

            this.parent.next();
         }

         return false;
      }
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return new XMLInputStreamFilterBase(this.parent.getSubStream(), this.filter);
   }

   public void close() throws XMLStreamException {
      this.parent.close();
      this.parent = null;
      this.open = false;
   }

   public XMLInputStream getParent() {
      return this.parent;
   }

   public void setParent(XMLInputStream parent) throws XMLStreamException {
      if (parent == null) {
         throw new XMLStreamException("Unable to set XMLInputStreamFilterBase.parent to null");
      } else {
         this.parent = parent;
      }
   }

   public void setReferenceResolver(ReferenceResolver resolver) {
      this.parent.setReferenceResolver(resolver);
   }

   public ReferenceResolver getReferenceResolver() {
      return this.parent.getReferenceResolver();
   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamBase input = new XMLInputStreamBase();
      System.out.println("REGULAR");
      input.open(SAXElementFactory.createInputSource(args[0]));

      while(input.hasNext()) {
         XMLEvent elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      System.out.println("FILTERED");
      input.open(SAXElementFactory.createInputSource(args[0]));
      XMLInputStreamFilterBase filteredInput = new XMLInputStreamFilterBase(input, new TypeFilter(22));

      while(filteredInput.hasNext()) {
         XMLEvent elementEvent = filteredInput.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      System.out.println("FILTERED NS");
      input.open(SAXElementFactory.createInputSource(args[0]));
      String uri = null;
      if (input.skip(1024)) {
         StartPrefixMapping e = (StartPrefixMapping)input.next();
         uri = e.getNamespaceUri();
         System.out.println("Filtering on" + uri);
      }

      filteredInput = new XMLInputStreamFilterBase(input, new NamespaceTypeFilter(uri, 22));

      while(filteredInput.hasNext()) {
         XMLEvent elementEvent = filteredInput.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      System.out.println("Testing use case");
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      TypeFilter filter = new TypeFilter(22);
      XMLInputStream stream = factory.newInputStream((InputStream)(new FileInputStream(args[0])), (ElementFilter)filter);

      while(stream.hasNext()) {
         XMLEvent elementEvent = stream.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

   }
}
