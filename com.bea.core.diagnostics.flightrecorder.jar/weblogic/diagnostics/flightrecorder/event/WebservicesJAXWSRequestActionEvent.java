package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXWS Request",
   description = "Webservices JAX-WS request action information with timing",
   path = "wls/Webservices/JAXWS/Webservices_JAXWS_Request",
   thread = true
)
public class WebservicesJAXWSRequestActionEvent extends WebservicesJAXWSBaseTimedEvent {
}
