package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Enlist Resource",
   description = "Generated when an outbound connection enlists a resource in a transaction",
   path = "wls/Connector/Connector_Outbound_Enlist_Resource",
   thread = true
)
public class ConnectorOutboundEnlistResourceEvent extends ConnectorTransactionBaseEvent {
}
