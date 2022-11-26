package weblogic.xml.stax.events;

import java.util.List;
import javax.xml.stream.events.DTD;

public class DTDEvent extends BaseEvent implements DTD {
   private List notations;
   private List entities;
   private String dtd;

   public DTDEvent() {
   }

   public DTDEvent(String dtd) {
      this.init();
      this.setDTD(dtd);
   }

   protected void init() {
      this.setEventType(11);
   }

   public void setDTD(String dtd) {
      this.dtd = dtd;
   }

   public Object getProcessedDTD() {
      return null;
   }

   public String getDocumentTypeDeclaration() {
      return this.dtd;
   }

   public String toString() {
      return this.dtd;
   }

   public List getEntities() {
      return this.entities;
   }

   public List getNotations() {
      return this.notations;
   }
}
