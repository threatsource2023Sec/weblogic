package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Request Run Begin")
@Name("com.oracle.weblogic.servlet.ServletRequestRunBeginEvent")
@Description("This event covers start of servlet request work thread run, including the start time. This is generated in conjunction with a timed event, but this event can show long running in-flight calls that are in progress when a JFR snaphot is captured")
@Category({"WebLogic Server", "Servlet"})
public class ServletRequestRunBeginEvent extends ServletBaseEvent {
   public ServletRequestRunBeginEvent(ServletRequestRunEvent timedEvent) {
      super(timedEvent);
   }

   public boolean isEventTimed() {
      return false;
   }
}
