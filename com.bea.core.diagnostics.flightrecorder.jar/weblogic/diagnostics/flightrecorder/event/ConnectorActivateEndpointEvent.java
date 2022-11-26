package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Activate Endpoint",
   description = "Connector endpoint activation",
   path = "wls/Connector/Connector_Activate_Endpoint",
   thread = true
)
public class ConnectorActivateEndpointEvent extends ConnectorEndpointBaseEvent {
}
