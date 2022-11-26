package weblogic.xml.babel.stream;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.adapters.ElementConsumer;
import weblogic.xml.babel.adapters.ElementFactory;
import weblogic.xml.babel.adapters.XMLInputStreamElementFactory;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.babel.parsers.ValidatingXMLInputStreamParser;
import weblogic.xml.babel.parsers.XMLInputStreamParser;
import weblogic.xml.babel.parsers.XMLStreamParser;
import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.ElementEvent;
import weblogic.xml.stream.events.NullEvent;

public class XMLInputStreamBase implements XMLInputStream, ElementConsumer {
   protected static boolean debugSubStream = false;
   protected InputSource inputSource;
   private XMLStreamParser inputStream;
   private ElementFactory elementFactory;
   protected CircularQueue elementQ;
   protected boolean open = false;
   protected ReferenceResolver resolver = null;
   protected List children = null;
   private int numChildren = 0;

   public void open(InputSource inputSource) throws XMLStreamException {
      this.open(inputSource, new XMLInputStreamElementFactory(), false, true, (Map)null);
   }

   public void openValidating(InputSource inputSource) throws XMLStreamException {
      this.open(inputSource, new XMLInputStreamElementFactory(), true, true, (Map)null);
   }

   public void openFragment(InputSource inputSource, Map namespaces) throws XMLStreamException {
      this.open(inputSource, new XMLInputStreamElementFactory(), true, true, namespaces);
   }

   public void open(InputSource inputSource, ElementFactory factory, boolean validating, boolean isFragmentParser, Map namespaces) throws XMLStreamException {
      this.inputSource = inputSource;
      this.elementQ = new CircularQueue(8);
      this.elementFactory = factory;

      try {
         if (!validating) {
            this.inputStream = new XMLInputStreamParser(inputSource, this.elementFactory, this);
         } else {
            this.inputStream = new ValidatingXMLInputStreamParser(inputSource, this.elementFactory, this, namespaces);
         }

         this.inputStream.setFragmentParser(isFragmentParser);
         this.inputStream.addNamespaceDeclarations(namespaces);
         this.open = true;
      } catch (IOException var7) {
         System.out.println(var7);
         throw new XMLStreamException("Unable to instantiate the stream, the error was: " + var7.getMessage());
      }
   }

   public void clear() {
      this.elementQ.clear();
      this.inputStream.clear();
   }

   public void recycle(Reader reader) throws XMLStreamException {
      if (this.inputStream != null && this.elementFactory != null && this.elementQ != null) {
         try {
            this.clear();
            this.inputStream.recycleStream(reader);
            if (this.children != null) {
               while(this.children.size() > 0) {
                  XMLInputStream s = (XMLInputStream)this.children.get(0);
                  s.close();
                  this.removeChild((XMLInputStreamBase)s);
               }
            }

            this.children = null;
            this.numChildren = 0;
            this.open = true;
         } catch (IOException var3) {
            throw new XMLStreamException(var3);
         }
      } else {
         throw new XMLStreamException("Unable to recycle stream.");
      }
   }

   public boolean addFirst(Object obj) {
      int size = this.elementQ.size();
      if (this.elementQ.size() == 0) {
         this.add(obj);
         return true;
      } else {
         this.add(obj);

         for(int i = 0; i < size; ++i) {
            this.elementQ.add(this.elementQ.remove());
         }

         return true;
      }
   }

   public boolean add(Object obj) {
      if (debugSubStream) {
         System.out.println("add XMLInputStreamBase[" + obj + "]:numChildren[" + this.numChildren + "]");
      }

      if (debugSubStream) {
         System.out.println(this.elementQ);
      }

      ElementEvent e = (ElementEvent)obj;
      this.elementQ.add(obj);
      if (this.numChildren > 0) {
         this.callbackChildren(obj);
      }

      return true;
   }

   public void callbackChildren(Object obj) {
      if (this.children != null) {
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            XMLInputStreamBase s = (XMLInputStreamBase)i.next();
            ElementEvent e = (ElementEvent)obj;
            if (debugSubStream) {
               System.out.println("add from Parent: [" + e + " ][ " + e.clone() + "]");
            }

            if (!s.add(e.clone())) {
               i.remove();
            }
         }

      }
   }

   protected XMLEvent get() throws XMLStreamException {
      XMLEvent element = (XMLEvent)this.elementQ.remove();
      return element;
   }

   public boolean parseSome() throws XMLStreamException {
      return this.inputStream.streamParseSome();
   }

   public boolean needsMore() {
      return this.elementQ.isEmpty();
   }

   public XMLEvent next() throws XMLStreamException {
      if (!this.open) {
         throw new XMLStreamException("Attempt to read from a stream that has not been opened");
      } else {
         if (this.needsMore()) {
            this.parseSome();
         }

         return this.get();
      }
   }

   public boolean hasNext() throws XMLStreamException {
      if (!this.open) {
         return false;
      } else if (!this.elementQ.isEmpty()) {
         return true;
      } else {
         return this.inputStream != null && this.inputStream.hasNext();
      }
   }

   public void skip() throws XMLStreamException {
      if (this.hasNext()) {
         this.next();
      }

   }

   public XMLEvent peek() throws XMLStreamException {
      if (!this.elementQ.isEmpty()) {
         return (XMLEvent)this.elementQ.peek();
      } else {
         return (XMLEvent)(this.inputStream != null && this.inputStream.hasNext() && this.inputStream.streamParseSome() ? (XMLEvent)this.elementQ.peek() : new NullEvent());
      }
   }

   public void close() throws XMLStreamException {
      this.inputSource = null;
      this.open = false;
      if (this.children != null) {
         while(true) {
            if (this.children.size() <= 0) {
               this.children.clear();
               break;
            }

            XMLInputStream s = (XMLInputStream)this.children.get(0);
            s.close();
            this.removeChild((XMLInputStreamBase)s);
         }
      }

      this.children = null;
      this.numChildren = 0;
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

   public void initChild(XMLInputStreamBase child) {
      int size = this.elementQ.size();
      if (debugSubStream) {
         System.out.println("XMLInputStreamBase:initChild:[" + size + "]");
      }

      if (debugSubStream) {
         System.out.println(this.elementQ);
      }

      for(int i = 0; i < size; ++i) {
         ElementEvent e = (ElementEvent)this.elementQ.remove();
         this.elementQ.add(e);
         ElementEvent subElement = (ElementEvent)e.clone();
         child.add(subElement);
         if (debugSubStream) {
            System.out.println("XMLInputStreamBase:initChild:[" + subElement + "]");
         }
      }

   }

   public void addChild(XMLInputStreamBase child) {
      if (debugSubStream) {
         System.out.println("BEGIN Adding and init child");
      }

      if (this.children == null) {
         this.children = new ArrayList();
      }

      ++this.numChildren;
      this.initChild(child);
      this.children.add(child);
      if (debugSubStream) {
         System.out.println("END Adding and init child");
      }

   }

   public void removeChild(XMLInputStreamBase child) {
      if (this.children != null && this.children.remove(child)) {
         --this.numChildren;
      }

   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return new SubXMLInputStream(this);
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

   public void setReferenceResolver(ReferenceResolver resolver) {
      this.resolver = resolver;
   }

   public ReferenceResolver getReferenceResolver() {
      return this.resolver;
   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamBase input = new XMLInputStreamBase();
      input.openValidating(SAXElementFactory.createInputSource(args[0]));

      while(input.hasNext()) {
         XMLEvent elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

   }
}
