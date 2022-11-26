package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Web Application Load")
@Name("com.oracle.weblogic.servlet.WebApplicationLoadEvent")
@Description("This event covers the loading of a web application, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class WebApplicationLoadEvent extends WebApplicationBaseTimedEvent {
}
