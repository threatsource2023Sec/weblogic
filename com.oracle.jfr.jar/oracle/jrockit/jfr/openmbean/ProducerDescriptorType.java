package oracle.jrockit.jfr.openmbean;

import java.util.Collection;
import java.util.List;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.ProducerDescriptor;

public class ProducerDescriptorType extends JFRMBeanType {
   private final EventDescriptorType events;

   public ProducerDescriptorType(EventDescriptorType events) throws OpenDataException {
      super("ProducerDescriptor", "Producer descriptor", new String[]{"id", "name", "description", "uri", "events"}, new String[]{"Producer ID", "Producer name", "Description", "URI", "Defined events"}, new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, new ArrayType(1, events.getType())});
      this.events = events;
   }

   public CompositeData toCompositeTypeData(ProducerDescriptor t) throws OpenDataException {
      Collection c = t.getEvents();
      List l = this.events.toCompositeData(c);
      CompositeData[] array = (CompositeData[])l.toArray(new CompositeData[l.size()]);
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.getId(), t.getName(), t.getDescription(), t.getURI().toString(), array});
   }
}
