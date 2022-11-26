package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.EventInfo;
import oracle.jrockit.jfr.settings.EventSetting;

public interface EventControl extends EventInfo {
   void setEnabled(boolean var1);

   void setStackTraceEnabled(boolean var1);

   void setThreshold(long var1);

   long getThresholdTicks();

   long getPeriod();

   void setPeriod(long var1);

   void apply(EventSetting var1);

   EventDescriptor getDescriptor();
}
