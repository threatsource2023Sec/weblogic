package weblogic.xml.babel.stream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.adapters.ElementConsumer;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.BufferedXMLInputStream;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.ElementEvent;
import weblogic.xml.stream.events.Name;

public class BufferedXMLInputStreamImpl extends XMLInputStreamBase implements BufferedXMLInputStream, ElementConsumer {
   protected List readElements;
   protected XMLInputStream parent;
   protected boolean markSet;
   protected int mark;
   protected int position;
   protected XMLEvent currentElement;

   protected BufferedXMLInputStreamImpl(XMLInputStream parent) {
      this.init(parent);
   }

   protected void init(XMLInputStream parent) {
      this.parent = parent;
      this.readElements = new ArrayList();
      this.open = true;
      this.mark = 0;
      this.position = 0;
      this.markSet = false;
      this.elementQ = new CircularQueue(8);
   }

   public void initChild(XMLInputStreamBase child) {
      int size;
      for(size = this.position; size < this.readElements.size(); ++size) {
         ElementEvent e = (ElementEvent)this.readElements.get(size);
         child.add(e.clone());
      }

      size = this.elementQ.size();

      for(int i = 0; i < size; ++i) {
         ElementEvent e = (ElementEvent)this.elementQ.remove();
         this.elementQ.add(e);
         child.add(e.clone());
      }

   }

   public boolean add(Object obj) {
      if (this.open && obj != null) {
         if (this.markSet) {
            this.readElements.add(obj);
         } else {
            this.elementQ.add(obj);
         }

         this.callbackChildren(obj);
         return true;
      } else {
         return false;
      }
   }

   public void close() throws XMLStreamException {
      this.readElements = null;
      this.open = false;
      this.markSet = false;
      this.mark = 0;
      this.position = 0;
   }

   public boolean hasNext() throws XMLStreamException {
      if (!this.open) {
         return false;
      } else if (this.checkBufferSize()) {
         return true;
      } else {
         return !this.elementQ.isEmpty() ? true : this.parent.hasNext();
      }
   }

   public XMLEvent peek() throws XMLStreamException {
      if (this.checkBufferSize()) {
         return (XMLEvent)this.readElements.get(this.position);
      } else {
         return !this.elementQ.isEmpty() ? (XMLEvent)this.elementQ.peek() : this.parent.peek();
      }
   }

   protected XMLEvent get() throws XMLStreamException {
      XMLEvent e;
      if (this.checkBufferSize()) {
         e = (XMLEvent)this.readElements.get(this.position);
         ++this.position;
      } else {
         e = super.get();
      }

      return e;
   }

   public boolean needsMore() {
      return this.checkBufferSize() ? false : this.elementQ.isEmpty();
   }

   public boolean checkBufferSize() {
      return this.position < this.readElements.size();
   }

   public boolean parseSome() throws XMLStreamException {
      XMLEvent e = this.parent.next();
      return this.add(e);
   }

   public void mark() throws XMLStreamException {
      int size;
      for(size = this.mark; size < this.position; ++size) {
         this.readElements.remove(size);
      }

      this.markSet = true;
      this.mark = this.position;
      size = this.elementQ.size();

      for(int i = 0; i < size; ++i) {
         ElementEvent e = (ElementEvent)this.elementQ.remove();
         this.readElements.add(e);
      }

   }

   public void reset() throws XMLStreamException {
      if (!this.markSet) {
         throw new XMLStreamException("Attempt to reset the stream without setting the mark");
      } else {
         this.markSet = false;
         this.position = this.mark;
      }
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return new SubXMLInputStream(this);
   }

   public static void testRelease(String filename) throws Exception {
      System.out.println("Buffering Stream");
      XMLInputStreamBase root = new XMLInputStreamBase();
      root.open(SAXElementFactory.createInputSource(filename));
      BufferedXMLInputStream input = new BufferedXMLInputStreamImpl(root);
      input.mark();

      while(input.hasNext()) {
         input.next();
      }

      input.reset();
      System.out.println("Getting Sub Streams");

      while(input.skip(2)) {
         XMLInputStream is2 = input.getSubStream();
         XMLEvent elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
         System.out.println("GETTING SUBSTREAMS");

         while(is2.hasNext()) {
            System.out.println("SS:[ " + is2.next() + " ]");
         }

         is2.close();
      }

   }

   public static void testBufferBuffer(String filename) throws Exception {
      System.out.println("-----------------------------------Buffer of Buffer ----------------");
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      XMLInputStream stream = factory.newInputStream(new File(filename));
      BufferedXMLInputStream firstBufferedStream = factory.newBufferedInputStream(stream);
      firstBufferedStream.mark();
      BufferedXMLInputStream bufferedStream = factory.newBufferedInputStream(firstBufferedStream);

      while(bufferedStream.hasNext()) {
         System.out.println(bufferedStream.next());
      }

      System.out.println("-----------------------------------Rewound first Buffer ----------------");
      firstBufferedStream.reset();

      while(firstBufferedStream.hasNext()) {
         System.out.println(firstBufferedStream.next());
      }

   }

   public static void testRewind(String filename) throws Exception {
      System.out.println("-------------- testing rewind ----------");
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      XMLInputStream stream = factory.newInputStream(new File(filename));
      BufferedXMLInputStream bufferedStream = factory.newBufferedInputStream(stream);
      bufferedStream.mark();

      while(bufferedStream.hasNext()) {
         System.out.println(bufferedStream.next());
      }

      bufferedStream.reset();
      System.out.println("-------------- rewinding stream ----------");

      while(bufferedStream.hasNext()) {
         System.out.println(bufferedStream.next());
      }

   }

   public static void testSubStream(String filename) throws Exception {
      System.out.println("-------------- testing substream ----------");
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      XMLInputStream stream = factory.newInputStream(new File(filename));
      BufferedXMLInputStream bufferedStream = factory.newBufferedInputStream(stream);
      XMLInputStream child = bufferedStream.getSubStream();

      while(child.hasNext()) {
         System.out.println("CHILD:" + child.next());
      }

      System.out.println("-------------- iterating over marked stream ----------");
      bufferedStream.mark();

      while(bufferedStream.hasNext()) {
         System.out.println("PARENT:" + bufferedStream.next());
      }

      bufferedStream.reset();
      System.out.println("-------------- rewinding stream ----------");

      while(bufferedStream.hasNext()) {
         System.out.println("REWOUND:" + bufferedStream.next());
      }

   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamBase root = new XMLInputStreamBase();
      root.open(SAXElementFactory.createInputSource(args[0]));
      BufferedXMLInputStream input = new BufferedXMLInputStreamImpl(root);
      input.mark();
      System.out.println("SELECTING STARTELEMENTS");

      XMLInputStream is2;
      XMLEvent elementEvent;
      while(input.skip(2)) {
         is2 = input.getSubStream();
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
         System.out.println("GETTING SUBSTREAMS");

         while(is2.hasNext()) {
            System.out.println("SS:[ " + is2.next() + " ]");
         }

         is2.close();
      }

      input.reset();
      input.mark();
      System.out.println("SELECTING STARTELEMENTS");

      XMLEvent elementEvent;
      while(input.skip(2)) {
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      input.reset();
      input.mark();
      System.out.println("SELECTING ENDELEMENTS");

      while(input.skip(4)) {
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      input.reset();
      input.mark();
      System.out.println("SELECTING STARTELEMENTS that are apple:a ");

      while(input.skip(new Name("apple", "a"), 2)) {
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      input.reset();
      input.mark();
      System.out.println("SELECTING STARTELEMENTS that are apple ");

      while(input.skip(new Name("apple", (String)null), 2)) {
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      input.reset();
      input.mark();
      System.out.println("SELECTING B ELEMENTS ");

      while(input.skip(new Name("B"))) {
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      input.reset();
      input.mark();
      System.out.println("SELECTING STARTELEMENTS");

      while(input.skip(2)) {
         is2 = input.getSubStream();
         elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
         System.out.println("GETTING SUBSTREAMS");

         while(is2.hasNext()) {
            System.out.println("SS:[ " + is2.next() + " ]");
         }

         is2.close();
      }

      testRelease(args[0]);
      testBufferBuffer(args[0]);
      testRewind(args[0]);
      testSubStream(args[0]);
   }
}
