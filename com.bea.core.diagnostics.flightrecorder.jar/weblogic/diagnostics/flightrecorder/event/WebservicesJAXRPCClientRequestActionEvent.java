package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXRPC Client Request",
   description = "Webservices JAX-RPC client request action information with timing",
   path = "wls/Webservices/JAXRPC/Webservices_JAXRPC_Client_Request",
   thread = true
)
public class WebservicesJAXRPCClientRequestActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
