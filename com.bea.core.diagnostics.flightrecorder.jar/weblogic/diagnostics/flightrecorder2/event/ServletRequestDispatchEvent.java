package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Request Dispatch")
@Name("com.oracle.weblogic.servlet.ServletRequestDispatchEvent")
@Description("This event covers the servlet request work dispatch (forward, include), including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletRequestDispatchEvent extends ServletBaseEvent {
}
