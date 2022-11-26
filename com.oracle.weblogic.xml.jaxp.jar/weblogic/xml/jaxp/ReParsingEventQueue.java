package weblogic.xml.jaxp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xml.sax.SAXException;

public class ReParsingEventQueue {
   private List events = new ArrayList();
   private List lastEvents = new ArrayList();

   public void addEvent(EventInfo info, EventHandler owner, int type) {
      info.owner = owner;
      info.type = type;
      this.events.add(info);
   }

   public void clearAllEvents() {
      this.lastEvents.clear();
      this.events.clear();
   }

   public void shiftLastEvents() {
      this.lastEvents.clear();
      this.lastEvents.addAll(this.events);
      this.events.clear();
   }

   public void reSendLastEvents() throws SAXException {
      Iterator iterator = this.lastEvents.iterator();

      while(iterator.hasNext()) {
         EventInfo info = (EventInfo)iterator.next();
         EventHandler handler = info.owner;
         handler.sendEvent(info);
      }

   }

   protected interface EventHandler {
      void sendEvent(EventInfo var1) throws SAXException;

      void registerQueue(ReParsingEventQueue var1);
   }

   protected static class EventInfo {
      public EventHandler owner = null;
      public int type = 0;
   }
}
