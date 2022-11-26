package weblogic.xml.stream.events;

import weblogic.xml.stream.EndPrefixMapping;
import weblogic.xml.stream.Location;

/** @deprecated */
@Deprecated
public class EndPrefixMappingEvent extends ElementEvent implements EndPrefixMapping {
   protected String prefix;

   public EndPrefixMappingEvent() {
      this.init();
   }

   public EndPrefixMappingEvent(String prefix) {
      this.init();
      this.prefix = prefix;
   }

   public EndPrefixMappingEvent(String prefix, Location location) {
      this.init();
      this.prefix = prefix;
      this.location = location;
   }

   protected void init() {
      this.type = 2048;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String toString() {
      return "<?endnamespace:" + this.prefix + "?>";
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof EndPrefixMapping)) {
         return false;
      } else {
         EndPrefixMapping endPrefixMapping = (EndPrefixMapping)obj;
         return this.prefix.equals(endPrefixMapping.getPrefix());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.prefix == null ? 0 : this.prefix.hashCode());
      return result;
   }
}
