package com.bea.xml.stream;

import com.bea.xml.stream.filters.TypeFilter;
import java.io.FileReader;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class EventReaderFilter implements XMLEventReader {
   private XMLEventReader parent;
   private EventFilter filter;

   public EventReaderFilter(XMLEventReader reader) throws XMLStreamException {
      this.parent = reader;
   }

   public EventReaderFilter(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
      this.parent = reader;
      this.filter = filter;
   }

   public void setFilter(EventFilter filter) {
      this.filter = filter;
   }

   public Object next() {
      try {
         return this.nextEvent();
      } catch (XMLStreamException var2) {
         return null;
      }
   }

   public XMLEvent nextEvent() throws XMLStreamException {
      return this.hasNext() ? this.parent.nextEvent() : null;
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

            this.nextEvent();
         }

         throw new XMLStreamException("Unexpected end of Document");
      }
   }

   public XMLEvent nextTag() throws XMLStreamException {
      XMLEvent e;
      do {
         if (!this.hasNext()) {
            throw new XMLStreamException("Unexpected end of Document");
         }

         e = this.nextEvent();
         if (e.isCharacters() && !((Characters)e).isWhiteSpace()) {
            throw new XMLStreamException("Unexpected text");
         }
      } while(!e.isStartElement() && !e.isEndElement());

      return e;
   }

   public boolean hasNext() {
      try {
         while(this.parent.hasNext()) {
            if (this.filter.accept(this.parent.peek())) {
               return true;
            }

            this.parent.nextEvent();
         }

         return false;
      } catch (XMLStreamException var2) {
         return false;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public XMLEvent peek() throws XMLStreamException {
      return this.hasNext() ? this.parent.peek() : null;
   }

   public void close() throws XMLStreamException {
      this.parent.close();
   }

   public Object getProperty(String name) {
      return this.parent.getProperty(name);
   }

   public static void main(String[] args) throws Exception {
      System.setProperty("javax.xml.stream.XMLInputFactory", "com.bea.xml.stream.MXParserFactory");
      System.setProperty("javax.xml.stream.XMLEventFactory", "com.bea.xml.stream.EventFactory");
      XMLInputFactory factory = XMLInputFactory.newInstance();
      TypeFilter f = new TypeFilter();
      f.addType(1);
      f.addType(2);
      XMLEventReader reader = factory.createFilteredReader(factory.createXMLEventReader(new FileReader(args[0])), f);

      while(reader.hasNext()) {
         System.out.println(reader.nextEvent());
      }

   }
}
