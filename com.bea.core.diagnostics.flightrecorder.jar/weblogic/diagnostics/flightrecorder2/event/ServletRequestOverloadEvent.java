package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Request Overload")
@Name("com.oracle.weblogic.ervlet.ServletRequestOverloadEvent")
@Description("This event covers the servlet request work overload action processing, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletRequestOverloadEvent extends ServletBaseEvent {
}
