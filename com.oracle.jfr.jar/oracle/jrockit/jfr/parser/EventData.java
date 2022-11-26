package oracle.jrockit.jfr.parser;

import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import oracle.jrockit.jfr.events.JavaEventDescriptor;

class EventData extends JavaEventDescriptor implements FLREventInfo {
   final int producer;
   final String xmlname;
   final String qname;

   public EventData(int producer, URI uri, String namespace, String name, String description, String path, boolean hasStartTime, boolean hasThread, boolean stacktrace, boolean timed, boolean requestable, int id, ValueData... values) throws InvalidValueException, InvalidEventDefinitionException {
      super(id, name, description, path, uri.resolve(path == null ? "" : path), hasStartTime, hasThread, stacktrace, timed, requestable, values);
      this.producer = producer;
      this.xmlname = ChunkParser.xmlName(path, name);
      this.qname = namespace + ":" + this.xmlname;
   }

   public List getValueInfos() {
      ValueData[] descs = (ValueData[])((ValueData[])this.getValues());
      return Arrays.asList(descs);
   }
}
