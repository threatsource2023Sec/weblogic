package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Reserve Connection")
@Description("Generated when an outbound connection is reserved")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundReserveConnectionEvent")
public class ConnectorOutboundReserveConnectionEvent extends ConnectorTransactionBaseEvent {
}
