package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Response Send",
   description = "This event covers the execution of the servlet response send, including the elapsed time",
   path = "wls/Servlet/Servlet_Response_Send",
   thread = true
)
public class ServletResponseSendEvent extends ServletBaseTimedEvent {
}
