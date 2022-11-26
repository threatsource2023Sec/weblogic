package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Destroy Connection",
   description = "Connector information",
   path = "wls/Connector/Connector_Outbound_Destroy_Connection",
   thread = true
)
public class ConnectorOutboundDestroyConnectionEvent extends ConnectorTransactionBaseEvent {
}
