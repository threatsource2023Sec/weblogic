package weblogic.xml.stream.util;

import java.io.Reader;
import java.io.StringReader;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputOutputStream;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;
import weblogic.xml.stream.events.StartElementEvent;

/** @deprecated */
@Deprecated
public class XMLInputOutputStreamBase implements XMLInputOutputStream {
   protected CircularQueue elementQ = new CircularQueue(8);
   protected StartElementEvent lastStartElement = null;
   protected ReferenceResolver resolver = null;

   public void add(XMLEvent e) throws XMLStreamException {
      if (e.isStartElement()) {
         if (!(e instanceof StartElementEvent)) {
            this.lastStartElement = new StartElementEvent((StartElement)e);
         } else {
            this.lastStartElement = (StartElementEvent)e;
         }

         this.elementQ.add(this.lastStartElement);
      } else {
         this.lastStartElement = null;
         this.elementQ.add(e);
      }
   }

   public void add(Attribute attribute) throws XMLStreamException {
      if (this.lastStartElement == null) {
         throw new XMLStreamException("Attempt to add an attribute to a null start element");
      } else {
         if (this.isNamespace(attribute)) {
            this.lastStartElement.addNamespace(attribute);
         } else {
            this.lastStartElement.addAttribute(attribute);
         }

      }
   }

   private boolean isNamespace(Attribute a) {
      XMLName name = a.getName();
      if (name == null) {
         return false;
      } else {
         String prefix = name.getPrefix();
         if (prefix != null && "xmlns".equals(prefix)) {
            return true;
         } else {
            String localName = name.getLocalName();
            return prefix == null && localName != null && "xmlns".equals(localName);
         }
      }
   }

   public void add(XMLInputStream stream) throws XMLStreamException {
      while(stream.hasNext()) {
         this.add(stream.next());
      }

   }

   public void add(String markup) throws XMLStreamException {
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      XMLInputStream stream = factory.newInputStream((Reader)(new StringReader(markup)), (ElementFilter)(new TypeFilter(8318)));
      this.add(stream);
   }

   public void flush() throws XMLStreamException {
   }

   public XMLEvent next() throws XMLStreamException {
      return (XMLEvent)this.elementQ.remove();
   }

   public boolean hasNext() throws XMLStreamException {
      if (this.elementQ == null) {
         return false;
      } else {
         return !this.elementQ.isEmpty();
      }
   }

   public void skip() throws XMLStreamException {
      this.next();
   }

   public void skipElement() throws XMLStreamException {
      int tagCount = 0;
      boolean done = false;

      while(this.hasNext() && !done) {
         XMLEvent e = this.next();
         switch (e.getType()) {
            case 2:
               ++tagCount;
               break;
            case 4:
               --tagCount;
               if (tagCount <= 0) {
                  done = true;
               }
               break;
            case 128:
            case 512:
               done = true;
         }
      }

   }

   public XMLEvent peek() throws XMLStreamException {
      return (XMLEvent)(!this.elementQ.isEmpty() ? (XMLEvent)this.elementQ.peek() : new NullEvent());
   }

   public boolean skip(int elementType) throws XMLStreamException {
      while(this.hasNext()) {
         XMLEvent e = this.peek();
         if (e == null) {
            return false;
         }

         if (e.getType() == 128) {
            return false;
         }

         if ((e.getType() & elementType) != 0) {
            return true;
         }

         this.next();
      }

      return false;
   }

   protected boolean matchName(XMLName name1, XMLName name2) {
      if (name1 != null && name2 != null) {
         if (name1.getNamespaceUri() != null && name1.getNamespaceUri().equals(name2.getNamespaceUri())) {
            return name1.getLocalName() != null ? name1.getLocalName().equals(name2.getLocalName()) : true;
         } else {
            return name1.getLocalName() != null ? name1.getLocalName().equals(name2.getLocalName()) : false;
         }
      } else {
         return false;
      }
   }

   public boolean skip(XMLName name) throws XMLStreamException {
      while(this.hasNext()) {
         XMLEvent e = this.peek();
         switch (e.getType()) {
            case 2:
            case 4:
               if (this.matchName(name, e.getName())) {
                  return true;
               }
            default:
               this.next();
         }
      }

      return false;
   }

   public boolean skip(XMLName name, int elementType) throws XMLStreamException {
      while(this.skip(name)) {
         XMLEvent e = this.peek();
         if ((e.getType() & elementType) != 0) {
            return true;
         }

         this.next();
      }

      return false;
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return new XMLSubStreamBase(this);
   }

   public void close() throws XMLStreamException {
      this.elementQ = null;
   }

   public void close(boolean flush) throws XMLStreamException {
      this.close();
   }

   public void setReferenceResolver(ReferenceResolver resolver) {
      this.resolver = resolver;
   }

   public ReferenceResolver getReferenceResolver() {
      return this.resolver;
   }

   public static void main(String[] args) throws Exception {
      XMLInputOutputStream stream = new XMLInputOutputStreamBase();
      XMLInputStreamBase root = new XMLInputStreamBase();
      root.open(SAXElementFactory.createInputSource(args[0]));
      stream.add(root);

      while(stream.hasNext()) {
         System.out.println(stream.next());
      }

   }
}
