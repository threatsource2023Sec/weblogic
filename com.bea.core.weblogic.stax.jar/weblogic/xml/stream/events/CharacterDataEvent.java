package weblogic.xml.stream.events;

import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.Location;

/** @deprecated */
@Deprecated
public class CharacterDataEvent extends ElementEvent implements CharacterData {
   public CharacterDataEvent() {
      this.init();
   }

   public CharacterDataEvent(String content) {
      this.init();
      this.content = content;
   }

   public CharacterDataEvent(String content, Location location) {
      this.init();
      this.content = content;
      this.location = location;
   }

   protected void init() {
      this.type = 16;
   }

   public String getContent() {
      return this.content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public boolean hasContent() {
      return this.content != null;
   }

   public String toString() {
      return this.content;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof CharacterData)) {
         return false;
      } else {
         CharacterData characterData = (CharacterData)obj;
         return this.content.equals(characterData.getContent());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.content == null ? 0 : this.content.hashCode());
      return result;
   }
}
