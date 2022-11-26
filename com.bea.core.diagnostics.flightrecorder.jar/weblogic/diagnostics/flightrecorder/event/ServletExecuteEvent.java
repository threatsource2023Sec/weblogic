package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Execute",
   description = "This event covers the servlet execute, including the elapsed time",
   path = "wls/Servlet/Servlet_Execute",
   thread = true
)
public class ServletExecuteEvent extends ServletBaseTimedEvent {
}
