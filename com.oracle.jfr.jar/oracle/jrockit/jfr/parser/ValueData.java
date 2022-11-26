package oracle.jrockit.jfr.parser;

import com.oracle.jrockit.jfr.DataType;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Transition;
import oracle.jrockit.jfr.events.ContentTypeImpl;
import oracle.jrockit.jfr.events.ValueDescriptor;

class ValueData extends ValueDescriptor implements FLRValueInfo {
   final int producer;
   final String xmlname;
   final String qname;
   Class type;

   public ValueData(int producer, String namespace, String id, String name, String descriptor, String relationKey, Transition trans, DataType dt, ContentTypeImpl ct, int arrayType) throws InvalidValueException {
      super(id, name, descriptor, relationKey, trans, dt, ct, arrayType, (String)null, (Class)null);
      this.producer = producer;
      this.xmlname = ChunkParser.xmlName("", id);
      this.qname = namespace + ":" + this.xmlname;
   }
}
