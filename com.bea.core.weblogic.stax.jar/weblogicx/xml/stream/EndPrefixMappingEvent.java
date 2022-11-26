package weblogicx.xml.stream;

import org.xml.sax.Locator;

public class EndPrefixMappingEvent extends PrefixMappingEvent {
   public EndPrefixMappingEvent(Object source, Locator locator, String prefix) {
      super(source, locator, prefix);
   }

   public String toString() {
      return "[" + this.getClass().getName() + ": " + this.getPrefix() + "]";
   }
}
