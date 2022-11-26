package org.apache.xml.security.stax.impl;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class XMLSecurityEventReader implements XMLEventReader {
   private final Iterator xmlSecEventIterator;
   private XMLEvent xmlSecEvent;

   public XMLSecurityEventReader(Deque xmlSecEvents, int fromIndex) {
      this.xmlSecEventIterator = xmlSecEvents.descendingIterator();
      int curIdx = 0;

      while(curIdx++ < fromIndex) {
         this.xmlSecEventIterator.next();
      }

   }

   public XMLEvent nextEvent() throws XMLStreamException {
      XMLEvent currentXMLEvent;
      if (this.xmlSecEvent != null) {
         currentXMLEvent = this.xmlSecEvent;
         this.xmlSecEvent = null;
         return currentXMLEvent;
      } else {
         try {
            currentXMLEvent = (XMLEvent)this.xmlSecEventIterator.next();
            return currentXMLEvent;
         } catch (NoSuchElementException var3) {
            throw new XMLStreamException(var3);
         }
      }
   }

   public boolean hasNext() {
      return this.xmlSecEvent != null ? true : this.xmlSecEventIterator.hasNext();
   }

   public XMLEvent peek() throws XMLStreamException {
      if (this.xmlSecEvent != null) {
         return this.xmlSecEvent;
      } else {
         try {
            this.xmlSecEvent = (XMLEvent)this.xmlSecEventIterator.next();
            return this.xmlSecEvent;
         } catch (NoSuchElementException var2) {
            return null;
         }
      }
   }

   public String getElementText() throws XMLStreamException {
      throw new XMLStreamException(new UnsupportedOperationException());
   }

   public XMLEvent nextTag() throws XMLStreamException {
      throw new XMLStreamException(new UnsupportedOperationException());
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      throw new IllegalArgumentException(new UnsupportedOperationException());
   }

   public void close() throws XMLStreamException {
   }

   public Object next() {
      try {
         return this.nextEvent();
      } catch (XMLStreamException var2) {
         throw new NoSuchElementException(var2.getMessage());
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
