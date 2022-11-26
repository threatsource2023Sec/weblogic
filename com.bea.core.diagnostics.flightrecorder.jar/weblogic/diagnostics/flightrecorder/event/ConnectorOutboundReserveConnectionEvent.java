package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Reserve Connection",
   description = "Generated when an outbound connection is reserved",
   path = "wls/Connector/Connector_Outbound_Reserve_Connection",
   thread = true
)
public class ConnectorOutboundReserveConnectionEvent extends ConnectorTransactionBaseEvent {
}
