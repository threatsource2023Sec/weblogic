package weblogic.xml.stax.events;

import javax.xml.stream.events.Comment;

public class CommentEvent extends CharactersEvent implements Comment {
   public CommentEvent() {
      this.init();
   }

   public CommentEvent(String data) {
      this.init();
      this.setData(data);
   }

   protected void init() {
      this.setEventType(5);
   }

   public String getText() {
      return this.getData();
   }

   public String toString() {
      return this.getText().length() == 0 ? "<!---->" : "<!--" + this.getText() + "-->";
   }
}
