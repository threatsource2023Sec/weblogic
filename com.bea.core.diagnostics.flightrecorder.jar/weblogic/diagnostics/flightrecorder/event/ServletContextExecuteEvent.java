package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Context Execute",
   description = "This event covers the execution of the servlet context execute call, including the elapsed time",
   path = "wls/Servlet/Servlet_Context_Execute",
   thread = true
)
public class ServletContextExecuteEvent extends ServletBaseTimedEvent {
}
