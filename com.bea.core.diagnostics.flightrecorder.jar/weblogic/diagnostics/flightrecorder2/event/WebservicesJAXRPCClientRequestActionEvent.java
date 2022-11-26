package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXRPC Client Request")
@Name("com.oracle.weblogic.webservices.WebservicesJAXRPCClientRequestActionEvent")
@Description("Webservices JAX-RPC client request action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXRPC"})
public class WebservicesJAXRPCClientRequestActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
