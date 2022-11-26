package weblogic.xml.stax.events;

import javax.xml.stream.events.ProcessingInstruction;

public class ProcessingInstructionEvent extends BaseEvent implements ProcessingInstruction {
   String name;
   String content;

   public ProcessingInstructionEvent() {
      this.init();
   }

   public ProcessingInstructionEvent(String name, String content) {
      this.init();
      this.name = name;
      this.content = content;
   }

   protected void init() {
      this.setEventType(3);
   }

   public String getTarget() {
      return this.name;
   }

   public void setTarget(String target) {
      this.name = target;
   }

   public void setData(String data) {
      this.content = data;
   }

   public String getData() {
      return this.content;
   }

   public String toString() {
      if (this.content != null && this.name != null) {
         return "<?" + this.name + " " + this.content + "?>";
      } else if (this.name != null) {
         return "<?" + this.name + "?>";
      } else {
         return this.content != null ? "<?" + this.content + "?>" : "<??>";
      }
   }
}
