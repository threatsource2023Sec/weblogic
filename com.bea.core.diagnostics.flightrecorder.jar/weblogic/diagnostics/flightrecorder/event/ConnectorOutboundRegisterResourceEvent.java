package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Register Resource",
   description = "Connector resource registration event",
   path = "wls/Connector/Connector_Outbound_Register_Resource",
   thread = true
)
public class ConnectorOutboundRegisterResourceEvent extends ConnectorTransactionBaseEvent {
}
