package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXRPC Request",
   description = "Webservices JAX-RPC request action information with timing",
   path = "wls/Webservices/JAXRPC/Webservices_JAXRPC_Request",
   thread = true
)
public class WebservicesJAXRPCRequestActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
