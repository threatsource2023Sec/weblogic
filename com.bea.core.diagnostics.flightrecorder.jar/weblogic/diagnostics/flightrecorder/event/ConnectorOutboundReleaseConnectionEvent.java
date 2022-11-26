package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Connector Outbound Release Connection",
   description = "Connector connection release event",
   path = "wls/Connector/Connector_Outbound_Release_Connection",
   thread = true
)
public class ConnectorOutboundReleaseConnectionEvent extends ConnectorTransactionBaseEvent {
}
