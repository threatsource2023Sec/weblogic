package oracle.jrockit.jfr.openmbean;

import java.net.URI;
import java.net.URISyntaxException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.settings.EventDefault;
import oracle.jrockit.jfr.settings.EventSetting;

public class EventDefaultType extends LazyImmutableJFRMBeanType {
   static final Member PATTERN;

   public EventDefaultType() throws OpenDataException {
      super(EventDefault.class, "EventDefault", "Event defaults", PATTERN, EventSettingType.ENABLED, EventSettingType.STACKTRACE, EventSettingType.THRESHOLD, EventSettingType.PERIOD);
   }

   public CompositeData toCompositeTypeDataLazy(EventDefault d) throws OpenDataException {
      EventSetting t = d.getSetting();

      try {
         return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{d.getPattern().toString(), t.isEnabled(), t.isStacktraceEnabled(), t.getThreshold(), t.getPeriod()});
      } catch (OpenDataException var4) {
         throw (InternalError)(new InternalError()).initCause(var4);
      }
   }

   public EventDefault toJavaTypeDataLazy(CompositeData d) throws OpenDataException {
      EventSetting s = new EventSetting(0, this.booleanAt(d, EventSettingType.ENABLED), this.booleanAt(d, EventSettingType.STACKTRACE), this.longAt(d, EventSettingType.THRESHOLD), this.longAt(d, EventSettingType.PERIOD));

      try {
         return new EventDefault(new URI(this.stringAt(d, PATTERN)), s);
      } catch (URISyntaxException var4) {
         throw this.openDataException(var4);
      }
   }

   static {
      PATTERN = new Member("pattern", "URI pattern", SimpleType.STRING);
   }
}
