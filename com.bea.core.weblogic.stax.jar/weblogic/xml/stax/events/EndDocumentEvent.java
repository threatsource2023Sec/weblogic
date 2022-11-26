package weblogic.xml.stax.events;

import javax.xml.stream.events.EndDocument;

public class EndDocumentEvent extends BaseEvent implements EndDocument {
   public EndDocumentEvent() {
      this.init();
   }

   protected void init() {
      this.setEventType(8);
   }

   public String toString() {
      return "<? EndDocument ?>";
   }
}
