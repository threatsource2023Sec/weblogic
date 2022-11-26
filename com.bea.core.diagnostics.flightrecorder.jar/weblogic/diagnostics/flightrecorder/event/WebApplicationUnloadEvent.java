package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Web Application Unload",
   description = "This event covers the unloading of a web application, including the elapsed time",
   path = "wls/Servlet/Web_Application_Unload",
   thread = true
)
public class WebApplicationUnloadEvent extends WebApplicationBaseTimedEvent {
}
