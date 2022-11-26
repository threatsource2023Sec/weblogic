package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Connection Closed")
@Description("Connector outbound connection closed")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundConnectionClosedEvent")
public class ConnectorOutboundConnectionClosedEvent extends ConnectorTransactionBaseEvent {
}
