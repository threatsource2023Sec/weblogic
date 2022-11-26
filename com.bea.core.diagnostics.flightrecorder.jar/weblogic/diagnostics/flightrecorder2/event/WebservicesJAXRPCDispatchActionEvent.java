package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Webservices JAXRPC Dispatch")
@Name("com.oracle.weblogic.webservices.WebservicesJAXRPCDispatchActionEvent")
@Description("Webservices JAX-RPC dispatch action information with timing")
@Category({"WebLogic Server", "Webservices", "JAXRPC"})
public class WebservicesJAXRPCDispatchActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
