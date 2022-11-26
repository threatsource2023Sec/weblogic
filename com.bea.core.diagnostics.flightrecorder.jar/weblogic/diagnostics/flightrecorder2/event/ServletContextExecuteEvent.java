package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Context Execute")
@Name("com.oracle.weblogic.servlet.ServletContextExecuteEvent")
@Description("This event covers the execution of the servlet context execute call, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletContextExecuteEvent extends ServletBaseEvent {
}
