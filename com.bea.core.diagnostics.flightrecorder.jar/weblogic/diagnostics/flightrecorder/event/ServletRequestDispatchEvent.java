package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Request Dispatch",
   description = "This event covers the servlet request work dispatch (forward, include), including the elapsed time",
   path = "wls/Servlet/Servlet_Request_Dispatch",
   thread = true
)
public class ServletRequestDispatchEvent extends ServletBaseTimedEvent {
}
