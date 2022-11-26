package weblogicx.xml.stream;

import org.xml.sax.Locator;

public class ChangePrefixMappingEvent extends StartPrefixMappingEvent {
   private String olduri;

   public ChangePrefixMappingEvent(Object source, Locator locator, String prefix, String uri, String olduri) {
      super(source, locator, prefix, uri);
      this.olduri = olduri;
   }

   public String getOldURI() {
      return this.olduri;
   }

   public String toString() {
      return "[" + this.getClass().getName() + ": " + this.getPrefix() + " -> " + this.getURI() + "(" + this.olduri + ")]";
   }
}
