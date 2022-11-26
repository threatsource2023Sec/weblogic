package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Request Cancel",
   description = "This event covers the servlet request work is cancelled, including the elapsed time",
   path = "wls/Servlet/Servlet_Request_Cancel",
   thread = true
)
public class ServletRequestCancelEvent extends ServletBaseTimedEvent {
}
