package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Web Application Load",
   description = "This event covers the loading of a web application, including the elapsed time",
   path = "wls/Servlet/Web_Application_Load",
   thread = true
)
public class WebApplicationLoadEvent extends WebApplicationBaseTimedEvent {
}
