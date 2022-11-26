package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Request Overload",
   description = "This event covers the servlet request work overload action processing, including the elapsed time",
   path = "wls/Servlet/Servlet_Request_Overload",
   thread = true
)
public class ServletRequestOverloadEvent extends ServletBaseTimedEvent {
}
