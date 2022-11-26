package weblogic.xml.stax.filters;

import javax.xml.stream.EventFilter;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class TypeFilter implements EventFilter, StreamFilter {
   protected boolean[] types = new boolean[20];

   public void addType(int type) {
      this.types[type] = true;
   }

   public boolean accept(XMLEvent e) {
      return this.types[e.getEventType()];
   }

   public boolean accept(XMLStreamReader r) {
      return this.types[r.getEventType()];
   }
}
