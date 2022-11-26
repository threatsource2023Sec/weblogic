package com.bea.staxb.runtime.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class PushBackStreamReader extends StreamReaderDelegate implements XMLStreamReader {
   int current_event = 0;
   boolean bufferedevent = false;

   public PushBackStreamReader(XMLStreamReader reader) {
      super(reader);
   }

   public int next() throws XMLStreamException {
      if (this.bufferedevent) {
         this.bufferedevent = false;
      } else {
         this.current_event = super.next();
      }

      return this.current_event;
   }

   public void bufferlastevent() {
      this.bufferedevent = true;
   }
}
