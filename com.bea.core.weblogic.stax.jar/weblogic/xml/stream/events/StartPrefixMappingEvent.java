package weblogic.xml.stream.events;

import weblogic.xml.stream.Location;
import weblogic.xml.stream.StartPrefixMapping;

/** @deprecated */
@Deprecated
public class StartPrefixMappingEvent extends ElementEvent implements StartPrefixMapping {
   protected String prefix;
   protected String namespaceUri;

   public StartPrefixMappingEvent() {
      this.init();
   }

   public StartPrefixMappingEvent(String prefix, String namespaceUri) {
      this.init();
      this.namespaceUri = namespaceUri;
      this.prefix = prefix;
   }

   public StartPrefixMappingEvent(String prefix, String namespaceUri, Location location) {
      this.init();
      this.namespaceUri = namespaceUri;
      this.prefix = prefix;
      this.location = location;
   }

   protected void init() {
      this.type = 1024;
   }

   public String getNamespaceUri() {
      return this.namespaceUri;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String toString() {
      return "<?beginnamespace:" + this.prefix + " " + this.namespaceUri + "?>";
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof StartPrefixMapping)) {
         return false;
      } else {
         StartPrefixMapping startPrefixMapping = (StartPrefixMapping)obj;
         return this.prefix.equals(startPrefixMapping.getPrefix()) && this.namespaceUri.equals(startPrefixMapping.getNamespaceUri());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.namespaceUri == null ? 0 : this.namespaceUri.hashCode());
      result = 31 * result + (this.prefix == null ? 0 : this.prefix.hashCode());
      return result;
   }
}
