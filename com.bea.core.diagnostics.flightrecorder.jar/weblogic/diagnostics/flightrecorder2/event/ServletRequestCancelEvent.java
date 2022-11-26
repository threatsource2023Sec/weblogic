package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Request Cancel")
@Name("com.oracle.weblogic.servlet.ServletRequestCancelEvent")
@Description("This event covers the servlet request work is cancelled, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletRequestCancelEvent extends ServletBaseEvent {
}
