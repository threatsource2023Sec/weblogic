package oracle.jrockit.jfr.openmbean;

import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.events.JavaEventDescriptor;
import oracle.jrockit.jfr.events.ValueDescriptor;

public class EventDescriptorType extends LazyImmutableJFRMBeanType {
   static final Member NAME;
   static final Member DESC;
   static final Member PATH;
   static final Member EVENTURI;
   static final Member HASSTARTTIME;
   static final Member HASTHREAD;
   static final Member HASSTACKTRACE;
   static final Member TIMED;
   static final Member REQUESTABLE;

   public EventDescriptorType() throws OpenDataException {
      super(EventDescriptor.class, "EventDescriptor", "Event descriptor", EventSettingType.ID, NAME, DESC, PATH, EVENTURI, HASSTARTTIME, HASTHREAD, HASSTACKTRACE, TIMED, REQUESTABLE);
   }

   public EventDescriptor toJavaTypeDataLazy(CompositeData d) throws OpenDataException {
      try {
         return new JavaEventDescriptor(this.intAt(d, EventSettingType.ID), this.stringAt(d, NAME), this.stringAt(d, DESC), this.stringAt(d, PATH), new URI(this.stringAt(d, EVENTURI)), this.booleanAt(d, HASSTARTTIME), this.booleanAt(d, HASTHREAD), this.booleanAt(d, HASSTACKTRACE), this.booleanAt(d, TIMED), this.booleanAt(d, REQUESTABLE), new ValueDescriptor[0]);
      } catch (InvalidEventDefinitionException var3) {
         throw new OpenDataException(var3.getMessage());
      } catch (URISyntaxException var4) {
         throw new OpenDataException(var4.getMessage());
      }
   }

   public CompositeData toCompositeTypeDataLazy(EventDescriptor t) throws OpenDataException {
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.getId(), t.getName(), t.getDescription(), t.getPath(), t.getURI().toString(), t.hasStartTime(), t.hasThread(), t.hasStackTrace(), t.isTimed(), t.isRequestable()});
   }

   static {
      NAME = new Member("name", "Event name", SimpleType.STRING);
      DESC = new Member("description", "Description", SimpleType.STRING);
      PATH = new Member("path", "Event URI path", SimpleType.STRING);
      EVENTURI = new Member("uri", "Event URI", SimpleType.STRING);
      HASSTARTTIME = new Member("hasStartTime", "Does event have a starttime (describes a period of time)", SimpleType.BOOLEAN);
      HASTHREAD = new Member("hasThread", "Does event contain a generating thread", SimpleType.BOOLEAN);
      HASSTACKTRACE = new Member("isStackTraceAvailable", "Can stacktrace be recorded for event", SimpleType.BOOLEAN);
      TIMED = new Member("isTimed", "Is event timed (responds to threshold)", SimpleType.BOOLEAN);
      REQUESTABLE = new Member("isRequestable", "Can event be requested/generated periodically", SimpleType.BOOLEAN);
   }
}
