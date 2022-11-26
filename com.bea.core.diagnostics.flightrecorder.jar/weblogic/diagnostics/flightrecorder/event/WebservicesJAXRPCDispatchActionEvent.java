package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXRPC Dispatch",
   description = "Webservices JAX-RPC dispatch action information with timing",
   path = "wls/Webservices/JAXRPC/Webservices_JAXRPC_Dispatch",
   thread = true
)
public class WebservicesJAXRPCDispatchActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
