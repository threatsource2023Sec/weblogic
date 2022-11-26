package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

class ListBasedXMLEventReader extends AbstractXMLEventReader {
   private final List events;
   @Nullable
   private XMLEvent currentEvent;
   private int cursor = 0;

   public ListBasedXMLEventReader(List events) {
      Assert.notNull(events, (String)"XMLEvent List must not be null");
      this.events = new ArrayList(events);
   }

   public boolean hasNext() {
      return this.cursor < this.events.size();
   }

   public XMLEvent nextEvent() {
      if (this.hasNext()) {
         this.currentEvent = (XMLEvent)this.events.get(this.cursor);
         ++this.cursor;
         return this.currentEvent;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Nullable
   public XMLEvent peek() {
      return this.hasNext() ? (XMLEvent)this.events.get(this.cursor) : null;
   }

   public String getElementText() throws XMLStreamException {
      this.checkIfClosed();
      if (this.currentEvent != null && this.currentEvent.isStartElement()) {
         StringBuilder builder = new StringBuilder();

         while(true) {
            XMLEvent event = this.nextEvent();
            if (event.isEndElement()) {
               return builder.toString();
            }

            if (!event.isCharacters()) {
               throw new XMLStreamException("Unexpected non-text event: " + event);
            }

            Characters characters = event.asCharacters();
            if (!characters.isIgnorableWhiteSpace()) {
               builder.append(event.asCharacters().getData());
            }
         }
      } else {
         throw new XMLStreamException("Not at START_ELEMENT: " + this.currentEvent);
      }
   }

   @Nullable
   public XMLEvent nextTag() throws XMLStreamException {
      this.checkIfClosed();

      while(true) {
         XMLEvent event = this.nextEvent();
         switch (event.getEventType()) {
            case 1:
            case 2:
               return event;
            case 3:
            case 5:
            case 6:
               break;
            case 4:
            case 12:
               if (!event.asCharacters().isWhiteSpace()) {
                  throw new XMLStreamException("Non-ignorable whitespace CDATA or CHARACTERS event: " + event);
               }
               break;
            case 7:
            case 9:
            case 10:
            case 11:
            default:
               throw new XMLStreamException("Expected START_ELEMENT or END_ELEMENT: " + event);
            case 8:
               return null;
         }
      }
   }

   public void close() {
      super.close();
      this.events.clear();
   }
}
