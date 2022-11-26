package weblogic.xml.stax;

import java.io.FileReader;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.stream.util.XMLEventConsumer;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.stax.util.TypeNames;

public class XMLEventReaderBase implements XMLEventReader, XMLEventConsumer {
   private CircularQueue elementQ;
   private boolean open;
   protected XMLStreamReader reader;
   protected XMLEventAllocator allocator;
   private boolean reachedEOF;
   private ConfigurationContextBase configurationContext;

   public XMLEventReaderBase(XMLStreamReader reader) throws XMLStreamException {
      this(reader, new XMLEventAllocatorBase());
   }

   public XMLEventReaderBase(XMLStreamReader reader, XMLEventAllocator alloc) throws XMLStreamException {
      this.elementQ = new CircularQueue();
      this.open = true;
      this.reachedEOF = false;
      if (reader == null) {
         throw new IllegalArgumentException("XMLStreamReader may not be null");
      } else if (alloc == null) {
         throw new IllegalArgumentException("XMLEvent Allocator may not be null");
      } else {
         this.reader = reader;
         this.open = true;
         this.allocator = alloc;
         if (reader.getEventType() == 7) {
            XMLEvent e = this.allocator.allocate(reader);
            reader.next();
            this.add(e);
         }

      }
   }

   public void setAllocator(XMLEventAllocator allocator) {
      if (allocator == null) {
         throw new IllegalArgumentException("XMLEvent Allocator may not be null");
      } else {
         this.allocator = allocator;
      }
   }

   public String getElementText() throws XMLStreamException {
      StringBuffer buf = new StringBuffer();
      XMLEvent e = this.nextEvent();
      if (!e.isStartElement()) {
         throw new XMLStreamException("Precondition for readText is nextEvent().getTypeEventType() == START_ELEMENT");
      } else {
         while(this.hasNext()) {
            e = this.peek();
            if (e.isStartElement()) {
               throw new XMLStreamException("Unexpected Element start");
            }

            if (e.isCharacters()) {
               buf.append(((Characters)e).getData());
            }

            if (e.isEndElement()) {
               return buf.toString();
            }

            this.next();
         }

         throw new XMLStreamException("Unexpected end of Document");
      }
   }

   public XMLEvent nextTag() throws XMLStreamException {
      while(true) {
         if (this.hasNext()) {
            XMLEvent e = this.nextEvent();
            if (e.isCharacters() && !((Characters)e).isWhiteSpace()) {
               throw new XMLStreamException("Unexpected text");
            }

            if (!e.isStartElement() && !e.isEndElement()) {
               continue;
            }

            return e;
         }

         throw new XMLStreamException("Unexpected end of Document");
      }
   }

   public Object next() {
      try {
         return this.nextEvent();
      } catch (XMLStreamException var2) {
         throw new RuntimeException("Error processing XML with message" + var2.getMessage());
      }
   }

   public XMLEvent nextEvent() throws XMLStreamException {
      if (!this.open) {
         throw new XMLStreamException("Attempt to read from a stream that is not open");
      } else if (this.needsMore() && !this.parseSome()) {
         throw new NoSuchElementException("Attempt to call next() on a stream with no more elements");
      } else {
         return this.get();
      }
   }

   public boolean hasNext() {
      if (!this.open) {
         return false;
      } else if (!this.elementQ.isEmpty()) {
         return true;
      } else {
         try {
            if (this.reader.hasNext()) {
               return true;
            }
         } catch (XMLStreamException var2) {
            return false;
         }

         this.open = false;
         return false;
      }
   }

   public XMLEvent peek() throws XMLStreamException {
      if (!this.elementQ.isEmpty()) {
         return (XMLEvent)this.elementQ.peek();
      } else if (this.parseSome()) {
         return (XMLEvent)this.elementQ.peek();
      } else {
         throw new NoSuchElementException("Attempt to peek() on a  stream that has no more  elements.");
      }
   }

   public void add(XMLEvent event) throws XMLStreamException {
      this.elementQ.add(event);
   }

   protected boolean needsMore() {
      return this.elementQ.isEmpty();
   }

   protected XMLEvent get() throws XMLStreamException {
      return (XMLEvent)this.elementQ.remove();
   }

   protected boolean isOpen() {
      return !this.reachedEOF;
   }

   public void close() {
      this.reachedEOF = true;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   protected boolean parseSome() throws XMLStreamException {
      this.allocator.allocate(this.reader, this);
      if (this.reader.hasNext()) {
         this.reader.next();
      }

      if (!this.reachedEOF && this.reader.getEventType() == 8) {
         this.allocator.allocate(this.reader, this);
         this.reachedEOF = true;
      }

      return !this.needsMore();
   }

   public void setConfigurationContext(ConfigurationContextBase base) {
      this.configurationContext = base;
   }

   public Object getProperty(String name) {
      return this.configurationContext.getProperty(name);
   }

   public static void main(String[] args) throws Exception {
      System.setProperty("javax.xml.stream.XMLInputFactory", "weblogic.xml.stax.XMLStreamInputFactory");
      System.setProperty("javax.xml.stream.XMLEventFactory", "weblogic.xml.stax.EventFactory");
      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLEventReader xmlr = factory.createXMLEventReader(new FileReader(args[0]));

      while(xmlr.hasNext()) {
         XMLEvent e = xmlr.nextEvent();
         System.out.println("[" + TypeNames.getName(e.getEventType()) + "][" + e + "]");
      }

   }
}
