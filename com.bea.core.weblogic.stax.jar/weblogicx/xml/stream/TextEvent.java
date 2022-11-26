package weblogicx.xml.stream;

import org.xml.sax.Locator;

public class TextEvent extends XMLEvent {
   private String text;

   public TextEvent(Object source, Locator locator, String text) {
      super(source, locator);
      this.text = text;
   }

   public String getText() {
      return this.text;
   }

   public String toString() {
      return "[TextEvent: " + this.text + "]";
   }
}
