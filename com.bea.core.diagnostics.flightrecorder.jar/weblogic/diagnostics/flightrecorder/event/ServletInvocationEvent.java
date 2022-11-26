package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Invocation",
   description = "This event covers the execution of a servlet invocation, including the elapsed time",
   path = "wls/Servlet/Servlet_Invocation",
   thread = true
)
public class ServletInvocationEvent extends ServletBaseTimedEvent {
}
