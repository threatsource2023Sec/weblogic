package weblogic.xml.stream.events;

import weblogic.xml.stream.ChangePrefixMapping;

/** @deprecated */
@Deprecated
public class ChangePrefixMappingEvent extends ElementEvent implements ChangePrefixMapping {
   protected String prefix;
   protected String oldNamespaceUri;
   protected String newNamespaceUri;

   public ChangePrefixMappingEvent() {
      this.init();
   }

   public ChangePrefixMappingEvent(String oldNamespaceUri, String newNamespaceUri, String prefix) {
      this.init();
      this.oldNamespaceUri = oldNamespaceUri;
      this.newNamespaceUri = newNamespaceUri;
      this.prefix = prefix;
   }

   protected void init() {
      this.type = 4096;
   }

   public String getNewNamespaceUri() {
      return this.newNamespaceUri;
   }

   public String getOldNamespaceUri() {
      return this.oldNamespaceUri;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String toString() {
      return "<?changenamespace:" + this.prefix + " old:" + this.oldNamespaceUri + " new:" + this.newNamespaceUri + "?>";
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof ChangePrefixMapping)) {
         return false;
      } else {
         ChangePrefixMapping changePrefixMapping = (ChangePrefixMapping)obj;
         return this.oldNamespaceUri.equals(changePrefixMapping.getOldNamespaceUri()) && this.newNamespaceUri.equals(changePrefixMapping.getNewNamespaceUri()) && this.prefix.equals(changePrefixMapping.getPrefix());
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.newNamespaceUri == null ? 0 : this.newNamespaceUri.hashCode());
      result = 31 * result + (this.oldNamespaceUri == null ? 0 : this.oldNamespaceUri.hashCode());
      result = 31 * result + (this.prefix == null ? 0 : this.prefix.hashCode());
      return result;
   }
}
