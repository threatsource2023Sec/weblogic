package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXRPC Client Response")
@Name("com.oracle.weblogic.webservices.WebservicesJAXRPCClientResponseActionEvent")
@Description("Webservices JAX-RPC client response action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXRPC"})
public class WebservicesJAXRPCClientResponseActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
