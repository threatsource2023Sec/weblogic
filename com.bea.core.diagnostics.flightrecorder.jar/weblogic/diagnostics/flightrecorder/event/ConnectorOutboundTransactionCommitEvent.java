package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Transaction Commit",
   description = "Generated for a successful outbound transaction commit event",
   path = "wls/Connector/Connector_Outbound_Transaction_Commit",
   thread = true
)
public class ConnectorOutboundTransactionCommitEvent extends ConnectorTransactionBaseEvent {
}
