package weblogic.xml.stream.events;

import weblogic.xml.stream.ProcessingInstruction;
import weblogic.xml.stream.XMLName;

/** @deprecated */
@Deprecated
public class ProcessingInstructionEvent extends ElementEvent implements ProcessingInstruction {
   public ProcessingInstructionEvent() {
      this.init();
   }

   public ProcessingInstructionEvent(XMLName name, String content) {
      this.init();
      this.name = name;
      this.content = content;
   }

   protected void init() {
      this.type = 8;
   }

   public String getTarget() {
      return this.name.toString();
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

   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else if (!(obj instanceof ProcessingInstruction)) {
         return false;
      } else {
         ProcessingInstruction processingInstruction = (ProcessingInstruction)obj;
         return compare(this.content, processingInstruction.getData());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.content == null ? 0 : this.content.hashCode());
      return result;
   }
}
