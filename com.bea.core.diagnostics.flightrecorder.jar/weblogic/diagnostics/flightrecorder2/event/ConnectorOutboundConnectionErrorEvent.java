package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Connection Error")
@Description("Connector information")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundConnectionErrorEvent")
public class ConnectorOutboundConnectionErrorEvent extends ConnectorTransactionBaseEvent {
}
