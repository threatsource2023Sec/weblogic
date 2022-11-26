package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Inbound Transaction Rollback",
   description = "Connector information",
   path = "wls/Connector/Connector_Inbound_Transaction_Rollback",
   thread = true
)
public class ConnectorInboundTransactionRollbackEvent extends ConnectorTransactionBaseEvent {
}
