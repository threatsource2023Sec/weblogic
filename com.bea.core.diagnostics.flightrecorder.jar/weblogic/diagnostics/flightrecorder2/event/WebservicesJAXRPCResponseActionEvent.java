package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXRPC Response")
@Name("com.oracle.weblogic.webservices.WebservicesJAXRPCResponseActionEvent")
@Description("Webservices JAX-RPC response action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXRPC"})
public class WebservicesJAXRPCResponseActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
