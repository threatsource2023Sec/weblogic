package oracle.jrockit.jfr.parser;

import java.util.HashMap;

class ContentTypeResolver {
   final ContentTypeDescriptor desc;
   final HashMap map;
   final long timestamp;
   final ContentTypeResolver next;

   public ContentTypeResolver(ContentTypeDescriptor desc, HashMap map, long timestamp, ContentTypeResolver next) {
      this.desc = desc;
      this.map = map;
      this.timestamp = timestamp;
      this.next = next;

      assert next == null || next.timestamp >= timestamp;

   }

   public String toString() {
      StringBuilder buf = (new StringBuilder(this.desc.toString())).append(", ").append(this.map.size()).append(" entries.");
      return buf.toString();
   }
}
