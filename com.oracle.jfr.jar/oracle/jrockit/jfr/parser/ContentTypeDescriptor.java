package oracle.jrockit.jfr.parser;

import oracle.jrockit.jfr.events.ContentTypeImpl;
import oracle.jrockit.jfr.events.DataStructureDescriptor;
import oracle.jrockit.jfr.events.ValueDescriptor;

class ContentTypeDescriptor extends DataStructureDescriptor {
   final int index;
   final int structIndex;
   final String desc;
   final ProducerData producer;
   final ContentTypeImpl contentType;

   public ContentTypeDescriptor(ContentTypeImpl ct, String desc, int index, ProducerData producer, int structIndex) {
      super((ValueDescriptor[])producer.structs.get(structIndex));
      this.contentType = ct;
      this.desc = desc;
      this.index = index;
      this.producer = producer;
      this.structIndex = structIndex;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(this.producer.getName());
      buf.append(", ").append(this.desc).append(", ").append(this.index);
      return buf.toString();
   }
}
