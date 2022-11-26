package weblogic.xml.stax.events;

import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;

public class EntityReferenceEvent extends BaseEvent implements EntityReference {
   private String name;
   private String replacementText;
   private EntityDeclaration ed;

   public EntityReferenceEvent() {
      this.init();
   }

   public EntityReferenceEvent(String name, EntityDeclaration ed) {
      this.init();
      this.name = name;
      this.replacementText = this.replacementText;
      this.ed = ed;
   }

   public String getReplacementText() {
      return this.replacementText;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setReplacementText(String text) {
      this.replacementText = text;
   }

   public String getBaseURI() {
      return null;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }

   public EntityDeclaration getDeclaration() {
      return this.ed;
   }

   protected void init() {
      this.setEventType(9);
   }

   public String toString() {
      String replacement = this.getReplacementText();
      if (replacement == null) {
         replacement = "";
      }

      return "&" + this.getName() + ":='" + replacement + "'";
   }
}
