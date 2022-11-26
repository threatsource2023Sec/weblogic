package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXRPC Client Response",
   description = "Webservices JAX-RPC client response action information with timing",
   path = "wls/Webservices/JAXRPC/Webservices_JAXRPC_Client_Response",
   thread = true
)
public class WebservicesJAXRPCClientResponseActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
