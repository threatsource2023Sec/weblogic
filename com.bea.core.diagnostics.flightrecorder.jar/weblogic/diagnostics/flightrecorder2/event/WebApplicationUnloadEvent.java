package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Web Application Unload")
@Name("com.oracle.weblogic.servlet.WebApplicationUnloadEvent")
@Description("This event covers the unloading of a web application, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class WebApplicationUnloadEvent extends WebApplicationBaseTimedEvent {
}
