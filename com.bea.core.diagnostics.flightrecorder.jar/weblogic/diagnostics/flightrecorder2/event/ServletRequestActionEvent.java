package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Request")
@Name("com.oracle.weblogic.servlet.ServletRequestActionEvent")
@Description("This event covers the servlet request actions (doPost, doGet), including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletRequestActionEvent extends ServletBaseEvent {
}
