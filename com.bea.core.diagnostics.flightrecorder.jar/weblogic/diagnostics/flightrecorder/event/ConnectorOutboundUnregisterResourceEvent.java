package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Unregister Resource",
   description = "Connector information",
   path = "wls/Connector/Connector_Outbound_Unregister_Resource",
   thread = true
)
public class ConnectorOutboundUnregisterResourceEvent extends ConnectorTransactionBaseEvent {
}
