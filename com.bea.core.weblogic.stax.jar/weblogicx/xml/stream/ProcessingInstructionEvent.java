package weblogicx.xml.stream;

import org.xml.sax.Locator;

public class ProcessingInstructionEvent extends XMLEvent {
   private String target;
   private String data;

   public ProcessingInstructionEvent(Object source, Locator locator, String target, String data) {
      super(source, locator);
      this.target = target;
      this.data = data;
   }

   public String getTarget() {
      return this.target;
   }

   public String getData() {
      return this.data;
   }

   public String toString() {
      return "[PI: " + this.target + " -> " + this.data + "]";
   }
}
