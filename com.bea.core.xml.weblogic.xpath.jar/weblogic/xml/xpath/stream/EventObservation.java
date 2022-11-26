package weblogic.xml.xpath.stream;

import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.XPathStreamObserver;

final class EventObservation implements Observation {
   private XMLEvent event;

   public EventObservation(XMLEvent e) {
      this.event = e;
   }

   public void notify(XPathStreamObserver o) {
      o.observe(this.event);
   }
}
