package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Invocation")
@Name("com.oracle.weblogic.servlet.ServletInvocationEvent")
@Description("This event covers the execution of a servlet invocation, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletInvocationEvent extends ServletBaseEvent {
}
