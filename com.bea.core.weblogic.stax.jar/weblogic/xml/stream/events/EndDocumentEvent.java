package weblogic.xml.stream.events;

import weblogic.xml.stream.EndDocument;

/** @deprecated */
@Deprecated
public class EndDocumentEvent extends ElementEvent implements EndDocument {
   public EndDocumentEvent() {
      this.init();
   }

   protected void init() {
      this.type = 512;
   }

   public String toString() {
      return "<? EndDocument ?>";
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         return obj instanceof EndDocumentEvent;
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result;
      return result;
   }
}
