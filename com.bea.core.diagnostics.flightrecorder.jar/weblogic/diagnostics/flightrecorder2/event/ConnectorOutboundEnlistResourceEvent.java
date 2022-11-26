package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Enlist Resource")
@Description("Generated when an outbound connection enlists a resource in a transaction")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundEnlistResourceEvent")
public class ConnectorOutboundEnlistResourceEvent extends ConnectorTransactionBaseEvent {
}
