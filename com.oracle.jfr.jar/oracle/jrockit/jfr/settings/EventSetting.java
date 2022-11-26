package oracle.jrockit.jfr.settings;

import com.oracle.jrockit.jfr.EventInfo;
import oracle.jrockit.jfr.events.EventDescriptor;

public final class EventSetting {
   private final int id;
   private final boolean enabled;
   private final boolean stacktrace;
   private final long thresholdNanos;
   private final long period;

   public EventSetting(int id, boolean enabled, boolean stacktrace, long threshold, long period) {
      this.id = id;
      this.enabled = enabled;
      this.stacktrace = stacktrace;
      this.thresholdNanos = threshold;
      this.period = period;
   }

   public EventSetting(int id) {
      this(id, false, false, -1L, -1L);
   }

   public EventSetting() {
      this(0);
   }

   public EventSetting(EventDescriptor event, boolean enabled, boolean stacktrace, long threshold, long period) {
      this(event.getId(), enabled, event.hasStackTrace() ? stacktrace : false, threshold, event.isRequestable() ? period : 0L);
   }

   public EventSetting(EventDescriptor event, EventSetting other) {
      this(event, other.isEnabled(), other.isStacktraceEnabled(), other.getThreshold(), other.getPeriod());
   }

   public EventSetting(EventInfo event, boolean enabled, boolean stacktrace, long threshold, long period) {
      this(event.getId(), enabled, event.hasStackTrace() ? stacktrace : false, threshold, event.isRequestable() ? period : 0L);
   }

   public EventSetting(EventInfo event, EventSetting other) {
      this(event, other.isEnabled(), other.isStacktraceEnabled(), other.getThreshold(), other.getPeriod());
   }

   public EventSetting(int id, EventSetting e) {
      this(id, e.isEnabled(), e.isStacktraceEnabled(), e.getThreshold(), e.getPeriod());
   }

   public EventSetting(EventSetting e1, EventSetting e2) {
      assert e1.id == e2.id || e2.id == 0;

      this.id = e1.id;
      this.enabled = e1.enabled || e2.enabled;
      this.stacktrace = e1.stacktrace || e2.stacktrace;
      long threshold = Math.min(e1.thresholdNanos, e2.thresholdNanos);
      if (threshold == -1L) {
         threshold = Math.max(e1.thresholdNanos, e2.thresholdNanos);
      }

      this.thresholdNanos = threshold;
      long period = Math.min(e1.period, e2.period);
      if (period == -1L) {
         period = Math.max(e1.period, e2.period);
      }

      this.period = period;
   }

   public EventSetting(EventInfo controller) {
      this(controller.getId());
   }

   public int getId() {
      return this.id;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isStacktraceEnabled() {
      return this.stacktrace;
   }

   public long getThreshold() {
      return this.thresholdNanos;
   }

   public long getPeriod() {
      return this.period;
   }

   public int hashCode() {
      return this.id;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("{ id=").append(this.id);
      if (this.enabled) {
         buf.append(", enabled");
      }

      if (this.stacktrace) {
         buf.append(", stacktrace");
      }

      if (this.thresholdNanos != 0L) {
         buf.append(", threshold=").append(this.thresholdNanos).append("ns");
      }

      if (this.period != 0L) {
         buf.append(", period=").append(this.period).append("ms");
      }

      buf.append(" }");
      return buf.toString();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof EventSetting)) {
         return false;
      } else {
         EventSetting o = (EventSetting)obj;
         return o.id == this.id && o.enabled == this.enabled && o.thresholdNanos == this.thresholdNanos && o.stacktrace == this.stacktrace && o.period == this.period;
      }
   }
}
