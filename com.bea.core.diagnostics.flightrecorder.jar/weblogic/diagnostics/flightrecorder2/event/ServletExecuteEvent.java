package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Execute")
@Name("com.oracle.weblogic.servlet.ServletExecuteEvent")
@Description("This event covers the servlet execute, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletExecuteEvent extends ServletBaseEvent {
}
