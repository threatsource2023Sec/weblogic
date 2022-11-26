package weblogicx.xml.stream;

import org.xml.sax.Locator;

public abstract class PrefixMappingEvent extends XMLEvent {
   private String prefix;

   public PrefixMappingEvent(Object source, Locator locator, String prefix) {
      super(source, locator);
      this.prefix = prefix;
   }

   public String getPrefix() {
      return this.prefix;
   }
}
