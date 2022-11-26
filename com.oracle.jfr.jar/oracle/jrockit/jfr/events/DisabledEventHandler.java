package oracle.jrockit.jfr.events;

import oracle.jrockit.jfr.settings.EventSetting;

public final class DisabledEventHandler extends EventHandler {
   public DisabledEventHandler(JavaEventDescriptor descriptor) {
      super(descriptor);
   }

   public void write(Object receiver, long start, long end) {
   }

   public void apply(EventSetting s) {
   }

   public long getPeriod() {
      return 0L;
   }

   public void setEnabled(boolean enabled) {
   }

   public void setPeriod(long period) {
   }

   public void setStackTraceEnabled(boolean stacktraceOn) {
   }

   public void setThreshold(long threshold) {
   }

   public long getThresholdTicks() {
      return 0L;
   }

   public long getThreshold() {
      return -1L;
   }

   public boolean hasStartTime() {
      return false;
   }

   public boolean isEnabled() {
      return false;
   }

   public long counterTime() {
      return 0L;
   }

   public boolean hasStackTrace() {
      return false;
   }

   public boolean isStackTraceEnabled() {
      return false;
   }
}
