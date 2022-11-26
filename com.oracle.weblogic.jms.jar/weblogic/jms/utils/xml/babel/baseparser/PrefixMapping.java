package weblogic.jms.utils.xml.babel.baseparser;

public class PrefixMapping {
   String prefix;
   String uri;
   String oldUri;

   PrefixMapping(String prefix, String uri, String oldUri) {
      this.prefix = prefix;
      this.uri = uri;
      this.oldUri = oldUri;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getUri() {
      return this.uri;
   }

   public String getOldUri() {
      return this.oldUri;
   }

   public String toString() {
      return "[" + this.prefix + "]:[" + this.uri + "]:[" + this.oldUri + "]";
   }
}
