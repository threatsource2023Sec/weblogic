package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Filter")
@Name("com.oracle.weblogic.servlet.ServletFilterEvent")
@Description("This event covers the servlet filter doFilter, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletFilterEvent extends ServletBaseEvent {
}
