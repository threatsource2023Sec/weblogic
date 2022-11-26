package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXRPC Response",
   description = "Webservices JAX-RPC response action information with timing",
   path = "wls/Webservices/JAXRPC/Webservices_JAXRPC_Response",
   thread = true
)
public class WebservicesJAXRPCResponseActionEvent extends WebservicesJAXRPCBaseTimedEvent {
}
