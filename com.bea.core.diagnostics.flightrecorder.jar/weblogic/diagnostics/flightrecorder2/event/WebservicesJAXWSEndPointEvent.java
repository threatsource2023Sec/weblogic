package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXWS Endpoint")
@Name("com.oracle.weblogic.webservices.WebservicesJAXWSEndPointEvent")
@Description("Webservices JAX-WS end-point action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXWS"})
public class WebservicesJAXWSEndPointEvent extends WebservicesJAXWSBaseTimedEvent {
}
