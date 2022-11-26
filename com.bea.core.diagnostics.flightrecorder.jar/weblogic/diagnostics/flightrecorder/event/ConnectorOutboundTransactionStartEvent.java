package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Transaction Start",
   description = "This event indicates the start of a connector outbound transaction",
   path = "wls/Connector/Connector_Outbound_Transaction_Start",
   thread = true
)
public class ConnectorOutboundTransactionStartEvent extends ConnectorTransactionBaseEvent {
}
