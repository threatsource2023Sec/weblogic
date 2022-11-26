package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

abstract class AbstractXMLEventReader implements XMLEventReader {
   private boolean closed;

   public Object next() {
      try {
         return this.nextEvent();
      } catch (XMLStreamException var2) {
         throw new NoSuchElementException(var2.getMessage());
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("remove not supported on " + ClassUtils.getShortName(this.getClass()));
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      throw new IllegalArgumentException("Property not supported: [" + name + "]");
   }

   public void close() {
      this.closed = true;
   }

   protected void checkIfClosed() throws XMLStreamException {
      if (this.closed) {
         throw new XMLStreamException("XMLEventReader has been closed");
      }
   }
}
