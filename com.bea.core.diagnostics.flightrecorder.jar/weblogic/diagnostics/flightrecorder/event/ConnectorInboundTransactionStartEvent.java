package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Inbound Transaction Start",
   description = "Connector information",
   path = "wls/Connector/Connector_Inbound_Transaction_Start",
   thread = true
)
public class ConnectorInboundTransactionStartEvent extends ConnectorTransactionBaseEvent {
}
