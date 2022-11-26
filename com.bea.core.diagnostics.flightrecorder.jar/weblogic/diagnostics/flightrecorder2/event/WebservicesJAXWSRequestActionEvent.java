package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXWS Request Action")
@Name("com.oracle.weblogic.webservices.WebservicesJAXWSRequestActionEvent")
@Description("Webservices JAX-WS request action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXWS"})
public class WebservicesJAXWSRequestActionEvent extends WebservicesJAXWSBaseTimedEvent {
}
