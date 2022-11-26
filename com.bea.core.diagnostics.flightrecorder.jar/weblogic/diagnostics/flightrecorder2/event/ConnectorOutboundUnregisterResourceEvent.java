package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Unregister Resource")
@Description("Connector information")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundUnregisterResourceEvent")
public class ConnectorOutboundUnregisterResourceEvent extends ConnectorTransactionBaseEvent {
}
