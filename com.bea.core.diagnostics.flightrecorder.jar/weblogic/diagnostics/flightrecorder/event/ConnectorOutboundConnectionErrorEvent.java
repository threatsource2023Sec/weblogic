package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Connection Error",
   description = "Connector information",
   path = "wls/Connector/Connector_Outbound_Connection_Error",
   thread = true
)
public class ConnectorOutboundConnectionErrorEvent extends ConnectorTransactionBaseEvent {
}
