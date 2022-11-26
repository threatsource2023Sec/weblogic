package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Deactivate Endpoint",
   description = "Connector endpoint deactivation",
   path = "wls/Connector/Connector_Deactivate_Endpoint",
   thread = true
)
public class ConnectorDeactivateEndpointEvent extends ConnectorEndpointBaseEvent {
}
