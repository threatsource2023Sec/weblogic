package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Webservices JAXWS Endpoint",
   description = "Webservices JAX-WS end-point action information with timing",
   path = "wls/Webservices/JAXWS/Webservices_JAXWS_Endpoint",
   thread = true
)
public class WebservicesJAXWSEndPointEvent extends WebservicesJAXWSBaseTimedEvent {
}
