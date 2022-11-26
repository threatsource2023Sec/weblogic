package weblogicx.xml.stream;

import org.xml.sax.Locator;

public class StartPrefixMappingEvent extends PrefixMappingEvent {
   private String uri;

   public StartPrefixMappingEvent(Object source, Locator locator, String prefix, String uri) {
      super(source, locator, prefix);
      this.uri = uri;
   }

   public String getURI() {
      return this.uri;
   }

   public String toString() {
      return "[" + this.getClass().getName() + ": " + this.getPrefix() + " -> " + this.uri + "]";
   }
}
