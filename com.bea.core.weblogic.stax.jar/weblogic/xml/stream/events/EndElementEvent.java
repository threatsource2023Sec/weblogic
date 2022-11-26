package weblogic.xml.stream.events;

import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.Location;
import weblogic.xml.stream.XMLName;

/** @deprecated */
@Deprecated
public class EndElementEvent extends ElementEvent implements EndElement {
   public EndElementEvent() {
      this.init();
   }

   protected void init() {
      this.type = 4;
   }

   public EndElementEvent(XMLName name) {
      this.name = name;
   }

   public EndElementEvent(XMLName name, Location location) {
      this.name = name;
      this.location = location;
   }

   public String toString() {
      return "</" + this.name + ">";
   }
}
