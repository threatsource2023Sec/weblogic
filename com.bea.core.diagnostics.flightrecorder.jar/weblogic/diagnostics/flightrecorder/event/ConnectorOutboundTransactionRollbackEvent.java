package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Transaction Rollback",
   description = "Generated on a connector outbound transaction rollback",
   path = "wls/Connector/Connector_Outbound_Transaction_Rollback",
   thread = true
)
public class ConnectorOutboundTransactionRollbackEvent extends ConnectorTransactionBaseEvent {
}
