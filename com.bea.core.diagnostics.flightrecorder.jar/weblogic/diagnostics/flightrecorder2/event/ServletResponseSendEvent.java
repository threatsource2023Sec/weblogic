package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Response Send")
@Name("com.oracle.weblogic.servlet.ServletResponseSendEvent")
@Description("This event covers the execution of the servlet response send, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletResponseSendEvent extends ServletBaseEvent {
}
