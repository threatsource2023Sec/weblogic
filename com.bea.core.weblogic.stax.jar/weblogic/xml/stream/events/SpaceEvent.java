package weblogic.xml.stream.events;

import weblogic.xml.stream.Location;
import weblogic.xml.stream.Space;

/** @deprecated */
@Deprecated
public class SpaceEvent extends CharacterDataEvent implements Space {
   protected boolean ignorable = false;

   public SpaceEvent() {
      this.init();
   }

   public SpaceEvent(String content) {
      this.init();
      this.content = content;
   }

   public SpaceEvent(String content, Location location) {
      this.init();
      this.content = content;
      this.location = location;
   }

   public void setIgnorable(boolean ignorable) {
      this.ignorable = ignorable;
   }

   protected void init() {
      this.type = 64;
   }

   public boolean ignorable() {
      return this.ignorable;
   }

   public String toString() {
      return this.content;
   }
}
