package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Request",
   description = "This event covers the servlet request actions (doPost, doGet), including the elapsed time",
   path = "wls/Servlet/Servlet_Request",
   thread = true
)
public class ServletRequestActionEvent extends ServletBaseTimedEvent {
}
