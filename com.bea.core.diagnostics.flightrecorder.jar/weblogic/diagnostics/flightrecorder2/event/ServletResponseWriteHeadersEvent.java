package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Response Write Headers")
@Name("com.oracle.weblogic.servlet.ServletResponseWriteHeadersEvent")
@Description("This event covers the execution of the servlet response writeHeaders call, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletResponseWriteHeadersEvent extends ServletBaseEvent {
}
