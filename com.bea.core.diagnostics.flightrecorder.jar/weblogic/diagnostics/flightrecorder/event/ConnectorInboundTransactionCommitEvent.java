package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Inbound Transaction Commit",
   description = "Connector inbound transaction commit event",
   path = "wls/Connector/Connector_Inbound_Transaction_Commit",
   thread = true
)
public class ConnectorInboundTransactionCommitEvent extends ConnectorTransactionBaseEvent {
}
