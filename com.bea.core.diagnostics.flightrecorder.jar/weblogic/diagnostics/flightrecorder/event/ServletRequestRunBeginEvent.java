package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Request Run Begin",
   description = "This event covers start of servlet request work thread run, including the start time. This is generated in conjunction with a timed event, but this event can show long running in-flight calls that are in progress when a JFR snaphot is captured",
   path = "wls/Servlet/Servlet_Request_Run_Begin",
   thread = true
)
public class ServletRequestRunBeginEvent extends ServletBaseInstantEvent {
   public ServletRequestRunBeginEvent(ServletRequestRunEvent timedEvent) {
      super(timedEvent);
   }
}
