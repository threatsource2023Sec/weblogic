package com.bea.xbean.common;

import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;

/** @deprecated */
public class GenericXmlInputStream implements XMLInputStream {
   private boolean _initialized;
   private EventItem _nextEvent;
   private int _elementCount;
   private GenericXmlInputStream _master;

   public GenericXmlInputStream() {
      this._master = this;
      this._elementCount = 1;
   }

   private GenericXmlInputStream(GenericXmlInputStream master) {
      (this._master = master).ensureInit();
      this._nextEvent = master._nextEvent;
   }

   protected XMLEvent nextEvent() throws XMLStreamException {
      throw new RuntimeException("nextEvent not overridden");
   }

   private void ensureInit() {
      if (!this._master._initialized) {
         try {
            this._master._nextEvent = this.getNextEvent();
         } catch (XMLStreamException var2) {
            throw new RuntimeException(var2);
         }

         this._master._initialized = true;
      }

   }

   private EventItem getNextEvent() throws XMLStreamException {
      XMLEvent e = this.nextEvent();
      return e == null ? null : new EventItem(e);
   }

   public XMLEvent next() throws XMLStreamException {
      this.ensureInit();
      EventItem currentEvent = this._nextEvent;
      if (this._nextEvent != null) {
         if (this._nextEvent._next == null) {
            this._nextEvent._next = this._master.getNextEvent();
         }

         this._nextEvent = this._nextEvent._next;
      }

      if (currentEvent == null) {
         return null;
      } else {
         if (currentEvent.getType() == 4) {
            if (--this._elementCount <= 0) {
               this._nextEvent = null;
            }
         } else if (currentEvent.getType() == 2) {
            ++this._elementCount;
         }

         return currentEvent._event;
      }
   }

   public boolean hasNext() throws XMLStreamException {
      this.ensureInit();
      return this._nextEvent != null;
   }

   public void skip() throws XMLStreamException {
      this.next();
   }

   public void skipElement() throws XMLStreamException {
      this.ensureInit();

      while(this._nextEvent != null && this._nextEvent.getType() != 2) {
         this.next();
      }

      for(int count = 0; this._nextEvent != null; this.next()) {
         int type = this.next().getType();
         if (type == 2) {
            ++count;
         } else if (type == 4) {
            --count;
            if (count == 0) {
               break;
            }
         }
      }

   }

   public XMLEvent peek() throws XMLStreamException {
      this.ensureInit();
      return this._nextEvent._event;
   }

   public boolean skip(int eventType) throws XMLStreamException {
      this.ensureInit();

      while(this._nextEvent != null) {
         if (this._nextEvent.getType() == eventType) {
            return true;
         }

         this.next();
      }

      return false;
   }

   public boolean skip(XMLName name) throws XMLStreamException {
      this.ensureInit();

      while(this._nextEvent != null) {
         if (this._nextEvent.hasName() && this._nextEvent.getName().equals(name)) {
            return true;
         }

         this.next();
      }

      return false;
   }

   public boolean skip(XMLName name, int eventType) throws XMLStreamException {
      this.ensureInit();

      while(this._nextEvent != null) {
         if (this._nextEvent.getType() == eventType && this._nextEvent.hasName() && this._nextEvent.getName().equals(name)) {
            return true;
         }

         this.next();
      }

      return false;
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      this.ensureInit();
      GenericXmlInputStream subStream = new GenericXmlInputStream(this);
      subStream.skip(2);
      return subStream;
   }

   public void close() throws XMLStreamException {
   }

   public ReferenceResolver getReferenceResolver() {
      this.ensureInit();
      throw new RuntimeException("Not impl");
   }

   public void setReferenceResolver(ReferenceResolver resolver) {
      this.ensureInit();
      throw new RuntimeException("Not impl");
   }

   private class EventItem {
      final XMLEvent _event;
      EventItem _next;

      EventItem(XMLEvent e) {
         this._event = e;
      }

      int getType() {
         return this._event.getType();
      }

      boolean hasName() {
         return this._event.hasName();
      }

      XMLName getName() {
         return this._event.getName();
      }
   }
}
