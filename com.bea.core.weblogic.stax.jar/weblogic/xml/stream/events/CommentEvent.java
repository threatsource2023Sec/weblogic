package weblogic.xml.stream.events;

import weblogic.xml.stream.Comment;
import weblogic.xml.stream.Location;

/** @deprecated */
@Deprecated
public class CommentEvent extends CharacterDataEvent implements Comment {
   public CommentEvent() {
      this.init();
   }

   public CommentEvent(String content) {
      this.init();
      this.content = content;
   }

   public CommentEvent(String content, Location location) {
      this.init();
      this.content = content;
      this.location = location;
   }

   protected void init() {
      this.type = 32;
   }

   public String toString() {
      return this.content.length() == 0 ? "<!---->" : "<!--" + this.content + "-->";
   }
}
