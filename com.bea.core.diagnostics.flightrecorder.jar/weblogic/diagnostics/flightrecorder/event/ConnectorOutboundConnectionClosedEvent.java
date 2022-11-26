package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Connection Closed",
   description = "Connector outbound connection closed",
   path = "wls/Connector/Connector_Outbound_Connection_Closed",
   thread = true
)
public class ConnectorOutboundConnectionClosedEvent extends ConnectorTransactionBaseEvent {
}
