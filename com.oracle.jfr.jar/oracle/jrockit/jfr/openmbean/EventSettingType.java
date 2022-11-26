package oracle.jrockit.jfr.openmbean;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.JFRImpl;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.settings.EventSetting;

public class EventSettingType extends LazyImmutableJFRMBeanType {
   static final Member ID;
   static final Member ENABLED;
   static final Member STACKTRACE;
   static final Member THRESHOLD;
   static final Member PERIOD;
   private final JFRImpl jfrImpl;

   public EventSettingType(JFRImpl jfrImpl) throws OpenDataException {
      super(EventSetting.class, "EventSetting", "Event setting", ID, ENABLED, STACKTRACE, THRESHOLD, PERIOD);
      this.jfrImpl = jfrImpl;
   }

   public CompositeData toCompositeTypeDataLazy(EventSetting t) throws OpenDataException {
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.getId(), t.isEnabled(), t.isStacktraceEnabled(), t.getThreshold(), t.getPeriod()});
   }

   public EventSetting toJavaTypeDataLazy(CompositeData d) throws OpenDataException {
      try {
         int id = this.intAt(d, ID);
         EventDescriptor e = this.jfrImpl.getEvent(id);
         return new EventSetting(e, this.booleanAt(d, ENABLED), this.booleanAt(d, STACKTRACE), this.longAt(d, THRESHOLD), this.longAt(d, PERIOD));
      } catch (Exception var4) {
         throw this.openDataException(var4);
      }
   }

   static {
      ID = new Member("id", "Event ID", SimpleType.INTEGER);
      ENABLED = new Member("enabled", "Is event type enabled", SimpleType.BOOLEAN);
      STACKTRACE = new Member("stacktrace", "Is stacktrace recording enabled for event type", SimpleType.BOOLEAN);
      THRESHOLD = new Member("threshold", "Timing threshold for recording event type", SimpleType.LONG);
      PERIOD = new Member("requestPeriod", "Period for auto generating event type (ms)", SimpleType.LONG);
   }
}
