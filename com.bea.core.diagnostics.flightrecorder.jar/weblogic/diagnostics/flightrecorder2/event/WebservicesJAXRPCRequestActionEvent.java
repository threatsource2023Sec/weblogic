package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXRPC Request")
@Name("com.oracle.weblogic.webservices.WebservicesJAXRPCRequestActionEvent")
@Description("Webservices JAX-RPC request action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXRPC"})
public class WebservicesJAXRPCRequestActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
